package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 省份
 * @author: L-BackPacker
 * @date: 2018/4/18 19:05
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ProvinceEvent {
    private String province;

    private String code;
    public  ProvinceEvent(String province,String code) {
        this.province = province;
        this.code=code;
    }

    public  String getProvince() {
        return province;
    }
    public  String getCode() {
        return code;
    }
}
