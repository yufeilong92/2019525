package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.view
 * @Description:
 * @author: L-BackPacker
 * @date: 2018/4/26 11:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface ColoctView {
    public void ErrorOrCollortNumberSuccess(String con);

    public void ErrorOrCollortNumberError(String con);
    public void BuySuccess(String con);
    public void BuyError(String con);

}
