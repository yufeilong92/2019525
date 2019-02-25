package com.xuechuan.xcedu.net;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/16 17:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class WeiXinLoginSercvice extends BaseHttpServcie {

    private static WeiXinLoginSercvice sercvice;
    private Context mContext;

    public WeiXinLoginSercvice(Context mContext) {
        this.mContext = mContext;
    }

    public static WeiXinLoginSercvice getInstance(Context context) {
        if (sercvice == null)
            sercvice = new WeiXinLoginSercvice(context);
        return sercvice;
    }

    public void requestWeiCode( String code, StringCallBackView backView) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("imei", Utils.getIMEI(mContext));
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        String weixin = mContext.getResources().getString(R.string.http_url_weixin);
        String url = getUrl(mContext, R.string.http_url_weixin);
        requestHttpServciePost(mContext, url, jsonObject, false,backView);

    }
}
