package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: 错题和收藏题获取list
 * @author: L-BackPacker
 * @date: 2018/5/7 18:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface ErrOrColListView {
    public void ErrOrColListSuccess(String con);
    public void ErrOrColListError(String con);
}
