package com.xuechuan.xcedu.utils;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.ui.RegisterActivity;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 倒计时
 * @author: L-BackPacker
 * @date: 2018/4/16 10:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CountdownUtil {
    int recLen = 60;
    private Context mContext;
    Handler handler = new Handler();
    private static CountdownUtil util;
    private Button button;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            if (recLen ==0) {
                button.setText("重新发送");
                button.setEnabled(true);
                button.setTextColor(mContext.getResources().getColor(R.color.red_text));
                recLen = 60;
                return;
            }
            button.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            button.setText("重发" + "(" + recLen + ")");
            handler.postDelayed(this, 1000);
        }
    };

    public void startTime(Context context, final Button button) {
        this.button = button;
        this.mContext = context;
        handler.postDelayed(runnable, 1000);
    }

    public static CountdownUtil getInstance() {
        if (util == null)
            util = new CountdownUtil();
        return util;
    }

    public void stop() {
        handler.postDelayed(null, 1000);
    }
}
