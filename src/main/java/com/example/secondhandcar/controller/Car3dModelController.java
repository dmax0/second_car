package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.Car3dModel;
import com.example.secondhandcar.service.Car3dModelService;
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
 * 车辆3D模型Controller
 */
@Tag(name = "车辆3D模型管理", description = "车辆3D模型相关接口")
@RestController
@RequestMapping("/car-3d-model")
public class Car3dModelController {

    @Autowired
    private Car3dModelService car3dModelService;
    
    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Operation(summary = "添加车辆3D模型", description = "添加车辆3D模型接口")
    @PostMapping
    public Result<Car3dModel> add(@RequestBody Car3dModel car3dModel) {
        Car3dModel result = car3dModelService.add(car3dModel);
        return Result.success(result);
    }

    @Operation(summary = "修改车辆3D模型", description = "修改车辆3D模型接口")
    @PutMapping
    public Result<Boolean> update(@RequestBody Car3dModel car3dModel) {
        boolean result = car3dModelService.update(car3dModel);
        return Result.success(result);
    }

    @Operation(summary = "删除车辆3D模型", description = "删除车辆3D模型接口")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = car3dModelService.delete(id);
        return Result.success(result);
    }

    @Operation(summary = "获取车辆3D模型详情", description = "获取车辆3D模型详情接口")
    @GetMapping("/{id}")
    public Result<Car3dModel> getById(@PathVariable Long id) {
        Car3dModel car3dModel = car3dModelService.getById(id);
        return Result.success(car3dModel);
    }

    @Operation(summary = "分页查询车辆3D模型", description = "分页查询车辆3D模型接口")
    @GetMapping("/page")
    public Result<IPage<Car3dModel>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "汽车型号ID") Long qichexinghaoid,
            @Parameter(description = "模型名称") String modelName
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<Car3dModel> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Car3dModel> result = car3dModelService.page(page, qichexinghaoid, modelName);
        return Result.success(result);
    }

    @Operation(summary = "查询指定汽车型号的所有3D模型", description = "查询指定汽车型号的所有3D模型接口")
    @GetMapping("/list/{qichexinghaoid}")
    public Result<List<Car3dModel>> listByQicheXinghaoId(@PathVariable Long qichexinghaoid) {
        List<Car3dModel> list = car3dModelService.listByQicheXinghaoId(qichexinghaoid);
        return Result.success(list);
    }
    
    @Operation(summary = "上传3D模型文件", description = "上传3D模型文件接口")
    @PostMapping("/upload/model")
    public Result<String> uploadModel(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.uploadCarModel(file);
        return Result.success(url);
    }
    
    @Operation(summary = "上传模型缩略图", description = "上传模型缩略图接口")
    @PostMapping("/upload/thumbnail")
    public Result<String> uploadThumbnail(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.uploadImage(file);
        return Result.success(url);
    }
} 