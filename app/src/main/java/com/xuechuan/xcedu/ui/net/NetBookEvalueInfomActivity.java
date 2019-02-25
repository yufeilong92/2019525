package com.xuechuan.xcedu.ui.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.xuechuan.xcedu.mvp.model.NetVideoEvalueInfomModelImple;
import com.xuechuan.xcedu.mvp.presenter.NetVideoEvalueInfomPresenter;
import com.xuechuan.xcedu.mvp.view.NetVideoEvalueInfomView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeSampUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.EvalueInfomVo;
import com.xuechuan.xcedu.vo.EvalueVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @Title:  NetBookEvalueInfomActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 网课评价页
 * @author: L-BackPacker
 * @date:   2018.10.16 下午 2:13
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.10.16
 */
public class NetBookEvalueInfomActivity extends BaseActivity implements View.OnClickListener, NetVideoEvalueInfomView {

    private RecyclerView mRlvInfomtwoContent;
    private XRefreshView mXfvContentTwoDetail;
    private EditText mEtInfomTwoContent;
    private Button mBtnInfomTwoSend;
    private TextView mTvEvalueEmpty;
    private RelativeLayout mRlInfomTwoLayout;
    private boolean isRefresh;
    private Context mContext;
    private List mArrary;
    private ImageView mIvEvaluateHear;
    private TextView mTvEvalueUserName;
    private TextView mTvEvalueContent;
    private TextView mTvEvalueTime;
    private TextView mTvEvalueEvalue;
    private CheckBox mChbEvaluaIssupper;
    private TextView mTvEvalueSuppernumber;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_book_evalue_infom);
        initView();
    }*/

    private static String EVAELUEINFOM = "evaelueinfom";
    private static String VIDEOID = "videoid";
    private EvalueVo.DatasBean mEvalueInfom;
    private EvalueTwoAdapter adapter;
    private long lastRefreshTime;
    private NetVideoEvalueInfomPresenter mPresenter;
    private String mVideoID;

    public static Intent newInstance(Context context, String videoid, EvalueVo.DatasBean evaelueinfom) {
        Intent intent = new Intent(context, NetBookEvalueInfomActivity.class);
        intent.putExtra(EVAELUEINFOM, (Serializable) evaelueinfom);
        intent.putExtra(VIDEOID, videoid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_book_evalue_infom);
        if (getIntent() != null) {
            mEvalueInfom = (EvalueVo.DatasBean) getIntent().getSerializableExtra(EVAELUEINFOM);
            mVideoID = getIntent().getStringExtra(VIDEOID);
        }
        initView();
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXfvContentTwoDetail.startRefresh();
    }

    private void initData() {
        mTvEvalueUserName.setText(mEvalueInfom.getNickname());
        if (mEvalueInfom.isIssupport()) {
            mTvEvalueSuppernumber.setText(mEvalueInfom.getSupportcount() + "");
        } else {
            mTvEvalueSuppernumber.setText("赞");
        }
        mChbEvaluaIssupper.setChecked(mEvalueInfom.isIssupport());
        mTvEvalueContent.setText(mEvalueInfom.getContent());
        String ymdt = TimeUtil.getYMDT(mEvalueInfom.getCreatetime());
        String stamp = TimeSampUtil.getStringTimeStamp(mEvalueInfom.getCreatetime());
        L.e(stamp);
        mTvEvalueTime.setText(stamp);
        if (!StringUtil.isEmpty(mEvalueInfom.getHeadicon())) {
            MyAppliction.getInstance().displayImages(mIvEvaluateHear, mEvalueInfom.getHeadicon(), true);
        }
        mTvEvalueEvalue.setText(mEvalueInfom.getCommentcount() + "");

        mPresenter = new NetVideoEvalueInfomPresenter(new NetVideoEvalueInfomModelImple(), this);

    }

    private void bindAdapterData() {
        setGridLayoutManger(mContext,mRlvInfomtwoContent,1);
        adapter = new EvalueTwoAdapter(mContext, mArrary);
        mRlvInfomtwoContent.setAdapter(adapter);

    }

    private void initXrfresh() {
        mXfvContentTwoDetail.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentTwoDetail.setPullLoadEnable(true);
        mXfvContentTwoDetail.setAutoLoadMore(true);
        mXfvContentTwoDetail.setPullRefreshEnable(true);
        mXfvContentTwoDetail.setEmptyView(mTvEvalueEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXfvContentTwoDetail.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        mPresenter.requestMoreVideoEvalue(mContext, mVideoID, String.valueOf(mEvalueInfom.getCommentid()), getNowPage() + 1);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestVideoEvalue(mContext, mVideoID, String.valueOf(mEvalueInfom.getCommentid()), 1);
    }

    private void initView() {
        mContext = this;
        mRlvInfomtwoContent = (RecyclerView) findViewById(R.id.rlv_infomtwo_content);
        mXfvContentTwoDetail = (XRefreshView) findViewById(R.id.xfv_content_two_detail);
        mEtInfomTwoContent = (EditText) findViewById(R.id.et_infom_two_content);
        mBtnInfomTwoSend = (Button) findViewById(R.id.btn_infom_two_send);
        mTvEvalueEmpty = (TextView) findViewById(R.id.tv_evalue_empty);
        mRlInfomTwoLayout = (RelativeLayout) findViewById(R.id.rl_infom_two_layout);

        mBtnInfomTwoSend.setOnClickListener(this);
        mIvEvaluateHear = (ImageView) findViewById(R.id.iv_evaluate_hear);
        mIvEvaluateHear.setOnClickListener(this);
        mTvEvalueUserName = (TextView) findViewById(R.id.tv_evalue_user_name);
        mTvEvalueUserName.setOnClickListener(this);
        mTvEvalueContent = (TextView) findViewById(R.id.tv_evalue_content);
        mTvEvalueContent.setOnClickListener(this);
        mTvEvalueTime = (TextView) findViewById(R.id.tv_evalue_time);
        mTvEvalueTime.setOnClickListener(this);
        mTvEvalueEvalue = (TextView) findViewById(R.id.tv_evalue_evalue);
        mTvEvalueEvalue.setOnClickListener(this);
        mChbEvaluaIssupper = (CheckBox) findViewById(R.id.chb_evalua_issupper);
        mChbEvaluaIssupper.setOnClickListener(this);
        mTvEvalueSuppernumber = (TextView) findViewById(R.id.tv_evalue_suppernumber);
        mTvEvalueSuppernumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_infom_two_send:
                String str = getTextStr(mEtInfomTwoContent);
                submit(str);
                break;
        }
    }

    private void submit(String str) {
        if (StringUtil.isEmpty(str)) {
            T.showToast(mContext, getStringWithId(R.string.content_is_empty));
            return;
        }
        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        UserBean user = userInfom.getData().getUser();
        EvalueVo.DatasBean bean = new EvalueVo.DatasBean();
        bean.setCommentcount(0);
        bean.setCommentid(mEvalueInfom.getCommentid());
        bean.setContent(str);
        bean.setHeadicon(user.getHeadicon());
        bean.setNickname(user.getNickname());
        bean.setTargetid(Integer.parseInt(mVideoID));
        bean.setSupportcount(0);
        List<EvalueVo.DatasBean> beans = new ArrayList<>();
        beans.add(bean);
        addListData(beans);
        adapter.notifyDataSetChanged();
        mEtInfomTwoContent.setText("");
        mPresenter.submitEvalue(mContext, mVideoID, str, String.valueOf(mEvalueInfom.getCommentid()));

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
    public void EvalueOneSuccess(String con) {
        mXfvContentTwoDetail.stopRefresh();
        isRefresh = false;
        L.e("视频评价" + con);
        Gson gson = new Gson();
        EvalueInfomVo vo = gson.fromJson(con, EvalueInfomVo.class);
        if (vo.getStatus().getCode() == 200) {//成功
            List list = vo.getDatas();
            clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentTwoDetail.setLoadComplete(false);
                mXfvContentTwoDetail.setPullLoadEnable(true);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mArrary.size()) {
                mXfvContentTwoDetail.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(vo.getStatus().getMessage());
            T.showToast(mContext, getStringWithId(R.string.net_error));
        }
    }

    @Override
    public void EvalueOneError(String rror) {
        isRefresh = false;
        mXfvContentTwoDetail.stopRefresh();
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    @Override
    public void EvalueMoreSuccess(String con) {
        isRefresh = false;
        L.e("视频评价vodie" + con);
        L.e(getNowPage() + "集合长度" + mArrary.size());
        Gson gson = new Gson();
        EvalueInfomVo vo = gson.fromJson(con, EvalueInfomVo.class);
        if (vo.getStatus().getCode() == 200) {//成功
            List list = vo.getDatas();
//                    clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentTwoDetail.setLoadComplete(false);
                mXfvContentTwoDetail.setPullLoadEnable(true);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mArrary.size()) {
                mXfvContentTwoDetail.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXfvContentTwoDetail.setLoadComplete(false);
            T.showToast(mContext, getStringWithId(R.string.net_error));
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void EvalueMoreError(String rror) {
        isRefresh = false;
        mXfvContentTwoDetail.setLoadComplete(false);
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    @Override
    public void SubmitEvalueSuccess(String con) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            T.showToast(mContext, "评价成功");
        } else {
            L.e(vo.getData().getMessage());
            T.showToast(mContext, getStringWithId(R.string.net_error));
        }
    }

    @Override
    public void SubmitEvalueError(String con) {
        L.e(con);
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

}
