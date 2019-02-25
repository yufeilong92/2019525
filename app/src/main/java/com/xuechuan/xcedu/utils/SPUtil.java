package com.xuechuan.xcedu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.io.File;
import java.lang.reflect.Field;

import static com.lzy.okgo.model.Progress.FILE_PATH;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/5 8:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SPUtil {
//    public static final String FILE_PATH = MyApplication.getContext().getExternalFilesDir(null).getAbsolutePath();
    /**
     * @param context
     * @param fileName
     * @return isDebug = 返回修改路径(路径不存在会自动创建)以后的 SharedPreferences :%FILE_PATH%/%fileName%.xml<br/>
     * !isDebug = 返回默认路径下的 SharedPreferences : /data/data/%package_name%/shared_prefs/%fileName%.xml
     */
    private static SharedPreferences getSharedPreferences(Context context, String fileName) {
        try {
            // 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象
            Field field = ContextWrapper.class.getDeclaredField("mBase");
            field.setAccessible(true);
            // 获取mBase变量
            Object obj = field.get(context);
            // 获取ContextImpl。mPreferencesDir变量，该变量保存了数据文件的保存路径
            field = obj.getClass().getDeclaredField("mPreferencesDir");
            field.setAccessible(true);
            // 创建自定义路径
            File file = new File(FILE_PATH);
            // 修改mPreferencesDir变量的值
            field.set(obj, file);
            // 返回修改路径以后的 SharedPreferences :%FILE_PATH%/%fileName%.xml
            return context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 返回默认路径下的 SharedPreferences : /data/data/%package_name%/shared_prefs/%fileName%.xml
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

}
