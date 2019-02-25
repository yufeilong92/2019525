package com.xuechuan.xcedu.utils;

import android.content.Context;

import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.DataMessageVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 判断当前网络工具
 * @author: L-BackPacker
 * @date: 2018/6/13 14:19
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetworkToolUtil {
    private Context mContext;
    private static NetworkToolUtil service;

    public NetworkToolUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static NetworkToolUtil getInstance(Context context) {
        if (service == null) {
            service = new NetworkToolUtil(context);
        }
        return service;
    }

    /**
     * 判断当前网络
     * @return
     */
    public String getNetWorkToolStauts() {
        boolean connect = Utils.isNetConnect(mContext);
        if (!connect) {//没有网络
            return DataMessageVo.NONETWORK;
        }
        boolean connect1 = Utils.isWifiConnect(mContext);
        if (connect1) {//wifi
            return DataMessageVo.WIFI;
        }//wifi
        return DataMessageVo.MONET;
    }
}
