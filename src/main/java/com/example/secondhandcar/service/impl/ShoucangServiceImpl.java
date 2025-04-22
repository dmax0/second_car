package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.entity.Shoucang;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.ErshoucheMapper;
import com.example.secondhandcar.mapper.ShoucangMapper;
import com.example.secondhandcar.service.ShoucangService;
import com.example.secondhandcar.vo.ErshoucheVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏服务实现
 */
@Service
public class ShoucangServiceImpl extends ServiceImpl<ShoucangMapper, Shoucang> implements ShoucangService {

    @Autowired
    private ShoucangMapper shoucangMapper;
    
    @Autowired
    private ErshoucheMapper ershoucheMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(Long userId, Long carId) {
        // 检查是否已收藏
        if (isCollected(userId, carId)) {
            throw new ServiceException("已收藏该车辆");
        }

        // 创建收藏
        Shoucang shoucang = new Shoucang();
        shoucang.setUserid(userId);
        shoucang.setRefid(carId);
        shoucang.setTablename("ershouche");
        shoucang.setAddtime(new Date());
        
        int result = shoucangMapper.insert(shoucang);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(Long userId, Long carId) {
        LambdaQueryWrapper<Shoucang> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Shoucang::getUserid, userId);
        queryWrapper.eq(Shoucang::getRefid, carId);
        queryWrapper.eq(Shoucang::getTablename, "ershouche");
        
        int result = shoucangMapper.delete(queryWrapper);
        return result > 0;
    }

    @Override
    public boolean isCollected(Long userId, Long carId) {
        LambdaQueryWrapper<Shoucang> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Shoucang::getUserid, userId);
        queryWrapper.eq(Shoucang::getRefid, carId);
        queryWrapper.eq(Shoucang::getTablename, "ershouche");
        
        return shoucangMapper.selectCount(queryWrapper) > 0;
    }
    
    @Override
    public List<ErshoucheVO> getUserFavorites(Long userId) {
        // 查询用户收藏的二手车ID列表
        LambdaQueryWrapper<Shoucang> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Shoucang::getUserid, userId);
        queryWrapper.eq(Shoucang::getTablename, "ershouche");
        queryWrapper.orderByDesc(Shoucang::getAddtime);
        
        List<Shoucang> shoucangList = shoucangMapper.selectList(queryWrapper);
        if (shoucangList.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 获取车辆ID列表
        List<Long> carIds = shoucangList.stream()
                .map(Shoucang::getRefid)
                .collect(Collectors.toList());
        
        // 查询车辆信息
        List<Ershouche> ershoucheList = new ArrayList<>();
        for (Long carId : carIds) {
            Ershouche ershouche = ershoucheMapper.selectById(carId);
            if (ershouche != null) {
                ershoucheList.add(ershouche);
            }
        }
        
        // 转换为VO对象并设置收藏标记
        return ershoucheList.stream()
                .map(car -> {
                    ErshoucheVO vo = ErshoucheVO.fromEntity(car);
                    vo.setIsCollected(true);  // 已收藏
                    return vo;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public IPage<ErshoucheVO> pageUserFavorites(Page<ErshoucheVO> page, Long userId) {
        // 获取用户所有收藏
        List<ErshoucheVO> allFavorites = getUserFavorites(userId);
        
        // 手动分页
        int total = allFavorites.size();
        int fromIndex = (int)((page.getCurrent() - 1) * page.getSize());
        
        if (fromIndex >= total) {
            return page.setRecords(new ArrayList<>()).setTotal(total);
        }
        
        int toIndex = Math.min(fromIndex + (int)page.getSize(), total);
        List<ErshoucheVO> records = allFavorites.subList(fromIndex, toIndex);
        
        return page.setRecords(records).setTotal(total);
    }
} 