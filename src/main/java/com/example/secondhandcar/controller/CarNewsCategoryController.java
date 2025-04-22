package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.CarNewsCategory;
import com.example.secondhandcar.service.CarNewsCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 汽车资讯分类Controller
 */
@Tag(name = "汽车资讯分类管理", description = "汽车资讯分类相关接口")
@RestController
@RequestMapping("/car-news-category")
public class CarNewsCategoryController {

    @Autowired
    private CarNewsCategoryService carNewsCategoryService;

    @Operation(summary = "添加汽车资讯分类", description = "添加汽车资讯分类接口")
    @PostMapping
    public Result<CarNewsCategory> add(@RequestBody CarNewsCategory carNewsCategory) {
        CarNewsCategory result = carNewsCategoryService.add(carNewsCategory);
        return Result.success(result);
    }

    @Operation(summary = "修改汽车资讯分类", description = "修改汽车资讯分类接口")
    @PutMapping
    public Result<Boolean> update(@RequestBody CarNewsCategory carNewsCategory) {
        boolean result = carNewsCategoryService.update(carNewsCategory);
        return Result.success(result);
    }

    @Operation(summary = "删除汽车资讯分类", description = "删除汽车资讯分类接口")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = carNewsCategoryService.delete(id);
        return Result.success(result);
    }

    @Operation(summary = "获取汽车资讯分类详情", description = "获取汽车资讯分类详情接口")
    @GetMapping("/{id}")
    public Result<CarNewsCategory> getById(@PathVariable Long id) {
        CarNewsCategory carNewsCategory = carNewsCategoryService.getById(id);
        return Result.success(carNewsCategory);
    }

    @Operation(summary = "分页查询汽车资讯分类", description = "分页查询汽车资讯分类接口")
    @GetMapping("/page")
    public Result<IPage<CarNewsCategory>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "分类名称") String typename
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<CarNewsCategory> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<CarNewsCategory> result = carNewsCategoryService.page(page, typename);
        return Result.success(result);
    }

    @Operation(summary = "查询所有汽车资讯分类", description = "查询所有汽车资讯分类接口")
    @GetMapping("/list")
    public Result<List<CarNewsCategory>> listAll() {
        List<CarNewsCategory> list = carNewsCategoryService.listAll();
        return Result.success(list);
    }
} 