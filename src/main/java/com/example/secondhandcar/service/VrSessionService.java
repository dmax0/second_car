package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondhandcar.entity.VrSession;

/**
 * VR会话服务接口
 */
public interface VrSessionService extends IService<VrSession> {
    
    /**
     * 创建VR会话
     *
     * @param vrSession VR会话信息
     * @return 创建结果
     */
    VrSession createSession(VrSession vrSession);
    
    /**
     * 结束VR会话
     *
     * @param sessionId 会话ID
     * @param viewDuration 查看时长
     * @param interactionData 交互数据
     * @return 结果
     */
    boolean endSession(String sessionId, Integer viewDuration, String interactionData);
    
    /**
     * 获取VR会话信息
     *
     * @param sessionId 会话ID
     * @return VR会话信息
     */
    VrSession getSession(String sessionId);
    
    /**
     * 分页查询用户VR会话列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<VrSession> listUserSessions(Page<VrSession> page, Long userId);
} 