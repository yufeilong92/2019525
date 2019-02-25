package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;

import java.util.Calendar;


/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 弹窗
 * @author: L-BackPacker
 * @date: 2018/4/12 16:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DialogUtil {

    private static DialogUtil dialogUtil;


    public DialogUtil() {
    }

    public static DialogUtil getInstance() {
        if (dialogUtil == null)
            dialogUtil = new DialogUtil();
        return dialogUtil;
    }

    /**
     * @param context
     * @param titel   标题
     * @param cont    内容
     * @return
     */
    public static AlertDialog showDialog(Context context, String titel, String cont) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.picture_alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_dialog, null);
        TextView tv_msg = view.findViewById(R.id.tv_msg);
        TextView tv_title = view.findViewById(R.id.tv_dialog_title);
        ImageView iv_dialog = view.findViewById(R.id.iv_dialog_progress);
        iv_dialog.setImageResource(R.drawable.animation_loading);
        AnimationDrawable drawable = (AnimationDrawable) iv_dialog.getDrawable();
        drawable.start();
        if (!StringUtil.isEmpty(titel)) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(titel);
        } else {
            tv_title.setVisibility(View.GONE);
        }
        tv_msg.setText(cont);
        builder.setView(view)
                .setCancelable(false)
                .create();
        return builder.show();
    }

    /**
     * 继续答题
     */
    private onContincueClickListener continueClickListener;

    public interface onContincueClickListener {
        public void onCancelClickListener();

        public void onNextClickListener();
    }

    public void setContinueClickListener(onContincueClickListener continueClickListener) {
        this.continueClickListener = continueClickListener;
    }

    /**
     * 交卷
     */
    private onSubmitClickListener submitclickListener;

    public interface onSubmitClickListener {
        public void onCancelClickListener();

        public void oSubmitClickListener();
    }

    public void setSubmitClickListener(onSubmitClickListener clickListener) {
        this.submitclickListener = clickListener;
    }

    /**
     * 交卷
     */
    private onStopClickListener stopclicklistener;

    public interface onStopClickListener {

        public void onNextClickListener();
    }

    public void setStopClickListener(onStopClickListener clickListener) {
        this.stopclicklistener = clickListener;
    }

    /**
     * 继续答题
     *
     * @param context
     * @param page    第几题
     * @return
     */
    public void showContinueDialog(Context context, String page) {
        if (Integer.valueOf(page) <= 1) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_continue, null);
        TextView tv = view.findViewById(R.id.tv_number);
        tv.setText(page);
        builder.setView(view)
                .setCancelable(false)
                .create();
        final AlertDialog show = builder.show();
        Button btnAgain = view.findViewById(R.id.btn_dialog_again);
        Button butGo = view.findViewById(R.id.btn_dialog_next);
        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continueClickListener != null)
                    show.dismiss();
                continueClickListener.onCancelClickListener();

            }
        });
        butGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continueClickListener != null) {
                    show.dismiss();
                    continueClickListener.onNextClickListener();
                }
            }
        });


    }

    private boolean isOver = false;

    /**
     * 判断用户是否做完
     *
     * @param isOver
     */
    public void setIsDoOver(boolean isOver) {
        this.isOver = isOver;
    }

    /**
     * 交卷
     *
     * @param context
     * @return
     */
    public void showSubmitDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_submit, null);
        builder.setView(view)
                .setCancelable(true)
                .create();
        final AlertDialog dialog = builder.show();
        TextView tvPost = view.findViewById(R.id.tv_submit_post_text);
        tvPost.setText(isOver ? "是否确认交卷" : context.getResources().getString(R.string.sure_submit_text));
        Button btnAgain = view.findViewById(R.id.btn_dialog_cancel);
        Button butGo = view.findViewById(R.id.btn_dialog_submit);
        btnAgain.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (submitclickListener != null) {
                    dialog.dismiss();
                    submitclickListener.onCancelClickListener();
                }

            }
        });
        butGo.setOnClickListener(new View.OnClickListener() {//提交
            @Override
            public void onClick(View v) {
                if (submitclickListener != null) {
                    dialog.dismiss();
                    submitclickListener.oSubmitClickListener();
                }
            }
        });


    }
    /*
     *//**
     * 时间暂停
     *
     * @param context
     * @return
     *//*
    public void showStopDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_time, null);
        builder.setView(view)
                .setCancelable(false)
                .create();
        final AlertDialog dialog = builder.show();
        Button butGo = view.findViewById(R.id.btn_stop_next);
        butGo.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (stopclicklistener != null) {
                    dialog.dismiss();
                    stopclicklistener.onNextClickListener();
                }

            }
        });


    }*/

    /**
     * 时间暂停
     *
     * @param context
     * @return
     */
    public AlertDialog showStopDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_time, null);
        builder.setView(view)
                .setCancelable(false)
                .create();
        final AlertDialog dialog = builder.show();
        Button butGo = view.findViewById(R.id.btn_stop_next);
        butGo.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (stopclicklistener != null) {
                    dialog.dismiss();
                    stopclicklistener.onNextClickListener();
                }

            }
        });
        return dialog;

    }

    private onTitleClickListener titelclickListener;

    public interface onTitleClickListener {
        public void onSureClickListener();

        public void onCancelClickListener();
    }

    public void setTitleClickListener(onTitleClickListener clickListener) {
        this.titelclickListener = clickListener;
    }

    /**
     * 普通标题
     *
     * @param context
     * @return
     */
    public void showTitleDialog(Context context, String title, String btnSure, String cancale,
                                boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_title, null);
        TextView tv = view.findViewById(R.id.tv_title);
        tv.setText(title + "?");

        Button sure = view.findViewById(R.id.btn_sure);
        sure.setText(btnSure);
        if (StringUtil.isEmpty(title)) {
            tv.setVisibility(View.GONE);
        }
        Button cancel = view.findViewById(R.id.btn_cancal);
        cancel.setText(cancale);
        builder.setView(view)
                .setCancelable(cancelable)
                .create();

        AlertDialog show = null;
        if (show == null)
            show = builder.show();

        final AlertDialog finalShow = show;
        sure.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (titelclickListener != null) {
                    finalShow.dismiss();
                    titelclickListener.onSureClickListener();

                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {//提交
            @Override
            public void onClick(View v) {
                if (titelclickListener != null) {
                    finalShow.dismiss();
                    titelclickListener.onCancelClickListener();
                }
            }
        });
    }

    /**
     * @param context
     * @param titel   标题
     * @param cont    内容
     * @return
     */
    public static AlertDialog showDialogTitleContent(Context context, String titel,
                                                     String cont, String btnSure, String cancale,
                                                     boolean cancelable, final onTitleClickListener onTitleClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_dialog_content, null);
        TextView tv = view.findViewById(R.id.tv_title);
        tv.setText(titel);
        TextView tv_title_fu = view.findViewById(R.id.tv_content_fu);
        tv_title_fu.setText(cont);
        Button sure = view.findViewById(R.id.btn_sure);
        sure.setText(btnSure);
        Button cancel = view.findViewById(R.id.btn_cancal);
        cancel.setText(cancale);
        builder.setView(view)
                .setCancelable(cancelable)
                .create();
        final AlertDialog show = builder.show();

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTitleClickListener != null) {
                    show.dismiss();
                    onTitleClickListener.onSureClickListener();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTitleClickListener != null) {
                    show.dismiss();
                    onTitleClickListener.onCancelClickListener();
                }
            }
        });
        return show;
    }

    /**
     * 普通标题
     *
     * @param context
     * @return
     */
    public void showTitleDialog(Context context, String title, String content, String btnSure, String cancale, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_title_msg, null);
        TextView tv = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_msg_contenxt);
        tv.setText(title + "?");
        tv_content.setText(content);
        Button sure = view.findViewById(R.id.btn_sure);
        sure.setText(btnSure);
        if (StringUtil.isEmpty(title)) {
            tv.setVisibility(View.GONE);
        }
        Button cancel = view.findViewById(R.id.btn_cancal);
        cancel.setText(cancale);
        builder.setView(view)
                .setCancelable(cancelable)
                .create();

        AlertDialog show = null;
        if (show == null)
            show = builder.show();

        final AlertDialog finalShow = show;
        sure.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (titelclickListener != null) {
                    finalShow.dismiss();
                    titelclickListener.onSureClickListener();

                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {//提交
            @Override
            public void onClick(View v) {
                if (titelclickListener != null) {
                    finalShow.dismiss();
                    titelclickListener.onCancelClickListener();
                }
            }
        });
    }

    Calendar cal;
    private int year, month, day;

    private onTimeClickListener timeListener;

    public interface onTimeClickListener {
        public void onTimeListener(String time);
    }

    public void setSelectTimeListener(onTimeClickListener clickListener) {
        this.timeListener = clickListener;
    }

    String time;

    public void showSelectTime(Context context, boolean cancelable) {
        DatePicker mDpShowTimeSelect;
        Button mBtnCancelTime;
        Button mBtnSureTime;
        getDate();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_select_time, null);
        mDpShowTimeSelect = view.findViewById(R.id.dp_show_time_select);
        mBtnCancelTime = view.findViewById(R.id.btn_cancel_time);
        mBtnSureTime = view.findViewById(R.id.btn_sure_time);
        builder.setView(view)
                .setCancelable(cancelable)
                .create();
        final AlertDialog show = builder.show();
        time = year + "-" + (month + 1) + "-" + day + "";
        DatePicker.OnDateChangedListener listener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
            }
        };
        mDpShowTimeSelect.init(year, month, day, listener);
        mBtnSureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                if (timeListener != null) {
                    timeListener.onTimeListener(time);
                }

            }
        });
        mBtnCancelTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });

    }

    //获取当前日期
    private void getDate() {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        month = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    private onItemPayDialogClickListener payClickListener;

    public interface onItemPayDialogClickListener {
        public void onPayDialogClickListener(int obj, int position);
    }

    public void setPayDialogClickListener(onItemPayDialogClickListener clickListener) {
        this.payClickListener = clickListener;
    }

    int a = -1;

    public void showPayDialog(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_pay, null);
        LinearLayout linzfb = view.findViewById(R.id.ll_net_pay_zfb);
        LinearLayout liweixin = view.findViewById(R.id.ll_net_weixin);
        final CheckBox chbweixin = view.findViewById(R.id.chb_net_pay_weixin);
        final CheckBox chbzfb = view.findViewById(R.id.chb_net_pay_zfb);
        Button btnsubmit = view.findViewById(R.id.btn_dialog_sure_pay);
        builder.setView(view)
                .setCancelable(true)
                .create();
        final AlertDialog show = builder.show();
        liweixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 1;
                ShowPayStatus(chbzfb, false, chbweixin, true);
            }
        });
        linzfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 2;
                ShowPayStatus(chbzfb, true, chbweixin, false);
            }
        });
        chbweixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!chbweixin.isPressed()) return;
                if (isChecked) {
                    a = 1;
                    ShowPayStatus(chbzfb, false, chbweixin, true);
                } else {
                    a = -1;
                    ShowPayStatus(chbzfb, false, chbweixin, false);
                }
            }
        });
        chbzfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!chbzfb.isPressed()) return;
                if (isChecked) {
                    a = 2;
                    ShowPayStatus(chbzfb, true, chbweixin, false);
                } else {
                    a = -1;
                    ShowPayStatus(chbzfb, false, chbweixin, false);
                }
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == -1) {
                    T.showToast(mContext, mContext.getResources().getString(R.string.pay_type));
                    return;
                }
                if (show != null && show.isShowing())
                    show.dismiss();

                if (payClickListener != null) {
                    if (show != null && show.isShowing())
                        show.dismiss();
                    payClickListener.onPayDialogClickListener(a, a);
                    a = -1;
                }

            }
        });


    }

    private void ShowPayStatus(CheckBox zc, boolean z, CheckBox wc, boolean w) {
        zc.setChecked(z);
        wc.setChecked(w);
    }

    /**
     * 设置监听
     */
    public interface OnDownClickListener {
        public void onClickDown(String path);

        public void onClickMsg();

        public void onClickPhone(String phone);
    }

    /**
     * 显示老师对话框
     *
     * @param context
     * @param vo
     */
    static boolean isDown = false;

    public static void showTeacherDialog(Context context, final ShowTeacherVo vo, final OnDownClickListener onDownClickListener) {
        final AlertDialog show;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.teacher_alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.item_dialog_teacher, null);
        ImageView ivMeTeacherPhoto = (ImageView) view.findViewById(R.id.iv_me_teacher_photo);
        ImageView close = (ImageView) view.findViewById(R.id.iv_m_my_teacher_close);
        final ImageView ivMTeacherScan = (ImageView) view.findViewById(R.id.iv_m_teacher_scan);
        TextView tvMTeacherName = (TextView) view.findViewById(R.id.tv_m_teacher_name);
        TextView tvMTeacherPhone = (TextView) view.findViewById(R.id.tv_m_teacher_phone);
        TextView tvMDownMyScan = (TextView) view.findViewById(R.id.tv_m_down_myscan);
        if (StringUtil.isEmpty(vo.TeacherImg)) {
            ivMeTeacherPhoto.setImageResource(R.drawable.ic_m_hear);
        } else {
            MyAppliction.getInstance().displayImages(ivMeTeacherPhoto, vo.TeacherImg, true);
        }
        if (StringUtil.isEmpty(vo.TeacherScan)) {
            isDown = true;
            ivMTeacherScan.setImageResource(R.mipmap.s_n);
        } else {
            isDown = false;
            MyAppliction.getInstance().displayImages(ivMTeacherScan, vo.TeacherScan, false);
        }
        tvMTeacherName.setText(vo.TeacherName);
        tvMTeacherPhone.setText(vo.TeacherPhone);
        tvMTeacherPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDownClickListener != null) {
                    onDownClickListener.onClickPhone(vo.TeacherPhone);
                }
            }
        });
        builder.setCancelable(true);
        builder.setView(view);
        builder.create();
        show = builder.show();
        tvMDownMyScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDownClickListener != null) {
                    if (isDown) {
                        onDownClickListener.onClickMsg();
                        return;
                    }
                    onDownClickListener.onClickDown(vo.TeacherScan);
                }
                if (show != null) {
                    show.dismiss();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }

    public static class ShowTeacherVo {
        private String TeacherImg;
        private String TeacherName;
        private String TeacherPhone;
        private String TeacherScan;

        public ShowTeacherVo(String teacherImg, String teacherName, String teacherPhone, String teacherScan) {
            TeacherImg = teacherImg;
            TeacherName = teacherName;
            TeacherPhone = teacherPhone;
            TeacherScan = teacherScan;
        }
    }

    /**
     * 设置监听
     */

    public interface OnBankClickListener {
        public void onCancelClickListener();

        public void onUpdataClickListener();

    }


    /**
     * 更新题库下载对话框
     *
     * @param progress
     */
    ProgressBar mProgerssBar;
    AlertDialog mShow;
    TextView mTvBankUpdataContent;
    private ProgressBar mProgerssBarJieYa;
    TextView tvBankUpdataTitle;

    public void showBankUpDialog(Context context, boolean outAble, String content, final OnBankClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.teacher_alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.item_bank_updata, null);
        mTvBankUpdataContent = (TextView) view.findViewById(R.id.tv_bank_updata_content);
        tvBankUpdataTitle = (TextView) view.findViewById(R.id.tv_bank_updata_title);
        final LinearLayout mLinBnt = (LinearLayout) view.findViewById(R.id.ll_bank_btn);
        mProgerssBar = (ProgressBar) view.findViewById(R.id.pb_bank_updata);
        mProgerssBarJieYa = (ProgressBar) view.findViewById(R.id.pb_bank_jieya);
        Button btnBankCancel = (Button) view.findViewById(R.id.btn_bank_cancel);
        Button btnBankUpdata = (Button) view.findViewById(R.id.btn_bank_updata);
        builder.setView(view);
        builder.setCancelable(outAble);
        builder.create();
        mShow = builder.show();
        mTvBankUpdataContent.setText(content);
        btnBankCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShow != null && mShow.isShowing())
                    mShow.dismiss();
                if (listener != null)
                    listener.onCancelClickListener();
            }
        });
        btnBankUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvBankUpdataTitle.setText("正在下载");
                mTvBankUpdataContent.setVisibility(View.INVISIBLE);
                mLinBnt.setVisibility(View.GONE);
                mProgerssBar.setVisibility(View.VISIBLE);
                if (listener != null)
                    listener.onUpdataClickListener();
            }
        });
    }

    /**
     * 更新题库下载对话框
     *
     * @param progress
     */
    ProgressBar mProgerssOneBar;
    AlertDialog mShowOne;
    TextView mTvBankUpdataOneContent;
    ProgressBar mProgerssBarOneJieYa;
    TextView tvBankUpdataOneTitle;
    private boolean show = false;

    public void showBankUpDialogOne(Context context, boolean outAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.teacher_alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.item_bank_updata, null);
        mTvBankUpdataOneContent = (TextView) view.findViewById(R.id.tv_bank_updata_content);
        tvBankUpdataOneTitle = (TextView) view.findViewById(R.id.tv_bank_updata_title);
        mProgerssOneBar = (ProgressBar) view.findViewById(R.id.pb_bank_updata);
        mProgerssBarOneJieYa = (ProgressBar) view.findViewById(R.id.pb_bank_jieya);
        builder.setView(view);
        builder.setCancelable(outAble);
        builder.create();
        mShowOne = builder.show();
        tvBankUpdataOneTitle.setText("正在下载");
        mTvBankUpdataOneContent.setVisibility(View.INVISIBLE);
        mProgerssOneBar.setVisibility(View.VISIBLE);
    }


    /**
     * 跟新进度条
     *
     * @param mContext
     * @param progress
     * @param finish
     */
    public void setUpdataBankDialogProgress(Context mContext, Progress progress, boolean finish, boolean sysn) {
        if (finish) {
            if (mShowOne != null && mShowOne.isShowing()) {
                mShowOne.dismiss();
                show = false;
                return;
            }
        }
        if (sysn) {
            mTvBankUpdataOneContent.setVisibility(View.GONE);
            tvBankUpdataOneTitle.setText("同步数据中...");
            return;
        }
        if (show) {
            return;
        }
        if (mProgerssOneBar != null && progress != null) {
            String total = Utils.convertFileSizeB(progress.totalSize);
            String currentSize = Utils.convertFileSizeB(progress.currentSize);
            if (total.equals(currentSize) && mProgerssBarOneJieYa != null) {
                show = true;
                mProgerssOneBar.setVisibility(View.GONE);
                mProgerssBarOneJieYa.setVisibility(View.VISIBLE);
                mTvBankUpdataOneContent.setVisibility(View.GONE);
                tvBankUpdataOneTitle.setText("解压并数据添加中...");
                return;
            }
            mTvBankUpdataOneContent.setVisibility(View.VISIBLE);
            mTvBankUpdataOneContent.setText(currentSize + "/" + total);
            float fraction = progress.fraction;
            int pg = (int) (fraction * 100);
            mProgerssOneBar.setProgress(pg);
        }

    }

    public interface EvaluesInterface {
        public void result(int start, String comment);
    }

    /**
     * 显示评价内容
     *
     * @param context
     * @param outAble     是否能取消
     * @param anInterface 接口
     */
    public static void showDialogEvalues(Context context, boolean outAble, final EvaluesInterface anInterface) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.teacher_alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.item_imger_dailog, null);
        final CheckBox mChbEvaluerOne = (CheckBox) view.findViewById(R.id.chb_evaluer_one);
        final CheckBox mChbEvaluerTwo = (CheckBox) view.findViewById(R.id.chb_evaluer_two);
        final CheckBox mChbEvaluerThree = (CheckBox) view.findViewById(R.id.chb_evaluer_three);
        final CheckBox mChbEvaluerFour = (CheckBox) view.findViewById(R.id.chb_evaluer_four);
        final CheckBox mChbEvaluerFive = (CheckBox) view.findViewById(R.id.chb_evaluer_five);
        EditText mEtEvaluerInput = (EditText) view.findViewById(R.id.et_evaluer_input);
        Button mBtnEvaluerSubmit = (Button) view.findViewById(R.id.btn_evaluer_submit);
        builder.setView(view);
        builder.setCancelable(outAble);
        builder.create();
        final AlertDialog show = builder.show();
        final String trim = mEtEvaluerInput.getText().toString().trim();
        mBtnEvaluerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 0;
                if (mChbEvaluerOne.isChecked()) {
                    a += 1;
                }
                if (mChbEvaluerTwo.isChecked()) {
                    a += 1;
                }
                if (mChbEvaluerThree.isChecked()) {
                    a += 1;
                }
                if (mChbEvaluerFour.isChecked()) {
                    a += 1;
                }
                if (mChbEvaluerFive.isChecked()) {
                    a += 1;
                }
                anInterface.result(a, trim);
                show.dismiss();
            }
        });


    }


}
