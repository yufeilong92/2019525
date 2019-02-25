package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 资讯列表
 * @author: L-BackPacker
 * @date: 2018/4/20 11:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AdvisoryListVo extends BaseVo {
//    private TotalVo total;
    private List<AdvisoryVo> datas;

//    public TotalVo getTotal() {
//        return total;
//    }

//    public void setTotal(TotalVo total) {
//        this.total = total;
//    }

    public List<AdvisoryVo> getDatas() {
        return datas;
    }

    public void setDatas(List<AdvisoryVo> datas) {
        this.datas = datas;
    }
}
