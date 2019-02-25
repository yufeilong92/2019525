package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description: 网课评价
 * @author: L-BackPacker
 * @date: 2018/5/21 14:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface NetVideoEvalueInfomView {
    public void EvalueOneSuccess(String con);
    public void EvalueOneError(String rror);
    public void EvalueMoreSuccess(String con);
    public void EvalueMoreError(String rror);
    public void SubmitEvalueSuccess(String con);
    public void SubmitEvalueError(String con);

}

