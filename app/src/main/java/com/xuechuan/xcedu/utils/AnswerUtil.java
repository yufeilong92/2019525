package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/4 10:13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerUtil {
   private static Context mContext;

    public AnswerUtil(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 设置字体颜色
     */
    public void setTextColor(){

    }
    //设置空格线
    public static void setLinebg(View v, int id) {
        v.setBackgroundColor(getLayoutColor(id));
    }

    /**
     * 获取背景颜色id
     * @param id
     * @return
     */
    public static int getLayoutColor(int id) {
        return mContext.getResources().getColor(id);
    }

    /**
     * 设置text 背景
     * @param tv
     * @param id
     */
    public static void setTvColor(TextView tv, int id) {
        tv.setTextColor(getLayoutColor(id));
    }


}
