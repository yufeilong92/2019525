package com.xuechuan.xcedu.xunfei.basic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.xunfei.util.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: testVoice
 * @Package com.example.andy.testvoice.basic
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 上午 9:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class VideoUitls {
    private static String TAG = "【" + VideoUitls.class + "】==";
    public static final String PRIVATE_SETTING = "com.iflytek.setting";           //缓存数据的名称
    private static volatile VideoUitls _singleton;
    private Context mContext;
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;

    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    int ret = 0; // 函数调用返回值
    private EditText mEditText;

    private VideoUitls(Context context) {
        this.mContext = context;
    }

    public static VideoUitls get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (VideoUitls.class) {
                if (_singleton == null) {
                    _singleton = new VideoUitls(context);
                }
            }
        }
        return _singleton;
    }

    public static VideoUitls get_singleton(Context context) {
        return new VideoUitls(context);
    }

    public void initData(EditText editText) {
        this.mEditText = editText;
        mString = mEditText.getText().toString();
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=5c04f1bc");
        initIat();
    }

    /**
     * 初始化语音识别中的对象
     */
    private void initIat() {
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(mContext, mInitListener);
        mSharedPreferences = mContext.getSharedPreferences(PRIVATE_SETTING,
                Activity.MODE_PRIVATE);

    }

    /**
     * 初始化监听器
     */
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.e(TAG, "onInit: 初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 显示结果,并赋值到EditText文本框中
     *
     * @param results
     * @param editText
     */
    private void printResult(RecognizerResult results, EditText editText) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        String content = resultBuffer.toString();
        Log.d(TAG, content);
        StringBuffer buffer = new StringBuffer();
        content = content.replace("。", "".trim());           //去掉最后面的 。
        if (!StringUtil.isEmpty(mString)) {
            buffer.append(mString);
        }
        buffer.append(content);
        String s1 = buffer.toString();
        editText.setText(s1);
        editText.setSelection(s1.length());
//        editText.setText(content);
//        editText.setSelection(content.length());
    }

    /**
     * 触发点击事件时的方法
     */
    public void clickMethod() {
//        mEditText.setText(null);// 清空显示内容
        mIatResults.clear();
        // 设置参数
        setParam();
        boolean isShowDialog = mSharedPreferences.getBoolean(mContext.getString(R.string.pref_key_iat_show), true);
        if (isShowDialog) {
            // 显示听写对话框
            mIatDialog.setListener(mRecognizerDialogListener);
            mIatDialog.show();
            //获取字体所在的控件，设置为"",隐藏字体，
            TextView txt = (TextView)mIatDialog.getWindow().getDecorView().findViewWithTag("textlink");
            txt.setText("");
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            Log.e(TAG, "clickMethod: " + mContext.getString(R.string.text_begin));
        } else {
            // 不显示听写对话框
//            ret = mIat.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                Log.d(TAG, "听写失败,错误码：" + ret);
            } else {
                Log.e(TAG, "onBeginOfSpeech: " + mContext.getString(R.string.text_begin));
            }
        }
    }

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    private String mString;
    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {


        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());

            printResult(results, mEditText);
            Log.d(TAG, results.getResultString());
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            Log.e(TAG, "onError" + error.getPlainDescription(true));
        }
    };
    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Log.e(TAG, "onBeginOfSpeech: 开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            Log.e(TAG, "onBeginOfSpeech: " + error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            Log.e(TAG, "onBeginOfSpeech: " + "结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results, mEditText);
            Log.d(TAG, results.getResultString());
            if (isLast) {
                // TODO 最后的结果

            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            Log.e(TAG, "onBeginOfSpeech: " + "当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };


}
