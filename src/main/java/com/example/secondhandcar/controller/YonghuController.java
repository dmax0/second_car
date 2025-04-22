package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.Yonghu;
import com.example.secondhandcar.dto.LoginDTO;
import com.example.secondhandcar.service.ShoucangService;
import com.example.secondhandcar.service.YonghuService;
import com.example.secondhandcar.utils.FileUploadUtils;
import com.example.secondhandcar.vo.ErshoucheVO;
import com.example.secondhandcar.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 */
@Tag(name = "用户管理", description = "用户相关接口")
@RestController
public class YonghuController {

    @Autowired
    private YonghuService yonghuService;

    @Autowired
    private FileUploadUtils fileUploadUtils;
    
    @Autowired
    private ShoucangService shoucangService;

    // 在主控制器直接添加获取用户信息的接口
    @Operation(summary = "根据ID查询用户", description = "根据ID查询用户接口")
    @GetMapping("/yonghu/{id}")
    public Result<Yonghu> getById(@PathVariable Long id) {
        Yonghu yonghu = yonghuService.getById(id);
        return Result.success(yonghu);
    }
    
    // 在主控制器直接添加用户收藏相关接口
    @Operation(summary = "获取用户收藏列表", description = "获取用户收藏的车辆列表")
    @GetMapping("/yonghu/favorites/{userId}")
    public Result<List<ErshoucheVO>> getUserFavorites(@PathVariable Long userId) {
        List<ErshoucheVO> list = shoucangService.getUserFavorites(userId);
        return Result.success(list);
    }
    
    @Operation(summary = "分页获取用户收藏列表", description = "分页获取用户收藏的车辆列表")
    @GetMapping("/yonghu/favorites/page/{userId}")
    public Result<IPage<ErshoucheVO>> pageUserFavorites(
            @PathVariable Long userId,
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<ErshoucheVO> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<ErshoucheVO> result = shoucangService.pageUserFavorites(page, userId);
        return Result.success(result);
    }
    
    @Operation(summary = "检查收藏状态", description = "检查用户是否收藏了指定车辆")
    @GetMapping("/yonghu/favorite/{userId}/{carId}")
    public Result<Boolean> isCollected(
            @PathVariable Long userId,
            @PathVariable Long carId
    ) {
        boolean collected = shoucangService.isCollected(userId, carId);
        return Result.success(collected);
    }
    
    @Operation(summary = "添加收藏", description = "用户收藏车辆")
    @PostMapping("/yonghu/favorite/{userId}/{carId}")
    public Result<Boolean> addFavorite(
            @PathVariable Long userId,
            @PathVariable Long carId
    ) {
        boolean result = shoucangService.add(userId, carId);
        return Result.success(result);
    }
    
    @Operation(summary = "取消收藏", description = "取消用户收藏的车辆")
    @DeleteMapping("/yonghu/favorite/{userId}/{carId}")
    public Result<Boolean> cancelFavorite(
            @PathVariable Long userId,
            @PathVariable Long carId
    ) {
        boolean result = shoucangService.cancel(userId, carId);
        return Result.success(result);
    }
    
    @Operation(summary = "收藏操作", description = "收藏或取消收藏车辆")
    @PutMapping("/yonghu/favorite/{userId}/{carId}")
    public Result<Map<String, Object>> toggleFavorite(
            @PathVariable Long userId,
            @PathVariable Long carId
    ) {
        Map<String, Object> result = new HashMap<>();
        boolean isCollected = shoucangService.isCollected(userId, carId);
        boolean operationResult;
        
        if (isCollected) {
            // 已收藏，执行取消
            operationResult = shoucangService.cancel(userId, carId);
            result.put("action", "cancel");
        } else {
            // 未收藏，执行添加
            operationResult = shoucangService.add(userId, carId);
            result.put("action", "add");
        }
        
        result.put("success", operationResult);
        result.put("isCollected", !isCollected);
        
        return Result.success(result);
    }
    
    @Operation(summary = "获取用户统计数据", description = "获取用户的收藏、浏览历史、询价和VR看车等统计数据")
    @GetMapping("/yonghu/stats/{userId}")
    public Result<Map<String, Object>> getUserStats(@PathVariable Long userId) {
        Map<String, Object> stats = yonghuService.getUserStats(userId);
        return Result.success(stats);
    }

    // 原有的yonghu路径映射
    @RequestMapping("/yonghu")
    @Tag(name = "用户管理", description = "用户相关接口")
    public static class YonghuEndpoints {
        @Autowired
        private YonghuService yonghuService;

        @Autowired
        private FileUploadUtils fileUploadUtils;
        
        @Autowired
        private ShoucangService shoucangService;

        @Operation(summary = "用户登录", description = "用户登录接口")
        @PostMapping("/login")
        public Result<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
            LoginVO result = yonghuService.login(loginDTO.getUsername(), loginDTO.getPassword());
            return Result.success(result);
        }

        @Operation(summary = "用户注册", description = "用户注册接口")
        @PostMapping("/register")
        public Result<Yonghu> register(@RequestBody @Valid Yonghu yonghu) {
            Yonghu result = yonghuService.register(yonghu);
            return Result.success(result);
        }

        @Operation(summary = "分页查询用户列表", description = "分页查询用户列表接口")
        @GetMapping("/page")
        public Result<IPage<Yonghu>> page(
                @Parameter(description = "页码") Integer current,
                @Parameter(description = "每页条数") Integer size,
                @Parameter(description = "用户名") String yonghuming,
                @Parameter(description = "用户类型") String yonghuType,
                @Parameter(description = "状态") String zhuangtai
        ) {
            PageRequest pageRequest = new PageRequest();
            if (current != null) {
                pageRequest.setCurrent(current);
            }
            if (size != null) {
                pageRequest.setSize(size);
            }

            Page<Yonghu> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
            IPage<Yonghu> result = yonghuService.listPage(page, yonghuming, yonghuType, zhuangtai);
            return Result.success(result);
        }

        @Operation(summary = "修改用户", description = "修改用户接口")
        @PutMapping
        public Result<Boolean> update(@RequestBody Yonghu yonghu) {
            boolean result = yonghuService.updateById(yonghu);
            return Result.success(result);
        }

        @Operation(summary = "修改用户状态", description = "修改用户状态接口")
        @PutMapping("/status/{id}")
        public Result<Boolean> updateStatus(
                @PathVariable Long id,
                @Parameter(description = "状态", required = true) @RequestParam String zhuangtai
        ) {
            boolean result = yonghuService.updateStatus(id, zhuangtai);
            return Result.success(result);
        }

        @Operation(summary = "重置密码", description = "重置密码接口")
        @PutMapping("/reset-password/{id}")
        public Result<Boolean> resetPassword(
                @PathVariable Long id,
                @Parameter(description = "新密码", required = true) @RequestParam String newPassword
        ) {
            boolean result = yonghuService.resetPassword(id, newPassword);
            return Result.success(result);
        }

        @Operation(summary = "上传头像", description = "上传头像接口")
        @PostMapping("/upload/avatar")
        public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
            String url = fileUploadUtils.uploadImage(file);
            return Result.success(url);
        }
    }

    // 新增的user路径映射
    @RequestMapping("/user")
    @Tag(name = "用户管理", description = "用户相关接口")
    public static class UserEndpoints {
        @Autowired
        private FileUploadUtils fileUploadUtils;
        
        @Operation(summary = "上传头像", description = "上传头像接口")
        @PostMapping("/upload-avatar")
        public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
            String url = fileUploadUtils.uploadImage(file);
            return Result.success(url);
        }
    }
} 