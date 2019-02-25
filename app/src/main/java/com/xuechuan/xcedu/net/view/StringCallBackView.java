package com.xuechuan.xcedu.net.view;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net.view
 * @Description: 请求结果回掉
 * @author: L-BackPacker
 * @date: 2018/4/12 12:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface StringCallBackView {
//    public void onSuccess(Response<String> response);
    public void onSuccess(String success);

//    public void onError(Response<String> response);
    public void onError(String msg);
}
