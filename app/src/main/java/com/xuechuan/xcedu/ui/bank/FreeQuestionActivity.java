package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.multilevel.treelist.Node;
import com.xuechuan.xcedu.event.FreeDataEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.AllQuestionModelImpl;
import com.xuechuan.xcedu.mvp.presenter.AllQuestionPresenter;
import com.xuechuan.xcedu.mvp.presenter.QuestionListPresenter;
import com.xuechuan.xcedu.mvp.view.AllQuestionView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.QuestionAllVo;
import com.xuechuan.xcedu.vo.QuestionAllVoNew;
import com.xuechuan.xcedu.vo.QuestionsBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: FreeQuestionActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 自由组卷
 * @author: L-BackPacker
 * @date: 2018/5/5 19:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/5
 */
public class FreeQuestionActivity extends BaseActivity implements View.OnClickListener, AllQuestionView {

    private ImageView mIcPopResult;
    private RadioGroup mRgQuestionNumber;
    private RadioGroup mRgDifficultyGrade;
    private RecyclerView mRlvFreeTitle;
    private QuestionListPresenter mListPresenter;
    private Context mContext;
    /**
     * 科目id
     */
    private String mCourseid;
    //    数据接口
    private ArrayList<Node> mNodeLists;
    private Button mBtnCreateQuestion;
    /**
     * 自由组卷数量
     */
    private String mPaperNumber = null;
    /**
     * 自由组卷难度
     */
    private int mPaperGrader = -1;
    private final String TWENTY = "20";
    private final String FIFTY = "50";
    private final String HUNDRED = "100";
    private final int EASY = 1;//容易
    private final int MEDIUM = 2; //中等
    private final int DIFFICULTY = 3;//困难
    /**
     * 简单
     */
    private List<QuestionsBean> mEasy = null;
    /**
     * 中等
     */
    private List<QuestionsBean> mMedium = null;
    /**
     * 困难
     */
    private List<QuestionsBean> mDifficulty = null;
    /**
     * 简单 单选
     */
    private List<QuestionsBean> mEasyOnly = null;
    /**
     * 简单 多选
     */
    private List<QuestionsBean> mEasyMore = null;
    /**
     * 中等 单选
     */
    private List<QuestionsBean> mMediumOnly = null;
    /**
     * 中等 多选
     */
    private List<QuestionsBean> mMediumMore = null;
    /**
     * 困难 单选
     */
    private List<QuestionsBean> mDifficultyOnly = null;
    /**
     * 困难 多选
     */
    private List<QuestionsBean> mDifficultyMore = null;
    private RadioButton mRdbSelect20;
    private RadioButton mRdbSelect50;
    private RadioButton mRdbSelect100;
    private RadioButton mRdbSelecteasy;
    private RadioButton mRdbSelectmedi;
    private RadioButton mRdbSelectdif;


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_question);
        initView();
        initData();

    }*/

    /**
     * 科目
     */
    private static String COURSEID = "courseid";
    private AlertDialog mDialog;
    private AllQuestionPresenter mAllQuestionPresenter;


    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, FreeQuestionActivity.class);
        intent.putExtra(COURSEID, courseid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_free_question);
        if (getIntent() != null) {
            mCourseid = getIntent().getStringExtra(COURSEID);
        }
        initView();
        initData();
    }


    private void initData() {

        mRdbSelect20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPaperNumber = TWENTY;
                }
            }
        });
        mRdbSelect50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPaperNumber = FIFTY;
                }
            }
        });
        mRdbSelect100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPaperNumber = HUNDRED;
                }
            }
        });

        mRdbSelecteasy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPaperGrader = EASY;
                }
            }
        });
        mRdbSelectmedi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPaperGrader = MEDIUM;
                }
            }
        });
        mRdbSelectdif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPaperGrader = DIFFICULTY;
                }
            }
        });


    }

    private void initView() {
        mContext = this;
        mIcPopResult = (ImageView) findViewById(R.id.ic_pop_result);
        mRgQuestionNumber = (RadioGroup) findViewById(R.id.rg_question_number);
        mRgDifficultyGrade = (RadioGroup) findViewById(R.id.rg_difficulty_grade);
        mRlvFreeTitle = (RecyclerView) findViewById(R.id.rlv_free_title);
        mBtnCreateQuestion = (Button) findViewById(R.id.btn_create_question);
        mBtnCreateQuestion.setOnClickListener(this);
        mRdbSelect20 = (RadioButton) findViewById(R.id.rdb_select20);
        mRdbSelect20.setOnClickListener(this);
        mRdbSelect50 = (RadioButton) findViewById(R.id.rdb_select50);
        mRdbSelect50.setOnClickListener(this);
        mRdbSelect100 = (RadioButton) findViewById(R.id.rdb_select100);
        mRdbSelect100.setOnClickListener(this);
        mRdbSelecteasy = (RadioButton) findViewById(R.id.rdb_selecteasy);
        mRdbSelecteasy.setOnClickListener(this);
        mRdbSelectmedi = (RadioButton) findViewById(R.id.rdb_selectmedi);
        mRdbSelectmedi.setOnClickListener(this);
        mRdbSelectdif = (RadioButton) findViewById(R.id.rdb_selectdif);
        mRdbSelectdif.setOnClickListener(this);
    }


    private void clearData() {
        if (mEasy == null) {
            mEasy = new ArrayList<>();
        } else {
            mEasy.clear();
        }
        if (mMedium == null) {
            mMedium = new ArrayList<>();
        } else {
            mMedium.clear();
        }
        if (mDifficulty == null) {
            mDifficulty = new ArrayList<>();
        } else {
            mDifficulty.clear();
        }
        if (mEasyOnly == null) {
            mEasyOnly = new ArrayList<>();
        } else {
            mEasyOnly.clear();
        }
        if (mMediumOnly == null) {
            mMediumOnly = new ArrayList<>();
        } else {
            mMediumOnly.clear();
        }
        if (mDifficultyOnly == null) {
            mDifficultyOnly = new ArrayList<>();
        } else {
            mDifficultyOnly.clear();
        }
        if (mEasyMore == null) {
            mEasyMore = new ArrayList<>();
        } else {
            mEasyMore.clear();
        }
        if (mMediumMore == null) {
            mMediumMore = new ArrayList<>();
        } else {
            mMediumMore.clear();
        }
        if (mDifficultyMore == null) {
            mDifficultyMore = new ArrayList<>();
        } else {
            mDifficultyMore.clear();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_question:
                createrQuestion();
                break;
        }
    }

    @Override
    public void QuestionIdAllSuccess(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        Gson gson = new Gson();
        clearData();
        QuestionAllVoNew vo = gson.fromJson(con, QuestionAllVoNew.class);
        if (vo != null && vo.getData()!= null)
            if (vo.getStatus().getCode() == 200) {
                List<QuestionsBean> mQuestionAlldatas = vo.getData().getQuestions();
                outLinePager(mQuestionAlldatas);
                CreateText();

            } else {
                T.showToast(mContext, getStringWithId(R.string.net_error));
//            T.showToast(mContext, vo.getStatus().getMessage());
            }

    }

    /**
     * 创建试卷
     */
    private void CreateText() {
        List<QuestionsBean> overList = null;
        if (mPaperGrader == EASY) {//容易
            if (mPaperNumber.equals(TWENTY)) {//20
                overList = getPagerData(mEasyOnly, mEasyMore, 16, 4);
            } else if (mPaperNumber.equals(FIFTY)) {//50
                overList = getPagerData(mEasyOnly, mEasyMore, 40, 10);
            } else if (mPaperNumber.equals(HUNDRED)) {//100
                overList = getPagerData(mEasyOnly, mEasyMore, 80, 20);
            }

        }
        if (mPaperGrader == MEDIUM) {//中等
            if (mPaperNumber.equals(TWENTY)) {//20
                overList = getPagerData(mMediumOnly, mMediumMore, 16, 4);
            } else if (mPaperNumber.equals(FIFTY)) {//50
                overList = getPagerData(mMediumOnly, mMediumMore, 40, 10);
            } else if (mPaperNumber.equals(HUNDRED)) {//100
                overList = getPagerData(mMediumOnly, mMediumMore, 80, 20);

            }
        }
        if (mPaperGrader == DIFFICULTY) {//困难
            if (mPaperNumber.equals(TWENTY)) {//20
                overList = getPagerData(mDifficultyOnly, mDifficultyMore, 16, 4);
            } else if (mPaperNumber.equals(FIFTY)) {//50
                overList = getPagerData(mDifficultyOnly, mDifficultyMore, 40, 10);
            } else if (mPaperNumber.equals(HUNDRED)) {//100
                overList = getPagerData(mDifficultyOnly, mDifficultyMore, 80, 20);
            }
        }
        L.e(overList.size() + "传递的数据个数");

        EventBus.getDefault().postSticky(new FreeDataEvent(overList));
        Intent intent = new Intent(mContext, AnswerActivity.class);
        startActivity(intent);
    }

    /**
     * 分级
     *
     * @param mQuestionAlldatas
     */
    private void outLinePager(List<QuestionsBean> mQuestionAlldatas) {
        for (int i = 0; i < mQuestionAlldatas.size(); i++) {
            QuestionsBean bean = mQuestionAlldatas.get(i);
            int difficultydegree = bean.getDifficultydegree();
            if (difficultydegree == 1)//容易
            {
                mEasy.add(bean);
            } else if (difficultydegree == 2 || difficultydegree == 3) {
                mMedium.add(bean);
            } else if (difficultydegree == 4 || difficultydegree == 5) {
                mDifficulty.add(bean);
            }
        }
        for (int i = 0; i < mEasy.size(); i++) {
            QuestionsBean bean = mEasy.get(i);
            switch (bean.getType()) {
                case 2://单选
                    mEasyOnly.add(bean);
                    break;
                case 3://多选
                    mEasyMore.add(bean);
                    break;
                default:

            }
        }
        for (int i = 0; i < mMedium.size(); i++) {
            QuestionsBean bean = mMedium.get(i);
            switch (bean.getType()) {
                case 2://单选
                    mMediumOnly.add(bean);
                    break;
                case 3://多选
                    mMediumMore.add(bean);
                    break;
                default:

            }
        }
        for (int i = 0; i < mDifficulty.size(); i++) {
            QuestionsBean bean = mDifficulty.get(i);
            switch (bean.getType()) {
                case 2://单选
                    mDifficultyOnly.add(bean);
                    break;
                case 3://多选
                    mDifficultyMore.add(bean);
                    break;
                default:

            }
        }


    }

    /***
     * 生成问题
     */
    private void createrQuestion() {
        if (StringUtil.isEmpty(mPaperNumber)) {
            T.showToast(mContext, "请选择试题数量");
            return;
        }
        if (mPaperGrader == -1) {
            T.showToast(mContext, "请选择试题难度");
            return;
        }
        if (!StringUtil.isEmpty(mCourseid)) {
            mAllQuestionPresenter = new AllQuestionPresenter(new AllQuestionModelImpl(), this);
            mAllQuestionPresenter.getcoursequestionid(mContext, mCourseid);
        }
        mDialog = DialogUtil.showDialog(mContext, "", "正在生成试卷...");


    }

    /**
     * 合并数据
     *
     * @param onlyData 单选数据
     * @param moreData 多选数据
     * @param only     单选抽取数量
     * @param more     多选抽取数量
     * @return
     */
    private List<QuestionsBean> getPagerData(List<QuestionsBean> onlyData, List<QuestionsBean> moreData, int only, int more) {
        List<QuestionsBean> dataContent = new ArrayList<>();
        //单选
        Collections.shuffle(onlyData);
        for (int i = 0; i < only; i++) {
            QuestionsBean datasBean = onlyData.get(i);
            dataContent.add(datasBean);
        }
        //多选
        Collections.shuffle(moreData);
        for (int i = 0; i < more; i++) {
            QuestionsBean bean = moreData.get(i);
            dataContent.add(bean);
        }
        return dataContent;
    }

    @Override
    public void QuestionIdAllError(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

}
