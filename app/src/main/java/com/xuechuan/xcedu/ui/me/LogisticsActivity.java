package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.TimeAxisAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.contract.OrderTracesContract;
import com.xuechuan.xcedu.mvp.model.OrderTracesModel;
import com.xuechuan.xcedu.mvp.presenter.OrderTracesPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.vo.OrderTracesVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: LogisticsActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 物流信息界面
 * @author: L-BackPacker
 * @date: 2018/8/22 15:05
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/22
 */
public class LogisticsActivity extends BaseActivity implements OrderTracesContract.View {

    private TextView mTvLogisticsMake;
    private TextView mTvLogisticsNumber;
    private TextView mTvLogisticsName;
    private TextView mTvLogisticsPhone;
    private TextView mTvLogisticsAddress;
    private RecyclerView mRlvLogisticsTime;
    private Context mContext;
    private TextView mTvInfomWarning;
    private LinearLayout mLlNameTitleWuliu;
    private LinearLayout mLiAddressWarning;
    private ImageView mIvPhoneService;
    private View mVBarkground;


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        initView();
    }*/

    private static String COMPNAMETAG = "tag";
    /**
     * 只查看地址
     */
    public static String COMPNAMETAGONE = "one";
    /**
     * 带有地址信息
     */
    public static String COMPNAMETAGTWO = "two";
    private static String NAME = "name";
    private static String PHONE = "phone";
    private static String ADDRES = "addres";
    private static String TCODE = "code";
    private String mTag;
    private int mCode;
    private AlertDialog showDialog;
    private String mName;
    private String mPhone;
    private String mAddress;


    public static Intent newInstance(Context context, String tag, int code, String name, String phone, String addres) {
        Intent intent = new Intent(context, LogisticsActivity.class);
        intent.putExtra(COMPNAMETAG, tag);
        intent.putExtra(TCODE, code);
        intent.putExtra(NAME, name);
        intent.putExtra(PHONE, phone);
        intent.putExtra(ADDRES, addres);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mTag = getIntent().getStringExtra(COMPNAMETAG);
            mCode = getIntent().getIntExtra(TCODE, 0);
            mName = getIntent().getStringExtra(NAME);
            mPhone = getIntent().getStringExtra(PHONE);
        }
        setContentView(R.layout.activity_logistics);
        initView();
        initData();
    }

    private void bindAdapteData(List<OrderTracesVo.DataBeanX.ResponseBean.OrderexpresslistBean.DataBean> data) {
        TimeAxisAdapter adapter = new TimeAxisAdapter(mContext, data);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvLogisticsTime.setLayoutManager(manager);
        mRlvLogisticsTime.setAdapter(adapter);

    }

    private void initData() {
        OrderTracesPresenter mPresenter = new OrderTracesPresenter();
        mPresenter.initModelView(new OrderTracesModel(), this);
        showDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestOrderTraces(mContext, mCode);
    }

    private void initView() {
        mContext = this;
        mTvLogisticsMake = (TextView) findViewById(R.id.tv_logistics_make);
        mTvLogisticsNumber = (TextView) findViewById(R.id.tv_logistics_number);
        mTvLogisticsName = (TextView) findViewById(R.id.tv_logistics_name);
        mTvLogisticsPhone = (TextView) findViewById(R.id.tv_logistics_phone);
        mTvLogisticsAddress = (TextView) findViewById(R.id.tv_logistics_address);
        mRlvLogisticsTime = (RecyclerView) findViewById(R.id.rlv_logistics_time);
        mTvInfomWarning = (TextView) findViewById(R.id.tv_infom_warning);
        mLlNameTitleWuliu = (LinearLayout) findViewById(R.id.ll_name_title_wuliu);
        mLiAddressWarning = (LinearLayout) findViewById(R.id.li_address_warning);
        mIvPhoneService = (ImageView) findViewById(R.id.iv_phone_service);
        mIvPhoneService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + "400-963-8119"));
                startActivity(intent1);
            }
        });
        mVBarkground = (View) findViewById(R.id.v_barkground);
    }

    @Override
    public void TracesSuccess(String success) {
        dismissDialog(showDialog);
        Gson gson = new Gson();
        OrderTracesVo vo = gson.fromJson(success, OrderTracesVo.class);
        if (vo.getStatus().getCode() == 200) {
            bindViewData(vo);
            bindAdapteData(vo.getData().getResponse().getOrderexpresslist().get(0).getData());
        } else {
            T_ERROR(mContext);
        }
    }

    private void bindViewData(OrderTracesVo vo) {
        if (mTag.equals(COMPNAMETAGTWO)) {//正常
            mLiAddressWarning.setVisibility(View.GONE);
            mRlvLogisticsTime.setVisibility(View.VISIBLE);
            mLlNameTitleWuliu.setVisibility(View.VISIBLE);
            mVBarkground.setVisibility(View.GONE);
        } else {//非正常.
            mLiAddressWarning.setVisibility(View.VISIBLE);
            mLlNameTitleWuliu.setVisibility(View.GONE);
            mRlvLogisticsTime.setVisibility(View.GONE);
            mVBarkground.setVisibility(View.VISIBLE);
        }
        OrderTracesVo.DataBeanX data = vo.getData();
        mTvLogisticsMake.setText(data.getExpress_company());
        mTvLogisticsNumber.setText(data.getExpress_code());
        StringBuffer buffer = new StringBuffer();
        buffer.append(data.getReceiver_state());
        buffer.append(data.getReceiver_city());
        buffer.append(data.getReceiver_district());
        buffer.append(data.getReceiver_address());
        mTvLogisticsAddress.setText(buffer.toString());
        mTvLogisticsName.setText(data.getReceiver_name());
        mTvLogisticsPhone.setText(data.getReceiver_mobile());
    }

    @Override
    public void TracesError(String msg) {
        dismissDialog(showDialog);
        T_ERROR(mContext);
    }
}
