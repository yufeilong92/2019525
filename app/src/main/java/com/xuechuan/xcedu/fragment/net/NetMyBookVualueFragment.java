package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.event.VideoIdEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.net.NetMyBookEvaleAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.NetVideoEvalueModelImple;
import com.xuechuan.xcedu.mvp.presenter.NetVideoEvaluePresenter;
import com.xuechuan.xcedu.mvp.view.NetVideoEvalueView;
import com.xuechuan.xcedu.ui.EvalueTwoActivity;
import com.xuechuan.xcedu.utils.DialogBgUtil;
import com.xuechuan.xcedu.utils.KeyboardUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.EvalueNewVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetMyBookVualueFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description 我的课程评价
 * @author: L-BackPacker
 * @date: 2018/5/16 14:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/16
 */
public class NetMyBookVualueFragment extends BaseFragment implements View.OnClickListener, NetVideoEvalueView {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView mRlvSpecaContent;
    private XRefreshView mXrfvSpecaRefresh;
    private boolean isRefresh;
    private Context mContext;
    private List mArrary;
    private TextView mTvNetEmptyContent;
    private long lastRefreshTime;
    private NetVideoEvaluePresenter mPresenter;
    private String mVideoId;
    private TextView mEtNetBookEvalue;
    private ImageView mIvNetBookSend;
    private NetMyBookEvaleAdapter adapter;
    private CommonPopupWindow inputEvaluePop;
    private RelativeLayout mRlRootMybook;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    public NetMyBookVualueFragment() {
    }

    public static NetMyBookVualueFragment newInstance(String param1, String param2) {
        NetMyBookVualueFragment fragment = new NetMyBookVualueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_my_book_vualue, container, false);
        initView(view);
        return view;
    }*/


    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_my_book_vualue;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        if (getArguments() != null) {
            mVideoId = getArguments().getString(ARG_PARAM1);
        }
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        loadNewData();
    }



    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MainVideoEvale(VideoIdEvent event) {
        mVideoId = event.getVideoId();
        loadNewData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        mPresenter = new NetVideoEvaluePresenter(new NetVideoEvalueModelImple(), this);
    }


    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
//        mPresenter.requestVideoEvalue(mContext, mVideoId, 1);
        mPresenter.requestVideoEvalueNew(mContext, 1, DataMessageVo.VIDEO, mVideoId);

    }

    private void bindAdapterData() {
       setGridLayoutManger(mContext,mRlvSpecaContent,1);
        adapter = new NetMyBookEvaleAdapter(mContext, mArrary);
        mRlvSpecaContent.setAdapter(adapter);
        adapter.setClickListener(new NetMyBookEvaleAdapter.onItemClickListener() {
            @Override
            public void onClickListener(EvalueNewVo.DatasBean vo, int position) {
                Intent intent = EvalueTwoActivity.newInstance(mContext, String.valueOf(vo.getTargetid()),
                        String.valueOf(vo.getId()),
                        DataMessageVo.USERTYPEVC,
                        DataMessageVo.VIDEO);
                startActivity(intent);
            }
        });
   /*     adapter.setChbClickListener(new NetMyBookEvaleAdapter.onItemChbClickListener() {
            @Override
            public void onClickChbListener(EvalueVo.DatasBean bean, boolean isChick, int position) {
                EvalueVo.DatasBean vo = (EvalueVo.DatasBean) mArrary.get(position);
                SuppertUtil suppertUtil = SuppertUtil.getInstance(mContext);
                vo.setIssupport(isChick);
                if (isChick) {
                    vo.setSupportcount(vo.getSupportcount() + 1);
                    suppertUtil.submitSupport(String.valueOf(bean.getTargetid()), "true", DataMessageVo.USERTYPEVC);
                    adapter.notifyDataSetChanged();
                } else {
                    vo.setSupportcount(vo.getSupportcount() - 1);
                    suppertUtil.submitSupport(String.valueOf(bean.getTargetid()), "false", DataMessageVo.USERTYPEVC);
                    adapter.notifyDataSetChanged();
                }
            }
        });*/
    }

    private void initXrfresh() {
        mXrfvSpecaRefresh.restoreLastRefreshTime(lastRefreshTime);
        mXrfvSpecaRefresh.setPullLoadEnable(true);
        mXrfvSpecaRefresh.setAutoLoadMore(true);
        mXrfvSpecaRefresh.setPullRefreshEnable(false);
        mXrfvSpecaRefresh.setEmptyView(mTvNetEmptyContent);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXrfvSpecaRefresh.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        mPresenter.requestVideoEvalueNewMore(mContext, getNowPage() + 1, DataMessageVo.VIDEO, mVideoId);
//        mPresenter.requestMoreVideoEvalue(mContext, mVideoId, getNowPage() + 1);
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvSpecaContent = (RecyclerView) view.findViewById(R.id.rlv_speca_content);
        mXrfvSpecaRefresh = (XRefreshView) view.findViewById(R.id.xrfv_speca_refresh);
        mTvNetEmptyContent = (TextView) view.findViewById(R.id.tv_net_empty_content);
        mIvNetBookSend = (ImageView) view.findViewById(R.id.iv_net_book_send);
        mIvNetBookSend.setOnClickListener(this);
        mTvNetEmptyContent.setOnClickListener(this);
        mEtNetBookEvalue = (TextView) view.findViewById(R.id.et_net_book_evalue);
        mEtNetBookEvalue.setOnClickListener(this);
        mRlRootMybook = (RelativeLayout) view.findViewById(R.id.rl_root_mybook);
        mRlRootMybook.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_net_book_evalue://发送
                if (StringUtil.isEmpty(mVideoId) || mVideoId.equals("-1")) {
                    T.showToast(mContext, "请选择播放视频，再来评价");
                    return;
                }
                showAnswerCardResultLayout();
                break;
            default:

        }
    }

    private void submitEvalue(String str) {
        if (StringUtil.isEmpty(mVideoId) || mVideoId.equals("-1")) {
            T.showToast(mContext, "请选择播放视频，再来评价");
            return;
        }
        if (StringUtil.isEmpty(str)) {
            T.showToast(mContext, getStrWithId(mContext, R.string.content_is_empty));
            return;
        }
        mPresenter.submitEvalue(mContext, mVideoId, str, null);
    }

    @Override
    public void EvalueOneSuccess(String con) {
     /*   mXrfvSpecaRefresh.stopRefresh();
        isRefresh = false;
        L.e("视频评价" + con);
        Gson gson = new Gson();
        EvalueNewVo vo = gson.fromJson(con, EvalueNewVo.class);
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
            if(!mArrary.isEmpty()&&mArrary.size()%DataMessageVo.CINT_PANGE_SIZE==0) {
                // 设置是否可以上拉加载
                mXrfvSpecaRefresh.setPullLoadEnable(true);
                mXrfvSpecaRefresh.setLoadComplete(false);
            }
            else
                mXrfvSpecaRefresh.setLoadComplete(true);
//            mXrfvSpecaRefresh.setPullLoadEnable(true);
//            mXrfvSpecaRefresh.setLoadComplete(false);
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(vo.getStatus().getMessage());
            T.showToast(mContext, getStrWithId(R.string.net_error));
        }
*/
    }

    @Override
    public void EvalueOneError(String rror) {
        mXrfvSpecaRefresh.stopRefresh();
        isRefresh = false;
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
    }

    @Override
    public void EvalueMoreSuccess(String con) {
   /*     isRefresh = false;
        L.e("视频评价vodie" + con);
        L.e(getNowPage() + "集合长度" + mArrary.size());
        Gson gson = new Gson();
        EvalueNewVo vo = gson.fromJson(con, EvalueNewVo.class);
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
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXrfvSpecaRefresh.setLoadComplete(false);
            L.e(vo.getStatus().getMessage());
            T.showToast(mContext, getStrWithId(R.string.net_error));
        }
        */
    }

    @Override
    public void EvalueMoreError(String rror) {
        mXrfvSpecaRefresh.setLoadComplete(false);
        isRefresh = false;
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
    }

    @Override
    public void SubmitEvalueSuccess(String con) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            T.showToast(mContext, getString(R.string.evelua_sucee));
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
        L.d("视频评价" + con);
    }

    @Override
    public void SubmitEvalueError(String con) {
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        L.e("视频评价" + con);
    }

    /**
     * 请求评价
     *
     * @param con
     */
    @Override
    public void RequestEvalueNewSuccess(String con) {
        mXrfvSpecaRefresh.stopRefresh();
        isRefresh = false;
        L.e("视频评价" + con);
        Gson gson = new Gson();
        EvalueNewVo vo = gson.fromJson(con, EvalueNewVo.class);
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
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXrfvSpecaRefresh.setPullLoadEnable(true);
                mXrfvSpecaRefresh.setLoadComplete(false);
            } else
                mXrfvSpecaRefresh.setLoadComplete(true);
            if (vo.getTotal().getTotal() == mArrary.size()) {
                mXrfvSpecaRefresh.setLoadComplete(true);
            }
//            mXrfvSpecaRefresh.setPullLoadEnable(true);
//            mXrfvSpecaRefresh.setLoadComplete(false);
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(vo.getStatus().getMessage());
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
        }

    }

    @Override
    public void RequestEvalueNewError(String rror) {
        mXrfvSpecaRefresh.stopRefresh();
        isRefresh = false;
        T_ERROR(mContext);
    }

    /**
     * 请求跟多评价
     *
     * @param con
     */
    @Override
    public void RequestEvalueMoreNewSuccess(String con) {
        isRefresh = false;
        L.e("视频评价vodie" + con);
        L.e(getNowPage() + "集合长度" + mArrary.size());
        Gson gson = new Gson();
        EvalueNewVo vo = gson.fromJson(con, EvalueNewVo.class);
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
            if (vo.getTotal().getTotal() == mArrary.size()) {
                mXrfvSpecaRefresh.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXrfvSpecaRefresh.setLoadComplete(false);
            L.e(vo.getStatus().getMessage());
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
        }

    }

    @Override
    public void RequestEvalueMoreNewError(String rror) {
        mXrfvSpecaRefresh.setLoadComplete(false);
        isRefresh = false;
        T_ERROR(mContext);
    }


    private void showAnswerCardResultLayout() {

        inputEvaluePop = new CommonPopupWindow(mContext, R.layout.item_input_layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private Button mBtnSubmit;
            private EditText mEtSubmitEvalue;

            @Override
            protected void initView() {
                View view = getContentView();
                mEtSubmitEvalue = view.findViewById(R.id.et_submit_evalue);
                mBtnSubmit = view.findViewById(R.id.btn_submit);
            }

            @Override
            protected void initEvent() {
                Utils.showSoftInputFromWindow(getActivity(), mEtSubmitEvalue);
                mBtnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitEvalue(getTextStr(mEtSubmitEvalue));
                        inputEvaluePop.getPopupWindow().dismiss();
                    }
                });
                KeyboardUtil keyboardUtil = KeyboardUtil.getInstance(mContext);
                keyboardUtil.setEditTextSendKey(mEtSubmitEvalue);
                keyboardUtil.setSendClickListener(new KeyboardUtil.onKeySendClickListener() {
                    @Override
                    public void onSendClickListener() {
                        submitEvalue(getTextStr(mEtSubmitEvalue));
                        inputEvaluePop.getPopupWindow().dismiss();
                    }
                });
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        DialogBgUtil.setBackgroundAlpha(1f, mContext);
                    }
                });
            }
        };
        inputEvaluePop.showAtLocation(mRlRootMybook, Gravity.BOTTOM, 0, 0);
        DialogBgUtil.setBackgroundAlpha(0.5f, mContext);
    }

}
