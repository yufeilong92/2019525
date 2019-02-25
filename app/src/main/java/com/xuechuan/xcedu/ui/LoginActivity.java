package com.xuechuan.xcedu.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.db.DbHelp.DBHelper;
import com.xuechuan.xcedu.event.ShowItemEvent;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.jg.RegisterTag;
import com.xuechuan.xcedu.mvp.model.LoginModelImpl;
import com.xuechuan.xcedu.mvp.presenter.LoginPresenter;
import com.xuechuan.xcedu.mvp.view.LoginView;
import com.xuechuan.xcedu.net.LoginService;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.utils.ActivityMangerUtil;
import com.xuechuan.xcedu.utils.CopyUitl;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.Md5;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.greenrobot.eventbus.EventBus;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: LoginActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 登陆页
 * @author: L-BackPacker
 * @date: 2018/4/16 12:02
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/16
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {

    private EditText mEtLoginUsername;
    private EditText mEtLoginPassword;
    private Button mBtnLoginLogin;
    private TextView mTvLoginForgetpaw;
    private TextView mTvLoginRegist;
    private Context mContext;
    private ImageView mIvWeixinlogin;
    private IWXAPI api;
    private BroadcastReceiver receiver;
    private CheckBox mChbLoginEyable;
    private LoginPresenter mPresenter;
    private AlertDialog mDialog;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null)
            unregisterReceiver(receiver);
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        OkGo.getInstance().cancelAll();
        DBHelper.initDb(getApplicationContext());
        setContentView(R.layout.activity_login);
        initView();
        regToWx();
        String userId = SaveUUidUtil.getInstance().getUserId();
        if (StringUtil.isEmpty(userId)) {
            initData();
        } else {
            HomeActivity.newInstance(mContext, HomeActivity.LOGIN_HOME);
            finishActivity();
        }
    }

    //注册微信
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(mContext, DataMessageVo.APP_ID, true);
        api.registerApp(DataMessageVo.APP_ID);
    }

    private void initView() {
        mEtLoginUsername = (EditText) findViewById(R.id.et_login_username);
        mEtLoginPassword = (EditText) findViewById(R.id.et_login_password);
        mBtnLoginLogin = (Button) findViewById(R.id.btn_login_login);
        mTvLoginForgetpaw = (TextView) findViewById(R.id.tv_login_forgetpaw);
        mTvLoginRegist = (TextView) findViewById(R.id.tv_login_regist);
        mBtnLoginLogin.setOnClickListener(this);
        mTvLoginForgetpaw.setOnClickListener(this);
        mTvLoginRegist.setOnClickListener(this);
        mContext = this;
        mIvWeixinlogin = (ImageView) findViewById(R.id.iv_weixinlogin);
        mIvWeixinlogin.setOnClickListener(this);
        mChbLoginEyable = (CheckBox) findViewById(R.id.chb_login_eyable);
        mChbLoginEyable.setOnClickListener(this);
        Utils.showPassWord(mChbLoginEyable, mEtLoginPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login://登录
                if (!Utils.handleOnDoubleClick()) {
                    Utils.hideInputMethod(LoginActivity.this);
                    String phone = getTextStr(mEtLoginUsername);
                    boolean phoneNum = Utils.isPhoneNum(phone);
                    L.w(phone + phoneNum);
                    if (!Utils.isPhoneNum(phone)) {
                        T.showToast(mContext, getStringWithId(R.string.please_right_phone));
                        return;
                    }
                    submit();
                }
                break;
            case R.id.tv_login_forgetpaw://忘记密码
                Intent intent1 = RegisterActivity.newInstance(mContext, RegisterActivity.CEX_INT_TYPE_PAW, null, null);
                intent1.putExtra(RegisterActivity.CSTR_EXTRA_TITLE_STR, getStringWithId(R.string.forget_password));
                startActivity(intent1);
                break;
            case R.id.tv_login_regist://手机注册
                Intent intent2 = RegisterActivity.newInstance(mContext, RegisterActivity.CEX_INT_TYPE_REG, null, null);
                startActivity(intent2);
                break;
            case R.id.iv_weixinlogin://微信登录
                if (api.isWXAppInstalled()) {
                    loginWeiXin();
                } else {
                    T.showToast(mContext, "请先安装微信");
                }
                break;

        }
    }

    /**
     * 处理微信登录回调
     */
    private void initData() {
        mPresenter = new LoginPresenter(new LoginModelImpl(), this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DataMessageVo.WEI_LOGIN_ACTION);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    String extra = intent.getStringExtra(DataMessageVo.WEISTATE);
                    String code = intent.getStringExtra(DataMessageVo.WEICODE);
//                    requestLogin(extra, code);
                    mPresenter.getWeiXinLoginContent(mContext, code);
                }
            }
        };
        registerReceiver(receiver, filter);
        mEtLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    mBtnLoginLogin.setEnabled(false);
                    mBtnLoginLogin.setBackgroundResource(R.drawable.btn_login_bg);
                    return;
                }
                String trim = mEtLoginPassword.getText().toString().trim();
                if (!StringUtil.isEmpty(trim)) {
                    mBtnLoginLogin.setEnabled(true);
                    mBtnLoginLogin.setBackgroundResource(R.drawable.btn_login_bg_normal);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEtLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mBtnLoginLogin.setEnabled(false);
                    mBtnLoginLogin.setBackgroundResource(R.drawable.btn_login_bg);
                    return;
                }
                String trim = mEtLoginUsername.getText().toString().trim();
                if (!StringUtil.isEmpty(trim)) {
                    mBtnLoginLogin.setEnabled(true);
                    mBtnLoginLogin.setBackgroundResource(R.drawable.btn_login_bg_normal);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //调用微信
    private void loginWeiXin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    private void submit() {
        String username = getTextStr(mEtLoginUsername);
        if (TextUtils.isEmpty(username)) {
            T.showToast(mContext, R.string.please_input_phone);
            return;
        }
        String password = getTextStr(mEtLoginPassword);
        if (password.length() < 6) {
            T.showToast(mContext, getStringWithId(R.string.passundersixt));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            T.showToast(mContext, R.string.please_paw);
            return;
        }
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.login_loading));
        mPresenter.getLoginContent(mContext, username, Md5.getMD5String(password));
    }

    @Override
    public void WeiXinLoginSuccess(String infom) {
        Gson gson = new Gson();
        UserInfomVo vo = gson.fromJson(infom, UserInfomVo.class);
        UserInfomVo.DataBean voData = vo.getData();
        if (vo.getStatus().getCode() != 200) {//失败情况
            T.showToast(mContext, "授权失败");
            return;
        }
        if (voData.isIsbinduser()) {//已经绑定数据（手机）
            //保存信息
            UserInfomSqliteVo infomSqliteVo = CopyUitl.get_Instance().setCopyUserInfom(vo.getData());
            UserInfomDbHelp.get_Instance(mContext).addUserInfom(infomSqliteVo);
//            DbHelperAssist.getInstance(mContext).saveUserInfom(vo);
            EventBus.getDefault().postSticky(new ShowItemEvent(0));
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            //注册激光
            RegisterTag tag = RegisterTag.getInstance(getApplicationContext());
            tag.registJG();
            tag.setTagAndAlias(String.valueOf(voData.getUser().getId()));
            finishActivity();
        } else {//没有绑定手机
            Intent intent = RegisterActivity.newInstance(mContext, RegisterActivity.CEX_INT_TYPE_BIND, voData.getOpenid(), voData.getUnionid());
            intent.putExtra(RegisterActivity.CSTR_EXTRA_TITLE_STR, getStringWithId(R.string.bingphone));
            startActivity(intent);
        }
    }

    @Override
    public void WeiXinLoginError(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
//        T.showToast(mContext, error);
    }

    @Override
    public void LoginSuccess(String message) {
        if (mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        L.d("登录成功", message);
        Gson gson = new Gson();
        UserInfomVo vo = gson.fromJson(message, UserInfomVo.class);
        if (vo.getStatus().getCode() == 200) {
            UserInfomVo.DataBean voData = vo.getData();
            int status = voData.getStatus();
            if (status != 1) {
                T.showToast(mContext, voData.getInfo());
                return;
            }
            UserInfomSqliteVo infomSqliteVo = CopyUitl.get_Instance().setCopyUserInfom(vo.getData());
            UserInfomDbHelp.get_Instance(mContext).addUserInfom(infomSqliteVo);
//            DbHelperAssist.getInstance(mContext).saveUserInfom(vo);
            MyAppliction.getInstance().setUserInfom(vo);
            EventBus.getDefault().postSticky(new ShowItemEvent(0));
            HomeActivity.newInstance(mContext, HomeActivity.LOGIN_HOME);
            //注册激光
            RegisterTag tag = RegisterTag.getInstance(getApplicationContext());
            tag.registJG();
            tag.setTagAndAlias(String.valueOf(voData.getUser().getId()));
            finishActivity();
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void LoginError(String con) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
        L.e(con);
    }

    public void finishActivity() {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityMangerUtil.finishAllActivity();
    }

}
