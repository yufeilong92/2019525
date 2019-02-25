package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 每日一练首页
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 4:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DayHomeVo extends BaseVo{
    private List<DayHomeBeanVo> datas;

    public List<DayHomeBeanVo> getDatas() {
        return datas;
    }

    public void setDatas(List<DayHomeBeanVo> datas) {
        this.datas = datas;
    }

}
