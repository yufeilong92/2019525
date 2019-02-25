package com.xuechuan.xcedu.voice;

import android.content.Intent;
import android.os.Bundle;
import com.xuechuan.xcedu.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.voice
 * @Description: 所有离线，在线语音识别
 * @author: L-BackPacker
 * @date: 2018.11.13 上午 10:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ActivityAllRecog extends ActivityAbstractRecog {
   /* *//**
     * 对话框界面的输入参数
     *//*
    private DigitalDialogInput input;
    private ChainRecogListener chainRecogListener;

//     * @param textId        界面上的说明文字
//     * @param enableOffline 展示的activity是否支持离线命令词

    public ActivityAllRecog() {
        super(R.raw.all_recog, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        *//**
         *

         * 有2个listner，一个是用户自己的业务逻辑，如MessageStatusRecogListener。另一个是UI对话框的。
         * 使用这个ChainRecogListener把两个listener和并在一起
         *//*
        chainRecogListener = new ChainRecogListener();
        // DigitalDialogInput 输入 ，MessageStatusRecogListener可替换为用户自己业务逻辑的listener
        chainRecogListener.addListener(new MessageStatusRecogListener(handler));
        myRecognizer.setEventListener(chainRecogListener); // 替换掉原来的listener


    }

    *//**
     * 开始录音，点击“开始”按钮后调用。
     *//*

    @Override
    protected void start() {
        // 此处params可以打印出来，直接写到你的代码里去，最终的json一致即可。
        final Map<String, Object> params = fetchParams();

        // BaiduASRDigitalDialog的输入参数
        input = new DigitalDialogInput(myRecognizer, chainRecogListener, params);
        BaiduASRDigitalDialog.setInput(input); // 传递input信息，在BaiduASRDialog中读取,
        Intent intent = new Intent(this, BaiduASRDigitalDialog.class);

        // 修改对话框样式
        // intent.putExtra(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_ORANGE_DEEPBG);

        running = true;
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        running = false;
        if (requestCode == 2) {
            String message = "对话框的识别结果：";
            if (resultCode == RESULT_OK) {
                ArrayList results = data.getStringArrayListExtra("results");
                if (results != null && results.size() > 0) {
                    message += results.get(0);
                }
            } else {
                message += "没有结果";
            }
            MyLogger.info(message);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!running) {
            myRecognizer.release();
            finish();
        }
    }*/
}
