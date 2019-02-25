package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 用户选择4g播放下载
 * @author: L-BackPacker
 * @date: 2018/9/25 18:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SelectVideoFGVo implements Serializable{
    private boolean saveFGD;
    private boolean saveFGP;

    public boolean isSaveFGD() {
        return saveFGD;
    }

    public void setSaveFGD(boolean saveFGD) {
        this.saveFGD = saveFGD;
    }

    public boolean isSaveFGP() {
        return saveFGP;
    }

    public void setSaveFGP(boolean saveFGP) {
        this.saveFGP = saveFGP;
    }
}
