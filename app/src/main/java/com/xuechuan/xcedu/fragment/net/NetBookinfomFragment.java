package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookinfomFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 网课详情页
 * @author: L-BackPacker
 * @date: 2018/5/14 17:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */
public class NetBookinfomFragment extends BaseFragment {

    private static final String URL = "url";
    private String mUrl;
    private WebView mWebAgreenement;
    private ImageView mIvAgreenmentImg;

    public NetBookinfomFragment() {
    }

    public static NetBookinfomFragment newInstance(String url) {
        NetBookinfomFragment fragment = new NetBookinfomFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(URL);
        }
    }

  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_bookinfom, container, false);
        initView(view);
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_bookinfom;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        if (StringUtil.isEmpty(mUrl)) {
            return;
        }
        initData();
    }


    private void initData() {
        mIvAgreenmentImg.setImageResource(R.drawable.animation_loading);
        final AnimationDrawable drawable = (AnimationDrawable) mIvAgreenmentImg.getDrawable();
        WebSettings settings = mWebAgreenement.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebAgreenement.loadUrl(mUrl);
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
                        getActivity().startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWebAgreenement.setVisibility(View.GONE);
                mIvAgreenmentImg.setVisibility(View.VISIBLE);
                drawable.start();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebAgreenement.setVisibility(View.VISIBLE);
                mIvAgreenmentImg.setVisibility(View.GONE);
                drawable.stop();
            }
        });
    }

    private void initView(View view) {
        mWebAgreenement = (WebView) view.findViewById(R.id.web_agreenement);
        mIvAgreenmentImg = (ImageView) view.findViewById(R.id.iv_agreenment_img);
    }
}
