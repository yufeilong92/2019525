package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/27 19:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ShowPopuWindonUtil {

    private  CreateView setOnCreate;

    public void setSetOnCreate(CreateView setOnCreate) {
        this.setOnCreate = setOnCreate;
    }

    public interface CreateView {
        public void initView(View view);

        public void initEvent();

        public void initWindow(PopupWindow popupWindow);
    }

    public  CommonPopupWindow createPopuWindwon(Context context, int layout) {

        CommonPopupWindow popupWindow = new CommonPopupWindow(context, layout,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {


            private TextView mTvShare;
            private TextView mTvSettring;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvSettring = view.findViewById(R.id.tv_popu_setting);
                mTvShare = view.findViewById(R.id.tv_popi_share);
                if (setOnCreate != null) {
                    setOnCreate.initView(view);
                }
            }

            @Override
            protected void initEvent() {

                if (setOnCreate != null) {
                    setOnCreate.initEvent();
                }
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                if (setOnCreate != null) {
                    setOnCreate.initWindow(instance);
                }
            }
        };
        return popupWindow;
    }
}
