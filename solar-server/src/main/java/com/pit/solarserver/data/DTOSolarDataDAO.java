package com.pit.solarserver.data;

import com.pit.solarserver.model.CurrentMeasuredData;

import java.util.ArrayList;

public class DTOSolarDataDAO {
    //date
    private String from;
    private String to;

    //current weather
    private String currentWeatherDescription;
    private String currentWeatherIcon;
    private int currentTemperature;
    private int currentHumidity;
    private int currentCloudsPercent;

    //current measured temperature
    private CurrentMeasuredData currentMessuredData;

    private ArrayList<DTOSolarDataRow> boilerTemperature = new ArrayList<DTOSolarDataRow>();
    private ArrayList<DTOSolarDataRow> roofTemperature = new ArrayList<DTOSolarDataRow>();
    private ArrayList<DTOSolarDataRow> solarTubeHotTemperature = new ArrayList<DTOSolarDataRow>();
    private ArrayList<DTOSolarDataRow> solarTubeColdTemperature = new ArrayList<DTOSolarDataRow>();
    private ArrayList<DTOSolarDataRow> outdoorTemperature = new ArrayList<DTOSolarDataRow>();
    private ArrayList<DTOSolarDataRow> rain = new ArrayList<DTOSolarDataRow>();

    public DTOSolarDataDAO(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCurrentWeatherDescription() {
        return currentWeatherDescription;
    }

    public void setCurrentWeatherDescription(String currentWeatherDescription) {
        this.currentWeatherDescription = currentWeatherDescription;
    }

    public String getCurrentWeatherIcon() {
        return currentWeatherIcon;
    }

    public void setCurrentWeatherIcon(String currentWeatherIcon) {
        this.currentWeatherIcon = currentWeatherIcon;
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(int currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public int getCurrentCloudsPercent() {
        return currentCloudsPercent;
    }

    public void setCurrentCloudsPercent(int currentCloudsPercent) {
        this.currentCloudsPercent = currentCloudsPercent;
    }

    public CurrentMeasuredData getCurrentMessuredData() {
        return currentMessuredData;
    }

    public void setCurrentMessuredData(CurrentMeasuredData currentMessuredData) {
        this.currentMessuredData = currentMessuredData;
    }

    public ArrayList<DTOSolarDataRow> getBoilerTemperature() {
        return boilerTemperature;
    }

    public void addBoilerTemperature(DTOSolarDataRow row) {
        boilerTemperature.add(row);
    }

    public ArrayList<DTOSolarDataRow> getSolarTubeHotTemperatureTemperature() {
        return solarTubeHotTemperature;
    }

    public void addSolarTubeHotTemperature(DTOSolarDataRow row) {
        solarTubeHotTemperature.add(row);
    }

    public ArrayList<DTOSolarDataRow> getSolarTubeColdTemperatureTemperature() {
        return solarTubeColdTemperature;
    }

    public void addSolarTubeColdTemperature(DTOSolarDataRow row) {
        solarTubeColdTemperature.add(row);
    }

    public ArrayList<DTOSolarDataRow> getRoofTemperature() {
        return roofTemperature;
    }

    public void addRoofTemperature(DTOSolarDataRow row) {
        roofTemperature.add(row);
    }

    public ArrayList<DTOSolarDataRow> getOutdoorTemperature() {
        return outdoorTemperature;
    }

    public void addOutdoorTemperature(DTOSolarDataRow row) {
        outdoorTemperature.add(row);
    }

    public ArrayList<DTOSolarDataRow> getRain() {
        return rain;
    }

    public void addRain(DTOSolarDataRow row) {
        rain.add(row);
    }

}
