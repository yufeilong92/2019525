package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 规范类
 * @author: L-BackPacker
 * @date: 2018/4/20 18:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecasChapterListVo extends BaseVo {

//    private TotalBeanVo total;
    private List<DatasBeanVo> datas;

//    public TotalBeanVo getTotal() {
//        return total;
//    }
//
//    public void setTotal(TotalBeanVo total) {
//        this.total = total;
//    }

    public List<DatasBeanVo> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBeanVo> datas) {
        this.datas = datas;
    }

}
