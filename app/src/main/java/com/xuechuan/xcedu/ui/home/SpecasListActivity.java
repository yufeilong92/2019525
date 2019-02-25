package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.SpecsOrderAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.SpecasListContract;
import com.xuechuan.xcedu.mvp.model.SpecasListModel;
import com.xuechuan.xcedu.mvp.presenter.SpecasListPresenter;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.RecyclerUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.SpecasChapterListVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SpecasActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 规范页
 * @author: L-BackPacker
 * @date: 2018/4/19 17:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class SpecasListActivity extends BaseActivity implements SpecasListContract.View {


    private RecyclerView mRlvSpecaContent;

    private static String PARAME = "PARAME";
    private static String PARAME1 = "PARAME";
    private String parame;
    private String parame1;
    private Context mContext;
    private XRefreshView mXrfvSpecaRefresh;
    private List mArrary;
    /**
     * 刷新时间
     */
    public static long lastRefreshTime;
    private SpecsOrderAdapter adapter;
    /**
     * 防止冲突
     */
    private boolean isRefresh = false;
    private ImageView mTvNetEmptyContent;
    private SpecasListPresenter mPresenter;

    public static Intent newInstance(Context context, String parame, String parame1) {
        Intent intent = new Intent(context, SpecasListActivity.class);
        intent.putExtra(PARAME, parame);
        intent.putExtra(PARAME1, parame1);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specas);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_specas);
        if (getIntent() != null) {
            parame = getIntent().getStringExtra(PARAME);
            parame1 = getIntent().getStringExtra(PARAME1);
        }
        initView();
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXrfvSpecaRefresh.startRefresh();
    }

    private void initData() {
        mPresenter = new SpecasListPresenter();
        mPresenter.initModelView(new SpecasListModel(),this);
    }
    private void requestData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.RequestSpecasListOne(mContext,1);
    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.RequestSpecasListTwo(mContext,getNowPage()+1);
    }

    /**
     * 绑定适配器数据
     */
    private void bindAdapterData() {
        GridLayoutManager gridLayoutManager = getGridLayoutManger(mContext, mRlvSpecaContent, 1);
        adapter = new SpecsOrderAdapter(mContext, mArrary, gridLayoutManager,mRlvSpecaContent);
        mRlvSpecaContent.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.VERTICAL));
        mRlvSpecaContent.setAdapter(adapter);

    }

    private void initXrfresh() {
        mXrfvSpecaRefresh.restoreLastRefreshTime(lastRefreshTime);
        mXrfvSpecaRefresh.setPullLoadEnable(true);
        mXrfvSpecaRefresh.setAutoLoadMore(true);
        mXrfvSpecaRefresh.setPullRefreshEnable(true);
        mXrfvSpecaRefresh.setEmptyView(mTvNetEmptyContent);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXrfvSpecaRefresh.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                requestData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }
        });
    }


    private void initView() {
        mContext = this;
        mRlvSpecaContent = (RecyclerView) findViewById(R.id.rlv_speca_content);
        mXrfvSpecaRefresh = (XRefreshView) findViewById(R.id.xrfv_speca_refresh);
        mTvNetEmptyContent = (ImageView) findViewById(R.id.tv_net_empty_content);
    }

    private void clearData() {
        if (mArrary == null) {
            mArrary = new ArrayList();
        } else {
            mArrary.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArrary == null) {
            clearData();
        }
        mArrary.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArrary == null || mArrary.isEmpty())
            return 0;
        if (mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }

    @Override
    public void SpecasListOneSuccess(String con) {
        mXrfvSpecaRefresh.stopRefresh();
        isRefresh = false;
//                String s = response.body().toString();
        L.e("获取规范章节列表数据" + con);
        Gson gson = new Gson();
        SpecasChapterListVo vo = gson.fromJson(con, SpecasChapterListVo.class);
        if (vo.getStatus().getCode() == 200) {//成功
            List list = vo.getDatas();
            clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }

//                    if ( mArrary.size() == vo.getTotal().getTotal()) {
//                        mXrfvSpecaRefresh.setLoadComplete(true);
//                    } else {
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvSpecaRefresh.setLoadComplete(false);
                mXrfvSpecaRefresh.setPullLoadEnable(true);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
            }
//                    }
            if (vo.getTotal().getTotal()==mArrary.size())
                mXrfvSpecaRefresh.setLoadComplete(true);
            adapter.initSelectVo(RecyclerUtil.initSelectVo(mArrary.size(),-1));
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            T.showToast(mContext, getStringWithId(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void SpecasListOneErrorr(String msg) {
        isRefresh = false;
        mXrfvSpecaRefresh.stopRefresh();
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    @Override
    public void SpecasListTwoSuccess(String con) {
        isRefresh = false;
        L.e("获取规范章节列表数据" + con);
        L.e(getNowPage() + "集合长度" + mArrary.size());
        Gson gson = new Gson();
        SpecasChapterListVo vo = gson.fromJson(con, SpecasChapterListVo.class);
        if (vo.getStatus().getCode() == 200) {//成功
            List list = vo.getDatas();
//                    clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvSpecaRefresh.setLoadComplete(false);
                mXrfvSpecaRefresh.setPullLoadEnable(true);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal()==mArrary.size())
                mXrfvSpecaRefresh.setLoadComplete(true);
            adapter.initSelectVo(RecyclerUtil.initSelectVo(mArrary.size(),-1));
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXrfvSpecaRefresh.setLoadComplete(false);
            T.showToast(mContext, getStringWithId(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void SpecasListTwoErrorr(String msg) {
        isRefresh = false;
        mXrfvSpecaRefresh.setLoadComplete(false);
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }
}