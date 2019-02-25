package com.xuechuan.xcedu.ui.net;

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
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.PolyvBitRate;
import com.easefun.polyvsdk.PolyvSDKUtil;
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
import com.google.gson.Gson;
import com.xuechuan.xcedu.event.NetPlayEvent;
import com.xuechuan.xcedu.event.NetPlayTrySeeEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.home.MyNetBookIndicatorAdapter;
import com.xuechuan.xcedu.adapter.home.MyTagPagerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.fragment.net.NetBookinfomFragment;
import com.xuechuan.xcedu.fragment.net.NetTableFragment;
import com.xuechuan.xcedu.mvp.contract.VideoBooksContract;
import com.xuechuan.xcedu.mvp.model.MyVideoBooksModel;
import com.xuechuan.xcedu.mvp.presenter.VideoInfomsPresenter;
import com.xuechuan.xcedu.net.NetService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.player.player.PolyvPlayerLightView;
import com.xuechuan.xcedu.player.player.PolyvPlayerMediaController;
import com.xuechuan.xcedu.player.player.PolyvPlayerProgressView;
import com.xuechuan.xcedu.player.player.PolyvPlayerVolumeView;
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
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.vo.NetBookTableVo;
import com.xuechuan.xcedu.vo.PerInfomVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.VideoSettingVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;
import com.xuechuan.xcedu.weight.NoScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookInfomActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 网课详情页
 * @author: L-BackPacker
 * @date: 2018/5/14 14:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */
public class NetBookInfomActivity extends BaseActivity implements View.OnClickListener, VideoBooksContract.View {
    private static final String TAG = NetBookInfomActivity.class.getSimpleName();
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

    /***
     * 数据类型
     */
    public static final String CLASSID = "classid";
    private String mClassId;
    private TextView mTvNetBookTitle;
    private MagicIndicator mNetMagicIndicator;
    private NoScrollViewPager mVpNetBar;
    private Context mContext;
    private TextView mTvNetBookAllprice;
    private TextView mTvNetContactService;
    private Button mBtnNetGoBuy;
    private ImageView mIvNetPlay;
    private ImageView mIvNetBookPlay;
    private RelativeLayout mRlPlaylayout;
    private String vid;
    private LinearLayout mLlNetPlayRoot;
    private LinearLayout mLlNetBuyLayou;
    /**
     * 课程详情
     */
    private ClassBeanVideoVo bookInfom;
    private AlertDialog mShowDialog;
    private LinearLayout mLiContent;
    private TextView mTvEmpty;
    private MyNetBorect netBorect;
    private int mFirst = 0;
    private List<GiftVo> mGiftvs;
    //标题栏显示得内容
    private String mTitleName;
    private int mVideoid;
    private DataMessageVo mMessageVo;

    public static Intent newInstance(Context context, String classid) {
        Intent intent = new Intent(context, NetBookInfomActivity.class);
        intent.putExtra(CLASSID, classid);
        return intent;
    }

    /*   @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_net_book_infom);
           initView();
       }
   */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        setContentView(R.layout.activity_net_book_infom);
        if (getIntent() != null) {
            mClassId = getIntent().getStringExtra(CLASSID);
        }
        mMessageVo = DataMessageVo.get_Instance();
        initView();
        initViewData();
        PolyvScreenUtils.generateHeight16_9(this);
        PolyvScreenUtils.initTitleBar(ll_title_bar, mRlPlaylayout, mLlNetBuyLayou);
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
                ll_title_bar.setVisibility(View.GONE);
                mediaController.changeToLandscape();
                break;
            case portrait://竖屏
                ll_title_bar.setVisibility(View.VISIBLE);
                mediaController.changeToPortrait();
                break;
        }
        initData();
        EventBus.getDefault().register(this);

    }

    private void initData() {
        VideoInfomsPresenter mPresenter = new VideoInfomsPresenter();
        mPresenter.initModelView(new MyVideoBooksModel(), this);
        mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestBookInfoms(mContext, mClassId);
    }


    /**
     * @param isall       是否是全科
     * @param description
     * @param list        课程表集合
     * @return
     */
    private List<Fragment> creartFragment(boolean isall, String description, List<ChaptersBeanVo> list) {
//        if (list.size() < 2) {
//            mNetMagicIndicator.setVisibility(View.GONE);
//        }
        List<Fragment> fragments = new ArrayList<>();
        NetBookinfomFragment bookinfomFragment = NetBookinfomFragment.newInstance(description);
        fragments.add(bookinfomFragment);
        if (isall) {

        } else {
//        NetBooKListFragment booKListFragment = NetBooKListFragment.newInstance(String.valueOf(dataVo.getId()));
            NetTableFragment booKListFragment = NetTableFragment.newInstance(list);
            fragments.add(booKListFragment);
        }
        return fragments;
    }

    /**
     * 播放视频
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNetMainPlayId(NetPlayEvent event) {
        VideosBeanVo vo = event.getVo();
        L.e(vo.getVid());
        L.e("调用===========" + vo.getVid());
        mTitleName = vo.getVideoname();
        mVideoid = vo.getVideoid();
        mRlPlaylayout.setVisibility(View.GONE);
        play(vo.getVid(), 0, true, false);

    }

    /**
     * 播放视频
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNetMainTryPlayId(NetPlayTrySeeEvent event) {
        VideosBeanVo vo = event.getVo();
        L.e(vo.getVid());
        vid = vo.getVid();
        mTitleName = vo.getVideoname();
    }

    private void initView() {
        mContext = this;
        mLiContent = (LinearLayout) findViewById(R.id.ll_content);
        mTvEmpty = (TextView) findViewById(R.id.tv_empty);
        mLlNetPlayRoot = (LinearLayout) findViewById(R.id.ll_net_play_root);
        mLlNetPlayRoot.setOnClickListener(this);
        mIvNetBookPlay = (ImageView) findViewById(R.id.iv_net_book_play);
        mIvNetBookPlay.setOnClickListener(this);
        mRlPlaylayout = (RelativeLayout) findViewById(R.id.rl_play_layout);
        mIvNetPlay = (ImageView) findViewById(R.id.iv_net_play);
        mTvNetBookAllprice = (TextView) findViewById(R.id.tv_net_book_allprice);
        mTvNetBookAllprice.setOnClickListener(this);
        mTvNetContactService = (TextView) findViewById(R.id.tv_net_contact_service);
        mTvNetContactService.setOnClickListener(this);
        mBtnNetGoBuy = (Button) findViewById(R.id.btn_net_go_buy);
        mBtnNetGoBuy.setOnClickListener(this);
        mTvNetBookTitle = (TextView) findViewById(R.id.tv_net_book_title);
        mTvNetBookTitle.setOnClickListener(this);
        mNetMagicIndicator = (MagicIndicator) findViewById(R.id.net_magic_indicator);
        mNetMagicIndicator.setOnClickListener(this);
        mVpNetBar = (NoScrollViewPager) findViewById(R.id.vp_net_bar);
        mVpNetBar.setOnClickListener(this);
        ll_title_bar = (LinearLayout) findViewById(R.id.activity_title_container);
        viewLayout = (RelativeLayout) findViewById(R.id.view_layout);
        videoView = (PolyvVideoView) findViewById(R.id.polyv_video_view);
        marqueeView = (PolyvMarqueeView) findViewById(R.id.polyv_marquee_view);
        mediaController = (PolyvPlayerMediaController) findViewById(R.id.polyv_player_media_controller);

        lightView = (PolyvPlayerLightView) findViewById(R.id.polyv_player_light_view);
        volumeView = (PolyvPlayerVolumeView) findViewById(R.id.polyv_player_volume_view);
        progressView = (PolyvPlayerProgressView) findViewById(R.id.polyv_player_progress_view);
        loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        mLlNetBuyLayou = (LinearLayout) findViewById(R.id.ll_net_buy_layou);
        mLlNetBuyLayou.setOnClickListener(this);
        mediaController.initTitltBar(ll_title_bar, mRlPlaylayout, mLlNetBuyLayou);
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
                // 没开预加载在这里开始弹幕
                // danmuFragment.start();
            }
        });
        videoView.setOnVideoStatusListener(new IPolyvOnVideoStatusListener() {
            @Override
            public void onStatus(int status) {
                if (status < 60) {
                    Toast.makeText(NetBookInfomActivity.this, "状态错误 " + status, Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(NetBookInfomActivity.this);
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
                Toast.makeText(NetBookInfomActivity.this, "当前视频无法播放，请向管理员反馈(error code " + PolyvPlayErrorReason.VIDEO_ERROR + ")", Toast.LENGTH_SHORT).show();
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
                Log.d(TAG, String.format("LeftUp %b %b brightness %d", start, end, videoView.getBrightness(NetBookInfomActivity.this)));
                int brightness = videoView.getBrightness(NetBookInfomActivity.this) + 5;
                if (brightness > 100) {
                    brightness = 100;
                }

                videoView.setBrightness(NetBookInfomActivity.this, brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureLeftDownListener(new IPolyvOnGestureLeftDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftDown %b %b brightness %d", start, end, videoView.getBrightness(NetBookInfomActivity.this)));
                int brightness = videoView.getBrightness(NetBookInfomActivity.this) - 5;
                if (brightness < 0) {
                    brightness = 0;
                }

                videoView.setBrightness(NetBookInfomActivity.this, brightness);
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
            }
        });

        registerBrocad();
    }

    /**
     * 是否注册广播
     */
    private boolean mIsRegister = false;

    private void registerBrocad() {
        netBorect = new MyNetBorect();
        mIsRegister = true;
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.intent.category.DEFAULT");
        registerReceiver(netBorect, filter);
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
        if (mediaController != null) {
            mediaController.setVideoTitle(mTitleName);
        }
        if (startNow) {
            //调用setVid方法视频会自动播放
            videoView.setVid(vid, bitrate, isMustFromLocal);
        }
    }

    private void clearGestureInfo() {
        videoView.clearGestureInfo();
        progressView.hide();
        volumeView.hide();
        lightView.hide();
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
        submitProgress();
//        danmuFragment.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.destroy();
        EventBus.getDefault().removeAllStickyEvents();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mediaController.disable();
        if (mIsRegister) {
            mIsRegister = false;
            unregisterReceiver(netBorect);
        }
//        submitProgress();
        mMessageVo.setWatchTime(0);
        MyAppliction.getInstance().setIsPlay(false);
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
        Intent intent = new Intent(context, NetBookInfomActivity.class);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_net_contact_service://客服
                Intent intent1 = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + "400-963-8119"));
                startActivity(intent1);
                break;
            case R.id.btn_net_go_buy://购买
                Intent intent = NetBuyActivity.newInstance(mContext, bookInfom.getPrice(), bookInfom.getId(),
                        bookInfom.getName(), bookInfom.getCoverimg(), mGiftvs);
                startActivity(intent);
                break;

            case R.id.iv_net_book_play:
                if (network()) return;
                if (bookInfom.isIsall()) {
                    return;
                }
                play();
                break;
        }
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
            T.showToast(mContext, getString(R.string.net_error_play));
            return true;
        }
        if (stauts.equals(DataMessageVo.MONET)) {//移动网络
            String net = MyAppliction.getInstance().getSelectNet();
            if (StringUtil.isEmpty(net)) {
                ShowNetDialog(true, false);
                return true;
            }
            if (net.equals(DataMessageVo.MONET)) {
                if (bookInfom.isIsall()) {
                    return true;
                }
                play();
            }
        }
        return false;
    }

    /**
     * 播放视频
     */
    private void play() {
        if (!StringUtil.isEmpty(vid)) {
            mRlPlaylayout.setVisibility(View.GONE);
            mTitleName = bookInfom.getName();
            mVideoid = bookInfom.getId();
            play(vid, 0, true, false);
//            mediaController.setIsPlay(true);
            MyAppliction.getInstance().setIsPlay(true);
//            PolyvScreenUtils.IsPlay(true);

        } else {
            T.showToast(mContext, getString(R.string.no_try_see));
        }

    }

    @Override
    public void BookInfomSucces(String com) {
        if (mShowDialog != null && mShowDialog.isShowing())
            mShowDialog.dismiss();
        mLiContent.setVisibility(View.VISIBLE);
        mTvEmpty.setVisibility(View.GONE);
        Gson gson = new Gson();
        NetBookTableVo tableVo = gson.fromJson(com, NetBookTableVo.class);
        if (tableVo != null)
            if (tableVo.getStatus().getCode() == 200) {
                NetBookTableVo.DataBean data = tableVo.getData();
                bookInfom = data.getClassX();
                mGiftvs = data.getGifts();
                List<ChaptersBeanVo> bookLists = data.getChapters();
                bindViewData(bookInfom, bookLists);
            }
    }

    private void bindViewData(ClassBeanVideoVo bookInfom, List<ChaptersBeanVo> bookLists) {
        List<String> list;
        mTvNetBookTitle.setText(bookInfom.getName());
        if (bookInfom.isIsall()) {
            mIvNetBookPlay.setVisibility(View.GONE);
            mNetMagicIndicator.setVisibility(View.GONE);
            list = new ArrayList<>();
            list.add("详情");
        } else {
            mNetMagicIndicator.setVisibility(View.VISIBLE);
            list = ArrayToListUtil.arraytoList(mContext, R.array.net_book_title);
        }
        mNetMagicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdjustMode(true);
        MyNetBookIndicatorAdapter adapter = new MyNetBookIndicatorAdapter(list, mVpNetBar);
        mNetMagicIndicator.setNavigator(commonNavigator);
        commonNavigator.setAdapter(adapter);
        List<Fragment> fragments = creartFragment(bookInfom.isIsall(), bookInfom.getDetailurl(), bookLists);
        MyTagPagerAdapter tagPagerAdapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
        mVpNetBar.setAdapter(tagPagerAdapter);
        mVpNetBar.setOffscreenPageLimit(4);
        ViewPagerHelper.bind(mNetMagicIndicator, mVpNetBar);
        mTvNetBookAllprice.setText("￥" + bookInfom.getPrice() + "");
        if (!StringUtil.isEmpty(bookInfom.getCoverimg())) {
            MyAppliction.getInstance().displayImages(mIvNetPlay, bookInfom.getCoverimg(), false);
        }

    }


    @Override
    public void BookInfomError(String msgt) {
        if (mShowDialog != null && mShowDialog.isShowing())
            mShowDialog.dismiss();
        T.showToast(mContext, getStringWithId(R.string.net_error));
        mLiContent.setVisibility(View.GONE);
        mTvEmpty.setVisibility(View.VISIBLE);
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

    private class MyNetBorect extends BroadcastReceiver {

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
//                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
//            if (wifiNetworkInfo!=null&&wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                MyAppliction.getInstance().saveUserNetSatus("1");
//            } else
                if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
                    startPlay();
//                    Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                } else if (wifiNetworkInfo != null && dataNetworkInfo != null && !wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    mFirst += 1;
                    mobileNet();
//                    Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                } else {
//                    pausePlay();
                    T.showToast(mContext, getStringWithId(R.string.no_net));
//                    Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
                }
            } else {
                //这里的就不写了，前面有写，大同小异
                System.out.println("API level 大于21");
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
         /*   if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                MyAppliction.getInstance().saveUserNetSatus("1");
            } else */
                if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
                    startPlay();
//                    Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                } else if (wifiNetworkInfo != null && dataNetworkInfo != null && !wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    mFirst += 1;
                    mobileNet();
//                    Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                } else {
//                    pausePlay();
                    T.showToast(mContext, getStringWithId(R.string.no_net));
//                    Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
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
        }
    }

    /**
     * 暂停播放播放
     *
     * @return
     */
    private void pausePlay() {
        boolean play = MyAppliction.getInstance().getPlay();
        if (!play) return;
        videoView.stopPlayback();
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
        boolean play = MyAppliction.getInstance().getPlay();
        if (!play) return;
        videoView.setAutoContinue(true);
        videoView.resume();
    }

    /**
     * @param click   是否是点击
     * @param playing 是否正在播放
     */
    public void ShowNetDialog(final boolean click, final boolean playing) {
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, getString(R.string.net_look), getStringWithId(R.string.sure)
                , getStringWithId(R.string.cancel), false);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                if (bookInfom.isIsall()) {
                    return;
                }
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

    /**
     * todo 处理提交播放进度
     * 提交视频进度
     */
    private void submitProgress() {
        int position = videoView.getCurrentPosition();
        if (mVideoid != -1 && mVideoid != 0) {
            if (position != 0) {
                int i = position / 1000;
                position = (int) Math.rint(i);
            }
            int watchTimeDuration = videoView.getWatchTimeDuration();
            int watchTime = mMessageVo.getWatchTime();
            int i = watchTimeDuration - watchTime;
            mMessageVo.setWatchTime(watchTimeDuration);
            SubmitProgress(String.valueOf(mVideoid), String.valueOf(bookInfom.getId()),i, String.valueOf(position));
        }
    }

    /**
     * @param videoId 视频 id
     * @param classId 科目id
     * @param prgress 进度
     */
    private void SubmitProgress(String videoId, String classId,int watchtime, String prgress) {
        NetService service = new NetService(this);
        service.SubmitViewProgres(videoId, classId, prgress,watchtime, new StringCallBackView() {
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

}