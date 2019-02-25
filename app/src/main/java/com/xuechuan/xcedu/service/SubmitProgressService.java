package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.net.NetService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SubmitProgressService
 * @Package com.xuechuan.xcedu.service
 * @Description: 提交播放进度
 * @author: L-BackPacker
 * @date: 2018/5/21 19:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/21
 */

public class SubmitProgressService extends IntentService {
    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";

    private static final String PROGRESS = "com.xuechuan.xcedu.service.extra.progress";
    private static final String CLASSID = "com.xuechuan.xcedu.service.extra.classid";
    private static final String VIDEOID = "com.xuechuan.xcedu.service.extra.videoid";
    private static final String WATCHTIME = "com.xuechuan.xcedu.service.extra.watchttime";

    public SubmitProgressService() {
        super("SubmitProgressServcie");
    }


    /**
     * @param context
     * @param progress 进度
     * @param classId  课目id
     * @param videoId  视频id
     */
    public static void startActionFoo(Context context, String progress, String classId, int watchtime, String videoId) {
        Intent intent = new Intent(context, SubmitProgressService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(PROGRESS, progress);
        intent.putExtra(CLASSID, classId);
        intent.putExtra(VIDEOID, videoId);
        intent.putExtra(WATCHTIME, watchtime);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                Log.d("=====", "提交进度服务");
                final String prgress = intent.getStringExtra(PROGRESS);
                final String classId = intent.getStringExtra(CLASSID);
                final String videoId = intent.getStringExtra(VIDEOID);
                final int wactchtime = intent.getIntExtra(WATCHTIME, 0);
                handleActiodnFoo(prgress, classId, wactchtime, videoId);
            }
        }
    }


    private void handleActiodnFoo(final String prgress, final String classId, int wacthtime, final String videoId) {
        NetService service = new NetService(this);
        L.d("提交进度" + prgress + "classId===" + classId + ":videoid" + videoId + "wactch" + wacthtime);
        service.SubmitViewProgres(videoId, classId, prgress, wacthtime, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                ResultVo vo = gson.fromJson(response, ResultVo.class);
                if (vo.getStatus().getCode() == 200) {
                    L.d("提交成功后的" + response);
                    stopSelf();
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
