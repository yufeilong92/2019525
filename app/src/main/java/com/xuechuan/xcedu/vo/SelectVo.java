package com.xuechuan.xcedu.vo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 处理用户选中显示问题
 * @author: L-BackPacker
 * @date: 2018/5/17 9:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SelectVo {
    private String parenid;
    private List<ItemSelectVo> data;

    public List<ItemSelectVo> getData() {
        return data;
    }

    public void setData(List<ItemSelectVo> data) {
        this.data = data;
    }

    public String getParenid() {
        return parenid;
    }

    public void setParenid(String parenid) {
        this.parenid = parenid;
    }
}
