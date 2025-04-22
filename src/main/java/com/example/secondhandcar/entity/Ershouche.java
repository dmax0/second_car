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
 * 二手车信息
 */
@Data
@TableName("ershouche")
@Schema(description = "二手车信息")
public class Ershouche implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "创建时间")
    private Date addtime;

    @Schema(description = "车辆编号", required = true)
    private String cheliangbianhao;

    @Schema(description = "汽车型号ID", required = true)
    private Long qichexinghaoid;

    @Schema(description = "车牌号")
    private String chepai;

    @Schema(description = "品牌", required = true)
    private String pinpai;

    @Schema(description = "车系", required = true)
    private String chexi;

    @Schema(description = "车型", required = true)
    private String chexing;

    @Schema(description = "年份", required = true)
    private Integer nianfen;

    @Schema(description = "里程", required = true)
    private Integer licheng;

    @Schema(description = "排量")
    private String pailiang;

    @Schema(description = "变速箱")
    private String biansuxiang;

    @Schema(description = "燃料类型")
    private String ranliao;

    @Schema(description = "颜色")
    private String yanse;

    @Schema(description = "内饰颜色")
    private String neishi;

    @Schema(description = "上牌日期")
    private Date shangpaidate;

    @Schema(description = "年检到期日")
    private Date nianjiandate;

    @Schema(description = "保险到期日")
    private Date baoxiandate;

    @Schema(description = "售价")
    private BigDecimal shoujia;

    @Schema(description = "封面图片")
    private String tupian;

    @Schema(description = "车辆图片集")
    private String tupianMore;

    @Schema(description = "详细描述")
    private String miaoshu;

    @Schema(description = "发布日期")
    private Date fabudate;

    @Schema(description = "发布人ID")
    private Long faburen;

    @Schema(description = "状态")
    private String zhuangtai = "在售";

    @Schema(description = "3D模型ID")
    private Long modelId;

    @Schema(description = "是否有VR视图")
    private Boolean hasVrView = false;

    @Schema(description = "VR查看次数")
    private Integer vrViewCount = 0;

    @Schema(description = "评估总分")
    private BigDecimal pingguZongfen = BigDecimal.ZERO;

    @Schema(description = "评估状态")
    private String pingguStatus = "未评估";
} 