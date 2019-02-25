package com.xuechuan.xcedu.event;

import com.xuechuan.xcedu.vo.MyClassVo;
import com.xuechuan.xcedu.vo.TagsBeanVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.event
 * @Description: 网课数据
 * @author: L-BackPacker
 * @date: 2018.12.03 下午 4:33
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RefreshVideoNetEvent {
    private int year;

    public RefreshVideoNetEvent(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }
}
