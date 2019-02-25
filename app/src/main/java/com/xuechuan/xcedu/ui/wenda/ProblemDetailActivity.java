package com.xuechuan.xcedu.ui.wenda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.wenda.ProblemAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.contract.WenDaDetailContract;
import com.xuechuan.xcedu.mvp.model.WenDaDetailModel;
import com.xuechuan.xcedu.mvp.presenter.WenDaDetailPresenter;
import com.xuechuan.xcedu.utils.DialogBgUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.ShareUtils;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ProblemDetailVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ProblemDetailActivity
 * @Package com.xuechuan.xcedu.ui.wenda
 * @Description: 问题详情
 * @author: L-BackPacker
 * @date: 2019.01.11 下午 2:23
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.11
 */
public class ProblemDetailActivity extends BaseActivity implements WenDaDetailContract.View, View.OnClickListener {

    private ImageView mIvTitleMore;
    private RecyclerView mRlvProblemDetailContent;
    private WenDaDetailPresenter mPresenter;
    private Context mContext;
    private static String TAG = "【" + ProblemDetailActivity.class + "】==";
    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.wendaProblemDetailActivity.paramt_key";
    private static String PARAMT1_KEY = "com.xuechuan.xcedu.ui.wendaProblemDetailActivity.paramt_key1";
    private static String PARAMT2_KEY = "com.xuechuan.xcedu.ui.wendaProblemDetailActivity.paramt_key2";
    private int mProblemId;
    private AlertDialog mShowDialog;
    private boolean mFinish;
    private AlertDialog mSubimtDialog;
    private CommonPopupWindow mPopShare;
    private LinearLayout mProblemDetailRoot;
    private String mTitle;
    private String mShareUrl = "https://www.baidu.com/";

    public static Intent start_Intent(Context context, int problemid, boolean finish, String title) {
        Intent intent = new Intent(context, ProblemDetailActivity.class);
        intent.putExtra(PARAMT_KEY, problemid);
        intent.putExtra(PARAMT1_KEY, finish);
        intent.putExtra(PARAMT2_KEY, title);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_detail);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_problem_detail);
        if (getIntent() != null) {
            mProblemId = getIntent().getIntExtra(PARAMT_KEY, 0);
            mFinish = getIntent().getBooleanExtra(PARAMT1_KEY, false);
            mTitle = getIntent().getStringExtra(PARAMT2_KEY);
        }
        initView();
//        mIvTitleMore.setVisibility(mFinish ? View.VISIBLE : View.GONE);
        mIvTitleMore.setVisibility(View.GONE);
        initData();
        requestDeatail();
    }

    private void requestDeatail() {
        mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestPreblemDetail(mContext, mProblemId);
    }


    private void initData() {
        mPresenter = new WenDaDetailPresenter();
        mPresenter.initModelView(new WenDaDetailModel(), this);

    }

    private void initView() {
        mContext = this;
        mIvTitleMore = (ImageView) findViewById(R.id.iv_title_more);
        mIvTitleMore.setOnClickListener(this);
        mRlvProblemDetailContent = (RecyclerView) findViewById(R.id.rlv_problem_detail_content);
        mProblemDetailRoot = (LinearLayout) findViewById(R.id.problem_detail_root);
    }

    @Override
    public void ProblemDetailSuccess(String success) {
        dismissDialog(mShowDialog);
        ProblemDetailVo vo = Utils.getGosnT(success, ProblemDetailVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ProblemDetailVo.DatasBean> datas = vo.getDatas();
            initAdapter(datas);
        } else {
            T_ERROR(mContext);
        }

    }

    private void initAdapter(List<ProblemDetailVo.DatasBean> datas) {
        ProblemAdapter adapter = new ProblemAdapter(mContext, datas);
        adapter.finishStatus(mFinish);
        setGridLayoutManger(mContext, mRlvProblemDetailContent, 1);
        mRlvProblemDetailContent.setAdapter(adapter);
        adapter.setOnSolveClickListener(new ProblemAdapter.OnSolveClickListener() {//已解决
            @Override
            public void onClickSolve(final ProblemDetailVo.DatasBean vo) {
                DialogUtil.showDialogEvalues(mContext, true, new DialogUtil.EvaluesInterface() {
                    @Override
                    public void result(int start, String comment) {
                        mSubimtDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
                        mPresenter.submitProblemEvaluet(mContext, vo.getProblemid(), comment, start);
                    }
                });
            }
        });

    }

    @Override
    public void ProblemDetailError(String msg) {
        dismissDialog(mShowDialog);
        T_ERROR(mContext);
    }

    @Override
    public void ProblemEvalueSuccess(String success) {
        dismissDialog(mSubimtDialog);
        ResultVo vo = Utils.getGosnT(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                T.showToast(mContext, getStringWithId(R.string.submit_success));
                this.finish();
            } else {
                T_ERROR(mContext);
            }

        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void ProblemEvalueError(String msg) {
        dismissDialog(mSubimtDialog);
        T_ERROR(mContext);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!mFinish) {
            if (mPresenter != null)
                mPresenter.requestPreblemDetail(mContext, mProblemId);
        }
    }

    /**
     * 分享布局
     */
    private void showShareLayout() {
        //                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.QQ);
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.QZONE);
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.SINA);
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.WEIXIN);
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.WEIXIN_CIRCLE);
        mPopShare = new CommonPopupWindow(this, R.layout.pop_item_share, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private TextView qqzon;
            private TextView qq;
            private TextView weibo;
            private TextView circle;
            private TextView weixin;

            @Override
            protected void initView() {
                View view = getContentView();
                weixin = (TextView) view.findViewById(R.id.tv_pop_weixin_share);
                circle = (TextView) view.findViewById(R.id.tv_pop_crile_share);
                weibo = (TextView) view.findViewById(R.id.tv_pop_weibo_share);
                qq = (TextView) view.findViewById(R.id.tv_pop_qq_share);
                qqzon = (TextView) view.findViewById(R.id.tv_pop_qqzon_share);
            }

            @Override
            protected void initEvent() {
                qq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(ProblemDetailActivity.this, mShareUrl, mTitle
                                , "", "", R.mipmap.appicon
                                , SHARE_MEDIA.QQ);
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.QQ);
                        getPopupWindow().dismiss();
                    }
                });
                qqzon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(ProblemDetailActivity.this, mShareUrl, mTitle
                                , "", "", R.mipmap.appicon, SHARE_MEDIA.QZONE
                        );
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.QZONE);
                        getPopupWindow().dismiss();
                    }
                });
                weibo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(ProblemDetailActivity.this, mShareUrl, mTitle
                                , "", "", R.mipmap.appicon
                                , SHARE_MEDIA.SINA
                        );
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.SINA);
                        getPopupWindow().dismiss();
                    }
                });
                weixin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(ProblemDetailActivity.this, mShareUrl, mTitle
                                , "", "", R.mipmap.appicon, SHARE_MEDIA.WEIXIN
                        );
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.WEIXIN);
                        getPopupWindow().dismiss();
                    }
                });
                circle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mSloViewShow));
                        ShareUtils.shareWeb(ProblemDetailActivity.this, mShareUrl, mTitle
                                , "", "", R.mipmap.appicon, SHARE_MEDIA.WEIXIN_CIRCLE
                        );
//                        ShareUtils.shareImg(AgreementActivity.this, mResultData.getQuestion(),
//                                pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        getPopupWindow().dismiss();
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
                        DialogBgUtil.setBackgroundAlpha(1f, ProblemDetailActivity.this);
                    }
                });
            }
        };
        mPopShare.showAtLocation(mProblemDetailRoot, Gravity.BOTTOM, 0, 0);
        DialogBgUtil.setBackgroundAlpha(0.5f, ProblemDetailActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_more://分享
                showShareLayout();
                break;

        }
    }
}
