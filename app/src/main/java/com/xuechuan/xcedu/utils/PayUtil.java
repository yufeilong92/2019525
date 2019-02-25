package com.xuechuan.xcedu.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.VideoOrderContract;
import com.xuechuan.xcedu.mvp.model.PayModelImpl;
import com.xuechuan.xcedu.mvp.model.VideoOrderModel;
import com.xuechuan.xcedu.mvp.presenter.PayPresenter;
import com.xuechuan.xcedu.mvp.presenter.VideoOrderPresenter;
import com.xuechuan.xcedu.mvp.view.PayUtilView;
import com.xuechuan.xcedu.mvp.view.PayView;
import com.xuechuan.xcedu.ui.BuyResultActivity;
import com.xuechuan.xcedu.vo.BuyFromResultVo;
import com.xuechuan.xcedu.vo.BuyFromVo;
import com.xuechuan.xcedu.vo.BuyZfbResultVo;
import com.xuechuan.xcedu.vo.PayResult;
import com.xuechuan.xcedu.vo.WechatsignBeanVo;

import java.util.List;
import java.util.Map;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 付款工具
 * @author: L-BackPacker
 * @date: 2018/5/26 17:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PayUtil implements PayView, VideoOrderContract.View {

    private final IWXAPI api;
    private final PayPresenter payPresenter;
    public static final String WEIXIN = "weixin";
    public static final String ZFB = "zfb";
    private final Activity mClass;
    private Context mContext;
    private static PayUtil service;
    private String payType;
    private PayUtilView mPayUtilsView;
    /**
     * 支付包结果
     */
    private static final int SDK_PAY_FLAG = 1;
    private AlertDialog dialog;
    private final VideoOrderPresenter mVideoPresenter;

    public PayUtil(Context mContext, Activity classa) {
        this.mContext = mContext;
        this.mClass = classa;
        api = WXAPIFactory.createWXAPI(mContext, DataMessageVo.APP_ID);
        api.registerApp(DataMessageVo.APP_ID);
        payPresenter = new PayPresenter(new PayModelImpl(), this);
        mVideoPresenter = new VideoOrderPresenter();
        mVideoPresenter.initModelView(new VideoOrderModel(),this);
    }

    public static PayUtil getInstance(Context context, Activity clal) {
        if (service == null) {
            service = new PayUtil(context, clal);
        }
        return service;
    }

    public void init(PayUtilView view) {
        this.mPayUtilsView = view;
    }

    /**
     * 提交订单
     *
     * @param type
     * @param price
     * @param list
     * @param remark
     */
    public void Submitfrom(String type, String price, List<Integer> list, String remark) {
        payType = type;
        payPresenter.submitPayFrom(mContext, price, list, "android_app", remark);
    }

    /**
     * 视频提交订单
     *
     * @param type
     * @param price
     * @param list
     * @param remark
     */
    public void SubmitVideofrom(String type, String price, List<Integer> list, String remark, int addressid) {
        payType = type;
        mVideoPresenter.sumbitPayFrom(mContext, price, list, "android_app", remark, addressid);
    }

    /**
     * 提交订单列表提交支付
     *
     * @param type
     */
    public void SubmitfromPay(String type, String ordernum) {
        payType = type;
        if (payType.equals(ZFB)) {//支付包
            payPresenter.submitPay(mContext, ordernum, DataMessageVo.PAYTYPE_ZFB);
        } else if (payType.equals(WEIXIN)) {//微信
            payPresenter.submitPay(mContext, ordernum, DataMessageVo.PAYTYPE_WEIXIN);
        }
    }

    public void showDiaolog(AlertDialog dialog) {
        this.dialog = dialog;
    }

    @Override

    public void SumbitFromSuccess(String con) {
        Gson gson = new Gson();
        BuyFromVo vo = gson.fromJson(con, BuyFromVo.class);
        if (vo.getStatus().getCode() == 200) {
            String orderid = vo.getData().getOrderid();
            if (payType.equals(ZFB)) {//支付包
                payPresenter.submitPay(mContext, orderid, DataMessageVo.PAYTYPE_ZFB);
            } else if (payType.equals(WEIXIN)) {//微信
                payPresenter.submitPay(mContext, orderid, DataMessageVo.PAYTYPE_WEIXIN);
            }
        } else {
            if (mPayUtilsView != null)
                mPayUtilsView.PayError(payType);
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }

    }

    @Override
    public void SumbitFromError(String con) {
        L.e(con);
        if (mPayUtilsView != null)
            mPayUtilsView.Dialog();
        if (mPayUtilsView != null)
            mPayUtilsView.PayError(payType);
    }

    @Override
    public void SumbitPaySuccess(String con) {
        if (mPayUtilsView != null)
            mPayUtilsView.Dialog();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Gson gson = new Gson();
        if (payType.equals(WEIXIN)) {//微信
            BuyFromResultVo vo = gson.fromJson(con, BuyFromResultVo.class);
            if (vo.getStatus().getCode() == 200) {
                WechatsignBeanVo wechatsign = vo.getData().getWechatsign();
                requestWeiXinPay(wechatsign);
            } else {
                L.e(vo.getStatus().getMessage());
            }

        } else if (payType.equals(ZFB)) {//支付包
            BuyZfbResultVo vo = gson.fromJson(con, BuyZfbResultVo.class);
            if (vo.getStatus().getCode() == 200) {
                BuyZfbResultVo.DataBean data = vo.getData();
                requestZFBPay(data);
            } else {
                L.e(vo.getStatus().getMessage());
            }
        }


    }

    @Override
    public void SumbitPayError(String con) {
        if (mPayUtilsView != null)
            mPayUtilsView.PayError(payType);
    }

    @Override
    public void BookIDSuccess(String con) {

    }

    @Override
    public void BookIDError(String con) {

    }

    private void requestWeiXinPay(final WechatsignBeanVo wechatsign) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayReq request = new PayReq();
                request.appId = DataMessageVo.APP_ID;
                request.partnerId = wechatsign.getPartnerid();
                request.prepayId = wechatsign.getPrepayid();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = wechatsign.getNoncestr();
                request.timeStamp = wechatsign.getTimespan();
                request.sign = wechatsign.getSign();
                api.sendReq(request);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = BuyResultActivity.newInstance(mContext, BuyResultActivity.STATUSSUCCESS);
                        intent.putExtra(BuyResultActivity.CSTR_EXTRA_TITLE_STR, mContext.getResources().getString(R.string.buyStauts));
                        mContext.startActivity(intent);
                        if (mPayUtilsView != null)
                            mPayUtilsView.PaySuccess(payType);
                        Toast.makeText(mClass, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Intent intent = BuyResultActivity.newInstance(mContext, BuyResultActivity.STATUSERROR);
                        intent.putExtra(BuyResultActivity.CSTR_EXTRA_TITLE_STR, mContext.getResources().getString(R.string.buyStauts));
                        mContext.startActivity(intent);
                        if (mPayUtilsView != null)
                            mPayUtilsView.PayError(payType);
                        Toast.makeText(mClass, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void requestZFBPay(BuyZfbResultVo.DataBean zfb) {
        final String orderInfo = zfb.getOrderstring();   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mClass);
                Map<String, String> map = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = map;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    @Override
    public void paySuccess(String result) {
        Gson gson = new Gson();
        BuyFromVo vo = gson.fromJson(result, BuyFromVo.class);
        if (vo.getStatus().getCode() == 200) {
            String orderid = vo.getData().getOrderid();
            if (payType.equals(ZFB)) {//支付包
                payPresenter.submitPay(mContext, orderid, DataMessageVo.PAYTYPE_ZFB);
            } else if (payType.equals(WEIXIN)) {//微信
                payPresenter.submitPay(mContext, orderid, DataMessageVo.PAYTYPE_WEIXIN);
            }
        } else {
            if (mPayUtilsView != null)
                mPayUtilsView.PayError(payType);
//            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void payError(String msg) {
        if (mPayUtilsView != null)
            mPayUtilsView.Dialog();
        if (mPayUtilsView != null)
            mPayUtilsView.PayError(payType);
    }
}
