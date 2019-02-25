package com.xuechuan.xcedu.vo.SqliteVo;

import android.content.Context;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: 考试关联表
 * @author: L-BackPacker
 * @date: 2018.12.26 上午 10:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MockExamKeySqliteVo {
    private int id;
    private long timekey;
    private String saffid;
    private int isDo;//判断是否做题

    public int getIsDo() {
        return isDo;
    }

    public void setIsDo(int isDo) {
        this.isDo = isDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimekey() {
        return timekey;
    }

    public void setTimekey(long timekey) {
        this.timekey = timekey;
    }

    public String getSaffid() {
        return saffid;
    }

    public void setSaffid(String saffid) {
        this.saffid = saffid;
    }
}
