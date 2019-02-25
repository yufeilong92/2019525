package com.xuechuan.xcedu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.xuechuan.xcedu.vo.UserInfomVo;

import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 保存最后用户id
 * @author: L-BackPacker
 * @date: 2018/4/28 8:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SaveUserInfomUtil {
    // 用户名key
    public final static String KEY_NAME = "userInfom";
    private static SaveUserInfomUtil sharedUserUtils;
    private final SharedPreferences msp;
    private UserInfomVo s_User =null;

    @SuppressLint("WrongConstant")
    public SaveUserInfomUtil(Context context) {
        msp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE | Context.MODE_APPEND);
    }

    public static synchronized void initSharedPreference(Context context) {
        if (sharedUserUtils == null) {
            sharedUserUtils = new SaveUserInfomUtil(context);
        }
    }
    public static synchronized SaveUserInfomUtil getInstance() {
        return sharedUserUtils;
    }
    public SharedPreferences getSharedPref()
    {
        return msp;
    }
    public  synchronized void putUserInfom(UserInfomVo  vo){
        SharedPreferences.Editor editor = msp.edit();
        String str="";
        try {
            str = SerializableUtil.obj2Str(vo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.putString(KEY_NAME,str);
        editor.commit();
        s_User = vo;
    }
   public synchronized UserInfomVo  getUserInfom(){
       if (s_User == null)
       {
           s_User = new UserInfomVo();
           //获取序列化的数据
           String str = msp.getString(SaveUserInfomUtil.KEY_NAME, "");
           try {
               Object obj = SerializableUtil.str2Obj(str);
               if(obj != null){
                   s_User = (UserInfomVo) obj;
               }
           } catch (StreamCorruptedException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return s_User;
   }
   public  synchronized void delectUserInfom(){
       SharedPreferences.Editor editor = msp.edit();
       editor.putString(KEY_NAME,"");
       editor.commit();
       s_User = null;
   }
}
