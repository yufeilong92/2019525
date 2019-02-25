package com.xuechuan.xcedu.fragment.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.mvp.model.CaseModelImpl;
import com.xuechuan.xcedu.mvp.presenter.CasePresenter;
import com.xuechuan.xcedu.mvp.view.CaseView;
import com.xuechuan.xcedu.ui.bank.AtricleListActivity;
import com.xuechuan.xcedu.ui.bank.newbank.MockTestActivity;
import com.xuechuan.xcedu.ui.bank.MyErrorOrCollectTextActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.BuyVo;
import com.xuechuan.xcedu.vo.ErrorOrColloctVo;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: CaseFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 案例分析
 * @author: YFL
 * @date: 2018/4/24  23:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/24   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class CaseFragment extends BaseFragment implements CaseView, View.OnClickListener {
    private static final String TYPEOID = "typeoid";

    private String mTypeOid;
    private TextView mTvCaseWroing;
    private LinearLayout mLiBCaseError;
    private TextView mTvCaseCoolect;
    private LinearLayout mLlBCaseCollect;
    private ImageView mIvBCaseOrder;
    private ImageView mIvBCaseText;
    private TextView mTvBCaseFree;
    private TextView mTvBCaseZhuanxiang;
    private TextView mTvBCaseShunxu;
    private Context mContext;
    private CasePresenter mCasePresenter;
    private ErrorOrColloctVo.DataBean mData;


    public CaseFragment() {
    }

    public static CaseFragment newInstance(String id) {
        CaseFragment fragment = new CaseFragment();
        Bundle args = new Bundle();
        args.putString(TYPEOID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTypeOid = getArguments().getString(TYPEOID);
        }
    }


 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case, container, false);
        initView(view);
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_case;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initData() {
        mCasePresenter = new CasePresenter(new CaseModelImpl(), this);
//        mCasePresenter.getErrOrCollNumber(mContext, mTypeOid);
//        mCasePresenter.requestBuyInfom(mContext, mTypeOid);
    }


    private void initView(View view) {
        mTvCaseWroing = (TextView) view.findViewById(R.id.tv_case_wroing);
        mLiBCaseError = (LinearLayout) view.findViewById(R.id.li_b_case_error);
        mTvCaseCoolect = (TextView) view.findViewById(R.id.tv_case_coolect);
        mLlBCaseCollect = (LinearLayout) view.findViewById(R.id.ll_b_case_collect);
        mIvBCaseOrder = (ImageView) view.findViewById(R.id.iv_b_case_order);
        mIvBCaseText = (ImageView) view.findViewById(R.id.iv_b_case_text);
        mTvBCaseFree = (TextView) view.findViewById(R.id.tv_b_case_free);
        mTvBCaseZhuanxiang = (TextView) view.findViewById(R.id.tv_b_case_zhuanxiang);
        mTvBCaseShunxu = (TextView) view.findViewById(R.id.tv_b_case_shunxu);
        mLiBCaseError.setOnClickListener(this);
        mLlBCaseCollect.setOnClickListener(this);
        mIvBCaseOrder.setOnClickListener(this);
        mIvBCaseText.setOnClickListener(this);
        mTvBCaseFree.setOnClickListener(this);
        mTvBCaseShunxu.setOnClickListener(this);
        mTvBCaseZhuanxiang.setOnClickListener(this);
        mContext = getActivity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_b_case_error://我的错题
                if (mData != null) {
                    Intent intent1 = MyErrorOrCollectTextActivity.newInstance(mContext, mTypeOid,
                            MyErrorOrCollectTextActivity.ERRTYPE, String.valueOf(mData.getError()));
                    intent1.putExtra(MyErrorOrCollectTextActivity.CSTR_EXTRA_TITLE_STR, "我的错题");
                    startActivity(intent1);
                } else {
                    showLoading();
                }
                break;
            case R.id.ll_b_case_collect://我的收藏
                if (mData != null) {
                    Intent intent2 =
                            MyErrorOrCollectTextActivity.newInstance(mContext, mTypeOid,
                                    MyErrorOrCollectTextActivity.FAVTYPE, String.valueOf(mData.getFavorite()));
                    intent2.putExtra(MyErrorOrCollectTextActivity.CSTR_EXTRA_TITLE_STR, "我的收藏");
                    startActivity(intent2);
                } else {
                    showLoading();
                }
                break;
            case R.id.iv_b_case_order://章节
                Intent intent = AtricleListActivity.newInstance(mContext, mTypeOid);
                intent.putExtra(AtricleListActivity.CSTR_EXTRA_TITLE_STR, "章节练习");
                startActivity(intent);
                break;
            case R.id.iv_b_case_text://考试
                Intent intent3 = MockTestActivity.newInstance(mContext, mTypeOid, DataMessageVo.MARKTYPECASE);
                intent3.putExtra(MockTestActivity.CSTR_EXTRA_TITLE_STR, "模拟考试");
                startActivity(intent3);
                break;
            case R.id.tv_b_case_free://自由
                showToast();
//                Intent intent6 = FreeQuestionActivity.newInstance(mContext, mTypeOid);
//                intent6.putExtra(FreeQuestionActivity.CSTR_EXTRA_TITLE_STR, "自由组卷");
//                startActivity(intent6);
                break;
            case R.id.tv_b_case_zhuanxiang://专项
                showToast();
//                Intent intent4 = SpecialListActivity.newInstance(mContext, mTypeOid);
//                intent4.putExtra(SpecasListActivity.CSTR_EXTRA_TITLE_STR, "专项练习");
//                startActivity(intent4);
                break;
            case R.id.tv_b_case_shunxu://顺序
                showToast();
//                Intent intent5 = AnswerActivity.newInstance(mContext, mTypeOid);
//                intent5.putExtra(AnswerActivity.CSTR_EXTRA_TITLE_STR, "顺序练习");
//                startActivity(intent5);
                break;
            default:
                break;
        }
    }

    private void showToast() {
        T.showToast(mContext, "该科暂不提供");
    }

    private void showLoading() {
        if (!Utils.isNet(mContext)) {
            T_ERROR(mContext);
            return;
        }
        T.showToast(mContext, getString(R.string.loading_wait));
    }

    @Override
    public void ErrorOrCollortNumberSuccess(String con) {
        Gson gson = new Gson();
        ErrorOrColloctVo vo = gson.fromJson(con, ErrorOrColloctVo.class);
        if (vo.getStatus().getCode() == 200) {
            mData = vo.getData();
            bindErrOrColViewData(mData);
        } else {
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
        }

    }

    private void bindErrOrColViewData(ErrorOrColloctVo.DataBean data) {
        mTvCaseWroing.setText(data.getError() + "道");
        mTvCaseCoolect.setText(data.getFavorite() + "道");
    }

    @Override
    public void ErrorOrCollortNumberError(String con) {
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));

    }

    @Override
    public void BuySuccess(String con) {
        Gson gson = new Gson();
        BuyVo vo = gson.fromJson(con, BuyVo.class);
        if (vo.getStatus().getCode() == 200) {
            BuyVo.DataBean data = vo.getData();
  /*          boolean course3 = data.isCourse3();
            data.setIsbought(course3);
            data.setCourseid(3);*/
//            DbHelperAssist.getInstance(mContext).upDataBuyInfom(String.valueOf(data.getCourseid()), data.isIsbought());
            DbHelperAssist.getInstance(mContext).upDataBuyInfom("3", data.isCourse3());

        } else {
            L.e(con);
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void BuyError(String con) {
        L.e(con);
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isVisible) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCasePresenter.requestBuyInfom(mContext, mTypeOid);
                mCasePresenter.getErrOrCollNumber(mContext, mTypeOid);
            }
        }, 200);
    }

    private boolean isVisible = false;
    /**
     * 是否第一次请求
     */
    private boolean isRequestHttp = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            if (isRequestHttp) return;
            isRequestHttp = true;
            if (mCasePresenter == null) return;
            mCasePresenter.requestBuyInfom(mContext, mTypeOid);
            mCasePresenter.getErrOrCollNumber(mContext, mTypeOid);
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isRequestHttp = false;
    }
}
