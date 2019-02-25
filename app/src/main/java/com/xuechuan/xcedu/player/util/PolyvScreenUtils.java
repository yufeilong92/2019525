package com.xuechuan.xcedu.player.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;

/**
 * 与屏幕相关的工具类
 */
public class PolyvScreenUtils {
    private static int height16_9;
    private static LinearLayout mTitleBar;

    private static LinearLayout mLlNetBuyLayous;
    //遮挡布局
    private static RelativeLayout mrlplaylayout;
    private static boolean mIsplay;

    public static void initTitleBar(LinearLayout titleBar, RelativeLayout mRlPlaylayout, LinearLayout mLlNetBuyLayou) {
        if (titleBar != null)
            mTitleBar = titleBar;
        if (mRlPlaylayout != null)
            mrlplaylayout = mRlPlaylayout;
        if (mLlNetBuyLayou != null)
            mLlNetBuyLayous = mLlNetBuyLayou;
    }
    private static  ImageView mIvBack;
    public static void initBack(ImageView ivback){
        if (ivback!=null){
            mIvBack=ivback;
        }
    }
    ;

    // 生成竖屏下w:h=16:9的高
    public static int generateHeight16_9(Activity activity) {
        return height16_9 != 0 ? height16_9 : (height16_9 = getNormalWH(activity)[isPortrait(activity) ? 0 : 1] * 9 / 16);
    }


    // 获取竖屏下w:h=16:9的高
    public static int getHeight16_9() {
        return height16_9;
    }


    // 是否竖屏
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    // 是否横屏
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    // 设置竖屏
    public static void setPortrait(Activity activity) {
        if (mTitleBar != null)
            mTitleBar.setVisibility(View.VISIBLE);
        if (mIvBack!=null){
            mIvBack.setVisibility(View.VISIBLE);
        }
        if (mrlplaylayout != null)
            if (MyAppliction.getInstance().getPlay()||mIsplay) {
                mrlplaylayout.setVisibility(View.GONE);
            } else {
                mrlplaylayout.setVisibility(View.VISIBLE);
            }
        if (mLlNetBuyLayous != null)
            mLlNetBuyLayous.setVisibility(View.VISIBLE);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
    }

    // 设置横屏
    public static void setLandscape(Activity activity) {
        if (mTitleBar != null)
            mTitleBar.setVisibility(View.GONE);
        if (mIvBack!=null){
            mIvBack.setVisibility(View.GONE);
        }
        if (mrlplaylayout != null)
            if (MyAppliction.getInstance().getPlay()||mIsplay) {
                mrlplaylayout.setVisibility(View.GONE);
            } else {
                mrlplaylayout.setVisibility(View.VISIBLE);
            }
        if (mLlNetBuyLayous != null)
            mLlNetBuyLayous.setVisibility(View.GONE);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    public static void IsPlay(boolean isPlay) {
        mIsplay = isPlay;
    }

    /**
     * 获取包含状态栏的屏幕宽度和高度
     *
     * @param activity
     * @return {宽,高}
     */
    public static int[] getNormalWH(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            return new int[]{dm.widthPixels, dm.heightPixels};
        } else {
            Point point = new Point();
            WindowManager wm = activity.getWindowManager();
            wm.getDefaultDisplay().getSize(point);
            return new int[]{point.x, point.y};
        }
    }

    // 重置状态栏
    public static void reSetStatusBar(Activity activity) {
        if (isLandscape(activity)) {
            hideStatusBar(activity);
        } else {
            setDecorVisible(activity);
        }
    }

    // 隐藏状态栏
    public static void hideStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (mTitleBar != null)
                mTitleBar.setVisibility(View.GONE);
            if (mIvBack!=null){
                mIvBack.setVisibility(View.GONE);
            }
            if (mrlplaylayout != null)
                if (MyAppliction.getInstance().getPlay()||mIsplay) {
                    mrlplaylayout.setVisibility(View.GONE);
                } else {
                    mrlplaylayout.setVisibility(View.VISIBLE);
                }
            if (mLlNetBuyLayous != null)
                mLlNetBuyLayous.setVisibility(View.GONE);
        } else {
            View decorView = activity.getWindow().getDecorView();
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
            if (mTitleBar != null)
                mTitleBar.setVisibility(View.GONE);
            if (mIvBack!=null){
                mIvBack.setVisibility(View.GONE);
            }
            if (mrlplaylayout != null)
                if (MyAppliction.getInstance().getPlay()||mIsplay) {
                    mrlplaylayout.setVisibility(View.GONE);
                } else {
                    mrlplaylayout.setVisibility(View.VISIBLE);
                }
            if (mLlNetBuyLayous != null)
                mLlNetBuyLayous.setVisibility(View.GONE);
        }
    }

    // 恢复为不全屏状态
    public static void setDecorVisible(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (mTitleBar != null)
                mTitleBar.setVisibility(View.VISIBLE);
            if (mIvBack!=null){
                mIvBack.setVisibility(View.VISIBLE);
            }
            if (mLlNetBuyLayous != null)
                mLlNetBuyLayous.setVisibility(View.VISIBLE);
            if (mrlplaylayout != null)
                if (MyAppliction.getInstance().getPlay()||mIsplay) {
                    mrlplaylayout.setVisibility(View.GONE);
                } else {
                    mrlplaylayout.setVisibility(View.VISIBLE);
                }
        } else {
            View decorView = activity.getWindow().getDecorView();
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
            if (mTitleBar != null)
                mTitleBar.setVisibility(View.VISIBLE);
            if (mIvBack!=null){
                mIvBack.setVisibility(View.VISIBLE);
            }
            if (mLlNetBuyLayous != null)
                mLlNetBuyLayous.setVisibility(View.VISIBLE);
            if (mrlplaylayout != null)
                if (MyAppliction.getInstance().getPlay()||mIsplay) {
                    mrlplaylayout.setVisibility(View.GONE);
                } else {
                    mrlplaylayout.setVisibility(View.VISIBLE);
                }
        }
    }

}
