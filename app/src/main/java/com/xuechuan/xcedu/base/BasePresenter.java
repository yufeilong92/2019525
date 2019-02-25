package com.xuechuan.xcedu.base;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.DialogUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: 基础控制
 * @author: L-BackPacker
 * @date: 2018/5/21 10:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BasePresenter {
    private Context context;

    protected AlertDialog showDialog(Context context, String title, String msg, boolean isShow) {
        this.context = context;
        if (isShow)
            return DialogUtil.showDialog(context, title, msg);
        else return null;
    }

    protected String getStrWithId(int id) {
        return MyAppliction.getInstance().getResources().getString(id);
    }

}
