package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: 搜索界面
 * @author: L-BackPacker
 * @date: 2018/5/8 17:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface SearchView {
    //热词
    public void HostSuccess(String cont);

    public void HostError(String cont);

    //结果
    public void ResultSuccess(String cont);

    public void ResultError(String cont);

    //结果
    public void ResultMoreSuccess(String cont);

    public void ResultMoreError(String cont);
}
