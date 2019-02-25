package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.mvp.contract.FileDownContract;
import com.xuechuan.xcedu.net.view.FileDownCallBackView;

import java.io.File;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.05 上午 10:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class FileDownPresenter implements FileDownContract.Presenter {
    FileDownContract.Model model;
    FileDownContract.View view;

    @Override
    public void initModelView(FileDownContract.Model model, FileDownContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void fileDownRequest(Context context,String path, String savePath) {
        model.fileDownRequest(context,path,savePath, new FileDownCallBackView() {
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
                view.OnErrorr(msg);
            }
        });
    }
}
