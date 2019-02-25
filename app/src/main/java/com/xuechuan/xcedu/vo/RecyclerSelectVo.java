package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 展示选中vo
 * @author: L-BackPacker
 * @date: 2018.11.08 上午 8:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RecyclerSelectVo {
    private boolean isOpen;
    private boolean isSelect;
    //后增（我的笔记）
    private int id;//笔记id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
