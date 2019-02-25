package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.xuechuan.xcedu.R;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 图片控制
 * @author: L-BackPacker
 * @date: 2018.12.14 下午 1:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmSelectImgManageUtil {
    private static volatile GmSelectImgManageUtil _singleton;
    private Context mContext;

    private GmSelectImgManageUtil(Context context) {
        this.mContext = context;
    }

    public static GmSelectImgManageUtil get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (GmSelectImgManageUtil.class) {
                if (_singleton == null) {
                    _singleton = new GmSelectImgManageUtil(context);
                }
            }
        }
        return _singleton;
    }

    private Drawable A;
    private Drawable B;
    private Drawable C;
    private Drawable D;
    private Drawable E;
    private Drawable P;

    private Drawable SelectA;
    private Drawable SelectB;
    private Drawable SelectC;
    private Drawable SelectD;
    private Drawable SelectE;

    private Drawable voice;
    private Drawable input;
    private Drawable note;
//    private int note;
    private Drawable tag;
//    private int tag;

    /**
     * 未选择得状态
     */
    public enum Stauts {
        DayRadio, Daychoice, NightRadio, NigthChoice, EyERadio, EyEChoice
    }

    /**
     * 用户选中得按钮
     */
    public enum SelectStatus {
        A, B, C, D, E, P
    }

    /**
     * 用户选中数据 (多选) 用户选中数据
     *
     * @param status
     */
    public void setSelectStatus(SelectStatus status) {

        switch (status) {
            case A:
                SelectA = getDrawable(R.drawable.qbank_answe_icon_multiple_a_s);
                break;
            case B:
                SelectB = getDrawable(R.drawable.qbank_answe_icon_multiple_b_s);
                break;
            case C:
                SelectC = getDrawable(R.drawable.qbank_answe_icon_multiple_c_s);
                break;
            case D:
                SelectD = getDrawable(R.drawable.qbank_answe_icon_multiple_d_s);
                break;
            case E:
                SelectE = getDrawable(R.drawable.qbank_answe_icon_multiple_e_s);
                break;


        }
    }

    private Drawable Right;
    private Drawable Error;
    private Drawable Miss;

    public enum RigthStatus {
        RadioRight, ChoiceRight
    }

    /**
     * 获取正确率
     *
     * @param stauts
     */
    public void setRightStauts(RigthStatus stauts) {
        switch (stauts) {
            case RadioRight:
                Right = getDrawable(R.mipmap.ic_b_right);
                Error = getDrawable(R.mipmap.ic_b_singlewrong);

                break;
            case ChoiceRight:
                Right = getDrawable(R.mipmap.ic_b_text_right);
                Error = getDrawable(R.mipmap.ic_b_erro);
                Miss = getDrawable(R.mipmap.ic_b_miss);
                break;

        }
    }

    public Drawable getRight() {
        return Right;
    }

    public Drawable getError() {
        return Error;
    }

    public Drawable getMiss() {
        return Miss;
    }

    /**
     * @param stauts
     */
    public void setStatus(Stauts stauts) {
        switch (stauts) {
            case DayRadio:
                A = getDrawable(R.drawable.ic_b_single_a_n);
                B = getDrawable(R.drawable.ic_b_single_b_n);
                C = getDrawable(R.drawable.ic_b_single_c_n);
                D = getDrawable(R.drawable.ic_b_single_d_n);
                P = getDrawable(R.mipmap.qb_pinglun);

                break;
            case Daychoice:
                A = getDrawable(R.drawable.ic_b_a_n);
                B = getDrawable(R.drawable.ic_b_b_n);
                C = getDrawable(R.drawable.ic_b_c_n);
                D = getDrawable(R.drawable.ic_b_d_n);
                E = getDrawable(R.drawable.ic_b_e_n);
                P = getDrawable(R.mipmap.qb_pinglun);

                break;
            case NightRadio:
                A = getDrawable(R.drawable.qbank_answer_icon_single_a_s);
                B = getDrawable(R.drawable.qbank_answer_icon_single_b_s);
                C = getDrawable(R.drawable.qbank_answer_icon_single_c_s);
                D = getDrawable(R.drawable.qbank_answer_icon_single_d_s);
                E = getDrawable(R.drawable.qbank_answer_icon_single_e_s);
                P = getDrawable(R.mipmap.qb_pinglun_y);
                voice = getDrawable(R.drawable.qb_anli_yuyinshuru_y);
                input = getDrawable(R.drawable.qb_anli_yuyinzanting_y);
                note = getDrawable(R.drawable.qb_note_bg_s);
                tag = getDrawable(R.drawable.qb_jdbq_bg_s);

                break;
            case NigthChoice:
                A = getDrawable(R.drawable.qbank_answe_icon_multiple_a_s);
                B = getDrawable(R.drawable.qbank_answe_icon_multiple_b_s);
                C = getDrawable(R.drawable.qbank_answe_icon_multiple_c_s);
                D = getDrawable(R.drawable.qbank_answe_icon_multiple_d_s);
                E = getDrawable(R.drawable.qbank_answe_icon_multiple_e_s);
                P = getDrawable(R.mipmap.qb_pinglun_y);
                voice = getDrawable(R.drawable.qb_anli_yuyinshuru_y);
                input = getDrawable(R.drawable.qb_anli_yuyinzanting_y);
                note = getDrawable(R.drawable.qb_note_bg_s);
                tag = getDrawable(R.drawable.qb_jdbq_bg_s);
                break;
            case EyERadio:
                A = getDrawable(R.drawable.ic_b_single_a_n);
                B = getDrawable(R.drawable.ic_b_single_b_n);
                C = getDrawable(R.drawable.ic_b_single_c_n);
                D = getDrawable(R.drawable.ic_b_single_d_n);
                P = getDrawable(R.mipmap.qb_pinglun_y);
                voice = getDrawable(R.drawable.qb_anli_yuyinshuru);
                input = getDrawable(R.drawable.qb_anli_yuyinzanting);
                note = getDrawable(R.drawable.qb_note_bg_n);
                tag = getDrawable(R.drawable.qb_jdbg_s);
                break;
            case EyEChoice:
                A = getDrawable(R.drawable.ic_b_a_n);
                B = getDrawable(R.drawable.ic_b_b_n);
                C = getDrawable(R.drawable.ic_b_c_n);
                D = getDrawable(R.drawable.ic_b_d_n);
                E = getDrawable(R.drawable.ic_b_e_n);
                P = getDrawable(R.mipmap.qb_pinglun_y);
                voice = getDrawable(R.drawable.qb_anli_yuyinshuru);
                input = getDrawable(R.drawable.qb_anli_yuyinzanting);
                note = getDrawable(R.drawable.qb_note_bg_n);
                tag = getDrawable(R.drawable.qb_jdbg_s);
                break;
        }

    }

    public Drawable getDrawable(int id) {
        return mContext.getResources().getDrawable(id);
    }
    public Drawable getA() {
        return A;
    }


    public Drawable getB() {
        return B;
    }


    public Drawable getC() {
        return C;
    }


    public Drawable getD() {
        return D;
    }


    public Drawable getE() {
        return E;
    }

    public Drawable getP() {
        return P;
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
}
