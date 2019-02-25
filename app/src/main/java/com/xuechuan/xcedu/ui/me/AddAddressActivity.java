package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.contract.CreataOrUpAddressContract;
import com.xuechuan.xcedu.mvp.model.CreataOrUpAddressModel;
import com.xuechuan.xcedu.mvp.presenter.CreaOrUpAddressPresenter;
import com.xuechuan.xcedu.utils.AddressPickTask;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.AddressItemsBean;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.weight.ContentEditText;

import java.io.Serializable;
import java.util.ArrayList;

import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AddAddressActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 添加地址页
 * @author: L-BackPacker
 * @date: 2018/8/15 17:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/15
 */

public class AddAddressActivity extends BaseActivity implements View.OnClickListener, CreataOrUpAddressContract.View {

    private TextView mTvAddressAdd;
    private EditText mEtAddressName;
    private EditText mEtAddressPhone;
    private TextView mTvAddressAddress;
    private ContentEditText mEtInfomContent;
    private CheckBox mChbSelectSet;
    private Context mContext;
    private CreaOrUpAddressPresenter mPresenter;
    private AlertDialog mAlertDialog;
    private String mProvince;
    private LinearLayout mLlChbLayou;
    private EditText mEtAddressPostalcode;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
    }
*/

    /**
     * 地址信息
     */
    private static String ADDRESS = "ADDRESS";
    private static String TAGMARK = "TAGMARK";
    private String mCity;
    private String mArea;
    private AddressItemsBean mData;
    private int mMark;
    public static final int NEWADD = 0;
    public static final int CREEDADD = 1;


    public static Intent newInstance(Context context, AddressItemsBean address, int tag) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra(ADDRESS, (Serializable) address);
        intent.putExtra(TAGMARK, tag);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_address);
        if (getIntent() != null) {
            mData = (AddressItemsBean) getIntent().getSerializableExtra(ADDRESS);
            mMark = getIntent().getIntExtra(TAGMARK, 0);
        }
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new CreaOrUpAddressPresenter();
        mPresenter.initModelView(new CreataOrUpAddressModel(), this);
        if (mMark != 0) {
            mEtAddressName.setText(mData.getReceivename());
            mEtAddressPhone.setText(mData.getTelphone());
            mEtInfomContent.setText(mData.getAddress());
            StringBuffer buffer = new StringBuffer();
            buffer.append(mData.getProvince());
            buffer.append("-");
            buffer.append(mData.getCity());
            buffer.append("-");
            buffer.append(mData.getArea());
            mTvAddressAddress.setText(buffer.toString());
            mLlChbLayou.setVisibility(View.GONE);
            mEtAddressPostalcode.setText(StringUtil.isEmpty(mData.getPost()) ? "" : mData.getPost());
        }
    }

    private void initView() {
        mContext = this;
        mTvAddressAdd = (TextView) findViewById(R.id.tv_address_add);
        mTvAddressAdd.setOnClickListener(this);
        mEtAddressName = (EditText) findViewById(R.id.et_address_name);
        mEtAddressPhone = (EditText) findViewById(R.id.et_address_phone);
        mTvAddressAddress = (TextView) findViewById(R.id.tv_address_address);
        mTvAddressAddress.setOnClickListener(this);
        mEtInfomContent = (ContentEditText) findViewById(R.id.et_infom_content);
        mChbSelectSet = (CheckBox) findViewById(R.id.chb_select_set);
        mLlChbLayou = (LinearLayout) findViewById(R.id.ll_chb_layou);
        mLlChbLayou.setOnClickListener(this);
        mEtAddressPostalcode = (EditText) findViewById(R.id.et_address_postalcode);
        mEtAddressPostalcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address_address://省市县
                if (!Utils.handleOnDoubleClick()) {
                    showPronver();
                }
                break;
            case R.id.tv_address_add://保存地址
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String name = getTextStr(mEtAddressName);
        if (TextUtils.isEmpty(name)) {
            T.showToast(mContext, getString(R.string.please_input_name));
            return;
        }
        String phone = getTextStr(mEtAddressPhone);
        if (TextUtils.isEmpty(phone)) {
            T.showToast(mContext, getStringWithId(R.string.please_input_phone));
            return;
        }
        if (!Utils.isPhoneNum(phone)) {
            T.showToast(mContext, getString(R.string.please_right_phone));
            return;
        }
        String addres = getTextStr(mTvAddressAddress);
        if (StringUtil.isEmpty(addres)) {
            T.showToast(mContext, getString(R.string.please_select_p_c_a));
            return;
        }
        String content = getTextStr(mEtInfomContent);
        if (TextUtils.isEmpty(content)) {
            T.showToast(mContext, getStringWithId(R.string.edit_detail_address));
            return;
        }
        if (mMark != 0) {
            ArrayList<String> pronvers = Utils.getPronvers(addres);
            if (pronvers.size() == 3) {
                mProvince = pronvers.get(0);
                mCity = pronvers.get(1);
                mArea = pronvers.get(2);
            }
        }
        if (StringUtil.isEmpty(mProvince) || StringUtil.isEmpty(mCity) || StringUtil.isEmpty(mArea)) {
            T.showToast(mContext, getString(R.string.please_select_p_c_a));
            return;
        }
        String postalcode = getTextStr(mEtAddressPostalcode);
        boolean checked = mChbSelectSet.isChecked();
        mAlertDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        mPresenter.submitCreateUpAddress(mContext, mMark == 0 ? 0 : mData.getId(),
                mProvince, mCity, mArea, content, postalcode, name, checked, phone);
    }

    public void showPronver() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideCounty(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {

            @Override
            public void onAddressInitFailed() {
                T.showToast(mContext, "数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                StringBuffer buffer = new StringBuffer();
                mProvince = province.getAreaName();
                buffer.append(mProvince);
                buffer.append("-");
                mCity = city.getAreaName();
                buffer.append(mCity);
                buffer.append("-");
                mArea = county.getAreaName();
                buffer.append(mArea);
                mTvAddressAddress.setText(buffer.toString());
            }
        });
        String textStr = getTextStr(mTvAddressAddress);
        if (!StringUtil.isEmpty(textStr)) {
            ArrayList<String> pronvers = Utils.getPronvers(textStr);
            if (pronvers.size() == 3)
                task.execute(pronvers.get(0), pronvers.get(1), pronvers.get(2));
        } else
            task.execute("", "", "");

    }

    @Override
    public void CreOrUpSuccress(String content) {
        dismissDialog(mAlertDialog);
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(content, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                finish();
            } else {
                T_ERROR(mContext, vo.getData().getMessage());
            }
        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void CreOrUpError(String msg) {
        dismissDialog(mAlertDialog);
        T_ERROR(mContext);
    }


}
