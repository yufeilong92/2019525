package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.FileReadUtil;
import com.xuechuan.xcedu.utils.Utils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdataLogSevice extends IntentService {

    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";


    private static final String UPDATAID = "com.xuechuan.xcedu.service.extra.PARAM1";
    private static final String UPDATABAN = "com.xuechuan.xcedu.service.extra.PARAM2";
    private static final String UPDATAVIDEOID = "com.xuechuan.xcedu.service.extra.PARAM3";
    private static final String UPDATAREGITID= "com.xuechuan.xcedu.service.extra.PARAM4";
    private static final String UPDATATYPE= "com.xuechuan.xcedu.service.extra.type";
    private static final String UPDATACODE= "com.xuechuan.xcedu.service.extra.code";

    public UpdataLogSevice() {
        super("MyIntentService");
    }

    /**
     *
     * @param context
     * @param vid  视频id
     * @param ban   版本号
     * @param videoid 视频id
     * @param regitid  视频码率
     * @param type  类型
     * @param code  code码
     */
    public static void startActionFoo(Context context, String vid, String ban,String videoid,String regitid,String type,int code) {
        Intent intent = new Intent(context, UpdataLogSevice.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(UPDATAID, vid);
        intent.putExtra(UPDATABAN, ban);
        intent.putExtra(UPDATAVIDEOID, videoid);
        intent.putExtra(UPDATAREGITID, regitid);
        intent.putExtra(UPDATATYPE, type);
        intent.putExtra(UPDATACODE, code);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String vid = intent.getStringExtra(UPDATAID);
                final String banben = intent.getStringExtra(UPDATABAN);
                final String videoid = intent.getStringExtra(UPDATAVIDEOID);
                final String ragith = intent.getStringExtra(UPDATAREGITID);
                final String mType = intent.getStringExtra(UPDATATYPE);
                final String mCode = intent.getStringExtra(UPDATACODE);
                handleActionFoo(vid, banben,videoid,ragith,mType,mCode);
            }
        }
    }

    private void handleActionFoo( String vid, String banben, String videoid, String ragith,String mType, String mCode) {
        FileReadUtil util = FileReadUtil.getInstance(getBaseContext());
        String s = util.loadFromSdFile();
        if (TextUtils.isEmpty(s)) {
            stopSelf();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("视频的id为" + vid);
        buffer.append("，" + banben);
        buffer.append("，视频本地id为" + videoid);
        buffer.append("，码率为" + ragith);
        buffer.append("，错误信息=" + mType);
        buffer.append("，错误码=" + mCode+",");
        buffer.append("，手机型号=" + Utils.getSystemModel());
        buffer.append("，手机厂商=" + Utils.getDeviceBrand());
        buffer.append("，系统版本号=" + Utils.getSystemVersion());
        buffer.append(s);
        HomeService service = new HomeService(getApplicationContext());
        service.updataLog(buffer.toString(), new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                stopSelf();
            }

            @Override
            public void onError(String response) {
                stopSelf();
            }
        });
    }


}
