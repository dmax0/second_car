package com.example.secondhandcar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.CarAssessment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 车辆评估记录Mapper
 */
@Mapper
public interface CarAssessmentMapper extends BaseMapper<CarAssessment> {

    /**
     * 分页查询评估记录，关联车辆信息和评估人信息
     */
    IPage<CarAssessment> selectAssessmentPage(
            Page<CarAssessment> page,
            @Param("carId") Long carId,
            @Param("assessorId") Long assessorId,
            @Param("status") String status
    );
} 