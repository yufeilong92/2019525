package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.EvalueTwoAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.EvalueInterfaceContract;
import com.xuechuan.xcedu.mvp.model.EvalueInterfaceModel;
import com.xuechuan.xcedu.mvp.presenter.EvalueInterfacePresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.KeyboardUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.SuppertUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeSampUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.EvalueInfomDataVo;
import com.xuechuan.xcedu.vo.EvalueNewVo;
import com.xuechuan.xcedu.vo.EvalueVo;
import com.xuechuan.xcedu.vo.TargetcommentBeanVo;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: EvalueTwoActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 带输入的评论
 * @author: YFL
 * @date: 2018/5/3  22:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/3   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class EvalueTwoActivity extends BaseActivity implements View.OnClickListener, EvalueInterfaceContract.View {
    /**
     * 评价类型类型
     */
    private static final String TESTYPE = "type";
    private static final String SUBMITTYPE = "summittype";
    private RecyclerView mRlvInfomtwoContent;
    private XRefreshView mXfvContentTwoDetail;
    private EditText mEtInfomTwoContent;
    private Button mBtnInfomTwoSend;
    private RelativeLayout mRlInfomTwoLayout;
    private Context mContext;
    private AlertDialog mDialog;
    /**
     * 问题id
     */
    public static final String TARGETID = "questtion";
    /**
     * 评价id
     */
    public static final String COMMONID = "commonid";
    /**
     * 评价内容
     */
    public static final String EVALUEDATA = "evaluedata";
    /**
     * 刷新时间
     */
    public static long lastRefreshTime;


    private String mTargetid;
    private String mCommonid;

    private List mArray;
    private EvalueTwoAdapter adapter;
    boolean isRefresh;
    private ImageView mTvEvalueEmpty;
    private EvalueNewVo.DatasBean mData;
    private AlertDialog mDialog1;
    private String mType;
    private EvalueInterfacePresenter mInfomPresenter;
    private LinearLayout mLlEdLayout;
    private String mSubmitType;

    /***
     *
     * @param context
     * @param targetid 文章di
     * @param commonid id
     * @param type 点赞类型
     * @param submittype 提交类型
     * @return
     */
    public static Intent newInstance(Context context, String targetid, String commonid, String type, String submittype) {
        Intent intent = new Intent(context, EvalueTwoActivity.class);
        intent.putExtra(TARGETID, targetid);
        intent.putExtra(COMMONID, commonid);
        intent.putExtra(TESTYPE, type);
        intent.putExtra(SUBMITTYPE, submittype);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalue_two);
        initView();
        initData();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evalue_two);
        if (getIntent() != null) {
            mTargetid = getIntent().getStringExtra(TARGETID);
            mCommonid = getIntent().getStringExtra(COMMONID);
            mType = getIntent().getStringExtra(TESTYPE);
            mSubmitType = getIntent().getStringExtra(SUBMITTYPE);

        }
        initView();
        clearData();
        initData();
        initAdapter();
        initXflrView();
        mXfvContentTwoDetail.startRefresh();
    }

    ImageView mIvEvaluateHear;
    TextView mTvEvalueUserName;
    TextView mTvEvalueContent;
    TextView mTvEvalueTime;
    TextView mTvEvalueEvalue;
    CheckBox mChbEvaluaIssupper;
    LinearLayout mLiner;
    TextView mTvEvalueNumber;
    View line;

    private void initHearView(View view, final TargetcommentBeanVo bean, String nubmer) {
        mCommonid = String.valueOf(bean.getId());
        mTargetid = String.valueOf(bean.getTargetid());
        mLlEdLayout.setVisibility(View.VISIBLE);
        if (mIvEvaluateHear == null)
            mIvEvaluateHear = (ImageView) view.findViewById(R.id.iv_evaluate_hear);
        if (mTvEvalueUserName == null)
            mTvEvalueUserName = (TextView) view.findViewById(R.id.tv_evalue_user_name);
        if (mTvEvalueContent == null)
            mTvEvalueContent = (TextView) view.findViewById(R.id.tv_evalue_content);
        if (mTvEvalueTime == null)
            mTvEvalueTime = (TextView) view.findViewById(R.id.tv_evalue_time);
        if (mTvEvalueEvalue == null)
            mTvEvalueEvalue = (TextView) view.findViewById(R.id.tv_evalue_evalue);
        if (mChbEvaluaIssupper == null)
            mChbEvaluaIssupper = (CheckBox) view.findViewById(R.id.chb_evalua_issupper);
        if (mLiner == null)
            mLiner = (LinearLayout) view.findViewById(R.id.li_relevant_response);
        if (mTvEvalueNumber == null)
            mTvEvalueNumber = (TextView) view.findViewById(R.id.tv_evalue_number);
        if (line == null)
            line = (View) view.findViewById(R.id.v_line_hear);
        line.setVisibility(View.VISIBLE);
        mLiner.setVisibility(View.VISIBLE);
        mTvEvalueNumber.setText(nubmer);
        mTvEvalueUserName.setText(bean.getNickname());
        mChbEvaluaIssupper.setClickable(false);
        mChbEvaluaIssupper.setText(String.valueOf(bean.getSupportcount()) + "");
        mChbEvaluaIssupper.setChecked(bean.isIssupport());
        mTvEvalueContent.setText(bean.getContent());
        String ymdt = TimeUtil.getYMDT(bean.getCreatetime());
        String stamp = TimeSampUtil.getStringTimeStamp(bean.getCreatetime());
        L.e(stamp);
        mTvEvalueTime.setText(stamp);
        if (!StringUtil.isEmpty(bean.getHeadicon())) {
            MyAppliction.getInstance().displayImages(mIvEvaluateHear, bean.getHeadicon(), true);
        }
        mChbEvaluaIssupper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mChbEvaluaIssupper.isPressed()) return;
                SuppertUtil util = SuppertUtil.getInstance(mContext);
                int nubmer = Integer.parseInt(mChbEvaluaIssupper.getText().toString());
                if (isChecked) {
                    mChbEvaluaIssupper.setText((nubmer + 1) + "");
                    util.submitSupport(String.valueOf(bean.getId()), "true", DataMessageVo.USERTYPEA);
                } else {
                    mChbEvaluaIssupper.setText((nubmer - 1) + "");
                    util.submitSupport(String.valueOf(bean.getId()), "false", DataMessageVo.USERTYPEA);
                }
            }
        });
        mTvEvalueEvalue.setText(bean.getCommentcount() + "");
    }

    private void initXflrView() {
        mXfvContentTwoDetail.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentTwoDetail.setPullLoadEnable(true);
        mXfvContentTwoDetail.setAutoLoadMore(true);
        mXfvContentTwoDetail.setPullRefreshEnable(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXfvContentTwoDetail.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                requestData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }
        });
        adapter.setClickListener(new EvalueTwoAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {

            }
        });
    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
//        mInfomPresenter.requestEvalueTwoMore(mContext, getNowPage() + 1, mCommonid, mType);
        mInfomPresenter.requestCommentEvalueMoreTwo(mContext, getNowPage() + 1, mType, mCommonid);
    }

    private void requestData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
//        mInfomPresenter.requestEvalueTwo(mContext, 1, mCommonid, mType);
        mInfomPresenter.requestCommentEvalueTwo(mContext, 1, mType, mCommonid);
    }

    private void initAdapter() {
        adapter = new EvalueTwoAdapter(mContext, mArray);
        adapter.setTypte(mType);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvInfomtwoContent.setLayoutManager(gridLayoutManager);
        mRlvInfomtwoContent.setAdapter(adapter);
    }

    private void initData() {
        mInfomPresenter = new EvalueInterfacePresenter();
        mInfomPresenter.initModelView(new EvalueInterfaceModel(), this);
        KeyboardUtil keyboardUtil = KeyboardUtil.getInstance(mContext);
        keyboardUtil.setEditTextSendKey(mEtInfomTwoContent);
        keyboardUtil.setSendClickListener(new KeyboardUtil.onKeySendClickListener() {
            @Override
            public void onSendClickListener() {
                doSubmitEvalue();
            }
        });
    }

    private void initView() {
        mContext = this;
        mRlvInfomtwoContent = (RecyclerView) findViewById(R.id.rlv_infomtwo_content);
        mXfvContentTwoDetail = (XRefreshView) findViewById(R.id.xfv_content_two_detail);
        mEtInfomTwoContent = (EditText) findViewById(R.id.et_infom_two_content);
        mBtnInfomTwoSend = (Button) findViewById(R.id.btn_infom_two_send);
        mRlInfomTwoLayout = (RelativeLayout) findViewById(R.id.rl_infom_two_layout);

        mBtnInfomTwoSend.setOnClickListener(this);
        mTvEvalueEmpty = (ImageView) findViewById(R.id.tv_evalue_empty);
        mTvEvalueEmpty.setOnClickListener(this);
        mLlEdLayout = (LinearLayout) findViewById(R.id.ll_ed_layout);
        mLlEdLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_infom_two_send:
                doSubmitEvalue();
                break;
        }
    }

    private void doSubmitEvalue() {
        String str = getTextStr(mEtInfomTwoContent);
        if (StringUtil.isEmpty(str)) {
            T.showToast(mContext, getStringWithId(R.string.content_is_empty));
            return;
        }
        submitEvalut(str);
        mDialog1 = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        Utils.hideInputMethod(mContext, mEtInfomTwoContent);
    }

    private void submitEvalut(String str) {
        mEtInfomTwoContent.setText("");
        mInfomPresenter.SubmitContent(mContext, mTargetid, str, mCommonid, mSubmitType);
    }

    @Override
    public void submitEvalueSuccess(String con) {
        if (mDialog1 != null) {
            mDialog1.dismiss();
        }
        T.showToast(mContext, getString(R.string.evelua_sucee));
    }

    @Override
    public void submitEvalueError(String con) {
        if (mDialog1 != null) {
            mDialog1.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    View view;

    @Override
    public void EvalueCommentSucMore(String com) {
        mXfvContentTwoDetail.stopRefresh();
        isRefresh = false;
        Gson gson = new Gson();
        EvalueInfomDataVo vo = gson.fromJson(com, EvalueInfomDataVo.class);
        EvalueInfomDataVo.DataBean data = vo.getData();
        if (view == null)
            view = LayoutInflater.from(mContext).inflate(R.layout.item_two_evaluate_hear, null);
        initHearView(view, data.getTargetcomment(), String.valueOf(data.getTotalX().getTotal()));
        adapter.setHeaderView(view, mRlvInfomtwoContent);
        if (vo.getStatus().getCode() == 200) {
            List list = data.getCommentcomments();
            clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentTwoDetail.setLoadComplete(false);
                mXfvContentTwoDetail.setPullLoadEnable(true);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
            }
            if (vo.getData().getTotalX().getTotal()==mArray.size())
                mXfvContentTwoDetail.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, getStringWithId(R.string.net_error));
        }
    }

    @Override
    public void EvalueCommentErroMore(String com) {
        isRefresh = false;
        mXfvContentTwoDetail.stopRefresh();
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void EvalueCommentSucMoreTwo(String com) {
        isRefresh = false;
        Gson gson = new Gson();
        EvalueInfomDataVo vo = gson.fromJson(com, EvalueInfomDataVo.class);
        if (vo.getStatus().getCode() == 200) {//成功
            List list = vo.getData().getCommentcomments();
//                    clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentTwoDetail.setLoadComplete(false);
                mXfvContentTwoDetail.setPullLoadEnable(true);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
            }
            if (vo.getData().getTotalX().getTotal()==mArray.size())
                mXfvContentTwoDetail.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXfvContentTwoDetail.setLoadComplete(false);
            T.showToast(mContext, getStringWithId(R.string.net_error));
        }
    }

    @Override
    public void EvalueCommentErroMoreTwo(String com) {
        isRefresh = false;
        mXfvContentTwoDetail.setLoadComplete(false);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        L.e(com);
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


    @Override
    public void EvalueTwoSuc(String con) {
      /*  mXfvContentTwoDetail.stopRefresh();
        isRefresh = false;
        L.e(con);
        Gson gson = new Gson();
        EvalueInfomDataVo vo = gson.fromJson(con, EvalueInfomDataVo.class);
        EvalueInfomDataVo.DataBean data = vo.getData();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_two_evaluate_hear, null);
        initHearView(view, data.getTargetcomment(), String.valueOf(data.getTotalX().getTotal()));
        adapter.setHeaderView(view, mRlvInfomtwoContent);
        if (vo.getStatus().getCode() == 200) {
            List list = data.getCommentcomments();
            clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentTwoDetail.setLoadComplete(false);
                mXfvContentTwoDetail.setPullLoadEnable(true);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, getStringWithId(R.string.net_error));
        }*/
    }

    @Override
    public void EvalueTwoErro(String com) {
  /*      isRefresh = false;
        mXfvContentTwoDetail.stopRefresh();
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        L.e(com);*/
    }

    @Override
    public void EvalueTwoSucMore(String com) {
    /*    isRefresh = false;
        Gson gson = new Gson();
        EvalueInfomDataVo vo = gson.fromJson(com, EvalueInfomDataVo.class);
        if (vo.getStatus().getCode() == 200) {//成功
            List list = vo.getData().getCommentcomments();
//                    clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentTwoDetail.setLoadComplete(false);
                mXfvContentTwoDetail.setPullLoadEnable(true);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXfvContentTwoDetail.setLoadComplete(false);
            T.showToast(mContext, getStringWithId(R.string.net_error));
        }*/
    }

    @Override
    public void EvalueTwoErroMore(String com) {
        isRefresh = false;
        mXfvContentTwoDetail.setLoadComplete(false);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        L.e(com);
    }

}
