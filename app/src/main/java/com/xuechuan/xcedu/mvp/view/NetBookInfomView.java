package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: 网课详情
 * @author: L-BackPacker
 * @date: 2018/5/14 16:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface NetBookInfomView {
    public void VideoInfomSuccess(String result);
    public void VideoInfomError(String msg);
    public void VideoInfomMoreSuccess(String result);
    public void VideoInfomMoreError(String msg);
}
