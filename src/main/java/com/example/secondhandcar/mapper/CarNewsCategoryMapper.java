package com.example.secondhandcar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.secondhandcar.entity.CarNewsCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 汽车资讯分类Mapper接口
 */
@Mapper
public interface CarNewsCategoryMapper extends BaseMapper<CarNewsCategory> {
} 