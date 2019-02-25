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
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.AddVauleModelImpl;
import com.xuechuan.xcedu.mvp.presenter.AddVaulePresenter;
import com.xuechuan.xcedu.mvp.view.AddVauleView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.QRCodeUtil;
import com.xuechuan.xcedu.utils.ScanCodeUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AddVauleActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 增值服务
 * @author: L-BackPacker
 * @date: 2018/5/22 11:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class AddVauleActivity extends BaseActivity implements View.OnClickListener, AddVauleView, QRCodeUtil.qrCodeView {

    private EditText mEtMAddCode;
    private Button mBtnMAddValue;
    private Context mContext;
    private AddVaulePresenter mPresenter;
    private AlertDialog dialog;
    private ImageView mIvCodeAddEc;
    private QRCodeUtil mQrCodeUtil;
    private ScanCodeUtil mScanCodeUtil;
    /*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaule);
        initView();
    }*/
    private static String CODE = "code";
    private String mCode;

    public static Intent newInstance(Context context, String code) {
        Intent intent = new Intent(context, AddVauleActivity.class);
        intent.putExtra(CODE, code);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_vaule);
        if (getIntent() != null) {
            mCode = getIntent().getStringExtra(CODE);
        }
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new AddVaulePresenter(new AddVauleModelImpl(), this);
        mQrCodeUtil = QRCodeUtil.getInstance(mContext);
        mQrCodeUtil.setQrCodeView(this);
        mScanCodeUtil = ScanCodeUtil.getInstance(mContext);
        if (StringUtil.isEmpty(mCode)) return;
        mScanCodeUtil.doIntent(mContext, DataMessageVo.ADDVALUEHTTP, mCode);

    }

    private void initView() {
        mEtMAddCode = (EditText) findViewById(R.id.et_m_add_code);
        mBtnMAddValue = (Button) findViewById(R.id.btn_m_add_value);
        mBtnMAddValue.setOnClickListener(this);
        mContext = this;
        mIvCodeAddEc = (ImageView) findViewById(R.id.iv_code_add_ec);
        mIvCodeAddEc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_m_add_value://兑换
                submit();
                break;
            case R.id.iv_code_add_ec://扫码
                mQrCodeUtil.doIntentScanCode(AddVauleActivity.this);
                break;
        }
    }

    private void submit() {
        String code = getTextStr(mEtMAddCode);
        if (TextUtils.isEmpty(code)) {
            T.showToast(mContext, R.string.code_empty);
            return;
        }
        mScanCodeUtil.doIntent(mContext, DataMessageVo.ADDVALUEHTTP, code);
  /*      dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestAddValueWithCode(mContext, code);
*/
    }

    @Override
    public void AddVauleSuccess(String com) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(com, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                finish();
            }
            T.showToast(mContext, vo.getData().getMessage());
//            T.showToast(mContext, getString(R.string.code_suceess));
        } else {
            T.showToast(mContext, getString(R.string.code_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void AddVauleError(String com) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    //扫码
    @Override
    public void codeResultSuccess(String code) {
        mScanCodeUtil.doIntent(mContext, DataMessageVo.QRTAG, code);
    }

    //扫码登录
    @Override
    public void loginResultSuccess(String code) {
        mScanCodeUtil.doIntent(mContext, DataMessageVo.LOGINTAG, code);
    }

    //增值服务
    @Override
    public void addValueResultSuccess(String code) {
        mScanCodeUtil.doIntent(mContext, DataMessageVo.ADDVALUEHTTP, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQrCodeUtil.doResueltIntent(requestCode, resultCode, data);
    }

}
