package com.xuechuan.xcedu.net;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.FileDownCallBackView;
import com.xuechuan.xcedu.net.view.FileDownView;

import java.io.File;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.05 上午 10:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class FileDownService extends BaseHttpServcie {

    private static volatile FileDownService _singleton;
    private Context mContext;

    private FileDownService(Context context) {
        this.mContext = context;
    }

    public static FileDownService get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (FileDownService.class) {
                if (_singleton == null) {
                    _singleton = new FileDownService(context);
                }
            }
        }
        return _singleton;
    }

    public void requestFileDown(String path,String savePath, final FileDownCallBackView view) {
        sendFileDown(mContext, path,savePath, new FileDownView() {
            @Override
            public void Start(Progress progress) {
                view.Start(progress);
            }

            @Override
            public void OnProgerss(Progress progress) {
                view.OnProgerss(progress);
            }

            @Override
            public void OnFinish(File file, Progress progress) {
                view.OnFinish(file, progress);
            }

            @Override
            public void OnRemove(Progress progress) {
                view.OnRemove(progress);
            }

            @Override
            public void OnErrorr(Progress msg) {
                view.OnRemove(msg);
            }
        });

    }

}
