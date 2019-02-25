package com.xuechuan.xcedu.vo;

import android.content.Context;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 省份
 * @author: L-BackPacker
 * @date: 2018/4/10 15:26
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ProvincesVo extends BaseVo {
    private static final long serialVersionUID = 118896089104573314L;
    //省
    private String name;
    //区位码
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Province{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }




}
