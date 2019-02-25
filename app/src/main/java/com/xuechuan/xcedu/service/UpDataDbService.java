package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.umeng.debug.log.I;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.utils.SaveSortUtil;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: UpDataDbService
 * @Package com.xuechuan.xcedu.service
 * @Description: 更新数据库
 * @author: L-BackPacker
 * @date: 2018/8/2 20:05
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/2
 */
public class UpDataDbService extends IntentService {
    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";

    private static final String EXTRA_PARAM1 = "com.xuechuan.xcedu.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.xuechuan.xcedu.service.extra.PARAM2";

    public UpDataDbService() {
        super("UpDataDbService");
    }

    public static void startActionFoo(Context context, String param1, List<ChaptersBeanVo> vo) {
        Intent intent = new Intent(context, UpDataDbService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, (Serializable) vo);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String mkid = intent.getStringExtra(EXTRA_PARAM1);
                final List<ChaptersBeanVo> vo = (List<ChaptersBeanVo>) intent.getSerializableExtra(EXTRA_PARAM2);
                handleActionFoo(mkid, vo);
            }
        }
    }

    /**
     * @param mkid 课目id
     * @param vo   线上数据
     */
    private void handleActionFoo(String mkid, List<ChaptersBeanVo> vo) {
        if (vo == null || vo.isEmpty()) return;
        DbHelperDownAssist mDao = DbHelperDownAssist.getInstance(getBaseContext());
        //该科已下载数据
        DownVideoDb db = mDao.queryUserDownInfomWithKid(mkid);
        List<DownVideoVo> downlist = db.getDownlist();
        if (downlist == null || db.getDownlist().isEmpty()) {
            stopSelf();
            return;
        }
        //获取用户下载的数据
        for (int i = 0; i < downlist.size(); i++) {
            //用户下载的数据
            DownVideoVo videoVo = downlist.get(i);
            //找到该篇id 进行srot 赋值
            String pid = videoVo.getPid();
            //比较线上数据
            for (int k = 0; k < vo.size(); k++) {
                ChaptersBeanVo xvo = vo.get(k);
                if (pid.equalsIgnoreCase(String.valueOf(xvo.getChapterid()))) {//相同篇
                    videoVo.setPsort(String.valueOf(xvo.getSort()));
                    videoVo.setpName(xvo.getChaptername());
                    //比较已下载的章
                    List<VideosBeanVo> videos = xvo.getVideos();
                    if (videos != null && !videos.isEmpty()) {
                        for (int j = 0; j < videos.size(); j++) {
                            VideosBeanVo beanVo = videos.get(j);
                            if (videoVo.getVid().equalsIgnoreCase(beanVo.getVid())) {
                                videoVo.setZsort(String.valueOf(beanVo.getSort()));
                            }
                        }
                    }
                }
            }
        }
        mDao.UpDataDownItem(db);
    }

    private DownVideoDb sort(DownVideoDb db) {
        List<DownVideoVo> downlist = db.getDownlist();
        if (downlist != null && !downlist.isEmpty()) {

            //找到有几篇
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < downlist.size(); i++) {
                DownVideoVo vo = downlist.get(i);
                if (list.contains(Integer.parseInt(vo.getPsort()))) {
                    continue;
                } else
                    list.add(Integer.parseInt(vo.getPsort()));
            }
            //排序
            Collections.sort(list);
            ArrayList<DownVideoVo> downLists = new ArrayList<DownVideoVo>();
            //该篇下章节数据
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Integer psort = list.get(i);
                    //临时保存
                    ArrayList<DownVideoVo> downVideoVos = new ArrayList<DownVideoVo>();
                    for (int k = 0; k < downlist.size(); k++) {
                        //对该篇下进行排序
                        DownVideoVo vo = downlist.get(k);
                        if (psort == Integer.parseInt(vo.getPsort())) {
                            downVideoVos.add(vo);
                        }
                    }
                    sortData(downVideoVos);
                    downLists.addAll(downVideoVos);
                }
            }
            db.setDownlist(downLists);

        }

        return db;
    }

    /**
     * 对章节排序
     *
     * @param downlist
     */
    private void sortData(ArrayList<DownVideoVo> downlist) {
        Collections.sort(downlist, new Comparator<DownVideoVo>() {
            @Override
            public int compare(DownVideoVo o1, DownVideoVo o2) {
                if (Integer.parseInt(o1.getZsort()) > Integer.parseInt( o2.getZsort())) {
                    return 1;
                } else if (Integer.parseInt(o1.getZsort())  <  Integer.parseInt( o2.getZsort())) {
                    return 0;
                }
                return 0;
            }
        });
    }
}
