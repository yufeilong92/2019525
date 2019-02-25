package com.xuechuan.xcedu.utils;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 程序管理
 * @author: L-BackPacker
 * @date: 2018/5/31 17:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ActivtiyMessagerUtl {
    private Context mContext;
    private static ActivtiyMessagerUtl service;

    public ActivtiyMessagerUtl(Context mContext) {
        this.mContext = mContext;
    }

    public static ActivtiyMessagerUtl getInstance(Context context) {
        if (service == null) {
            service = new ActivtiyMessagerUtl(context);
        }
        return service;
    }

    /**打开的activity**/
    private List<Activity> activities = new ArrayList<Activity>();
//    添加数据
    public void addActivity(Activity activity){
        activities.add(activity);
    }
    /**
     *  结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if (activity!=null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }
    /**
     * 应用退出，结束所有的activity
     */
    public void exit(){
        for (Activity activity : activities) {
            if (activity!=null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
