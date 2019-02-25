package com.xuechuan.xcedu.utils;

import android.content.Context;


import com.xuechuan.xcedu.vo.SelectRefundVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/20 8:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ArrayToListUtil {
    public static ArrayList<String> arraytoList(Context context, int id) {
        String[] array = context.getResources().getStringArray(id);
        ArrayList<String> list = new ArrayList<>();
        for (String a : array
                ) {
            list.add(a);
        }
        return list.size() > 0 ? list : null;
    }


}
