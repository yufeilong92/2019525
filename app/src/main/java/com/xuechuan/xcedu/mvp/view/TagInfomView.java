package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/13 13:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface TagInfomView {
    //标签下内容
    public void ArticleTagOneConSuccess(String con);
    public void ArticleTagOneConError(String con);
    //标签下内容
    public void ArticleTagMoreConSuccess(String con);
    public void ArticleTagMoreConError(String con);
}
