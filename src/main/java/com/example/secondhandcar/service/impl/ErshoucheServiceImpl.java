package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.entity.Qichexinghao;
import com.example.secondhandcar.entity.Shoucang;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.ErshoucheMapper;
import com.example.secondhandcar.mapper.QichexinghaoMapper;
import com.example.secondhandcar.mapper.ShoucangMapper;
import com.example.secondhandcar.service.ErshoucheService;
import com.example.secondhandcar.vo.ErshoucheVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 二手车信息服务实现
 */
@Service
public class ErshoucheServiceImpl extends ServiceImpl<ErshoucheMapper, Ershouche> implements ErshoucheService {

    @Autowired
    private ErshoucheMapper ershoucheMapper;

    @Autowired
    private QichexinghaoMapper qichexinghaoMapper;

    @Autowired
    private ShoucangMapper shoucangMapper;

    @Override
    public IPage<ErshoucheVO> pageList(
            Page<ErshoucheVO> page,
            String pinpai,
            String chexi,
            Integer nianfenStart,
            Integer nianfenEnd,
            Integer priceStart,
            Integer priceEnd,
            String zhuangtai
    ) {
        return ershoucheMapper.selectDetailPage(page, pinpai, chexi, nianfenStart, nianfenEnd, priceStart, priceEnd, zhuangtai);
    }

    @Override
    public ErshoucheVO getDetailById(Long id, Long userId) {
        ErshoucheVO vo = ershoucheMapper.selectDetailById(id);
        if (vo == null) {
            throw new ServiceException("二手车信息不存在");
        }

        // 查询是否收藏
        if (userId != null) {
            LambdaQueryWrapper<Shoucang> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Shoucang::getUserid, userId)
                    .eq(Shoucang::getRefid, id)
                    .eq(Shoucang::getTablename, "ershouche");
            long count = shoucangMapper.selectCount(queryWrapper);
            vo.setIsCollected(count > 0);
        }

        // 将图片字符串转换为列表
        if (StrUtil.isNotBlank(vo.getTupianMore())) {
            List<String> imageList = JSONUtil.toList(vo.getTupianMore(), String.class);
            vo.setTupianList(imageList);
        }

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Ershouche add(Ershouche ershouche) {
        // 验证汽车型号是否存在
        Qichexinghao qichexinghao = qichexinghaoMapper.selectById(ershouche.getQichexinghaoid());
        if (qichexinghao == null) {
            qichexinghao = new Qichexinghao();
        }

        // 生成车辆编号
        if (StrUtil.isBlank(ershouche.getCheliangbianhao())) {
            ershouche.setCheliangbianhao("CAR" + IdUtil.fastSimpleUUID().substring(0, 8).toUpperCase());
        } else {
            // 检查车辆编号是否重复
            LambdaQueryWrapper<Ershouche> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Ershouche::getCheliangbianhao, ershouche.getCheliangbianhao());
            if (count(queryWrapper) > 0) {
                throw new ServiceException("车辆编号已存在");
            }
        }

        // 设置默认值
        ershouche.setAddtime(new Date());
        ershouche.setFabudate(new Date());
        if (ershouche.getZhuangtai() == null) {
            ershouche.setZhuangtai("在售");
        }
        if (ershouche.getPingguStatus() == null) {
            ershouche.setPingguStatus("未评估");
        }
        if (ershouche.getPingguZongfen() == null) {
            ershouche.setPingguZongfen(BigDecimal.ZERO);
        }
        if (ershouche.getHasVrView() == null) {
            ershouche.setHasVrView(false);
        }
        if (ershouche.getVrViewCount() == null) {
            ershouche.setVrViewCount(0);
        }

        // 补充型号信息（如果为空）
        if (StrUtil.isBlank(ershouche.getPinpai())) {
            ershouche.setPinpai(qichexinghao.getPinpai());
        }
        if (StrUtil.isBlank(ershouche.getChexi())) {
            ershouche.setChexi(qichexinghao.getChexi());
        }
        if (StrUtil.isBlank(ershouche.getChexing())) {
            ershouche.setChexing(qichexinghao.getChexing());
        }
        if (ershouche.getNianfen() == null) {
            ershouche.setNianfen(qichexinghao.getNianfen());
        }
        if (StrUtil.isBlank(ershouche.getPailiang())) {
            ershouche.setPailiang(qichexinghao.getPailiang());
        }
        if (StrUtil.isBlank(ershouche.getBiansuxiang())) {
            ershouche.setBiansuxiang(qichexinghao.getBiansuxiang());
        }
        if (StrUtil.isBlank(ershouche.getRanliao())) {
            ershouche.setRanliao(qichexinghao.getRanliao());
        }

        save(ershouche);
        return ershouche;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Ershouche ershouche) {
        Ershouche existErshouche = getById(ershouche.getId());
        if (existErshouche == null) {
            throw new ServiceException("二手车信息不存在");
        }

        // 车辆编号不能修改
        ershouche.setCheliangbianhao(existErshouche.getCheliangbianhao());


        return updateById(ershouche);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, String zhuangtai) {
        Ershouche ershouche = getById(id);
        if (ershouche == null) {
            throw new ServiceException("二手车信息不存在");
        }

        ershouche.setZhuangtai(zhuangtai);
        return updateById(ershouche);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePinggu(Long id, String pingguStatus, BigDecimal pingguZongfen) {
        Ershouche ershouche = getById(id);
        if (ershouche == null) {
            throw new ServiceException("二手车信息不存在");
        }

        ershouche.setPingguStatus(pingguStatus);
        ershouche.setPingguZongfen(pingguZongfen);
        return updateById(ershouche);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean favorite(Long userId, Long carId) {
        // 检查车辆是否存在
        Ershouche ershouche = getById(carId);
        if (ershouche == null) {
            throw new ServiceException("二手车信息不存在");
        }

        // 检查是否已收藏
        LambdaQueryWrapper<Shoucang> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Shoucang::getUserid, userId)
                .eq(Shoucang::getRefid, carId)
                .eq(Shoucang::getTablename, "ershouche");
        if (shoucangMapper.selectCount(queryWrapper) > 0) {
            return true; // 已收藏，直接返回成功
        }

        // 添加收藏记录
        Shoucang shoucang = new Shoucang();
        shoucang.setAddtime(new Date());
        shoucang.setUserid(userId);
        shoucang.setRefid(carId);
        shoucang.setTablename("ershouche");
        shoucang.setName(ershouche.getCheliangbianhao() + " " + ershouche.getPinpai() + " " + ershouche.getChexi() + " " + ershouche.getChexing());
        shoucang.setPicture(ershouche.getTupian());
        shoucang.setType("car");

        return shoucangMapper.insert(shoucang) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelFavorite(Long userId, Long carId) {
        LambdaQueryWrapper<Shoucang> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Shoucang::getUserid, userId)
                .eq(Shoucang::getRefid, carId)
                .eq(Shoucang::getTablename, "ershouche");
        return shoucangMapper.delete(queryWrapper) >= 0;
    }

    @Override
    public IPage<ErshoucheVO> pageFavorites(Page<ErshoucheVO> page, Long userId) {
        return page.setRecords(List.of());
    }
} 