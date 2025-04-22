package com.example.secondhandcar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.vo.ErshoucheVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 二手车信息Mapper
 */
@Mapper
public interface ErshoucheMapper extends BaseMapper<Ershouche> {

    /**
     * 分页查询二手车信息，包含汽车型号和其他详细信息
     *
     * @param page 分页参数
     * @param pinpai 品牌，可为空
     * @param chexi 车系，可为空
     * @param nianfenStart 年份开始，可为空
     * @param nianfenEnd 年份结束，可为空
     * @param priceStart 价格开始，可为空
     * @param priceEnd 价格结束，可为空
     * @param zhuangtai 状态，可为空
     * @return 查询结果
     */
    IPage<ErshoucheVO> selectDetailPage(
            Page<ErshoucheVO> page,
            @Param("pinpai") String pinpai,
            @Param("chexi") String chexi,
            @Param("nianfenStart") Integer nianfenStart,
            @Param("nianfenEnd") Integer nianfenEnd,
            @Param("priceStart") Integer priceStart,
            @Param("priceEnd") Integer priceEnd,
            @Param("zhuangtai") String zhuangtai
    );

    /**
     * 根据ID查询二手车详细信息
     *
     * @param id 二手车ID
     * @return 二手车详细信息
     */
    ErshoucheVO selectDetailById(@Param("id") Long id);
}