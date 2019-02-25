package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: 支付接口
 * @author: L-BackPacker
 * @date: 2018/5/22 17:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface PayView {
    //下单
    public void SumbitFromSuccess(String con);
    public void SumbitFromError(String con);
    //支付成功
    public void SumbitPaySuccess(String con);
    public void SumbitPayError(String con);
    //功课
    public void BookIDSuccess(String con);
    public void BookIDError(String con);
}
