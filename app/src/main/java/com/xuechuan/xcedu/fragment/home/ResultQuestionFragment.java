package com.xuechuan.xcedu.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.event.SearchEvenText;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.QuestionListAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.SearchModelImpl;
import com.xuechuan.xcedu.mvp.presenter.SearchPresenter;
import com.xuechuan.xcedu.mvp.view.SearchView;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.SeachResultActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.ResultQuesitonVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ResultFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 结果
 * @author: L-BackPacker
 * @date: 2018/5/8 18:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/8
 */
public class ResultQuestionFragment extends BaseFragment implements SearchView {
    private static final String SEARCHKEY = "param1";
    private static final String TYPELEI = "param2";

    private String mSearchkey;
    private String mType;
    private RecyclerView mRlvReasultContent;
    private ImageView mTvEmpty;
    private XRefreshView mXrfResultContent;

    private List mArray;
    private boolean isRefresh;
    private Context mContext;
    private AlertDialog mAlertDialog;
    private SearchPresenter mPresenter;
    private long lastRefreshtime;
    private QuestionListAdapter adapter;
    private QuestionSqliteHelp mSqliteHelp;

    /**
     * @param searck
     * @param type
     * @return
     */
    public static ResultQuestionFragment newInstance(String searck, String type) {
        ResultQuestionFragment fragment = new ResultQuestionFragment();
        Bundle args = new Bundle();
        args.putString(SEARCHKEY, searck);
        args.putString(TYPELEI, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchkey = getArguments().getString(SEARCHKEY);
            mType = getArguments().getString(TYPELEI);
        }
        EventBus.getDefault().register(this);
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        initView(view);
        initData();
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_result;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MainThreaData(SearchEvenText text) {
        String key = text.getmSearchData();
        mSearchkey = key;
        if (mXrfResultContent != null)
            mXrfResultContent.startRefresh();
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initUtils();
//        initData();
        clearData();
        bindAdapterData();
        initXrfresh();

    }

    private void initUtils() {
        mSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);
    }


    private void bindAdapterData() {
        setGridLayoutManger(mContext, mRlvReasultContent, 1);
        adapter = new QuestionListAdapter(mContext, mArray);
        adapter.setKeyStr(mSearchkey);
//        mRlvReasultContent.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.VERTICAL));
        mRlvReasultContent.setAdapter(adapter);
        adapter.setClickListener(new QuestionListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                QuestionSqliteVo vo = (QuestionSqliteVo) obj;
                Intent intent = SeachResultActivity.start_Intent(mContext, vo, "");
                startActivity(intent);
            }
        });


    }

    private void initXrfresh() {
        mXrfResultContent.setPullLoadEnable(true);
        mXrfResultContent.setAutoRefresh(true);
        mXrfResultContent.setAutoLoadMore(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXrfResultContent.setEmptyView(mTvEmpty);
        mXrfResultContent.restoreLastRefreshTime(lastRefreshtime);
        mXrfResultContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                reqestData();
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<QuestionSqliteVo> listLick = mSqliteHelp.getQuestionListLick(mArray.size() + 1, mSearchkey);
                isRefresh = false;
                if (listLick != null && !listLick.isEmpty()) {
                    addListData(listLick);
                } else {
                    mXrfResultContent.setLoadComplete(true);
                    adapter.notifyDataSetChanged();
                    return;
                }
                if (mArray != null && mArray.size() % 30 == 0) {
                    mXrfResultContent.setPullLoadEnable(true);
                    mXrfResultContent.setLoadComplete(false);
                } else {
                    mXrfResultContent.setLoadComplete(true);
                }
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    private void reqestData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<QuestionSqliteVo> listLick = mSqliteHelp.getQuestionListLick(1, mSearchkey);
                mXrfResultContent.stopRefresh();
                lastRefreshtime = mXrfResultContent.getLastRefreshTime();
                isRefresh = false;
                clearData();
                if (listLick != null && !listLick.isEmpty()) {
                    addListData(listLick);
                } else {
                    adapter.notifyDataSetChanged();
                    mXrfResultContent.setLoadComplete(true);
                    return;
                }
                if (mArray != null && mArray.size() % 30 == 0) {
                    mXrfResultContent.setPullLoadEnable(true);
                    mXrfResultContent.setLoadComplete(false);
                } else {
                    mXrfResultContent.setLoadComplete(true);
                }
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvReasultContent = (RecyclerView) view.findViewById(R.id.rlv_reasult_content);
        mTvEmpty = (ImageView) view.findViewById(R.id.tv_empty);
        mXrfResultContent = (XRefreshView) view.findViewById(R.id.xrf_result_content);
    }

    @Override
    public void HostSuccess(String cont) {

    }

    @Override
    public void HostError(String cont) {

    }

    @Override
    public void ResultSuccess(String message) {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        mXrfResultContent.stopRefresh();
        lastRefreshtime = mXrfResultContent.getLastRefreshTime();
        isRefresh = false;

        L.w(message);
        Gson gson = new Gson();
        ResultQuesitonVo vo = gson.fromJson(message, ResultQuesitonVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<ResultQuesitonVo.DatasBean> datas = vo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                adapter.notifyDataSetChanged();
                mXrfResultContent.setLoadComplete(true);
                return;
            }
//            if ( mArray.size() == vo.getTotal().getTotal()) {
//                mXrfResultContent.setLoadComplete(true);
//            } else {
            if (mArray != null && mArray.size() % 30 == 0) {
                mXrfResultContent.setPullLoadEnable(true);
                mXrfResultContent.setLoadComplete(false);
            } else {
                mXrfResultContent.setLoadComplete(true);
            }
//            mXrfResultContent.setPullLoadEnable(true);
//            mXrfResultContent.setLoadComplete(false);
//            }
            if (vo.getTotal().getTotal() == mArray.size()) {
                mXrfResultContent.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            T.showToast(mContext, status.getMessage());
        }

    }

    @Override
    public void ResultError(String cont) {
        isRefresh = false;
        mXrfResultContent.stopRefresh();
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void ResultMoreSuccess(String message) {
        isRefresh = false;
        L.w(message);
        Gson gson = new Gson();
        ResultQuesitonVo vo = gson.fromJson(message, ResultQuesitonVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<ResultQuesitonVo.DatasBean> datas = vo.getDatas();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXrfResultContent.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }

            if (mArray != null && mArray.size() % 30 == 0) {
                mXrfResultContent.setPullLoadEnable(true);
                mXrfResultContent.setLoadComplete(false);
            } else {
                mXrfResultContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mArray.size()) {
                mXrfResultContent.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            mXrfResultContent.setLoadComplete(false);
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            T.showToast(mContext, status.getMessage());
        }
    }

    @Override
    public void ResultMoreError(String cont) {
        isRefresh = false;
        mXrfResultContent.setLoadComplete(false);
        L.e(cont);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    private void clearData() {
        if (mArray == null) {
            mArray = new ArrayList();
        } else {
            mArray.clear();
        }
    }

    private void addListData(List<?> list) {
        if (mArray == null) {
            clearData();
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        mArray.addAll(list);
    }

    private int getPager() {
        if (mArray == null || mArray.isEmpty()) {
            return 0;
        }
        if (mArray.size() % 30 == 0) {
            return mArray.size() / 30;
        } else {
            return mArray.size() /30 + 1;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
