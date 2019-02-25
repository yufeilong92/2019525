package com.xuechuan.xcedu.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.wxapi
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/16 15:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static IWXAPI api;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        api = WXAPIFactory.createWXAPI(this,
                DataMessageVo.APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
//        L.e(resp.errStr+"//"+resp.errCode+"//"+resp.transaction);
        SendMessageToWX res;
        if (resp instanceof SendMessageToWX.Resp) {
            finish();
            return;
        }
        String code = null;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK://用户同意
                SendAuth.Resp sendResp = (SendAuth.Resp) resp;
//                code = sendResp.code;
//                L.e(code);
                sendBroadcastData(sendResp);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户拒绝
                T.showToast(mContext, "用户拒绝");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户取消
                T.showToast(mContext, "用户取消");
                break;

            default:
                break;
        }
        finish();
    }

    private void sendBroadcastData(SendAuth.Resp sendResp) {
        Intent intent = new Intent();
        intent.setAction(DataMessageVo.WEI_LOGIN_ACTION);
        intent.putExtra(DataMessageVo.WEICODE,sendResp.code);
        intent.putExtra(DataMessageVo.WEISTATE,sendResp.state);
        this.sendBroadcast(intent);
    }
}