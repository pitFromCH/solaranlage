package com.pit.solarserver.model;

import com.pit.solarserver.data.WeatherData;
import com.pit.solarserver.weatherdata.ScheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;


public class OpenWeatherData {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Value("${solarserver.openweathermap.url}")
    private String url;

    private long currentDate;

    private WeatherData weatherData = null;

    @Retryable(maxAttempts=3,value={Exception.class, URISyntaxException.class}, backoff = @Backoff(delay = 5000))
    private void updateWeatherData() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(url);
        ResponseEntity<WeatherData> result = restTemplate.getForEntity(uri, WeatherData.class);
        weatherData = result.getBody();
        currentDate = weatherData.getCurrent().getDt();
    }

    public WeatherData getOpenWeatherData() {
        if (weatherData == null) {
            try {
                updateWeatherData();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return weatherData;
    }

    public void updateWeatherDataModell() {
        try {
            updateWeatherData();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

}
