package com.tan.springcloud2.dao;

import com.tan.springcloud2.entity.Rainfall;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RainfallMapper {
    List<Rainfall> selectRainfall(@Param("stcd") String stcd, @Param("year") Integer year, @Param("month") Integer month);
    List<Rainfall> getStcdData(@Param("stcd") String stcd);
    Integer UpdateModel(@Param("id") Integer id, @Param("rainfall") float rainfall);
}
