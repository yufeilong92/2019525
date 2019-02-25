package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.callback.IFooterCallBack;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.contract.RefundProgressContract;
import com.xuechuan.xcedu.mvp.model.RefundProgressModel;
import com.xuechuan.xcedu.mvp.presenter.RefundProgressPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: RefundProgressActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 退款进度
 * @author: L-BackPacker
 * @date: 2018/8/22 17:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/22
 */
public class RefundProgressActivity extends BaseActivity implements View.OnClickListener, RefundProgressContract.View {

    private ImageView mIvRefundProgress;
    private TextView mTvRefundInfomTitle;
    private TextView mTvRefundInfom;
    private TextView mTvRefundProIdentifier;
    private TextView mTvRefundProTime;
    private TextView mTvRefundProCause;
    private TextView mTvRefundProMoney;
    private TextView mTvRefundProPhone;
    private Button mBtnRefundProCancel;
    private Context mContext;
    private RefundProgressPresenter mPresenter;

    /*
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_refund_progress);
            initView();
        }
    */
    private static String REFUNDID = "refundid";
    private String mRefundId;
    private AlertDialog showDialog;

    public static Intent newInstance(Context context, String runfid) {
        Intent intent = new Intent(context, RefundProgressActivity.class);
        intent.putExtra(REFUNDID, runfid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_refund_progress);
        if (getIntent() != null) {
            mRefundId = getIntent().getStringExtra(REFUNDID);
        }
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new RefundProgressPresenter();
        mPresenter.initModelView(new RefundProgressModel(), this);
        showDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestRefundProgres(mContext, mRefundId);
    }

    private void initView() {
        mContext = this;
        mIvRefundProgress = (ImageView) findViewById(R.id.iv_refund_progress);
        mTvRefundInfomTitle = (TextView) findViewById(R.id.tv_refund_infom_title);
        mTvRefundInfom = (TextView) findViewById(R.id.tv_refund_infom);
        mTvRefundProIdentifier = (TextView) findViewById(R.id.tv_refund_pro_identifier);
        mTvRefundProTime = (TextView) findViewById(R.id.tv_refund_pro_time);
        mTvRefundProCause = (TextView) findViewById(R.id.tv_refund_pro_cause);
        mTvRefundProMoney = (TextView) findViewById(R.id.tv_refund_pro_money);
        mTvRefundProPhone = (TextView) findViewById(R.id.tv_refund_pro_phone);
        mBtnRefundProCancel = (Button) findViewById(R.id.btn_refund_pro_cancel);
        mBtnRefundProCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refund_pro_cancel:
                cancelRefund();
                break;
        }
    }

    private void cancelRefund() {
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, getString(R.string.cancel_apply), getString(R.string.cancel_close), getStringWithId(R.string.sure), getStringWithId(R.string.cancel), false);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {

            }

            @Override
            public void onCancelClickListener() {

            }
        });
    }

    @Override
    public void RefundSuccess(String success) {
        dismissDialog(showDialog);
    }

    @Override
    public void RefundError(String msg) {
        dismissDialog(showDialog);
        T_ERROR(mContext);
    }

}
