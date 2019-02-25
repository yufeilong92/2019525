package com.xuechuan.xcedu.ui.bank.newbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.GmExamOldAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.presenter.ExamPresenter;
import com.xuechuan.xcedu.mvp.view.ExamView;
import com.xuechuan.xcedu.sqlitedb.QuestionChapterSqliteHelp;
import com.xuechuan.xcedu.ui.bank.newBankActivity.CaseExamtActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.MockExamActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ExamChapterVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionChapterSqliteVo;

import java.util.ArrayList;
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
public class GmMockTestActivity extends BaseActivity implements ExamView {
    private ExamPresenter mPresenter;
    /**
     * 问题id
     */
    private static String MCOURSEID = "mcourseid";
    /**
     * 类型
     */
    private static String MARKTYPE = "type";

    private String mCourseId;

    private Context mContext;
    private String mExtraType;
    private AlertDialog mDialog;
    private ImageView mIvBMore;
    private RecyclerView mRlvGmOldExam;
    private RecyclerView mRlvGmDuExam;

    /**
     * @param context
     * @param mCourseId 科目id
     * @return
     */
    public static Intent newInstance(Context context, String mCourseId) {
        Intent intent = new Intent(context, GmMockTestActivity.class);
        intent.putExtra(MCOURSEID, mCourseId);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmmock_test);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gmmock_test);
        if (getIntent() != null) {
            mCourseId = getIntent().getStringExtra(MCOURSEID);
        }
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
      /*  mIvBMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = CaseExamtActivity.start_Intent(mContext, DataMessageVo.COURESID_CASE);
                startActivity(intent3);
            }
        });*/
    }

    private void initData() {
//        mPresenter = new ExamPresenter(new ExamModelImpl(), this);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        QuestionChapterSqliteHelp help = QuestionChapterSqliteHelp.get_Instance(mContext);
        List<QuestionChapterSqliteVo> mYearlist = help.queryCalendarYearList(mCourseId);
        List<QuestionChapterSqliteVo> mExclusivelist = help.queryExclusiveList(mCourseId);
        if (mYearlist != null) {
            bindAdapterData(mYearlist);
        }
        if (mExclusivelist != null) {
            bindAdapterSecrtData(mExclusivelist);
        }
        dismissDialog(mDialog);
//        mPresenter.requestExamContent(this, mOid);
    }

    @Override
    public void ExamSuccess(String con) {
        L.e("真题/迷题1" + con);
        Gson gson = new Gson();
        ExamChapterVo vo = gson.fromJson(con, ExamChapterVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (mDialog != null) {
                mDialog.dismiss();
            }

        } else {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            L.e(vo.getStatus().getMessage());
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    /**
     * 独家密卷
     *
     * @param secretexam
     */
    private void bindAdapterSecrtData(List<QuestionChapterSqliteVo> secretexam) {
        setGridLayoutManger(mContext, mRlvGmDuExam, 1);
        GmExamOldAdapter adapter = new GmExamOldAdapter(mContext, secretexam);
        mRlvGmDuExam.setAdapter(adapter);
        adapter.setClickListener(new GmExamOldAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                QuestionChapterSqliteVo vo = (QuestionChapterSqliteVo) obj;
                if (mCourseId.equals(DataMessageVo.COURESID_CASE)) {
                    Intent intent = CaseExamtActivity.start_Intent(
                            mContext, mCourseId, String.valueOf(vo.getQuestionchapterid()), 0);
                    startActivity(intent);
                } else {
                    Intent intent = MockExamActivity.start_Intent(
                            mContext, mCourseId, String.valueOf(vo.getQuestionchapterid()), 0);
                    startActivity(intent);
                }
            }

        });

    }

    /**
     * 历年真题
     *
     * @param exam
     */
    private void bindAdapterData(List<QuestionChapterSqliteVo> exam) {
        setGridLayoutManger(mContext, mRlvGmOldExam, 1);
        GmExamOldAdapter adapter = new GmExamOldAdapter(mContext, exam);
        mRlvGmOldExam.setAdapter(adapter);
        adapter.setClickListener(new GmExamOldAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                QuestionChapterSqliteVo vo = (QuestionChapterSqliteVo) obj;
                if (mCourseId.equals(DataMessageVo.COURESID_CASE)) {
                    Intent intent = CaseExamtActivity.start_Intent(
                            mContext, mCourseId, String.valueOf(vo.getQuestionchapterid()), 0);
                    startActivity(intent);
                } else {
                    Intent intent = MockExamActivity.start_Intent(
                            mContext, mCourseId, String.valueOf(vo.getQuestionchapterid()), 0);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void ExamError(String con) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    private void initView() {
        mContext = this;
        mIvBMore = (ImageView) findViewById(R.id.iv_b_more);
        mRlvGmOldExam = (RecyclerView) findViewById(R.id.rlv_gm_old_exam);
        mRlvGmDuExam = (RecyclerView) findViewById(R.id.rlv_gm_du_exam);
    }
}
