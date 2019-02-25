package com.xuechuan.xcedu.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.BitmapCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: 图片请求
 * @author: L-BackPacker
 * @date: 2018.11.23 上午 10:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BitmapService extends BaseHttpServcie {
    private static volatile BitmapService _singleton;
    private Context mContext;

    private BitmapService(Context context) {
        this.mContext = context;
    }

    public static BitmapService get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (BitmapService.class) {
                if (_singleton == null) {
                    _singleton = new BitmapService(context);
                }
            }
        }
        return _singleton;
    }

    public interface BitmapInfaceCallBack {
        public void onSuccess(Bitmap bitmap);

        public void onError(String msg);
    }

    /**
     * 请求图标
     * @param path
     * @param callBackView
     */
    public void requestBitmap( String path, final BitmapInfaceCallBack callBackView) {
        requstBitmap( mContext,path, new BitmapCallBackView() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                callBackView.onSuccess(bitmap);
            }

            @Override
            public void onError(String msg) {
                callBackView.onError(msg);
            }
        });
    }

}
