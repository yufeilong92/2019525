package com.xuechuan.xcedu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.xuechuan.xcedu.vo.SelectVideoFGVo;

import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/28 8:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SaveSelectFGUtil {
    // 用户名key
    public final static String KEY_NAME_SAVE = "save_fg";
    private static SaveSelectFGUtil saveSelectFGUtil;
    private final SharedPreferences msp;
    private SelectVideoFGVo s_FG =null;

    @SuppressLint("WrongConstant")
    public SaveSelectFGUtil(Context context) {
        msp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE | Context.MODE_APPEND);
    }

    public static synchronized void initSharedPreference(Context context) {
        if (saveSelectFGUtil == null) {
            saveSelectFGUtil = new SaveSelectFGUtil(context);
        }
    }

    public static synchronized SaveSelectFGUtil getInstance() {
        return saveSelectFGUtil;
    }
    public SharedPreferences getSharedPref()
    {
        return msp;
    }
    public  synchronized void putSelectFGVo(SelectVideoFGVo vo){
        SharedPreferences.Editor editor = msp.edit();
        String str="";
        try {
            str = SerializableUtil.obj2Str(vo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.putString(KEY_NAME_SAVE,str);
        editor.commit();
        s_FG = vo;
    }
   public synchronized SelectVideoFGVo getSaveFG(){
       if (s_FG == null)
       {
           s_FG = new SelectVideoFGVo();
           //获取序列化的数据
           String str = msp.getString(SaveSelectFGUtil.KEY_NAME_SAVE, "");
           try {
               Object obj = SerializableUtil.str2Obj(str);
               if(obj != null){
                   s_FG = (SelectVideoFGVo) obj;
               }
           } catch (StreamCorruptedException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return s_FG;
   }
   public  synchronized void delectUserVo(){
       SharedPreferences.Editor editor = msp.edit();
       editor.putString(KEY_NAME_SAVE,"");
       editor.commit();
       s_FG = null;
   }
}
