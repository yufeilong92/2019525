package com.xuechuan.xcedu.net.view;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net.view
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/28 15:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface StringCallBackUpView {
    public void onSuccess(String success);

    public void onError(String msg);

    public void onUpProgree(Progress progress);
}
