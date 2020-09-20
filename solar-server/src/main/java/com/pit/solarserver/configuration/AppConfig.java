package com.pit.solarserver.configuration;

import com.pit.solarserver.model.CurrentMeasuredData;
import com.pit.solarserver.model.OpenWeatherData;
import com.pit.solarserver.model.TemperaturSensor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean(Constants.OPEN_WEATHER_DATA_BEAN)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public OpenWeatherData getOpenWeatherData() {
        return new OpenWeatherData();
    }

    @Bean(Constants.MESSURED_DATA_BEAN)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CurrentMeasuredData getMessuredData() {
        return new CurrentMeasuredData();
    }

    @Bean(Constants.TEMPERATUR_DATA_BEAN)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TemperaturSensor getTemperaturSensor() {
        return new TemperaturSensor();
    }

}
