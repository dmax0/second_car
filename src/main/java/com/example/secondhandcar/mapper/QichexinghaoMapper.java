package com.example.secondhandcar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.secondhandcar.entity.Qichexinghao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 汽车型号信息Mapper
 */
@Mapper
public interface QichexinghaoMapper extends BaseMapper<Qichexinghao> {

    /**
     * 查询所有品牌列表（去重）
     */
    List<String> selectAllPinpai();

    /**
     * 根据品牌查询车系列表（去重）
     */
    List<String> selectChexiByPinpai(@Param("pinpai") String pinpai);
} 