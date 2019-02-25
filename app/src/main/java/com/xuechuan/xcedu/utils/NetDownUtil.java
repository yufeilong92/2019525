package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.util.Log;

import com.easefun.polyvsdk.PolyvSDKUtil;
import com.easefun.polyvsdk.vo.PolyvVideoVO;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.player.BaolIHttp.PolyvVlmsHelper;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetDownAnsyTask.java
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: YFL
 * @date: 2018/5/18 22:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/18 星期五
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class NetDownUtil {

    private PolyvVlmsHelper helper;
    private List<ChaptersBeanVo> mDataList;
    final List<VideosBeanVo> vos = new ArrayList<>();
    private int mBitrate;
    private String mkid;
    private static NetDownUtil ansyTask;
    private String kname;
    private String kImg;

    public interface onItemListener {
        public void onDone();
    }

    public static NetDownUtil getInstance() {
        if (ansyTask == null)
            ansyTask = new NetDownUtil();
        return ansyTask;
    }

    public NetDownUtil() {
        helper = new PolyvVlmsHelper();
    }

    private onItemListener itemListener;

    public void setItemListener(onItemListener itemListener) {
        this.itemListener = itemListener;
    }

    /**
     * @param context
     * @param table   课程表
     * @param bitrate 码率
     * @param Kid     课目id
     */
    public void startAddData(Context context, List table, int bitrate, String Kid, String kName, String kimg) {
        mDataList = (List<ChaptersBeanVo>) table;
        this.mkid = Kid;
        this.mBitrate = bitrate;
        this.kname = kName;
        this.kImg = kimg;
        start();
    }

    private void start() {
        if (vos.size() > 0) {
            vos.clear();
        }
        DownVideoDb db = new DownVideoDb();
        db.setKid(mkid);
        db.setKName(kname);
        db.setUrlImg(kImg);
        List<DownVideoVo> list = new ArrayList<>();
        for (int i = 0; i < mDataList.size(); i++) {
            ChaptersBeanVo vo = mDataList.get(i);
            List<VideosBeanVo> videos = vo.getVideos();

            if (videos != null && !videos.isEmpty()) {
                for (int j = 0; j < videos.size(); j++) {
                    final VideosBeanVo beanVo = videos.get(j);
                    try {
                        addData(mBitrate, beanVo, list, mBitrate);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        db.setDownlist(list);
//        DbHelperDownAssist.getInstance().addDownItem(db);
        MyAppliction.getInstance().getDownDao().addDownItem(db);
        if (itemListener != null) {
            itemListener.onDone();
        }
    }

    private void addData(int mBitrate, VideosBeanVo beanVo, List<DownVideoVo> db, int bitrate) throws JSONException {
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

        vo.setBitRate(String.valueOf(bitrate));

        vo.setFileSize(type);
        //视频id
        vo.setZid(String.valueOf(beanVo.getVideoid()));
        //篇id
        vo.setPid(String.valueOf(beanVo.getChapterid()));
        //保利视频id
        vo.setVid(beanVo.getVid());
        //视频名字
        vo.setTitle(beanVo.getVideoname());
        vo.setVideoOid(String.valueOf(beanVo.getVideoid()));
        vo.setStatus("2");
        db.add(vo);
        Log.e("==视频信息==", bitrate + "\naddData:总时长 " + duration + "\n"
                + "总大小" + type + "\n"
                + beanVo.getVideoname() + "\n"
                + beanVo.getVid() + "\n");

    }


}
