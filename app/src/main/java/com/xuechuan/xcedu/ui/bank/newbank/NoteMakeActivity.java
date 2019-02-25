package com.xuechuan.xcedu.ui.bank.newbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.contract.NoteSubmiteContract;
import com.xuechuan.xcedu.mvp.model.NoteSubmiteModel;
import com.xuechuan.xcedu.mvp.presenter.NoteSubmitePresenter;
import com.xuechuan.xcedu.ui.bank.newBankActivity.NoteLookActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.NoteSubmiteVo;
import com.xuechuan.xcedu.weight.ContentEditText;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NoteMakeActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 笔记编辑界面
 * @author: L-BackPacker
 * @date: 2019.01.02 下午 3:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.02
 */

public class NoteMakeActivity extends BaseActivity implements NoteSubmiteContract.View {

    private TextView mTvNoteSave;
    private TextView mTvMakeTitle;
    private LinearLayout mLlNoteMakeTitle;
    private ContentEditText mCetNoteMakeContent;
    private NoteSubmitePresenter mPresenter;
    private Context mContext;

    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.bankNoteMakeActivity.questionid";
    private static String PARAMT1_KEY = "com.xuechuan.xcedu.ui.bankNoteMakeActivity.content";
    private static String PARAMT2_KEY = "com.xuechuan.xcedu.ui.bankNoteMakeActivity.title";
    private static String PARAMT3_KEY = "com.xuechuan.xcedu.ui.bankNoteMakeActivity.note";
    private static String PARAMT4_KEY = "com.xuechuan.xcedu.ui.bankNoteMakeActivity.type";
    private static String PARAMT5_KEY = "com.xuechuan.xcedu.ui.bankNoteMakeActivity.questionIdtype";
    private static String PARAMT6_KEY = "com.xuechuan.xcedu.ui.bankNoteMakeActivity.courseid";
    private int mQuestionId;
    private String mContent;
    private String mQuesitonTitle;
    private String mQuesitonNote;
    private GmTextUtil mGmTextUtil;
    private TextView mTvNoteTesting;
    private AlertDialog dialog;
    private int mType;
    private int mNoteId;
    private int mCouresid;

    /**
     * @param context
     * @param questionid 问题编号
     * @param content    笔记内容
     * @param title      问题标题
     * @param note       笔记考点
     * @return
     */
    public static Intent start_Intent(Context context, int questionid, int noteid, int courseid,
                                      String content, String title, String note, int type) {
        Intent intent = new Intent(context, NoteMakeActivity.class);
        intent.putExtra(PARAMT_KEY, questionid);
        intent.putExtra(PARAMT1_KEY, content);
        intent.putExtra(PARAMT2_KEY, title);
        intent.putExtra(PARAMT3_KEY, note);
        intent.putExtra(PARAMT4_KEY, type);
        intent.putExtra(PARAMT5_KEY, noteid);
        intent.putExtra(PARAMT6_KEY, courseid);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_make);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_note_make);
        if (getIntent() != null) {
            mQuestionId = getIntent().getIntExtra(PARAMT_KEY, 0);
            mContent = getIntent().getStringExtra(PARAMT1_KEY);
            mQuesitonTitle = getIntent().getStringExtra(PARAMT2_KEY);
            mQuesitonNote = getIntent().getStringExtra(PARAMT3_KEY);
            mType = getIntent().getIntExtra(PARAMT4_KEY, 0);
            mNoteId = getIntent().getIntExtra(PARAMT5_KEY, 0);
            mCouresid = getIntent().getIntExtra(PARAMT6_KEY, 0);
        }
        initView();
        initUtils();
        initData();
        BindViewData();
    }

    private void initUtils() {
        mGmTextUtil = GmTextUtil.get_Instance(mContext);
    }

    private void BindViewData() {
        mTvMakeTitle.setText(mQuesitonTitle);
        mTvNoteTesting.setText(mQuesitonNote);
        mCetNoteMakeContent.setText(mContent);
        mTvNoteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mCetNoteMakeContent.getText().toString().trim();
                if (StringUtil.isEmpty(trim)) {
                    T.showToast(mContext, R.string.input_content);
                    return;
                }
                submitNote(trim);
            }
        });
        mLlNoteMakeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mType) {
                    case 1://题库跳转
                        finish();
                        break;
                    case 2://笔记跳转
                        Intent intent = NoteLookActivity.start_Intent(mContext, mQuestionId, mCouresid);
                        startActivity(intent);
                        break;
                    default:

                }

            }
        });
    }


    private void submitNote(String trim) {
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        int id=0;
        if (!StringUtil.isEmpty(mContent)){
            id=mNoteId;
        }
        mPresenter.submiteNote(mContext,id ,mQuestionId, trim);
    }

    private void initData() {
        mPresenter = new NoteSubmitePresenter();
        mPresenter.initModelView(new NoteSubmiteModel(), this);
    }

    private void initView() {
        mContext = this;
        mTvNoteSave = (TextView) findViewById(R.id.tv_note_save);
        mTvMakeTitle = (TextView) findViewById(R.id.tv_make_title);
        mLlNoteMakeTitle = (LinearLayout) findViewById(R.id.ll_note_make_title);
        mCetNoteMakeContent = (ContentEditText) findViewById(R.id.cet_note_make_content);
        mTvNoteTesting = (TextView) findViewById(R.id.tv_note_testing);
    }


    @Override
    public void SubmiteNoteSuccess(String success) {
        dismissDialog(dialog);
        NoteSubmiteVo submiteVo = Utils.getGosnT(success, NoteSubmiteVo.class);
        if (submiteVo.getStatus().getCode() == 200) {
            this.finish();
        } else {
            T_ERROR(mContext);
        }

    }

    @Override
    public void SubmiteNoteError(String msg) {
        dismissDialog(dialog);
        T_ERROR(mContext);
    }
}
