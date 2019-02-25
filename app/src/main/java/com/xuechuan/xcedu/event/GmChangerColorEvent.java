package com.xuechuan.xcedu.event;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: GmChangerColorEvent.java
 * @Package com.xuechuan.xcedu.event
 * @Description: 做题界面改变颜色
 * @author: YFL
 * @date: 2018/12/23 19:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/12/23 星期日
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class GmChangerColorEvent {
    private int number;//类型
    private int type;//1为改变颜色，2为改变字体 3，只做刷新界面使用,4分享
    private boolean isClick;

    public GmChangerColorEvent(int number, int type, boolean isClick) {
        this.number = number;
        this.type = type;
        this.isClick = isClick;
    }

    public int getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }

    public boolean getClick() {
        return isClick;
    }
}
