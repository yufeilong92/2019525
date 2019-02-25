package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.utils.DialogBgUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.ShareUtils;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AgreementActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 协议网址
 * @author: L-BackPacker
 * @date: 2018/5/30 20:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/30
 */
public class AgreementActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebAgreenement;
    private ImageView mIvAgreenmentImg;
    private String mViewTitle;
    private ImageView mIvTitleMore;
    private LinearLayout mLlRootView;
    private ImageView mIvBack;


  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        initView();
    }*/

    private static String URLDATA = "urldata";
    private String mUrl;
    private Context mContext;
    private CommonPopupWindow popShare;
    private static String typeMark = "mark";
    public static final String SHAREMARK = "share";
    public static final String NOSHAREMARK = "noshaor";
    public static final String MTITLE = "aggreestitle";
    public static final String SHAREURL = "shareurl";
    private String mShareUrl;


    public static Intent newInstance(Context context, String urldata, String mark, String title, String ShareUrl) {
        Intent intent = new Intent(context, AgreementActivity.class);
        intent.putExtra(URLDATA, urldata);
        intent.putExtra(typeMark, mark);
        intent.putExtra(MTITLE, title);
        intent.putExtra(SHAREURL, ShareUrl);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_agreement);
        initView();
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(URLDATA);
            String mType = getIntent().getStringExtra(typeMark);
            mViewTitle = getIntent().getStringExtra(MTITLE);
            mShareUrl = getIntent().getStringExtra(SHAREURL);
            if (mType.equals(SHAREMARK)) {
                mIvTitleMore.setVisibility(View.VISIBLE);
            } else {
                mIvTitleMore.setVisibility(View.INVISIBLE);
            }
        }
        initData();
    }


    private void initData() {
        setWebVIewSetting(mWebAgreenement);
/*        WebSettings settings = mWebAgreenement.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式*/
        mWebAgreenement.loadUrl(mUrl);
        final AlertDialog dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));

        mWebAgreenement.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return true;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                mWebAgreenement.setVisibility(View.GONE);
                mViewTitle = view.getTitle();
//                mIvAgreenmentImg.setVisibility(View.VISIBLE);
//                drawable.start();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissDialog(dialog);
                mWebAgreenement.setVisibility(View.VISIBLE);
//                mIvAgreenmentImg.setVisibility(View.GONE);
//                drawable.stop();
            }
        });
        mWebAgreenement.setWebChromeClient(new WebChromeClient());
        mWebAgreenement.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebAgreenement.canGoBack()) { // 表示按返回键
                        // 时的操作
                        mWebAgreenement.goBack(); // 后退
                        // webview.goForward();//前进
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
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
            if (keyCode == KeyEvent.KEYCODE_BACK && mWebAgreenement.canGoBack()) {
                mWebAgreenement.goBack();// 返回前一个页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        mContext = this;
        mWebAgreenement = (WebView) findViewById(R.id.web_agreenement);
        mIvAgreenmentImg = (ImageView) findViewById(R.id.iv_agreenment_img);
        mIvTitleMore = (ImageView) findViewById(R.id.iv_title_more);
        mIvTitleMore.setOnClickListener(this);
        mLlRootView = (LinearLayout) findViewById(R.id.ll_root_view);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
    }

    /**
     * 分享布局
     */
    private void showShareLayout() {
        popShare = new CommonPopupWindow(this, R.layout.pop_item_share, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private TextView qqzon;
            private TextView qq;
            private TextView weibo;
            private TextView circle;
            private TextView weixin;

            @Override
            protected void initView() {
                View view = getContentView();
                weixin = (TextView) view.findViewById(R.id.tv_pop_weixin_share);
                circle = (TextView) view.findViewById(R.id.tv_pop_crile_share);
                weibo = (TextView) view.findViewById(R.id.tv_pop_weibo_share);
                qq = (TextView) view.findViewById(R.id.tv_pop_qq_share);
                qqzon = (TextView) view.findViewById(R.id.tv_pop_qqzon_share);
            }

            @Override
            protected void initEvent() {
                qq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(AgreementActivity.this, mShareUrl, mViewTitle
                                , "", "", R.mipmap.appicon
                                , SHARE_MEDIA.QQ);
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.QQ);
                        getPopupWindow().dismiss();
                    }
                });
                qqzon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(AgreementActivity.this, mShareUrl, mViewTitle
                                , "", "", R.mipmap.appicon, SHARE_MEDIA.QZONE
                        );
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.QZONE);
                        getPopupWindow().dismiss();
                    }
                });
                weibo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(AgreementActivity.this, mShareUrl, mViewTitle
                                , "", "", R.mipmap.appicon
                                , SHARE_MEDIA.SINA
                        );
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.SINA);
                        getPopupWindow().dismiss();
                    }
                });
                weixin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(AgreementActivity.this, mShareUrl, mViewTitle
                                , "", "", R.mipmap.appicon, SHARE_MEDIA.WEIXIN
                        );
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.WEIXIN);
                        getPopupWindow().dismiss();
                    }
                });
                circle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(AgreementActivity.this, mShareUrl, mViewTitle
                                , "", "", R.mipmap.appicon, SHARE_MEDIA.WEIXIN_CIRCLE
                        );
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        getPopupWindow().dismiss();
                    }
                });
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        DialogBgUtil.setBackgroundAlpha(1f, AgreementActivity.this);
                    }
                });
            }
        };
        popShare.showAtLocation(mLlRootView, Gravity.BOTTOM, 0, 0);
        DialogBgUtil.setBackgroundAlpha(0.5f, AgreementActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_more:
                showShareLayout();
                break;
            case R.id.iv_back:
                if (mWebAgreenement.canGoBack()) {
                    mWebAgreenement.goBack();
                } else {
                    this.finish();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
