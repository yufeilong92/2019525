package com.xuechuan.xcedu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.db.UpDataNoteDb;
import com.xuechuan.xcedu.db.UpDataNoteDbDao;
import com.xuechuan.xcedu.event.ShowItemEvent;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.home.MyTagPagerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DbHelp.DBHelper;
import com.xuechuan.xcedu.flyn.Eyes;
import com.xuechuan.xcedu.fragment.bank.newbank.BankTestHomeFragment;
import com.xuechuan.xcedu.fragment.home.HomesFragment;
import com.xuechuan.xcedu.fragment.me.PersionalFragment;
import com.xuechuan.xcedu.fragment.net.NetVideoHomeFragment;
import com.xuechuan.xcedu.fragment.wenda.AnswerFragment;
import com.xuechuan.xcedu.mvp.contract.ConfigContract;
import com.xuechuan.xcedu.mvp.contract.PersionInfomContract;
import com.xuechuan.xcedu.mvp.contract.VideoBooksContract;
import com.xuechuan.xcedu.mvp.model.CollectSubmitModel;
import com.xuechuan.xcedu.mvp.model.ConfigModel;
import com.xuechuan.xcedu.mvp.model.ErrorSubmtModel;
import com.xuechuan.xcedu.mvp.model.MyVideoBooksModel;
import com.xuechuan.xcedu.mvp.model.PersionInfomModel;
import com.xuechuan.xcedu.mvp.model.RequestCollectModel;
import com.xuechuan.xcedu.mvp.model.RqeustErrorModel;
import com.xuechuan.xcedu.mvp.presenter.CollectSubmitPresenter;
import com.xuechuan.xcedu.mvp.presenter.ConfigPresenter;
import com.xuechuan.xcedu.mvp.presenter.ErrorSubmtPresenter;
import com.xuechuan.xcedu.mvp.presenter.PersionInfomPresenter;
import com.xuechuan.xcedu.mvp.presenter.RequestCollectPresenter;
import com.xuechuan.xcedu.mvp.presenter.RequestErrorPresenter;
import com.xuechuan.xcedu.mvp.presenter.VideoInfomsPresenter;
import com.xuechuan.xcedu.service.RequestCollectService;
import com.xuechuan.xcedu.service.RequestErrorService;
import com.xuechuan.xcedu.service.RequestProgressService;
import com.xuechuan.xcedu.service.SubmitCollectService;
import com.xuechuan.xcedu.service.SubmitErrorService;
import com.xuechuan.xcedu.service.SubmitHistoryService;
import com.xuechuan.xcedu.service.SubmitProgressService;
import com.xuechuan.xcedu.service.UpDataDbService;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.SubmiteLogHelp;
import com.xuechuan.xcedu.sqlitedb.UpCollectSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDeleteCollectSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDeleteErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.ui.me.TakeDeliveryActivity;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.CProgressDialogUtils;
import com.xuechuan.xcedu.utils.CompareVersionUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.OkGoUpdateHttpUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.AppUpDataVo;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.NetBookTableVo;
import com.xuechuan.xcedu.vo.PerInfomVo;
import com.xuechuan.xcedu.vo.SqliteVo.SubmitLogVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.vo.VideoSettingVo;
import com.xuechuan.xcedu.weight.NoScrollViewPager;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdk.basic.YouzanBasicSDKAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: HomeActivity
 * @Package com.xuechuan.xcedu
 * @Description: 主页
 * @author: L-BackPacker
 * @date: 2018/4/17 8:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/17
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, ConfigContract.View, VideoBooksContract.View, PersionInfomContract.View {

    private ArrayList<Fragment> mFragmentLists;
    private FragmentManager mSfm;
    private Context mContext;
    /**
     * 填充的布局
     */
    private int mFragmentLayout = R.id.fl_content;
    private static String PARAMS = "Params";
    private static String TYPE = "type";
    public final static String BOOK = "1";
    public final static String VIDEO = "2";
    private String mType;
    private NoScrollViewPager mViewpageContetn;
    private MagicIndicator mMagicindicatorHome;
    public final static String LOGIN_HOME = "loginhome";
    private String mLoginType;
    public final static String mHomeMeType = "4";
    public final static String Type = "4";
    private PersionInfomPresenter mPresenter;
    private ConfigPresenter mConfigPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        OkGo.getInstance().cancelTag(mContext);
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }


    public static void newInstance(Context context, String params1) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(PARAMS, params1);
        context.startActivity(intent);
    }

    public static void startInstance(Context context, String type) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        Eyes.translucentStatusBar(this, false);
        showBar(false);
        setContentView(R.layout.activity_home);
        if (getIntent() != null) {
            mType = getIntent().getStringExtra(TYPE);
            mLoginType = getIntent().getStringExtra(PARAMS);
        }
        DBHelper.initDb(MyAppliction.getInstance());
        initView();
        initData();
        initMagicIndicator1();
        doShareActivity();
        requetUserinfom();
        requestAppConfig();
        submiteDoBank();
    }

    public void showBar(boolean show) {

    }

    private void submiteDoBank() {
        UserInfomDbHelp help = UserInfomDbHelp.get_Instance(mContext);
        final UserInfomSqliteVo userInfomVo = help.findUserInfomVo();
        if (userInfomVo == null) {
            return;
        }
        SubmiteLogHelp submiteLogHelp = SubmiteLogHelp.get_Instance(mContext);
        final SubmitLogVo vo = submiteLogHelp.queryIsAdd(userInfomVo.getSaffid());
        if (vo == null || StringUtil.isEmpty(vo.getDobanktime())) {//第一次做题记录
            SubmitHistoryService.startActionFoo(mContext, userInfomVo.getSaffid());
        } else {
            String dobanktime = vo.getDobanktime();
            boolean more = TimeUtil.isMore(dobanktime);
            if (more) {
                SubmitHistoryService.startActionFoo(mContext, userInfomVo.getSaffid());
            }
        }
        if (vo == null || StringUtil.isEmpty(vo.getCollecttime())) {//收藏
            SubmitCollectService.startActionFoo(mContext, userInfomVo.getSaffid());
        } else {
            String collecttime = vo.getCollecttime();
            boolean more = TimeUtil.isMore(collecttime);
            if (more) {
                SubmitCollectService.startActionFoo(mContext, userInfomVo.getSaffid());
            }
        }
        if (vo == null || StringUtil.isEmpty(vo.getErrortime())) {//错题
            SubmitErrorService.startActionFoo(mContext, userInfomVo.getSaffid());
        } else {
            String errortime = vo.getErrortime();
            boolean more = TimeUtil.isMore(errortime);
            if (more) {
                SubmitErrorService.startActionFoo(mContext, userInfomVo.getSaffid());
            }
        }
        RequestProgressService.startActionFoo(mContext, userInfomVo.getSaffid());


    }


    private void requestAppConfig() {
        mConfigPresenter = new ConfigPresenter();
        mConfigPresenter.initModelView(new ConfigModel(), this);
        mConfigPresenter.requestConfig(mContext);
    }

    private void requetUserinfom() {
        mPresenter = new PersionInfomPresenter();
        mPresenter.basePresenter(new PersionInfomModel(), this);
        mPresenter.reqeustMInfo(mContext);
    }

    /**
     * 更新版本
     */
    private void initAppUpData() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xuechuan/" + new Date().getTime();
        String hear = mContext.getResources().getString(R.string.app_content_heat);
        String updata = mContext.getResources().getString(R.string.http_upApp);
        String url = hear.concat(updata);
        new UpdateAppManager.Builder()
                .setActivity(HomeActivity.this)
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
                            String versionCode = Utils.getVersionName(HomeActivity.this);
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
                        CProgressDialogUtils.cancelProgressDialog(HomeActivity.this);
                    }

                    @Override
                    protected void noNewApp(String error) {
//                        T.showToast(mContext, getString(R.string.latest_version));
                    }

                    @Override
                    protected void onBefore() {
                        CProgressDialogUtils.showProgressDialog(HomeActivity.this);
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MianSelectShowItem(ShowItemEvent event) {
        if (mViewpageContetn != null)
            mViewpageContetn.setCurrentItem(0);
        doShareActivity();

    }

    private void doShareActivity() {
        if (!StringUtil.isEmpty(MyAppliction.getInstance().getIsAtricle())) {
            if (MyAppliction.getInstance().getIsAtricle().equals("0")) {
                doIntentAct(new Infom(), MyAppliction.getInstance().getShareParems());
                return;
            }
            if (MyAppliction.getInstance().getIsAtricle().equals("1")) {
                doIntentAct(new WenZhang(), MyAppliction.getInstance().getShareParems());
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!StringUtil.isEmpty(mType)) {
            if (mType.equals(BOOK)) {
                mViewpageContetn.setCurrentItem(1);
            } else if (mType.equals(VIDEO)) {
                mViewpageContetn.setCurrentItem(2);
            }
        }/* else if (!StringUtil.isEmpty(mLoginType) && mLoginType.equals(LOGIN_HOME)) {
            mViewpageContetn.setCurrentItem(0);
        }*/ else if (!StringUtil.isEmpty(mType) && mType.equals(mHomeMeType)) {
            mViewpageContetn.setCurrentItem(3);
        }
        if (mConfigPresenter != null)
            mConfigPresenter.requestConfig(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginType = "";
        mType = "";
    }

    private void initData() {
        List<Fragment> fragments = getcreateFragment();
        MyTagPagerAdapter adapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
        mViewpageContetn.setAdapter(adapter);
        upDataDb();

    }

    private void initMagicIndicator1() {
        final ArrayList<String> list = ArrayToListUtil.arraytoList(mContext, R.array.home_order_title);
        mMagicindicatorHome.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
                final ImageView radioButton = (ImageView) customLayout.findViewById(R.id.rdb_home);
                final TextView titleText = (TextView) customLayout.findViewById(R.id.title_text);
                if (index == 0) {
                    radioButton.setImageResource(R.drawable.tab_icon_home_n);
                } else if (index == 1) {
                    radioButton.setImageResource(R.drawable.tab_icon_ex_n);
                } else if (index == 2) {
                    radioButton.setImageResource(R.drawable.tab_icon_class_n);
                } else if (index == 3) {
                    radioButton.setImageResource(R.drawable.tab_icon_ans_na);
                } else if (index == 4) {
                    radioButton.setImageResource(R.drawable.tab_icon_me_n);
                }
                titleText.setText(list.get(index));
                commonPagerTitleView.setContentView(customLayout);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        switch (index) {
                            case 0:
                                radioButton.setImageResource(R.drawable.tab_icon_home_s);
                                break;
                            case 1:
                                radioButton.setImageResource(R.drawable.tab_icon_ex_s);
                                break;
                            case 2:

                                radioButton.setImageResource(R.drawable.tab_icon_class_s);
                                break;
                            case 3:
                                radioButton.setImageResource(R.drawable.tab_icon_ans_s);
                                break;
                            case 4:
                                radioButton.setImageResource(R.drawable.tab_icon_me_s);
                                break;
                        }
                        titleText.setTextColor(getResources().getColor(R.color.red_text));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        switch (index) {
                            case 0:
                                radioButton.setImageResource(R.drawable.tab_icon_home_n);
                                break;
                            case 1:
                                radioButton.setImageResource(R.drawable.tab_icon_ex_n);
                                break;
                            case 2:
                                radioButton.setImageResource(R.drawable.tab_icon_class_n);
                                break;
                            case 3:
                                radioButton.setImageResource(R.drawable.tab_icon_ans_na);
                                break;
                            case 4:
                                radioButton.setImageResource(R.drawable.tab_icon_me_n);
                                break;
                        }
                        titleText.setTextColor(getResources().getColor(R.color.text_fu_color));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                        //放大中setScaleX（）数据要小
                        titleText.setScaleX(1.3f + (1.0f - 1.3f) * leavePercent);
                        radioButton.setScaleY(1.3f + (1.0f - 1.3f) * leavePercent);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                        titleText.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
                        //setScaleY（）数据要小
                        radioButton.setScaleY(1.0f + (1.3f - 1.2f) * enterPercent);
                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewpageContetn.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMagicindicatorHome.setNavigator(commonNavigator);
        boolean pad = Utils.isPad(mContext);
        if (pad) {
            mViewpageContetn.setOffscreenPageLimit(2);
        } else {
            mViewpageContetn.setOffscreenPageLimit(4);
        }
        ViewPagerHelper.bind(mMagicindicatorHome, mViewpageContetn);
        mViewpageContetn.setCurrentItem(0);
    }


    protected void initView() {
        mContext = this;
        mViewpageContetn = (NoScrollViewPager) findViewById(R.id.viewpage_contetn);
        mViewpageContetn.setOnClickListener(this);
        mViewpageContetn.setPagingEnabled(false);
        mMagicindicatorHome = (MagicIndicator) findViewById(R.id.magicindicator_home);
        mMagicindicatorHome.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {

    }

    public List<Fragment> getcreateFragment() {
        List<Fragment> list = new ArrayList<>();
        HomesFragment homeFragment = new HomesFragment();
//        BankFragment bankFragment = new BankFragment();
        BankTestHomeFragment bankFragment = new BankTestHomeFragment();
        NetVideoHomeFragment netFragment = new NetVideoHomeFragment();
        AnswerFragment answerFragment = new AnswerFragment();
        PersionalFragment persionalFragment = new PersionalFragment();
        list.add(homeFragment);
        list.add(bankFragment);
        list.add(netFragment);
        list.add(answerFragment);
        list.add(persionalFragment);
        return list;
    }

    private void upDataDb() {
        UpDataNoteDbDao dao = DBHelper.getDaoSession().getUpDataNoteDbDao();
        if (dao == null) return;
        List<UpDataNoteDb> list = dao.queryBuilder().list();
        if (list == null || list.isEmpty()) return;
        UpDataNoteDb unique = list.get(0);
        boolean isupdata = unique.getIsupdata();
        DbHelperDownAssist mDao = DbHelperDownAssist.getInstance(mContext);
        List<DownVideoDb> downLists = mDao.queryUserDownInfom();
        boolean updataSuceess = true;
        //网络延迟没有更新上去重新更新
        if (downLists != null && !downLists.isEmpty()) {
            for (int i = 0; i < downLists.size(); i++) {
                DownVideoDb db = downLists.get(i);
                List<DownVideoVo> downlist = db.getDownlist();
                if (downlist != null && !downlist.isEmpty()) {
                    for (int k = 0; k < downlist.size(); k++) {
                        DownVideoVo vo = downlist.get(k);
                        if (StringUtil.isEmpty(vo.getpName()) || unique.getUpatanote().equals(DataMessageVo.UPDATATAYPE)) {
                            updataSuceess = false;
                        }
                    }
                }
            }
        }
        //防止后续升级数据库
        if (unique.getUpatanote().equals(DataMessageVo.UPDATATAYPEONE)) {
            updataSuceess = true;
        }
        if (isupdata && updataSuceess) return;
        int oldVersion = unique.getOldVersion();
        if (oldVersion == 1) {
            Log.e("===", "upDataDb: 排序开始啦");
            if (downLists == null || downLists.isEmpty()) return;
            for (int i = 0; i < downLists.size(); i++) {
                final DownVideoDb db = downLists.get(i);
                List<DownVideoVo> downlist = db.getDownlist();
                if (downlist != null && !downlist.isEmpty()) {
                    final VideoInfomsPresenter mInfomsPresenter = new VideoInfomsPresenter();
                    mInfomsPresenter.initModelView(new MyVideoBooksModel(), this);
                    synchronized (this) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                mInfomsPresenter.requestBookInfoms(mContext, db.getKid());
                            }
                        }, 300);
                    }
                }
            }
        }

    }

    @Override
    public void BookInfomSucces(String com) {
        Gson gson = new Gson();
        NetBookTableVo tableVo = gson.fromJson(com, NetBookTableVo.class);
        if (tableVo != null && tableVo.getData() != null)
            if (tableVo.getStatus().getCode() == 200) {
                UpDataNoteDbDao dao = DBHelper.getDaoSession().getUpDataNoteDbDao();
                if (dao == null) return;
                List<UpDataNoteDb> list = dao.queryBuilder().list();
                if (list == null || list.isEmpty()) return;
                UpDataNoteDb unique = list.get(0);
                if (unique.getIsupdata()) {
                    unique.setUpatanote(DataMessageVo.UPDATATAYPEONE);
                    unique.setIsupdata(true);
                    dao.update(unique);
                }
                NetBookTableVo.DataBean bean = tableVo.getData();
                UpDataDbService.startActionFoo(mContext, String.valueOf(bean.getClassX().getId()), bean.getChapters());
            } else {
                T_ERROR(mContext);
                UpDataNoteDbDao dao = DBHelper.getDaoSession().getUpDataNoteDbDao();
                List<UpDataNoteDb> list = dao.queryBuilder().list();
                if (list == null || list.isEmpty()) return;
                UpDataNoteDb unique = list.get(0);
                unique.setIsupdata(false);
                unique.setUpatanote(DataMessageVo.UPDATATAYPE);
                dao.update(unique);
                L.e(tableVo.getStatus().getMessage());
            }
    }

    @Override
    public void BookInfomError(String msgt) {
        UpDataNoteDbDao dao = DBHelper.getDaoSession().getUpDataNoteDbDao();
        List<UpDataNoteDb> list = dao.queryBuilder().list();
        if (list == null || list.isEmpty()) return;
        UpDataNoteDb unique = list.get(0);
        unique.setIsupdata(false);
        unique.setUpatanote(DataMessageVo.UPDATATAYPE);
        dao.update(unique);
    }

    @Override
    public void InfomSuccess(String cont) {
        L.d(cont);
        Gson gson = new Gson();
        PerInfomVo vo = gson.fromJson(cont, PerInfomVo.class);
        if (vo == null) return;
        if (vo.getStatus() != null && vo.getStatus().getCode() == 200) {
            MyAppliction.getInstance().setUserData(vo);
            boolean ishavewaitaddress = vo.getData().isIshavewaitaddress();
            if (ishavewaitaddress) {
                showDailog();
            } else {
                initAppUpData();
            }
        } else {
            T_ERROR(mContext);
        }

    }

    @Override
    public void InfomError(String cont) {
        T_ERROR(mContext);
    }

    /**
     * 显示订单确认按钮
     */
    private void showDailog() {
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, getString(R.string.order_infom_1), getStringWithId(R.string.sure),
                getStringWithId(R.string.cancel), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                Intent intent = TakeDeliveryActivity.newInstance(mContext, "");
                startActivity(intent);
            }

            @Override
            public void onCancelClickListener() {
                initAppUpData();
            }
        });

    }

    /**
     * 初始app配置
     *
     * @param success
     */
    @Override
    public void ConfigSuccess(String success) {
        Gson gson = new Gson();
        VideoSettingVo vo = gson.fromJson(success, VideoSettingVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData() == null) {
                return;
            }
            YouzanSDK.init(mContext, vo.getData().getYouzanappsetting().getClient_id(), new YouzanBasicSDKAdapter());
            MyAppliction.getInstance().saveVideoSetting(vo.getData());
        }
    }

    @Override
    public void ConfigError(String msg) {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_UP) {
            T.showToast(mContext, getStringWithId(R.string.out));
            if (Utils.handleOnDoubleClick()) {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
