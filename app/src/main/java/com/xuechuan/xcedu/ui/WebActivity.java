package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.xuechuan.xcedu.R;

public class WebActivity extends AppCompatActivity {

    private WebView mWvContent;
    private static String PARAMT_KEY = "WebActivity.paramt_key";
    private static String PARAMT1_KEY = "WebActivity.paramt_key1";
    private String mUrl;

    public static Intent start_Intent(Context context, String url, String paramt1) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(PARAMT_KEY, url);
        intent.putExtra(PARAMT1_KEY, paramt1);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(PARAMT_KEY);
        }
        setContentView(R.layout.activity_web);
        initView();
        initData();
    }

    private void initData() {
        WebSettings settings = mWvContent.getSettings();
        settings.setJavaScriptEnabled(true);
  /*       // 设置可以支持缩放
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
        mWvContent.loadUrl(mUrl);
    }

    private void initView() {
        mWvContent = (WebView) findViewById(R.id.wv_content);

    }
}
