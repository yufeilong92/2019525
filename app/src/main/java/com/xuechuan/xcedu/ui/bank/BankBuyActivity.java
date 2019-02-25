package com.xuechuan.xcedu.ui.bank;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.MyTextContract;
import com.xuechuan.xcedu.mvp.model.MyTextModel;
import com.xuechuan.xcedu.mvp.model.PayModelImpl;
import com.xuechuan.xcedu.mvp.presenter.MyTextPresenter;
import com.xuechuan.xcedu.mvp.presenter.PayPresenter;
import com.xuechuan.xcedu.mvp.view.PayUtilView;
import com.xuechuan.xcedu.mvp.view.PayView;
import com.xuechuan.xcedu.ui.BuyResultActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.MyBigDecimal;
import com.xuechuan.xcedu.utils.PayUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.BankValueVo;
import com.xuechuan.xcedu.vo.BuyFromResultVo;
import com.xuechuan.xcedu.vo.BuyFromVo;
import com.xuechuan.xcedu.vo.PayResult;
import com.xuechuan.xcedu.vo.WechatsignBeanVo;
import com.xuechuan.xcedu.vo.BuyZfbResultVo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BankBuyActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 题库购买页
 * @author: L-BackPacker
 * @date: 2018/5/22 16:26
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class BankBuyActivity extends BaseActivity implements MyTextContract.View, View.OnClickListener, PayUtilView {

    private Context mContext;
    /**
     * 课目id
     */
    private static String COUNTID = "countid";
    private double value = 0;
    private IWXAPI wxapi;
    private LinearLayout mLlBBankPay;
    private CheckBox mChbBPaySkill;
    private TextView mTvBSkillPayValue;
    private CheckBox mChbBPayCollo;
    private TextView mTvBColloPayValue;
    private CheckBox mChbBPayCase;
    private TextView mTvBCasePayValue;
    private TextView mTvBPayCount;
    private CheckBox mChbBPayZfb;
    private LinearLayout mLlBPayZfb;
    private CheckBox mChbBPayWeixin;
    private LinearLayout mLlBWeixin;
    private Button mBtnBSubmitFrom;
    private double skillPrice = -1;
    private double colloPrice = -1;
    private double casePrice = -1;
    private int colloid;
    private int skillId;
    private int caseId;
    private AlertDialog mDialog;
    private int payType;
    private IWXAPI api;
    /**
     * 支付包结果
     */
    private static final int SDK_PAY_FLAG = 1;
    private MyTextPresenter mPresenter;
    private String mCountid;
    //题干
    public static String TEXT = "text";
    //个人
    public static String PERSION = "persion";
    //类型
    public static String TYPE = "type";
    private String mType;

    public static Intent newInstance(Context context, String countid, String type) {
        Intent intent = new Intent(context, BankBuyActivity.class);
        intent.putExtra(COUNTID, countid);
        intent.putExtra(TYPE, type);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_buy);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bank_buy);
        if (getIntent() != null) {
            mCountid = getIntent().getStringExtra(COUNTID);
            mType = getIntent().getStringExtra(TYPE);
        }
        initView();
        api = WXAPIFactory.createWXAPI(mContext, DataMessageVo.APP_ID);
        api.registerApp(DataMessageVo.APP_ID);
//        mPresenter = new PayPresenter(new PayModelImpl(), this);
        mPresenter = new MyTextPresenter();
        mPresenter.initModelView(new MyTextModel(), this);
        mPresenter.reuqestBookId(mContext);

    }



    private void initData(List<BankValueVo.DatasBean> bean) {
        bindData(bean);
        if (!StringUtil.isEmpty(mCountid)) {
            if (mCountid.equals("1")) {
                value += skillPrice;
                mChbBPaySkill.setChecked(true);
            } else if (mCountid.equals("2")) {
                value += colloPrice;
                mChbBPayCollo.setChecked(true);
            } else if (mCountid.equals("3")) {
                value += casePrice;
                mChbBPayCase.setChecked(true);
            }
            showCount();
        }

        mChbBPayWeixin.setChecked(true);
        mChbBPaySkill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    value = MyBigDecimal.addI(value,skillPrice);
                } else {
                    value = MyBigDecimal.sub(value,skillPrice);
                }
                showCount();
            }
        });
        mChbBPayCollo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    value = MyBigDecimal.addI(value,colloPrice);
//                    value += colloPrice;
                } else {
                    value = MyBigDecimal.sub(value,colloPrice);
//                    value -= colloPrice;
                }
                showCount();
            }
        });
        mChbBPayCase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    value = MyBigDecimal.addI(value,casePrice);
//                    value += casePrice;
                } else {
                    value = MyBigDecimal.sub(value,casePrice);
//                    value -= casePrice;
                }
                showCount();
            }
        });
        mChbBPayZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mChbBPayZfb.isPressed()) {
                    return;
                }
                if (isChecked) {
                    setPayChb(true, false);
                } else {
                    setPayChb(false, false);
                }
            }
        });
        mChbBPayWeixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mChbBPayWeixin.isPressed()) {
                    return;
                }
                if (isChecked) {
                    setPayChb(false, true);
                } else {
                    setPayChb(false, false);
                }
            }
        });

    }

    private void bindData(List<BankValueVo.DatasBean> bean) {
        for (BankValueVo.DatasBean vo : bean) {
            switch (vo.getCourseid()) {
                case 1://技术
                    mTvBSkillPayValue.setText(String.valueOf(vo.getPrice()));
                    skillPrice =  vo.getPrice();
                    skillId = vo.getId();
                    break;
                case 2://综合能力
                    mTvBColloPayValue.setText(String.valueOf(vo.getPrice()));
                    colloPrice = vo.getPrice();
                    colloid = vo.getId();
                    break;
                case 3://案例
                    mTvBCasePayValue.setText(String.valueOf(vo.getPrice()));
                    casePrice = vo.getPrice();
                    caseId = vo.getId();
                    break;
                default:
            }


        }


    }

    private void showCount() {
        mTvBPayCount.setText(value + "");
    }

    private void setPayChb(boolean zfb, boolean weixin) {
        mChbBPayZfb.setChecked(zfb);
        mChbBPayWeixin.setChecked(weixin);
    }

    private void initView() {
        mContext = this;
        mLlBBankPay = (LinearLayout) findViewById(R.id.ll_b_bank_pay);
        mLlBBankPay.setOnClickListener(this);
        mChbBPaySkill = (CheckBox) findViewById(R.id.chb_b_pay_skill);
        mChbBPaySkill.setOnClickListener(this);
        mTvBSkillPayValue = (TextView) findViewById(R.id.tv_b_skill_pay_value);
        mTvBSkillPayValue.setOnClickListener(this);
        mChbBPayCollo = (CheckBox) findViewById(R.id.chb_b_pay_collo);
        mChbBPayCollo.setOnClickListener(this);
        mTvBColloPayValue = (TextView) findViewById(R.id.tv_b_collo_pay_value);
        mTvBColloPayValue.setOnClickListener(this);
        mChbBPayCase = (CheckBox) findViewById(R.id.chb_b_pay_case);
        mChbBPayCase.setOnClickListener(this);
        mTvBCasePayValue = (TextView) findViewById(R.id.tv_b_case_pay_value);
        mTvBCasePayValue.setOnClickListener(this);
        mTvBPayCount = (TextView) findViewById(R.id.tv_b_pay_count);
        mTvBPayCount.setOnClickListener(this);
        mChbBPayZfb = (CheckBox) findViewById(R.id.chb_b_pay_zfb);
        mChbBPayZfb.setOnClickListener(this);
        mLlBPayZfb = (LinearLayout) findViewById(R.id.ll_b_pay_zfb);
        mLlBPayZfb.setOnClickListener(this);
        mChbBPayWeixin = (CheckBox) findViewById(R.id.chb_b_pay_weixin);
        mChbBPayWeixin.setOnClickListener(this);
        mLlBWeixin = (LinearLayout) findViewById(R.id.ll_b_weixin);
        mLlBWeixin.setOnClickListener(this);
        mBtnBSubmitFrom = (Button) findViewById(R.id.btn_b_submit_from);
        mBtnBSubmitFrom.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_b_submit_from://提交表单
                submit();
                break;
            default:
        }

    }

    private void submit() {
        double value = 0;
        payType = -1;

        List<Integer> list = new ArrayList<>();
        if (mChbBPaySkill.isChecked()) {
            value = MyBigDecimal.addI(value,skillPrice);
//            value += skillPrice;
            list.add(skillId);
        }
        if (mChbBPayCollo.isChecked()) {
            value = MyBigDecimal.addI(value,colloPrice);
//            value += colloPrice;
            list.add(colloid);
        }
        if (mChbBPayCase.isChecked()) {
            value = MyBigDecimal.addI(value,casePrice);
//            value += casePrice;
            list.add(caseId);
        }
        if (value <= 0) {
            T.showToast(mContext, "请选择要购买题库");
            return;
        }
        if (mChbBPayZfb.isChecked()) {
            payType = 1;
        }
        if (mChbBPayWeixin.isChecked()) {
            if (!api.isWXAppInstalled()) {
                T.showToast(mContext, getString(R.string.weixin_installed));
                return;
            }
            payType = 2;
        }
        if (payType == -1) {
            T.showToast(mContext, getString(R.string.pay_type));
            return;
        }

        PayUtil payUtil = PayUtil.getInstance(mContext, BankBuyActivity.this);
        payUtil.init(this);
        if (payType == 1) {
            payUtil.Submitfrom(PayUtil.ZFB, "", list, "");
        } else if (payType == 2) {
            payUtil.Submitfrom(PayUtil.WEIXIN, "", list, "");
        }
//        mPresenter.submitPayFrom(mContext, String.valueOf(value), list, "app", null);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));

    }
/*

    @Override
    public void SumbitFromSuccess(String con) {
        Gson gson = new Gson();
        BuyFromVo vo = gson.fromJson(con, BuyFromVo.class);
        if (vo.getStatus().getCode() == 200) {
            String orderid = vo.getData().getOrderid();
            if (payType == 1) {//支付包
                mPresenter.submitPay(mContext, orderid, DataMessageVo.PAYTYPE_ZFB);
            } else if (payType == 2) {//微信
                mPresenter.submitPay(mContext, orderid, DataMessageVo.PAYTYPE_WEIXIN);
            }
        } else {
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void SumbitFromError(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }

    }

    @Override
    public void SumbitPaySuccess(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        Gson gson = new Gson();
        if (payType == 2) {//微信
            BuyFromResultVo vo = gson.fromJson(con, BuyFromResultVo.class);
            if (vo.getStatus().getCode() == 200) {
                WechatsignBeanVo wechatsign = vo.getData().getWechatsign();
                requestWeiXinPay(wechatsign);
            } else {
                L.e(vo.getStatus().getMessage());
            }

        } else if (payType == 1) {//支付包
            BuyZfbResultVo vo = gson.fromJson(con, BuyZfbResultVo.class);
            if (vo.getStatus().getCode() == 200) {
                BuyZfbResultVo.DataBean data = vo.getData();
                requestZFBPay(data);
            } else {
                L.e(vo.getStatus().getMessage());
            }
        }


    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    */

    /**
     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
     *//*

                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = BuyResultActivity.newInstance(mContext, BuyResultActivity.STATUSSUCCESS);
                        intent.putExtra(BuyResultActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.buyStauts));
                        startActivity(intent);
                        Toast.makeText(BankBuyActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Intent intent = BuyResultActivity.newInstance(mContext, BuyResultActivity.STATUSERROR);
                        intent.putExtra(BuyResultActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.buyStauts));
                        startActivity(intent);
                        Toast.makeText(BankBuyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void requestZFBPay(BuyZfbResultVo.DataBean zfb) {
        final String orderInfo = zfb.getOrderstring();   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(BankBuyActivity.this);
                Map<String, String> map = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = map;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    private void requestWeiXinPay(final WechatsignBeanVo wechatsign) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayReq request = new PayReq();
                request.appId = DataMessageVo.APP_ID;
                request.partnerId = wechatsign.getPartnerid();
                request.prepayId = wechatsign.getPrepayid();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = wechatsign.getNoncestr();
                request.timeStamp = wechatsign.getTimespan();
                request.sign = wechatsign.getSign();
                api.sendReq(request);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    @Override
    public void SumbitPayError(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }

    }
*/
    @Override
    public void BookIDSuccess(String con) {
        clear();
        Gson gson = new Gson();
        BankValueVo vo = gson.fromJson(con, BankValueVo.class);
        if (vo.getStatus().getCode() == 200) {
            mLlBBankPay.setVisibility(View.VISIBLE);
            List<BankValueVo.DatasBean> list = vo.getDatas();
            initData(list);
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());

        }
    }

    private void clear() {
        value = 0;
    }

    @Override
    public void BookIDError(String con) {
        L.e(con);
    }

    @Override
    public void PaySuccess(String type) {
        HomeActivity.startInstance(mContext, HomeActivity.BOOK);
        finish();
    }

    @Override
    public void PayError(String type) {

    }

    @Override
    public void Dialog() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }
}
