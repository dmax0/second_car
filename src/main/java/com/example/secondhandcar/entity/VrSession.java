package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * VR会话记录
 */
@Data
@TableName("vr_sessions")
@Schema(description = "VR会话记录")
public class VrSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "创建时间")
    private Date addtime;

    @Schema(description = "会话ID", required = true)
    private String sessionId;

    @Schema(description = "用户ID", required = true)
    private Long userId;

    @Schema(description = "车辆ID", required = true)
    private Long carId;

    @Schema(description = "开始时间", required = true)
    private Date startTime;

    @Schema(description = "结束时间")
    private Date endTime;

    @Schema(description = "设备类型")
    private String deviceType;

    @Schema(description = "浏览器信息")
    private String browserInfo;

    @Schema(description = "交互数据JSON")
    private String interactionData;

    @Schema(description = "查看时长(秒)")
    private Integer viewDuration = 0;

    @Schema(description = "是否评估会话")
    private Boolean isAssessment = false;

    @Schema(description = "关联评估ID")
    private Long assessmentId;

    @Schema(description = "用户代理字符串")
    private String userAgent;

    @Schema(description = "屏幕分辨率")
    private String screenResolution;
} 