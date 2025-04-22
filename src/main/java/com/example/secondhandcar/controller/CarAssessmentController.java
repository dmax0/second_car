package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.CarAssessment;
import com.example.secondhandcar.entity.DamageMarker;
import com.example.secondhandcar.service.CarAssessmentService;
import com.example.secondhandcar.service.DamageMarkerService;
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
 * 车辆评估Controller
 */
@Tag(name = "车辆评估管理", description = "车辆评估相关接口")
@RestController
@RequestMapping("/assessment")
public class CarAssessmentController {

    @Autowired
    private CarAssessmentService carAssessmentService;

    @Autowired
    private DamageMarkerService damageMarkerService;

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Operation(summary = "分页查询评估记录", description = "分页查询评估记录接口")
    @GetMapping("/page")
    public Result<IPage<CarAssessment>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "车辆ID") Long carId,
            @Parameter(description = "评估人ID") Long assessorId,
            @Parameter(description = "状态") String status
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<CarAssessment> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<CarAssessment> result = carAssessmentService.pageList(page, carId, assessorId, status);
        return Result.success(result);
    }

    @Operation(summary = "根据ID查询评估记录", description = "根据ID查询评估记录接口")
    @GetMapping("/{id}")
    public Result<CarAssessment> getById(@PathVariable Long id) {
        CarAssessment carAssessment = carAssessmentService.getById(id);
        return Result.success(carAssessment);
    }

    @Operation(summary = "创建评估记录", description = "创建评估记录接口")
    @PostMapping
    public Result<CarAssessment> add(@RequestBody CarAssessment carAssessment) {
        CarAssessment result = carAssessmentService.add(carAssessment);
        return Result.success(result);
    }

    @Operation(summary = "更新评估记录", description = "更新评估记录接口")
    @PutMapping
    public Result<Boolean> update(@RequestBody CarAssessment carAssessment) {
        boolean result = carAssessmentService.update(carAssessment);
        return Result.success(result);
    }

    @Operation(summary = "添加损伤标记", description = "添加损伤标记接口")
    @PostMapping("/damage-marker")
    public Result<DamageMarker> addDamageMarker(@RequestBody DamageMarker damageMarker) {
        DamageMarker result = damageMarkerService.add(damageMarker);
        return Result.success(result);
    }

    @Operation(summary = "查询评估记录的损伤标记", description = "查询评估记录的损伤标记接口")
    @GetMapping("/damage-markers/{assessmentId}")
    public Result<List<DamageMarker>> getDamageMarkers(@PathVariable Long assessmentId) {
        List<DamageMarker> damageMarkers = damageMarkerService.listByAssessmentId(assessmentId);
        return Result.success(damageMarkers);
    }

    @Operation(summary = "删除损伤标记", description = "删除损伤标记接口")
    @DeleteMapping("/damage-marker/{id}")
    public Result<Boolean> deleteDamageMarker(@PathVariable Long id) {
        boolean result = damageMarkerService.delete(id);
        return Result.success(result);
    }

    @Operation(summary = "上传损伤图片", description = "上传损伤图片接口")
    @PostMapping("/upload/damage-image")
    public Result<String> uploadDamageImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.uploadImage(file);
        return Result.success(url);
    }
} 