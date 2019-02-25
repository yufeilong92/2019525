package com.xuechuan.xcedu.vo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 删除课目
 * @author: L-BackPacker
 * @date: 2018/5/19 15:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetDownSelectVo {
    /**
     * 课目id
     */
    private String id;

    private boolean isShow;
    private boolean isSelect;
    private List<String>zips;

    public List<String> getZips() {
        return zips;
    }

    public void setZips(List<String> zips) {
        this.zips = zips;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
