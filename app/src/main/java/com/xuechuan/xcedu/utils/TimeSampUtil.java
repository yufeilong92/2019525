package com.xuechuan.xcedu.utils;

import java.math.BigDecimal;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 时间计算类
 * @author: L-BackPacker
 * @date: 2018/4/10 10:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TimeSampUtil {
    /**
     * 获取当前时间戳
     *
     * @param date 当时时间
     * @return
     */
    public static String getStringTimeStamp(String date) {
        if (StringUtil.isEmpty(date)){
            return "";
        }
        String commonDateStr = TimeUtil.getCommonDateStr(date);
        long nowTime = TimeUtil.intervalNow(commonDateStr);
        String result = "";
        long ms = nowTime / 1000;
        BigDecimal decimal = new BigDecimal(ms).setScale(0, BigDecimal.ROUND_HALF_UP);
        //秒数
        long longNow = decimal.longValue();
        long temp = 0;
        if (longNow < 60) {
            result = "刚刚";
        } else if ((temp = longNow / 60) < 60) {
            result = temp + "分前";
        } else if ((temp = temp / 60) < 24) {
            result = temp + "小时前";
        } else if ((temp = temp / 24) < 30) {
            result = temp + "天前";
        } else if ((temp = temp / 30) < 12) {
            result = temp + "月前";
        } else {
            temp = temp / 12;
            result = temp + "年前";
        }
        return result;
    }

}
