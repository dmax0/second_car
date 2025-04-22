package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.service.ErshoucheService;
import com.example.secondhandcar.utils.FileUploadUtils;
import com.example.secondhandcar.vo.ErshoucheVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 二手车Controller
 */
@Tag(name = "二手车管理", description = "二手车相关接口")
@RestController
@RequestMapping("/ershouche")
public class ErshoucheController {

    @Autowired
    private ErshoucheService ershoucheService;

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Operation(summary = "分页查询二手车列表", description = "分页查询二手车列表接口")
    @GetMapping("/page")
    public Result<IPage<ErshoucheVO>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "品牌") String pinpai,
            @Parameter(description = "车系") String chexi,
            @Parameter(description = "年份开始") Integer nianfenStart,
            @Parameter(description = "年份结束") Integer nianfenEnd,
            @Parameter(description = "价格开始") Integer priceStart,
            @Parameter(description = "价格结束") Integer priceEnd,
            @Parameter(description = "状态") String zhuangtai
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<ErshoucheVO> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<ErshoucheVO> result = ershoucheService.pageList(page, pinpai, chexi, nianfenStart, nianfenEnd, priceStart, priceEnd, zhuangtai);
        return Result.success(result);
    }

    @Operation(summary = "根据ID查询二手车详情", description = "根据ID查询二手车详情接口")
    @GetMapping("/{id}")
    public Result<ErshoucheVO> getById(
            @PathVariable Long id,
            @Parameter(description = "当前用户ID") @RequestParam(required = false) Long userId
    ) {
        ErshoucheVO vo = ershoucheService.getDetailById(id, userId);
        return Result.success(vo);
    }

    @Operation(summary = "添加二手车", description = "添加二手车接口")
    @PostMapping
    public Result<Ershouche> add(@RequestBody Ershouche ershouche) {
        Ershouche result = ershoucheService.add(ershouche);
        return Result.success(result);
    }

    @Operation(summary = "修改二手车", description = "修改二手车接口")
    @PutMapping
    public Result<Boolean> update(@RequestBody Ershouche ershouche) {
        boolean result = ershoucheService.update(ershouche);
        return Result.success(result);
    }

    @Operation(summary = "修改二手车状态", description = "修改二手车状态接口")
    @PutMapping("/status/{id}")
    public Result<Boolean> updateStatus(
            @PathVariable Long id,
            @Parameter(description = "状态", required = true) @RequestParam String zhuangtai
    ) {
        boolean result = ershoucheService.updateStatus(id, zhuangtai);
        return Result.success(result);
    }

    @Operation(summary = "修改评估信息", description = "修改评估信息接口")
    @PutMapping("/pinggu/{id}")
    public Result<Boolean> updatePinggu(
            @PathVariable Long id,
            @Parameter(description = "评估状态", required = true) @RequestParam String pingguStatus,
            @Parameter(description = "评估总分", required = true) @RequestParam BigDecimal pingguZongfen
    ) {
        boolean result = ershoucheService.updatePinggu(id, pingguStatus, pingguZongfen);
        return Result.success(result);
    }

    @Operation(summary = "收藏二手车", description = "收藏二手车接口")
    @PostMapping("/favorite")
    public Result<Boolean> favorite(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId,
            @Parameter(description = "二手车ID", required = true) @RequestParam Long carId
    ) {
        boolean result = ershoucheService.favorite(userId, carId);
        return Result.success(result);
    }

    @Operation(summary = "取消收藏", description = "取消收藏接口")
    @DeleteMapping("/favorite")
    public Result<Boolean> cancelFavorite(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId,
            @Parameter(description = "二手车ID", required = true) @RequestParam Long carId
    ) {
        boolean result = ershoucheService.cancelFavorite(userId, carId);
        return Result.success(result);
    }

    @Operation(summary = "查询收藏列表", description = "查询收藏列表接口")
    @GetMapping("/favorites")
    public Result<IPage<ErshoucheVO>> favorites(
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

        Page<ErshoucheVO> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<ErshoucheVO> result = ershoucheService.pageFavorites(page, userId);
        return Result.success(result);
    }

    @Operation(summary = "上传车辆图片", description = "上传车辆图片接口")
    @PostMapping("/upload/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.uploadImage(file);
        return Result.success(url);
    }

    @Operation(summary = "上传3D模型", description = "上传3D模型接口")
    @PostMapping("/upload/model")
    public Result<String> uploadModel(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.uploadCarModel(file);
        return Result.success(url);
    }
} 