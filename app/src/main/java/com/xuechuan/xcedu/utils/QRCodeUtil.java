package com.xuechuan.xcedu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.ui.SecondActivity;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 二维码扫描工具类
 * @author: L-BackPacker
 * @date: 2018/7/30 9:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QRCodeUtil {
    /**
     * 扫描码
     */
    private static String CODEVALUE = "codevalue";
    private String mCode;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    private Context mContext;
    private static QRCodeUtil service;

    public QRCodeUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static QRCodeUtil getInstance(Context context) {
        if (service == null) {
            service = new QRCodeUtil(context);
        }

        return service;
    }

    /**
     * 扫描结果
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据类型
     */
    public void doResueltIntent(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    T.showToast(mContext, "解析"+result);
                    if (qrCodeViewInface == null) return;
                    if (result.contains(DataMessageVo.LOGINTAG)) {
                        Uri parse = Uri.parse(result);
                        String login = parse.getQueryParameter(DataMessageVo.LOGINKEY);
                        qrCodeViewInface.loginResultSuccess(login);
                        return;
                    }
                    if (result.contains(DataMessageVo.QRTAG)) {
                        Uri parse = Uri.parse(result);
                        String code = parse.getQueryParameter(DataMessageVo.QRKEY);
                        qrCodeViewInface.codeResultSuccess(code);
                        return;
                    }
                    if (result.contains(DataMessageVo.ADDVALUEHTTP)) {
                        Uri parse = Uri.parse(result);
                        String code = parse.getQueryParameter(DataMessageVo.QRKEY);
                        qrCodeViewInface.addValueResultSuccess(code);
                        return;
                    }
                    T.showToast(mContext, result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    T.showToast(mContext, "解析二维码失败");
                }
            }
        }
    }

    public void doIntentScanCode(Activity activity) {
        Intent star = new Intent(mContext, SecondActivity.class);
        activity.startActivityForResult(star, REQUEST_CODE);
    }

    public interface qrCodeView {
        public void codeResultSuccess(String code);

        public void loginResultSuccess(String code);

        public void addValueResultSuccess(String code);
    }

    public qrCodeView qrCodeViewInface;

    public QRCodeUtil.qrCodeView getQrCodeView() {
        return qrCodeViewInface;
    }

    public void setQrCodeView(QRCodeUtil.qrCodeView qrCodeView) {
        this.qrCodeViewInface = qrCodeView;
    }
}
