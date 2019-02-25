package com.xuechuan.xcedu.live;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: LiveInfomActivity
 * @Package com.xuechuan.xcedu.live
 * @Description: 直播课详情信息页
 * @author: L-BackPacker
 * @date: 2018.10.13 上午 10:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.10.13
 */

public class LiveInfomActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvLiveProgress;
    private WebView mWebLiveContent;
    private TextView mTvLivePrice;
    private TextView mTvLiveService;
    private Button mBtnLiveBuy;
    private LinearLayout mLlLiveButtomFloat;
    private Button mBtnLiveValue;
    private RelativeLayout mRlLiveContent;
    private static String PARAMT_KEY = "LiveInfomActivity.paramt_key";
    private static String PARAMT1_KEY = "LiveInfomActivity.paramt_key1";
    private String mUrl;
    private boolean mIsBuy;

    public static void start_Intent(Context context, String url, boolean isBuy) {
        Intent intent = new Intent(context, LiveInfomActivity.class);
        intent.putExtra(PARAMT_KEY, url);
        intent.putExtra(PARAMT1_KEY, isBuy);
        context.startActivity(intent);
    }

    /*    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_live_infom);
            initView();
        }*/
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_live_infom);
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(PARAMT_KEY);
            mIsBuy = getIntent().getBooleanExtra(PARAMT1_KEY, false);
        }
        initView();
        initWebView();
        initData();
    }

    private void initData() {
        mLlLiveButtomFloat.setVisibility(mIsBuy ? View.GONE : View.VISIBLE);
        mBtnLiveValue.setVisibility(mIsBuy ? View.VISIBLE : View.GONE);
    }

    private void initWebView() {
        setWebVIewSetting(mWebLiveContent);
        mIvLiveProgress.setImageResource(R.drawable.animation_loading);
        final AnimationDrawable drawable = (AnimationDrawable) mIvLiveProgress.getDrawable();
        mWebLiveContent.loadUrl(mUrl);
        mWebLiveContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;

                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return true;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mRlLiveContent.setVisibility(View.GONE);
                mIvLiveProgress.setVisibility(View.VISIBLE);
                drawable.start();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mRlLiveContent.setVisibility(View.VISIBLE);
                mIvLiveProgress.setVisibility(View.GONE);
                drawable.stop();
            }
        });
    }

    private void initView() {
        mIvLiveProgress = (ImageView) findViewById(R.id.iv_live_progress);
        mWebLiveContent = (WebView) findViewById(R.id.web_live_content);
        mTvLivePrice = (TextView) findViewById(R.id.tv_live_price);
        mTvLiveService = (TextView) findViewById(R.id.tv_live_service);
        mBtnLiveBuy = (Button) findViewById(R.id.btn_live_buy);
        mLlLiveButtomFloat = (LinearLayout) findViewById(R.id.ll_live_buttom_float);
        mBtnLiveValue = (Button) findViewById(R.id.btn_live_value);

        mBtnLiveBuy.setOnClickListener(this);
        mBtnLiveValue.setOnClickListener(this);
        mRlLiveContent = (RelativeLayout) findViewById(R.id.rl_live_content);
        mRlLiveContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_live_buy:

                break;
            case R.id.btn_live_value:

                break;
        }
    }
}
