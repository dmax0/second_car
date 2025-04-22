package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 车辆评估记录
 */
@Data
@TableName("car_assessments")
@Schema(description = "车辆评估记录")
public class CarAssessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "创建时间")
    private Date addtime;

    @Schema(description = "评估编号", required = true)
    private String assessmentCode;

    @Schema(description = "车辆ID", required = true)
    private Long carId;

    @Schema(description = "评估日期", required = true)
    private Date assessmentDate;

    @Schema(description = "评估人ID", required = true)
    private Long assessorId;

    @Schema(description = "外观状态")
    private Integer exteriorCondition = 0;

    @Schema(description = "内饰状态")
    private Integer interiorCondition = 0;

    @Schema(description = "驾驶系统状态")
    private Integer drivingSystem = 0;

    @Schema(description = "发动机状态")
    private Integer engine = 0;

    @Schema(description = "变速箱状态")
    private Integer transmission = 0;

    @Schema(description = "底盘状态")
    private Integer chassis = 0;

    @Schema(description = "电气系统状态")
    private Integer electricalSystem = 0;

    @Schema(description = "其他配置状态")
    private Integer otherConfig = 0;

    @Schema(description = "损伤标记数量")
    private Integer damageMarkerCount = 0;

    @Schema(description = "综合评分")
    private BigDecimal totalScore = BigDecimal.ZERO;

    @Schema(description = "评估结果详情")
    private String assessmentResult;

    @Schema(description = "估价值")
    private BigDecimal estimatedValue = BigDecimal.ZERO;

    @Schema(description = "状态")
    private String status = "进行中";

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "评估报告数据")
    private String reportData;

    @Schema(description = "VR会话ID")
    private String vrSessionId;

    @Schema(description = "最后视角位置")
    private String lastViewPosition;

    @Schema(description = "最后视角旋转")
    private String lastViewRotation;
} 