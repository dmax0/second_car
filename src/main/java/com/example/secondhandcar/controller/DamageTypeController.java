package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.DamageType;
import com.example.secondhandcar.service.DamageTypeService;
import com.example.secondhandcar.utils.FileUploadUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 损伤类型Controller
 */
@Tag(name = "损伤类型管理", description = "损伤类型相关接口")
@RestController
@RequestMapping("/damage-type")
public class DamageTypeController {

    @Autowired
    private DamageTypeService damageTypeService;
    
    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Operation(summary = "添加损伤类型", description = "添加损伤类型接口")
    @PostMapping
    public Result<DamageType> add(@RequestBody DamageType damageType) {
        DamageType result = damageTypeService.add(damageType);
        return Result.success(result);
    }

    @Operation(summary = "修改损伤类型", description = "修改损伤类型接口")
    @PutMapping
    public Result<Boolean> update(@RequestBody DamageType damageType) {
        boolean result = damageTypeService.update(damageType);
        return Result.success(result);
    }

    @Operation(summary = "删除损伤类型", description = "删除损伤类型接口")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = damageTypeService.delete(id);
        return Result.success(result);
    }

    @Operation(summary = "获取损伤类型详情", description = "获取损伤类型详情接口")
    @GetMapping("/{id}")
    public Result<DamageType> getById(@PathVariable Long id) {
        DamageType damageType = damageTypeService.getById(id);
        return Result.success(damageType);
    }

    @Operation(summary = "分页查询损伤类型", description = "分页查询损伤类型接口")
    @GetMapping("/page")
    public Result<IPage<DamageType>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "类型名称") String typeName
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<DamageType> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<DamageType> result = damageTypeService.page(page, typeName);
        return Result.success(result);
    }

    @Operation(summary = "查询所有损伤类型", description = "查询所有损伤类型接口")
    @GetMapping("/list")
    public Result<List<DamageType>> listAll() {
        List<DamageType> list = damageTypeService.listAll();
        return Result.success(list);
    }
    
    @Operation(summary = "上传损伤类型图标", description = "上传损伤类型图标接口")
    @PostMapping("/upload/icon")
    public Result<String> uploadIcon(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.uploadImage(file);
        return Result.success(url);
    }
} 