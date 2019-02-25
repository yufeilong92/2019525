package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 通用解析总数vo
 * @author: L-BackPacker
 * @date: 2018/4/20 9:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TotalVo {
    private String current_total;
    private String skip_total;
    private String total;

    public String getCurrent_total() {
        return current_total;
    }

    public void setCurrent_total(String current_total) {
        this.current_total = current_total;
    }

    public String getSkip_total() {
        return skip_total;
    }

    public void setSkip_total(String skip_total) {
        this.skip_total = skip_total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
