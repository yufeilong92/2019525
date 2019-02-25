package com.xuechuan.xcedu.ui.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.ResultNetAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.HomeNetContract;
import com.xuechuan.xcedu.mvp.model.HomeNetModel;
import com.xuechuan.xcedu.mvp.presenter.HomeNetPresenter;
import com.xuechuan.xcedu.vo.NetAllVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: HomeNetActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 首页界面展示网课模块
 * @author: L-BackPacker
 * @date: 2018.11.23 下午 2:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.23
 */
public class HomeNetActivity extends BaseActivity implements HomeNetContract.View {

    private RecyclerView mRlvNetContent;
    private ImageView mIvNetEmpty;
    private XRefreshView mXrfvContent;
    private long lastRefreshTime;
    private List mArrary;
    private Context mContext;
    private ResultNetAdapter adapter;
    private HomeNetPresenter mPresenter;
    private boolean isRefresh;

    /*   @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_home_net);
           initView();
       }
   */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_net);
        initView();
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXrfvContent.startRefresh();
    }

    private void initData() {
        mPresenter = new HomeNetPresenter();
        mPresenter.initModelView(new HomeNetModel(), this);
    }

    private void bindAdapterData() {
        setGridLayoutManger(mContext, mRlvNetContent, 1);
        adapter = new ResultNetAdapter(mContext, mArrary);
        mRlvNetContent.setAdapter(adapter);
        adapter.setOnItemClickListener(new ResultNetAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(NetAllVo.DatasBean vo) {
                Intent intent = VideoInfomActivity.start_Intent(mContext,String.valueOf(vo.getId()));
                intent.putExtra(VideoInfomActivity.CSTR_EXTRA_TITLE_STR_HOME, vo.getName());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mContext = this;
        mRlvNetContent = (RecyclerView) findViewById(R.id.rlv_net_content);
        mIvNetEmpty = (ImageView) findViewById(R.id.iv_net_empty);
        mXrfvContent = (XRefreshView) findViewById(R.id.xrfv_content);
    }

    private void initXrfresh() {
        mXrfvContent.restoreLastRefreshTime(lastRefreshTime);
        mXrfvContent.setPullRefreshEnable(true);
        mXrfvContent.setPullLoadEnable(true);
        mXrfvContent.setAutoLoadMore(true);
        mXrfvContent.setEmptyView(mIvNetEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXrfvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }
        });
    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestNetOne(mContext, getNowPage() + 1);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestNetOne(mContext, 1);
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
    public void NetSuccessOne(String success) {
        mXrfvContent.stopRefresh();
        lastRefreshTime = mXrfvContent.getLastRefreshTime();
        isRefresh = false;
        Gson gson = new Gson();
        NetAllVo vo = gson.fromJson(success, NetAllVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<NetAllVo.DatasBean> datas = vo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                adapter.notifyDataSetChanged();
                mXrfvContent.setLoadComplete(true);
                return;
            }
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXrfvContent.setPullLoadEnable(true);
                mXrfvContent.setLoadComplete(false);
            } else
                mXrfvContent.setLoadComplete(true);
            if (vo.getTotal().getTotal() == mArrary.size())
                mXrfvContent.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            T_ERROR(mContext);
        }

    }

    @Override
    public void NetErrorOne(String msg) {
        isRefresh = false;
        mXrfvContent.stopRefresh();
        T_ERROR(mContext);
    }

    @Override
    public void NetSuccessTwo(String success) {
        isRefresh = false;
        lastRefreshTime = mXrfvContent.getLastRefreshTime();
        isRefresh = false;
        Gson gson = new Gson();
        NetAllVo vo = gson.fromJson(success, NetAllVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<NetAllVo.DatasBean> datas = vo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                adapter.notifyDataSetChanged();
                mXrfvContent.setLoadComplete(true);
                return;
            }
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXrfvContent.setPullLoadEnable(true);
                mXrfvContent.setLoadComplete(false);
            } else
                mXrfvContent.setLoadComplete(true);
            if (vo.getTotal().getTotal() == mArrary.size())
                mXrfvContent.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void NetErrorTwo(String msg) {
        isRefresh = false;
        mXrfvContent.setLoadComplete(false);
        T_ERROR(mContext);
    }
}
