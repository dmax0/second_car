package com.example.secondhandcar.vo;

import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.entity.Qichexinghao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 二手车信息VO
 */
@Data
@Schema(description = "二手车详细信息")
public class ErshoucheVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "创建时间")
    private Date addtime;

    @Schema(description = "车辆编号")
    private String cheliangbianhao;

    @Schema(description = "汽车型号ID")
    private Long qichexinghaoid;

    @Schema(description = "车牌号")
    private String chepai;

    @Schema(description = "品牌")
    private String pinpai;

    @Schema(description = "车系")
    private String chexi;

    @Schema(description = "车型")
    private String chexing;

    @Schema(description = "年份")
    private Integer nianfen;

    @Schema(description = "里程")
    private Integer licheng;

    @Schema(description = "排量")
    private String pailiang;

    @Schema(description = "变速箱")
    private String biansuxiang;

    @Schema(description = "燃料类型")
    private String ranliao;

    @Schema(description = "颜色")
    private String yanse;

    @Schema(description = "内饰颜色")
    private String neishi;

    @Schema(description = "上牌日期")
    private Date shangpaidate;

    @Schema(description = "年检到期日")
    private Date nianjiandate;

    @Schema(description = "保险到期日")
    private Date baoxiandate;

    @Schema(description = "售价")
    private BigDecimal shoujia;

    @Schema(description = "封面图片")
    private String tupian;

    @Schema(description = "车辆图片集JSON")
    private String tupianMore;

    @Schema(description = "车辆图片集")
    private List<String> tupianList;

    @Schema(description = "详细描述")
    private String miaoshu;

    @Schema(description = "发布日期")
    private Date fabudate;

    @Schema(description = "发布人ID")
    private Long faburen;

    @Schema(description = "发布人信息")
    private String faburenName;

    @Schema(description = "状态")
    private String zhuangtai;

    @Schema(description = "3D模型ID")
    private Long modelId;

    @Schema(description = "3D模型文件路径")
    private String modelFilePath;

    @Schema(description = "是否有VR视图")
    private Boolean hasVrView;

    @Schema(description = "VR查看次数")
    private Integer vrViewCount;

    @Schema(description = "评估总分")
    private BigDecimal pingguZongfen;

    @Schema(description = "评估状态")
    private String pingguStatus;

    @Schema(description = "汽车型号信息")
    private Qichexinghao qichexinghaoInfo;

    @Schema(description = "是否收藏")
    private Boolean isCollected = false;

    /**
     * 从实体转换成VO对象
     */
    public static ErshoucheVO fromEntity(Ershouche ershouche) {
        ErshoucheVO vo = new ErshoucheVO();
        // 复制基本属性
        vo.setId(ershouche.getId());
        vo.setAddtime(ershouche.getAddtime());
        vo.setCheliangbianhao(ershouche.getCheliangbianhao());
        vo.setQichexinghaoid(ershouche.getQichexinghaoid());
        vo.setChepai(ershouche.getChepai());
        vo.setPinpai(ershouche.getPinpai());
        vo.setChexi(ershouche.getChexi());
        vo.setChexing(ershouche.getChexing());
        vo.setNianfen(ershouche.getNianfen());
        vo.setLicheng(ershouche.getLicheng());
        vo.setPailiang(ershouche.getPailiang());
        vo.setBiansuxiang(ershouche.getBiansuxiang());
        vo.setRanliao(ershouche.getRanliao());
        vo.setYanse(ershouche.getYanse());
        vo.setNeishi(ershouche.getNeishi());
        vo.setShangpaidate(ershouche.getShangpaidate());
        vo.setNianjiandate(ershouche.getNianjiandate());
        vo.setBaoxiandate(ershouche.getBaoxiandate());
        vo.setShoujia(ershouche.getShoujia());
        vo.setTupian(ershouche.getTupian());
        vo.setTupianMore(ershouche.getTupianMore());
        vo.setMiaoshu(ershouche.getMiaoshu());
        vo.setFabudate(ershouche.getFabudate());
        vo.setFaburen(ershouche.getFaburen());
        vo.setZhuangtai(ershouche.getZhuangtai());
        vo.setModelId(ershouche.getModelId());
        vo.setHasVrView(ershouche.getHasVrView());
        vo.setVrViewCount(ershouche.getVrViewCount());
        vo.setPingguZongfen(ershouche.getPingguZongfen());
        vo.setPingguStatus(ershouche.getPingguStatus());
        return vo;
    }
} 