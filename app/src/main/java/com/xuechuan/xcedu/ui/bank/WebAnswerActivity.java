package com.xuechuan.xcedu.ui.bank;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.DbReadUtil;
import com.xuechuan.xcedu.utils.XzipUtil;
import com.xuechuan.xcedu.voice.ActivityAbstractRecog;

import java.util.ArrayList;
import java.util.Map;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: WebAnswerActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 测试界面
 * @author: L-BackPacker
 * @date: 2018.11.15 上午 8:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.15
 */
public class WebAnswerActivity /*extends ActivityAbstractRecog implements View.OnClickListener*/ {
/*

*/
/**
     * 对话框界面的输入参数
     *//*


    private DigitalDialogInput input;
    private ChainRecogListener chainRecogListener;
    private Button mBtnRead;
    private Button mBtnOutZip;
    private TextView mTvTranslation;
    private TextView mTvAlpha;
    private TextView mTvScaley;
    private TextView mTvRotation;
    private TextView mTvSeekbar;
    private SeekBar mSeekbar;
    private Button mBtnVideo;
    private TextView mTvContent;
    private LinearLayout mRoot;
    private long duction = 500;
    private Context mContext;




    public WebAnswerActivity() {
        super(R.raw.all_recog, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

*/
/**
         * 有2个listner，一个是用户自己的业务逻辑，如MessageStatusRecogListener。另一个是UI对话框的。
         * 使用这个ChainRecogListener把两个listener和并在一起
         *//*


        chainRecogListener = new ChainRecogListener();
        // DigitalDialogInput 输入 ，MessageStatusRecogListener可替换为用户自己业务逻辑的listener
        chainRecogListener.addListener(new MessageStatusRecogListener(handler));
        myRecognizer.setEventListener(chainRecogListener); // 替换掉原来的listener
        setContentView(R.layout.activity_web_answer);


        initDataView();
    }



*/
/**
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
//            MyLogger.info(message);
            mTvContent.setText(message);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!running) {
            myRecognizer.release();
            finish();
        }
    }

    private void initDataView() {
        mBtnRead = (Button) findViewById(R.id.btn_read);
        mBtnOutZip = (Button) findViewById(R.id.btn_OutZip);
        mTvTranslation = (TextView) findViewById(R.id.tv_translation);
        mTvAlpha = (TextView) findViewById(R.id.tv_alpha);
        mTvScaley = (TextView) findViewById(R.id.tv_scaley);
        mTvRotation = (TextView) findViewById(R.id.tv_rotation);
        mTvSeekbar = (TextView) findViewById(R.id.tv_seekbar);
        mSeekbar = (SeekBar) findViewById(R.id.seekbar);
        mBtnVideo = (Button) findViewById(R.id.btn_video);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mRoot = (LinearLayout) findViewById(R.id.root);
        mContext = this;
        mBtnRead.setOnClickListener(this);
        mBtnOutZip.setOnClickListener(this);
        mBtnVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_read:
                readDb();
                break;
            case R.id.btn_OutZip:
                readJieZip();
                break;
            case R.id.btn_video:
                start();
                break;
            case R.id.tv_translation:

                break;
        }
    }

    private void rotation() {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mTvRotation, "rotation", 0f, 360f);
        rotation.setDuration(duction);
        rotation.setRepeatCount(2);
        rotation.setInterpolator(new DecelerateInterpolator());
        rotation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTvRotation.requestLayout();
            }
        });
        rotation.start();

    }

    private void scaley() {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mTvRotation, "scaleY", 1f, 3f, 1f);
        scaleY.setDuration(duction);
        scaleY.setRepeatCount(2);
        scaleY.setInterpolator(new DecelerateInterpolator());
        scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRoot.requestLayout();
            }
        });
        scaleY.start();

    }

    private void alpha() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mTvAlpha, "alpha", 1.0f, 0f);
        alpha.setDuration(duction);
        alpha.setInterpolator(new DecelerateInterpolator());
        alpha.setRepeatCount(2);
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRoot.requestLayout();
            }
        });
        alpha.start();

    }

    private void translation() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(mTvTranslation, "translationX", 0, 300);
        translationX.setDuration(duction);
        translationX.setRepeatCount(2);
        translationX.setInterpolator(new DecelerateInterpolator());
        translationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRoot.requestLayout();
            }
        });
        translationX.start();
    }

    private void readJieZip() {

        String name = Environment.getExternalStorageDirectory().getAbsolutePath() + "/down/demo.zip";
        String DB_NAME = "xuechuan.db";
        String dbPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xuechuan/databasecopy/" + DB_NAME;
        try {
            XzipUtil.UnZipFolder(name, dbPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readDb() {
        DbReadUtil instance = DbReadUtil.get_Instance();
        SQLiteDatabase path = instance.GetDataBasePath(mContext);
        Log.e("=============", "readDb: " + path);
    }
*/

}

