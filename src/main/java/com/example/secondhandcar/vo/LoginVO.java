package com.example.secondhandcar.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录响应VO
 */
@Data
@Schema(description = "登录响应结果")
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户类型：user-普通用户，pinggu-评估师，admin-管理员")
    private String userType;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "jwt令牌")
    private String token;
} 