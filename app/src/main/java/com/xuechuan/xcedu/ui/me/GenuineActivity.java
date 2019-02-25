package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
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
import com.xuechuan.xcedu.ui.SecondActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.QRCodeUtil;
import com.xuechuan.xcedu.utils.ScanCodeUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.GenuienVo;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: GenuineActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 正版验证
 * @author: L-BackPacker
 * @date: 2018/5/22 12:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class GenuineActivity extends BaseActivity implements View.OnClickListener, ExchangeView, QRCodeUtil.qrCodeView, QRLoginContract.View, ChangeInfoContract.View {

    private EditText mEtMGCode;
    private Button mBtnMGValue;
    private Context mContext;
    private ExchangePresenter mPresenter;
    private AlertDialog dialog;
    private ImageView mIvCodeTwoqr;
    /**
     * 扫描码
     */
    private static String CODEVALUE = "codevalue";
    private String mCode;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    private String code;
    private QRCodeUtil mQrCodeUtil;
    private QRLoginPresenter mQrLoginPresenter;
    private ChangeInfoPresenter mAddValuePresenter;
    private ScanCodeUtil mScanCodeUtil;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genuine);
        initView();
    }
*/

    public static Intent newInstance(Context context, String codevalue) {
        Intent intent = new Intent(context, GenuineActivity.class);
        intent.putExtra(CODEVALUE, codevalue);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_genuine);
        if (getIntent() != null) {
            mCode = getIntent().getStringExtra(CODEVALUE);
        }
        initView();
        initData();
    }

    private void initData() {
        mScanCodeUtil = ScanCodeUtil.getInstance(mContext);
        mQrLoginPresenter = new QRLoginPresenter();
        mQrLoginPresenter.initModelView(new QRLoginModel(), this);
        mQrCodeUtil = QRCodeUtil.getInstance(mContext);
        mQrCodeUtil.setQrCodeView(this);
        mPresenter = new ExchangePresenter(new ExchangeModelImpl(), this);
        mAddValuePresenter = new ChangeInfoPresenter();
        mAddValuePresenter.initModelView(new ChangeInfoModel(), this);
        if (StringUtil.isEmpty(mCode)) return;
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        mPresenter.requestExchangeWithCode(mContext, mCode);
    }

    private void initView() {
        mEtMGCode = (EditText) findViewById(R.id.et_m_g_code);
        mBtnMGValue = (Button) findViewById(R.id.btn_m_g_value);
        mBtnMGValue.setOnClickListener(this);
        mContext = this;
        mIvCodeTwoqr = (ImageView) findViewById(R.id.iv_code_twoqr);
        mIvCodeTwoqr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_m_g_value:
                mCode = getTextStr(mEtMGCode);
                if (TextUtils.isEmpty(mCode)) {
                    T.showToast(mContext, R.string.code_empty1);
                    return;
                }
                submit(mCode);
                break;
            case R.id.iv_code_twoqr:
                mQrCodeUtil.doIntentScanCode(GenuineActivity.this);
/*                Intent star = new Intent(mContext, SecondActivity.class);
                startActivityForResult(star, REQUEST_CODE);*/

                break;
        }
    }

    private void submit(String code) {
        mScanCodeUtil.doIntent(mContext,DataMessageVo.QRTAG,code);
/*        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        mPresenter.requestExchangeWithCode(mContext, code);*/
    }


    @Override
    public void ExchangeSuccess(String com) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Gson gson = new Gson();
        GenuienVo vo = gson.fromJson(com, GenuienVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                GenuienVo.DataBean data = vo.getData();
                finish();
                Intent intent = ExchangeActivity.newInstance(mContext, true, data.getQuerynum(), data.isHaveexchange()
                        , mCode, data.getExchangeinfo(),DataMessageVo.QRTAG);
                startActivity(intent);
                mCode = null;

            } else {
                finish();
                Intent intent = ExchangeActivity.newInstance(mContext, false, 0, false, null, null,DataMessageVo.QRTAG);
                startActivity(intent);
                mCode = null;
            }

        } else {
            mCode = null;
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void ExchangeError(String com) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        mCode = null;
        T.showToast(mContext, getStringWithId(R.string.net_error));
        L.e(com);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQrCodeUtil.doResueltIntent(requestCode, resultCode, data);
     /*   if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    mCode=result;
                    submit(result);
//                    T.showToast(mContext, "解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    T.showToast(mContext, "解析二维码失败:");
                }
            }
        }*/
    }

    /**
     * 扫码
     *
     * @param code
     */
    @Override
    public void codeResultSuccess(String code) {
        mCode = code;
        submit(code);
    }

    /**
     * 登录接口
     *
     * @param code
     */
    @Override
    public void loginResultSuccess(String code) {
        mScanCodeUtil.doIntent(mContext,DataMessageVo.LOGINTAG,code);
/*        mCode = code;
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        mQrLoginPresenter.submitLoginRequest(mContext, code);*/

    }

    @Override
    public void addValueResultSuccess(String code) {
        mScanCodeUtil.doIntent(mContext,DataMessageVo.ADDVALUEHTTP,code);
   /*     mCode = code;
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        mAddValuePresenter.requestChangeCode(mContext, code);*/
    }

    @Override
    public void QrloginSuccess(String content) {
        dismissDialog(dialog);
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(content, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                finish();
                Intent intent = ScanConfirmActivity.newInstance(mContext, mCode);
                startActivity(intent);
                mCode = null;
            } else {
                mCode = null;
                T_ERROR(mContext, vo.getData().getMessage());
            }

        } else {
            mCode = null;
            T_ERROR(mContext);
        }

    }

    @Override
    public void QrloginError(String msg) {
        dismissDialog(dialog);
        mCode = null;
        T_ERROR(mContext);
    }

    @Override
    public void ChangeInfomSuccess(String result) {

    }

    @Override
    public void ChangeInfomError(String msg) {

    }

}
