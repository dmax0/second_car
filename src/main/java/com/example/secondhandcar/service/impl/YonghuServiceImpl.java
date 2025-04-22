package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.entity.Shoucang;
import com.example.secondhandcar.entity.VrSession;
import com.example.secondhandcar.entity.Yonghu;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.ErshoucheMapper;
import com.example.secondhandcar.mapper.ShoucangMapper;
import com.example.secondhandcar.mapper.VrSessionMapper;
import com.example.secondhandcar.mapper.YonghuMapper;
import com.example.secondhandcar.service.YonghuService;
import com.example.secondhandcar.utils.JwtUtils;
import com.example.secondhandcar.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现
 */
@Service
public class YonghuServiceImpl extends ServiceImpl<YonghuMapper, Yonghu> implements YonghuService {

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private ShoucangMapper shoucangMapper;
    
    @Autowired
    private VrSessionMapper vrSessionMapper;
    
    @Autowired
    private ErshoucheMapper ershoucheMapper;

    @Override
    public LoginVO login(String username, String password) {
        // 查询用户
        Yonghu yonghu = getByUsername(username);
        if (yonghu == null) {
            throw new ServiceException("用户不存在");
        }

        // 验证密码
        if (!yonghu.getMima().equals(password)) {
            throw new ServiceException("密码错误");
        }

        // 检查用户状态
        if (!"active".equals(yonghu.getZhuangtai())) {
            throw new ServiceException("账号已被禁用");
        }

        // 构建登录结果
        return createLoginVO(yonghu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Yonghu register(Yonghu yonghu) {
        // 校验用户名
        Yonghu existUser = getByUsername(yonghu.getYonghuming());
        if (existUser != null) {
            throw new ServiceException("用户名已存在");
        }

        // 校验手机号（如果有）
        if (StringUtils.hasText(yonghu.getShoujihao())) {
            existUser = getByPhone(yonghu.getShoujihao());
            if (existUser != null) {
                throw new ServiceException("手机号已被注册");
            }
        }

        // 设置默认值
        yonghu.setAddtime(new Date());
        if (yonghu.getYonghuType() == null) {
            yonghu.setYonghuType("user");
        }
        if (yonghu.getZhuangtai() == null) {
            yonghu.setZhuangtai("active");
        }

        // 保存用户
        save(yonghu);
        return yonghu;
    }

    @Override
    public IPage<Yonghu> listPage(Page<Yonghu> page, String yonghuming, String yonghuType, String zhuangtai) {
        LambdaQueryWrapper<Yonghu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(yonghuming)) {
            queryWrapper.like(Yonghu::getYonghuming, yonghuming);
        }
        if (StringUtils.hasText(yonghuType)) {
            queryWrapper.eq(Yonghu::getYonghuType, yonghuType);
        }
        if (StringUtils.hasText(zhuangtai)) {
            queryWrapper.eq(Yonghu::getZhuangtai, zhuangtai);
        }
        queryWrapper.orderByDesc(Yonghu::getAddtime);
        return page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, String zhuangtai) {
        Yonghu yonghu = getById(id);
        if (yonghu == null) {
            throw new ServiceException("用户不存在");
        }
        
        yonghu.setZhuangtai(zhuangtai);
        return updateById(yonghu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(Long id, String newPassword) {
        Yonghu yonghu = getById(id);
        if (yonghu == null) {
            throw new ServiceException("用户不存在");
        }
        
        yonghu.setMima(newPassword);
        return updateById(yonghu);
    }

    @Override
    public Yonghu getByUsername(String yonghuming) {
        LambdaQueryWrapper<Yonghu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Yonghu::getYonghuming, yonghuming);
        return getOne(queryWrapper);
    }

    @Override
    public Yonghu getByPhone(String shoujihao) {
        LambdaQueryWrapper<Yonghu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Yonghu::getShoujihao, shoujihao);
        return getOne(queryWrapper);
    }
    
    @Override
    public Map<String, Object> getUserStats(Long userId) {
        Map<String, Object> stats = new HashMap<>();
        
        // 统计收藏数量
        LambdaQueryWrapper<Shoucang> shoucangQuery = new LambdaQueryWrapper<>();
        shoucangQuery.eq(Shoucang::getUserid, userId);
        Long favoriteCount = shoucangMapper.selectCount(shoucangQuery);
        stats.put("favoriteCount", favoriteCount);
        
        // 统计浏览历史 - 基于VR会话记录统计不同车辆的浏览次数
        LambdaQueryWrapper<VrSession> vrSessionQuery = new LambdaQueryWrapper<>();
        vrSessionQuery.eq(VrSession::getUserId, userId);
        vrSessionQuery.select(VrSession::getCarId);
        vrSessionQuery.groupBy(VrSession::getCarId);
        List<VrSession> distinctCarSessions = vrSessionMapper.selectList(vrSessionQuery);
        stats.put("viewHistoryCount", distinctCarSessions.size());
        
        // 统计VR看车次数
        LambdaQueryWrapper<VrSession> vrViewQuery = new LambdaQueryWrapper<>();
        vrViewQuery.eq(VrSession::getUserId, userId);
        Long vrViewCount = vrSessionMapper.selectCount(vrViewQuery);
        stats.put("vrViewCount", vrViewCount);
        
        // 计算VR累计观看时长(秒) - 通过查询所有VR会话的时长并求和
        Integer totalViewDuration = calculateTotalVrViewDuration(userId);
        stats.put("vrViewDuration", totalViewDuration);
        
        // 计算收藏车辆的总价值
        Double totalFavoriteValue = calculateTotalFavoriteValue(userId);
        stats.put("totalFavoriteValue", totalFavoriteValue);
        
        return stats;
    }
    
    /**
     * 计算用户VR累计观看时长
     */
    private Integer calculateTotalVrViewDuration(Long userId) {
        LambdaQueryWrapper<VrSession> query = new LambdaQueryWrapper<>();
        query.eq(VrSession::getUserId, userId);
        query.select(VrSession::getViewDuration);
        
        List<VrSession> sessions = vrSessionMapper.selectList(query);
        return sessions.stream()
                .map(VrSession::getViewDuration)
                .filter(duration -> duration != null)
                .mapToInt(Integer::intValue)
                .sum();
    }
    
    /**
     * 计算用户收藏车辆的总价值
     */
    private Double calculateTotalFavoriteValue(Long userId) {
        // 查询用户收藏的所有二手车ID
        LambdaQueryWrapper<Shoucang> shoucangQuery = new LambdaQueryWrapper<>();
        shoucangQuery.eq(Shoucang::getUserid, userId);
        shoucangQuery.eq(Shoucang::getTablename, "ershouche");
        shoucangQuery.select(Shoucang::getRefid);
        
        List<Shoucang> favorites = shoucangMapper.selectList(shoucangQuery);
        if (favorites.isEmpty()) {
            return 0.0;
        }
        
        // 获取车辆ID列表
        List<Long> carIds = favorites.stream()
                .map(Shoucang::getRefid)
                .toList();
        
        // 查询这些车辆的价格并求和
        double totalValue = 0.0;
        for (Long carId : carIds) {
            LambdaQueryWrapper<Ershouche> carQuery = new LambdaQueryWrapper<>();
            carQuery.eq(Ershouche::getId, carId);
            carQuery.select(Ershouche::getShoujia);
            
            Ershouche car = ershoucheMapper.selectOne(carQuery);
            if (car != null && car.getShoujia() != null) {
                totalValue += car.getShoujia().doubleValue();
            }
        }
        
        return totalValue;
    }

    /**
     * 创建登录响应对象
     */
    private LoginVO createLoginVO(Yonghu yonghu) {
        LoginVO loginVO = new LoginVO();
        loginVO.setId(yonghu.getId());
        loginVO.setUsername(yonghu.getYonghuming());
        loginVO.setUserType(yonghu.getYonghuType());
        loginVO.setAvatar(yonghu.getTouxiang());
        
        // 生成JWT令牌
        String token = jwtUtils.generateToken(yonghu.getYonghuming(), yonghu.getYonghuType(), yonghu.getId());
        loginVO.setToken(token);
        
        return loginVO;
    }
} 