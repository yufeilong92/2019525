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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easefun.polyvsdk.download.util.PolyvDownloaderUtils;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.net.DownDoneInfomHomeAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.event.PalyContentEvent;
import com.xuechuan.xcedu.mvp.presenter.VideoInfomsPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.SaveSortUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.ClassBeanVideoVo;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.DownInfomSelectVo;
import com.xuechuan.xcedu.vo.MyDownOverListVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookDownInfonActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 我的下载详情
 * @author: L-BackPacker
 * @date: 2018/5/16 11:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/16
 */
public class NetBookDownOverActivity extends BaseActivity implements View.OnClickListener {


    private TextView mTvNetDownInfomMake;
    private ImageView mIvNetDownInfomImg;
    private TextView mTvDownBookInfomeName;
    private TextView mTvNetDownInfomeNumber;
    private TextView mTvNetDownInfomeCout;
    private RecyclerView mRlvNetDownInfomeList;
    private CheckBox mChbNetDownInfomeAll;
    private Button mBtnNetDownInfomeDelect;
    private LinearLayout mLlNetDownInfomeAll;
    private DownVideoDb mVideoDb;
    private List<DownInfomSelectVo> mDataSelectList;
    private Context mContext;
    private DownDoneInfomHomeAdapter mInfomAdapter;
    private String mCount = "0";
    private int mNumber = 0;
    private DbHelperDownAssist mDao;
    private TextView mTvInfomEmpty;
    boolean isStartIntent = true;

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_book_down_infon);
        initView();
    }*/

    private static String KID = "kid";
    private String mKid;
    private VideoInfomsPresenter mInfomsPresenter;
    private ClassBeanVideoVo mClassBeanVideo;
    private List<ChaptersBeanVo> mChaptersVo;

    /**
     * @param context
     * @param kid     课目id
     * @return
     */
    public static Intent newInstance(Context context, String kid) {
        Intent intent = new Intent(context, NetBookDownOverActivity.class);
        intent.putExtra(KID, kid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_book_down_over);
        if (getIntent() != null) {
            mKid = getIntent().getStringExtra(KID);
        }
        initView();
//        mDao = DbHelperDownAssist.getInstance();
        mDao = MyAppliction.getInstance().getDownDao();
        initData();
        bindAdapter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 继续播放
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MainThreadPlayContext(PalyContentEvent event) {
        String vid = event.getVid();
        refreshAdapter(vid);
    }

    private void bindViewData() {
        mVideoDb = mDao.queryUserDownInfomWithKid(mKid);
        if (mVideoDb == null) {
            mTvInfomEmpty.setVisibility(View.VISIBLE);
            mTvNetDownInfomMake.setText(getStringWithId(R.string.edit));
            mTvNetDownInfomMake.setVisibility(View.INVISIBLE);
            return;
        }
        List<DownVideoVo> dbDownlist = mVideoDb.getDownlist();
        List<DownVideoVo> vos = new ArrayList<>();
        for (int i = 0; i < dbDownlist.size(); i++) {
            DownVideoVo vo = dbDownlist.get(i);
            if (vo.getStatus().equals("0")) {
                vos.add(vo);
            }
        }
        if (vos != null && !vos.isEmpty()) {
            long count = 0;
            for (DownVideoVo vo : vos) {
                if (vo.getStatus().equals("0")) {
                    long fileSize = vo.getFileSize();
                    count += fileSize;
                }
            }
            mCount = Utils.convertFileSizeB(count);
            mNumber = vos.size();
        }
        mVideoDb.setDownlist(vos);
        if (vos == null || vos.isEmpty()) {
            mTvInfomEmpty.setVisibility(View.VISIBLE);
            mTvNetDownInfomMake.setText(getStringWithId(R.string.edit));
            mTvNetDownInfomMake.setVisibility(View.INVISIBLE);
            return;
        }
        mTvDownBookInfomeName.setText(mVideoDb.getKName());
        mTvNetDownInfomeCout.setText(mCount);
        mTvNetDownInfomeNumber.setText(mNumber + "");
    }

    private void initData() {
        mVideoDb = mDao.queryUserDownInfomWithKid(mKid);
        if (mVideoDb == null) {
            mTvInfomEmpty.setVisibility(View.VISIBLE);
            mTvNetDownInfomMake.setText(getStringWithId(R.string.edit));
            mTvNetDownInfomMake.setVisibility(View.INVISIBLE);
            return;
        }
        List<DownVideoVo> downlist = mVideoDb.getDownlist();
        List<DownVideoVo> dbDownlist = mVideoDb.getDownlist();
        List<DownVideoVo> vos = new ArrayList<>();
        for (int i = 0; i < dbDownlist.size(); i++) {
            DownVideoVo vo = dbDownlist.get(i);
            if (vo.getStatus().equals("0")) {
                vos.add(vo);
            }
        }
        downlist = vos;
        mVideoDb.setDownlist(vos);
        if (downlist != null && !downlist.isEmpty()) {
            long count = 0;
            for (int i = 0; i < dbDownlist.size(); i++) {
                DownVideoVo vo = dbDownlist.get(i);
                if (vo.getStatus().equals("0")) {
                    long fileSize = vo.getFileSize();
                    count += fileSize;
                }
            }

            mCount = Utils.convertFileSizeB(count);
            mNumber = downlist.size();
        }
        mTvDownBookInfomeName.setText(mVideoDb.getKName());
        mTvNetDownInfomeCout.setText(mCount);
        mTvNetDownInfomeNumber.setText(mNumber + "");
        mDataSelectList = new ArrayList<>();
        for (int i = 0; i < mVideoDb.getDownlist().size(); i++) {
            DownVideoVo vo = mVideoDb.getDownlist().get(i);
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
        MyAppliction.getInstance().displayImages(mIvNetDownInfomImg, mVideoDb.getUrlImg(), false);
        mChbNetDownInfomeAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    initShow(false, false, true, true);
                } else {
                    initShow(false, false, true, false);
                }
                mInfomAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        bindViewData();
    }

    private void bindAdapter() {
        setGridLayoutManger(NetBookDownOverActivity.this,mRlvNetDownInfomeList,1);
//        DownVideoDb sort = SaveSortUtil.getInstance().sort(mVideoDb);
         MyDownOverListVo psort = SaveSortUtil.getInstance().psort(mVideoDb);
//        mInfomAdapter = new DownDoneInfomAdapter(mContext, sort, mDataSelectList);
        mInfomAdapter = new DownDoneInfomHomeAdapter(mContext, psort, mDataSelectList);
        mRlvNetDownInfomeList.setAdapter(mInfomAdapter);
/*        mInfomAdapter.setChbClickListener(new DownDoneInfomAdapter.onItemChbClickListener() {
            @Override
            public void onChecaListener(DownVideoVo db, boolean isCheck, int position) {
                if (mDataSelectList == null || mDataSelectList.isEmpty()) {
                    return;
                }
                if (isCheck) {
                    for (int i = 0; i < mDataSelectList.size(); i++) {
                        DownInfomSelectVo vo = mDataSelectList.get(i);
                        if (db.getZid().equals(vo.getZid()))
                            vo.setChbSelect(true);
                    }

                } else {
                    for (int i = 0; i < mDataSelectList.size(); i++) {
                        DownInfomSelectVo vo = mDataSelectList.get(i);
                        if (db.getZid().equals(vo.getZid()))
                            vo.setChbSelect(false);
                    }
                }
                if (mInfomAdapter != null)
                    mInfomAdapter.notifyDataSetChanged();

            }
        });*/
        mInfomAdapter.setClickListener(new DownDoneInfomHomeAdapter.onItemClickListener() {
            @Override
            public void onClickListener(MyDownOverListVo.zDownListVo vo, int position) {
                if (!isStartIntent) {
                    return;
                }

                Intent intent = NetBookPlayActivity.newIntent(mContext, NetBookPlayActivity.PlayMode.landScape, vo.getVid(),
                        Integer.parseInt(vo.getBitRate()), true, true, mKid,vo.getPid(),vo.getTitle());
                // 在线视频和下载的视频播放的时候只显示播放器窗口，用该参数来控制
                mContext.startActivity(intent);
                refreshAdapter(vo.getVid());
            }
        });
        mInfomAdapter.setChbClickListener(new DownDoneInfomHomeAdapter.onItemChbClickListener() {
            @Override
            public void onChecaListener(MyDownOverListVo.zDownListVo db, boolean isCheck, int position) {
                if (mDataSelectList == null || mDataSelectList.isEmpty()) {
                    return;
                }
                if (isCheck) {
                    for (int i = 0; i < mDataSelectList.size(); i++) {
                        DownInfomSelectVo vo = mDataSelectList.get(i);
                        if (db.getZid().equals(vo.getZid()))
                            vo.setChbSelect(true);
                    }

                } else {
                    for (int i = 0; i < mDataSelectList.size(); i++) {
                        DownInfomSelectVo vo = mDataSelectList.get(i);
                        if (db.getZid().equals(vo.getZid()))
                            vo.setChbSelect(false);
                    }
                }
                if (mInfomAdapter != null)
                    mInfomAdapter.notifyDataSetChanged();

            }
        });
    }

    private void refreshAdapter(String vid) {
        if (mDataSelectList == null || mDataSelectList.isEmpty()) {
            return;
        }
        for (int i = 0; i < mDataSelectList.size(); i++) {
            DownInfomSelectVo vo1 = mDataSelectList.get(i);
            if (vid.equalsIgnoreCase(vo1.getVid())) {
                vo1.setPlaySelect(true);
            } else {
                vo1.setPlaySelect(false);
            }
        }
        if (mInfomAdapter != null)
            mInfomAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mContext = this;
        mTvNetDownInfomMake = (TextView) findViewById(R.id.tv_net_down_infom_make);
        mTvNetDownInfomMake.setOnClickListener(this);
        mIvNetDownInfomImg = (ImageView) findViewById(R.id.iv_net_down_infom_img);
        mTvDownBookInfomeName = (TextView) findViewById(R.id.tv_down_book_infome_name);
        mTvNetDownInfomeNumber = (TextView) findViewById(R.id.tv_net_down_infome_number);
        mTvNetDownInfomeCout = (TextView) findViewById(R.id.tv_net_down_infome_cout);
        mRlvNetDownInfomeList = (RecyclerView) findViewById(R.id.rlv_net_down_infome_list);
        mChbNetDownInfomeAll = (CheckBox) findViewById(R.id.chb_net_down_infome_all);
        mBtnNetDownInfomeDelect = (Button) findViewById(R.id.btn_net_down_infome_delect);
        mLlNetDownInfomeAll = (LinearLayout) findViewById(R.id.ll_net_down_infome_all);
        mBtnNetDownInfomeDelect.setOnClickListener(this);
        mTvInfomEmpty = (TextView) findViewById(R.id.tv_infom_empty);
        mTvInfomEmpty.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_down_infome_delect:
                boolean isSelect = false;
                if (mDataSelectList != null && !mDataSelectList.isEmpty()) {
                    for (int i = 0; i < mDataSelectList.size(); i++) {
                        DownInfomSelectVo vo = mDataSelectList.get(i);
                        if (vo.isChbSelect()) {
                            isSelect = true;
                        }
                    }

                }
                if (!isSelect) {
                    T.showToast(mContext, getString(R.string.please_del_video));
                    return;
                }
                DialogUtil dialogUtil = DialogUtil.getInstance();
                dialogUtil.showTitleDialog(mContext, getStringWithId(R.string.is_del),
                        getStringWithId(R.string.delect),
                        getStringWithId(R.string.cancel), true);
                dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        final AlertDialog dialog = DialogUtil.showDialog(mContext, "", "删除中...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < mDataSelectList.size(); i++) {
                                    DownInfomSelectVo selectVo = mDataSelectList.get(i);
                                    if (selectVo.isChbSelect()) {

                                        mDao.delectItem(mVideoDb.getKid(), selectVo.getPid(), selectVo.getZid());
                                        delectVideo(selectVo.getVid(), selectVo.getBitrate());
                                    }
                                }
                                bindViewData();
                                MyDownOverListVo psort = SaveSortUtil.getInstance().psort(mVideoDb);
                                mInfomAdapter.setRefreshData(psort);
                                mInfomAdapter.notifyDataSetChanged();
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
            case R.id.tv_net_down_infom_make:
                String str = getTextStr(mTvNetDownInfomMake);
                if (str.equals(getString(R.string.edit))) {
                    mTvNetDownInfomMake.setText(R.string.complete);
                    setChickAll(false);
                    isStartIntent = false;
                    mLlNetDownInfomeAll.setVisibility(View.VISIBLE);
                    if (mDataSelectList != null && !mDataSelectList.isEmpty())
                        initShow(false, false, true, false);
                } else {
                    mLlNetDownInfomeAll.setVisibility(View.GONE);
                    mTvNetDownInfomMake.setText(R.string.edit);
                    isStartIntent = true;
                    if (mDataSelectList != null && !mDataSelectList.isEmpty()) {
                        initShow(true, false, false, false);
                    }
                }
                mInfomAdapter.notifyDataSetChanged();
                break;
        }
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

    /**
     * 保利视频id
     *
     * @param vid
     */
    private void delectVideo(String vid, final String bitrate) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String vid = strings[0];
                String bitrates = strings[1];
                PolyvDownloaderUtils.deleteVideo(vid, Integer.parseInt(bitrates));
//                PolyvDownloader downloader = PolyvDownloaderManager.clearPolyvDownload(vid, Integer.parseInt(bitrates));
//                downloader.deleteVideo();
                return null;
            }
        };
        asyncTask.execute(vid, bitrate);
    }

    private void setChickAll(boolean isClick) {

        mChbNetDownInfomeAll.setChecked(isClick);
    }


}
