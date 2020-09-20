package com.pit.solarserver.configuration;

import java.util.ArrayList;
import java.util.Arrays;

public final class Constants {

    public static final int TYPE_BOILER = 1;
    public static final int TYPE_ROOF = 2;
    public static final int TYPE_OUTDOOR = 3;
    public static final int TYPE_RAIN = 4;
    public static final int TYPE_SOLAR_TUBE_HOT = 5;
    public static final int TYPE_SOLAR_TUBE_COLD = 6;
    public static final String OPEN_WEATHER_DATA_BEAN = "openWeatherDataBean";
    public static final String TEMPERATUR_DATA_BEAN = "temperaturDataBean";
    public static final String MESSURED_DATA_BEAN = "messuredDataBean";

    public static final int START_SOLAR_SYSTEM_TIME = 8;
    public static final int STOP_SOLAR_SYSTEM_TIME = 18;

    public static ArrayList<Integer> SOLAR_CLOUD_VALUES_LIST = new ArrayList<Integer>(Arrays.asList(800, 801, 802, 803));

}
