package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
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
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.WaitAddressOrderContract;
import com.xuechuan.xcedu.mvp.model.WaitAddressOrderModel;
import com.xuechuan.xcedu.mvp.presenter.WaitAddressOrderPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.AddressItemsBean;
import com.xuechuan.xcedu.vo.MyOrderNewVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.WaitAddressVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: TakeDeliveryActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 收货确认界面
 * @author: L-BackPacker
 * @date: 2018/8/20 11:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/20
 */
public class TakeDeliveryActivity extends BaseActivity implements View.OnClickListener, WaitAddressOrderContract.View {

    private TextView mTvTakeAddressName;
    private TextView mTvTakeAddressPhone;
    private TextView mTvTakeAddressDefault;
    private LinearLayout mLlSelectAddress;
    private RecyclerView mRlvTakeGoodsContent;
    private Button mBtnTakeSure;
    private Context mContext;
    public static final String TAG_NAME = "tag_name";
    /**
     * 结果码
     */
    public static final int RESYKTY_RESULT_CODE = 102;
    /**
     * 请求码
     */
    public static final int RESYKTY_REQUEST_CODE = 103;
    private WaitAddressOrderPresenter mPresenter;
    private AlertDialog showDialog;
    private TextView mTvAddressAddres;
    private LinearLayout mLlAddressName;
    private int addressid = -1;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_delivery);
        initView();
    }
*/

    private static String INTENTTAG = "intenttag";
    private AlertDialog showDialog1;

    public static Intent newInstance(Context context, String intenttag) {
        Intent intent = new Intent(context, TakeDeliveryActivity.class);
        intent.putExtra(INTENTTAG, intenttag);
        return intent;
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_take_delivery);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new WaitAddressOrderPresenter();
        mPresenter.initModelView(new WaitAddressOrderModel(), this);
        showDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestWaitAddressOrder(mContext);
    }

    private void initView() {
        mContext = this;
        mTvTakeAddressName = (TextView) findViewById(R.id.tv_take_address_name);
        mTvTakeAddressPhone = (TextView) findViewById(R.id.tv_take_address_phone);
        mTvTakeAddressDefault = (TextView) findViewById(R.id.tv_take_address_default);
        mLlSelectAddress = (LinearLayout) findViewById(R.id.ll_select_address);
        mRlvTakeGoodsContent = (RecyclerView) findViewById(R.id.rlv_take_goods_content);
        mBtnTakeSure = (Button) findViewById(R.id.btn_take_sure);
        mLlSelectAddress.setOnClickListener(this);
        mBtnTakeSure.setOnClickListener(this);
        mTvAddressAddres = (TextView) findViewById(R.id.tv_address_addres);
        mLlAddressName = (LinearLayout) findViewById(R.id.ll_address_name);
        mLlAddressName.setOnClickListener(this);
        mTvAddressAddres.setOnClickListener(this);
        mTvTakeAddressName.setOnClickListener(this);
        mTvTakeAddressPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_sure://确认收货
                    submitSure();
                break;
            case R.id.tv_take_address_name://名字
            case R.id.tv_take_address_phone://电话
            case R.id.tv_address_addres://地址
            case R.id.ll_select_address://地址布局
                Intent intent = AddressMangerActivity.newInstance(mContext, AddressMangerActivity.TAG_SELECT);
                startActivityForResult(intent, RESYKTY_REQUEST_CODE);
                break;
        }
    }

    private void submitSure() {
        if (addressid != -1) {
            showDialog1 = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
            mPresenter.submitConfimAddress(mContext, addressid);
        } else {
            T.showToast(mContext, getString(R.string.add_sure_address));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESYKTY_REQUEST_CODE) {
            if (data != null) {
                AddressItemsBean mDataBean = (AddressItemsBean) data.getSerializableExtra(DataMessageVo.SELECTTAG);
                if (mDataBean != null)
                    bindData(mDataBean);
            }
        }
    }

    private void bindData(AddressItemsBean mDataBean) {
        if (mTvAddressAddres.getVisibility() == View.VISIBLE) {
            mLlAddressName.setVisibility(View.VISIBLE);
            mTvAddressAddres.setVisibility(View.GONE);
            mTvTakeAddressDefault.setVisibility(View.VISIBLE);
        }
        addressid = mDataBean.getId();
        mTvTakeAddressName.setText(mDataBean.getReceivename());
        mTvTakeAddressPhone.setText(mDataBean.getTelphone());
        StringBuffer buffer = new StringBuffer();
        buffer.append(mDataBean.getProvince());
        buffer.append(mDataBean.getCity());
        buffer.append(mDataBean.getArea());
        buffer.append(mDataBean.getAddress());
        mTvTakeAddressDefault.setText(buffer.toString());
    }

    /**
     * 确认收货信息
     *
     * @param success
     */
    @Override
    public void OrderInfomSuccess(String success) {
        dismissDialog(showDialog);
        Gson gson = new Gson();
        WaitAddressVo vo = gson.fromJson(success, WaitAddressVo.class);
        bindViewData(vo);
        bindAdapter(vo);

    }

    private void bindAdapter(WaitAddressVo vo) {
        if (vo.getData().getGifts() == null || vo.getData().getGifts().isEmpty()) {
            this.finish();
        }
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvTakeGoodsContent.setLayoutManager(manager);
        MyOrderDetailAdapter adapter = new MyOrderDetailAdapter(mContext, vo.getData().getGifts());
        mRlvTakeGoodsContent.setAdapter(adapter);
    }

    private void bindViewData(WaitAddressVo vo) {
        if (StringUtil.isEmpty(vo.getData().getReceiver_mobile())) {
            mLlAddressName.setVisibility(View.GONE);
            mTvAddressAddres.setVisibility(View.VISIBLE);
            mTvTakeAddressDefault.setVisibility(View.GONE);
            addressid = -1;
        } else {
            mLlAddressName.setVisibility(View.VISIBLE);
            mTvAddressAddres.setVisibility(View.GONE);
            mTvTakeAddressDefault.setVisibility(View.VISIBLE);
            addressid = vo.getData().getAddress_id();
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(vo.getData().getReceiver_state());
        buffer.append(vo.getData().getReceiver_city());
        buffer.append(vo.getData().getReceiver_district());
        buffer.append(vo.getData().getReceiver_address());
        mTvTakeAddressDefault.setText(buffer.toString());
        mTvTakeAddressName.setText(vo.getData().getReceiver_name());
        mTvTakeAddressPhone.setText(vo.getData().getReceiver_mobile());

    }

    @Override
    public void OrderInfomError(String msg) {
        dismissDialog(showDialog);
        T_ERROR(mContext);
    }

    /**
     * 确认收货
     *
     * @param success
     */
    @Override
    public void ConfimAddressSuccess(String success) {
        dismissDialog(showDialog1);
        Gson gson = new Gson();
        ResultVo resultVo = gson.fromJson(success, ResultVo.class);
        if (resultVo.getStatus().getCode() == 200) {
            if (resultVo.getData().getStatusX() == 1) {
                T.showToast(mContext, resultVo.getData().getMessage());
                finish();
            } else {
                T_ERROR(mContext, resultVo.getData().getMessage());
            }

        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void ConfimAddressError(String msg) {
        dismissDialog(showDialog1);
        T_ERROR(mContext);
    }

    /**
     * 删除项的位置
     */
    private int mDelPostion = -1;

    /**
     * 取消订单
     *
     * @param obj
     * @param position
     */
    private void showData(final MyOrderNewVo.DatasBean obj, final int position, final boolean isDel) {
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, isDel ? getStringWithId(R.string.delect_order1) : getString(R.string.iscancelorder), getStringWithId(R.string.sure)
                , getStringWithId(R.string.cancel), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                if (isDel) {
                    mDelPostion = position;
                }
//                mPresenter.submitDelOrd(mContext, obj.getOrdernum(), isDel ? DataMessageVo.DELETEORDER : DataMessageVo.CANCELORDER);
            }

            @Override
            public void onCancelClickListener() {
            }
        });

    }

}
