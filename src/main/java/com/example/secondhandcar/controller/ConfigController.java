package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.Config;
import com.example.secondhandcar.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配置管理Controller
 */
@Tag(name = "配置管理", description = "配置相关接口")
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Operation(summary = "添加配置", description = "添加配置接口")
    @PostMapping
    public Result<Config> add(@RequestBody Config config) {
        Config result = configService.add(config);
        return Result.success(result);
    }

    @Operation(summary = "修改配置", description = "修改配置接口")
    @PutMapping
    public Result<Boolean> update(@RequestBody Config config) {
        boolean result = configService.update(config);
        return Result.success(result);
    }

    @Operation(summary = "删除配置", description = "删除配置接口")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = configService.delete(id);
        return Result.success(result);
    }

    @Operation(summary = "获取配置详情", description = "获取配置详情接口")
    @GetMapping("/{id}")
    public Result<Config> getById(@PathVariable Long id) {
        Config config = configService.getById(id);
        return Result.success(config);
    }

    @Operation(summary = "根据名称获取配置", description = "根据名称获取配置接口")
    @GetMapping("/name/{name}")
    public Result<Config> getByName(@PathVariable String name) {
        Config config = configService.getByName(name);
        return Result.success(config);
    }

    @Operation(summary = "分页查询配置", description = "分页查询配置接口")
    @GetMapping("/page")
    public Result<IPage<Config>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "配置名称") String name
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<Config> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Config> result = configService.page(page, name);
        return Result.success(result);
    }

    @Operation(summary = "查询所有配置", description = "查询所有配置接口")
    @GetMapping("/list")
    public Result<List<Config>> listAll() {
        List<Config> list = configService.listAll();
        return Result.success(list);
    }
} 