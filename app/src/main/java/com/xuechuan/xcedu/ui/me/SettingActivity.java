package com.xuechuan.xcedu.ui.me;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.vector.update_app.service.DownloadService;
import com.vector.update_app.utils.AppUpdateUtils;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.jg.RegisterTag;
import com.xuechuan.xcedu.mvp.contract.SettingViewContract;
import com.xuechuan.xcedu.mvp.model.SettingViewModel;
import com.xuechuan.xcedu.mvp.presenter.SettingViewPresenter;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.CProgressDialogUtils;
import com.xuechuan.xcedu.utils.CompareVersionUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.HProgressDialogUtils;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.LocaUtil;
import com.xuechuan.xcedu.utils.OkGoUpdateHttpUtil;
import com.xuechuan.xcedu.utils.SaveSelectFGUtil;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.AppUpDataVo;
import com.xuechuan.xcedu.vo.BindWeiXinVo;
import com.xuechuan.xcedu.vo.SelectVideoFGVo;
import com.youzan.androidsdk.YouzanSDK;

import java.io.File;
import java.util.Date;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SettingActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 设置界面
 * @author: L-BackPacker
 * @date: 2018/5/22 9:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, SettingViewContract.View {

    private TextView mTvMSettingWeixin;
    private LinearLayout mLlMSettingBindWei;
    private TextView mTvMSettingPaw;
    private TextView mTvMSettingCode;
    private LinearLayout mLlMSettingUpdata;
    private TextView mTvMSettingAbout;
    private Button mBtnBSOut;
    private Context mContext;
    private SettingViewPresenter mPresenter;
    private boolean isShowDownloadProgress;
    private IWXAPI api;
    private BroadcastReceiver receiver;
    private TextView mTvMSettingAddress;
    private CheckBox mChbSelectDown;
    private CheckBox mChbSelectPaly;
    private TextView mTvMSettingClear;
    private TextView mTvMSettiongCache;
    private LinearLayout mLlMSettintClear;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }*/

    public static String WEIXINNAME = "weixinname";
    private String mName;
    private LocaUtil mlocaUtil;
    private boolean isFGD =false;
    private boolean isFGP=false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        if (getIntent() != null) {
            mName = getIntent().getStringExtra(WEIXINNAME);
        }
        initView();
        initData();
        regToWx();
        initWeiXinResult();
        initCache();
    }

    private void initCache() {
        if (mlocaUtil == null)
            mlocaUtil = LocaUtil.get_Instance(mContext);
        mTvMSettiongCache.setText(mlocaUtil.getCacheSize());
    }

    private void initWeiXinResult() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DataMessageVo.WEI_LOGIN_ACTION);
        //                    requestLogin(extra, code);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    String extra = intent.getStringExtra(DataMessageVo.WEISTATE);
                    String code = intent.getStringExtra(DataMessageVo.WEICODE);
                    mPresenter.submitBindWeiXin(mContext, code);
                }
            }
        };
        registerReceiver(receiver, filter);

    }

    private void initData() {
        String versionCode = Utils.getVersionName(mContext);
        mTvMSettingCode.setText(versionCode);
        mTvMSettingWeixin.setText(mName);
        mPresenter = new SettingViewPresenter();
        mPresenter.initModelView(new SettingViewModel(), this);
       final SaveSelectFGUtil saveSelectFGUtil = SaveSelectFGUtil.getInstance();
        if (saveSelectFGUtil != null) {
            SelectVideoFGVo sfg = saveSelectFGUtil.getSaveFG();
            if (sfg != null) {
                    isFGP =sfg.isSaveFGP();
                    isFGD =sfg.isSaveFGD();
            }
        }
        mChbSelectPaly.setChecked(isFGP);
        mChbSelectDown.setChecked(isFGD);
        mChbSelectDown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//下载
                if (saveSelectFGUtil != null) {
                    SelectVideoFGVo vo = new SelectVideoFGVo();
                    isFGD=isChecked;
                    vo.setSaveFGD(isChecked);
                    vo.setSaveFGP(isFGP);
                    saveSelectFGUtil.putSelectFGVo(vo);
                    MyAppliction.getInstance().saveSelectNet(isChecked?DataMessageVo.MONET:"");
                }
            }
        });
        mChbSelectPaly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//播放
                if (saveSelectFGUtil != null) {
                        SelectVideoFGVo vo1 = new SelectVideoFGVo();
                        isFGP=isChecked;
                        vo1.setSaveFGP(isChecked);
                        vo1.setSaveFGD(isFGD);
                        saveSelectFGUtil.putSelectFGVo(vo1);
                        MyAppliction.getInstance().saveSelectNet(isChecked?DataMessageVo.MONET:"");
                }
            }
        });

    }

    //注册微信
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(mContext, DataMessageVo.APP_ID, true);
        api.registerApp(DataMessageVo.APP_ID);
    }

    private void initView() {
        mContext = this;
        mTvMSettingWeixin = (TextView) findViewById(R.id.tv_m_setting_weixin);
        mLlMSettingBindWei = (LinearLayout) findViewById(R.id.ll_m_setting_bindWei);
        mLlMSettingBindWei.setOnClickListener(this);
        mTvMSettingPaw = (TextView) findViewById(R.id.tv_m_setting_paw);
        mTvMSettingPaw.setOnClickListener(this);
        mTvMSettingCode = (TextView) findViewById(R.id.tv_m_setting_code);
        mLlMSettingUpdata = (LinearLayout) findViewById(R.id.ll_m_setting_updata);
        mLlMSettingUpdata.setOnClickListener(this);
        mTvMSettingAbout = (TextView) findViewById(R.id.tv_m_setting_about);
        mTvMSettingAbout.setOnClickListener(this);
        mBtnBSOut = (Button) findViewById(R.id.btn_b_s_out);
        mBtnBSOut.setOnClickListener(this);
        mTvMSettingAddress = (TextView) findViewById(R.id.tv_m_setting_address);
        mTvMSettingAddress.setOnClickListener(this);
        mChbSelectDown = (CheckBox) findViewById(R.id.chb_select_down);
        mChbSelectPaly = (CheckBox) findViewById(R.id.chb_select_paly);
        mTvMSettiongCache = (TextView) findViewById(R.id.tv_m_settiong_cache);
        mLlMSettintClear = (LinearLayout) findViewById(R.id.ll_m_settint_clear);
        mLlMSettintClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_m_setting_about://关于
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                intent.putExtra(AddVauleActivity.CSTR_EXTRA_TITLE_STR, getStringWithId(R.string.about));
                startActivity(intent);

                break;
            case R.id.ll_m_setting_updata://更新
                mPresenter.requestAppCode(mContext);
                upDataApp();
                break;

            case R.id.tv_m_setting_paw://修改密码
                startActivity(new Intent(SettingActivity.this, PawChangerActivity.class));
                break;
            case R.id.ll_m_setting_bindWei://绑定微信
                if (!StringUtil.isEmpty(mName)) {
                    return;
                }
                if (api.isWXAppInstalled()) {
                    loginWeiXin();
                } else {
                    T.showToast(mContext, "请先安装微信");
                }
                break;
            case R.id.btn_b_s_out:
                DialogUtil dialogUtil = DialogUtil.getInstance();
                dialogUtil.showTitleDialog(mContext, getString(R.string.sure_out),
                        getStringWithId(R.string.sure), getStringWithId(R.string.cancel), true);
                dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        out();
                    }

                    @Override
                    public void onCancelClickListener() {

                    }
                });
                break;
            case R.id.tv_m_setting_address://地址管理
                Intent intent1 = AddressMangerActivity.newInstance(mContext, AddressMangerActivity.TAG_MANGE);
                startActivity(intent1);
                break;
            case R.id.ll_m_settint_clear://清楚缓存
                mlocaUtil.clearAppCache();
                T.showToast(mContext, "清理缓存成功");
                initCache();
                break;


        }
    }

    private void out() {
        mPresenter.requestOutLogin(mContext);
        MyAppliction.getInstance().setUserInfom(null);
        SaveUUidUtil.getInstance().delectUUid();
        PolyvDownloaderManager.stopAll();
        //退出有赞
        YouzanSDK.userLogout(mContext);
        MyAppliction.getInstance().setUserData(null);
//        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        RegisterTag tag = RegisterTag.getInstance(getApplicationContext());
        tag.cancleTagAndAlias();
        this.finish();
    }

    //调用微信
    private void loginWeiXin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    /**
     * 更新版本
     */
    private void upDataApp() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xuechuan/" + new Date().getTime();
        String hear = mContext.getResources().getString(R.string.app_content_heat);
        String updata = mContext.getResources().getString(R.string.http_upApp);
        String url = hear.concat(updata);
        new UpdateAppManager.Builder()
                .setActivity(SettingActivity.this)
                .setHttpManager(new OkGoUpdateHttpUtil())
                .setUpdateUrl(url)
                .setPost(false)
                .setTopPic(R.mipmap.pop_img_update)
                .setTargetPath(path)
                .setThemeColor(0xf1e4655d)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                    @Override
                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {

                    }
                })
                .build()
                .checkNewApp(new UpdateCallback() {
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        try {
                            new JsonParser().parse(json);
                            OkGo.getInstance().cancelTag(mContext);
                        } catch (JsonParseException e) {
                            L.e("数据异常");
                            T.showToast(mContext, "服务器正在更新,请稍候点击");
                            e.printStackTrace();
                            return null;
                        }
                        Gson gson = new Gson();

                        AppUpDataVo vo = gson.fromJson(json, AppUpDataVo.class);
                        if (vo.getStatus().getCode() == 200) {
                            UpdateAppBean updateAppBean = new UpdateAppBean();
                            boolean isConstraint = false;
                            AppUpDataVo.DataBean data = vo.getData();
                            String versionCode = Utils.getVersionName(mContext);
                            int i = CompareVersionUtil.compareVersion(data.getVersion(), versionCode);
                            String updata = "No";
                            if (i == 0 || i == -1) {
                                updata = "No";
                            } else if (i == 1) {
                                updata = "Yes";
                                if (data.getType().equals("0")) {
                                    isConstraint = false;
                                } else if (data.getType().equals("1")) {
                                    isConstraint = true;
                                }
                            }
                            updateAppBean.setApkFileUrl(data.getUrl())
                                    //（必须）是否更新Yes,No
                                    .setUpdate(updata)
                                    .setNewVersion(data.getVersion())
                                    //（必须）下载地址
                                    .setApkFileUrl(data.getUrl())
                                    //（必须）更新内容
                                    .setUpdateLog(data.getDepict())
                                    //大小，不设置不显示大小，可以不设置
                                    .setTargetSize(data.getAppsize())
                                    //是否强制更新，可以不设置
                                    .setConstraint(isConstraint);
                            return updateAppBean;
                        } else {
                            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
                            L.e(vo.getStatus().getMessage());
                        }
                        return null;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
//                        super.hasNewApp(updateApp, updateAppManager);
                        updateAppManager.showDialogFragment();
//                        showDiyDialog(updateApp, updateAppManager);
                    }

                    @Override
                    protected void onAfter() {
                        CProgressDialogUtils.cancelProgressDialog(SettingActivity.this);
                    }

                    @Override
                    protected void noNewApp(String error) {
                        T.showToast(mContext, getString(R.string.latest_version));
                    }

                    @Override
                    protected void onBefore() {
                        CProgressDialogUtils.showProgressDialog(SettingActivity.this);
                    }
                });
    }

    @Override
    public void AppCodeSuccess(String cont) {
        L.d("======请求app====" + cont);
        Gson gson = new Gson();
        AppUpDataVo vo = gson.fromJson(cont, AppUpDataVo.class);
        if (vo.getStatus().getCode() == 200) {
            AppUpDataVo.DataBean data = vo.getData();
        } else {
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void AppCodeError(String msg) {
        L.d("======请求错误app====" + msg);
        L.e(msg);
    }

    @Override
    public void submitBindWeiXin(String com) {
        Gson gson = new Gson();
        BindWeiXinVo vo = gson.fromJson(com, BindWeiXinVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                String nickname = vo.getData().getUser().getNickname();
                mTvMSettingWeixin.setText(nickname);
                T.showToast(mContext, getString(R.string.bindSuccess));
            } else {
                T.showToast(mContext, vo.getData().getMessage());
            }
        } else {
            String message = vo.getStatus().getMessage();
            L.e(message);
            T.showToast(mContext, getStringWithId(R.string.net_error));
        }

    }

    @Override
    public void submitBindWeiXinError(String com) {
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    @Override
    public void OutSuccess(String com) {

    }

    @Override
    public void OutError(String msg) {

    }

    /**
     * 自定义对话框
     *
     * @param updateApp
     * @param updateAppManager
     */
    private void showDiyDialog(final UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
        String targetSize = updateApp.getTargetSize();
        String updateLog = updateApp.getUpdateLog();

        String msg = "";

        if (!TextUtils.isEmpty(targetSize)) {
            msg = "新版本大小：" + targetSize + "\n\n";
        }

        if (!TextUtils.isEmpty(updateLog)) {
            msg += updateLog;
        }

        new AlertDialog.Builder(this)
                .setTitle(String.format("是否升级到%s版本？", updateApp.getNewVersion()))
                .setMessage(msg)
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //显示下载进度
                        if (isShowDownloadProgress) {
                            updateAppManager.download(new DownloadService.DownloadCallback() {
                                @Override
                                public void onStart() {
                                    HProgressDialogUtils.showHorizontalProgressDialog(SettingActivity.this, "下载进度", false);
                                }

                                /**
                                 * 进度
                                 *
                                 * @param progress  进度 0.00 -1.00 ，总大小
                                 * @param totalSize 总大小 单位B
                                 */
                                @Override
                                public void onProgress(float progress, long totalSize) {
                                    HProgressDialogUtils.setProgress(Math.round(progress * 100));
                                }

                                /**
                                 *
                                 * @param total 总大小 单位B
                                 */
                                @Override
                                public void setMax(long total) {
                                }

                                @Override
                                public boolean onFinish(File file) {
                                    HProgressDialogUtils.cancel();
                                    return true;
                                }

                                @Override
                                public void onError(String msg) {
                                    Toast.makeText(SettingActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    HProgressDialogUtils.cancel();

                                }

                                @Override
                                public boolean onInstallAppAndAppOnForeground(File file) {
                                    return false;
                                }
                            });
                        } else {
                            //不显示下载进度
                            updateAppManager.download();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("===", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        switch (resultCode) {
            case Activity.RESULT_CANCELED:
                switch (requestCode) {
                    // 得到通过UpdateDialogFragment默认dialog方式安装，用户取消安装的回调通知，以便用户自己去判断，比如这个更新如果是强制的，但是用户下载之后取消了，在这里发起相应的操作
                    case AppUpdateUtils.REQ_CODE_INSTALL_APP:
//                        T.showToast(SettingActivity.this, "用户取消了安装包的更新");
                        break;
                }
                break;
            default:
        }
    }

}
