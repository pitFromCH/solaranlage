package com.pit.solarserver.repository;

import com.pit.solarserver.model.SolarData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface SolarRepository extends JpaRepository<SolarData, Integer> {

    //get all data by date
    SolarData findSolarDataByDate(Date date);

    //find all data from a date fram a specific type
    SolarData findSolarDataByDateAndAndType(Date date, int type);

    //find all data between a date from a specific type
    List<SolarData> findAllByDateBetweenAndTypeOrderByDateDesc(Date startDate, Date endDate, int type);

}
