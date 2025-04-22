package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.Qichexinghao;
import com.example.secondhandcar.service.QichexinghaoService;
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
 * 汽车型号Controller
 */
@Tag(name = "汽车型号管理", description = "汽车型号相关接口")
@RestController
@RequestMapping("/qichexinghao")
public class QichexinghaoController {

    @Autowired
    private QichexinghaoService qichexinghaoService;

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Operation(summary = "分页查询汽车型号列表", description = "分页查询汽车型号列表接口")
    @GetMapping("/page")
    public Result<IPage<Qichexinghao>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "型号") String qichexinghao,
            @Parameter(description = "品牌") String pinpai,
            @Parameter(description = "车系") String chexi
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<Qichexinghao> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Qichexinghao> result = qichexinghaoService.pageList(page, qichexinghao, pinpai, chexi);
        return Result.success(result);
    }

    @Operation(summary = "根据ID查询汽车型号", description = "根据ID查询汽车型号接口")
    @GetMapping("/{id}")
    public Result<Qichexinghao> getById(@PathVariable Long id) {
        Qichexinghao qichexinghao = qichexinghaoService.getById(id);
        return Result.success(qichexinghao);
    }

    @Operation(summary = "添加汽车型号", description = "添加汽车型号接口")
    @PostMapping
    public Result<Qichexinghao> add(@RequestBody Qichexinghao qichexinghao) {
        Qichexinghao result = qichexinghaoService.add(qichexinghao);
        return Result.success(result);
    }

    @Operation(summary = "修改汽车型号", description = "修改汽车型号接口")
    @PutMapping
    public Result<Boolean> update(@RequestBody Qichexinghao qichexinghao) {
        boolean result = qichexinghaoService.update(qichexinghao);
        return Result.success(result);
    }

    @Operation(summary = "删除汽车型号", description = "删除汽车型号接口")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = qichexinghaoService.delete(id);
        return Result.success(result);
    }

    @Operation(summary = "查询所有品牌", description = "查询所有品牌接口")
    @GetMapping("/pinpai/all")
    public Result<List<String>> getAllPinpai() {
        List<String> result = qichexinghaoService.getAllPinpai();
        return Result.success(result);
    }

    @Operation(summary = "根据品牌查询车系", description = "根据品牌查询车系接口")
    @GetMapping("/chexi/by-pinpai")
    public Result<List<String>> getChexiByPinpai(@Parameter(description = "品牌", required = true) @RequestParam String pinpai) {
        List<String> result = qichexinghaoService.getChexiByPinpai(pinpai);
        return Result.success(result);
    }

    @Operation(summary = "上传型号图片", description = "上传型号图片接口")
    @PostMapping("/upload/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.uploadImage(file);
        return Result.success(url);
    }
} 