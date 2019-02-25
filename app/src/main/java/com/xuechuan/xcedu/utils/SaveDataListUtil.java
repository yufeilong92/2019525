package com.xuechuan.xcedu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.xuechuan.xcedu.vo.CaseCardVo;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 保存用户已做过的题数据
 * @author: L-BackPacker
 * @date: 2018/4/27 13:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SaveDataListUtil {
    // 用户名key
    public final static String KEY_NAME = "54684848848416184818a";
    public final static String KEY_LEVEL = "KEY_LEVEL";
    private static SaveDataListUtil s_SharedTextListUtil;
    private static List<CaseCardVo > s_User = null;
    private SharedPreferences msp;
    // 初始化，一般在应用启动之后就要初始化
    public static synchronized void initSharedPreference(Context context)
    {
        if (s_SharedTextListUtil == null)
        {
            s_SharedTextListUtil = new SaveDataListUtil(context);
        }
    }
    /**
     * 获取唯一的instance
     *
     * @return
     */
    public static synchronized SaveDataListUtil getInstance()
    {
        return s_SharedTextListUtil;
    }
    @SuppressLint("WrongConstant")
    public SaveDataListUtil(Context context)
    {
        msp = context.getSharedPreferences("SharedPreUtil",
                Context.MODE_PRIVATE | Context.MODE_APPEND);
    }
    public SharedPreferences getSharedPref()
    {
        return msp;
    }
    public synchronized void putCaseCardLists(List<CaseCardVo>user)
    {
        SharedPreferences.Editor editor = msp.edit();
        String str="";
        try {
            str = SerializableUtil.list2String(user);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        editor.putString(KEY_NAME,str);
        editor.commit();
        s_User = user;
    }
    public synchronized List <CaseCardVo >getCaseCardVo()
    {
        if (s_User == null)
        {
            s_User = new ArrayList();
            //获取序列化的数据
            String str = msp.getString(SaveDataListUtil.KEY_NAME, "");
            try {
                List<CaseCardVo> list = SerializableUtil.string2List(str);
                if(list != null){
                    s_User = (List<CaseCardVo>) list;
                }
            } catch (StreamCorruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return s_User;
    }
    public synchronized void DeleteUser()
    {
        SharedPreferences.Editor editor = msp.edit();
        editor.putString(KEY_NAME,"");
        editor.commit();
        s_User = null;
    }
}
