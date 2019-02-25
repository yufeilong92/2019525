package com.xuechuan.xcedu.ui.bank.newbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.ExamOldAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.ExamModelImpl;
import com.xuechuan.xcedu.mvp.presenter.ExamPresenter;
import com.xuechuan.xcedu.mvp.view.ExamView;
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ExamBeanVo;
import com.xuechuan.xcedu.vo.ExamChapterVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MockTestActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 获取试卷（真题，独家密卷）
 * @author: L-BackPacker
 * @date: 2018/5/3 20:48
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/3
 */
public class MockTestActivity extends BaseActivity implements ExamView {
    private ExamPresenter mPresenter;

    /**
     * 问题id
     */
    private static String MQUESTIONOID = "mquestionoid";
    /**
     * 类型
     */
    private static String MARKTYPE = "type";

    private String mOid;
    private ImageView mIvBMore;
    private RecyclerView mRlvOldExam;
    private RecyclerView mRlvDuExam;
    private Context mContext;
    private String mExtraType;
    private AlertDialog mDialog;

    /**
     *
     * @param context
     * @param questionoid  问题id
     * @param type 类型
     * @return
     */
    public static Intent newInstance(Context context, String questionoid,String type) {
        Intent intent = new Intent(context, MockTestActivity.class);
        intent.putExtra(MQUESTIONOID, questionoid);
        intent.putExtra(MARKTYPE, type);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mock_test);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(MQUESTIONOID);
           //类型
            mExtraType = getIntent().getStringExtra(MARKTYPE);
        }
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new ExamPresenter(new ExamModelImpl(), this);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestExamContent(this, mOid);
    }

    @Override
    public void ExamSuccess(String con) {
        L.e("真题/迷题1" + con);
        Gson gson = new Gson();
        ExamChapterVo vo = gson.fromJson(con, ExamChapterVo.class);
        if (vo.getStatus().getCode() == 200) {
           if (mDialog!=null){
               mDialog.dismiss();
           }
            List<ExamBeanVo> exam = vo.getData().getExam();
            if (exam != null) {
                bindAdapterData(exam);
            }
            List<ExamBeanVo> secretexam = vo.getData().getSecretexam();
            if (secretexam != null) {
                bindAdapterSecrtData(secretexam);
            }


        } else {
            if (mDialog!=null){
                mDialog.dismiss();
            }
            L.e(vo.getStatus().getMessage());
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    /**
     * 独家密卷
     * @param secretexam
     */
    private void bindAdapterSecrtData(List<ExamBeanVo> secretexam) {
        setGridLayoutManger(mContext,mRlvDuExam,1);
        ExamOldAdapter adapter = new ExamOldAdapter(mContext, secretexam);
        mRlvDuExam.setAdapter(adapter);
        adapter.setClickListener(new ExamOldAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ExamBeanVo vo = (ExamBeanVo) obj;
                Intent intent = AnswerActivity.examInstance(mContext, String.valueOf(vo.getId()),
                        mExtraType);
                startActivity(intent);
            }
        });

    }

    /**
     * 历年真题
     * @param exam
     */
    private void bindAdapterData(List<ExamBeanVo> exam) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvOldExam.setLayoutManager(manager);
        ExamOldAdapter adapter = new ExamOldAdapter(mContext, exam);
        mRlvOldExam.setAdapter(adapter);
        adapter.setClickListener(new ExamOldAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ExamBeanVo vo = (ExamBeanVo) obj;
                Intent intent = AnswerActivity.examInstance(mContext, String.valueOf(vo.getId())
                ,mExtraType);
                startActivity(intent);
            }
        });
    }

    @Override
    public void ExamError(String con) {
        if (mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
        }
        T.showToast(mContext,getStringWithId(R.string.net_error));
    }

    private void initView() {
        mContext = this;
        mIvBMore = (ImageView) findViewById(R.id.iv_b_more);
        mRlvOldExam = (RecyclerView) findViewById(R.id.rlv_old_exam);
        mRlvDuExam = (RecyclerView) findViewById(R.id.rlv_du_exam);
    }
}
