package com.xuechuan.xcedu.ui.net;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.easefun.polyvsdk.PolyvDownloader;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.download.util.PolyvDownloaderUtils;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.net.NetDownGoingAdapter;
import com.xuechuan.xcedu.adapter.net.NetDownOverAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.NetDownSelectVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookDownActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 下载课目缓存界面
 * @author: L-BackPacker
 * @date: 2018/5/16 11:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/16
 */
public class NetBookDownActivity extends BaseActivity implements View.OnClickListener {

    boolean isStartIntent = true;
    private Context mContext;
    private TextView mTvNetBookDownMake;
    private RecyclerView mRlvNetLoadingGoing;
    private LinearLayout mLlNetLoadingGoing;
    private RecyclerView mRlvLoadingOver;
    private LinearLayout mLlLoadingOver;
    private CheckBox mChbNetBookDownAll;
    private Button mBtnNetBookDownDelect;
    private LinearLayout mLlNetDownAll;
    private NetDownOverAdapter mOverAdapter;
    private NetDownGoingAdapter mNoDoneAdapter;
    private List<NetDownSelectVo> mSelectDoneVos;
    private List<NetDownSelectVo> mSelectNOVos;
    private DbHelperDownAssist mDao;
    private AlertDialog mDelectdialog;
    private LinearLayout mLlNetBookDownAll;
    private TextView mTvMydowning;
    private ScrollView mScroviewLayout;
    private TextView mTvEmpty;

    /*   @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_net_book_down);
           initView();
       }
   */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_book_down);
        initView();
//        mDao = DbHelperDownAssist.getInstance();
        mDao = MyAppliction.getInstance().getDownDao();
        initData(false, false);
        initDownOrDownring();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData(false, false);
    }

    private void initDownOrDownring() {
        mChbNetBookDownAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty()) {
                        for (int i = 0; i < mSelectDoneVos.size(); i++) {
                            NetDownSelectVo net = mSelectDoneVos.get(i);
                            net.setShow(true);
                            net.setSelect(true);
                        }
                    }
                    if (mSelectNOVos != null && !mSelectNOVos.isEmpty()) {
                        for (int i = 0; i < mSelectNOVos.size(); i++) {
                            NetDownSelectVo net = mSelectNOVos.get(i);
                            net.setShow(true);
                            net.setSelect(true);
                        }
                    }

                } else {
                    if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty()) {
                        for (int i = 0; i < mSelectDoneVos.size(); i++) {
                            NetDownSelectVo net = mSelectDoneVos.get(i);
                            net.setShow(true);
                            net.setSelect(false);
                        }
                    }
                    if (mSelectNOVos != null && !mSelectNOVos.isEmpty()) {
                        for (int i = 0; i < mSelectNOVos.size(); i++) {
                            NetDownSelectVo net = mSelectNOVos.get(i);
                            net.setShow(true);
                            net.setSelect(false);
                        }
                    }
                }
                mNoDoneAdapter.notifyDataSetChanged();
                mOverAdapter.notifyDataSetChanged();
            }
        });
    }

    private void refreshData() {
        //记录那些没有缓存完成的
        List<DownVideoDb> dbs = mDao.queryUserDownInfom();
        if (dbs == null || dbs.isEmpty()) {
            mScroviewLayout.setVisibility(View.GONE);
            mTvEmpty.setVisibility(View.VISIBLE);
            mTvNetBookDownMake.setVisibility(View.INVISIBLE);
            return;
        }
        //下载完数据
//        ArrayList<DownVideoDb> downOverLists = new ArrayList<>();
        //正在下载
//        ArrayList<DownVideoDb> downingLists = new ArrayList<>();
        //下载完成
        List<DownVideoVo> downVideos = new ArrayList<>();
        //正在下载
        List<DownVideoVo> downIngVideos = new ArrayList<>();
        for (int i = 0; i < dbs.size(); i++) {
            DownVideoDb db = dbs.get(i);
            List<DownVideoVo> downlist = db.getDownlist();
            if (downlist != null && !downlist.isEmpty()) {
                for (int j = 0; j < downlist.size(); j++) {
                    DownVideoVo vo = downlist.get(j);
                    if (vo.getStatus().equals("0")) {
                        downVideos.add(vo);
                    } else {
                        downIngVideos.add(vo);
                    }
                }
            }

            //未完成
//            copeList(downIngVideoDbs, db, downIngVideos);
            //已完成
//            copeList(downVideoDbs, db, downVideos);
        }
        if (downIngVideos.isEmpty() && downVideos.isEmpty()) {
            mScroviewLayout.setVisibility(View.GONE);
            mTvEmpty.setVisibility(View.VISIBLE);
            mTvNetBookDownMake.setVisibility(View.INVISIBLE);
            mLlNetDownAll.setVisibility(View.GONE);
            return;
        }

    }

    /**
     * @param isShow   是否显示
     * @param isSelect 是否显示选中
     */
    private void initData(boolean isShow, boolean isSelect) {
        computeStorage();
        //记录那些没有缓存完成的
        List<DownVideoDb> dbs = mDao.queryUserDownInfom();
        if (dbs == null || dbs.isEmpty()) {
            mScroviewLayout.setVisibility(View.GONE);
            mTvEmpty.setVisibility(View.VISIBLE);
            mTvNetBookDownMake.setVisibility(View.INVISIBLE);
            return;
        }
        //已完成
        List<DownVideoDb> downVideoDbs = new ArrayList<>();
        //未完成的
        List<DownVideoDb> downIngVideoDbs = new ArrayList<>();

        downVideoDbs.clear();
        downIngVideoDbs.clear();
//        downVideos.clear();
//        downIngVideos.clear();

        for (int i = 0; i < dbs.size(); i++) {
            DownVideoDb db = dbs.get(i);
            List<DownVideoVo> downlist = db.getDownlist();
            //已完成
            List<DownVideoVo> downVideos = new ArrayList<>();
            //未完成的
            List<DownVideoVo> downIngVideos = new ArrayList<>();
            if (downlist != null && !downlist.isEmpty()) {
                for (int j = 0; j < downlist.size(); j++) {
                    DownVideoVo vo = downlist.get(j);
                    if (vo.getStatus().equals("0")) {
                        downVideos.add(vo);
                    } else {
                        downIngVideos.add(vo);
                    }
                }
                //未完成
                if (downIngVideos != null && !downIngVideos.isEmpty()) {
                    copeList(downIngVideoDbs, db, downIngVideos);
                }
                //已完成
                if (downVideos != null && !downVideos.isEmpty()) {
                    copeList(downVideoDbs, db, downVideos);
                }
            }
        }
//        已完成
        if (downVideoDbs == null || downVideoDbs.isEmpty()) {
            mLlLoadingOver.setVisibility(View.GONE);
        }
        if (downVideoDbs != null && !downVideoDbs.isEmpty()) {
            mSelectDoneVos = new ArrayList<>();
            for (int i = 0; i < downVideoDbs.size(); i++) {
                DownVideoDb db = downVideoDbs.get(i);
                NetDownSelectVo vo = new NetDownSelectVo();
                vo.setId(db.getKid());
                vo.setSelect(isSelect);
                vo.setShow(isShow);
                List<String> list = new ArrayList<>();
                for (int j = 0; j < db.getDownlist().size(); j++) {
                    DownVideoVo videoVo = db.getDownlist().get(j);
                    list.add(videoVo.getZid());
                }
                vo.setZips(list);
                mSelectDoneVos.add(vo);
            }
        }
        //未完成
        mSelectNOVos = new ArrayList<>();
        if (downIngVideoDbs == null || downIngVideoDbs.isEmpty()) {
            mLlNetLoadingGoing.setVisibility(View.GONE);
        }
        if (downIngVideoDbs != null && !downIngVideoDbs.isEmpty())
            for (int i = 0; i < downIngVideoDbs.size(); i++) {
                DownVideoDb db = downIngVideoDbs.get(i);
                NetDownSelectVo vo = new NetDownSelectVo();
                vo.setId(db.getKid());
                vo.setSelect(isSelect);
                vo.setShow(isShow);
                List<String> list = new ArrayList<>();
                for (int k = 0; k < db.getDownlist().size(); k++) {
                    DownVideoVo videoVo = db.getDownlist().get(k);
                    list.add(videoVo.getZid());
                }
                vo.setZips(list);
                mSelectNOVos.add(vo);
            }
        bindViewData(downVideoDbs, downIngVideoDbs);
    }

    /**
     * 拷贝数据
     *
     * @param downVideoDbs  展示的集合
     * @param db
     * @param downIngVideos 缓存的视频
     */
    private void copeList(List<DownVideoDb> downVideoDbs, DownVideoDb db, List<DownVideoVo> downIngVideos) {
        if (downIngVideos == null || downIngVideos.isEmpty() || downIngVideos.size() == 0) {
            return;
        }
        DownVideoDb videoDb = new DownVideoDb();
        videoDb.setUrlImg(db.getUrlImg());
        videoDb.setStaffid(db.getStaffid());
        videoDb.setKName(db.getKName());
        videoDb.setKid(db.getKid());
        videoDb.setDownlist(downIngVideos);
        downVideoDbs.add(videoDb);
    }


    /**
     * @param over 已完成
     * @param no   未完成
     */
    private void bindViewData(List<DownVideoDb> over, List<DownVideoDb> no) {
        bindDoneViewData(over);
        bindNoDoneViewData(no);
    }

    /**
     * 绑定完成的数据
     *
     * @param dbs
     */
    private void bindDoneViewData(List<DownVideoDb> dbs) {
        setGridLayoutManger(mContext,mRlvLoadingOver,1);
        mOverAdapter = new NetDownOverAdapter(mContext, dbs, mSelectDoneVos);
        mRlvLoadingOver.setAdapter(mOverAdapter);
        mOverAdapter.setClickListener(new NetDownOverAdapter.onItemClickListener() {
            @Override
            public void onClickListener(DownVideoDb db, int position) {
                if (!isStartIntent) {
                    return;
                }
                Intent intent = NetBookDownOverActivity.newInstance(mContext, db.getKid());
                startActivity(intent);
            }
        });
        mOverAdapter.setChbClickListener(new NetDownOverAdapter.onItemChbClickListener() {
            @Override
            public void onChecaListener(DownVideoDb db, boolean isCheck, int position) {
                if (mSelectDoneVos == null || mSelectDoneVos.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mSelectDoneVos.size(); i++) {
                    NetDownSelectVo selectVo = mSelectDoneVos.get(i);
                    if (selectVo.getId().equals(db.getKid())) {
                        selectVo.setSelect(isCheck);
                    }
                }
            }
        });

    }

    /**
     * 绑定未完成的数据
     *
     * @param downVideoDbs
     */
    private void bindNoDoneViewData(List<DownVideoDb> downVideoDbs) {
        setGridLayoutManger(mContext,mRlvNetLoadingGoing,1);
        mNoDoneAdapter = new NetDownGoingAdapter(mContext, downVideoDbs, mSelectNOVos);
        mRlvNetLoadingGoing.setAdapter(mNoDoneAdapter);
        mNoDoneAdapter.setClickListener(new NetDownGoingAdapter.onItemClickListener() {
            @Override
            public void onClickListener(DownVideoDb db, int position) {
                if (!isStartIntent) {
                    return;
                }
                Intent intent = NetBookDowningActivity.newInstance(mContext, db.getKid());
                startActivity(intent);
            }
        });
        mNoDoneAdapter.setChbClickListener(new NetDownGoingAdapter.onItemChbClickListener() {
            @Override
            public void onChecaListener(DownVideoDb db, boolean isCheck, int position) {
                if (mSelectNOVos == null || mSelectNOVos.isEmpty()) {
                    return;
                }
                if (mSelectNOVos != null && !mSelectNOVos.isEmpty())
                    for (int i = 0; i < mSelectNOVos.size(); i++) {
                        NetDownSelectVo vo = mSelectNOVos.get(i);
                        if (vo.getId().equals(db.getKid())) {
                            vo.setSelect(isCheck);
                        }
                    }

            }
        });
    }

    /**
     * 计算剩余内存
     */
    private void computeStorage() {
        long externalSize = 0;
        if (Utils.hasSDCard()) {
/*            File downloadDir = PolyvSDKClient.getInstance().getDownloadDir();
            StatFs statFs = new StatFs(downloadDir.getPath());
            long blocSize = statFs.getBlockSize();
            long availaBlock = statFs.getAvailableBlocks();
                    externalSize = availaBlock * blocSize;*/
            externalSize = Utils.getSDCardAvailableSize();
        } else {
            externalSize = Utils.getSystemAvailableSize();
        }
        String s = Utils.convertFileSizeB(externalSize);
    }

    private void initView() {
        mContext = this;
        mTvNetBookDownMake = (TextView) findViewById(R.id.tv_net_book_down_make);
        mTvNetBookDownMake.setOnClickListener(this);
        mRlvNetLoadingGoing = (RecyclerView) findViewById(R.id.rlv_net_loading_going);
        mRlvNetLoadingGoing.setOnClickListener(this);
        mLlNetLoadingGoing = (LinearLayout) findViewById(R.id.ll_net_loading_going);
        mLlNetLoadingGoing.setOnClickListener(this);
        mRlvLoadingOver = (RecyclerView) findViewById(R.id.rlv_loading_over);
        mRlvLoadingOver.setOnClickListener(this);
        mLlLoadingOver = (LinearLayout) findViewById(R.id.ll_loading_over);
        mLlLoadingOver.setOnClickListener(this);
        mChbNetBookDownAll = (CheckBox) findViewById(R.id.chb_net_book_down_all);
        mBtnNetBookDownDelect = (Button) findViewById(R.id.btn_net_book_down_delect);
        mBtnNetBookDownDelect.setOnClickListener(this);
        mLlNetDownAll = (LinearLayout) findViewById(R.id.ll_net_book_down_all);
        mLlNetDownAll.setOnClickListener(this);
        mLlNetBookDownAll = (LinearLayout) findViewById(R.id.ll_net_book_down_all);
        mLlNetBookDownAll.setOnClickListener(this);
        mTvMydowning = (TextView) findViewById(R.id.tv_mydowning);
        mTvMydowning.setOnClickListener(this);
        mScroviewLayout = (ScrollView) findViewById(R.id.scroview_layout);
        mScroviewLayout.setOnClickListener(this);
        mTvEmpty = (TextView) findViewById(R.id.tv_empty);
        mTvEmpty.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btn_net_book_down_delect:
                boolean isSelect = false;
                if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty()) {
                    for (int i = 0; i < mSelectDoneVos.size(); i++) {
                        NetDownSelectVo vo = mSelectDoneVos.get(i);
                        if (vo.isSelect()) {
                            isSelect = true;
                        }
                    }
                }
                if (mSelectNOVos != null && !mSelectNOVos.isEmpty())
                    for (int i = 0; i < mSelectNOVos.size(); i++) {
                        NetDownSelectVo vo = mSelectNOVos.get(i);
                        if (vo.isSelect()) {
                            isSelect = true;
                        }
                    }

                if (!isSelect) {
                    T.showToast(mContext, getString(R.string.please_del_video));
                    return;
                }
                DialogUtil dialogUtil = DialogUtil.getInstance();
                dialogUtil.showTitleDialog(mContext, getStringWithId(R.string.is_del), getStringWithId(R.string.delect)
                        , getStringWithId(R.string.cancel), true);
                dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        PolyvDownloaderManager.stopAll();
                        final AlertDialog dialog = DialogUtil.showDialog(mContext, "", "删除中...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty())
                                    for (int i = 0; i < mSelectDoneVos.size(); i++) {
                                        NetDownSelectVo vo = mSelectDoneVos.get(i);
                                        if (vo.isSelect()) {
                                            mSelectDoneVos.remove(vo);
                                            DownVideoDb db = mDao.queryUserDownInfomWithKid(vo.getId());
                                            delectVideo(db.getDownlist());
                                            List<String> zips = vo.getZips();
                                            for (int k = 0; k < zips.size(); k++) {
                                                String zip = zips.get(k);
                                                mDao.delectZItem(vo.getId(), zip);
                                            }
                                        }
                                    }

                                if (mSelectNOVos != null && !mSelectNOVos.isEmpty())
                                    for (int i = 0; i < mSelectNOVos.size(); i++) {
                                        NetDownSelectVo vo = mSelectNOVos.get(i);
                                        if (vo.isSelect()) {
                                            mSelectNOVos.remove(vo);
                                            DownVideoDb db = mDao.queryUserDownInfomWithKid(vo.getId());
                                            delectVideo(db.getDownlist());
                                            List<String> zips = vo.getZips();
                                            for (int k = 0; k < zips.size(); k++) {
                                                String zip = zips.get(k);
                                                mDao.delectZItem(vo.getId(), zip);
                                            }

                                        }
                                    }
                                refreshData();
                                initData(false, false);
                                mNoDoneAdapter.notifyDataSetChanged();
                                mOverAdapter.notifyDataSetChanged();
                                mLlNetDownAll.setVisibility(View.GONE);
                                mTvNetBookDownMake.setText(R.string.edit);
                                PolyvDownloaderManager.startAll(getApplicationContext());
                                if (dialog != null && dialog.isShowing())
                                    dialog.dismiss();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onCancelClickListener() {

                    }
                });
                break;
            case R.id.tv_net_book_down_make:
                String trim = mTvNetBookDownMake.getText().toString().trim();
                if (trim.equals(getString(R.string.edit))) {
                    mLlNetDownAll.setVisibility(View.VISIBLE);
                    isStartIntent = false;
                    setChockAll(false);
                    mTvNetBookDownMake.setText(R.string.complete);
                    if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty()) {
                        for (int i = 0; i < mSelectDoneVos.size(); i++) {
                            NetDownSelectVo net = mSelectDoneVos.get(i);
                            net.setShow(true);
                        }
                    }
                    if (mSelectNOVos != null && !mSelectNOVos.isEmpty()) {
                        for (int i = 0; i < mSelectNOVos.size(); i++) {
                            NetDownSelectVo net = mSelectNOVos.get(i);
                            net.setShow(true);
                        }
                    }
                    mNoDoneAdapter.notifyDataSetChanged();
                    mOverAdapter.notifyDataSetChanged();

                } else {
                    mLlNetDownAll.setVisibility(View.GONE);
                    mTvNetBookDownMake.setText(R.string.edit);
                    isStartIntent = true;
                    if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty()) {
                        for (int i = 0; i < mSelectDoneVos.size(); i++) {
                            NetDownSelectVo net = mSelectDoneVos.get(i);
                            net.setShow(false);
                        }
                    }
                    if (mSelectNOVos != null && !mSelectNOVos.isEmpty()) {
                        for (int i = 0; i < mSelectNOVos.size(); i++) {
                            NetDownSelectVo net = mSelectNOVos.get(i);
                            net.setShow(false);
                        }
                    }

                    mNoDoneAdapter.notifyDataSetChanged();
                    mOverAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
//    static int	BITRATE_ERROR
//    码率（清晰度）错误
//    static int	DELETE_VIDEO_FILE_FAIL
//    删除文件失败
//    static int	DELETE_VIDEO_SUCCESS
//    删除视频成功
//    static int	DOWNLOADER_DIR_NULL
//    下载文件夹为null（未设置）
//    static int	VIDEO_ID_ERROR
//    视频id错误

    private void delectVideo(List<DownVideoVo> vid) {
        if (vid == null || vid.isEmpty()) {
            return;
        }
        for (int i = 0; i < vid.size(); i++) {
            DownVideoVo vo = vid.get(i);
            if (vo.getPercent() > 0) {
                delectVideo(vo.getVid(), vo.getBitRate());
            }
        }
  /*      for (DownVideoVo vo : vid) {
            if (vo.getPercent() > 0) {
                delectVideo(vo.getVid(), vo.getBitRate());
            }
        }*/
    }

    private void delectVideo(String vid, String bitrate) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String vid = strings[0];
                String bitrates = strings[1];
                PolyvDownloader downloader = PolyvDownloaderManager.clearPolyvDownload(vid, Integer.parseInt(bitrates));
                downloader.deleteVideo();
                PolyvDownloaderUtils.deleteVideo(vid, Integer.parseInt(bitrates));
                return null;
            }
        };
        asyncTask.execute(vid, bitrate);
    }

    private void setChockAll(boolean isClick) {
        mChbNetBookDownAll.setChecked(isClick);
    }

}
