package com.xuechuan.xcedu.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.T;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(initInflateView(), container, false);
        initCreateView(view, savedInstanceState);
        return view;
    }

    protected abstract int initInflateView();

    protected abstract void initCreateView(View view, Bundle savedInstanceState);

    protected String getStrWithId(Context mContext, int str) {
        return  mContext==null?this.getResources().getString(str):mContext.getResources().getString(str);
    }
    protected String getStrWithId( int str) {
        return this.getResources().getString(str);
    }

    protected String getTextStr(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            return tv.getText().toString().trim();
        }
        if (view instanceof Button) {
            Button btn = (Button) view;
            return btn.getText().toString().trim();
        }
        if (view instanceof EditText) {
            EditText et = (EditText) view;
            return et.getText().toString().trim();
        }
        return null;
    }

    public void dialogDimiss(AlertDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void T_ERROR(Context context) {
        T.showToast(context, getStrWithId(context, R.string.net_error));
    }
    public void T_ERROR(Context context,String Msg) {
        T.showToast(context,Msg);
    }
    /**
     * 设置布局
     * @param context
     * @param view
     */
    protected void setGridLayoutManger(Context context, RecyclerView view, int number) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, number);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
    }
    /**
     * 设置布局
     * @param context
     * @param view
     */
    protected GridLayoutManager getGridLayoutManger( Context context,RecyclerView view,int number) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, number);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        return layoutManager;
    }
    protected void setWebVIewSetting(WebView webView){
        WebSettings settings = webView.getSettings();
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
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }
    public void dismissDialog(AlertDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
