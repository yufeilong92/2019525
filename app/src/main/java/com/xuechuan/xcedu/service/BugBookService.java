package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
/**
 * @Title:  BugBookService
 * @Package com.xuechuan.xcedu.service
 * @Description: 用户是否购买
 * @author: L-BackPacker
 * @date:   2018/4/27 21:58
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/27
 */
public class BugBookService extends IntentService {
    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";

    private static final String EXTRA_PARAM1 = "";
    private static final String EXTRA_PARAM2 = "com.xuechuan.xcedu.service.extra.PARAM2";

    public BugBookService() {
        super("BugBookService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, BugBookService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            }
        }
    }

    private void handleActionFoo(String param1, String param2) {

    }


}
