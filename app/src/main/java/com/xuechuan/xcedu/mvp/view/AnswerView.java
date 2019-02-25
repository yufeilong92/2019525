package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: 题干
 * @author: L-BackPacker
 * @date: 2018/5/2 11:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface AnswerView {
    //题干类型
    public void TextSuccess(String con);

    public void TextError(String con);

    //题干详情
    public void TextDetailSuccess(String con);

    public void TextDetailError(String con);

    //提交结果
    public void SumbitCollectSuccess(String con);

    public void SumbitCollectError(String con);

    //做题结果
    public void DoResultSuccess(String con);

    public void DoResultError(String con);

    //移除错题
    public void WoringSuccess(String con);

    public void WoringError(String con);


}
