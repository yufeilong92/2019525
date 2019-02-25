package com.xuechuan.xcedu.utils;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: Activity 管理
 * @author: L-BackPacker
 * @date: 2018/8/14 11:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ActivityMangerUtil {
    private static ArrayList<Activity> activitiyListes;

    public static void addActivity(Activity activity) {
        if (activitiyListes == null) {
            activitiyListes = new ArrayList<>();
        }
        activitiyListes.add(activity);
    }

    public static void finishAllActivity() {
        if (activitiyListes != null && !activitiyListes.isEmpty()) {
            for (int i = 0; i < activitiyListes.size(); i++) {
                Activity activity1 = activitiyListes.get(i);
                activity1.finish();
            }
            activitiyListes = null;
        }

    }

    public static void finishActivity(Activity activity) {
        if (activity == null) return;
        if (activitiyListes == null || activitiyListes.isEmpty()) return;
        activitiyListes.remove(activity);
        if (activity.isFinishing()) {
            activity.finish();
        }
        activity = null;
    }

}
