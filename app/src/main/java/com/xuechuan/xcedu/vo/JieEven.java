package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 章节返回EventBus
 * @author: L-BackPacker
 * @date: 2018/4/24 8:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class JieEven {
    private BookHomePageVo.DatasBean bean;

    public  JieEven(BookHomePageVo.DatasBean bean) {
        this.bean = bean;
    }

    public BookHomePageVo.DatasBean getBean() {
        return bean;
    }
}
