package com.xuechuan.xcedu.utils;

import com.google.gson.Gson;
import com.xuechuan.xcedu.vo.UserInfomVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/17 14:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GsonFromStr {

    private static GsonFromStr gsonFromStr;

    public static GsonFromStr getInstance() {
        gsonFromStr = new GsonFromStr();
        return gsonFromStr;
    }

}
