package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;

import java.util.Date;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BuyResultActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 支付结果
 * @author: L-BackPacker
 * @date: 2018/5/25 14:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/25
 */
public class BuyResultActivity extends BaseActivity {

    private ImageView mIvBuyImg;
    private TextView mTvBuyStatus;
    private TextView mTvBuyTime;

    private static String PAYSTATUS = "paystatus";
    public static String STATUSSUCCESS = "success";
    public static String STATUSERROR = "error";
    private String mStatus;

    public static Intent newInstance(Context context, String paystatus) {
        Intent intent = new Intent(context, BuyResultActivity.class);
        intent.putExtra(PAYSTATUS, paystatus);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_result);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_buy_result);
        if (getIntent() != null) {
            mStatus = getIntent().getStringExtra(PAYSTATUS);
        }
        initView();
        initData();
    }

    private void initData() {
        if (mStatus.equals(STATUSSUCCESS)) {
            mTvBuyStatus.setText(getStringWithId(R.string.buySuccess));
            mIvBuyImg.setImageResource(R.mipmap.common_feedback_suc);
        } else if (mStatus.equals(STATUSERROR)) {
            mTvBuyStatus.setText(R.string.buyError);
            mIvBuyImg.setImageResource(R.mipmap.common_feedback_fail);
        }
        mTvBuyTime.setText(TimeUtil.dateToString(new Date()));
    }

    private void initView() {
        mIvBuyImg = (ImageView) findViewById(R.id.iv_buy_img);
        mTvBuyStatus = (TextView) findViewById(R.id.tv_buy_status);
        mTvBuyTime = (TextView) findViewById(R.id.tv_buy_time);
    }

}
