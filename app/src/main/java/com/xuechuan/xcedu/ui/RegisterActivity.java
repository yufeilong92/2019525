package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.event.ShowItemEvent;
import com.xuechuan.xcedu.jg.RegisterTag;
import com.xuechuan.xcedu.net.RegisterService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.utils.CopyUitl;
import com.xuechuan.xcedu.utils.CountdownUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.SmsVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.greenrobot.eventbus.EventBus;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: RegisterActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 注册界面
 * @author: L-BackPacker
 * @date: 2018/4/16 12:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/16
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private CountdownUtil mTimeUtils;
    private Button mBtnSend;
    private EditText mEtRegisterPaw;
    private EditText mEtRegisterPaws;
    private EditText mEtRegisterPhone;
    private EditText mEtRegisterCode;
    private Context mContext;
    private Button mBtnRegisterLogin;
    private CheckBox mChbShowPasw;
    private CheckBox mChbShowPassw;
    private CheckBox mChbRegisterAgreen;
    private TextView mTvRegithsAgreem;
    private LinearLayout mLlRegisterAgreem;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        initView();
//    }

    /**
     * 微信
     */
    private static String OPENID = "openid";
    private static String UNIONID = "uuidid";
    /**
     * 请求类型
     */
    private static String HTTPTYPE = "httptype";
    private String mOpenid;
    private String mUuionid;
    private String mType;
    /**
     * 注册
     */
    public static final String CEX_INT_TYPE_REG = "reg";
    /**
     * 找回密码
     */
    public static final String CEX_INT_TYPE_PAW = "retrieve";
    /**
     * 绑定手机
     */
    public static final String CEX_INT_TYPE_BIND = "bindreg";

    /**
     * @param context 上下文
     * @param openid  微信openid
     * @param unionid 平台标识
     */
    public static Intent newInstance(Context context, String type, String openid, String unionid) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(OPENID, openid);
        intent.putExtra(HTTPTYPE, type);
        intent.putExtra(UNIONID, unionid);
        return intent;
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        if (getIntent() != null) {
            mOpenid = getIntent().getStringExtra(OPENID);
            mUuionid = getIntent().getStringExtra(UNIONID);
            mType = getIntent().getStringExtra(HTTPTYPE);
        }
        initView();
        initData();
    }

    //处理请求
    private void initData() {
        if (!StringUtil.isEmpty(mType)) {
            if (mType.equals(CEX_INT_TYPE_PAW)) {//找回密码
                mBtnRegisterLogin.setText(R.string.reset_password);
            } else if (mType.equals(CEX_INT_TYPE_BIND)) {//绑定手机
                mBtnRegisterLogin.setText(R.string.bingphone);
            } else if (mType.equals(CEX_INT_TYPE_REG)) {//注册
                mLlRegisterAgreem.setVisibility(View.VISIBLE);
                mBtnRegisterLogin.setText(R.string.regist);
            }
        }
    }

    private void initView() {
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mEtRegisterPaw = (EditText) findViewById(R.id.et_register_paw);
        mEtRegisterPaw.setOnClickListener(this);
        mEtRegisterPaws = (EditText) findViewById(R.id.et_register_paws);
        mEtRegisterPaws.setOnClickListener(this);
        mEtRegisterPhone = (EditText) findViewById(R.id.et_register_phone);
        mEtRegisterPhone.setOnClickListener(this);
        mEtRegisterCode = (EditText) findViewById(R.id.et_register_code);
        mEtRegisterCode.setOnClickListener(this);
        mContext = this;
        mBtnRegisterLogin = (Button) findViewById(R.id.btn_register_login);
        mBtnRegisterLogin.setOnClickListener(this);
        mChbShowPasw = (CheckBox) findViewById(R.id.chb_show_pasw);
        mChbShowPasw.setOnClickListener(this);
        mChbShowPassw = (CheckBox) findViewById(R.id.chb_show_passw);
        mChbShowPassw.setOnClickListener(this);
        mChbRegisterAgreen = (CheckBox) findViewById(R.id.chb_register_agreen);
        mChbRegisterAgreen.setOnClickListener(this);
        mTvRegithsAgreem = (TextView) findViewById(R.id.tv_regiths_agreem);
        mTvRegithsAgreem.setOnClickListener(this);
        mLlRegisterAgreem = (LinearLayout) findViewById(R.id.ll_register_agreem);
        mLlRegisterAgreem.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send://发送验证码
                Utils.hideInputMethod(RegisterActivity.this);
                String phone = getTextStr(mEtRegisterPhone);
                if (!StringUtil.isEmpty(phone)) {
                    if (!Utils.isPhoneNum(phone)) {
                        T.showToast(mContext, getString(R.string.please_right_phone));
                        return;
                    }
                    CountdownUtil.getInstance().startTime(mContext, mBtnSend);
                    mBtnSend.setEnabled(false);
                    sendRequestCodeHttp(phone);
                } else {
                    T.showToast(mContext, getString(R.string.please_input_phone));
                }
                break;
            case R.id.btn_register_login://登录
                Utils.hideInputMethod(RegisterActivity.this);
                submit();
                break;
            case R.id.chb_show_pasw:
                Utils.showPassWord(mChbShowPasw, mEtRegisterPaw);
                break;
            case R.id.chb_show_passw:
                Utils.showPassWord(mChbShowPassw, mEtRegisterPaws);
                break;
            case R.id.tv_regiths_agreem:
                Intent intent = AgreementActivity.newInstance(mContext, DataMessageVo.AGREEMENT,
                        AgreementActivity.NOSHAREMARK, "", "");
                intent.putExtra(AgreementActivity.CSTR_EXTRA_TITLE_STR, "注册协议");
                startActivity(intent);
                break;
        }
    }


    /**
     * 请求验证码
     */
    private void sendRequestCodeHttp(String phone) {
        final RegisterService service = RegisterService.getInstance(mContext);
        final AlertDialog showDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.get_code));
        service.requestRegisterCode(phone, new StringCallBackView() {
            @Override
            public void onSuccess(String message) {
//                String message = response.body().toString();
                dismissDialog(showDialog);
                Gson gson = new Gson();
                SmsVo smsVo = gson.fromJson(message, SmsVo.class);
                SmsVo.StatusBean status = smsVo.getStatus();
                L.d("短信", message);
                int code = status.getCode();
                if (code == 200) {
                    SmsVo.DataBean data = smsVo.getData();
                    int status1 = data.getStatus();
                    if (status1==1){
                        T.showToast(mContext, getString(R.string.sms_ok));
                    }else {
                        T.showToast(mContext,data.getInfo());
                    }

                }
            }

            @Override
            public void onError(String response) {
                dismissDialog(showDialog);
                T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountdownUtil.getInstance().stop();
    }

    private void submit() {
        String phone = getTextStr(mEtRegisterPhone);
        if (TextUtils.isEmpty(phone)) {
            T.showToast(mContext, getString(R.string.please_input_phone));
            return;
        }
        final String code = getTextStr(mEtRegisterCode);
        if (TextUtils.isEmpty(code)) {
            T.showToast(mContext, getString(R.string.please_input_code));
            return;
        }
        String paw = getTextStr(mEtRegisterPaw);
        if (TextUtils.isEmpty(paw)) {
            T.showToast(mContext, getString(R.string.please_input_pass));
            return;
        }
        if (paw.length() < 6) {
            T.showToast(mContext, getString(R.string.passundersixt));
            return;
        }
        String paws = getTextStr(mEtRegisterPaws);
        if (TextUtils.isEmpty(paws)) {
            T.showToast(mContext, getString(R.string.please_input_pass));
            return;
        }
        if (paws.length() < 6) {
            T.showToast(mContext, getString(R.string.passundersixt));
            return;
        }
        if (!paw.equals(paws)) {
            T.showToast(mContext, getString(R.string.pas_no_same));
            return;
        }
        if (mType.equals(CEX_INT_TYPE_REG)) {
            if (!mChbRegisterAgreen.isChecked()) {
                T.showToast(mContext, "请确认同意注册协议");
                return;
            }
        }
        sumbit(phone, code, paw);
    }

    private void sumbit(String phone, String code, String paw) {
        RegisterService service = RegisterService.getInstance(mContext);
        final AlertDialog dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        service.requestRegister(mType, phone, code, paw, mOpenid, mUuionid, new StringCallBackView() {
            @Override
            public void onSuccess(String message) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
//                String message = response.body().toString();
                L.w(message);
                Gson gson = new Gson();
                UserInfomVo vo = gson.fromJson(message, UserInfomVo.class);
                int code1 = vo.getStatus().getCode();
                if (code1 == 200) {//注册成功
                    UserInfomVo.DataBean data = vo.getData();
                    int status = data.getStatus();
                    if (mType.equals(CEX_INT_TYPE_PAW)) {//找回密码
                        if (status == 1) {
                            T.showToast(mContext, getString(R.string.resetpaw));
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            T.showToast(mContext, data.getInfo());
                        }
                    } else {
                        if (status == 1) {
                            T.showToast(mContext, getString(R.string.registerOK));
                            //注册激光
                            RegisterTag tag = RegisterTag.getInstance(getApplicationContext());
                            tag.registJG();
                            tag.setTagAndAlias(String.valueOf(data.getUser().getId()));
                            //跳转首页
                            MyAppliction.getInstance().setUserInfom(vo);
                            EventBus.getDefault().postSticky(new ShowItemEvent(0));
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            MyAppliction.getInstance().setUserInfom(vo);
//                            DbHelperAssist.getInstance(mContext).saveUserInfom(vo);
                            UserInfomSqliteVo infomSqliteVo = CopyUitl.get_Instance().setCopyUserInfom(vo.getData());
                            UserInfomDbHelp.get_Instance(mContext).addUserInfom(infomSqliteVo);
                            startActivity(intent);
                        } else {
                            T.showToast(mContext, data.getInfo());
                        }

                    }
                } else {//失败
//                    String message1 = vo.getStatus().getMessage();
                    T.showToast(mContext, getStringWithId(R.string.net_error));
//                    T.showToast(mContext, message1);
                }
            }

            @Override
            public void onError(String response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                T.showToast(mContext, getStringWithId(R.string.net_error));
                L.e(response);
            }
        });
    }


}
