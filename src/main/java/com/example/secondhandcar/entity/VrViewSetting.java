package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * VR视图设置
 */
@Data
@TableName("vr_view_settings")
@Schema(description = "VR视图设置")
public class VrViewSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "创建时间")
    private Date addtime;

    @Schema(description = "车辆ID", required = true)
    private Long carId;

    @Schema(description = "3D模型ID", required = true)
    private Long modelId;

    @Schema(description = "识别编码", required = true)
    private String recognitionCode;

    @Schema(description = "默认相机位置")
    private String cameraPosition = "0,5,10";

    @Schema(description = "默认相机目标点")
    private String cameraTarget = "0,0,0";

    @Schema(description = "环境光颜色")
    private String ambientLightColor = "#FFFFFF";

    @Schema(description = "环境光强度")
    private Float ambientLightIntensity = 0.5f;

    @Schema(description = "方向光颜色")
    private String directionalLightColor = "#FFFFFF";

    @Schema(description = "方向光强度")
    private Float directionalLightIntensity = 0.8f;

    @Schema(description = "方向光位置")
    private String directionalLightPosition = "10,10,10";

    @Schema(description = "是否启用阴影")
    private Boolean shadowEnabled = true;

    @Schema(description = "环境预设")
    private String environmentPreset = "showroom";

    @Schema(description = "视图模式")
    private String viewMode = "exterior";

    @Schema(description = "是否自动旋转")
    private Boolean autoRotateEnabled = false;

    @Schema(description = "自动旋转速度")
    private Float autoRotateSpeed = 0.5f;

    @Schema(description = "最小缩放值")
    private Float zoomMin = 2.0f;

    @Schema(description = "最大缩放值")
    private Float zoomMax = 10.0f;

    @Schema(description = "可交互部件JSON")
    private String interactiveParts;

    @Schema(description = "是否启用WebXR")
    private Boolean webxrEnabled = true;

    @Schema(description = "是否启用移动端控制")
    private Boolean mobileControlsEnabled = true;

    @Schema(description = "标记点上限")
    private Integer markerLimit = 30;
} 