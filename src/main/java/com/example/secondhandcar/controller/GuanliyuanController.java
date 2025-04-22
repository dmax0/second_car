package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.Guanliyuan;
import com.example.secondhandcar.dto.LoginDTO;
import com.example.secondhandcar.service.GuanliyuanService;
import com.example.secondhandcar.utils.FileUploadUtils;
import com.example.secondhandcar.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 管理员Controller
 */
@Tag(name = "管理员管理", description = "管理员相关接口")
@RestController
@RequestMapping("/guanliyuan")
public class GuanliyuanController {

    @Autowired
    private GuanliyuanService guanliyuanService;

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Operation(summary = "管理员登录", description = "管理员登录接口")
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO result = guanliyuanService.login(loginDTO.getUsername(), loginDTO.getPassword());
        return Result.success(result);
    }
    
    @Operation(summary = "分页查询管理员列表", description = "分页查询管理员列表接口")
    @GetMapping("/page")
    public Result<IPage<Guanliyuan>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "用户名") String username,
            @Parameter(description = "角色") String role
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }
        
        Page<Guanliyuan> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Guanliyuan> result = guanliyuanService.pageList(page, username, role);
        return Result.success(result);
    }
    
    @Operation(summary = "根据ID查询管理员", description = "根据ID查询管理员接口")
    @GetMapping("/{id}")
    public Result<Guanliyuan> getById(@PathVariable Long id) {
        Guanliyuan guanliyuan = guanliyuanService.getById(id);
        return Result.success(guanliyuan);
    }
    
    @Operation(summary = "添加管理员", description = "添加管理员接口")
    @PostMapping
    public Result<Guanliyuan> add(@RequestBody Guanliyuan guanliyuan) {
        Guanliyuan result = guanliyuanService.add(guanliyuan);
        return Result.success(result);
    }
    
    @Operation(summary = "修改管理员", description = "修改管理员接口")
    @PutMapping
    public Result<Boolean> update(@RequestBody Guanliyuan guanliyuan) {
        boolean result = guanliyuanService.update(guanliyuan);
        return Result.success(result);
    }
    
    @Operation(summary = "上传头像", description = "上传头像接口")
    @PostMapping("/upload/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.uploadImage(file);
        return Result.success(url);
    }
} 