package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 */
@Data
@TableName("yonghu")
@Schema(description = "用户信息")
public class Yonghu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "创建时间")
    private Date addtime;

    @Schema(description = "用户名", required = true)
    private String yonghuming;

    @Schema(description = "姓名")
    private String xingming;

    @Schema(description = "密码", required = true)
    private String mima;

    @Schema(description = "性别")
    private String xingbie;

    @Schema(description = "手机号")
    private String shoujihao;

    @Schema(description = "头像")
    private String touxiang;

    @Schema(description = "用户类型")
    private String yonghuType = "user";

    @Schema(description = "状态")
    private String zhuangtai = "active";
} 