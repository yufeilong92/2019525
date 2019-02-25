package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.event.ShowItemEvent;
import com.xuechuan.xcedu.mvp.model.RefreshTokenModelImpl;
import com.xuechuan.xcedu.mvp.presenter.RefreshTokenPresenter;
import com.xuechuan.xcedu.mvp.view.RefreshTokenView;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.TokenVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;
import com.xuechuan.xcedu.vo.YouzanuserBean;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.basic.YouzanBrowser;
import com.youzan.androidsdk.event.AbsAuthEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: YouZanActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 有赞
 * @author: L-BackPacker
 * @date: 2018/7/10 16:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/7/10
 */
public class YouZanActivity extends BaseActivity implements RefreshTokenView {

    private YouzanBrowser mView;
    //有赞url
    private static String YOUZANURL = "youzanurl";
    private String mUrl;
    private ImageView mIvAgreenmentImg;
    private Context mContext;
    private ImageView mIvBack;

    public static Intent newInstance(Context context, String youzanurl) {
        Intent intent = new Intent(context, YouZanActivity.class);
        intent.putExtra(YOUZANURL, youzanurl);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_zan);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_you_zan);
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(YOUZANURL);
        }
        initView();
        initData();
    }

    private AlertDialog dialog;

    private void initData() {
//        mIvAgreenmentImg.setImageResource(R.drawable.animation_loading);
        loginYouZan(true);
        mView.subscribe(new AbsAuthEvent() {
            @Override
            public void call(Context context, boolean b) {
                /**
                 * 建议实现逻辑:
                 *
                 *     判断App内的用户是否登录?
                 *       => 已登录: 请求带用户角色的认证信息(login接口);
                 *       => 未登录: needLogin为true, 唤起App内登录界面, 请求带用户角色的认证信息(login接口);
                 *       => 未登录: needLogin为false, 请求不带用户角色的认证信息(initToken接口).
                 *
                 *      服务端接入文档: https://www.youzanyun.com/docs/guide/appsdk/683
                 */
                if (b) {
                    requestData();
                }
            }
        });
        mView.loadUrl(mUrl);
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
//        final AnimationDrawable drawable = (AnimationDrawable) mIvAgreenmentImg.getDrawable();
        mView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mView.setVisibility(View.GONE);
//                mIvAgreenmentImg.setVisibility(View.VISIBLE);
//                drawable.start();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissDialog(dialog);
                mView.setVisibility(View.VISIBLE);
//                mIvAgreenmentImg.setVisibility(View.GONE);
//                drawable.stop();
            }

        });

        mView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mView.canGoBack()) {  //表示按返回键
                        mView.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }

    private void loginYouZan(boolean isFirst) {
        if (MyAppliction.getInstance().getUserInfom() != null && MyAppliction.getInstance().getUserInfom().getData().getYouzanuser() != null) {
            YouzanuserBean user = MyAppliction.getInstance().getUserInfom().getData().getYouzanuser();
            if (user == null) return;
            YouzanToken token = new YouzanToken();
            token.setAccessToken(user.getAccess_token());
            token.setCookieKey(user.getCookie_key());
            token.setCookieValue(user.getCookie_value());
            mView.sync(token);
            mView.reload();
            if (!isFirst) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void requestData() {
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb userInfomDb = DbHelperAssist.getInstance(mContext).queryWithuuId(userId);
        if (userInfomDb != null && userInfomDb.getVo() != null) {
            RefreshTokenPresenter presenter = new RefreshTokenPresenter(new RefreshTokenModelImpl(), this);
            presenter.refreshToken(mContext, userInfomDb.getToken());
        } else {
            MyAppliction.getInstance().startLogin(mContext);
        }
    }


    private void initView() {
        mContext = this;
        mView = (YouzanBrowser) findViewById(R.id.view);
        mIvAgreenmentImg = (ImageView) findViewById(R.id.iv_agreenment_img);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mView.canGoBack()) {
                    mView.goBack();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().postSticky(new ShowItemEvent(0));
    }


    @Override
    public void TokenSuccess(String con) {
        Gson gson = new Gson();
        TokenVo tokenVo = gson.fromJson(con, TokenVo.class);
        if (tokenVo.getStatus().getCode() == 200) {
            int statusX = tokenVo.getData().getStatusX();
            TokenVo.DataBean data = tokenVo.getData();
            switch (statusX) {
                case -1:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    this.finish();
                    break;
                case -2:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent1 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent1);
                    this.finish();
                    break;
                case 1:
                    updataToken(data);
                    break;
                default:
            }
        } else {
            SaveUUidUtil.getInstance().delectUUid();
            Intent intent1 = new Intent(mContext, LoginActivity.class);
            startActivity(intent1);
            this.finish();
            L.e(tokenVo.getStatus().getMessage());
        }
    }

    private void updataToken(TokenVo.DataBean data) {
        TokenVo.DataBean.TokenBean token = data.getToken();
        YouzanuserBean dataYouZanUser = data.getYouZanUser();
        YouzanuserBean youZanUser = new YouzanuserBean();
        if (dataYouZanUser != null) {
            youZanUser.setAccess_token(dataYouZanUser.getAccess_token());
            youZanUser.setCookie_key(dataYouZanUser.getCookie_key());
            youZanUser.setCookie_value(dataYouZanUser.getCookie_value());
        }
        UserInfomVo userInfom = new UserInfomVo();
        UserBean bean = new UserBean();
        bean.setId(token.getStaffid());
        bean.setToken(token.getSigntoken());
        bean.setTokenexpire(token.getExpiretime());
        UserInfomVo.DataBean dataBean = new UserInfomVo.DataBean();
        dataBean.setUser(bean);
        dataBean.setYouzanuser(youZanUser);
        userInfom.setData(dataBean);
        MyAppliction.getInstance().setUserInfom(userInfom);
        DbHelperAssist.getInstance(mContext).saveUserInfom(userInfom);
        loginYouZan(false);
    }

    @Override
    public void TokenError(String con) {
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    /**
     * 设置点击返回按钮，跳转到上一个html页面，而不是退出当前activity
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK && mView.canGoBack()) {
                mView.goBack();// 返回前一个页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
