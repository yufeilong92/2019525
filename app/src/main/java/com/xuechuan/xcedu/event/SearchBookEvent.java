package com.xuechuan.xcedu.event;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.event
 * @Description: 搜索图书模式
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 4:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SearchBookEvent {
    private String mKey;

    public SearchBookEvent(String mKey) {
        this.mKey = mKey;
    }

    public String getmKey() {
        return mKey;
    }


}
