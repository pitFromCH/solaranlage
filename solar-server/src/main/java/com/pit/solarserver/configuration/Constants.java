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
    private static final String ROLE_PRAEFIX = "ROLE_";
    public static final String USER = "USER";
    public static final String SUPERUSER = "SUPERUSER";
    public static final String ADMINISTRATOR = "ADMINISTRATOR";
    public static final String USER_AUTHORITY = ROLE_PRAEFIX + USER;
    public static final String SUPERUSER_AUTHORITY = ROLE_PRAEFIX + SUPERUSER;
    public static final String ADMINISTRATOR_AUTHORITY = ROLE_PRAEFIX + ADMINISTRATOR;
    public static String JWT_SECRET = "JWTMy$SuperJWT!Secret";
    public static String JWT_BEARER = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static long EXPIRATION_TIME = 1000 * 60 * 60 * 10;


}
