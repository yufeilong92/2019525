package com.xuechuan.xcedu.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/11 20:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyBigDecimal {
    public static String add(double d1, double d2) { // 进行加法运算
        String st;
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        st = df.format(b1.add(b2).doubleValue());
        return st;
    }
    public static double addI(double d1, double d2) { // 进行加法运算
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return Double.parseDouble(df.format(b1.add(b2).doubleValue()));
    }

    public static double sub(double d1, double d2) { // 进行减法运算

        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        BigDecimal decimal = b1.subtract(b2);
//        b1.subtract(b2).doubleValue()
        return   decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double mul(double d1, double d2) { // 进行乘法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double d1, double d2, int len) {// 进行除法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round(double d,int len) {     // 进行四舍五入操作
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
