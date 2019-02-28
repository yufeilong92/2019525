package com.xuechuan.xcedu.ui.net;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.event.PalyContentEvent;
import com.xuechuan.xcedu.player.player.PolyvPlayerLightView;
import com.xuechuan.xcedu.player.player.PolyvPlayerMediaController;
import com.xuechuan.xcedu.player.player.PolyvPlayerProgressView;
import com.xuechuan.xcedu.player.player.PolyvPlayerVolumeView;
import com.xuechuan.xcedu.player.util.PolyvErrorMessageUtils;
import com.xuechuan.xcedu.player.util.PolyvScreenUtils;
import com.xuechuan.xcedu.utils.SaveSortUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.MyDownOverListVo;
import com.xuechuan.xcedu.vo.PerInfomVo;
import com.xuechuan.xcedu.vo.VideoSettingVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import org.greenrobot.eventbus.EventBus;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookPlayActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 离线播放页
 * @author: L-BackPacker
 * @date: 2018/5/21 10:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/21
 */
public class NetBookPlayActivity extends BaseActivity {
    private static final String TAG = NetBookPlayActivity.class.getSimpleName();
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
    private String vid;
    private Context mContext;
    private String mKid;
    private String mPid;
    private String mKName;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        setContentView(R.layout.activity_net_book_play);
        findIdAndNew();
        initView();

        PolyvScreenUtils.generateHeight16_9(this);
        PolyvScreenUtils.initTitleBar(ll_title_bar, null, null);
        int playModeCode = getIntent().getIntExtra("playMode", NetBookPlayActivity.PlayMode.portrait.getCode());
        NetBookPlayActivity.PlayMode playMode = NetBookPlayActivity.PlayMode.getPlayMode(playModeCode);
        if (playMode == null)
            playMode = NetBookPlayActivity.PlayMode.portrait;
        vid = getIntent().getStringExtra("value");
        mKid = getIntent().getStringExtra("Kid");
        mPid = getIntent().getStringExtra("Pid");
        mKName = getIntent().getStringExtra("KName");
        int bitrate = getIntent().getIntExtra("bitrate", PolyvBitRate.ziDong.getNum());
        boolean startNow = getIntent().getBooleanExtra("startNow", false);
        boolean isMustFromLocal = getIntent().getBooleanExtra("isMustFromLocal", false);
        switch (playMode) {
            case landScape:
                mediaController.changeToLandscape();
                break;
            case portrait:
                mediaController.changeToPortrait();
                break;
        }
        play(vid, bitrate, startNow, isMustFromLocal);
    }


    private void findIdAndNew() {
        ll_title_bar = findViewById(R.id.activity_title_container);
        viewLayout = (RelativeLayout) findViewById(R.id.view_layout);
        videoView = (PolyvVideoView) findViewById(R.id.polyv_video_view);
        marqueeView = (PolyvMarqueeView) findViewById(R.id.polyv_marquee_view);
        mediaController = (PolyvPlayerMediaController) findViewById(R.id.polyv_player_media_controller);
        lightView = (PolyvPlayerLightView) findViewById(R.id.polyv_player_light_view);
        volumeView = (PolyvPlayerVolumeView) findViewById(R.id.polyv_player_volume_view);
        progressView = (PolyvPlayerProgressView) findViewById(R.id.polyv_player_progress_view);
        loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        mediaController.initTitltBar(ll_title_bar, null, null);
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

    private void initView() {
        mContext = this;
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
                    Toast.makeText(NetBookPlayActivity.this, "状态错误 " + status, Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(NetBookPlayActivity.this);
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
                Toast.makeText(NetBookPlayActivity.this, "当前视频无法播放，请向管理员反馈(error code " + PolyvPlayErrorReason.VIDEO_ERROR + ")", Toast.LENGTH_SHORT).show();
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
                Log.d(TAG, String.format("LeftUp %b %b brightness %d", start, end, videoView.getBrightness(NetBookPlayActivity.this)));
                int brightness = videoView.getBrightness(NetBookPlayActivity.this) + 5;
                if (brightness > 100) {
                    brightness = 100;
                }

                videoView.setBrightness(NetBookPlayActivity.this, brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureLeftDownListener(new IPolyvOnGestureLeftDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftDown %b %b brightness %d", start, end, videoView.getBrightness(NetBookPlayActivity.this)));
                int brightness = videoView.getBrightness(NetBookPlayActivity.this) - 5;
                if (brightness < 0) {
                    brightness = 0;
                }

                videoView.setBrightness(NetBookPlayActivity.this, brightness);
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
                nextPlayVideo();
            }
        });
    }

    private void nextPlayVideo() {
        if (StringUtil.isEmpty(vid))
            return;
        DbHelperDownAssist downAssist = DbHelperDownAssist.getInstance(mContext);
        DownVideoDb downVideoDb = downAssist.queryUserDownInfomWithKid(mKid);
        if (downVideoDb == null) return;
        List<DownVideoVo> downlist = downVideoDb.getDownlist();
        if (downlist == null || downlist.isEmpty())
            return;
        MyDownOverListVo psort = SaveSortUtil.getInstance().psort(downVideoDb);
        if (psort == null) return;
        int mZindex = -1;
        for (int i = 0; i < downlist.size(); i++) {
            DownVideoVo videoVo = downlist.get(i);
            if (videoVo.getVid().equalsIgnoreCase(vid)) {
                mZindex = i;
            }
        }
        if (mZindex == -1) return;
        //找到该片视频的播放位置
        findVidPostion(psort, vid);


  /*      mZindex += 1;
        if (mZindex == downlist.size()) {
            T.showToast(mContext, getStringWithId(R.string.lastVideo));
            return;
        }
        DownVideoVo videoVo = downlist.get(mZindex);
        if (videoVo != null) {
            vid = videoVo.getVid();
            play(vid, Integer.parseInt(videoVo.getBitRate()), true, true);
        }*/
    }

    /**
     * 排序后的位置
     *
     * @param psort
     * @param baolivid 当前视频id
     */
    private void findVidPostion(MyDownOverListVo psort, String baolivid) {
        List<MyDownOverListVo.PdownListVo> downlist = psort.getDownlist();
        if (downlist == null || downlist.isEmpty()) return;

        //当前篇id
        int pPostion = -1;
        //当前章id
        int zPositon = -1;
        boolean isLast = false;
        int listSize = downlist.size();
        for (int i = 0; i < listSize; i++) {
            MyDownOverListVo.PdownListVo vo = downlist.get(i);
            if (vo.getChapterid().equalsIgnoreCase(mPid)) {//找到相应篇id
                ArrayList<MyDownOverListVo.zDownListVo> vos = vo.getzDownlist();
                int size = vos.size();
                for (int k = 0; k < size; k++) {
                    MyDownOverListVo.zDownListVo zDownListVo = vos.get(k);
                    if (baolivid.equalsIgnoreCase(zDownListVo.getVid())) {
                        zPositon = k;
                        pPostion = i;
                    }
                }
                //当前播放为视频最后要一个，则切换下一篇
                if (size == (zPositon + 1))
                    isLast = true;
            }

        }
        if (isLast) {//切换
            if (listSize > (pPostion + 1)) {
                MyDownOverListVo.PdownListVo vo = downlist.get(pPostion + 1);
                ArrayList<MyDownOverListVo.zDownListVo> zDownListVos = vo.getzDownlist();
                if (zDownListVos == null || zDownListVos.isEmpty()) return;
                MyDownOverListVo.zDownListVo z = zDownListVos.get(0);
                vid = z.getVid();
                mPid = z.getPid();
                mKName=z.getpName();
                play(vid, Integer.parseInt(z.getBitRate()), true, true);
                return;
            } else {
                T.showToast(mContext, getStringWithId(R.string.lastVideo));
                return;
            }
        } else {
            MyDownOverListVo.PdownListVo pdownListVo = downlist.get(pPostion);
            ArrayList<MyDownOverListVo.zDownListVo> vos = pdownListVo.getzDownlist();
            MyDownOverListVo.zDownListVo vo = vos.get(zPositon + 1);
            vid = vo.getVid();
            mKName=vo.getpName();
            play(vid, Integer.parseInt(vo.getBitRate()), true, true);
            return;
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
    public void play(final String vid, final int bitrate, boolean startNow,
                     final boolean isMustFromLocal) {
        if (TextUtils.isEmpty(vid)) return;
        videoView.release();
        mediaController.hide();
        loadingProgress.setVisibility(View.GONE);
        progressView.resetMaxValue();
        if (mediaController != null) {
            mediaController.setVideoTitle(mKName);
        }
        if (startNow) {
            if (videoView.isCompletedState()) {
                videoView.seekTo(0);
            }
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
//        danmuFragment.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.destroy();
        mediaController.disable();
        EventBus.getDefault().postSticky(new PalyContentEvent(vid));
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

    public static Intent newIntent(Context context, NetBookPlayActivity.PlayMode
            playMode, String vid) {
        return newIntent(context, playMode, vid, PolyvBitRate.ziDong.getNum());
    }

    public static Intent newIntent(Context context, NetBookPlayActivity.PlayMode
            playMode, String vid, int bitrate) {
        return newIntent(context, playMode, vid, bitrate, false);
    }

    public static Intent newIntent(Context context, NetBookPlayActivity.PlayMode
            playMode, String vid, int bitrate, boolean startNow) {
        return newIntent(context, playMode, vid, bitrate, startNow, false);
    }

    public static Intent newIntent(Context context, NetBookPlayActivity.PlayMode
            playMode, String vid, int bitrate, boolean startNow,
                                   boolean isMustFromLocal) {
        Intent intent = new Intent(context, NetBookPlayActivity.class);
        intent.putExtra("playMode", playMode.getCode());
        intent.putExtra("value", vid);
        intent.putExtra("bitrate", bitrate);
        intent.putExtra("startNow", startNow);
        intent.putExtra("isMustFromLocal", isMustFromLocal);
        return intent;
    }

    public static Intent newIntent(Context context, NetBookPlayActivity.PlayMode
            playMode, String vid, int bitrate, boolean startNow,
                                   boolean isMustFromLocal, String kid, String pid, String name) {
        Intent intent = new Intent(context, NetBookPlayActivity.class);
        intent.putExtra("playMode", playMode.getCode());
        intent.putExtra("value", vid);
        intent.putExtra("bitrate", bitrate);
        intent.putExtra("startNow", startNow);
        intent.putExtra("isMustFromLocal", isMustFromLocal);
        intent.putExtra("Kid", kid);
        intent.putExtra("Pid", pid);
        intent.putExtra("KName", name);
        return intent;
    }

    public static void intentTo(Context context, NetBookPlayActivity.PlayMode playMode, String
            vid) {
        intentTo(context, playMode, vid, PolyvBitRate.ziDong.getNum());
    }

    public static void intentTo(Context context, NetBookPlayActivity.PlayMode playMode, String
            vid, int bitrate) {
        intentTo(context, playMode, vid, bitrate, false);
    }

    public static void intentTo(Context context, NetBookPlayActivity.PlayMode playMode, String
            vid, int bitrate, boolean startNow) {
        intentTo(context, playMode, vid, bitrate, startNow, false);
    }

    public static void intentTo(Context context, NetBookPlayActivity.PlayMode playMode, String
            vid, int bitrate, boolean startNow,
                                boolean isMustFromLocal) {
        context.startActivity(newIntent(context, playMode, vid, bitrate, startNow, isMustFromLocal));
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

        public static NetBookPlayActivity.PlayMode getPlayMode(int code) {
            switch (code) {
                case 3:
                    return landScape;
                case 4:
                    return portrait;
            }

            return null;
        }
    }

}
