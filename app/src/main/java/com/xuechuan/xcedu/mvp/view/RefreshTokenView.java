package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/9 14:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface RefreshTokenView {
    public void TokenSuccess(String con);
    public void TokenError(String con);
}
