package com.tan.springcloud2.service;

import com.tan.springcloud2.entity.Rainfall;

import java.util.List;

public interface RainfallService {
    List<Rainfall> getAllRain(String stcd, Integer year, Integer month);
    List<Rainfall> getStcdData(String stcd);

    Integer UpdateModel(Integer id, float rainfall);
}
