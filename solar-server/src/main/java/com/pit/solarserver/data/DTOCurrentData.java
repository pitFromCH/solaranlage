package com.pit.solarserver.data;

public class DTOCurrentData {

    //date
    private String currentDate;

    //current weather
    private String currentWeatherDescription;
    private String currentWeatherIcon;
    private int currentTemperature;
    private int currentHumidity;
    private int currentCloudsPerzent;
    private int currentRain;

    //current measured temperature
    private int currentRoofTemperature;
    private int currentTendencyBoilerTemperature;
    private int currentBoilerTemperature;
    private int currentSolarTubeHotTemperature;
    private int currentSolarTubeColdTemperature;
    private boolean switchOnBoilerElectronic;
    private int current24hSunShining;
    private int current48hSunShining;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
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

    public int getCurrentCloudsPerzent() {
        return currentCloudsPerzent;
    }

    public void setCurrentCloudsPerzent(int currentCloudsPerzent) {
        this.currentCloudsPerzent = currentCloudsPerzent;
    }

    public int getCurrentRain() {
        return currentRain;
    }

    public void setCurrentRain(int currentRain) {
        this.currentRain = currentRain;
    }

    public int getCurrentRoofTemperature() {
        return currentRoofTemperature;
    }

    public void setCurrentRoofTemperature(int currentRoofTemperature) {
        this.currentRoofTemperature = currentRoofTemperature;
    }

    public int getCurrentBoilerTemperature() {
        return currentBoilerTemperature;
    }

    public void setCurrentBoilerTemperature(int currentBoilerTemperature) {
        this.currentBoilerTemperature = currentBoilerTemperature;
    }

    public int getCurrentTendencyBoilerTemperature() {
        return currentTendencyBoilerTemperature;
    }

    public void setCurrentTendencyBoilerTemperature(int currentTendencyBoilerTemperature) {
        this.currentTendencyBoilerTemperature = currentTendencyBoilerTemperature;
    }

    public int getCurrentSolarTubeHotTemperature() {
        return currentSolarTubeHotTemperature;
    }

    public void setCurrentSolarTubeHotTemperature(int currentSolarTubeHotTemperature) {
        this.currentSolarTubeHotTemperature = currentSolarTubeHotTemperature;
    }

    public int getCurrentSolarTubeColdTemperature() {
        return currentSolarTubeColdTemperature;
    }

    public void setCurrentSolarTubeColdTemperature(int currentSolarTubeColdTemperature) {
        this.currentSolarTubeColdTemperature = currentSolarTubeColdTemperature;
    }

    public boolean isSwitchOnBoilerElectronic() {
        return switchOnBoilerElectronic;
    }

    public void setSwitchOnBoilerElectronic(boolean switchOnBoilerElectronic) {
        this.switchOnBoilerElectronic = switchOnBoilerElectronic;
    }

    public int getCurrent24hSunShining() {
        return current24hSunShining;
    }

    public void setCurrent24hSunShining(int current24hSunShining) {
        this.current24hSunShining = current24hSunShining;
    }

    public int getCurrent48hSunShining() {
        return current48hSunShining;
    }

    public void setCurrent48hSunShining(int current48hSunShining) {
        this.current48hSunShining = current48hSunShining;
    }
}
