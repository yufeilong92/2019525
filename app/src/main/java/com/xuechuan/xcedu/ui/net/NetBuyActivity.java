package com.xuechuan.xcedu.ui.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.me.GoodsAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.DefaultAddressContract;
import com.xuechuan.xcedu.mvp.model.DefaultAddressModel;
import com.xuechuan.xcedu.mvp.presenter.DefaultAddressPresenter;
import com.xuechuan.xcedu.mvp.view.PayUtilView;
import com.xuechuan.xcedu.ui.me.AddressMangerActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.PayUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.AddressItemsBean;
import com.xuechuan.xcedu.vo.CoursesBeanVo;
import com.xuechuan.xcedu.vo.DefaultVo;
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.weight.ContentEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBuyActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 网课购买
 * @author: L-BackPacker
 * @date: 2018/5/25 10:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/25
 */
public class NetBuyActivity extends BaseActivity implements View.OnClickListener, PayUtilView, DefaultAddressContract.View {

    private ImageView mIvNetPayImg;
    private TextView mTvNetBookMame;
    private TextView mTvNetBookPrice;
    private TextView mTvNPayCount;
    private CheckBox mChbNetPayZfb;
    private LinearLayout mLlNetPayZfb;
    private CheckBox mChbNetPayWeixin;
    private LinearLayout mLlNetWeixin;
    private Button mBtnNetSubmitFrom;
    private LinearLayout mLlBBankPay;
    private static String PAYINFOM = "payinfom";
    private static String KID = "kid";
    private CoursesBeanVo mDataVo;
    private Context mContext;
    /**
     * 请求码
     */
    public static final int BUYU_REQUEST_CODE = 105;
    private int statusType = -1;
    private int STATUSTYPEWEIXIN = 2;
    private int STATUSTYPEZFB = 1;
    private AlertDialog dialog;
    private IWXAPI api;
    /**
     * 支付包结果
     */
    private static final int SDK_PAY_FLAG = 1;
    private PayUtil payUtil;
    private static String PRICE = "price";
    private static String ID = "id";
    private static String NAME = "name";
    private static String URLIMG = "urlimg";
    private static String GIFTS = "gifts";
    private double mPrice;
    private String mName;
    private int mId;
    private String mUrlImg;
    private List<GiftVo> mListGifts;
    private RecyclerView mRlvContentGitfs;
    private TextView mTvBuyAddAddress;
    private TextView mTvBuyName;
    private TextView mTvBuyPhone;
    private TextView mTvBuyAddress;
    private LinearLayout mLlBuyAddress;
    private ContentEditText mEtBuyContent;
    public static final String TAG_NAME = "selectaddress";
    private DefaultAddressPresenter mPresenter;
    private AddressItemsBean mDataBean;
    private LinearLayout mLlBuyAddressRoot;
    private ScrollView mScoBuyRoot;
    private LinearLayout mLlBuyPay;
    private LinearLayout mLlBuyRoot;

    /**
     * @param context
     * @param payinfom
     * @param kid
     * @return
     */
    public static Intent newInstance(Context context, CoursesBeanVo payinfom, String kid) {
        Intent intent = new Intent(context, NetBuyActivity.class);
        intent.putExtra(PAYINFOM, (Serializable) payinfom);
        intent.putExtra(KID, kid);
        return intent;
    }

    /**
     * @param context
     * @param price   价格
     * @param id      购买的id
     * @param name    购买的名字
     * @param urlimg  图片的url
     * @param mGiftvs 赠品信息
     * @return
     */
    public static Intent newInstance(Context context, double price, int id, String name, String urlimg, List<GiftVo> mGiftvs) {
        Intent intent = new Intent(context, NetBuyActivity.class);
        intent.putExtra(PRICE, price);
        intent.putExtra(ID, id);
        intent.putExtra(NAME, name);
        intent.putExtra(URLIMG, urlimg);
        intent.putExtra(GIFTS, (Serializable) mGiftvs);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_buy);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_buy);
        if (getIntent() != null) {
//            mDataVo = (CoursesBeanVo) getIntent().getSerializableExtra(PAYINFOM);
            mPrice = getIntent().getDoubleExtra(PRICE, 0);
            mName = getIntent().getStringExtra(NAME);
            mId = getIntent().getIntExtra(ID, 0);
            mUrlImg = getIntent().getStringExtra(URLIMG);
            mListGifts = (List<GiftVo>) getIntent().getSerializableExtra(GIFTS);
        }
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new DefaultAddressPresenter();
        mPresenter.initModelView(new DefaultAddressModel(), this);
        mPresenter.requestDefaultAddress(mContext);
        api = WXAPIFactory.createWXAPI(mContext, DataMessageVo.APP_ID);
        api.registerApp(DataMessageVo.APP_ID);
//        mPresenter = new PayPresenter(new PayModelImpl(), this);
        payUtil = PayUtil.getInstance(mContext, NetBuyActivity.this);
        mTvNetBookMame.setText(mName);
        mTvNetBookPrice.setText(String.valueOf(mPrice));
        mTvNPayCount.setText(String.valueOf(mPrice));
        if (StringUtil.isEmpty(mUrlImg)) {
            mIvNetPayImg.setImageResource(R.mipmap.defaultimg);
        } else
            MyAppliction.getInstance().displayImages(mIvNetPayImg, mUrlImg, false);
        mLlNetPayZfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChb(true, false);
            }
        });
        mLlNetWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChb(false, true);
            }
        });
        mChbNetPayWeixin.setChecked(true);
        statusType = 2;
        mChbNetPayWeixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mChbNetPayWeixin.isPressed()) return;
                if (isChecked) {
                    showChb(false, true);
                } else {
                    showChb(false, false);
                }
            }
        });
        mChbNetPayZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mChbNetPayZfb.isPressed()) return;
                if (isChecked) {
                    showChb(true, false);
                } else {
                    showChb(false, false);
                }
            }
        });
        if (mListGifts == null || mListGifts.isEmpty()) {
            mListGifts = new ArrayList<>();
            mLlBuyAddressRoot.setVisibility(View.GONE);
        } else {
            mLlBuyAddressRoot.setVisibility(View.VISIBLE);

        }
        bingAdapterData();
    }

    private void bingAdapterData() {

        GoodsAdapter adapter = new GoodsAdapter(mContext, mListGifts);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvContentGitfs.setLayoutManager(manager);
        mRlvContentGitfs.setAdapter(adapter);
        adapter.setClickListener(new GoodsAdapter.onItemClickListener() {
            @Override
            public void onClickListener(GiftVo obj, int position) {

            }
        });

    }

    private void initView() {
        mContext = this;
        mIvNetPayImg = (ImageView) findViewById(R.id.iv_net_pay_img);
        mTvNetBookMame = (TextView) findViewById(R.id.tv_net_book_mame);
        mTvNetBookPrice = (TextView) findViewById(R.id.tv_net_book_price);
        mTvNPayCount = (TextView) findViewById(R.id.tv_n_pay_count);
        mChbNetPayZfb = (CheckBox) findViewById(R.id.chb_net_pay_zfb);
        mLlNetPayZfb = (LinearLayout) findViewById(R.id.ll_net_pay_zfb);
        mChbNetPayWeixin = (CheckBox) findViewById(R.id.chb_net_pay_weixin);
        mLlNetWeixin = (LinearLayout) findViewById(R.id.ll_net_weixin);
        mBtnNetSubmitFrom = (Button) findViewById(R.id.btn_net_submit_from);
        mLlBBankPay = (LinearLayout) findViewById(R.id.ll_b_bank_pay);
        mBtnNetSubmitFrom.setOnClickListener(this);
        mRlvContentGitfs = (RecyclerView) findViewById(R.id.rlv_content_gitfs);
        mRlvContentGitfs.setOnClickListener(this);
        mTvBuyAddAddress = (TextView) findViewById(R.id.tv_buy_add_address);
        mTvBuyAddAddress.setOnClickListener(this);
        mTvBuyName = (TextView) findViewById(R.id.tv_buy_name);
        mTvBuyName.setOnClickListener(this);
        mTvBuyPhone = (TextView) findViewById(R.id.tv_buy_phone);
        mTvBuyPhone.setOnClickListener(this);
        mTvBuyAddress = (TextView) findViewById(R.id.tv_buy_address);
        mTvBuyAddress.setOnClickListener(this);
        mLlBuyAddress = (LinearLayout) findViewById(R.id.ll_buy_address);
        mLlBuyAddress.setOnClickListener(this);
        mEtBuyContent = (ContentEditText) findViewById(R.id.et_buy_content);
        mEtBuyContent.setOnClickListener(this);
        mLlBuyAddressRoot = (LinearLayout) findViewById(R.id.ll_buy_address_root);
        mLlBuyAddressRoot.setOnClickListener(this);
        mScoBuyRoot = (ScrollView) findViewById(R.id.sco_buy_root);
        mScoBuyRoot.setOnClickListener(this);
        mLlBuyPay = (LinearLayout) findViewById(R.id.ll_buy_pay);
        mLlBuyPay.setOnClickListener(this);
        mLlBuyRoot = (LinearLayout) findViewById(R.id.ll_buy_root);
        mLlBuyRoot.setOnClickListener(this);
    }

    private void showChb(boolean zfb, boolean wein) {
        mChbNetPayZfb.setChecked(zfb);
        mChbNetPayWeixin.setChecked(wein);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_submit_from:
                submit();
                break;
            case R.id.ll_buy_address://添加地址
            case R.id.tv_buy_add_address://添加地址
                Intent intent = AddressMangerActivity.newInstance(mContext, AddressMangerActivity.TAG_SELECT);
                startActivityForResult(intent, BUYU_REQUEST_CODE);
                break;
        }
    }

    private void submit() {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(mId);
        if (mDataBean == null || StringUtil.isEmpty(mDataBean.getTelphone()) || StringUtil.isEmpty(mDataBean.getReceivename())) {
            if (mListGifts != null && !mListGifts.isEmpty()) {
                T.showToast(mContext, "请选择收货地址");
                return;
            }
        }

        if (mChbNetPayWeixin.isChecked()) {
            if (!api.isWXAppInstalled()) {
                T.showToast(mContext, R.string.weixin_installed);
                return;
            }
            statusType = STATUSTYPEWEIXIN;
        } else if (mChbNetPayZfb.isChecked()) {
            statusType = STATUSTYPEZFB;
        }
        if (statusType == -1) {
            T.showToast(mContext, getString(R.string.pay_type));
            return;
        }

        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_from));
        String s = getTextStr(mEtBuyContent);
        payUtil.showDiaolog(dialog);
        if (statusType == 1) {
            payUtil.SubmitVideofrom(PayUtil.ZFB, "", integers, s, mDataBean == null ? 0 : mDataBean.getId());
        } else if (statusType == 2) {
            payUtil.SubmitVideofrom(PayUtil.WEIXIN, "", integers, s, mDataBean == null ? 0 : mDataBean.getId());
        }

    }


    @Override
    public void PaySuccess(String type) {
        HomeActivity.startInstance(mContext, HomeActivity.VIDEO);
        finish();
    }

    @Override
    public void PayError(String type) {

    }

    @Override
    public void Dialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BUYU_REQUEST_CODE) {
            if (data != null) {
                mDataBean = (AddressItemsBean) data.getSerializableExtra(DataMessageVo.SELECTTAG);
                if (mDataBean != null)
                    bindData(mDataBean);
            }
        }
    }

    private void bindData(AddressItemsBean mDataBean) {
        if (StringUtil.isEmpty(mDataBean.getAddress())) {
            mTvBuyAddAddress.setVisibility(View.VISIBLE);
            mLlBuyAddress.setVisibility(View.GONE);
            return;
        } else {
            mLlBuyAddress.setVisibility(View.VISIBLE);
            mTvBuyAddAddress.setVisibility(View.GONE);
        }
        mTvBuyName.setText(mDataBean.getReceivename());
        mTvBuyPhone.setText(mDataBean.getTelphone());
        StringBuffer buffer = new StringBuffer();
        buffer.append(mDataBean.getProvince());
        buffer.append(mDataBean.getCity());
        buffer.append(mDataBean.getArea());
        buffer.append(mDataBean.getAddress());
        mTvBuyAddress.setText(buffer.toString());
    }

    @Override
    public void addressSuccess(String success) {
        Gson gson = new Gson();
        DefaultVo vo = gson.fromJson(success, DefaultVo.class);
        mDataBean = vo.getData();
        bindData(mDataBean);
    }

    @Override
    public void addressError(String msg) {
        T_ERROR(mContext);
    }

}
