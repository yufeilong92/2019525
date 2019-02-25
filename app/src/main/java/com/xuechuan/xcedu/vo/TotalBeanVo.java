package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 解析类vo
 * @author: L-BackPacker
 * @date: 2018/4/20 19:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TotalBeanVo  {
    /**
     * 当前返回集合总数
     */
    private int current_total;
    /**
     * 	跳过多少条后获取的数据
     */
    private int skip_total;
    /**
     * 对象集合总数
     */
    private int total;

    public int getCurrent_total() {
        return current_total;
    }

    public void setCurrent_total(int current_total) {
        this.current_total = current_total;
    }

    public int getSkip_total() {
        return skip_total;
    }

    public void setSkip_total(int skip_total) {
        this.skip_total = skip_total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
