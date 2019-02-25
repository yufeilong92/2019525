package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.me.MyOrderDetailAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.contract.ConfirmReceiveContract;
import com.xuechuan.xcedu.mvp.contract.OrderDetailContract;
import com.xuechuan.xcedu.mvp.model.ConfirmReceiveModel;
import com.xuechuan.xcedu.mvp.model.OrderDetailModel;
import com.xuechuan.xcedu.mvp.presenter.ConfirmReceivePresenter;
import com.xuechuan.xcedu.mvp.presenter.OrderDetailPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.OrderUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.DetailsBeanMainVo;
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.vo.OrderInfomdVo;
import com.xuechuan.xcedu.vo.ResultVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: OrderInfomActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 订单详情
 * @author: L-BackPacker
 * @date: 2018/8/23 16:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/23
 */
public class OrderInfomActivity extends BaseActivity implements OrderDetailContract.View, ConfirmReceiveContract.View, View.OnClickListener {

    private RecyclerView mOrderInfomContent;
    private TextView mTvOrderInfomName;
    private TextView mTvOrderInfomPhone;
    private TextView mTvOrderInfomAddress;
    private TextView mTvOrderInfomNumber;
    private TextView mTvOrderInfomSellerMsg;
    private TextView mBtnInfomService;
    private OrderDetailPresenter mPresenter;
    private Context mContext;
    private AlertDialog mShowDialog;
    private static String mInfomNumber = "infomNumber";
    private static String mOrdertyep = "mInfomType";
    private String mNumberInfom;
    private TextView mTvInfomDefalitValue;
    private TextView mTvInfomService;
    private TextView mTvNPayCount;
    private Button mBtnNetSubmitFrom;
    private LinearLayout mLlInfomPayLayout;
    private int mOrderType;
    private LinearLayout mLlOrderinfomAdress;
    private TextView mTvInfomWarning;
    private ConfirmReceivePresenter mComfirmPresenter;
    private AlertDialog showDialog;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_infom);
        initView();
    }*/

    /**
     * @param mContext
     * @param ordernum 订单编号
     * @return
     */
    public static Intent newInstance(Context mContext, String ordernum) {
        Intent intent = new Intent(mContext, OrderInfomActivity.class);
        intent.putExtra(mInfomNumber, ordernum);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mNumberInfom = getIntent().getStringExtra(mInfomNumber);
            mOrderType = getIntent().getIntExtra(mOrdertyep, 0);
        }
        setContentView(R.layout.activity_order_infom);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new OrderDetailPresenter();
        mComfirmPresenter = new ConfirmReceivePresenter();
        mComfirmPresenter.initModelView(new ConfirmReceiveModel(), this);
        mPresenter.initModelView(new OrderDetailModel(), this);
        mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestOrderDetail(mContext, mNumberInfom);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestOrderDetail(mContext, mNumberInfom);
    }

    private void initView() {
        mContext = this;
        mOrderInfomContent = (RecyclerView) findViewById(R.id.order_infom_content);
        mTvOrderInfomName = (TextView) findViewById(R.id.tv_order_infom_name);
        mTvOrderInfomPhone = (TextView) findViewById(R.id.tv_order_infom_phone);
        mTvOrderInfomAddress = (TextView) findViewById(R.id.tv_order_infom_address);
        mTvOrderInfomNumber = (TextView) findViewById(R.id.tv_order_infom_number);
        mTvOrderInfomSellerMsg = (TextView) findViewById(R.id.tv_order_infom_seller_msg);
        mBtnInfomService = (TextView) findViewById(R.id.tv_infom_service);
        mBtnInfomService.setOnClickListener(this);
        mTvInfomDefalitValue = (TextView) findViewById(R.id.tv_infom_defalit_value);
        mTvInfomDefalitValue.setOnClickListener(this);
        mTvInfomService = (TextView) findViewById(R.id.tv_infom_service);
        mTvInfomService.setOnClickListener(this);
        mTvNPayCount = (TextView) findViewById(R.id.tv_n_pay_count);
        mTvNPayCount.setOnClickListener(this);
        mBtnNetSubmitFrom = (Button) findViewById(R.id.btn_net_submit_from);
        mBtnNetSubmitFrom.setOnClickListener(this);
        mLlInfomPayLayout = (LinearLayout) findViewById(R.id.ll_infom_pay_layout);
        mLlInfomPayLayout.setOnClickListener(this);
        mLlOrderinfomAdress = (LinearLayout) findViewById(R.id.ll_orderinfom_adress);
        mLlOrderinfomAdress.setOnClickListener(this);
        mTvInfomWarning = (TextView) findViewById(R.id.tv_infom_warning);
        mTvInfomWarning.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_infom_service:
                Intent intent1 = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + "400-963-8119"));
                startActivity(intent1);
                break;
            case R.id.btn_net_submit_from:

                break;
        }
    }


    @Override
    public void OrderInfomSuccess(String success) {
        dismissDialog(mShowDialog);
        Gson gson = new Gson();
        OrderInfomdVo vo = gson.fromJson(success, OrderInfomdVo.class);
        List<DetailsBeanMainVo> details = vo.getData().getDetails();
        List<GiftVo> gifvos = OrderUtil.getGifvos(details);
        gifvos.addAll(vo.getData().getGifts());
        binDViewData(vo.getData());
        bindAdatpterData(details == null || details.isEmpty(), gifvos, vo.getData().getOrdernum());
    }

    private void binDViewData(OrderInfomdVo.DataBean data) {
        mTvInfomDefalitValue.setText(String.valueOf(data.getTotalprice()));
        if (StringUtil.isEmpty(data.getReceiver_mobile())) {
            mLlOrderinfomAdress.setVisibility(View.GONE);
            mTvInfomWarning.setVisibility(View.GONE);
        } else {
            mTvInfomWarning.setVisibility(View.VISIBLE);
            mLlOrderinfomAdress.setVisibility(View.VISIBLE);
            mTvOrderInfomName.setText(data.getReceiver_name());
            mTvOrderInfomPhone.setText(data.getReceiver_mobile());
            StringBuffer buffer = new StringBuffer();
            buffer.append(data.getReceiver_state());
            buffer.append(data.getReceiver_city());
            buffer.append(data.getReceiver_district());
            buffer.append(data.getReceiver_address());
            mTvOrderInfomAddress.setText(buffer.toString());
        }
        mTvOrderInfomNumber.setText(data.getOrdernum());
        mTvOrderInfomSellerMsg.setText(StringUtil.isEmpty(data.getBuyer_message()) ? "无" :
                data.getBuyer_message());
        mTvNPayCount.setText(String.valueOf(data.getTotalprice()));
    }

    private void bindAdatpterData(boolean isMainEmpty, List<GiftVo> gifvos, final String ordernum) {
        MyOrderDetailAdapter adapter = new MyOrderDetailAdapter(mContext, gifvos);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        adapter.setIsShow(true);
        adapter.setMainIsEmpty(isMainEmpty);
        adapter.setProgressIsShow(true);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mOrderInfomContent.setLayoutManager(manager);
        mOrderInfomContent.setAdapter(adapter);
        adapter.setApplyRefundClickListener(new MyOrderDetailAdapter.onApplyRefundItemClickListener() {
            @Override
            public void onApplyRefundClickListener(GiftVo obj, int position) {//申请退款
                boolean isSend = false;
                if (obj.getState() == 11) {
                    isSend = false;
                } else {
                    isSend = true;
                }
                Intent intent = RefundActivity.newInstance(mContext, isSend ? RefundActivity.TAKESTATUSYI : RefundActivity.TAKESTATUS, obj);
                startActivity(intent);

            }
        });
        adapter.setLookLogisticsClickListener(new MyOrderDetailAdapter.onLookLogisticsItemClickListener() {
            @Override
            public void onLookLogisticsClickListener(GiftVo obj, int position) {//查看物流
                Intent intent = LogisticsActivity.newInstance(mContext, LogisticsActivity.COMPNAMETAGTWO,
                        obj.getOid(), getTextStr(mTvOrderInfomName), getTextStr(mTvOrderInfomPhone)
                        , getTextStr(mTvOrderInfomAddress));
                startActivity(intent);
            }
        });
        adapter.setClickListener(new MyOrderDetailAdapter.onItemClickListener() {
            @Override
            public void onClickListener(GiftVo obj, int position) {
                if (obj.getState() == 11) {
                    Intent intent = LogisticsActivity.newInstance(mContext, LogisticsActivity.COMPNAMETAGONE,
                            obj.getOid(), getTextStr(mTvOrderInfomName), getTextStr(mTvOrderInfomPhone)
                            , getTextStr(mTvOrderInfomAddress));
                    startActivity(intent);
                } else if (obj.getState() == 1) {
                    Intent intent = TakeDeliveryActivity.newInstance(mContext, String.valueOf(obj.getOid()));
                    startActivity(intent);
                }
            }
        });

        adapter.setRefundProgressClickListener(new MyOrderDetailAdapter.onRefundProgressClickListener() {
            @Override
            public void onRefundProgressClickListener(GiftVo obj, int position) {//退款进度

            }
        });
        adapter.setSureGoodsClickListener(new MyOrderDetailAdapter.onSureGoodsItemClickListener() {
            @Override
            public void onSureGoodsClickListener(GiftVo obj, int position) {//确认收货
                showDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
                mComfirmPresenter.submitConfirmReceive(mContext, ordernum, obj.getOid());
            }
        });

    }

    @Override
    public void OrderInfomError(String msg) {
        dismissDialog(mShowDialog);
        T_ERROR(mContext);
    }

    /**
     * 确认收货
     *
     * @param success
     */
    @Override
    public void ConfirmSucces(String success) {
        dismissDialog(showDialog);
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                DelectSuceessActivity.newInstance(mContext, DelectSuceessActivity.TAKEGOODSSUCCESS);
//                T_ERROR(mContext, vo.getData().getMessage());
            } else {
                T_ERROR(mContext, vo.getData().getMessage());
            }
        } else {
            T_ERROR(mContext);
        }

    }

    @Override
    public void ConfirmError(String msg) {
        dismissDialog(showDialog);
        T_ERROR(mContext);
    }

}
