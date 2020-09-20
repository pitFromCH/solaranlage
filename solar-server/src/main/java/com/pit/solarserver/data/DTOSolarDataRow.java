package com.pit.solarserver.data;

import com.pit.solarserver.model.SolarData;
import com.pit.solarserver.service.SolarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class DTOSolarDataRow {

    private static Logger logger = LoggerFactory.getLogger(DTOSolarDataRow.class);

    private String date;
    private int dataType;
    private int [] datarow = new int[24];

    public DTOSolarDataRow(int dataType, String date, SolarData solarData) {
        this.date = date;
        this.dataType = dataType;

        try {
            for (int i=0; i<24; i++) {
                datarow[i] = (int) SolarData.class.getMethod("getT" + i).invoke(solarData);
            }

        } catch (NoSuchMethodException| InvocationTargetException | IllegalAccessException ex) {
            //should never happen
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }

    }

    public String getDate() {
        return date;
    }

    public int getDataType() {
        return dataType;
    }

    public int[] getDatarow() {
        return datarow;
    }
}
