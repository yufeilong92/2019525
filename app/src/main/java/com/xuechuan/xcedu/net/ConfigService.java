package com.xuechuan.xcedu.net;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.26 下午 5:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ConfigService extends BaseHttpServcie {
    private static volatile ConfigService _singleton;
    private Context mContext;

    private ConfigService(Context context) {
        this.mContext = context;
    }

    public static ConfigService get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (ConfigService.class) {
                if (_singleton == null) {
                    _singleton = new ConfigService(context);
                }
            }
        }
        return _singleton;
    }

    /**
     * 请求配置
     *
     * @param backView
     */
    public void requestConfig( StringCallBackView backView) {
        String url = getUrl(R.string.http_setting);
        sendRequestConfig(mContext, url, backView);
    }
    /**
     * 获取所有问答模块问题标签
     *
     * @param backView
     */
    public void requestProblemtags( StringCallBackView backView) {
        String url = getUrl(R.string.http_app_answer_tag);
        sendRequestConfig(mContext, url, backView);
    }

    private String getUrl(int str) {
        return mContext.getResources().getString(str);
    }
}
