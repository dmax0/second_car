package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondhandcar.entity.VrViewSetting;

/**
 * VR视图设置服务接口
 */
public interface VrViewSettingService extends IService<VrViewSetting> {
    
    /**
     * 获取车辆VR视图设置
     *
     * @param carId 车辆ID
     * @return VR视图设置
     */
    VrViewSetting getViewSettings(Long carId);
    
    /**
     * 更新车辆VR视图设置
     *
     * @param vrViewSetting VR视图设置
     * @return 更新结果
     */
    boolean updateViewSettings(VrViewSetting vrViewSetting);
} 