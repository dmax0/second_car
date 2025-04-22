package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 损伤标记
 */
@Data
@TableName("damage_markers")
@Schema(description = "损伤标记")
public class DamageMarker implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "创建时间")
    private Date addtime;

    @Schema(description = "评估ID", required = true)
    private Long assessmentId;

    @Schema(description = "X坐标", required = true)
    private Float positionX;

    @Schema(description = "Y坐标", required = true)
    private Float positionY;

    @Schema(description = "Z坐标", required = true)
    private Float positionZ;

    @Schema(description = "法线X分量")
    private Float normalX;

    @Schema(description = "法线Y分量")
    private Float normalY;

    @Schema(description = "法线Z分量")
    private Float normalZ;

    @Schema(description = "损伤类型", required = true)
    private String damageType;

    @Schema(description = "严重程度(1-5)")
    private Integer severityLevel = 3;

    @Schema(description = "损伤描述")
    private String description;

    @Schema(description = "损伤图片路径")
    private String imagePath;

    @Schema(description = "标记颜色")
    private String markerColor = "#FFFF00";

    @Schema(description = "标记大小")
    private Float markerSize = 1.0f;

    @Schema(description = "创建人ID", required = true)
    private Long createdBy;

    @Schema(description = "车辆部位")
    private String modelPart;
} 