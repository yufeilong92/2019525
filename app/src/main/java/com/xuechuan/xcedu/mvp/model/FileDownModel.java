package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.FileDownContract;
import com.xuechuan.xcedu.net.FileDownService;
import com.xuechuan.xcedu.net.view.FileDownCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.05 上午 10:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class FileDownModel implements FileDownContract.Model {
    @Override
    public void fileDownRequest(Context context, String path,String savePath, FileDownCallBackView view) {
        FileDownService service = FileDownService.get_Instance(context);
        service.requestFileDown( path,savePath, view);
    }
}
