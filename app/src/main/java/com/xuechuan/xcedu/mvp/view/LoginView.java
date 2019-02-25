package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/2 12:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface LoginView {
    public void WeiXinLoginSuccess(String con);

    public void WeiXinLoginError(String error);
    public void LoginSuccess(String con);
    public void LoginError(String con);

}
