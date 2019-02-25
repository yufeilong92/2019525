package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.easefun.polyvsdk.danmaku.StringUtils;
import com.xuechuan.xcedu.base.BasePresenter;
import com.xuechuan.xcedu.mvp.model.NetDownIngModel;
import com.xuechuan.xcedu.mvp.view.NetDownIngView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 网课下载视频
 * @author: L-BackPacker
 * @date: 2018/5/21 9:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetDownIngPresenter  extends BasePresenter{

    private NetDownIngModel model;
    private NetDownIngView view;

    public NetDownIngPresenter(NetDownIngModel model, NetDownIngView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 提交网课进度
     * @param context
     * @param videoOid
     * @param classid
     */
    public void submitVideo(Context context, String videoOid, String classid) {
        if (StringUtils.isEmpty(videoOid) && StringUtils.isEmpty(classid)) {
            return;
        }
        model.submitBookDownProgress(context, videoOid, classid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.submitSuccess(result);
            }
            @Override
            public void error(String result) {
                view.submitError(result);
            }
        });
    }
}
