package com.xuechuan.xcedu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.xuechuan.xcedu.R;


/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/26 16:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerCardUtil {
    /**
     * 获取题类型
     *
     * @param id
     * @return
     */
    public static String getTextType(int id) {
        switch (id) {
            case 2:
                return "单选";
            case 3:
                return "多选";
            case 4:
                return "问答题";
            default:
        }
        return "";
    }

    public static String getSelectItem(String item) {
        if (item.equalsIgnoreCase("A")) {
            return "A";
        }
        if (item.equalsIgnoreCase("B")) {
            return "B";
        }
        if (item.equalsIgnoreCase("C")) {
            return "C";
        }
        if (item.equalsIgnoreCase("D")) {
            return "D";
        }
        if (item.equalsIgnoreCase("E")) {
            return "E";
        }
        return "";

    }
}

