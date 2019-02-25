package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.contract.QRLoginContract;
import com.xuechuan.xcedu.mvp.contract.QRSureLoginContract;
import com.xuechuan.xcedu.mvp.model.QRLoginModel;
import com.xuechuan.xcedu.mvp.model.QRSureLoginModel;
import com.xuechuan.xcedu.mvp.presenter.QRLoginPresenter;
import com.xuechuan.xcedu.mvp.presenter.QRSureLoginPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ScanConfirmActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 扫描码确认界面
 * @author: L-BackPacker
 * @date: 2018/8/1 11:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/1
 */

public class ScanConfirmActivity extends BaseActivity implements View.OnClickListener, QRSureLoginContract.View {

    private Button mBtnQrLoginSure;
    private Button mBtnQrLoginCancel;
    private QRSureLoginPresenter mPresenter;
    /*
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scan_confirm);
            initView();
        }
    */
    private static String QRCOD = "qrcod";
    private String mCode;
    private Context mContext;
    private AlertDialog mAlertDialog;

    public static Intent newInstance(Context context, String qrcod) {
        Intent intent = new Intent(context, ScanConfirmActivity.class);
        intent.putExtra(QRCOD, qrcod);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scan_confirm);
        if (getIntent() != null) {
            mCode = getIntent().getStringExtra(QRCOD);
        }
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new QRSureLoginPresenter();
        mPresenter.initModelView(new QRSureLoginModel(), this);
    }


    private void initView() {
        mContext = this;
        mBtnQrLoginSure = (Button) findViewById(R.id.btn_qr_login_sure);
        mBtnQrLoginCancel = (Button) findViewById(R.id.btn_qr_login_cancel);
        mBtnQrLoginSure.setOnClickListener(this);
        mBtnQrLoginCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qr_login_sure:
                mAlertDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
                mPresenter.submitLoginSureRequest(mContext,mCode);
                break;
            case R.id.btn_qr_login_cancel:
                this.finish();
                break;
        }
    }



    @Override
    public void QrloginSureSuccess(String content) {
        dismissDialog(mAlertDialog);
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(content, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                finish();
            } else {
                T_ERROR(mContext, vo.getData().getMessage());
            }

        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void QrloginSureError(String msg) {
        dismissDialog(mAlertDialog);
        T_ERROR(mContext);
    }

}
