package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.VrSession;
import com.example.secondhandcar.entity.VrViewSetting;
import com.example.secondhandcar.service.VrSessionService;
import com.example.secondhandcar.service.VrViewSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * VR会话管理Controller
 */
@Tag(name = "VR管理", description = "VR会话和设置相关接口")
@RestController
@RequestMapping("/vr")
public class VrController {

    @Autowired
    private VrSessionService vrSessionService;

    @Autowired
    private VrViewSettingService vrViewSettingService;

    @Operation(summary = "创建VR会话", description = "创建VR会话接口")
    @PostMapping("/session")
    public Result<VrSession> createSession(@RequestBody VrSession vrSession) {
        VrSession result = vrSessionService.createSession(vrSession);
        return Result.success(result);
    }

    @Operation(summary = "结束VR会话", description = "结束VR会话接口")
    @PutMapping("/session/{sessionId}/end")
    public Result<Boolean> endSession(
            @PathVariable String sessionId,
            @Parameter(description = "查看时长(秒)") @RequestParam(required = false) Integer viewDuration,
            @Parameter(description = "交互数据JSON") @RequestParam(required = false) String interactionData
    ) {
        boolean result = vrSessionService.endSession(sessionId, viewDuration, interactionData);
        return Result.success(result);
    }

    @Operation(summary = "获取VR会话信息", description = "获取VR会话信息接口")
    @GetMapping("/session/{sessionId}")
    public Result<VrSession> getSession(@PathVariable String sessionId) {
        VrSession vrSession = vrSessionService.getSession(sessionId);
        return Result.success(vrSession);
    }

    @Operation(summary = "分页查询用户的VR会话列表", description = "分页查询用户的VR会话列表接口")
    @GetMapping("/sessions")
    public Result<IPage<VrSession>> getSessions(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }
        
        Page<VrSession> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<VrSession> result = vrSessionService.listUserSessions(page, userId);
        return Result.success(result);
    }

    @Operation(summary = "获取车辆VR视图设置", description = "获取车辆VR视图设置接口")
    @GetMapping("/settings/{carId}")
    public Result<VrViewSetting> getViewSettings(@PathVariable Long carId) {
        VrViewSetting vrViewSetting = vrViewSettingService.getViewSettings(carId);
        return Result.success(vrViewSetting);
    }

    @Operation(summary = "更新车辆VR视图设置", description = "更新车辆VR视图设置接口")
    @PutMapping("/settings")
    public Result<Boolean> updateViewSettings(@RequestBody VrViewSetting vrViewSetting) {
        boolean result = vrViewSettingService.updateViewSettings(vrViewSetting);
        return Result.success(result);
    }
} 