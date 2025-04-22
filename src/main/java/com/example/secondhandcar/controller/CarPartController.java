package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.CarPart;
import com.example.secondhandcar.service.CarPartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆部件Controller
 */
@Tag(name = "车辆部件管理", description = "车辆部件相关接口")
@RestController
@RequestMapping("/car-part")
public class CarPartController {

    @Autowired
    private CarPartService carPartService;

    @Operation(summary = "添加车辆部件", description = "添加车辆部件接口")
    @PostMapping
    public Result<CarPart> add(@RequestBody CarPart carPart) {
        CarPart result = carPartService.add(carPart);
        return Result.success(result);
    }

    @Operation(summary = "修改车辆部件", description = "修改车辆部件接口")
    @PutMapping
    public Result<Boolean> update(@RequestBody CarPart carPart) {
        boolean result = carPartService.update(carPart);
        return Result.success(result);
    }

    @Operation(summary = "删除车辆部件", description = "删除车辆部件接口")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = carPartService.delete(id);
        return Result.success(result);
    }

    @Operation(summary = "获取车辆部件详情", description = "获取车辆部件详情接口")
    @GetMapping("/{id}")
    public Result<CarPart> getById(@PathVariable Long id) {
        CarPart carPart = carPartService.getById(id);
        return Result.success(carPart);
    }

    @Operation(summary = "分页查询车辆部件", description = "分页查询车辆部件接口")
    @GetMapping("/page")
    public Result<IPage<CarPart>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "部件名称") String name,
            @Parameter(description = "是否可标记") Boolean isMarkable
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<CarPart> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<CarPart> result = carPartService.page(page, name, isMarkable);
        return Result.success(result);
    }

    @Operation(summary = "查询顶级部件列表", description = "查询顶级部件列表接口")
    @GetMapping("/top-level")
    public Result<List<CarPart>> listTopLevelParts() {
        List<CarPart> list = carPartService.listTopLevelParts();
        return Result.success(list);
    }

    @Operation(summary = "查询子部件列表", description = "查询子部件列表接口")
    @GetMapping("/children/{parentId}")
    public Result<List<CarPart>> listChildParts(@PathVariable Long parentId) {
        List<CarPart> list = carPartService.listChildParts(parentId);
        return Result.success(list);
    }
} 