package com.pit.solarserver.controller;

import com.pit.solarserver.configuration.Constants;
import com.pit.solarserver.data.CurrentWeather;
import com.pit.solarserver.data.DTOCurrentData;
import com.pit.solarserver.data.DTOSolarDataDAO;
import com.pit.solarserver.model.CurrentMeasuredData;
import com.pit.solarserver.model.OpenWeatherData;
import com.pit.solarserver.service.SolarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/data")
@RestController
public class SolarController {

    private static Logger logger = LoggerFactory.getLogger(SolarController.class);

    @Qualifier(Constants.OPEN_WEATHER_DATA_BEAN)
    @Autowired
    private OpenWeatherData openWeatherData;

    @Qualifier(Constants.MESSURED_DATA_BEAN)
    @Autowired
    private CurrentMeasuredData currentMessuredData;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy ', ' HH:mm");

    private SolarService solarService;

    @Autowired
    public SolarController(SolarService solarService){
        this.solarService = solarService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getSolarData(@RequestParam("from") @DateTimeFormat(pattern = "dd.MM.yyyy") Date from, @RequestParam("to") @DateTimeFormat(pattern = "dd.MM.yyyy") Date to) {
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        logger.info("Rest-API-Call: getSolarData() from " + formater.format(from) + ", to " +  formater.format(to));

        DTOSolarDataDAO dtoSolarData = solarService.getSolarData(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()));
        if (dtoSolarData == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            //Add current temperature and state
            dtoSolarData.setCurrentMessuredData(currentMessuredData);
            //add current weather infos
            CurrentWeather currentWeather = openWeatherData.getOpenWeatherData().getCurrent();
            dtoSolarData.setCurrentWeatherIcon(currentWeather.getWeather().get(0).getIcon());
            dtoSolarData.setCurrentWeatherDescription(currentWeather.getWeather().get(0).getDescription());
            dtoSolarData.setCurrentCloudsPercent(currentWeather.getClouds());
            dtoSolarData.setCurrentHumidity(currentWeather.getHumidity());
            dtoSolarData.setCurrentTemperature((int) currentWeather.getTemp());
            return ResponseEntity.ok(dtoSolarData);
        }

    }

    @GetMapping(value="/current",  produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCurrentData() {

        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = GregorianCalendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        CurrentWeather currentWeather = openWeatherData.getOpenWeatherData().getCurrent();
        DTOCurrentData currentData = new DTOCurrentData();
        currentData.setCurrentDate(formatter.format(date));
        currentData.setCurrentWeatherDescription(currentWeather.getWeather().get(0).getDescription());
        currentData.setCurrentWeatherIcon(currentWeather.getWeather().get(0).getIcon());

        currentData.setCurrentTemperature((int) currentWeather.getTemp());
        currentData.setCurrentHumidity(currentWeather.getHumidity());
        currentData.setCurrentCloudsPerzent(currentWeather.getClouds());

        int rain = 0;
        if (openWeatherData.getOpenWeatherData().getHourly().get(0).getRain() != null) {
            rain = (int) (100 * openWeatherData.getOpenWeatherData().getHourly().get(0).getRain().get1h());
        }
        currentData.setCurrentRain(rain);
        currentData.setCurrentRoofTemperature(currentMessuredData.getCurrentRoofTemperature());
        currentData.setCurrentTendencyBoilerTemperature(currentMessuredData.getTendencyBoilerTemperature());
        currentData.setCurrentBoilerTemperature(currentMessuredData.getCurrentBoilerTemperature());
        currentData.setCurrentSolarTubeHotTemperature(currentMessuredData.getCurrentSolarTubeHotTemperature());
        currentData.setCurrentSolarTubeColdTemperature(currentMessuredData.getCurrentSolarTubeColdTemperature());
        currentData.setSwitchOnBoilerElectronic(currentMessuredData.isSwitchOnBoilerElectronic());
        currentData.setCurrent24hSunShining(currentMessuredData.getCurrent24hSunShiningPrognosis());
        currentData.setCurrent48hSunShining(currentMessuredData.getCurrent48hSunShiningPrognosis());

        return ResponseEntity.ok(currentData);
    }

}
