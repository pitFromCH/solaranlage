package com.pit.solarserver.weatherdata;

import com.pit.solarserver.configuration.Constants;
import com.pit.solarserver.data.HourlyWeather;
import com.pit.solarserver.data.Weather;
import com.pit.solarserver.data.WeatherData;
import com.pit.solarserver.helper.CircularBuffer;
import com.pit.solarserver.model.*;
import com.pit.solarserver.repository.SolarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy ', ' HH:mm");
    private static boolean firstReadTemeraturSensor = false;
    CircularBuffer temperatureBuffer = new CircularBuffer(10);

    @Autowired
    private SolarRepository solarRepository;

    @Qualifier(Constants.TEMPERATUR_DATA_BEAN)
    @Autowired
    private TemperaturSensor temperaturSensor;

    @Qualifier(Constants.OPEN_WEATHER_DATA_BEAN)
    @Autowired
    private OpenWeatherData openWeatherData;

    @Qualifier(Constants.MESSURED_DATA_BEAN)
    @Autowired
    private CurrentMeasuredData currentMeasuredData;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${solarserver.openweathermap.url}")
    private String url;

    @Value("${solarserver.switch.url}")
    public String switchURL;

    @Value("${solarserver.temperatursensor.roof}")
    private String roofTemperaturSensor;

    @Value("${solarserver.temperatursensor.boiler}")
    private String boilerTemperaturSensor;

    @Value("${solarserver.temperatursensor.solartube.hot}")
    private String solarTubeHotTemperaturSensor;

    @Value("${solarserver.temperatursensor.solartube.cold}")
    private String solarTubeColdTemperaturSensor;

    @Value("${solarserver.boilerswitch.24hThreshold}")
    private int heatingBoilerTemperature24hThreshold;
    @Value("${solarserver.boilerswitch.minSunShineDurance24h}")
    private int minSunShineDurance24h;

    @Value("${solarserver.boilerswitch.48hThreshold}")
    private int heatingBoilerTemperatur48hThreshold;
    @Value("${solarserver.boilerswitch.minSunShineDurance48h}")
    private int minSunShineDurance48h;

    public ScheduledTasks() {
        log.info("ScheduledTasks construct");
    }

    @Bean(initMethod="init")
    public void init() throws URISyntaxException, ParseException {
        collectWeatherData();
        calculateSolarModus();
    }

    //<second> <minute> <hour> <day-of-month> <month> <day-of-week> <year> <command>
    //every hour
    @Scheduled(cron = "0 0 * ? * *")
    //every minute
    //@Scheduled(cron = "0 * * ? * *")
    public void collectWeatherData() throws URISyntaxException {

        //get common data
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = GregorianCalendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        log.info("Collecting solar data, [date]="  + formatter.format(date) + ",  [hour]=" + hour);

        //read current temperature of all sensors if not yet read
        if (! ScheduledTasks.firstReadTemeraturSensor) {
            temperaturSensor.readAllTemperaturSensor();
        }

        //read current weather data from open
        openWeatherData.updateWeatherDataModell();
        WeatherData weatherData = openWeatherData.getOpenWeatherData();

        if (weatherData != null) {
            //save current outdoor temp
            saveSolarValue(Constants.TYPE_OUTDOOR, (int) weatherData.getCurrent().getTemp(), date, hour);

            int rain = 0;
            if (weatherData.getHourly().get(0).getRain() != null) {
                rain = (int) (100 * weatherData.getHourly().get(0).getRain().get1h());
            }
            saveSolarValue(Constants.TYPE_RAIN, rain, date, hour);
        }

        saveSolarValue(Constants.TYPE_BOILER, currentMeasuredData.getCurrentBoilerTemperature(), date, hour);
        saveSolarValue(Constants.TYPE_ROOF, currentMeasuredData.getCurrentRoofTemperature(), date, hour);
        saveSolarValue(Constants.TYPE_SOLAR_TUBE_HOT, currentMeasuredData.getCurrentSolarTubeHotTemperature(), date, hour);
        saveSolarValue(Constants.TYPE_SOLAR_TUBE_COLD, currentMeasuredData.getCurrentSolarTubeColdTemperature(), date, hour);
    }


    private void saveSolarValue(int type,int value, Date date, int hour) {
        SolarData solarData = solarRepository.findSolarDataByDateAndAndType(date, type);
        if (solarData == null) {
            //create new row
            solarData = new SolarData();
            solarData.setDate(date);
            solarData.setType(type);
        }
        try {
            SolarData.class.getMethod("setT" + hour, int.class).invoke(solarData, value);
            solarRepository.saveAndFlush(solarData);
        } catch (NoSuchMethodException| InvocationTargetException| IllegalAccessException ex) {
            log.error(ex.getMessage());
        }
    }

    //calculating boiler needs to heat up
    @Scheduled(cron = "0 15 21 * * ?")
    //every minute
    //@Scheduled(cron = "0 * * ? * *")
    public void calculateSolarModus() throws ParseException {
        log.info("Calculating solar modus");

        WeatherData weatherData = openWeatherData.getOpenWeatherData();
        ArrayList<HourlyWeather> hourlyWeatherlist = weatherData.getHourly();
        Calendar currentCalendar = GregorianCalendar.getInstance();
        Date currentDate = new Date(System.currentTimeMillis());
        currentCalendar.setTime(currentDate);
        int currenteDay = currentCalendar.get(Calendar.DATE);

        currentCalendar.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

        String nextDay = sdf.format(currentCalendar.getTime());
        currentCalendar.add(Calendar.DATE, 1);
        String dayAfterNextDay = sdf.format(currentCalendar.getTime());;

        int numberOfHourHeatingPercentNext24h = 0;

        int numberOfHourHeatingPercentNext48h = 0;

        Calendar calendar = GregorianCalendar.getInstance();

        for (int i = 0; i<hourlyWeatherlist.size(); i++) {
            HourlyWeather hourlyWeather = hourlyWeatherlist.get(i);
            long dt = hourlyWeather.getDt();
            Date date = new Date(dt * 1000L);
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int day = calendar.get(Calendar.DATE);
            String temDate = formatter.format(date);

            if ((hour >= Constants.START_SOLAR_SYSTEM_TIME) && (hour<= Constants.STOP_SOLAR_SYSTEM_TIME) && (day != currenteDay) ) {
                ArrayList<Weather>  weatherList = hourlyWeather.getWeather();
                if (weatherList!=null && weatherList.size()==1 ) {
                    Weather weather = weatherList.get(0);
                    int weatherID = Integer.valueOf(weather.getId());
                    if (Constants.SOLAR_CLOUD_VALUES_LIST.contains(weatherID)) {
                        //check if forecast = next day
                        if (sdf.format(date).equalsIgnoreCase(dayAfterNextDay)) {
                            //next 48 h
                            int heatingPercent = 100 -  ((weatherID - 800) * 25) ;
                            numberOfHourHeatingPercentNext48h += heatingPercent;

                        } else if (sdf.format(date).equalsIgnoreCase(nextDay)) {
                            //next 24h
                            int heatingPercent = 100 -  ((weatherID - 800) * 25) ;
                            numberOfHourHeatingPercentNext24h += heatingPercent;
                            numberOfHourHeatingPercentNext48h += heatingPercent;
                        }
                    }
                }
                //log.info("Opendata forecast heating time  [date/time]="  + formatter.format(date));
            }

        }

        int numberOfHourEffectiveHeating = numberOfHourHeatingPercentNext24h / 100;
        int numberOfHourEffectiveHeatingNextDay = numberOfHourHeatingPercentNext48h / 100;
        currentMeasuredData.setCurrent24hSunShiningPrognosis(numberOfHourEffectiveHeating);
        currentMeasuredData.setCurrent48hSunShiningPrognosis(numberOfHourEffectiveHeatingNextDay);

        int currentBoilerTemperature = currentMeasuredData.getCurrentBoilerTemperature();
        boolean switchElectronicBoilerOn = false;
        if (currentBoilerTemperature < heatingBoilerTemperature24hThreshold ) {
            if (numberOfHourEffectiveHeating < minSunShineDurance24h) {
                switchElectronicBoilerOn = true;
            }
        }

        if (currentBoilerTemperature < heatingBoilerTemperatur48hThreshold ) {
            if (numberOfHourEffectiveHeatingNextDay < minSunShineDurance48h) {
                switchElectronicBoilerOn = true;
            }
        }

        if (switchElectronicBoilerOn) {
            currentMeasuredData.setSwitchOnBoilerElectronic(switchElectronicBoilerOn);
        }

        SolarSwitch solarSwitch = new SolarSwitch(mailSender);
        solarSwitch.setSwitch(switchURL, switchElectronicBoilerOn, currentBoilerTemperature, numberOfHourEffectiveHeating, numberOfHourEffectiveHeatingNextDay);

        log.info("Solar modus calculated 24 [" + numberOfHourEffectiveHeating + "], 48 [" + numberOfHourEffectiveHeatingNextDay + "]" );
    }

    //reset switch in the morning
    @Scheduled(cron = "0 15 06 * * ?")
    public void resetSolarModus() {
        currentMeasuredData.setSwitchOnBoilerElectronic(false);
        SolarSwitch solarSwitch = new SolarSwitch(mailSender);
        solarSwitch.setSwitch(switchURL,false,currentMeasuredData.getCurrentBoilerTemperature(), 0, 0);
        log.info("Boiler switch reset");
    }

    @Scheduled(cron = "0 */2 * ? * *")
    public void readAllTemperaturSensor() {
        log.info("All temperature seonsor read ");
        temperaturSensor.readAllTemperaturSensor();

        int currentRoofTemperature =  temperaturSensor.getSensorTemperatur(roofTemperaturSensor);
        int currentBoilerTemperature = temperaturSensor.getSensorTemperatur(boilerTemperaturSensor);
        int currentSolarTubeHotTemperature = temperaturSensor.getSensorTemperatur(solarTubeHotTemperaturSensor);
        int currentSolarTubeColdTemperature = temperaturSensor.getSensorTemperatur(solarTubeColdTemperaturSensor);

        //add boiler-temp
        temperatureBuffer.insert(currentBoilerTemperature);
        double avgBoilerTemp = temperatureBuffer.getAverageValue();

        if (currentBoilerTemperature > avgBoilerTemp) {
            currentMeasuredData.setTendencyBoilerTemperature(-1);
        } else {
            currentMeasuredData.setTendencyBoilerTemperature(1);
        }

        if (currentBoilerTemperature == avgBoilerTemp) {
            currentMeasuredData.setTendencyBoilerTemperature(0);
        }

        currentMeasuredData.setCurrentBoilerTemperature(currentBoilerTemperature);
        currentMeasuredData.setCurrentRoofTemperature(currentRoofTemperature);
        currentMeasuredData.setCurrentSolarTubeColdTemperature(currentSolarTubeColdTemperature);
        currentMeasuredData.setCurrentSolarTubeHotTemperature(currentSolarTubeHotTemperature);

        ScheduledTasks.firstReadTemeraturSensor = true;

        log.info("All temperature seonsor read ");
    }
}
