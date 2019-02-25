package com.xuechuan.xcedu.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeUtil;

import java.util.Date;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI wxapi;
    private ImageView mIvPayImg;
    private TextView mTvPayReasult;
    private TextView mTvPayTime;
    private LinearLayout mLlPayContent;

    public void onHomeClick(View view){
        HomeActivity.startInstance(this,HomeActivity.BOOK);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        initView();
        wxapi = WXAPIFactory.createWXAPI(this, DataMessageVo.WEIXINVID);
        wxapi.handleIntent(getIntent(), this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
   /*         Toast.makeText(WXPayEntryActivity.this,baseResp.errStr
                    +baseResp.openId+baseResp.errCode,Toast.LENGTH_SHORT).show();*/
            if (baseResp.errCode == 0) {//成功
                mIvPayImg.setImageResource(R.mipmap.common_feedback_suc);
                mTvPayReasult.setText(R.string.paysuccess);
                String date = TimeUtil.dateToString(new Date());
                mTvPayTime.setText(date);

            } else if (baseResp.errCode == -1) {
                mIvPayImg.setImageResource(R.mipmap.common_feedback_fail);
                mTvPayReasult.setText(R.string.pay_error);
                String date = TimeUtil.dateToString(new Date());
                mTvPayTime.setText(date);
            }else if (baseResp.errCode==-2){
                mIvPayImg.setImageResource(R.mipmap.common_feedback_fail);
                mTvPayReasult.setText(R.string.pay_error);
                String date = TimeUtil.dateToString(new Date());
                mTvPayTime.setText(date);

            }
        }
    }

    private void initView() {
        mIvPayImg = (ImageView) findViewById(R.id.iv_pay_img);
        mTvPayReasult = (TextView) findViewById(R.id.tv_pay_reasult);
        mTvPayTime = (TextView) findViewById(R.id.tv_pay_time);
        mLlPayContent = (LinearLayout) findViewById(R.id.ll_pay_content);
    }
}
