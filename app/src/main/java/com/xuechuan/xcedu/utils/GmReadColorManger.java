package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.xuechuan.xcedu.R;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 做题颜色管理器
 * @author: L-BackPacker
 * @date: 2018.12.07 上午 8:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmReadColorManger {
    private static volatile GmReadColorManger _singleton;
    private Context mContext;
    /**
     * 颜色为浅灰色
     */
    private int mTextFuColor;
    /**
     * 颜色为黑色
     */
    private int mTextTitleColor;
    /**
     * 颜色为红色
     */
    private int mTextRedColor;
    /**
     * 正确颜色
     */
    private int mTextRightColor;
    /**
     * 分割线的颜色
     */
    private int mCutLineColor;
    /**
     * 整体背景颜色
     */
    private int mLayoutBgColor;
    /**
     * 评价的颜色
     */
    private int mLayoutEvalueBgColor;
    /**
     * 交卷背景
     */
    private int mSubmitColorBg;

    /**
     * 白天模式
     */
    public static final int DAYTIME = 1;
    /**
     * 夜间模式
     */
    public static final int NIGHT = 2;
    /**
     * 护眼模式
     */
    public static final int EYE = 3;

    private Drawable voice;
    private Drawable input;
    private Drawable note;
    private Drawable tag;

    public static GmReadColorManger get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (GmReadColorManger.class) {
                if (_singleton == null) {
                    _singleton = new GmReadColorManger(context);
                }
            }
        }
        return _singleton;
    }


    private GmReadColorManger(Context context) {
        this.mContext = context;
        mSubmitColorBg=R.drawable.bg_select_answer_btn_submit;
        mTextFuColor = getColor(R.color.text_fu_color);
        mTextTitleColor = getColor(R.color.text_title_color);
        mTextRedColor = getColor(R.color.red_text);
        mCutLineColor = getColor(R.color.gray_line);
        mLayoutBgColor = getColor(R.color.white);
        mTextRightColor = getColor(R.color.text_color_right);
        mLayoutEvalueBgColor = getColor(R.color.gray_line);
        voice = getDrawable(R.drawable.qb_anli_yuyinshuru);
        input = getDrawable(R.drawable.qb_anli_yuyinzanting);
        note = getDrawable(R.drawable.bg_login_li);
        tag = getDrawable(R.drawable.tv_bank_bar_bg);
    }
/*    voice = getDrawable(R.drawable.qb_anli_yuyinshuru);
    input = getDrawable(R.drawable.qb_anli_yuyinzanting);
    note = getDrawable(R.drawable.qb_note_bg_n);
    tag = getDrawable(R.drawable.qb_jdbg_s);*/
    private Drawable getDrawable(int id) {
        return mContext.getResources().getDrawable(id);
    }

    private int getColor(int text_fu_color) {
        return mContext.getResources().getColor(text_fu_color);
    }

    public void setGmBgColor(int number) {
        switch (number) {
            case DAYTIME://白天模式
                mSubmitColorBg=R.drawable.bg_select_answer_btn_submit;
                mTextFuColor = getColor(R.color.text_fu_color);
                mTextTitleColor = getColor(R.color.text_title_color);
                mTextRedColor = getColor(R.color.red_text);
                mCutLineColor = getColor(R.color.gray_line);
                mLayoutBgColor = getColor(R.color.white);
                mTextRightColor = getColor(R.color.text_color_right);
                note = getDrawable(R.drawable.bg_login_li);
                tag = getDrawable(R.drawable.tv_bank_bar_bg);
                voice = getDrawable(R.drawable.qb_anli_yuyinshuru);
                input = getDrawable(R.drawable.qb_anli_yuyinzanting);
                break;
            case NIGHT://夜间夜间
                mSubmitColorBg=R.drawable.bg_select_answer_btn_submit_s;
                mTextFuColor = getColor(R.color.night_text_color);
                mTextTitleColor = getColor(R.color.night_text_color);
                mTextRedColor = getColor(R.color.red_text);
                mCutLineColor = getColor(R.color.night_line_bg);
                mLayoutBgColor = getColor(R.color.night_layout_bg);
                mTextRightColor = getColor(R.color.text_color_right);
                note = getDrawable(R.drawable.gm_note_ll_bg);
                tag = getDrawable(R.drawable.tv_bank_bar_gray_bg);
                voice = getDrawable(R.drawable.qb_anli_yuyinshuru_y);
                input = getDrawable(R.drawable.qb_anli_yuyinzanting_y);
                break;
            case EYE://护眼模式
                mSubmitColorBg=R.drawable.bg_select_answer_btn_submit;
                mTextFuColor = getColor(R.color.text_fu_color);
                mTextTitleColor = getColor(R.color.text_title_color);
                mTextRedColor = getColor(R.color.red_text);
                mCutLineColor = getColor(R.color.eye_line_bg);
                mLayoutBgColor = getColor(R.color.eye_layout_bg);
                mTextRightColor = getColor(R.color.text_color_right);
                note = getDrawable(R.drawable.bg_login_li);
                tag = getDrawable(R.drawable.tv_bank_bar_bg);
                voice = getDrawable(R.drawable.qb_anli_yuyinshuru);
                input = getDrawable(R.drawable.qb_anli_yuyinzanting);
                break;
            default:

        }

    }


    public int getmTextFuColor() {
        return mTextFuColor;
    }

    public int getmTextTitleColor() {
        return mTextTitleColor;
    }

    public int getmTextRedColor() {
        return mTextRedColor;
    }

    public int getmTextRightColor() {
        return mTextRightColor;
    }

    public int getmCutLineColor() {
        return mCutLineColor;
    }

    public Drawable getVoice() {
        return voice;
    }

    public Drawable getInput() {
        return input;
    }

    public Drawable getNote() {
        return note;
    }

    public Drawable getTag() {
        return tag;
    }

    public int getmLayoutBgColor() {
        return mLayoutBgColor;
    }

    public int getmSubmitColorBg() {
        return mSubmitColorBg;
    }
}
