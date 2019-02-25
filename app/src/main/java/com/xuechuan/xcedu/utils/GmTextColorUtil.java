package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.widget.TextView;

import com.xuechuan.xcedu.R;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 字体颜色
 * @author: L-BackPacker
 * @date: 2018.12.20 下午 3:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmTextColorUtil {
    private static volatile GmTextColorUtil _singleton;
    private Context mContext;

    private GmTextColorUtil(Context context) {
        this.mContext = context;
        textColor = getColorDraw(R.color.text_fu_color);
        textBg = R.drawable.bg_select_answer_btn_ss;
    }

    public static GmTextColorUtil get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (GmTextColorUtil.class) {
                if (_singleton == null) {
                    _singleton = new GmTextColorUtil(context);
                }
            }
        }
        return _singleton;
    }

    private int textColor;
    private int textBg;

    public static enum TextColorStatus {
        GRAY, MISS, RED, GREED, BLACE,QianGray
    }

    public void status(TextColorStatus status) {
        switch (status) {
            case GRAY:
                textColor = getColorDraw(R.color.text_fu_color);
                textBg = R.drawable.bg_select_answer_btn_ss;
                break;
            case MISS:
                textColor = getColorDraw(R.color.text_tab_miss_color);
                textBg = R.drawable.bg_select_answer_btn_miss;
                break;
            case RED:
                textColor = getColorDraw(R.color.red_text);
                textBg = R.drawable.btn_answer_bg_error;
                break;
            case GREED:
                textColor = getColorDraw(R.color.text_tab_right);
                textBg = R.drawable.bg_select_answer_btn_n;
                break;
            case BLACE:
                textColor = getColorDraw(R.color.black);
                textBg = R.drawable.bg_select_answer_btn_su;
                break;
            case QianGray:
                textColor = getColorDraw(R.color.black);
                textBg = R.drawable.bg_select_answer_btn_su_gray;
                break;


        }
    }

    private int getColorDraw(int id) {
        return mContext.getResources().getColor(id);
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTextBg() {
        return textBg;
    }
}
