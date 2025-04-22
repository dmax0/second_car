package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Qichexinghao;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.QichexinghaoMapper;
import com.example.secondhandcar.service.QichexinghaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 汽车型号服务实现
 */
@Service
public class QichexinghaoServiceImpl extends ServiceImpl<QichexinghaoMapper, Qichexinghao> implements QichexinghaoService {

    @Autowired
    private QichexinghaoMapper qichexinghaoMapper;

    @Override
    public IPage<Qichexinghao> pageList(Page<Qichexinghao> page, String qichexinghao, String pinpai, String chexi) {
        LambdaQueryWrapper<Qichexinghao> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(qichexinghao)) {
            queryWrapper.like(Qichexinghao::getQichexinghao, qichexinghao);
        }
        if (StringUtils.hasText(pinpai)) {
            queryWrapper.eq(Qichexinghao::getPinpai, pinpai);
        }
        if (StringUtils.hasText(chexi)) {
            queryWrapper.eq(Qichexinghao::getChexi, chexi);
        }
        queryWrapper.orderByDesc(Qichexinghao::getAddtime);
        
        return qichexinghaoMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Qichexinghao add(Qichexinghao qichexinghao) {
        // 检查型号是否重复
        LambdaQueryWrapper<Qichexinghao> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Qichexinghao::getQichexinghao, qichexinghao.getQichexinghao());
        if (qichexinghaoMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("汽车型号已存在");
        }

        qichexinghao.setAddtime(new Date());
        qichexinghaoMapper.insert(qichexinghao);
        return qichexinghao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Qichexinghao qichexinghao) {
        // 检查是否存在
        Qichexinghao existQichexinghao = qichexinghaoMapper.selectById(qichexinghao.getId());
        if (existQichexinghao == null) {
            throw new ServiceException("汽车型号不存在");
        }

        // 检查型号是否重复（排除自身）
        if (!existQichexinghao.getQichexinghao().equals(qichexinghao.getQichexinghao())) {
            LambdaQueryWrapper<Qichexinghao> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Qichexinghao::getQichexinghao, qichexinghao.getQichexinghao());
            if (qichexinghaoMapper.selectCount(queryWrapper) > 0) {
                throw new ServiceException("汽车型号已存在");
            }
        }

        int result = qichexinghaoMapper.updateById(qichexinghao);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        // 检查是否存在
        Qichexinghao existQichexinghao = qichexinghaoMapper.selectById(id);
        if (existQichexinghao == null) {
            throw new ServiceException("汽车型号不存在");
        }

        int result = qichexinghaoMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public List<String> getAllPinpai() {
        return qichexinghaoMapper.selectAllPinpai();
    }

    @Override
    public List<String> getChexiByPinpai(String pinpai) {
        return qichexinghaoMapper.selectChexiByPinpai(pinpai);
    }
} 