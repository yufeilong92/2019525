package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.widget.TextView;

import com.xuechuan.xcedu.R;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.19 下午 5:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmFontSizeUtil {
    private static volatile GmFontSizeUtil _singleton;
    private Context mContext;

    private GmFontSizeUtil(Context context) {
        this.mContext = context;
        fontSize = 16;
    }

    public static GmFontSizeUtil get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (GmFontSizeUtil.class) {
                if (_singleton == null) {
                    _singleton = new GmFontSizeUtil(context);
                }
            }
        }
        return _singleton;
    }

    private float fontSize;


    public static enum fontStatus {
        font14, font16, font18, font20, font24
    }

    public void status(fontStatus status) {
        switch (status) {
            case font14:
                fontSize = 14;
                break;
            case font16:
                fontSize = 16;
                break;
            case font18:
                fontSize = 18;
                break;
            case font20:
                fontSize = 20;
                break;
            case font24:
                fontSize = 24;
                break;

        }
    }

    public float getFontSize() {
        return fontSize;
    }
}
