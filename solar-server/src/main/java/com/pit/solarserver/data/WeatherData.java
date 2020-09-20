package com.pit.solarserver.data;

import java.util.ArrayList;

public class WeatherData {

    private float lat;
    private float lon;
    private String timezone;
    private float timezone_offset;
    private CurrentWeather current;
    private Rain rain;
    private ArrayList<HourlyWeather> hourly = new ArrayList <HourlyWeather> ();
    private ArrayList <DailyWeather> daily = new ArrayList <DailyWeather> ();

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public float getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(float timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }

    public ArrayList<HourlyWeather> getHourly() {
        return hourly;
    }

    public void setHourly(ArrayList<HourlyWeather> hourly) {
        this.hourly = hourly;
    }

    public ArrayList<DailyWeather> getDaily() {
        return daily;
    }

    public void setDaily(ArrayList<DailyWeather> daily) {
        this.daily = daily;
    }
}

