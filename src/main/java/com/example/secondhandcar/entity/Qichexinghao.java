package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 汽车型号信息
 */
@Data
@TableName("qichexinghao")
@Schema(description = "汽车型号信息")
public class Qichexinghao implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "创建时间")
    private Date addtime;

    @Schema(description = "汽车型号", required = true)
    private String qichexinghao;

    @Schema(description = "品牌")
    private String pinpai;

    @Schema(description = "车系")
    private String chexi;

    @Schema(description = "车型")
    private String chexing;

    @Schema(description = "年份")
    private Integer nianfen;

    @Schema(description = "排量")
    private String pailiang;

    @Schema(description = "变速箱类型")
    private String biansuxiang;

    @Schema(description = "燃料类型")
    private String ranliao;

    @Schema(description = "型号图片")
    private String tupian;

    @Schema(description = "默认3D模型ID")
    private Long moxingId;
} 