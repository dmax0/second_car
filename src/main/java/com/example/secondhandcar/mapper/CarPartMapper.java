package com.example.secondhandcar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.secondhandcar.entity.CarPart;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车辆部件Mapper接口
 */
@Mapper
public interface CarPartMapper extends BaseMapper<CarPart> {
} 