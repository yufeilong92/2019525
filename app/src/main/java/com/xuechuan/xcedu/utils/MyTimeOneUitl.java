package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.xuechuan.xcedu.mvp.view.TimeShowView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/11 14:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyTimeOneUitl {
    private Context context;
    private long curTime = 0;
    private static int number = 0;
    private boolean isPause = false;
    private TextView mTimeView;
    private static long MAX_TIME = 0;
    private static MyTimeOneUitl timeUitl;
    private TimeShowView TimeShow;

    public MyTimeOneUitl(Context context) {
        this.context = context;
    }

    public static MyTimeOneUitl getInstance(Context context) {
        if (timeUitl == null)
            timeUitl = new MyTimeOneUitl(context);
        return timeUitl;
    }

    /**
     * 开始
     *
     * @param tv
     * @param view
     */
    public void start(TextView tv,  TimeShowView view) {
        this.mTimeView = tv;
        this.TimeShow = view;
        number = 0;
        restart();
    }

    public static long getNubmer() {
        if (number * 1000 >= MAX_TIME)
            return MAX_TIME;
        return number * 1000;
    }

    /**
     * 重新开始
     */
    public void restart() {
        curTime = MAX_TIME;
        number = 0;
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 1000);
    }


    Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            curTime += 1000;
            number += 1;
            mTimeView.setText(TimeTools.getCountTimeByLong(curTime));
            if (curTime > 0) {
                mHandler.postDelayed(this, 1000);
            } else {
                TimeShow.TimeFinish();
//                Toast.makeText(context, "运行结束", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 取消
     */
    public void cancel() {
        number = 0;
        mTimeView.setText("00:00:00");
        mHandler.removeCallbacks(runnable);
    }

    /**
     * 暂停
     */
    public void pause() {
        if (!isPause) {
            mHandler.removeCallbacks(runnable);
        }
    }

    /**
     * 继续
     */
    public void resume() {
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 1000);
    }
}
