package com.example.secondhandcar.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页请求参数
 */
@Data
@Schema(description = "分页请求参数")
public class PageRequest {
    
    @Schema(description = "页码", example = "1")
    private Integer current = 1;
    
    @Schema(description = "每页条数", example = "10")
    private Integer size = 10;
    
    @Schema(description = "排序字段")
    private String orderField;
    
    @Schema(description = "排序方式：asc/desc")
    private String orderType;
} 