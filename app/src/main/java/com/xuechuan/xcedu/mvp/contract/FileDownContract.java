package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.view.FileDownCallBackView;

import java.io.File;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.05 上午 10:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface FileDownContract {
    interface Model {
        /**
         * @param context
         * @param savePath 保存的路径
         * @param view
         */
        public void fileDownRequest(Context context,String path, String savePath, FileDownCallBackView view);


    }

    interface View {
        public void Start(Progress progress);

        public void OnProgerss(Progress progress);

        public void OnFinish(File file, Progress progress);

        public void OnRemove(Progress progress);

        public void OnErrorr(Progress msg);



    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void fileDownRequest(Context context,String path, String savePath);
    }
}
