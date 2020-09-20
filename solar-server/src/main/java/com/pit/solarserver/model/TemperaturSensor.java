package com.pit.solarserver.model;

import com.pi4j.io.w1.W1Master;
import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.temperature.TemperatureScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;

public class TemperaturSensor {

    private static Logger logger = LoggerFactory.getLogger(TemperaturSensor.class);

    private W1Master w1Master = new W1Master();
    private HashMap<String, Double> sensorTemperatureList = new HashMap<String, Double>();

    public TemperaturSensor() {
        w1Master = new W1Master();
        logger.info(w1Master.toString());
    }

    public void readAllTemperaturSensor() {
        List<TemperatureSensor> deviceList = w1Master.getDevices(TemperatureSensor.class);

        for (int i = 0; i < deviceList.size(); i++) {
            TemperatureSensor temperatureSensor = deviceList.get(i);
            String name = temperatureSensor.getName();
            Double temperature = temperatureSensor.getTemperature(TemperatureScale.CELSIUS);
            sensorTemperatureList.put(name, temperature);
            logger.info("Temperature sensor " + name + " has temperatur " + temperature);
        }
    }

    public int getSensorTemperatur(String sensorName) {
        int temperatur = 0;
        Double sensorTemperatur = sensorTemperatureList.get(sensorName);
        if (sensorTemperatur != null) {
            temperatur = sensorTemperatur.intValue();
        }
        return temperatur;
    }

    public int getRealTimeSensorTemperature(String sensor) {
        int temperatur = 0;
        List<TemperatureSensor> deviceList = w1Master.getDevices(TemperatureSensor.class);
        for (int i = 0; i < deviceList.size(); i++) {
            TemperatureSensor temperatureSensor = deviceList.get(i);
            String name = temperatureSensor.getName();
            if (name.equalsIgnoreCase(sensor)) {
                Double sensorTemperatur = temperatureSensor.getTemperature(TemperatureScale.CELSIUS);
                if (sensorTemperatur != null) {
                    temperatur = sensorTemperatur.intValue();
                }
            }
        }
        return temperatur;
    }


}
