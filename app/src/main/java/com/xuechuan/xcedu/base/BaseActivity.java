package com.xuechuan.xcedu.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.ui.me.AddVauleActivity;
import com.xuechuan.xcedu.ui.me.GenuineActivity;
import com.xuechuan.xcedu.utils.ActivityMangerUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: 基类
 * @author: L-BackPacker
 * @date: 2018/4/10 10:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 传入参数-标题
     */
    public static final String CSTR_EXTRA_TITLE_STR = "title";
    private String mBaseTitle;
    /**
     * 二维码
     */
    private String VC = "vc";
    /**
     * 文章
     */
    private String ARTICLE = "article";
    /**
     * 增值
     */
    private String ADDVALUE = "ec";
    /**
     * 扫描分享
     */
    private String QRSHARE = "qr";
    /**
     * 增值服务
     */
    private String ADDSERVICE = "addSercvice";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getIntent() != null) {
            Uri data = this.getIntent().getData();
            if (data != null) {
                String type = data.getQueryParameter("type");
                if (StringUtil.isEmpty(type)) return;
                if (type.equalsIgnoreCase(ARTICLE)) {
                    String title = data.getQueryParameter("title");
                    String isarticle = data.getQueryParameter("isarticle");
                    String url = data.getQueryParameter("url");
                    String shareurl = data.getQueryParameter("shareurl");
                    String articleid = data.getQueryParameter("articleid");
                    Log.e("第三方调取===", "onCreate: " + title + "//"
                            + isarticle + "//" + url + "//" + shareurl + "//" + articleid);
                    MyAppliction.getInstance().setIsAtricle(isarticle);
                    MyAppliction.getInstance().setShareParems(url, articleid, title, shareurl, "");
                }
                if (type.equalsIgnoreCase(VC)) {
                    String code = data.getQueryParameter("code");
                    MyAppliction.getInstance().setIsAtricle(QRSHARE);
                    MyAppliction.getInstance().setShareParems("", "", "", "", code);
                }
                if (type.equalsIgnoreCase(ADDVALUE)) {
                    String code = data.getQueryParameter("code");
                    MyAppliction.getInstance().setIsAtricle(ADDSERVICE);
                    MyAppliction.getInstance().setShareParems("", "", "", "", code);
                }
                doShareActivity();
            }
        }
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        Intent intent = getIntent();
        mBaseTitle = intent.getStringExtra(CSTR_EXTRA_TITLE_STR);
        ActivityMangerUtil.addActivity(this);
        initContentView(savedInstanceState);
        if (!StringUtil.isEmpty(mBaseTitle)) {
            setTitle(mBaseTitle);
        }

    }

    /**
     * 删除分享记录
     */
    public void delectShare() {
        MyAppliction.getInstance().setIsAtricle(null);
        MyAppliction.getInstance().delectShareParems();
    }


    public static class ShareParems {
        public String title;
        public String url;
        public String shareurl;
        public String articleid;
        public String code;
    }

    private void doShareActivity() {
        if (StringUtil.isEmpty(MyAppliction.getInstance().getIsAtricle())) {
            return;
        }
        if (MyAppliction.getInstance().getIsAtricle().equals("0")) {
            doIntentAct(new Infom(), MyAppliction.getInstance().getShareParems());
            return;
        }
        if (MyAppliction.getInstance().getIsAtricle().equals("1")) {
            doIntentAct(new WenZhang(), MyAppliction.getInstance().getShareParems());
            return;
        }
        if (MyAppliction.getInstance().getIsAtricle().equalsIgnoreCase(QRSHARE)) {
            doIntentAct(new AddVaule(), MyAppliction.getInstance().getShareParems());
            return;
        }
        if (MyAppliction.getInstance().getIsAtricle().equalsIgnoreCase(ADDSERVICE)) {
            doIntentAct(new Add_Add_Vaule(), MyAppliction.getInstance().getShareParems());
        }

    }

    protected abstract void initContentView(Bundle savedInstanceState);


    private void setTitle(String str) {
        TextView title = (TextView) findViewById(R.id.activity_title_text);
        title.setText(str);
        title.setSelected(true);
    }

    public void onHomeClick(View view) {
        finish();
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

    protected String getStringWithId(int id) {
        return getResources().getString(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityMangerUtil.finishActivity(this);
//        OkGo.getInstance().cancelTag(this);
    }

    public interface ShareActivity {
        public void startAct(ShareParems shareParems);
    }

    //资讯
    public class Infom implements ShareActivity {
        @Override
        public void startAct(ShareParems shareParems) {
            UserInfomVo infom = MyAppliction.getInstance().getUserInfom();
            if (infom == null) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            delectShare();
            final Intent intent = AgreementActivity.newInstance(BaseActivity.this, shareParems.url, AgreementActivity.SHAREMARK,
                    shareParems.title, shareParems.shareurl);
//            startActivity(intent);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 300);
        }
    }

    //文章
    public class WenZhang implements ShareActivity {
        @Override
        public void startAct(ShareParems shareParems) {
            UserInfomVo infom = MyAppliction.getInstance().getUserInfom();
            if (infom == null || infom.getData().getUser() == null) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            delectShare();
            final Intent intent = InfomDetailActivity.startInstance(BaseActivity.this, shareParems.url,
                    String.valueOf(shareParems.articleid), shareParems.title, DataMessageVo.USERTYOEARTICLE);
//            startActivity(intent);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 300);
        }
    }

    //扫码增值
    public class AddVaule implements ShareActivity {
        @Override
        public void startAct(ShareParems shareParems) {
            UserInfomVo infom = MyAppliction.getInstance().getUserInfom();
            if (infom == null || infom.getData().getUser() == null) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            delectShare();
            final Intent intent = GenuineActivity.newInstance(BaseActivity.this, shareParems.code);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 300);
        }
    }

    //增值服务
    public class Add_Add_Vaule implements ShareActivity {

        @Override
        public void startAct(ShareParems shareParems) {
            UserInfomVo infom = MyAppliction.getInstance().getUserInfom();
            if (infom == null || infom.getData().getUser() == null) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            delectShare();
            final Intent intent = AddVauleActivity.newInstance(BaseActivity.this, shareParems.code);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 300);
        }
    }


    public void doIntentAct(ShareActivity activity, ShareParems shareParems) {
        activity.startAct(shareParems);
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

    public void T_ERROR(Context context) {
        T.showToast(context, context.getResources().getString(R.string.net_error));
    }

    public void T_ERROR(Context context, String Msg) {
        T.showToast(context, Msg);
    }

    /**
     * 设置布局
     *
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
     *
     * @param context
     * @param view
     */
    protected GridLayoutManager getGridLayoutManger(Context context, RecyclerView view, int number) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, number);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        return layoutManager;
    }

    protected void setWebVIewSetting(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        //加载网络数据，不读取本地
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
 /*       //开启缓存
        settings.setDomStorageEnabled(true);*/
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }
    /**
     * 获取对应图片drable
     * @param context
     * @param id
     * @return
     */
    protected Drawable getDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

}
