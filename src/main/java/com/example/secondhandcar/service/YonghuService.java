package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondhandcar.entity.Yonghu;
import com.example.secondhandcar.vo.LoginVO;

import java.util.Map;

/**
 * 用户服务接口
 */
public interface YonghuService extends IService<Yonghu> {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    LoginVO login(String username, String password);

    /**
     * 用户注册
     *
     * @param yonghu 用户信息
     * @return 注册结果
     */
    Yonghu register(Yonghu yonghu);

    /**
     * 分页查询用户列表
     *
     * @param page 分页参数
     * @param yonghuming 用户名（模糊查询）
     * @param yonghuType 用户类型
     * @param zhuangtai 状态
     * @return 查询结果
     */
    IPage<Yonghu> listPage(Page<Yonghu> page, String yonghuming, String yonghuType, String zhuangtai);

    /**
     * 修改用户状态
     *
     * @param id 用户ID
     * @param zhuangtai 状态
     * @return 修改结果
     */
    boolean updateStatus(Long id, String zhuangtai);

    /**
     * 重置密码
     *
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 修改结果
     */
    boolean resetPassword(Long id, String newPassword);

    /**
     * 根据用户名查询用户
     *
     * @param yonghuming 用户名
     * @return 用户信息
     */
    Yonghu getByUsername(String yonghuming);

    /**
     * 根据手机号查询用户
     *
     * @param shoujihao 手机号
     * @return 用户信息
     */
    Yonghu getByPhone(String shoujihao);


    /**
     * 获取用户统计数据
     *
     * @param userId 用户ID
     * @return 统计数据
     */
    Map<String, Object> getUserStats(Long userId);
} 