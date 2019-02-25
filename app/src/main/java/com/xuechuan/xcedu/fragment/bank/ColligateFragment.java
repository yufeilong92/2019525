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
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.mvp.model.ColoctModelImpl;
import com.xuechuan.xcedu.mvp.presenter.ColoctPresenter;
import com.xuechuan.xcedu.mvp.view.ColoctView;
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
 * @Title: ColligateFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 综合能力
 * @author: YFL
 * @date: 2018/4/24  23:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/24   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ColligateFragment extends BaseFragment implements View.OnClickListener, ColoctView {
    private static final String TYPEOID = "typeoid";

    private String mTypeOid;
    private TextView mTvBCooWroing;
    private LinearLayout mLlBCoError;
    private TextView mTvBCoCoolect;
    private LinearLayout mLlBCoCollect;
    private ImageView mIvBCoOrder;
    private ImageView mIvBCoText;
    private TextView mTvBCoFree;
    private TextView mTvBCoZhuanxiang;
    private TextView mTvBCoShunxu;
    private Context mContext;
    private ColoctPresenter colPresenter;
    private ErrorOrColloctVo.DataBean mData;

    public static ColligateFragment newInstance(String id) {
        ColligateFragment fragment = new ColligateFragment();
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
            View view = inflater.inflate(R.layout.fragment_colligate, container, false);
            initView(view);
            return view;
        }
    */
    @Override
    protected int initInflateView() {
        return R.layout.fragment_colligate;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initData() {
        colPresenter = new ColoctPresenter(new ColoctModelImpl(), this);
//        colPresenter.getErrOrCollNumber(mContext, mTypeOid);
//        colPresenter.requestBuyInfom(mContext,mTypeOid);

    }

    private void initView(View view) {
        mTvBCooWroing = (TextView) view.findViewById(R.id.tv_b_coo_wroing);
        mLlBCoError = (LinearLayout) view.findViewById(R.id.ll_b_co_error);
        mTvBCoCoolect = (TextView) view.findViewById(R.id.tv_b_co_coolect);
        mLlBCoCollect = (LinearLayout) view.findViewById(R.id.ll_b_co_collect);
        mIvBCoOrder = (ImageView) view.findViewById(R.id.iv_b_co_order);
        mIvBCoText = (ImageView) view.findViewById(R.id.iv_b_co_text);
        mTvBCoFree = (TextView) view.findViewById(R.id.tv_b_co_free);
        mTvBCoZhuanxiang = (TextView) view.findViewById(R.id.tv_b_co_zhuanxiang);
        mTvBCoShunxu = (TextView) view.findViewById(R.id.tv_b_co_shunxu);
        mLlBCoCollect.setOnClickListener(this);
        mLlBCoError.setOnClickListener(this);
        mIvBCoOrder.setOnClickListener(this);
        mIvBCoText.setOnClickListener(this);
        mTvBCoCoolect.setOnClickListener(this);
        mTvBCoFree.setOnClickListener(this);
        mTvBCoShunxu.setOnClickListener(this);
        mTvBCoZhuanxiang.setOnClickListener(this);
        mContext = getActivity();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_b_co_collect://我的收藏
                if (mData != null) {
                    Intent intent2 = MyErrorOrCollectTextActivity.newInstance(mContext, mTypeOid,
                            MyErrorOrCollectTextActivity.FAVTYPE, String.valueOf(mData.getError()));
                    intent2.putExtra(MyErrorOrCollectTextActivity.CSTR_EXTRA_TITLE_STR, "我的收藏");
                    startActivity(intent2);
                }else {
                    showLoading();
                }
                break;
            case R.id.ll_b_co_error://我的错误
                if (mData != null) {
                    Intent intent1 = MyErrorOrCollectTextActivity.newInstance(mContext, mTypeOid,
                            MyErrorOrCollectTextActivity.ERRTYPE, String.valueOf(mData.getFavorite()));
                    intent1.putExtra(MyErrorOrCollectTextActivity.CSTR_EXTRA_TITLE_STR, "我的错题");
                    startActivity(intent1);
                }else {
                    showLoading();
                }
                break;
            case R.id.iv_b_co_order://章节练习
                Intent intent = AtricleListActivity.newInstance(mContext, mTypeOid);
                intent.putExtra(AtricleListActivity.CSTR_EXTRA_TITLE_STR, "章节练习");
                startActivity(intent);
                break;
            case R.id.iv_b_co_text://模拟考试
                Intent intent3 = MockTestActivity.newInstance(mContext, mTypeOid, DataMessageVo.MARKTYPECOLLORT);
                intent3.putExtra(MockTestActivity.CSTR_EXTRA_TITLE_STR, "模拟考试");
                startActivity(intent3);

                break;
            case R.id.tv_b_co_free://自由 组卷
                Intent intent6 = FreeQuestionActivity.newInstance(mContext, mTypeOid);
                intent6.putExtra(FreeQuestionActivity.CSTR_EXTRA_TITLE_STR, "自由组卷");
                startActivity(intent6);
//                T.showToast(mContext,"该科暂不提供");
                break;
            case R.id.tv_b_co_shunxu://顺序练习
                Intent intent5 = AnswerActivity.newInstance(mContext, mTypeOid);
                intent5.putExtra(AnswerActivity.CSTR_EXTRA_TITLE_STR, "顺序练习");
                startActivity(intent5);
//                T.showToast(mContext,"该科暂不提供");
                break;
            case R.id.tv_b_co_zhuanxiang://专项练习
                Intent intent4 = SpecialListActivity.newInstance(mContext, mTypeOid);
                intent4.putExtra(SpecasListActivity.CSTR_EXTRA_TITLE_STR, "专项练习");
                startActivity(intent4);
//                T.showToast(mContext,"该科暂不提供");
                break;


            default:

        }
    }
    private void showLoading(){
        if (!Utils.isNet(mContext)){
            T_ERROR(mContext);
            return;
        }
        T.showToast(mContext,getString(R.string.loading_wait));
    }


    public boolean ifBuyBook() {
        UserInfomDb db = DbHelperAssist.getInstance(mContext).queryWithuuUserInfom();
        return db.getColligateBook();
    }

    public void buyBook() {

    }

    @Override
    public void ErrorOrCollortNumberSuccess(String con) {
        Gson gson = new Gson();
        ErrorOrColloctVo errorOrColloctVo = gson.fromJson(con, ErrorOrColloctVo.class);
        if (errorOrColloctVo.getStatus().getCode() == 200) {
            mData = errorOrColloctVo.getData();
            bindErrOrColViewData(mData);
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            T.showToast(mContext, errorOrColloctVo.getStatus().getMessage());
        }
    }

    private void bindErrOrColViewData(ErrorOrColloctVo.DataBean data) {
        mTvBCooWroing.setText(data.getError() + "道");
        mTvBCoCoolect.setText(data.getFavorite() + "道");

    }

    @Override
    public void ErrorOrCollortNumberError(String con) {
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void BuySuccess(String con) {
        Gson gson = new Gson();
        BuyVo vo = gson.fromJson(con, BuyVo.class);
        if (vo.getStatus().getCode() == 200) {
            BuyVo.DataBean data = vo.getData();
//            boolean course1 = data.isCourse2();
//            data.setCourseid(2);
//            data.setIsbought(course1);
//            DbHelperAssist.getInstance(mContext).upDataBuyInfom(String.valueOf(data.getCourseid()), data.isIsbought());
            DbHelperAssist.getInstance(mContext).upDataBuyInfom("2", data.isCourse2());

        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void BuyError(String con) {
        L.e(con);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
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
                colPresenter.requestBuyInfom(mContext, mTypeOid);
                colPresenter.getErrOrCollNumber(mContext, mTypeOid);
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
            isVisible=true;
            if (isRequestHttp)return;
            isRequestHttp=true;
            if (colPresenter==null)return;
            colPresenter.requestBuyInfom(mContext, mTypeOid);
            colPresenter.getErrOrCollNumber(mContext, mTypeOid);
        }else {
            isVisible=false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isRequestHttp=false;
    }
}
