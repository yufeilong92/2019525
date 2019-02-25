package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.me.MyMsgAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.MyMsgContract;
import com.xuechuan.xcedu.mvp.model.MyMsgModel;
import com.xuechuan.xcedu.mvp.presenter.MyMsgPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.MyMsgVo;
import com.xuechuan.xcedu.vo.ResultVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MyMsgActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 我的消息
 * @author: L-BackPacker
 * @date: 2018/5/22 15:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class MyMsgActivity extends BaseActivity implements MyMsgContract.View, View.OnClickListener {

    private RecyclerView mRlvMyMsg;
    private ImageView mIvContentEmpty;
    private XRefreshView mXfvContentMsg;
    private List mArrary;
    private long lastRefreshTime;
    private Context mContext;
    private MyMsgPresenter mPresenter;
    private MyMsgAdapter adapter;
    private boolean isRefresh;
    private ImageView mIvDelAll;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_msg);
//        initView();
//    }

    /**
     * 删除数据
     */
    private int mDelPostion = -1;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_msg);
        initView();
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXfvContentMsg.startRefresh();
    }

    private void initData() {
        mPresenter = new MyMsgPresenter();
        mPresenter.initModelView(new MyMsgModel(), this);
    }

    private void initView() {
        mContext = this;
        mRlvMyMsg = (RecyclerView) findViewById(R.id.rlv_my_msg);
        mIvContentEmpty = (ImageView) findViewById(R.id.iv_content_empty);
        mXfvContentMsg = (XRefreshView) findViewById(R.id.xfv_content_msg);
        mIvDelAll = (ImageView) findViewById(R.id.iv_del_all);
        mIvDelAll.setOnClickListener(this);
    }

    private void initXrfresh() {
        mXfvContentMsg.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentMsg.setPullRefreshEnable(true);
        mXfvContentMsg.setPullLoadEnable(true);
        mXfvContentMsg.setAutoLoadMore(true);
        mXfvContentMsg.setEmptyView(mIvContentEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXfvContentMsg.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        mPresenter.requestMyMsgMore(mContext, getNowPage() + 1);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestMyMsg(mContext, 1);
    }

    private void bindAdapterData() {
        setGridLayoutManger(mContext,mRlvMyMsg,1);
        adapter = new MyMsgAdapter(mContext, mArrary);
        mRlvMyMsg.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.VERTICAL));
        mRlvMyMsg.setAdapter(adapter);
        adapter.setLangClickListener(new MyMsgAdapter.onItemLangClickListener() {
            @Override
            public void onClickLangListener(MyMsgVo.DatasBean datas, int position) {
                List<Integer> list = new ArrayList<>();
                list.add(datas.getId());
                mDelPostion = position;
                mPresenter.submitDelMyMsg(mContext, list);
            }
        });
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
    public void MyMsgSuccess(String con) {
        mXfvContentMsg.stopRefresh();
        isRefresh = false;
        Gson gson = new Gson();
        Log.e("=====", "MyMsgSuccess: " + con);
        MyMsgVo orderVo = gson.fromJson(con, MyMsgVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<MyMsgVo.DatasBean> datas = orderVo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentMsg.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentMsg.setLoadComplete(false);
                mXfvContentMsg.setPullLoadEnable(true);
            } else {
                mXfvContentMsg.setLoadComplete(true);
            }
            if (orderVo.getTotal().getTotal()==mArrary.size()){
                mXfvContentMsg.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(orderVo.getStatus().getMessage());
        }
    }

    @Override
    public void MyMsgError(String con) {
        mXfvContentMsg.stopRefresh();
        isRefresh = false;

        T.showToast(mContext,getStringWithId(R.string.net_error));
    }

    @Override
    public void MyMsgMoreSuccess(String con) {
        isRefresh = false;
        Gson gson = new Gson();
        MyMsgVo orderVo = gson.fromJson(con, MyMsgVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<MyMsgVo.DatasBean> datas = orderVo.getDatas();
//                    clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentMsg.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentMsg.setLoadComplete(false);
                mXfvContentMsg.setPullLoadEnable(true);
            } else {
                mXfvContentMsg.setLoadComplete(true);
            }
            if (orderVo.getTotal().getTotal()==mArrary.size()){
                mXfvContentMsg.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXfvContentMsg.setLoadComplete(false);
            L.e(orderVo.getStatus().getMessage());
        }
    }

    @Override
    public void MyMsgMoreError(String con)
    {
        isRefresh = false;
        mXfvContentMsg.setLoadComplete(false);
        T.showToast(mContext,getStringWithId(R.string.net_error));
    }

    @Override
    public void DelMyMsg(String con) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (mDelPostion != -1)
                mArrary.remove(mDelPostion);
            else
                mXfvContentMsg.startRefresh();
            adapter.notifyDataSetChanged();

            T.showToast(mContext, getStringWithId(R.string.delect_Success));
        } else {
            T.showToast(mContext, getStringWithId(R.string.delect_error));
            String message = vo.getStatus().getMessage();
            L.e(message);
        }
    }

    @Override
    public void DelMyError(String con) {
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_del_all:
                doDelAll();
                break;
        }

    }

    private void doDelAll() {
        if (mArrary==null||mArrary.isEmpty()){
            T.showToast(mContext,getString(R.string.my_msg));
            return;
        }
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, "是否删除当前列表消息",
                getStringWithId(R.string.sure), getStringWithId(R.string.cancel), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                List<MyMsgVo.DatasBean> data = (List<MyMsgVo.DatasBean>) mArrary;
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    MyMsgVo.DatasBean datasBean = data.get(i);
                    list.add(datasBean.getId());
                }
                mPresenter.submitDelMyMsg(mContext, list);
            }

            @Override
            public void onCancelClickListener() {

            }
        });
    }

}
