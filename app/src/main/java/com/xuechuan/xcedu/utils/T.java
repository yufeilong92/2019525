package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 土司优化
 * @author: L-BackPacker
 * @date: 2018/4/10 10:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class T {
    private static Toast mToast;
    private static Handler mhandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        };
    };

    public static void showToast(Context context, String text) {
        mhandler.removeCallbacks(r);
        if (null != mToast) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        mhandler.postDelayed(r, 2000);
        mToast.show();
    }

    public static void showToast(Context context, int strId) {
        showToast(context, context.getString(strId));
    }

}
