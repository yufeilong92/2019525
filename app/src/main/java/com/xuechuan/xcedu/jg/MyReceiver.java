package com.xuechuan.xcedu.jg;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;

import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.ui.home.YouZanActivity;
import com.xuechuan.xcedu.ui.me.TakeDeliveryActivity;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.service
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/29 16:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        receivingNotification(context,bundle);
//        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            processCustomMessage(context, bundle);
//            receivingNotification(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
/*            String type = intent.getStringExtra("type");
            Log.e("推送通知","接受参数"+type);*/
            Bundle extras = intent.getExtras();
            String string = extras.getString(JPushInterface.EXTRA_EXTRA);
            if (!StringUtil.isEmpty(string)) {
                doJson(context, string);
                return;
            }
            Utils.launchAppByPackageName(context, "com.xuechuan.xcedu");
//            Intent i = new Intent(context, TestActivity.class);
//            i.putExtras(bundle);
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private void doJson(Context context, String string) {
        if (StringUtil.isEmpty(string)) {
            return;
        }
        try {
            JSONObject json = new JSONObject(string);
            Iterator<String> it = json.keys();
            while (it.hasNext()) {
                String myKey = it.next().toString();
                if (myKey.equalsIgnoreCase("type")) {//接受自定消息
                    if (json.optString(myKey).equalsIgnoreCase(DataMessageVo.WAITADDRESS)) {
                        DoIntentMange.doStart(context, new WaitAddress());
                        return;
                    }
                }
                if (myKey.equalsIgnoreCase("gourl")) {
                    DoIntentMange.doGoStart(context, new GourlIntent(), json.optString(myKey));
                    return;
                }
                if (myKey.equalsIgnoreCase("yzgourl")) {
                    DoIntentMange.doGoStart(context, new YzGourlIntent(), json.optString(myKey));
                    return;
                }

            /*    sb.append("\nkey:" + key + ", value: [" +
                        myKey + " - " + json.optString(myKey) + "]");*/
            }
        } catch (JSONException e) {
            Log.e(TAG, "Get message extra JSON error!");
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        if (bundle != null && bundle.keySet() != null && !bundle.keySet().isEmpty())
            for (String key : bundle.keySet()) {
                if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                    sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
                } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                    sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
                } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                    if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                        Log.i(TAG, "This message has no Extra data");
                        continue;
                    }

                    try {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                        Iterator<String> it = json.keys();

                        while (it.hasNext()) {
                            String myKey = it.next().toString();
                            sb.append("\nkey:" + key + ", value: [" +
                                    myKey + " - " + json.optString(myKey) + "]");
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Get message extra JSON error!");
                    }

                } else {
                    sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
                }
            }
        return sb.toString();
    }

    private void receivingNotification(Context context, Bundle bundle) {
        // 指定定制的 Notification Layout
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
                context, R.layout.view_notification, R.id.iv_jg_icon,
                R.id.tv_jg_title,
                R.id.tv_jg_content);


        // 指定最顶层状态栏小图标
        builder.statusBarDrawable = R.mipmap.appicon_one_one;

        // 指定下拉状态栏时显示的通知图标
        builder.layoutIconDrawable = R.mipmap.appicon_one_one;
        JPushInterface.setPushNotificationBuilder(1, builder);


    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent msgIntent = new Intent(TestActivity.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(TestActivity.KEY_MESSAGE, message);
        if (!StringUtil.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (null != extraJson && extraJson.length() > 0) {
                    msgIntent.putExtra(TestActivity.KEY_EXTRAS, extras);
                }
            } catch (JSONException e) {
            }
        }
        context.sendBroadcast(msgIntent);
    }

    private interface doIntent {
        public void doIntentStart(Context context);

        public void doIntentStart(Context context, String gourl);
    }

    /**
     * 地址确认
     */
    public class WaitAddress implements doIntent {
        @Override
        public void doIntentStart(Context context) {
            Intent intent = TakeDeliveryActivity.newInstance(context, "");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }

        @Override
        public void doIntentStart(Context context, String gourl) {

        }
    }

    public static class DoIntentMange {
        public static void doStart(Context context, doIntent doIntent) {
            doIntent.doIntentStart(context);
        }

        public static void doGoStart(Context context, doIntent doIntent, String gourl) {
            doIntent.doIntentStart(context, gourl);
        }
    }

    /**
     * 地址确认
     */
    public class GourlIntent implements doIntent {
        @Override
        public void doIntentStart(Context context) {
        }

        @Override
        public void doIntentStart(Context context, String gourl) {
            Intent intent = AgreementActivity.newInstance(context, gourl, AgreementActivity.NOSHAREMARK,
                    "", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    /**
     * 地址确认
     */
    public class YzGourlIntent implements doIntent {
        @Override
        public void doIntentStart(Context context) {

        }

        @Override
        public void doIntentStart(Context context, String gourl) {
            Intent intent = YouZanActivity.newInstance(context, gourl);
            intent.putExtra(YouZanActivity.CSTR_EXTRA_TITLE_STR, "商城");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }
}
