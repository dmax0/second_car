package com.example.secondhandcar.common;

import lombok.Data;
import java.io.Serializable;

/**
 * 通用响应结果
 */
@Data
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     */
    private Boolean success;
    
    /**
     * 响应码
     */
    private Integer code;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 成功结果
     */
    public static <T> Result<T> success() {
        return success(null);
    }
    
    /**
     * 成功结果
     */
    public static <T> Result<T> success(T data) {
        return success(data, "操作成功");
    }
    
    /**
     * 成功结果
     */
    public static <T> Result<T> success(T data, String message) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    
    /**
     * 失败结果
     */
    public static <T> Result<T> failure(String message) {
        return failure(ResultCode.FAILURE, message);
    }
    
    /**
     * 失败结果
     */
    public static <T> Result<T> failure(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 参数错误
     */
    public static <T> Result<T> paramError() {
        return failure(ResultCode.PARAM_ERROR, "参数错误");
    }
    
    /**
     * 参数错误
     */
    public static <T> Result<T> paramError(String message) {
        return failure(ResultCode.PARAM_ERROR, message);
    }
    
    /**
     * 未授权
     */
    public static <T> Result<T> unauthorized() {
        return failure(ResultCode.UNAUTHORIZED, "未授权");
    }
    
    /**
     * 未授权
     */
    public static <T> Result<T> unauthorized(String message) {
        return failure(ResultCode.UNAUTHORIZED, message);
    }
} 