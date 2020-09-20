package com.pit.solarserver.service;

import com.pit.solarserver.configuration.Constants;
import com.pit.solarserver.data.DTOSolarDataDAO;
import com.pit.solarserver.data.DTOSolarDataRow;
import com.pit.solarserver.model.SolarData;
import com.pit.solarserver.repository.SolarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class SolarService {
    private static Logger logger = LoggerFactory.getLogger(SolarService.class);

    @Autowired
    private SolarRepository solarRepository;

    public DTOSolarDataDAO getSolarData(Date from, Date to) {

        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        DTOSolarDataDAO dtoSolarData = new DTOSolarDataDAO(formater.format(from), formater.format(to));

        //get all outdoor temp rows
        List<SolarData> solarDataList = solarRepository.findAllByDateBetweenAndTypeOrderByDateDesc(from, to, Constants.TYPE_OUTDOOR);
        for (int i = 0; i<solarDataList.size(); i++) {
            SolarData solarData = solarDataList.get(i);
            DTOSolarDataRow dtoSolarDataRow = new DTOSolarDataRow(Constants.TYPE_OUTDOOR, formater.format(solarData.getDate()), solarData);
            dtoSolarData.addOutdoorTemperature(dtoSolarDataRow);
        }

        //get all rain rows
        solarDataList = solarRepository.findAllByDateBetweenAndTypeOrderByDateDesc(from, to, Constants.TYPE_RAIN);
        for (int i = 0; i<solarDataList.size(); i++) {
            SolarData solarData = solarDataList.get(i);
            DTOSolarDataRow dtoSolarDataRow = new DTOSolarDataRow(Constants.TYPE_RAIN, formater.format(solarData.getDate()), solarData);
            dtoSolarData.addRain(dtoSolarDataRow);
        }

        //get all roof temp rows
        solarDataList = solarRepository.findAllByDateBetweenAndTypeOrderByDateDesc(from, to, Constants.TYPE_ROOF);
        for (int i = 0; i<solarDataList.size(); i++) {
            SolarData solarData = solarDataList.get(i);
            DTOSolarDataRow dtoSolarDataRow = new DTOSolarDataRow(Constants.TYPE_ROOF, formater.format(solarData.getDate()), solarData);
            dtoSolarData.addRoofTemperature(dtoSolarDataRow);
        }

        //get all boiler temp rows
        solarDataList = solarRepository.findAllByDateBetweenAndTypeOrderByDateDesc(from, to, Constants.TYPE_BOILER);
        for (int i = 0; i<solarDataList.size(); i++) {
            SolarData solarData = solarDataList.get(i);
            DTOSolarDataRow dtoSolarDataRow = new DTOSolarDataRow(Constants.TYPE_BOILER, formater.format(solarData.getDate()), solarData);
            dtoSolarData.addBoilerTemperature(dtoSolarDataRow);
        }

        //get all solarTubeHot temp rows
        solarDataList = solarRepository.findAllByDateBetweenAndTypeOrderByDateDesc(from, to, Constants.TYPE_SOLAR_TUBE_HOT);
        for (int i = 0; i<solarDataList.size(); i++) {
            SolarData solarData = solarDataList.get(i);
            DTOSolarDataRow dtoSolarDataRow = new DTOSolarDataRow(Constants.TYPE_SOLAR_TUBE_HOT, formater.format(solarData.getDate()), solarData);
            dtoSolarData.addSolarTubeHotTemperature(dtoSolarDataRow);
        }

        //get all solarTubeHot temp rows
        solarDataList = solarRepository.findAllByDateBetweenAndTypeOrderByDateDesc(from, to, Constants.TYPE_SOLAR_TUBE_COLD);
        for (int i = 0; i<solarDataList.size(); i++) {
            SolarData solarData = solarDataList.get(i);
            DTOSolarDataRow dtoSolarDataRow = new DTOSolarDataRow(Constants.TYPE_SOLAR_TUBE_COLD, formater.format(solarData.getDate()), solarData);
            dtoSolarData.addSolarTubeColdTemperature(dtoSolarDataRow);
        }

        return dtoSolarData;
    }


}
