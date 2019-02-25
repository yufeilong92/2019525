package com.xuechuan.xcedu.ui.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StatFs;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easefun.polyvsdk.PolyvDownloader;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.easefun.polyvsdk.download.util.PolyvDownloaderUtils;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.net.DownNetBookAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.mvp.model.NetDownIngModelImpl;
import com.xuechuan.xcedu.mvp.presenter.NetDownIngPresenter;
import com.xuechuan.xcedu.mvp.view.NetDownIngView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.NetworkToolUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.DownInfomSelectVo;
import com.xuechuan.xcedu.vo.ResultVo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookDowningActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 正在下载界面
 * @author: L-BackPacker
 * @date: 2018/5/20 16:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/20
 */
public class NetBookDowningActivity extends BaseActivity implements View.OnClickListener, NetDownIngView {

    private static final String TETYWIFI = "wifi";
    private TextView mTvNetDowningMake;
    private TextView mTvNetDowningDo;
    private TextView mTvDowning;
    //    private RecyclerView mRlvNetDowningList;
    private ListView mRlvNetDowningList;
    private TextView mTvNetDowningStop;
    private CheckBox mChbNetDownAll;
    private Button mBtnNetDownDelect;
    private RelativeLayout mRlNetDownDelect;
    private Context mContext;
    private TextView mTvNetDownEmpty;
    //有网
    private String MNETWORK = "network";
    //无网
    private String NOTETWORK = "notnet";

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_book_downing);
        initView();
    }*/

    /**
     * 课目id
     */
    private static String KID = "KID";
    private String mKid;
    private DownVideoDb mDataBean;
    private List<DownInfomSelectVo> mDataSelectList;
    private DownNetBookAdapter mListAdapter;
    private DbHelperDownAssist mDao;
    private NetDownIngPresenter mPresenter;
    private int mFirst = 0;
    private MyNetBroadcast netBorect;
    private int NoDonw = 0;
    private boolean mIsRegisterBRO = false;

    public static Intent newInstance(Context context, String kid) {
        Intent intent = new Intent(context, NetBookDowningActivity.class);
        intent.putExtra(KID, kid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_book_downing);
        if (getIntent() != null) {
            mKid = getIntent().getStringExtra(KID);
        }
//        mDao = DbHelperDownAssist.getInstance();
        mDao = MyAppliction.getInstance().getDownDao();
        initView();
        mPresenter = new NetDownIngPresenter(new NetDownIngModelImpl(), this);
        initData();
        netBorect = new MyNetBroadcast();
        IntentFilter filter = new IntentFilter();
        mIsRegisterBRO = true;
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.intent.category.DEFAULT");
        registerReceiver(netBorect, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsRegisterBRO) {
            mIsRegisterBRO = false;
            unregisterReceiver(netBorect);
        }
    }

    private void doComputeView() {
        computeStorage();
        mDataBean = mDao.queryUserDownInfomWithKid(mKid);
        if (mDataBean == null || mDataBean.getDownlist() == null || mDataBean.getDownlist().isEmpty()) {
            mTvNetDownEmpty.setVisibility(View.VISIBLE);
            mTvNetDowningMake.setText(getStringWithId(R.string.edit));
            mTvNetDowningMake.setVisibility(View.INVISIBLE);
            return;
        }
        boolean isDowning = false;
        List<DownVideoVo> downlist = mDataBean.getDownlist();
        for (int i = 0; i < downlist.size(); i++) {
            DownVideoVo vo = downlist.get(i);
            if (!vo.getStatus().equals("0"))
                isDowning = true;
        }
        if (!isDowning) {
            mTvNetDownEmpty.setVisibility(View.VISIBLE);
            mTvNetDowningMake.setText(getStringWithId(R.string.edit));
            mTvNetDowningMake.setVisibility(View.INVISIBLE);
            return;
        }

        mTvNetDownEmpty.setVisibility(View.GONE);
        mTvNetDowningMake.setVisibility(View.VISIBLE);
        int number = 0;
        if (mDataBean.getDownlist() != null && !mDataBean.getDownlist().isEmpty())
            for (int i = 0; i < mDataBean.getDownlist().size(); i++) {
                DownVideoVo vo = mDataBean.getDownlist().get(i);
                if (vo.getStatus().equals("0")) {
                    number += 1;
                }
            }

        else {
            mTvNetDownEmpty.setVisibility(View.VISIBLE);
            mTvNetDowningMake.setText(getStringWithId(R.string.edit));
            computeStorage();
            mTvNetDowningDo.setText(0 + "");
            return;
        }
        PolyvDownloaderManager.startAll(getApplicationContext());
        mTvNetDowningDo.setText(number + "");
    }

    private void initData() {
        doComputeView();
        if (mDataBean == null || mDataBean.getDownlist() == null || mDataBean.getDownlist().isEmpty()) {
            mTvNetDownEmpty.setVisibility(View.VISIBLE);
            mTvNetDowningMake.setText(getStringWithId(R.string.edit));
            mTvNetDowningMake.setVisibility(View.INVISIBLE);
            return;
        }
        mDataSelectList = new ArrayList<>();


        for (int i = 0; i < mDataBean.getDownlist().size(); i++) {
            DownVideoVo vo = mDataBean.getDownlist().get(i);
            if (vo.getStatus().equals("0")) {
                continue;
            } else {
                DownInfomSelectVo selectVo = new DownInfomSelectVo();
                selectVo.setPid(vo.getPid());
                selectVo.setZid(vo.getZid());
                selectVo.setVid(vo.getVid());
                selectVo.setBitrate(vo.getBitRate());
                selectVo.setChbSelect(false);
                selectVo.setShowChb(false);
                selectVo.setShowPlay(true);
                selectVo.setPlaySelect(false);
                mDataSelectList.add(selectVo);
            }
        }
      /*  for (DownVideoVo vo : mDataBean.getDownlist()) {
            DownInfomSelectVo selectVo = new DownInfomSelectVo();
            selectVo.setPid(vo.getPid());
            selectVo.setZid(vo.getZid());
            selectVo.setVid(vo.getVid());
            selectVo.setBitrate(vo.getBitRate());
            selectVo.setChbSelect(false);
            selectVo.setShowChb(false);
            selectVo.setShowPlay(true);
            selectVo.setPlaySelect(false);
            mDataSelectList.add(selectVo);
        }*/
        bindAdapter();
        mChbNetDownAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    initShow(false, false, true, true);
                } else {
                    initShow(false, false, true, false);
                }
                mListAdapter.notifyDataSetChanged();
            }
        });
        int number = 0;
        boolean isfale = false;
        for (int i = 0; i < mDataBean.getDownlist().size(); i++) {
            DownVideoVo vo = mDataBean.getDownlist().get(i);
            if (vo.getStatus().equals("0")) {
                number += 1;
            } else if (vo.getStatus().equals("1") || vo.getStatus().equals("2")) {
                isfale = true;
            }
        }
//        for (DownVideoVo vo : mDataBean.getDownlist()) {
//            if (vo.getStatus().equals("0")) {
//                number += 1;
//            } else if (vo.getStatus().equals("1") || vo.getStatus().equals("2")) {
//                isfale = true;
//            }
//        }
        mTvNetDowningDo.setText(number + "");
        startDownAll(isfale);
    }

    private void bindAdapter() {
//        DownloadListViewAdapter adapter = new DownloadListViewAdapter(mDataBean, mContext, mRlvNetDowningList);

        mListAdapter = new DownNetBookAdapter(NetBookDowningActivity.this, mContext, copyData(mDataBean), mRlvNetDowningList, mDataSelectList);
        mRlvNetDowningList.setAdapter(mListAdapter);
        mListAdapter.setChbClickListener(new DownNetBookAdapter.onItemChbClickListener() {
            @Override
            public void onChecaListener(DownVideoVo db, boolean isCheck, int position) {
                if (mDataSelectList == null || mDataSelectList.isEmpty()) {
                    return;
                }
                if (isCheck) {
                    for (int i = 0; i < mDataSelectList.size(); i++) {
                        DownInfomSelectVo vo = mDataSelectList.get(i);
                        if (vo.getZid().equals(db.getZid())) {
                            vo.setChbSelect(true);
                        }
                    }

                } else {
                    for (int i = 0; i < mDataSelectList.size(); i++) {
                        DownInfomSelectVo vo = mDataSelectList.get(i);
                        if (vo.getZid().equals(db.getZid())) {
                            vo.setChbSelect(false
                            );
                        }
                    }

                }
                if (mListAdapter != null)
                    mListAdapter.notifyDataSetChanged();
            }
        });
        mListAdapter.setDownClickListener(new DownNetBookAdapter.onDownClickListener() {
            @Override
            public void onDownClickListener(String oid, int position) {
                mPresenter.submitVideo(mContext, oid, mKid);
//                DownVideoDb videoDb = DbHelperDownAssist.getInstance().queryUserDownInfomWithKid(mKid);
                DownVideoDb videoDb = MyAppliction.getInstance().getDownDao().queryUserDownInfomWithKid(mKid);
                if (videoDb != null) {
                    List<DownVideoVo> downlist = videoDb.getDownlist();
                    boolean isOver = false;
                    int number = 0;
                    if (downlist != null && !downlist.isEmpty()) {
                        for (int i = 0; i < downlist.size(); i++) {
                            DownVideoVo vo = downlist.get(i);
                            if (vo.getStatus().equals("0")) {
                                number += 1;
                            } else {
                                NoDonw += 0;
                                isOver = true;
                            }
                        }

                    }
                    if (isOver) {
                        mTvNetDowningStop.setText(R.string.stopdown);
                    } else {
                        mTvNetDowningStop.setText(R.string.start_down);
                    }
                    mTvNetDowningDo.setText(number + "");
                    computeStorage();
                }

            }
        });


    }

    /**
     * 开始下载，并提示
     *
     * @param isShowTos 是否提示
     */
    private void startDownAll(boolean isShowTos) {
        boolean isDownAll = false;
        if (mDataBean != null && mDataBean.getDownlist() != null && !mDataBean.getDownlist().isEmpty()) {
            List<DownVideoVo> downlist = mDataBean.getDownlist();
            for (int i = 0; i < downlist.size(); i++) {
                DownVideoVo videoVo = downlist.get(i);
                if (!videoVo.getStatus().equals("0")) {
                    isDownAll = true;
                    break;
                }
            }

            if (isDownAll) {//有还有没有下载的
                if (network()) return;
                mListAdapter.downloadAll();
                mTvNetDowningStop.setText(R.string.stopdown);
            } else {
                if (isShowTos) {
                    mTvNetDowningStop.setText(R.string.start_down);
                    T.showToast(mContext, getString(R.string.no_down_video));
                }
            }
        }
    }

    /**
     * 计算剩余内存
     */
    private void computeStorage() {
        long externalSize = 0;
        if (Utils.hasSDCard()) {
            File downloadDir = PolyvSDKClient.getInstance().getDownloadDir();
            StatFs statfs = new StatFs(downloadDir.getPath());
            long blocSize = statfs.getBlockSize();
            long availaBlock = statfs.getAvailableBlocks();
            externalSize = availaBlock * blocSize;
        } else {
            externalSize = Utils.getSystemAvailableSize();
        }
        String s = Utils.convertFileSizeB(externalSize);
        mTvDowning.setText(s);
    }

    private void initView() {
        mTvNetDowningMake = (TextView) findViewById(R.id.tv_net_downing_make);
        mTvNetDowningMake.setOnClickListener(this);
        mTvNetDowningDo = (TextView) findViewById(R.id.tv_net_downing_do);
        mTvDowning = (TextView) findViewById(R.id.tv_downing);
//        mRlvNetDowningList = (RecyclerView) findViewById(R.id.rlv_net_downing_list);
        mRlvNetDowningList = (ListView) findViewById(R.id.rlv_net_downing_list);
        mTvNetDowningStop = (TextView) findViewById(R.id.tv_net_downing_stop);
        mTvNetDowningStop.setOnClickListener(this);
        mChbNetDownAll = (CheckBox) findViewById(R.id.chb_net_down_all);
        mBtnNetDownDelect = (Button) findViewById(R.id.btn_net_down_delect);
        mBtnNetDownDelect.setOnClickListener(this);
        mRlNetDownDelect = (RelativeLayout) findViewById(R.id.rl_net_down_delect);
        mContext = this;
        mTvNetDownEmpty = (TextView) findViewById(R.id.tv_net_down_empty);
        mTvNetDownEmpty.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_down_delect://删除
                boolean isSelect = false;
                for (int i = 0; i < mDataSelectList.size(); i++) {
                    DownInfomSelectVo vo = mDataSelectList.get(i);
                    if (vo.isChbSelect()) {
                        isSelect = true;
                    }
                }
//                for (DownInfomSelectVo vo : mDataSelectList) {
//                    if (vo.isChbSelect()) {
//                        isSelect = true;
//                    }
//                }
                if (!isSelect) {
                    T.showToast(mContext, getString(R.string.please_del_video));
                    return;
                }
                DialogUtil dialogUtil = DialogUtil.getInstance();
                dialogUtil.showTitleDialog(mContext, getStringWithId(R.string.is_del)
                        , getStringWithId(R.string.delect), getStringWithId(R.string.cancel), true);
                dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        PolyvDownloaderManager.stopAll();
                        final AlertDialog dialog = DialogUtil.showDialog(mContext, "", "删除中...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < mDataSelectList.size(); i++) {
                                    DownInfomSelectVo selectVo = mDataSelectList.get(i);
                                    if (selectVo.isChbSelect()) {
                                        mDao.delectItem(mDataBean.getKid(), selectVo.getPid(), selectVo.getZid());
                                        delectVideo(selectVo.getVid(), selectVo.getBitrate());
                                    }
                                }
//                            initData(true);
                                doComputeView();
                                mListAdapter.setRefreshData(mDataBean);
                                mListAdapter.notifyDataSetChanged();

                                if (dialog != null && dialog.isShowing())
                                    dialog.dismiss();
                            }
                        }, 2000);
                        //选中的vid 集合
                    }

                    @Override
                    public void onCancelClickListener() {

                    }
                });
                break;
            case R.id.tv_net_downing_make://编辑
                String str = getTextStr(mTvNetDowningMake);
                if (str.equals(getString(R.string.edit))) {
                    setChickBoK(false);
                    mTvNetDowningMake.setText(R.string.complete);
                    mTvNetDowningStop.setVisibility(View.GONE);
                    mRlNetDownDelect.setVisibility(View.VISIBLE);
                    if (mDataSelectList != null && !mDataSelectList.isEmpty())
                        initShow(false, false, true, false);
                } else {
                    mTvNetDowningStop.setVisibility(View.VISIBLE);
                    mRlNetDownDelect.setVisibility(View.GONE);
                    mTvNetDowningMake.setText(R.string.edit);
                    if (mDataSelectList != null && !mDataSelectList.isEmpty()) {
                        initShow(true, false, false, false);
                    }
                }
                mListAdapter.notifyDataSetChanged();

                break;
            case R.id.tv_net_downing_stop://开始下载

                String start = getTextStr(mTvNetDowningStop);
                if (start.equals(getString(R.string.start_down))) {//开始下载
//                    mTvNetDowningStop.setText(R.string.stopdown);
                    if (network()) return;
                    startdown();
//                    mListAdapter.downloadAll();
                } else {//暂停下载
                    mTvNetDowningStop.setText(R.string.start_down);
                    mListAdapter.pauseAll();
                }

                break;
        }
    }

    /**
     * 开始下载
     */
    private void startdown() {
        startDownAll(true);
    }

    private void delectVideo(String vid, String bitrate) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String vid = strings[0];
                String bitrates = strings[1];
                PolyvDownloader downloader = PolyvDownloaderManager.clearPolyvDownload(vid, Integer.parseInt(bitrates));
                downloader.deleteVideo(vid, Integer.parseInt(bitrates));
                PolyvDownloaderUtils.deleteVideo(vid, Integer.parseInt(bitrates));
                return null;
            }
        };
        asyncTask.execute(vid, bitrate);
    }

    /**
     * @param paly       是否展示播放按钮
     * @param selectplay 是否选中
     * @param chb        是否展示选择
     * @param selelctchb 是否选中
     */
    private void initShow(boolean paly, boolean selectplay, boolean chb, boolean selelctchb) {
        for (int i = 0; i < mDataSelectList.size(); i++) {
            DownInfomSelectVo vo = mDataSelectList.get(i);
            vo.setShowPlay(paly);
            vo.setPlaySelect(selectplay);
            vo.setShowChb(chb);
            vo.setChbSelect(selelctchb);
        }
//        for (DownInfomSelectVo vo : mDataSelectList) {
//            vo.setShowPlay(paly);
//            vo.setPlaySelect(selectplay);
//            vo.setShowChb(chb);
//            vo.setChbSelect(selelctchb);
//        }
    }

    @Override
    public void submitSuccess(String con) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
        } else {
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void submitError(String con) {
        L.e(con);
    }

    private class MyNetBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("网络状态发生变化");
            //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                //获取ConnectivityManager对象对应的NetworkInfo对象
                //获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            /*if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                MyAppliction.getInstance().saveUserNetSatus("1");
//                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else*/
                if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
                    downNet(TETYWIFI);
//                Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                } else if (wifiNetworkInfo != null && dataNetworkInfo != null && !wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                    mFirst += 1;
                    downNet(MNETWORK);
                } else {
                    mFirst += 1;
                    mListAdapter.pauseAll();
                    mTvNetDowningStop.setText(R.string.start_down);
                    downNet(NOTETWORK);
//                    T.showToast(mContext, getStringWithId(R.string.net_error));
//                Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
                }
            } else {
                //这里的就不写了，前面有写，大同小异
                System.out.println("API level 大于21");
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
         /*   if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                MyAppliction.getInstance().saveUserNetSatus("1");
                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else */
                if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
                    downNet(TETYWIFI);
//  Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                } else if (wifiNetworkInfo != null && dataNetworkInfo != null && !wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    mFirst += 1;
                    downNet(MNETWORK);
//                Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                } else {
                    mFirst += 1;
                    mListAdapter.pauseAll();
                    mTvNetDowningStop.setText(R.string.start_down);
                    downNet(NOTETWORK);
//                    T.showToast(mContext, getStringWithId(R.string.net_error));
//                Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 下载
     */
    private void downNet(String type) {
        if (mFirst == 1) {
            return;
        }
        String net = MyAppliction.getInstance().getSelectNet();
        String trim = mTvNetDowningStop.getText().toString().trim();
        if (trim.equals(getStringWithId(R.string.start_down)))
            return;
        if (NoDonw == 0) {//下载数
            return;
        }
        if (type.equals(NOTETWORK)) {//没有网络
            mListAdapter.pauseAll();
            return;
        }
        if (type.equals(TETYWIFI)) {
            mListAdapter.downloadAll();
            return;
        }
        if (MyAppliction.getInstance().getSaveFGD()) {
            mListAdapter.downloadAll();
            return;
        }
        if (StringUtil.isEmpty(net)) {
            ShowDownNetDialog();
            return;
        }
        mListAdapter.downloadAll();


    }

    /**
     * 网络判断
     *
     * @return
     */
    private boolean network() {
        NetworkToolUtil toolUtil = NetworkToolUtil.getInstance(mContext);
        String stauts = toolUtil.getNetWorkToolStauts();
        if (stauts.equals(DataMessageVo.NONETWORK)) {
            T.showToast(mContext, getString(R.string.net_error_down));
            return true;
        }
        if (stauts.equals(DataMessageVo.MONET)) {//移动网络
            String net = MyAppliction.getInstance().getSelectNet();
            if (StringUtil.isEmpty(net)) {
                ShowDownNetDialog();
                return true;
            }
            if (net.equals(DataMessageVo.MONET)) {
                return false;
            }
        }
        return false;
    }

    /**
     * 下载提示
     */
    public void ShowDownNetDialog() {
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, getString(R.string.net_down), getStringWithId(R.string.sure)
                , getStringWithId(R.string.cancel), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                startdown();
                MyAppliction.getInstance().saveSelectNet(DataMessageVo.MONET);
            }

            @Override
            public void onCancelClickListener() {

            }
        });
    }

    /**
     * 过滤一下子的内容
     *
     * @param db
     * @return
     */
    public DownVideoDb copyData(DownVideoDb db) {
        DownVideoDb videoDb = new DownVideoDb();
        videoDb.setKid(db.getKid());
        videoDb.setId(db.getId());
        videoDb.setKName(db.getKName());
        videoDb.setStaffid(db.getStaffid());
        videoDb.setUrlImg(db.getUrlImg());
        videoDb.setTime(db.getTime());
        videoDb.setVid(db.getVid());
        if (db.getDownlist() != null && !db.getDownlist().isEmpty()) {
            ArrayList<DownVideoVo> videoLists = new ArrayList<>();
            for (int i = 0; i < db.getDownlist().size(); i++) {
                DownVideoVo videoVo = new DownVideoVo();
                DownVideoVo vo = db.getDownlist().get(i);
                if (!vo.getStatus().equals("0")) {
                    videoVo.setBitRate(vo.getBitRate());
                    videoVo.setDuration(vo.getDuration());
                    videoVo.setFileSize(vo.getFileSize());
                    videoVo.setPercent(vo.getPercent());
                    videoVo.setPid(vo.getPid());
                    videoVo.setStatus(vo.getStatus());
                    videoVo.setTitle(vo.getTitle());
                    videoVo.setTotal(vo.getTotal());
                    videoVo.setVid(vo.getVid());
                    videoVo.setVideoOid(vo.getVideoOid());
                    videoVo.setZid(vo.getZid());
                    videoLists.add(videoVo);
                }
            }
            videoDb.setDownlist(videoLists);
        }
        return videoDb;
    }

    private void setChickBoK(boolean isClick) {
        mChbNetDownAll.setChecked(isClick);
    }

}
