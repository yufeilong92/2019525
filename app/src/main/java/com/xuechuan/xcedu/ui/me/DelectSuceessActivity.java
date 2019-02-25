package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.utils.TimeUtil;

import java.util.Date;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: DelectSuceessActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 删除成功
 * @author: L-BackPacker
 * @date: 2018/6/7 17:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/6/7
 */

public class DelectSuceessActivity extends BaseActivity {

    private ImageView mIvDelImg;
    private TextView mTvDelReasult;
    private TextView mTvDelTime;
    private LinearLayout mLlDelContent;
    public static String DELECTSUCCESS = "del";
    public static String CANCELSUCCESS = "can";
    /**
     * 收货成功
     */
    public static String TAKEGOODSSUCCESS = "takegoods";

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delect_suceess);
        initView();
    }
*/

    private static String TYPE = "type";
    private String mType;

    public static void newInstance(Context context, String type) {
        Intent intent = new Intent(context, DelectSuceessActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_delect_suceess);
        if (getIntent() != null) {
            mType = getIntent().getStringExtra(TYPE);
        }
        initView();
        initData();
    }

    private void initData() {
        if (mType.equals(DELECTSUCCESS)) {
            mTvDelReasult.setText(getStringWithId(R.string.delect_Success));
        }else if (mType.equals(TAKEGOODSSUCCESS)){
            mTvDelReasult.setText("收货成功");
        }
        else {
            mTvDelReasult.setText(getStringWithId(R.string.cancel_sucess));
        }

        String date = TimeUtil.dateToString(new Date());
        mTvDelTime.setText(date);
    }

    private void initView() {
        mIvDelImg = (ImageView) findViewById(R.id.iv_del_img);
        mTvDelReasult = (TextView) findViewById(R.id.tv_del_reasult);
        mTvDelTime = (TextView) findViewById(R.id.tv_del_time);
        mLlDelContent = (LinearLayout) findViewById(R.id.ll_del_content);
    }

}
