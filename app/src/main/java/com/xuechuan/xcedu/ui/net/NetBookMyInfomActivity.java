package com.xuechuan.xcedu.ui.net;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.PolyvBitRate;
import com.easefun.polyvsdk.PolyvDownloader;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.PolyvSDKUtil;
import com.easefun.polyvsdk.Video;
import com.easefun.polyvsdk.marquee.PolyvMarqueeItem;
import com.easefun.polyvsdk.marquee.PolyvMarqueeView;
import com.easefun.polyvsdk.video.PolyvPlayErrorReason;
import com.easefun.polyvsdk.video.PolyvVideoView;
import com.easefun.polyvsdk.video.listener.IPolyvOnAdvertisementEventListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnCompletionListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnErrorListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureClickListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureLeftDownListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureLeftUpListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureRightDownListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureRightUpListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureSwipeLeftListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureSwipeRightListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnPreparedListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnQuestionAnswerTipsListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnVideoPlayErrorListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnVideoStatusListener;
import com.easefun.polyvsdk.vo.PolyvADMatterVO;
import com.easefun.polyvsdk.vo.PolyvVideoVO;
import com.google.gson.Gson;
import com.xuechuan.xcedu.event.NetMyPlayEvent;
import com.xuechuan.xcedu.event.NetMyPlayTrySeeEvent;
import com.xuechuan.xcedu.event.RefreshPlayIconEvent;
import com.xuechuan.xcedu.event.VideoIdEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.home.MyNetBookIndicatorAdapter;
import com.xuechuan.xcedu.adapter.home.MyTagPagerAdapter;
import com.xuechuan.xcedu.adapter.net.NetMyDownTableAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.flyn.Eyes;
import com.xuechuan.xcedu.fragment.net.NetMyBokTableFragment;
import com.xuechuan.xcedu.fragment.net.NetMyBookVualueFragment;
import com.xuechuan.xcedu.mvp.contract.VideoBooksContract;
import com.xuechuan.xcedu.mvp.model.MyVideoBooksModel;
import com.xuechuan.xcedu.mvp.presenter.VideoInfomsPresenter;
import com.xuechuan.xcedu.net.NetService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.player.BaolIHttp.PolyvVlmsHelper;
import com.xuechuan.xcedu.player.player.PolyvPlayerLightView;
import com.xuechuan.xcedu.player.player.PolyvPlayerMediaController;
import com.xuechuan.xcedu.player.player.PolyvPlayerProgressView;
import com.xuechuan.xcedu.player.player.PolyvPlayerVolumeView;
import com.xuechuan.xcedu.player.util.MyDownloadListener;
import com.xuechuan.xcedu.player.util.PolyvErrorMessageUtils;
import com.xuechuan.xcedu.player.util.PolyvScreenUtils;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.NetworkToolUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.ClassBeanVideoVo;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.Db.UserLookVideoVo;
import com.xuechuan.xcedu.vo.NetBookTableVo;
import com.xuechuan.xcedu.vo.PerInfomVo;
import com.xuechuan.xcedu.vo.PlayProgressBeanVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.VideoSettingVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.NoScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookMyInfomActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 我的网课
 * @author: L-BackPacker
 * @date: 2018/5/16 17:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/16
 */
public class NetBookMyInfomActivity extends BaseActivity implements View.OnClickListener, VideoBooksContract.View {

    private static final String TAG = NetBookMyInfomActivity.class.getSimpleName();
    /**
     * 播放器的parentView
     */
    private RelativeLayout viewLayout = null;
    /**
     * 播放主视频播放器
     */
    private PolyvVideoView videoView = null;
    /**
     * 跑马灯控件
     */
    private PolyvMarqueeView marqueeView = null;
    private PolyvMarqueeItem marqueeItem = null;
    /**
     * 视频控制栏
     */
    private PolyvPlayerMediaController mediaController = null;
    /**
     * 手势出现的亮度界面
     */
    private PolyvPlayerLightView lightView = null;
    /**
     * 手势出现的音量界面
     */
    private PolyvPlayerVolumeView volumeView = null;
    /**
     * 手势出现的进度界面
     */
    private PolyvPlayerProgressView progressView = null;
    /**
     * 视频加载缓冲视图
     */
    private ProgressBar loadingProgress = null;

    private int fastForwardPos = 0;
    private boolean isPlay = false;

    private LinearLayout ll_title_bar;
    private boolean isPlayafter = false;

    /***
     * 数据类型
     */
    public static final String CLSSID = "clssid";
    private String mClassId;
//    private TextView mTvNetBookTitle;
    private MagicIndicator mNetMagicIndicator;
    private NoScrollViewPager mVpNetBar;
    private Context mContext;
    private ImageView mIvNetPlay;
    private ImageView mIvNetBookPlay;
    private RelativeLayout mRlPlaylayout;
    private String vid;
    private CommonPopupWindow popDown;
    private LinearLayout mLlNetPlayRoot;
    private ImageView mIvNetMybookDown;
    /**
     * 科目章集合
     */
    private List mTableList;
    private NetMyDownTableAdapter mDownAdapter;
    /**
     * 保利视频请求
     */
    private PolyvVlmsHelper helper;
    private int mVideoid = -1;
    private ImageView mIvBack;
    private DbHelperDownAssist mDao;
    private int mPid;
    private int mZid;

    private String mTitleName;
    private AlertDialog mShowDialog;
    private NetBookTableVo.DataBean mBookInfom;
    private ClassBeanVideoVo bookInfmo;
    private List<ChaptersBeanVo> mBookList;
    private int videoProgress = 0;
    private boolean iscontinue = false;
    private MyNetBroadcast netBorect;
    private int mFirst = 0;
    private boolean mIsRegister = false;
    private PlayProgressBeanVo mPlayprogress;
    //标题栏显示标题
    private String mTitleTagName;
    private DataMessageVo mMessageVo;
    private String mContinueVid;
    private String mTittle;


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //回来后继续播放
        if (isPlay) {
            videoView.onActivityResume();
//            danmuFragment.resume();

        }
        mediaController.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearGestureInfo();
        mediaController.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //弹出去暂停
        isPlay = videoView.onActivityStop();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        int position = videoView.getCurrentPosition();
        int watchTimeDuration = videoView.getWatchTimeDuration();
        if (mVideoid != -1 && mVideoid != 0) {
            if (position != 0) {
                int i = position / 1000;
                position = (int) Math.rint(i);
                int watchTime = mMessageVo.getWatchTime();
                int k = watchTimeDuration - watchTime;
                mMessageVo.setWatchTime(watchTimeDuration);
                SubmitProgress(String.valueOf(mVideoid), String.valueOf(bookInfmo.getId()), k, String.valueOf(position));
            }
        }
//        danmuFragment.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.destroy();
        mediaController.disable();
//        submitProgress();
        if (mIsRegister) {
            mIsRegister = false;
            unregisterReceiver(netBorect);

        }
        mMessageVo.setWatchTime(0);
        MyAppliction.getInstance().setIsPlay(false);

    }

    /**
     * 提交视频进度
     */
    private void submitProgress() {
        int position = videoView.getCurrentPosition();
        int watchTimeDuration = videoView.getWatchTimeDuration();
        if (isPlayafter) {
            if (mVideoid != -1 && mVideoid != 0) {
                if (position != 0) {
                    int i = position / 1000;
                    position = (int) Math.rint(i);
                }
                int watchTime = mMessageVo.getWatchTime();
                int i = watchTimeDuration - watchTime;
                mMessageVo.setWatchTime(watchTimeDuration);
                SubmitProgress(String.valueOf(mVideoid), String.valueOf(bookInfmo.getId()), i, String.valueOf(position));
//                SubmitProgressService.
// (mContext, String.valueOf(position), String.valueOf(bookInfmo.getId()), String.valueOf(mVideoid));
            }
            saveLookVideo();
        }
    }

    private void SubmitProgress(String videoId, String classId, int watchtime, String prgress) {
        NetService service = new NetService(this);
        service.SubmitViewProgres(videoId, classId, prgress, watchtime, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                ResultVo vo = gson.fromJson(response, ResultVo.class);
                if (vo.getStatus().getCode() == 200) {
                    L.d("提交成功后的" + response);
                } else {
//                    handleActionFoo(prgress, classId, videoId);
                    L.e("提交成功后的" + response);
                }
            }

            @Override
            public void onError(String response) {
                L.e(response);
            }
        });
    }

    public static Intent newInstance(Context context, String classid) {
        Intent intent = new Intent(context, NetBookMyInfomActivity.class);
        intent.putExtra(CLSSID, classid);
        return intent;
    }

    public static Intent newIntent(Context context, PlayMode playMode, String vid) {
        return newIntent(context, playMode, vid, PolyvBitRate.ziDong.getNum());
    }

    public static Intent newIntent(Context context, PlayMode playMode, String vid, int bitrate) {
        return newIntent(context, playMode, vid, bitrate, false);
    }

    public static Intent newIntent(Context context, PlayMode playMode, String vid, int bitrate, boolean startNow) {
        return newIntent(context, playMode, vid, bitrate, startNow, false);
    }

    public static Intent newIntent(Context context, PlayMode playMode, String vid, int bitrate, boolean startNow,
                                   boolean isMustFromLocal) {
        Intent intent = new Intent(context, NetBookMyInfomActivity.class);
        intent.putExtra("playMode", playMode.getCode());
        intent.putExtra("value", vid);
        intent.putExtra("bitrate", bitrate);
        intent.putExtra("startNow", startNow);
        intent.putExtra("isMustFromLocal", isMustFromLocal);
        return intent;
    }

    public static void intentTo(Context context, PlayMode playMode, String vid) {
        intentTo(context, playMode, vid, PolyvBitRate.ziDong.getNum());
    }

    public static void intentTo(Context context, PlayMode playMode, String vid, int bitrate) {
        intentTo(context, playMode, vid, bitrate, false);
    }

    public static void intentTo(Context context, PlayMode playMode, String vid, int bitrate, boolean startNow) {
        intentTo(context, playMode, vid, bitrate, startNow, false);
    }

    public static void intentTo(Context context, PlayMode playMode, String vid, int bitrate, boolean startNow,
                                boolean isMustFromLocal) {
        context.startActivity(newIntent(context, playMode, vid, bitrate, startNow, isMustFromLocal));
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_book_infom);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        Eyes.translucentStatusBar(this, false);
        setContentView(R.layout.activity_net_mybook_infom);

        if (getIntent() != null) {
            mClassId = getIntent().getStringExtra(CLSSID);
        }
        mMessageVo = DataMessageVo.get_Instance();
        initView();
        initViewData();

        PolyvScreenUtils.generateHeight16_9(this);
        PolyvScreenUtils.initTitleBar(ll_title_bar, mRlPlaylayout, null);
        PolyvScreenUtils.initBack(mIvBack);
        int playModeCode = getIntent().getIntExtra("playMode", PlayMode.portrait.getCode());
        PlayMode playMode = PlayMode.getPlayMode(playModeCode);
        if (playMode == null)
            playMode = PlayMode.portrait;
        //视频id
        String vid = getIntent().getStringExtra("value");
        int bitrate = getIntent().getIntExtra("bitrate", PolyvBitRate.ziDong.getNum());
        boolean startNow = getIntent().getBooleanExtra("startNow", false);
        boolean isMustFromLocal = getIntent().getBooleanExtra("isMustFromLocal", false);

        switch (playMode) {
            case landScape: //横屏
                mIvBack.setVisibility(View.GONE);
                mediaController.changeToLandscape();
                break;
            case portrait://竖屏
                mIvBack.setVisibility(View.VISIBLE);
                mediaController.changeToPortrait();
                break;
        }
        initData();

//        play("d740a56357c361f76cdd800b204e9800_d", 0, true, false);
    }


    private void initData() {
        VideoInfomsPresenter mPresenter = new VideoInfomsPresenter();
        mPresenter.initModelView(new MyVideoBooksModel(), this);
        mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestBookInfoms(mContext, mClassId);
 /*       DbHelperAssist dao = DbHelperAssist.getInstance(mContext);
        UserInfomDb db = dao.queryWithuuUserInfom();
        if (db != null) {
            List<UserLookVideoVo> lookVideoVos = db.getLookVideolist();
            if (lookVideoVos != null && !lookVideoVos.isEmpty())
                for (UserLookVideoVo vo : lookVideoVos) {
                    if (vo.getKid().equals(mClassId)) {
                        vid = vo.getVid();
                        mZid = Integer.parseInt(vo.getZid());
                        mVideoid = Integer.parseInt(vo.getZid());
                        mPid = Integer.parseInt(vo.getPid());
                        mTitleName = vo.getTitleName();
                        videoProgress = Integer.parseInt(vo.getProgress());
                        iscontinue = true;
                    }
                }
        }
*/

    }

    /**
     * 播放视频
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNetMainPlayId(NetMyPlayEvent event) {
        VideosBeanVo vo = event.getVo();
        L.e(vo.getVid());
        L.e("调用===========" + vo.getVid());
        vid = vo.getVid();
        mVideoid = vo.getVideoid();
        mPid = vo.getChapterid();
        mZid = vo.getVideoid();
        mTitleName = vo.getVideoname();
        mTitleTagName = vo.getVideoname();
        play();
        iscontinue = false;
        mMessageVo.setWatchTime(0);

//        play(vo.getVid(), 3, true, false);
//        mRlPlaylayout.setVisibility(View.GONE);
    }

    /**
     * 播放视频
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNetMainTryPlayId(NetMyPlayTrySeeEvent event) {
        VideosBeanVo vo = event.getVo();
        L.e(vo.getVid());
        if (!iscontinue && !isPlayafter) {
            mVideoid = vo.getVideoid();
            mPid = vo.getChapterid();
            mZid = vo.getVideoid();
            vid = vo.getVid();
            mTitleName = vo.getVideoname();
            mTitleTagName = vo.getVideoname();
            mMessageVo.setWatchTime(0);
            EventBus.getDefault().postSticky(new VideoIdEvent(String.valueOf(mVideoid)));
        }
    }

    private void initView() {
        mContext = this;
        mIvBack = findViewById(R.id.iv_net_book_back);
        mIvBack.setOnClickListener(this);
        mLlNetPlayRoot = (LinearLayout) findViewById(R.id.ll_net_play_my_root);
        mLlNetPlayRoot.setOnClickListener(this);
        mIvNetBookPlay = (ImageView) findViewById(R.id.iv_net_my_book_play);
        mIvNetBookPlay.setOnClickListener(this);
        mIvNetMybookDown = (ImageView) findViewById(R.id.iv_net_icon_my_down);
        mIvNetMybookDown.setOnClickListener(this);
        mRlPlaylayout = (RelativeLayout) findViewById(R.id.rl_play_my_layout);
        mRlPlaylayout.setOnClickListener(this);
        mIvNetPlay = (ImageView) findViewById(R.id.iv_net_my_play);
//        mTvNetBookTitle = (TextView) findViewById(R.id.tv_net_my_book_title);
//        mTvNetBookTitle.setOnClickListener(this);
        mNetMagicIndicator = (MagicIndicator) findViewById(R.id.net_magic_my_indicator);
        mNetMagicIndicator.setOnClickListener(this);
        mVpNetBar = (NoScrollViewPager) findViewById(R.id.vp_net_my_bar);
        mVpNetBar.setOnClickListener(this);
//        ll_title_bar = (LinearLayout) findViewById(R.id.activity_title_container);
        viewLayout = (RelativeLayout) findViewById(R.id.view_layout);
        videoView = (PolyvVideoView) findViewById(R.id.polyv_video_view);
        marqueeView = (PolyvMarqueeView) findViewById(R.id.polyv_marquee_view);
        mediaController = (PolyvPlayerMediaController) findViewById(R.id.polyv_player_media_controller);

        lightView = (PolyvPlayerLightView) findViewById(R.id.polyv_player_light_view);
        volumeView = (PolyvPlayerVolumeView) findViewById(R.id.polyv_player_volume_view);
        progressView = (PolyvPlayerProgressView) findViewById(R.id.polyv_player_progress_view);
        loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        mediaController.initTitltBar(ll_title_bar, mRlPlaylayout, null);
        mediaController.initConfig(viewLayout);
        videoView.setMediaController(mediaController);
        videoView.setPlayerBufferingIndicator(loadingProgress);
        // 设置跑马灯
        int Duration = 10000;
        int textSize = 18;
        int style = PolyvMarqueeItem.STYLE_ROLL;
        String textColor = "#DC143C";
        int TextAlpha = 70;
        int Interval = 10000;
        int LifeTime = 10000;
        int TweenTime = 10000;
        if (MyAppliction.getInstance().getVideoSet() != null) {
            VideoSettingVo.DataBean.PolyvideosettingBean set = MyAppliction.getInstance().getVideoSet();
            switch (set.getStyle()) {
                case 1:
                    style = PolyvMarqueeItem.STYLE_ROLL;
                    break;
                case 2:
                    style = PolyvMarqueeItem.STYLE_FLICK;
                    break;
                case 3:
                    style = PolyvMarqueeItem.STYLE_ROLL_FLICK;
                    break;

            }
            Duration = set.getDuration();
            textSize = set.getTextsize();
            textColor = set.getTextcolor();
            TextAlpha = set.getTextalpha();
            Interval = set.getInterval();
            LifeTime = set.getLifetime();
            TweenTime = set.getTweentime();
        }
        String phone = "学川教育";
        PerInfomVo userData = MyAppliction.getInstance().getUserData();
        if (userData != null && userData.getData() != null) {
            if (!StringUtil.isEmpty(userData.getData().getPhone()))
                phone = userData.getData().getPhone();
        }
        videoView.setMarqueeView(marqueeView, marqueeItem = new PolyvMarqueeItem()
                .setStyle(style) //样式
                .setDuration(Duration) //时长
                .setText(phone) //文本
                .setSize(textSize) //字体大小
                .setColor(Color.parseColor(textColor)) //字体颜色
                .setTextAlpha(TextAlpha) //字体透明度
                .setInterval(Interval) //隐藏时间
                .setLifeTime(LifeTime) //显示时间
                .setTweenTime(TweenTime) //渐隐渐现时间
                .setHasStroke(true) //是否有描边
                .setBlurStroke(true) //是否模糊描边
                .setStrokeWidth(3) //描边宽度
                .setStrokeColor(Color.MAGENTA) //描边颜色
                .setStrokeAlpha(70)); //描边透明度

    }

    private void initViewData() {
        videoView.setOpenAd(true);
        videoView.setOpenTeaser(true);
        videoView.setOpenQuestion(true);
        videoView.setOpenSRT(true);
        videoView.setOpenPreload(true, 2);
        videoView.setOpenMarquee(true);
        videoView.setAutoContinue(true);
        videoView.setNeedGestureDetector(true);

        videoView.setOnPreparedListener(new IPolyvOnPreparedListener2() {
            @Override
            public void onPrepared() {
                mediaController.preparedView();
                progressView.setViewMaxValue(videoView.getDuration());
                //调用setVid方法视频会自动播放
                if (!StringUtil.isEmpty(vid)&&iscontinue&&vid.equals(mContinueVid)) {
                    videoView.seekTo(videoProgress);
                }
                // 没开预加载在这里开始弹幕
                // danmuFragment.start();
            }
        });
        videoView.setOnVideoStatusListener(new IPolyvOnVideoStatusListener() {
            @Override
            public void onStatus(int status) {
                if (status < 60) {
                    Toast.makeText(NetBookMyInfomActivity.this, "状态错误 " + status, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, String.format("状态正常 %d", status));
                }
            }
        });

        videoView.setOnVideoPlayErrorListener(new IPolyvOnVideoPlayErrorListener2() {
            @Override
            public boolean onVideoPlayError(@PolyvPlayErrorReason.PlayErrorReason int playErrorReason) {
                String message = PolyvErrorMessageUtils.getPlayErrorMessage(playErrorReason);
                message += "(error code " + playErrorReason + ")";
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(NetBookMyInfomActivity.this);
                builder.setTitle("错误");
                builder.setMessage(message);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                if (videoView.getWindowToken() != null) {
                    builder.show();
                }
                return true;
            }
        });

        videoView.setOnErrorListener(new IPolyvOnErrorListener2() {
            @Override
            public boolean onError() {
                Toast.makeText(NetBookMyInfomActivity.this, "当前视频无法播放，请向管理员反馈(error code " + PolyvPlayErrorReason.VIDEO_ERROR + ")", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        videoView.setOnAdvertisementEventListener(new IPolyvOnAdvertisementEventListener2() {
            @Override
            public void onShow(PolyvADMatterVO adMatter) {
                Log.i(TAG, "开始播放视频广告");
            }

            @Override
            public void onClick(PolyvADMatterVO adMatter) {
                if (!TextUtils.isEmpty(adMatter.getAddrUrl())) {
                    try {
                        new URL(adMatter.getAddrUrl());
                    } catch (MalformedURLException e1) {
                        Log.e(TAG, PolyvSDKUtil.getExceptionFullMessage(e1, -1));
                        return;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(adMatter.getAddrUrl()));
                    startActivity(intent);
                }
            }
        });


        videoView.setOnQuestionAnswerTipsListener(new IPolyvOnQuestionAnswerTipsListener() {

            @Override
            public void onTips(@NonNull String msg) {
            }

            @Override
            public void onTips(@NonNull String msg, int seek) {

            }
        });


        videoView.setOnGestureLeftUpListener(new IPolyvOnGestureLeftUpListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftUp %b %b brightness %d", start, end, videoView.getBrightness(NetBookMyInfomActivity.this)));
                int brightness = videoView.getBrightness(NetBookMyInfomActivity.this) + 5;
                if (brightness > 100) {
                    brightness = 100;
                }

                videoView.setBrightness(NetBookMyInfomActivity.this, brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureLeftDownListener(new IPolyvOnGestureLeftDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftDown %b %b brightness %d", start, end, videoView.getBrightness(NetBookMyInfomActivity.this)));
                int brightness = videoView.getBrightness(NetBookMyInfomActivity.this) - 5;
                if (brightness < 0) {
                    brightness = 0;
                }

                videoView.setBrightness(NetBookMyInfomActivity.this, brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureRightUpListener(new IPolyvOnGestureRightUpListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("RightUp %b %b volume %d", start, end, videoView.getVolume()));
                // 加减单位最小为10，否则无效果
                int volume = videoView.getVolume() + 10;
                if (volume > 100) {
                    volume = 100;
                }

                videoView.setVolume(volume);
                volumeView.setViewVolumeValue(volume, end);
            }
        });

        videoView.setOnGestureRightDownListener(new IPolyvOnGestureRightDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("RightDown %b %b volume %d", start, end, videoView.getVolume()));
                // 加减单位最小为10，否则无效果
                int volume = videoView.getVolume() - 10;
                if (volume < 0) {
                    volume = 0;
                }

                videoView.setVolume(volume);
                volumeView.setViewVolumeValue(volume, end);
            }
        });

        videoView.setOnGestureSwipeLeftListener(new IPolyvOnGestureSwipeLeftListener() {

            @Override
            public void callback(boolean start, boolean end) {
                // 左滑事件
                Log.d(TAG, String.format("SwipeLeft %b %b", start, end));
                if (fastForwardPos == 0) {
                    fastForwardPos = videoView.getCurrentPosition();
                }

                if (end) {
                    if (fastForwardPos < 0)
                        fastForwardPos = 0;
                    videoView.seekTo(fastForwardPos);
                    if (videoView.isCompletedState()) {
                        videoView.start();
                    }
                    fastForwardPos = 0;
                } else {
                    fastForwardPos -= 10000;
                    if (fastForwardPos <= 0)
                        fastForwardPos = -1;
                }
                progressView.setViewProgressValue(fastForwardPos, videoView.getDuration(), end, false);
            }
        });

        videoView.setOnGestureSwipeRightListener(new IPolyvOnGestureSwipeRightListener() {

            @Override
            public void callback(boolean start, boolean end) {
                // 右滑事件
                Log.d(TAG, String.format("SwipeRight %b %b", start, end));
                if (fastForwardPos == 0) {
                    fastForwardPos = videoView.getCurrentPosition();
                }

                if (end) {
                    if (fastForwardPos > videoView.getDuration())
                        fastForwardPos = videoView.getDuration();
                    if (!videoView.isCompletedState()) {
                        videoView.seekTo(fastForwardPos);
                    } else if (videoView.isCompletedState() && fastForwardPos != videoView.getDuration()) {
                        videoView.seekTo(fastForwardPos);
                        videoView.start();
                    }
                    fastForwardPos = 0;
                } else {
                    fastForwardPos += 10000;
                    if (fastForwardPos > videoView.getDuration())
                        fastForwardPos = videoView.getDuration();
                }
                progressView.setViewProgressValue(fastForwardPos, videoView.getDuration(), end, true);
            }
        });

        videoView.setOnGestureClickListener(new IPolyvOnGestureClickListener() {
            @Override
            public void callback(boolean start, boolean end) {
                if (videoView.isInPlaybackState() && mediaController != null)
                    if (mediaController.isShowing())
                        mediaController.hide();
                    else
                        mediaController.show();
            }
        });
        videoView.setOnCompletionListener(new IPolyvOnCompletionListener2() {
            @Override
            public void onCompletion() {
                submitProgress();
                nextPlayVideo();
            }
        });
        netBorect = new MyNetBroadcast();
        IntentFilter filter = new IntentFilter();
        mIsRegister = true;
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.intent.category.DEFAULT");
        registerReceiver(netBorect, filter);
    }

    /**
     * 自动播放下一个视频
     */
    private void nextPlayVideo() {
        if (StringUtil.isEmpty(vid))
            return;
        if (!isPlayafter) {
            return;
        }
        if (mPid == 0) {
            return;
        }
        int mPindex = -1;
        int mZindex = -1;
        for (int i = 0; i < mBookList.size(); i++) {
            ChaptersBeanVo vo = mBookList.get(i);
            if (vo.getChapterid() == mPid) {
                List<VideosBeanVo> videos = vo.getVideos();
                mPindex = i;
                if (videos != null && !videos.isEmpty()) {
                    for (int k = 0; k < videos.size(); k++) {
                        VideosBeanVo beanVo = videos.get(k);
                        String mVid = beanVo.getVid();
                        if (vid.equalsIgnoreCase(mVid)) {
                            mZindex = k;
                        }
                    }
                    if (videos.size() == (mZindex + 1)) {
                        mZindex = -2;
                    }
                }
            }
        }
        if (mPindex == -1) return;
        if (mZindex == -2) {//该篇最后一节，判断该篇是否最后一篇
            if (mBookList.size() == (mPindex + 1)) {
                T.showToast(mContext, getString(R.string.lastVideo));
                return;
            }
            ChaptersBeanVo vo = mBookList.get(mPindex + 1);
            mZindex = 0;
            VideosBeanVo beanVo = vo.getVideos().get(mZindex);
            vid = beanVo.getVid();
            mZid = beanVo.getVideoid();
            mVideoid = beanVo.getVideoid();
            mTitleName = beanVo.getVideoname();
            mPid = vo.getChapterid();
            isPlayafter = false;
            iscontinue = true;
            play();
            return;
        }

        if (mZindex != -1 && mZindex != -2) {
            ChaptersBeanVo vo = mBookList.get(mPindex);
            VideosBeanVo beanVo = vo.getVideos().get(mZindex + 1);
            vid = beanVo.getVid();
            mZid = beanVo.getVideoid();
            mVideoid = beanVo.getVideoid();
            mTitleName = beanVo.getVideoname();
            mPid = vo.getChapterid();
            isPlayafter = false;
            iscontinue = true;
            play();
        }

    }

    /**
     * 播放视频
     *
     * @param vid             视频id
     * @param bitrate         码率（清晰度）
     * @param startNow        是否现在开始播放视频
     * @param isMustFromLocal 是否必须从本地（本地缓存的视频）播放
     */
    public void play(final String vid, final int bitrate, boolean startNow, final boolean isMustFromLocal) {
        if (TextUtils.isEmpty(vid)) return;
        mMessageVo.setWatchTime(0);
        videoView.release();
        mediaController.hide();
        loadingProgress.setVisibility(View.GONE);
        progressView.resetMaxValue();
        if (startNow) {

            if (iscontinue && !isPlayafter && !StringUtil.isEmpty(mTitleName)) {
                EventBus.getDefault().postSticky(new RefreshPlayIconEvent(vid, mTitleName));
            }
            isPlayafter = true;
            videoView.setVid(vid, bitrate, isMustFromLocal);
        }
        if (mediaController != null) {
            mediaController.setVideoTitle(mTitleTagName);
        }
    }


    private void clearGestureInfo() {
        videoView.clearGestureInfo();
        progressView.hide();
        volumeView.hide();
        lightView.hide();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (PolyvScreenUtils.isLandscape(this) && mediaController != null) {
                mediaController.changeToPortrait();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_net_my_book_play://播放按钮
                if (StringUtil.isEmpty(vid)) {
                    T.showToast(mContext, "暂无课观看");
                    return;
                }
                if (network()) {
                    return;
                }
                play();
                break;
            case R.id.iv_net_icon_my_down://我的下载
                if (mBookList == null || mBookList.isEmpty()) {
                    T.showToast(mContext, "暂无课下载");
                    return;
                }

                showPopwindow();
                break;
            case R.id.iv_net_book_back:
//                int position = videoView.getCurrentPosition();
//                int watchTimeDuration = videoView.getWatchTimeDuration();
//                String s = PolyvTimeUtils.generateTime(position);
//                if (position != 0 && mVideoid != -1) {
//                    SubmitProgressService.startActionFoo(mContext, String.valueOf(position),
//                            String.valueOf(bookInfmo.getId()), watchTimeDuration, String.valueOf(mVideoid));
//                }
                this.finish();
                break;
            default:

        }
    }

    private void play() {
        if (!StringUtil.isEmpty(vid)) {
            mRlPlaylayout.setVisibility(View.GONE);
            play(vid, 3, true, false);
            mediaController.setIsPlay(true);
            MyAppliction.getInstance().setIsPlay(true);
            PolyvScreenUtils.IsPlay(true);
//            saveLookVideo();
            EventBus.getDefault().postSticky(new VideoIdEvent(String.valueOf(mVideoid)));
        }else {
            T.showToast(mContext,"获取视频信息失败");
        }
    }

    private void saveLookVideo() {
        DbHelperAssist mUserDao = DbHelperAssist.getInstance(mContext);
        if (StringUtil.isEmpty(vid) && !isPlayafter) {
            return;
        }
        if (mZid == 0) {
            return;
        }
        UserLookVideoVo vo = new UserLookVideoVo();
        vo.setKid(String.valueOf(bookInfmo.getId()));
        vo.setPid(String.valueOf(mPid));
        vo.setZid(String.valueOf(mZid));
        vo.setTitleName(mTitleName);
        vo.setVid(vid);
        int position = videoView.getCurrentPosition();
//                String s = PolyvTimeUtils.generateTime(position);
        vo.setProgress(String.valueOf(position));
        mUserDao.saveLookVideo(vo);
    }

    @Override
    public void BookInfomSucces(String com) {
        if (mShowDialog != null && mShowDialog.isShowing()) {
            mShowDialog.dismiss();
        }
        Gson gson = new Gson();
        NetBookTableVo tableVo = gson.fromJson(com, NetBookTableVo.class);
        if (tableVo != null && tableVo.getData() != null)
            if (tableVo.getStatus().getCode() == 200) {
                NetBookTableVo.DataBean bean = tableVo.getData();
                bookInfmo = bean.getClassX();
                mBookList = bean.getChapters();
                mPlayprogress = bean.getPlayprogress();
                initProgress(mPlayprogress, mBookList);
                bindViewData(bookInfmo, mBookList, bookInfmo.isIsall());
            } else {
                T_ERROR(mContext);
                L.e(tableVo.getStatus().getMessage());
            }
    }

    /**
     * 初始化用户进度
     *
     * @param mPlayprogress
     * @param mBookList
     */
    private void initProgress(PlayProgressBeanVo mPlayprogress, List<ChaptersBeanVo> mBookList) {
        int videoid = mPlayprogress.getVideoid();
        if (videoid == 0) {
            return;
        }
        for (int i = 0; i < mBookList.size(); i++) {
            ChaptersBeanVo pVo = mBookList.get(i);
            if (pVo.getVideos() != null && !pVo.getVideos().isEmpty()) {
                List<VideosBeanVo> videos = pVo.getVideos();
                for (int k = 0; k < videos.size(); k++) {
                    VideosBeanVo zBeanVo = videos.get(k);
                    if (zBeanVo.getVideoid() == videoid) {
                        vid = zBeanVo.getVid();
                        mContinueVid = zBeanVo.getVid();
                        mZid = zBeanVo.getVideoid();
                        mVideoid = zBeanVo.getVideoid();
                        mPid = pVo.getChapterid();
                        mTitleName = zBeanVo.getVideoname();
                        videoProgress = mPlayprogress.getProgress() * 1000;
                        iscontinue = true;

                        EventBus.getDefault().postSticky(new RefreshPlayIconEvent(vid, mTitleName));
                    }
                }

            }
        }

    }

    private void bindViewData(ClassBeanVideoVo bookInfmo, List<ChaptersBeanVo> bookList, boolean isall) {
        helper = new PolyvVlmsHelper();
//        mTvNetBookTitle.setText(mTittle);
        List<String> list = ArrayToListUtil.arraytoList(mContext, R.array.net_mybook_title);
        mNetMagicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdjustMode(true);
        MyNetBookIndicatorAdapter adapter = new MyNetBookIndicatorAdapter(list, mVpNetBar);
        mNetMagicIndicator.setNavigator(commonNavigator);
        commonNavigator.setAdapter(adapter);
        List<Fragment> fragments = creartFragment(bookList);
        MyTagPagerAdapter tagPagerAdapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
        mVpNetBar.setAdapter(tagPagerAdapter);
        mVpNetBar.setOffscreenPageLimit(4);
        ViewPagerHelper.bind(mNetMagicIndicator, mVpNetBar);
        if (!StringUtil.isEmpty(bookInfmo.getCoverimg())) {
            MyAppliction.getInstance().displayImages(mIvNetPlay, bookInfmo.getCoverimg(), false);
        }
    }

    private List<Fragment> creartFragment(List<ChaptersBeanVo> bookList) {
        List<Fragment> fragments = new ArrayList<>();
        NetMyBokTableFragment tableFragment = NetMyBokTableFragment.newInstance(bookList);
        NetMyBookVualueFragment bookVualueFragment = NetMyBookVualueFragment.newInstance(String.valueOf(mVideoid), "");
        fragments.add(tableFragment);
        fragments.add(bookVualueFragment);
        return fragments;
    }

    @Override
    public void BookInfomError(String msgt) {
        if (mShowDialog != null && mShowDialog.isShowing()) {
            mShowDialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }


    /**
     * 播放模式
     *
     * @author TanQu
     */
    public enum PlayMode {
        /**
         * 横屏
         */
        landScape(3),
        /**
         * 竖屏
         */
        portrait(4);

        private final int code;

        private PlayMode(int code) {
            this.code = code;
        }

        /**
         * 取得类型对应的code
         *
         * @return
         */
        public int getCode() {
            return code;
        }

        public static PlayMode getPlayMode(int code) {
            switch (code) {
                case 3:
                    return landScape;
                case 4:
                    return portrait;
            }

            return null;
        }
    }

    /**
     * 显示pop
     */
    private void showPopwindow() {
        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
//        mDao = DbHelperDownAssist.getInstance();
        mDao = MyAppliction.getInstance().getDownDao();
        popDown = new CommonPopupWindow(this, R.layout.pop_net_down_layout, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
            private boolean isRuning = true;
            int downnumber = 0;
            int downnumberOver = 0;
            private ExecutorService executorService;
            private Button mBtnPopDownRun;
            private Button mBtnPopDownLook;
            private Button mBtnPopDownAll;
            private Button mBtnPopDownSureAll;
            private Button mBtnPopDownCancel;
            private TextView mTvNetPopEmpty;
            private RecyclerView mRlvTableList;
            private ImageView mIvNetPopBack;
            private RadioButton mChbNetPopDownLiu;
            private RadioButton mChbNetPopDownGao;
            private RadioButton mChbNetPopDownChao;
            int bitrer = 2;
            boolean isSure = false;
            private DownVideoDb vo;
            private AlertDialog dialog;
            private Handler handler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.arg1 == 1) {
                        vo = (DownVideoDb) msg.obj;
//                      DbHelperDownAssist.getInstance().addDownItem(vo);
                        if (dialog != null) {
                            dialog.dismiss();
                            dialog = null;
                            isRuning = false;
                            executorService.shutdown();
                            isShow(false, true, false, true, false);
                            mBtnPopDownSureAll.setText("全部缓存(" + vo.getDownlist().size() + ")");
//                            mDownAdapter.notifyDataSetChanged();
                        } else {
                            isRuning = false;
                            executorService.shutdown();
//                            DbHelperDownAssist.getInstance().addDownItem(vo);
                            startDown(vo);
                            mBtnPopDownRun.setVisibility(View.VISIBLE);
                            mBtnPopDownLook.setVisibility(View.GONE);
                            mBtnPopDownRun.setText("正在缓存(" + (downnumber + downnumberOver) + ")");


                        }
                    }
                }
            };

            @Override
            protected void initView() {
                View view = getContentView();
                mBtnPopDownLook = view.findViewById(R.id.btn_pop_down_look);
                mBtnPopDownAll = view.findViewById(R.id.btn_pop_down_all);
                mBtnPopDownSureAll = view.findViewById(R.id.btn_pop_down_sure_all);
                mBtnPopDownCancel = view.findViewById(R.id.btn_pop_down_cancel);
                mBtnPopDownRun = view.findViewById(R.id.btn_pop_down_run);
                mRlvTableList = view.findViewById(R.id.rlv_table_list);
                mIvNetPopBack = view.findViewById(R.id.iv_net_pop_back);
                mChbNetPopDownLiu = view.findViewById(R.id.chb_net_pop_down_liu);
                mChbNetPopDownGao = view.findViewById(R.id.chb_net_pop_down_gao);
                mChbNetPopDownChao = view.findViewById(R.id.chb_net_pop_down_chao);
                mTvNetPopEmpty = view.findViewById(R.id.tv_net_pop_empty);
                if (mBookList == null || mBookList.isEmpty()) {
                    mTvNetPopEmpty.setVisibility(View.VISIBLE);
                    mRlvTableList.setVisibility(View.GONE);
                }
                isShow(true, false, true, false, false);
            }

            @Override
            protected void initEvent() {
                DownVideoDb videoDb = mDao.queryUserDownInfomWithKid(mClassId);
                if (videoDb != null && videoDb.getDownlist() != null && !videoDb.getDownlist().isEmpty()) {
                    List<DownVideoVo> downlist = videoDb.getDownlist();
                    for (DownVideoVo vo : downlist) {
                        if (vo.getStatus().equals("1") || vo.getStatus().equals("2")) {
                            downnumberOver += 1;
                        }
                    }
                    if (downnumberOver == 0) {
                        mBtnPopDownRun.setVisibility(View.GONE);
                        mBtnPopDownLook.setVisibility(View.VISIBLE);
                    } else {
                        mBtnPopDownRun.setVisibility(View.VISIBLE);
                        mBtnPopDownLook.setVisibility(View.GONE);
                        mBtnPopDownRun.setText("正在缓存(" + downnumberOver + ")");
                    }
                }
                mIvNetPopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popDown.getPopupWindow().dismiss();
                    }
                });

                bindAdapter();
                mChbNetPopDownChao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            bitrer = 3;
                        }
                    }
                });
                mChbNetPopDownGao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            bitrer = 2;
                        }
                    }
                });
                mChbNetPopDownLiu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            bitrer = 1;
                        }
                    }
                });
                //全部缓存确认
                mBtnPopDownSureAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow(true, false, true, false, false);
//                        DbHelperDownAssist.getInstance().addDownItem(vo);
                        startDown(vo);
                    }
                });
                //全选
                mBtnPopDownAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNoCantdownnetwork(true, null, -1)) return;

                        dowmStatus(true, null, -1);
//                        downAll();
                    }
                });
                //取消按钮
                mBtnPopDownCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow(true, false, true, false, false);
                    }
                });
                //正在缓存
                mBtnPopDownRun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popDown.getPopupWindow().dismiss();
                        Intent intent = NetBookDowningActivity.newInstance(mContext, String.valueOf(bookInfmo.getId()));
                        startActivity(intent);
                    }
                });
                //查看缓存
                mBtnPopDownLook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popDown.getPopupWindow().dismiss();
                        Intent intent = NetBookDowningActivity.newInstance(mContext, String.valueOf(bookInfmo.getId()));
                        startActivity(intent);
                    }
                });


            }

            private void downAll() {
                dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
                isRuning = true;
                GetNetBookService(mBookList, bitrer);
            }

            /**
             * 下载适配器
             */
            private void bindAdapter() {
                GridLayoutManager manager = new GridLayoutManager(mContext, 1);
                manager.setOrientation(GridLayoutManager.VERTICAL);
                mRlvTableList.setLayoutManager(manager);
                mDownAdapter = new NetMyDownTableAdapter(mContext, mBookList, bookInfmo.getId());
                mRlvTableList.setAdapter(mDownAdapter);
                mDownAdapter.setClickListener(new NetMyDownTableAdapter.onItemClickListener() {
                    @Override
                    public void onClickListener(ChaptersBeanVo obj, int position) {
                        downnumber += 1;
                        if (mBtnPopDownCancel.getVisibility() == View.VISIBLE) {
                            downnumberOver = 0;
                            vo = null;
                            isShow(true, false, true, false, false);
                        }
                        if (isNoCantdownnetwork(false, obj, position)) return;
                        dowmStatus(false, obj, position);

                    }
                });
            }

            /**
             *
             * @param isAll 是否全部下载
             * @param obj  下载数据
             * @param position  单个下载时需要
             */
            private void dowmStatus(boolean isAll, ChaptersBeanVo obj, int position) {
                if (isAll)
                    downAll();
                else
                    down(obj, position);
            }


            private void down(ChaptersBeanVo obj, int position) {
                //添加到数据库
                List<ChaptersBeanVo> list = new ArrayList<>();
                ChaptersBeanVo vo = new ChaptersBeanVo();
                vo.setChapterid(obj.getChapterid());
                vo.setChaptername(obj.getChaptername());
                vo.setCourseid(obj.getCourseid());
                vo.setSort(obj.getSort());
                List<VideosBeanVo> vos = new ArrayList<>();
                VideosBeanVo beanVo = obj.getVideos().get(position);
                vos.add(beanVo);
                vo.setVideos(vos);
                list.add(vo);
                isRuning = true;
                GetNetBookService(list, bitrer);
            }

            /**
             * @param list
             * @param bitrate 编码
             */
            private void GetNetBookService(final List list, final int bitrate) {
                if (mBookList == null || mBookList.isEmpty()) {
                    return;
                }
                executorService = Executors.newFixedThreadPool(5);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (isRuning) {
                            DownVideoDb db = addListData(list, bitrate);
                            Message message = new Message();
                            message.arg1 = 1;
                            message.obj = db;
                            handler.sendMessage(message);
                        }
                    }
                });

//                thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (isRuning) {
//                            DownVideoDb db = addListData(list, bitrate);
//                            Message message = new Message();
//                            message.arg1 = 1;
//                            message.obj = db;
//                            handler.sendMessage(message);
//                        }
//                    }
//                });
//                thread.start();
                //NetBookService.startActionBaz(mContext, list, bitrate, String.valueOf(dataVo.getId()));
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setBackgroundAlpha(1f, NetBookMyInfomActivity.this);
                    }
                });
            }

            private void isShow(boolean all, boolean cancel, boolean look, boolean sureall, boolean run) {
                mBtnPopDownAll.setVisibility(all ? View.VISIBLE : View.GONE);
                mBtnPopDownCancel.setVisibility(cancel ? View.VISIBLE : View.GONE);
                mBtnPopDownLook.setVisibility(look ? View.VISIBLE : View.GONE);
                mBtnPopDownSureAll.setVisibility(sureall ? View.VISIBLE : View.GONE);
                mBtnPopDownRun.setVisibility(run ? View.VISIBLE : View.GONE);
            }

            /**
             * 下载网络判断
             * @param isAll 是否全选
             * @param obj  下载数据
             * @param position 坐标
             * @return
             */

            private boolean isNoCantdownnetwork(boolean isAll, ChaptersBeanVo obj, int position) {
                NetworkToolUtil toolUtil = NetworkToolUtil.getInstance(mContext);
                String stauts = toolUtil.getNetWorkToolStauts();
                if (stauts.equals(DataMessageVo.NONETWORK)) {
                    T.showToast(mContext, getString(R.string.net_error_down));
                    return true;
                }
                if (stauts.equals(DataMessageVo.MONET)) {//移动网络
                    String net = MyAppliction.getInstance().getSelectNet();
                    if (StringUtil.isEmpty(net)) {
                        ShowDownNetDialog(isAll, obj, position);
                        return true;
                    }
                    if (net.equals(DataMessageVo.MONET)) {
                        return false;
                    }
                }
                return false;
            }

            /**
             * 是否全选
             * @param isAll
             */
            public void ShowDownNetDialog(final boolean isAll, final ChaptersBeanVo obj, final int position) {
                DialogUtil dialogUtil = DialogUtil.getInstance();
                dialogUtil.showTitleDialog(mContext, getString(R.string.net_down), getStringWithId(R.string.sure)
                        , getStringWithId(R.string.cancel), true);
                dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        if (isAll)
                            downAll();
                        else
                            down(obj, position);
                        MyAppliction.getInstance().saveSelectNet(DataMessageVo.MONET);
                    }

                    @Override
                    public void onCancelClickListener() {

                    }
                });
            }


        };

        popDown.showAtLocation(mLlNetPlayRoot, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, NetBookMyInfomActivity.this);
    }

    private void startDown(final DownVideoDb vo) {
        for (int i = 0; i < vo.getDownlist().size(); i++) {
            final DownVideoVo vo1 = vo.getDownlist().get(i);
            Video.loadVideo(vid, new Video.OnVideoLoaded() {
                @Override
                public void onloaded(@Nullable Video v) {
                    if (v == null) {
                        Toast.makeText(mContext, "获取下载信息失败，请重试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 码率数
                    String[] items = PolyvBitRate.getBitRateNameArray(v.getDfNum());
                    if (items.length == 1) {
                        vo1.setBitRate("1");
                    }
                    if (items.length == 2) {
                        int rate = Integer.parseInt(vo1.getBitRate());
                        if (rate == 3) {
                            vo1.setBitRate("2");
                        }
                        if (rate == 1) {
                            vo1.setBitRate("1");
                        }

                    }
                    if (items.length == 3) {
                        int rate = Integer.parseInt(vo1.getBitRate());
                        if (rate == 3) {
                            vo1.setBitRate("3");
                        }
                        if (rate == 2) {
                            vo1.setBitRate("2");
                        }
                        if (rate == 1) {
                            vo1.setBitRate("1");
                        }
                    }
                    doDown(vo, vo1);

                }
            });

        }
    }

    private void doDown(DownVideoDb vo, DownVideoVo vo1) {
        if (mDao != null && !mDao.isAdd(vo, vo1)) {
            mDao.addDownItem(vo);
            PolyvDownloader polyvDownloader = PolyvDownloaderManager.getPolyvDownloader(vid, Integer.parseInt(vo1.getBitRate()));
            polyvDownloader.setPolyvDownloadProressListener(new MyDownloadListener(mContext, vo, vo1));
            polyvDownloader.start(mContext);
            mDownAdapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, "已经添加到下载队列");
        }
    }


    /**
     * 添加数据
     *
     * @param table
     * @param bitrate
     * @return
     */
    public DownVideoDb addListData(List table, int bitrate) {
        List<ChaptersBeanVo> mDataList = (List<ChaptersBeanVo>) table;
        DownVideoDb db = new DownVideoDb();
        db.setKid(String.valueOf(bookInfmo.getId()));
        db.setKName(bookInfmo.getName());
        db.setUrlImg(bookInfmo.getCoverimg());
        List<DownVideoVo> list = new ArrayList<>();
        for (int i = 0; i < mDataList.size(); i++) {
            ChaptersBeanVo vo = mDataList.get(i);
            List<VideosBeanVo> videos = vo.getVideos();
            if (videos != null && !videos.isEmpty()) {
                for (int j = 0; j < videos.size(); j++) {
                    final VideosBeanVo beanVo = videos.get(j);
                    try {
                        addData(bitrate, vo.getSort(), vo.getChaptername(), beanVo, list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        db.setDownlist(list);
//        DbHelperDownAssist.getInstance().addDownItem(db);
        return db;
    }

    /**
     * 设置背景颜色
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);

    }

    private void addData(int mBitrate, int sort, String pName, VideosBeanVo beanVo, List<DownVideoVo> db) throws JSONException {
        DownVideoVo vo = new DownVideoVo();
        PolyvSDKUtil sdkUtil = new PolyvSDKUtil();
        PolyvVideoVO video = sdkUtil.loadVideoJSON2Video(beanVo.getVid());
        //总时长
        if (video == null) {
            return;
        }
        String duration = video.getDuration();
        //大小
        long type = video.getFileSizeMatchVideoType(mBitrate);
        vo.setDuration(duration);
        vo.setZsort(String.valueOf(beanVo.getSort()));
        vo.setPsort(String.valueOf(sort));
        vo.setpName(pName);

        vo.setBitRate(String.valueOf(mBitrate));

        vo.setFileSize(type);
        //视频id
        vo.setZid(String.valueOf(beanVo.getVideoid()));
        //篇id
        vo.setPid(String.valueOf(beanVo.getChapterid()));
        //保利视频id
        vo.setVid(beanVo.getVid());
        //视频名字
        vo.setTitle(beanVo.getVideoname());
        //该视频id
        vo.setVideoOid(String.valueOf(beanVo.getVideoid()));
        vo.setStatus("2");
        db.add(vo);
        Log.e("==视频信息==", mBitrate + "\naddData:总时长 " + duration + "\n"
                + "总大小" + type + "\n"
                + beanVo.getVideoname() + "\n"
                + beanVo.getVid() + "\n");

    }

    /**
     * 播放网络判断
     *
     * @return
     */
    private boolean network() {
        NetworkToolUtil toolUtil = NetworkToolUtil.getInstance(mContext);
        String stauts = toolUtil.getNetWorkToolStauts();
        if (stauts.equals(DataMessageVo.NONETWORK)) {
            T.showToast(mContext, getStringWithId(R.string.net_error_play));
            return true;
        }
        if (stauts.equals(DataMessageVo.MONET)) {//移动网络
            String net = MyAppliction.getInstance().getSelectNet();
            if (MyAppliction.getInstance().getSaveFGP()) {
                play();
                return false;
            }
            if (StringUtil.isEmpty(net)) {
                ShowNetDialog(true, false);
                return true;
            }
            if (net.equals(DataMessageVo.MONET)) {
                play();
            }
        }
        return false;
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
                if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
                    startPlay();
                } else if (wifiNetworkInfo != null && dataNetworkInfo != null && !wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    mFirst += 1;
                    mobileNet();
                } else {
//                    pausePlay();
                    T.showToast(mContext, getStringWithId(R.string.no_net));
                }
            } else {
                System.out.println("API level 大于21");
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
////                MyAppliction.getInstance().saveUserNetSatus("1");
//                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
//            } else
                if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
//                    MyAppliction.getInstance().saveUserNetSatus(DataMessageVo.WIFI);
                    startPlay();
//                Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                } else if (wifiNetworkInfo != null && dataNetworkInfo != null && !wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    mFirst += 1;
                    mobileNet();
//                Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                } else {
//                    pausePlay();
                    T.showToast(mContext, getStringWithId(R.string.no_net));
//                Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    /**
     * 移动网络
     */
    private void mobileNet() {
        if (mFirst == 1) {
            return;
        }
        String net = MyAppliction.getInstance().getSelectNet();
        boolean play = isPlay();
        if (StringUtil.isEmpty(net)) {
            if (play) {//是否播放
                pausePlay();
            }
            ShowNetDialog(false, play);
            return;
        }


    }

    /**
     * 暂停播放播放
     *
     * @return
     */
    private void pausePlay() {
        if (!isPlayafter) return;
        videoView.stopPlayback();
//    videoView.pause();

    }

    /**
     * 是否播放
     */
    private boolean isPlay() {
        return videoView.isPlayState();
    }

    /**
     * 开始
     */
    private void startPlay() {
        if (!isPlayafter) return;
        videoView.setAutoContinue(true);
        videoView.resume();
//        videoView.start();
    }

    /**
     * @param click   是否是点击
     * @param playing 是否正在播放
     */
    public void ShowNetDialog(final boolean click, final boolean playing) {
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, getString(R.string.net_look), getStringWithId(R.string.sure)
                , getStringWithId(R.string.cancel), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                if (click) {
                    play();
                }
                if (playing) {
                    startPlay();
                }
                MyAppliction.getInstance().saveSelectNet(DataMessageVo.MONET);
            }

            @Override
            public void onCancelClickListener() {

            }
        });
    }


}
