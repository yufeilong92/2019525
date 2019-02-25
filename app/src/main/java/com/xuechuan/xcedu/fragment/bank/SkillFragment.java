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
import com.xuechuan.xcedu.mvp.model.SkillModelImpl;
import com.xuechuan.xcedu.mvp.presenter.SkillPresenter;
import com.xuechuan.xcedu.mvp.view.SkillView;
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.ui.bank.AtricleListActivity;
import com.xuechuan.xcedu.ui.bank.FreeQuestionActivity;
import com.xuechuan.xcedu.ui.bank.newbank.MockTestActivity;
import com.xuechuan.xcedu.ui.bank.MyErrorOrCollectTextActivity;
import com.xuechuan.xcedu.ui.bank.SpecialListActivity;
import com.xuechuan.xcedu.ui.home.SpecasListActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.BuyVo;
import com.xuechuan.xcedu.vo.ErrorOrColloctVo;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: SkillFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 技术实务
 * @author: YFL
 * @date: 2018/4/24  22:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/24   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SkillFragment extends BaseFragment implements View.OnClickListener, SkillView {
    private static final String TYPEOID = "typeoid";
    private String mTypeOid;
    private TextView mTvSkillWroing;
    private LinearLayout mLlBSkillError;
    private TextView mTvSkillCoolect;
    private LinearLayout mLlBSkillCollect;
    private ImageView mIvBOrder;
    private ImageView mIvBTest;
    private TextView mTvBFree;
    private TextView mTvBSpecial;
    private TextView mTvBTurn;
    private Context mContext;
    private SkillPresenter mPresenter;
    private ErrorOrColloctVo.DataBean mData;

    public SkillFragment() {
    }

    public static SkillFragment newInstance(String id) {
        SkillFragment fragment = new SkillFragment();
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
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill, container, false);
        initView(view);
        L.e(++i+"");
        initData();
        return view;
    }
*/


    @Override
    protected int initInflateView() {
        return R.layout.fragment_skill;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initData() {
        mPresenter = new SkillPresenter(new SkillModelImpl(), this);
//        mPresenter.getErrOrCollNumber(mContext, mTypeOid);
//        mPresenter.requestBuyInfom(mContext, mTypeOid);

    }

    private void initView(View view) {
        mTvSkillWroing = (TextView) view.findViewById(R.id.tv_skill_wroing);
        mLlBSkillError = (LinearLayout) view.findViewById(R.id.ll_b_skill_error);
        mTvSkillCoolect = (TextView) view.findViewById(R.id.tv_skill_coolect);
        mLlBSkillCollect = (LinearLayout) view.findViewById(R.id.ll_b_skill_collect);
        mIvBOrder = (ImageView) view.findViewById(R.id.iv_b_order);
        mIvBTest = (ImageView) view.findViewById(R.id.iv_b_test);
        mTvBFree = (TextView) view.findViewById(R.id.tv_b_free);
        mTvBSpecial = (TextView) view.findViewById(R.id.tv_b_special);
        mTvBTurn = (TextView) view.findViewById(R.id.tv_b_turn);
        mTvBTurn.setOnClickListener(this);
        mIvBOrder.setOnClickListener(this);
        mLlBSkillError.setOnClickListener(this);
        mTvSkillCoolect.setOnClickListener(this);
        mLlBSkillCollect.setOnClickListener(this);
        mIvBTest.setOnClickListener(this);
        mTvBFree.setOnClickListener(this);
        mTvBSpecial.setOnClickListener(this);
        mContext = getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_b_skill_error://错题
                if (mData != null) {
                    Intent intent1 = MyErrorOrCollectTextActivity.newInstance(mContext, mTypeOid,
                            MyErrorOrCollectTextActivity.ERRTYPE, String.valueOf(mData.getError()));
                    intent1.putExtra(MyErrorOrCollectTextActivity.CSTR_EXTRA_TITLE_STR, "我的错题");
                    startActivity(intent1);
                } else {
                    showLoading();
                }
                break;
            case R.id.ll_b_skill_collect://收藏
                if (mData != null) {
                    Intent intent2 = MyErrorOrCollectTextActivity.newInstance(mContext, mTypeOid,
                            MyErrorOrCollectTextActivity.FAVTYPE, String.valueOf(mData.getFavorite()));
                    intent2.putExtra(MyErrorOrCollectTextActivity.CSTR_EXTRA_TITLE_STR, "我的收藏");
                    startActivity(intent2);
                } else {
                    showLoading();
                }
                break;
            case R.id.iv_b_order://章节
                Intent intent = AtricleListActivity.newInstance(mContext, mTypeOid);
                intent.putExtra(AtricleListActivity.CSTR_EXTRA_TITLE_STR, "章节练习");
                startActivity(intent);
                break;
            case R.id.iv_b_test://考试
                Intent intent3 = MockTestActivity.newInstance(mContext, mTypeOid, DataMessageVo.MARKTYPESKILL);
                intent3.putExtra(MockTestActivity.CSTR_EXTRA_TITLE_STR, "模拟考试");
                startActivity(intent3);
                break;
            case R.id.tv_b_free://自由
                Intent intent6 = FreeQuestionActivity.newInstance(mContext, mTypeOid);
                intent6.putExtra(FreeQuestionActivity.CSTR_EXTRA_TITLE_STR, "自由组卷");
                startActivity(intent6);
                break;
            case R.id.tv_b_special://专项
                Intent intent4 = SpecialListActivity.newInstance(mContext, mTypeOid);
                intent4.putExtra(SpecasListActivity.CSTR_EXTRA_TITLE_STR, "专项练习");
                startActivity(intent4);
                break;
            case R.id.tv_b_turn://顺序
                Intent intent5 = AnswerActivity.newInstance(mContext, mTypeOid);
                intent5.putExtra(AnswerActivity.CSTR_EXTRA_TITLE_STR, "顺序练习");
                startActivity(intent5);

                break;
            default:

        }
    }

    private void showLoading() {
        if (!Utils.isNet(mContext)){
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
            bindErrorOrCollortData(mData);
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    private void bindErrorOrCollortData(ErrorOrColloctVo.DataBean data) {
        mTvSkillWroing.setText(data.getError() + "道");
        mTvSkillCoolect.setText(data.getFavorite() + "道");

    }

    @Override
    public void ErrorOrCollortNumberError(String con) {
        L.e("yfl", "ErrorOrCollortNumberError: " + con);
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
                mPresenter.getErrOrCollNumber(mContext, mTypeOid);
                mPresenter.requestBuyInfom(mContext, mTypeOid);
            }
        }, 200);

    }

    @Override
    public void BuySuccess(String msg) {
        Gson gson = new Gson();
        BuyVo vo = gson.fromJson(msg, BuyVo.class);
        if (vo.getStatus().getCode() == 200) {
            BuyVo.DataBean data = vo.getData();
//            boolean course1 = data.isCourse1();
/*            data.setIsbought(course1);
            data.setCourseid(1);*/
//            DbHelperAssist.getInstance(mContext).upDataBuyInfom(String.valueOf(data.getCourseid()), data.isIsbought());
            DbHelperAssist.getInstance(mContext).upDataBuyInfom("1", data.isCourse1());

        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }


    @Override
    public void BuyError(String con) {
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        L.e(con);
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
            if (isRequestHttp)return;
            isRequestHttp=true;
            if (mPresenter == null) return;
            mPresenter.getErrOrCollNumber(mContext, mTypeOid);
            mPresenter.requestBuyInfom(mContext, mTypeOid);
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isRequestHttp=false;
    }
}
