package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.AllAddressAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.AddressContract;
import com.xuechuan.xcedu.mvp.model.AddressModel;
import com.xuechuan.xcedu.mvp.presenter.AddressPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.vo.AddressItemsBean;
import com.xuechuan.xcedu.vo.AllAddressVo;
import com.xuechuan.xcedu.vo.ResultVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AddressMessageActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 地址管理
 * @author: L-BackPacker
 * @date: 2018/8/15 17:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/15
 */
public class AddressMangerActivity extends BaseActivity implements AddressContract.View, View.OnClickListener {

    private RecyclerView mRlvAddressContent;
    private AddressPresenter mPresenter;
    private Context mContext;
    private AlertDialog mAlertDialog;
    private TextView mTvAddressAdd;
    private ArrayList mArray;
    private ImageView mIvContentEmpty;
    private XRefreshView mXfvContentAddress;
    private AllAddressAdapter mAdapter;
    private long lastRefreshTime;
    private boolean isRefresh;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_message);
        initView();
    }
*/
    /**
     * tag 传参类型
     */
    private static String INTENTTAG = "intenttag";
    /**
     * 地址选择
     */
    public static final String TAG_SELECT = "select";
    /**
     * 地址管理
     */
    public static final String TAG_MANGE = "mange";
    private String mTag;

    public static Intent newInstance(Context context, String intenttag) {
        Intent intent = new Intent(context, AddressMangerActivity.class);
        intent.putExtra(INTENTTAG, intenttag);
        return intent;
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_address_message);
        if (getIntent() != null) {
            mTag = getIntent().getStringExtra(INTENTTAG);
        }
        initView();
        initData();
        clearData();
        bindAdapte();
        initXRfresh();
        mXfvContentAddress.startRefresh();

    }

    private void initXRfresh() {
        mXfvContentAddress.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentAddress.setPullRefreshEnable(true);
        mXfvContentAddress.setPullLoadEnable(true);
        mXfvContentAddress.setAutoLoadMore(true);
        mXfvContentAddress.setEmptyView(mIvContentEmpty);
        mAdapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXfvContentAddress.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();
            }
        });
    }

    private void loadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestAllAddressMore(mContext, getNowPage() + 1);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestAllAddress(mContext, 1);
    }

    private void bindAdapte() {
        mAdapter = new AllAddressAdapter(mContext, mArray);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvAddressContent.setLayoutManager(manager);
        mRlvAddressContent.setAdapter(mAdapter);
        if (mTag.equalsIgnoreCase(TAG_MANGE)) {
            mAdapter.showDetailButton(true);
        } else if (mTag.equalsIgnoreCase(TAG_SELECT)) {
            mAdapter.showDetailButton(false);
        }
        mAdapter.setChbClickListener(new AllAddressAdapter.onChbItemClickListener() {
            @Override
            public void onChbClickListener(AddressItemsBean obj, boolean isChecked, int position) {
                mAlertDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
                mPresenter.submitDafaultAddress(mContext, obj.getId(), 0);
            }
        });
        mAdapter.setClickListener(new AllAddressAdapter.onItemClickListener() {
            @Override
            public void onClickListener(AddressItemsBean obj, int position) {
                if (mTag.equalsIgnoreCase(TAG_MANGE)) {
                    Intent intent = AddAddressActivity.newInstance(mContext, obj, AddAddressActivity.CREEDADD);
                    startActivity(intent);
                    return;
                }
                if (mTag.equalsIgnoreCase(TAG_SELECT)) {
                    Intent intent = new Intent();
                    intent.putExtra(DataMessageVo.SELECTTAG, obj);
                    setResult(TakeDeliveryActivity.RESYKTY_RESULT_CODE, intent);
                    finish();
                    return;
                }
            }
        });
        mAdapter.setDelecteClickListener(new AllAddressAdapter.onDelecteItemClickListener() {
            @Override
            public void onDelecteClickListener(AddressItemsBean obj, int position) {
                mAlertDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
                mPresenter.submitDafaultAddress(mContext, obj.getId(), 1);
            }
        });

        mAdapter.setEditClickListener(new AllAddressAdapter.onEditItemClickListener() {
            @Override
            public void onEditClickListener(AddressItemsBean obj, int position) {
                Intent intent = AddAddressActivity.newInstance(mContext, obj, AddAddressActivity.CREEDADD);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        mPresenter = new AddressPresenter();
        mPresenter.initModelView(new AddressModel(), this);
    }

    private void initView() {
        mContext = this;
        mRlvAddressContent = (RecyclerView) findViewById(R.id.rlv_address_content);
        mTvAddressAdd = (TextView) findViewById(R.id.tv_address_add);
        mTvAddressAdd.setOnClickListener(this);
        mIvContentEmpty = (ImageView) findViewById(R.id.iv_content_empty);
        mIvContentEmpty.setOnClickListener(this);
        mXfvContentAddress = (XRefreshView) findViewById(R.id.xfv_content_address);
        mXfvContentAddress.setOnClickListener(this);
    }


    @Override
    public void allAddressSuccess(String com) {
        mXfvContentAddress.stopRefresh();
        isRefresh = false;
        Gson gson = new Gson();
        AllAddressVo orderVo = gson.fromJson(com, AllAddressVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<AddressItemsBean> datas = orderVo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentAddress.setLoadComplete(true);
                mAdapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentAddress.setLoadComplete(false);
            } else {
                mXfvContentAddress.setLoadComplete(true);
            }
            if (orderVo.getTotal().getTotal() == mArray.size()) {
                mXfvContentAddress.setLoadComplete(true);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            T_ERROR(mContext);
        }
    }

    @Override
    public void allAddressError(String msg) {
        mXfvContentAddress.stopRefresh();
        isRefresh = false;
        T_ERROR(mContext);
    }

    @Override
    public void allAddressMoreSuccess(String com) {
        isRefresh = false;
        Gson gson = new Gson();
        AllAddressVo orderVo = gson.fromJson(com, AllAddressVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<AddressItemsBean> datas = orderVo.getDatas();
//                    clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentAddress.setLoadComplete(true);
                mAdapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentAddress.setLoadComplete(false);
            } else {
                mXfvContentAddress.setLoadComplete(true);
            }
            if (orderVo.getTotal().getTotal() == mArray.size()) {
                mXfvContentAddress.setLoadComplete(true);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXfvContentAddress.setLoadComplete(false);
            T_ERROR(mContext);
        }
    }

    @Override
    public void allAddressMoreError(String msg) {
        isRefresh = false;
        mXfvContentAddress.setLoadComplete(false);
        T_ERROR(mContext);
    }

    @Override
    public void DefaulitOrDelectSuccess(String com) {
        dismissDialog(mAlertDialog);
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(com, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                mXfvContentAddress.startRefresh();
            } else {
                T_ERROR(mContext, vo.getData().getMessage());
            }
        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void DefaulitOrDelectError(String msg) {
        dismissDialog(mAlertDialog);
        T_ERROR(mContext);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address_add:
                startActivity(new Intent(AddressMangerActivity.this, AddAddressActivity.class));
                break;
            default:

        }
    }

    private void clearData() {
        if (mArray == null) {
            mArray = new ArrayList();
        } else {
            mArray.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArray == null) {
            clearData();
        }
        mArray.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArray == null || mArray.isEmpty())
            return 0;
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mXfvContentAddress != null) {
            mXfvContentAddress.startRefresh();
        }
    }

}
