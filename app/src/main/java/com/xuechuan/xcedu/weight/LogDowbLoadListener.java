package com.xuechuan.xcedu.weight;

import android.util.Log;

import com.lzy.okgo.model.Progress;
import com.lzy.okserver.download.DownloadListener;
import com.xuechuan.xcedu.net.view.FileDownView;

import java.io.File;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.weight
 * @Description: 下载监听
 * @author: L-BackPacker
 * @date: 2018.12.05 上午 10:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class LogDowbLoadListener extends DownloadListener {
    private FileDownView view;

    public LogDowbLoadListener(FileDownView view) {
        super("FileDown");
        this.view = view;
    }

    @Override
    public void onStart(Progress progress) {
        view.Start(progress);
    }

    @Override
    public void onProgress(Progress progress) {
        view.OnProgerss(progress);
    }

    @Override
    public void onError(Progress progress) {
        view.OnErrorr(progress);
    }

    @Override
    public void onFinish(File file, Progress progress) {
        view.OnFinish(file, progress);
    }

    @Override
    public void onRemove(Progress progress) {
        view.OnRemove(progress);
    }
}
