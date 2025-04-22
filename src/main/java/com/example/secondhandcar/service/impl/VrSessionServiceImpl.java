package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.VrSession;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.VrSessionMapper;
import com.example.secondhandcar.service.VrSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * VR会话服务实现
 */
@Service
public class VrSessionServiceImpl extends ServiceImpl<VrSessionMapper, VrSession> implements VrSessionService {

    @Autowired
    private VrSessionMapper vrSessionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VrSession createSession(VrSession vrSession) {
        // 生成会话ID
        vrSession.setSessionId(UUID.randomUUID().toString());
        vrSession.setAddtime(new Date());
        vrSession.setStartTime(new Date());
        
        // 设置默认值
        if (vrSession.getViewDuration() == null) {
            vrSession.setViewDuration(0);
        }
        if (vrSession.getIsAssessment() == null) {
            vrSession.setIsAssessment(false);
        }
        
        vrSessionMapper.insert(vrSession);
        return vrSession;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean endSession(String sessionId, Integer viewDuration, String interactionData) {
        // 查询会话
        LambdaQueryWrapper<VrSession> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VrSession::getSessionId, sessionId);
        VrSession vrSession = vrSessionMapper.selectOne(queryWrapper);
        
        if (vrSession == null) {
            throw new ServiceException("会话不存在");
        }
        
        // 设置结束时间和其他信息
        vrSession.setEndTime(new Date());
        if (viewDuration != null) {
            vrSession.setViewDuration(viewDuration);
        } else {
            // 计算查看时长
            long duration = (new Date().getTime() - vrSession.getStartTime().getTime()) / 1000;
            vrSession.setViewDuration((int) duration);
        }
        
        if (StringUtils.hasText(interactionData)) {
            vrSession.setInteractionData(interactionData);
        }
        
        int result = vrSessionMapper.updateById(vrSession);
        return result > 0;
    }

    @Override
    public VrSession getSession(String sessionId) {
        LambdaQueryWrapper<VrSession> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VrSession::getSessionId, sessionId);
        VrSession vrSession = vrSessionMapper.selectOne(queryWrapper);
        
        if (vrSession == null) {
            throw new ServiceException("会话不存在");
        }
        
        return vrSession;
    }

    @Override
    public IPage<VrSession> listUserSessions(Page<VrSession> page, Long userId) {
        LambdaQueryWrapper<VrSession> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VrSession::getUserId, userId);
        queryWrapper.orderByDesc(VrSession::getStartTime);
        
        return vrSessionMapper.selectPage(page, queryWrapper);
    }
} 