package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.lzy.okgo.OkGo;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.MainActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.ui.me.PersionActivity;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SubmitHearService
 * @Package com.xuechuan.xcedu.service
 * @Description: 头像上传
 * @author: L-BackPacker
 * @date: 2018/5/28 15:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/28
 */
public class SubmitHearService extends IntentService {

    private static final String ACTION_BAZ = "com.xuechuan.xcedu.service.action.BAZ";


    private static final String EXTRA_PATH = "com.xuechuan.xcedu.service.extra.path";
    private static final String EXTRA_PARAM2 = "com.xuechuan.xcedu.service.extra.PARAM2";

    public SubmitHearService() {
        super("SubmitHearService");
    }


    public static void startActionBaz(Context context, int imgpath, String param2) {
        Intent intent = new Intent(context, SubmitHearService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PATH, imgpath);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_BAZ.equals(action)) {
                final int path = intent.getIntExtra(EXTRA_PATH, 0);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(path, param2);
            }
        }
    }

    private void handleActionBaz(int path, String param2) {
        CreateNation(1000, param2, path);
    }

    private void CreateNation(int max, String cont, int prgress) {

        Bitmap btm = BitmapFactory.decodeResource(getResources(),
                R.drawable.m_setting_about_xcimg);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getBaseContext()).setSmallIcon(R.drawable.m_setting_about_xcimg)
                .setContentTitle(cont);
        mBuilder.setTicker(cont);//第一次提示消息的时候显示在通知栏上
        mBuilder.setNumber(12);
        mBuilder.setLargeIcon(btm);
        mBuilder.setProgress(max, prgress, false);
        mBuilder.setAutoCancel(true);//自己维护通知的消失
        //构建一个Intent
        Intent resultIntent = new Intent(getBaseContext(),
                HomeActivity.class);
        resultIntent.putExtra(HomeActivity.Type, HomeActivity.mHomeMeType);
        //封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                getBaseContext(), 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        mBuilder.setContentIntent(resultPendingIntent);
        //获取通知管理器对象
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

     /*   Bitmap btm = BitmapFactory.decodeResource(getResources(),
                R.drawable.m_setting_about_xcimg);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getBaseContext())
                .setContentTitle("头像上传中...");
        mBuilder.setTicker("头像上传中...");//第一次提示消息的时候显示在通知栏上
        mBuilder.setNumber(12);
        mBuilder.setLargeIcon(btm);
        mBuilder.setProgress(max, prgress, false);
        mBuilder.setAutoCancel(true);//自己维护通知的消失
*//*        //构建一个Intent
        Intent resultIntent = new Intent(MainActivity.this,
                MainActivity.class);
        //封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                MainActivity.this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        mBuilder.setContentIntent(resultPendingIntent);*//*
        //获取通知管理器对象
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());*/
    }

}
