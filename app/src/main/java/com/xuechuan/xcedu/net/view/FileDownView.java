package com.xuechuan.xcedu.net.view;

import com.lzy.okgo.model.Progress;

import java.io.File;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net.view
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.05 上午 10:26
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface FileDownView {
    public void Start(Progress progress);

    public void OnProgerss(Progress progress);

    public void OnFinish(File file, Progress progress);

    public void OnRemove(Progress progress);

    public void OnErrorr(Progress msg);

}
