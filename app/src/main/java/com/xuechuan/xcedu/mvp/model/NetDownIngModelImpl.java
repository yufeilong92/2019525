package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.NetService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 网课下载视频提交
 * @author: L-BackPacker
 * @date: 2018/5/21 9:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetDownIngModelImpl implements NetDownIngModel {

    @Override
    public void submitBookDownProgress(Context context, String videoOid, String clssId, final RequestResulteView view) {
        NetService service = new NetService(context);
        service.submitDownLoadVideo(videoOid, clssId, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                view.success(response);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }
        });
    }
}
