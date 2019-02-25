package com.xuechuan.xcedu.net.view;

import android.graphics.Bitmap;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net.view
 * @Description: 请求图片
 * @author: L-BackPacker
 * @date: 2018.11.23 上午 10:27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface BitmapCallBackView {
    public void onSuccess(Bitmap bitmap);

    public void onError(String msg);
}
