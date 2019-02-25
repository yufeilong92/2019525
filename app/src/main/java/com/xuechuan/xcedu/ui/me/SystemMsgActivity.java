package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.me.MySystemAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.MySystemContract;
import com.xuechuan.xcedu.mvp.model.MySystemModel;
import com.xuechuan.xcedu.mvp.presenter.MySystemPresenter;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SystemVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SystemMsgActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 系统设置消息
 * @author: L-BackPacker
 * @date: 2018/5/30 18:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/30
 */
public class SystemMsgActivity extends BaseActivity implements MySystemContract.View, View.OnClickListener {

    private RecyclerView mRlvMySystem;
    private ImageView mIvContentEmpty;
    private XRefreshView mXfvContentSystem;
    private List mArrary;
    private Context mContext;
    private long lastRefreshTime;
    private boolean isRefresh;
    private MySystemPresenter mPresenter;
    private MySystemAdapter adapter;
    private ImageView mIvDelAllSystem;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_system_msg);
//        initView();
//    }

    /**
     * 删除
     */
    private int mDelPosition = -1;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_system_msg);
        initView();
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXfvContentSystem.startRefresh();
    }

    private void initData() {
        mPresenter = new MySystemPresenter();
        mPresenter.initModelView(new MySystemModel(), this);
    }

    private void initView() {
        mContext = this;
        mRlvMySystem = (RecyclerView) findViewById(R.id.rlv_my_system);
        mIvContentEmpty = (ImageView) findViewById(R.id.iv_content_empty);
        mXfvContentSystem = (XRefreshView) findViewById(R.id.xfv_content_system);
        mIvDelAllSystem = (ImageView) findViewById(R.id.iv_del_all_system);
        mIvDelAllSystem.setOnClickListener(this);
    }

    private void initXrfresh() {
        mXfvContentSystem.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentSystem.setPullLoadEnable(true);
        mXfvContentSystem.setAutoLoadMore(true);
        mXfvContentSystem.setPullRefreshEnable(true);
        mXfvContentSystem.setEmptyView(mIvContentEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXfvContentSystem.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        mPresenter.requestSystemMoreMsg(mContext, getNowPage() + 1);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestSystemMsg(mContext, 1);
    }

    private void bindAdapterData() {
        setGridLayoutManger(mContext,mRlvMySystem,1);
        adapter = new MySystemAdapter(mContext, mArrary);
        mRlvMySystem.setAdapter(adapter);
        adapter.setClickListener(new MySystemAdapter.onItemClickListener() {
            @Override
            public void onClickListener(SystemVo.DatasBean obj, int position) {
                Intent intent = AgreementActivity.newInstance(mContext,
                        obj.getGourl(), AgreementActivity.NOSHAREMARK, "", "");
                intent.putExtra(AgreementActivity.CSTR_EXTRA_TITLE_STR, obj.getTitle());
                startActivity(intent);
            }
        });
        adapter.setClickLangListener(new MySystemAdapter.onItemLangClickListener() {
            @Override
            public void onClickLangListener(final SystemVo.DatasBean obj, int position) {
                submitDelSystem(obj, position);
            }
        });

    }

    /**
     * 提交删除
     *
     * @param obj
     * @param position
     */
    private void submitDelSystem(final SystemVo.DatasBean obj, final int position) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(obj.getId());
        mDelPosition = position;
        mPresenter.submitDelSystemMsg(mContext, list);

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
    public void SystemSuccess(String con) {
        mXfvContentSystem.stopRefresh();
        isRefresh = false;
        Gson gson = new Gson();
        SystemVo orderVo = gson.fromJson(con, SystemVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<SystemVo.DatasBean> datas = orderVo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentSystem.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentSystem.setLoadComplete(false);
                mXfvContentSystem.setPullLoadEnable(true);
            } else {
                mXfvContentSystem.setLoadComplete(true);
            }
            if (orderVo.getTotal().getTotal()==mArrary.size()){
                mXfvContentSystem.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(orderVo.getStatus().getMessage());
        }
    }

    @Override
    public void SystemErrorr(String con) {
        mXfvContentSystem.stopRefresh();
        isRefresh = false;
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void SystemMoreSuccess(String con) {
        isRefresh = false;
        Gson gson = new Gson();
        SystemVo orderVo = gson.fromJson(con, SystemVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<SystemVo.DatasBean> datas = orderVo.getDatas();
//                    clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentSystem.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentSystem.setLoadComplete(false);
                mXfvContentSystem.setPullLoadEnable(true);
            } else {
                mXfvContentSystem.setLoadComplete(true);
            }
            if (orderVo.getTotal().getTotal()==mArrary.size()){
                mXfvContentSystem.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXfvContentSystem.setLoadComplete(false);
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(orderVo.getStatus().getMessage());
        }
    }

    @Override
    public void SystemMoreErrorr(String con) {
        isRefresh = false;
        mXfvContentSystem.setLoadComplete(false);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void DelSuccess(String con) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (mDelPosition != -1)
                mArrary.remove(mDelPosition);
            else
                mXfvContentSystem.startRefresh();
            adapter.notifyDataSetChanged();
            T.showToast(mContext, getStringWithId(R.string.delect_Success));
        } else {
            T.showToast(mContext, getStringWithId(R.string.delect_error));
            String message = vo.getStatus().getMessage();
            L.e(message);
        }
    }

    @Override
    public void DelError(String con) {
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    private void doDelAll() {
        if (mArrary==null||mArrary.isEmpty()){
            T.showToast(mContext,getString(R.string.system_msg));
            return;
        }
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, "是否删除当前消息", getStringWithId(R.string.cancel),
                getStringWithId(R.string.sure), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                List<SystemVo.DatasBean> data = (List<SystemVo.DatasBean>) mArrary;
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    SystemVo.DatasBean bean = data.get(i);
                    list.add(bean.getId());
                }

                mPresenter.submitDelSystemMsg(mContext, list);
            }

            @Override
            public void onCancelClickListener() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_del_all_system:
                doDelAll();
                break;


        }

    }

}
