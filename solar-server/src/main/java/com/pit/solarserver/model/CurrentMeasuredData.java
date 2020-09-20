package com.pit.solarserver.model;

public class CurrentMeasuredData {

    //all data
    private int currentRoofTemperature = 0;
    private int currentBoilerTemperature = 0;
    private int currentSolarTubeHotTemperature = 0;
    private int currentSolarTubeColdTemperature = 0;
    private boolean switchOnBoilerElectronic = false;
    private int current24hSunShiningPrognosis = 0;
    private int current48hSunShiningPrognosis = 0;
    private int tendencyBoilerTemperature = 0;

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

    public int getCurrent24hSunShiningPrognosis() {
        return current24hSunShiningPrognosis;
    }

    public void setCurrent24hSunShiningPrognosis(int current24hSunShiningPrognosis) {
        this.current24hSunShiningPrognosis = current24hSunShiningPrognosis;
    }

    public int getCurrent48hSunShiningPrognosis() {
        return current48hSunShiningPrognosis;
    }

    public void setCurrent48hSunShiningPrognosis(int current48hSunShiningPrognosis) {
        this.current48hSunShiningPrognosis = current48hSunShiningPrognosis;
    }

    public int getTendencyBoilerTemperature() {
        return tendencyBoilerTemperature;
    }

    public void setTendencyBoilerTemperature(int tendencyBoilerTemperature) {
        this.tendencyBoilerTemperature = tendencyBoilerTemperature;
    }
}
