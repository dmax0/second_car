package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondhandcar.entity.Guanliyuan;
import com.example.secondhandcar.vo.LoginVO;

/**
 * 管理员服务接口
 */
public interface GuanliyuanService extends IService<Guanliyuan> {
    
    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    LoginVO login(String username, String password);
    
    /**
     * 根据用户名查询管理员
     *
     * @param username 用户名
     * @return 管理员信息
     */
    Guanliyuan getByUsername(String username);
    
    /**
     * 添加管理员
     *
     * @param guanliyuan 管理员信息
     * @return 添加结果
     */
    Guanliyuan add(Guanliyuan guanliyuan);
    
    /**
     * 修改管理员
     *
     * @param guanliyuan 管理员信息
     * @return 修改结果
     */
    boolean update(Guanliyuan guanliyuan);
    
    /**
     * 分页查询管理员列表
     *
     * @param page 分页参数
     * @param username 用户名
     * @param role 角色
     * @return 分页结果
     */
    IPage<Guanliyuan> pageList(Page<Guanliyuan> page, String username, String role);
} 