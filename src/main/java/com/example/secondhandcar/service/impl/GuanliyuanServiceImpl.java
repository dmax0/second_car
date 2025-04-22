package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Guanliyuan;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.GuanliyuanMapper;
import com.example.secondhandcar.service.GuanliyuanService;
import com.example.secondhandcar.utils.JwtUtils;
import com.example.secondhandcar.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 管理员服务实现
 */
@Service
public class GuanliyuanServiceImpl extends ServiceImpl<GuanliyuanMapper, Guanliyuan> implements GuanliyuanService {

    @Autowired
    private GuanliyuanMapper guanliyuanMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public LoginVO login(String username, String password) {
        // 查询管理员
        Guanliyuan guanliyuan = getByUsername(username);
        if (guanliyuan == null) {
            throw new ServiceException("管理员不存在");
        }

        // 验证密码
        if (!guanliyuan.getPassword().equals(password)) {
            throw new ServiceException("密码错误");
        }

        // 构建登录结果
        return createLoginVO(guanliyuan);
    }

    @Override
    public Guanliyuan getByUsername(String username) {
        LambdaQueryWrapper<Guanliyuan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Guanliyuan::getUsername, username);
        return getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Guanliyuan add(Guanliyuan guanliyuan) {
        // 校验用户名
        Guanliyuan existAdmin = getByUsername(guanliyuan.getUsername());
        if (existAdmin != null) {
            throw new ServiceException("用户名已存在");
        }

        // 设置默认值
        guanliyuan.setAddtime(new Date());
        if (guanliyuan.getRole() == null) {
            guanliyuan.setRole("admin");
        }

        // 保存管理员
        save(guanliyuan);
        return guanliyuan;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Guanliyuan guanliyuan) {
        // 校验管理员是否存在
        Guanliyuan existGuanliyuan = getById(guanliyuan.getId());
        if (existGuanliyuan == null) {
            throw new ServiceException("管理员不存在");
        }

        // 校验用户名（如果更改了）
        if (!existGuanliyuan.getUsername().equals(guanliyuan.getUsername())) {
            Guanliyuan existAdmin = getByUsername(guanliyuan.getUsername());
            if (existAdmin != null) {
                throw new ServiceException("用户名已存在");
            }
        }

        // 更新管理员
        updateById(guanliyuan);
        return true;
    }

    @Override
    public IPage<Guanliyuan> pageList(Page<Guanliyuan> page, String username, String role) {
        LambdaQueryWrapper<Guanliyuan> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(username)) {
            queryWrapper.like(Guanliyuan::getUsername, username);
        }
        if (StringUtils.hasText(role)) {
            queryWrapper.eq(Guanliyuan::getRole, role);
        }
        queryWrapper.orderByDesc(Guanliyuan::getAddtime);
        
        return page(page, queryWrapper);
    }

    /**
     * 构建登录结果
     */
    private LoginVO createLoginVO(Guanliyuan guanliyuan) {
        LoginVO loginVO = new LoginVO();
        loginVO.setId(guanliyuan.getId());
        loginVO.setUsername(guanliyuan.getUsername());
        loginVO.setAvatar(guanliyuan.getImage());
        loginVO.setUserType("admin");

        // 生成Token
        String token = jwtUtils.generateToken(guanliyuan.getUsername(), "admin", guanliyuan.getId());
        loginVO.setToken(token);

        return loginVO;
    }
} 