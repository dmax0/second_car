package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏信息
 */
@Data
@TableName("shoucang")
@Schema(description = "收藏信息")
public class Shoucang implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "创建时间")
    private Date addtime;

    @Schema(description = "用户id", required = true)
    private Long userid;

    @Schema(description = "商品id", required = true)
    private Long refid;

    @Schema(description = "表名", required = true)
    private String tablename;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "图片")
    private String picture;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "推荐类型")
    private String inteltype;

    @Schema(description = "备注")
    private String remark;
} 