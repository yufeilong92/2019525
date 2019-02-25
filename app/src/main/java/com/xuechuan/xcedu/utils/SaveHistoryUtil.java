package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SaveHistoryUtil.java
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: YFL
 * @date: 2018/4/15 12:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/15 星期日
 */
public class SaveHistoryUtil {
    private String key = "history";
    private String fileName = "fileName";
    private SharedPreferences sp = null;
    /**
     * 截取标示
     */
    private String mSpilt = "///>>";
    /**
     * 默认保存数据
     */
    private int mSavelength = 9;
    private static SaveHistoryUtil historyUtil;

    public SaveHistoryUtil(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, MODE_PRIVATE);
        }
    }

    public static SaveHistoryUtil getInstance(Context context) {
        if (historyUtil == null) {
            historyUtil = new SaveHistoryUtil(context);
        }
        return historyUtil;
    }

    /**
     * 保存数据
     *
     * @param newStr
     */
    public boolean saveHistory(String newStr) {
        if (StringUtil.isEmpty(newStr)) {
            return false;
        }
        String string;
        if (sp != null) {
            //先查询
            string = sp.getString(key, "");
            if (isAddGuo(string, newStr)) {
                return false;
            }
            //有保存数据
            if (!StringUtil.isEmpty(string)) {
                string = subString(string, newStr);
            } else {//无保存数据
                string = newStr + mSpilt;
            }
            //便利保存
            SharedPreferences.Editor edit = sp.edit();
            Log.e("yfl", "saveHistory: "+string );
            edit.putString(key, string);
            edit.commit();
        }
        return true;
    }

    /**
     * \
     *
     * @param oldStr 旧数据
     * @param newStr 要添加的数据
     * @return 拼接后的
     */
    private String subString(String oldStr, String newStr) {
        String[] olds = oldStr.split(mSpilt);
        String content;
        if (olds.length > mSavelength) {
            String old = olds[0];
            int length = old.length() + mSpilt.length();
            int cout = oldStr.length();
            String oldcotent = oldStr.substring(length, cout);
            content = oldcotent+ newStr + mSpilt;
            olds = null;
        } else {
            content = oldStr + newStr + mSpilt;
        }

        return content;
    }

    public void delete(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, "");
        edit.commit();

    }

    /**
     * 是否保存过
     *
     * @param old
     * @param fresh
     * @return
     */
    private boolean isAddGuo(String old, String fresh) {
        String[] split = old.split(mSpilt);
        for (String data : split) {
            if (data.equals(fresh)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取缓存数据
     *
     * @param context
     * @return
     */
    public ArrayList<String> getHistoryList(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, MODE_PRIVATE);
        }
        String string = sp.getString(key, "");
        return getArraylist(string);
    }

    /**
     * @param list
     * @return
     */
    private ArrayList<String> objectTolist(String[] list) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            if (!StringUtil.isEmpty(list[i]))
                strings.add(list[i]);
        }
        return strings;
    }

    /**
     * 得到集合
     *
     * @param content
     * @return
     */
    private ArrayList<String> getArraylist(String content) {
        String[] split = content.split(mSpilt);
        ArrayList<String> strings = objectTolist(split);
        return strings;
    }
}
