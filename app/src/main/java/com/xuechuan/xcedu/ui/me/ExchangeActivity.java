package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.AddVauleModelImpl;
import com.xuechuan.xcedu.mvp.presenter.AddVaulePresenter;
import com.xuechuan.xcedu.mvp.view.AddVauleView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ExchangeinfoBean;
import com.xuechuan.xcedu.vo.ResultVo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ExchangeActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 二维码扫描结果
 * @author: L-BackPacker
 * @date: 2018/7/24 10:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/7/24
 */
public class ExchangeActivity extends BaseActivity implements View.OnClickListener, AddVauleView {

    private ImageView mIvQrResult;
    private TextView mTvQrResult;
    private TextView mTvQrNumber;
    private LinearLayout mLlQrCheck;
    private TextView mTvAddvluesCode;
    private TextView mTvQrAddvluesContent;
    private Button mBtnExchangeSubmit;
    private LinearLayout mLlQrAddValues;
    private TextView mTvNumberTitle;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exchange);
//        initView();
//    }

    /**
     * 查询次数
     */
    private static String QUERYNUM = "querynum";
    /**
     * 是否兑换
     */
    private static String HAVEEXCHANGE = "haveexchange";
    /**
     * 兑换信息
     */
    private static String EXCHANGERINFO = "exchangerinfo";
    /**
     * 是否正版
     */
    private static String ISGENUINE = "isgenuine";
    /**
     * 判断类型 增值、扫码
     */
    private static String TAGMAKE = "make";
    /**
     * 扫描结果
     */
    private static String MCODE = "mCode";

    private ExchangeinfoBean mInfoBean;
    private int mQuerynum;
    private boolean mHaveExchange;
    private String mCode;
    private AddVaulePresenter mPresenter;
    private Context mContext;
    private AlertDialog dialog;
    /**
     * 是否正版
     */
    private boolean mIsgenuine;
    private String mMake;


    /**
     * 兑换信息
     *
     * @param context
     * @param querynum     查询次数
     * @param haveexchange 是否有兑换信息
     * @param mCode        code 码
     * @param bean         兑换数据
     * @return
     */
    public static Intent newInstance(Context context, boolean isgenuine, int querynum,
                                     boolean haveexchange, String mCode, ExchangeinfoBean bean) {
        Intent intent = new Intent(context, ExchangeActivity.class);
        intent.putExtra(QUERYNUM, querynum);
        intent.putExtra(ISGENUINE, isgenuine);
        intent.putExtra(HAVEEXCHANGE, haveexchange);
        intent.putExtra(MCODE, mCode);
        intent.putExtra(EXCHANGERINFO, (Serializable) bean);
        return intent;
    }

    /**
     * 兑换信息
     *
     * @param context
     * @param querynum     查询次数
     * @param haveexchange 是否有兑换信息
     * @param mCode        code 码
     * @param bean         兑换数据
     * @return
     */
    public static Intent newInstance(Context context, boolean isgenuine, int querynum,
                                     boolean haveexchange, String mCode, ExchangeinfoBean bean, String tag) {
        Intent intent = new Intent(context, ExchangeActivity.class);
        intent.putExtra(QUERYNUM, querynum);
        intent.putExtra(ISGENUINE, isgenuine);
        intent.putExtra(HAVEEXCHANGE, haveexchange);
        intent.putExtra(MCODE, mCode);
        intent.putExtra(EXCHANGERINFO, (Serializable) bean);
        intent.putExtra(TAGMAKE, tag);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exchange);
        if (getIntent() != null) {
            mQuerynum = getIntent().getIntExtra(QUERYNUM, 0);
            mHaveExchange = getIntent().getBooleanExtra(HAVEEXCHANGE, false);
            mCode = getIntent().getStringExtra(MCODE);
            mIsgenuine = getIntent().getBooleanExtra(ISGENUINE, false);
            mInfoBean = (ExchangeinfoBean) getIntent().getSerializableExtra(EXCHANGERINFO);
            mMake = getIntent().getStringExtra(TAGMAKE);
        }
        initView();
        initData();
        binDViewData();
    }

    private void initData() {
        mPresenter = new AddVaulePresenter(new AddVauleModelImpl(), this);
    }

    private void binDViewData() {
        if (mMake.equalsIgnoreCase(DataMessageVo.ADDVALUEHTTP)) {//增值服务
            mLlQrCheck.setVisibility(View.INVISIBLE);
            mIvQrResult.setImageResource(R.mipmap.zduihuan);
            mTvQrResult.setVisibility(View.GONE);
            mTvNumberTitle.setText(getStringWithId(R.string.exchange_code));
            mBtnExchangeSubmit.setBackgroundResource(R.mipmap.m_added_bt_exchange);
            mTvQrNumber.setText(String.valueOf(mQuerynum));
            mTvAddvluesCode.setText(mCode);
            mLlQrAddValues.setVisibility(View.VISIBLE);

            mTvQrAddvluesContent.setText(mInfoBean.getProductname());
            if (mInfoBean.isExchangestate()) {//是否已兑换
                mBtnExchangeSubmit.setText("已兑换");
                mBtnExchangeSubmit.setClickable(false);
                mBtnExchangeSubmit.setBackgroundResource(R.drawable.btn_login_bg);
            } else {
                mBtnExchangeSubmit.setClickable(true);
            }


        } else if (mMake.equalsIgnoreCase(DataMessageVo.QRTAG)) {//扫码兑换
            mTvNumberTitle.setText(getStringWithId(R.string.book_serial_numbe));
            mLlQrCheck.setVisibility(View.VISIBLE);
            mTvQrResult.setVisibility(View.VISIBLE);
            mBtnExchangeSubmit.setBackgroundResource(R.mipmap.ic_m_identify_);

            if (mIsgenuine) {
                mIvQrResult.setImageResource(R.mipmap.ic_m_identify);
                mLlQrCheck.setVisibility(View.VISIBLE);
                mTvQrResult.setText(R.string.genuine);
            } else {
                mIvQrResult.setImageResource(R.mipmap.m_identify_img_f);
                mLlQrAddValues.setVisibility(View.GONE);
                mLlQrCheck.setVisibility(View.GONE);
                mTvQrResult.setText(R.string.piracy);
                return;
            }
            mTvQrNumber.setText(String.valueOf(mQuerynum));
            mTvAddvluesCode.setText(mCode);
            if (mHaveExchange) {//是否有兑换信息
                mLlQrAddValues.setVisibility(View.VISIBLE);
                mLlQrCheck.setVisibility(View.VISIBLE);
                mTvQrAddvluesContent.setText(mInfoBean.getProductname());
                if (mInfoBean.isExchangestate()) {//是否已兑换
                    mBtnExchangeSubmit.setText("已兑换");
                    mBtnExchangeSubmit.setClickable(false);
                    mBtnExchangeSubmit.setBackgroundResource(R.drawable.btn_login_bg);
                } else {
                    mBtnExchangeSubmit.setClickable(true);
                }
            } else {
                mLlQrAddValues.setVisibility(View.GONE);
            }
        }


    }

    private void initView() {
        mContext = this;
        mIvQrResult = (ImageView) findViewById(R.id.iv_qr_result);
        mTvQrResult = (TextView) findViewById(R.id.tv_qr_result);
        mTvQrNumber = (TextView) findViewById(R.id.tv_qr_number);
        mLlQrCheck = (LinearLayout) findViewById(R.id.ll_qr_check);
        mTvAddvluesCode = (TextView) findViewById(R.id.tv_addvlues_code);
        mTvQrAddvluesContent = (TextView) findViewById(R.id.tv_qr_addvlues_content);
        mBtnExchangeSubmit = (Button) findViewById(R.id.btn_exchange_submit);
        mLlQrAddValues = (LinearLayout) findViewById(R.id.ll_qr_add_values);
        mBtnExchangeSubmit.setOnClickListener(this);
        mTvNumberTitle = (TextView) findViewById(R.id.tv_number_title);
        mTvNumberTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exchange_submit:
                dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
                mPresenter.requestAddValueWithCode(mContext, mCode);
                break;
        }
    }

    @Override
    public void AddVauleSuccess(String com) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(com, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                mBtnExchangeSubmit.setText("已兑换");
                mBtnExchangeSubmit.setClickable(false);
                mBtnExchangeSubmit.setBackgroundResource(R.drawable.btn_login_bg);
            }
            T.showToast(mContext, vo.getData().getMessage());

        } else {
            T.showToast(mContext, getString(R.string.code_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void AddVauleError(String com) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }


}
