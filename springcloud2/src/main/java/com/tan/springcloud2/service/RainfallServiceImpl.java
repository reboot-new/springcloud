package com.tan.springcloud2.service;

import com.tan.springcloud2.dao.RainfallMapper;
import com.tan.springcloud2.entity.Rainfall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RainfallServiceImpl implements RainfallService {

    @Autowired
    private RainfallMapper rainfallMapper;

    @Override
    public List<Rainfall> getAllRain(String stcd, Integer year, Integer month) {
        return rainfallMapper.selectRainfall(stcd, year, month);
    }

    @Override
    public List<Rainfall> getStcdData(String stcd) {

        return rainfallMapper.getStcdData(stcd);
    }

    @Override
    public Integer UpdateModel(Integer id, float rainfall) {

        return rainfallMapper.UpdateModel(id, rainfall);
    }

}
