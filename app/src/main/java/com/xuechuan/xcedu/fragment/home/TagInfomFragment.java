package com.xuechuan.xcedu.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.TagListAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.TagInfomModelImpl;
import com.xuechuan.xcedu.mvp.presenter.TagInfomPresenter;
import com.xuechuan.xcedu.mvp.view.TagInfomView;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ArticleVo;
import com.xuechuan.xcedu.vo.TagListVo;


import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: TagInfomFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: tag详情
 * @author: L-BackPacker
 * @date: 2018/5/13 13:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/13
 */
public class TagInfomFragment extends BaseFragment implements TagInfomView {
    /**
     * 标签tagid
     */
    private static final String TAGID = "tagid";

    private String mTagId;
    private RecyclerView mRlvInfomList;
    private XRefreshView mXfvContent;
    //    数据集合
    private List mArray;
    private TagListAdapter adapter;
    private Context mContext;
    private long lastRefreshtime;
    private TagInfomPresenter mPresenter;
    private TextView tvEmpty;
    /**
     * 防止重复刷新
     */
    private boolean isRefresh;


    public TagInfomFragment() {
    }

    public static TagInfomFragment newInstance(String tagid) {
        TagInfomFragment fragment = new TagInfomFragment();
        Bundle args = new Bundle();
        args.putString(TAGID, tagid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTagId = getArguments().getString(TAGID);
        }
    }

    /*   @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
           View inflate = inflater.inflate(R.layout.fragment_tag_infom, container, false);
           initView(inflate);
           return inflate;
       }
   */
    @Override
    protected int initInflateView() {
        return R.layout.fragment_tag_infom;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXfvContent.startRefresh();
    }


    private void initData() {
        mPresenter = new TagInfomPresenter(new TagInfomModelImpl(), this);
        mPresenter.requestTagNewCont(mContext, 1, mTagId);
    }


    private void initView(View inflate) {
        mContext = getActivity();
        tvEmpty = inflate.findViewById(R.id.tv_listEmpty);
        mRlvInfomList = (RecyclerView) inflate.findViewById(R.id.rlv_infom_list);
        mXfvContent = (XRefreshView) inflate.findViewById(R.id.xfv_content);
    }

    private void bindAdapterData() {
        adapter = new TagListAdapter(mContext, mArray);
        setGridLayoutManger(mContext, mRlvInfomList, 1);
        mRlvInfomList.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.VERTICAL));
        mRlvInfomList.setAdapter(adapter);
        adapter.setClickListener(new TagListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ArticleVo vo = (ArticleVo) obj;
                Log.e("====", "onClickListener: ");
                Intent intent = InfomDetailActivity.startInstance(mContext,
                        vo.getGourl(), String.valueOf(vo.getId())
                        , vo.getTitle(), DataMessageVo.USERTYOEARTICLE);
//                Intent intent = InfomDetailActivity.startInstance(mContext, String.valueOf(vo.getId()), vo.getGourl(),DataMessageVo.USERTYPEA );
                mContext.startActivity(intent);
            }

        });

    }

    private void initXrfresh() {
        mXfvContent.setPullLoadEnable(true);
        mXfvContent.setAutoRefresh(true);
        mXfvContent.setAutoLoadMore(true);
        mXfvContent.setEmptyView(tvEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        mXfvContent.restoreLastRefreshTime(lastRefreshtime);
        mXfvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        mPresenter.requestTagMoreCont(mContext, getPager() + 1, mTagId);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestTagNewCont(mContext, 1, mTagId);
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
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        } else {
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
        }
    }


    @Override
    public void ArticleTagOneConSuccess(String message) {
        L.d(message);
        mXfvContent.stopRefresh();
        lastRefreshtime = mXfvContent.getLastRefreshTime();
        isRefresh = false;
        L.w(message);
        Gson gson = new Gson();
        TagListVo vo = gson.fromJson(message, TagListVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<ArticleVo> datas = vo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                adapter.notifyDataSetChanged();
                mXfvContent.setLoadComplete(true);
                return;
            }
//            if ( mArray.size() == vo.getTotal().getTotal()) {
//                mXfvContent.setLoadComplete(true);
//            } else {
            if (mArray != null && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContent.setPullLoadEnable(true);
                mXfvContent.setLoadComplete(false);
            } else {
                mXfvContent.setLoadComplete(true);
            }
//            }
            adapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(status.getMessage());
        }
    }

    @Override
    public void ArticleTagOneConError(String con) {
        isRefresh = false;
        mXfvContent.stopRefresh();
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void ArticleTagMoreConSuccess(String message) {
        isRefresh = false;
        L.w(message);
        Gson gson = new Gson();
        TagListVo vo = gson.fromJson(message, TagListVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<ArticleVo> datas = vo.getDatas();
//           clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContent.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }

            if (mArray != null && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContent.setPullLoadEnable(true);
                mXfvContent.setLoadComplete(false);
            } else {
                mXfvContent.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            mXfvContent.setLoadComplete(false);
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(status.getMessage());
        }
    }

    @Override
    public void ArticleTagMoreConError(String con) {
        isRefresh = false;
        mXfvContent.setLoadComplete(false);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }
}
