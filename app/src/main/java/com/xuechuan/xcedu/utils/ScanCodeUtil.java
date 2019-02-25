package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.google.android.exoplayer.C;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.ChangeInfoContract;
import com.xuechuan.xcedu.mvp.contract.QRLoginContract;
import com.xuechuan.xcedu.mvp.model.ChangeInfoModel;
import com.xuechuan.xcedu.mvp.model.ExchangeModelImpl;
import com.xuechuan.xcedu.mvp.model.QRLoginModel;
import com.xuechuan.xcedu.mvp.presenter.ChangeInfoPresenter;
import com.xuechuan.xcedu.mvp.presenter.ExchangePresenter;
import com.xuechuan.xcedu.mvp.presenter.QRLoginPresenter;
import com.xuechuan.xcedu.mvp.view.ExchangeView;
import com.xuechuan.xcedu.ui.ScanConfirmActivity;
import com.xuechuan.xcedu.ui.me.ExchangeActivity;
import com.xuechuan.xcedu.vo.GenuienVo;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: 1.0.8
 * @Package com.xuechuan.xcedu.utils
 * @Description: 扫码工具类
 * @author: L-BackPacker
 * @date: 2018/8/24 10:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ScanCodeUtil {
    private Context mContext;
    private static ScanCodeUtil service;


    public interface DONEXT {
        public void doDone(Context content,String code);
    }

    public ScanCodeUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static ScanCodeUtil getInstance(Context context) {
        if (service == null) {
            service = new ScanCodeUtil(context);
        }
        return service;
    }

    public void doIntent(Context context,String make, String code) {
        if (make.equalsIgnoreCase(DataMessageVo.LOGINTAG)) {//扫码登录
            doIntent(new login(),context, code);
        }
        if (make.equalsIgnoreCase(DataMessageVo.QRTAG)) {//扫码验证
            doIntent(new QrIntent(),context, code);
        }
        if (make.equalsIgnoreCase(DataMessageVo.ADDVALUEHTTP)) {//增值服务
            doIntent(new AddValue(),context, code);
        }
    }


    private void doIntent(DONEXT donext,Context context, String code) {
        donext.doDone(context,code);
    }

    public class login implements DONEXT, QRLoginContract.View {

        private AlertDialog mAlertDialog;
        private String mCode = null;
        private Context mContext;

        @Override
        public void doDone(Context context,String code) {
            mCode = code;
            mContext=context;
            QRLoginPresenter mQrPresenter = new QRLoginPresenter();
            mQrPresenter.initModelView(new QRLoginModel(), this);
//            mAlertDialog = DialogUtil.showDialog(context, "", getStrWithId(context, R.string.loading));
            mQrPresenter.submitLoginRequest(context, code);
        }

        @Override
        public void QrloginSuccess(String content) {
            dialogDimiss(mAlertDialog);
            Gson gson = new Gson();
            ResultVo vo = gson.fromJson(content, ResultVo.class);
            if (vo.getStatus().getCode() == 200) {
                if (vo.getData().getStatusX() == 1) {
                    Intent intent = ScanConfirmActivity.newInstance(mContext, mCode);
                    mContext.startActivity(intent);
                    mCode = null;
                } else {
                    T_ERROR(mContext, vo.getData().getMessage());
                }

            } else {
                T_ERROR(mContext);
            }
        }

        @Override
        public void QrloginError(String msg) {
            dialogDimiss(mAlertDialog);
            T_ERROR(mContext);
        }
    }

    public class QrIntent implements DONEXT, ExchangeView {

        private AlertDialog mAlertDialog;
        private String mCode = null;
        private Context mContext;
        @Override
        public void doDone(Context context,String code) {
            mContext=context;
            ExchangePresenter mExchangPresenter = new ExchangePresenter(new ExchangeModelImpl(), this);
            mCode = code;
//            mAlertDialog = DialogUtil.showDialog(context, "", getStrWithId(context, R.string.loading));
            mExchangPresenter.requestExchangeWithCode(context, code);

        }

        @Override
        public void ExchangeSuccess(String com) {
            dialogDimiss(mAlertDialog);
            Gson gson = new Gson();
            GenuienVo vo = gson.fromJson(com, GenuienVo.class);
            if (vo.getStatus().getCode() == 200) {
                if (vo.getData().getStatusX() == 1) {
                    GenuienVo.DataBean data = vo.getData();
                    Intent intent = ExchangeActivity.newInstance(mContext, true, data.getQuerynum(), data.isHaveexchange()
                            , mCode, data.getExchangeinfo(),DataMessageVo.QRTAG);
                    mContext.startActivity(intent);
                    mCode = null;

                } else {
                    Intent intent = ExchangeActivity.newInstance(mContext, false, 0, false, null,
                            null,DataMessageVo.QRTAG);
                    mContext.startActivity(intent);
                    mCode = null;
                }

            } else {
                T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
                L.e(vo.getStatus().getMessage());
            }
        }

        @Override
        public void ExchangeError(String com) {
            dialogDimiss(mAlertDialog);
            T_ERROR(mContext);
        }
    }

    public class AddValue implements DONEXT, ChangeInfoContract.View {
        private AlertDialog mAlertDialog;
        private String mCode = null;
        private Context mContext;

        @Override
        public void doDone(Context context,String code) {
            mCode = code;
            mContext= context;
            ChangeInfoPresenter mAddValuePresenter = new ChangeInfoPresenter();
            mAddValuePresenter.initModelView(new ChangeInfoModel(), this);
//            mAlertDialog = DialogUtil.showDialog(context, "", getStrWithId(context, R.string.loading));
            mAddValuePresenter.requestChangeCode(context, code);
        }

        @Override
        public void ChangeInfomSuccess(String result) {
            dialogDimiss(mAlertDialog);
            Gson gson = new Gson();
            GenuienVo vo = gson.fromJson(result, GenuienVo.class);
            if (vo.getStatus().getCode() == 200) {
                if (vo.getData().getStatusX() == 1) {
                    GenuienVo.DataBean data = vo.getData();
                    Intent intent = ExchangeActivity.newInstance(mContext, true, data.getQuerynum(), data.isHaveexchange()
                            , mCode, data.getExchangeinfo(),DataMessageVo.ADDVALUEHTTP);
                    intent.putExtra(ExchangeActivity.CSTR_EXTRA_TITLE_STR,"兑换增值服务");
                    mContext.startActivity(intent);
                    mCode = null;

                } else {
                    /*Intent intent = ExchangeActivity.newInstance(mContext, false, 0, false, null, null,DataMessageVo.ADDVALUEHTTP);
                    intent.putExtra(ExchangeActivity.CSTR_EXTRA_TITLE_STR,"兑换增值服务");
                    mContext.startActivity(intent);*/
                    T.showToast(mContext,vo.getData().getMessage());
                    mCode = null;
                }

            } else {
                T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
                L.e(vo.getStatus().getMessage());
            }
        }

        @Override
        public void ChangeInfomError(String msg) {
            dialogDimiss(mAlertDialog);
            T_ERROR(mContext);
        }


    }

    private void dialogDimiss(AlertDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void T_ERROR(Context context) {
        T.showToast(context, context.getResources().getString(R.string.net_error));
    }

    private void T_ERROR(Context context, String Msg) {
        T.showToast(context, Msg);
    }

    private String getStrWithId(Context mContext, int str) {
        return mContext.getResources().getString(str);
    }
}
