package com.xuechuan.xcedu.ui.bank.newbank;

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

import com.multilevel.treelist.Node;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.ui.bank.newBankActivity.CaseFreedomTestActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.FreedomTestActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.QuestionCaseVo;

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
public class GmFreeQuestionActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIcPopResult;
    private RadioGroup mRgQuestionNumber;
    private RadioGroup mRgDifficultyGrade;
    private RecyclerView mRlvFreeTitle;
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
    private List<QuestionCaseVo> mEasy = null;
    /**
     * 中等
     */
    private List<QuestionCaseVo> mMedium = null;
    /**
     * 困难
     */
    private List<QuestionCaseVo> mDifficulty = null;
    /**
     * 简单 单选
     */
    private List<QuestionCaseVo> mEasyOnly = null;
    /**
     * 简单 多选
     */
    private List<QuestionCaseVo> mEasyMore = null;
    /**
     * 中等 单选
     */
    private List<QuestionCaseVo> mMediumOnly = null;
    /**
     * 中等 多选
     */
    private List<QuestionCaseVo> mMediumMore = null;
    /**
     * 困难 单选
     */
    private List<QuestionCaseVo> mDifficultyOnly = null;
    /**
     * 困难 多选
     */
    private List<QuestionCaseVo> mDifficultyMore = null;
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


    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, GmFreeQuestionActivity.class);
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
                clearData();
                createrQuestion();
                break;
        }
    }


    /**
     * 创建试卷
     */
    private ArrayList<QuestionCaseVo> CreateText() {
        ArrayList<QuestionCaseVo> overList = null;
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
        return overList;
    }

    /**
     * 分级
     *
     * @param mQuestionAlldatas
     */
    private void outLinePager(List<QuestionCaseVo> mQuestionAlldatas) {
        for (int i = 0; i < mQuestionAlldatas.size(); i++) {
            QuestionCaseVo bean = mQuestionAlldatas.get(i);
            int difficultydegree = bean.getDifficulty();
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
            QuestionCaseVo bean = mEasy.get(i);
            switch (bean.getQuestiontype()) {
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
            QuestionCaseVo bean = mMedium.get(i);
            switch (bean.getQuestiontype()) {
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
            QuestionCaseVo bean = mDifficulty.get(i);
            switch (bean.getQuestiontype()) {
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
        QuestionSqliteHelp help = QuestionSqliteHelp.get_Instance(mContext);
        if (StringUtil.isEmpty(mPaperNumber)) {
            T.showToast(mContext, "请选择试题数量");
            return;
        }
        if (mPaperGrader == -1) {
            T.showToast(mContext, "请选择试题难度");
            return;
        }
        mDialog = DialogUtil.showDialog(mContext, "", "正在生成试卷...");
        if (!StringUtil.isEmpty(mCourseid)) {
            ArrayList<QuestionCaseVo> list = null;
            switch (mPaperGrader) {
                case EASY:
                    list = help.queryCaseCondition(mCourseid, "1", "1");
                    break;
                case MEDIUM:
                    list = help.queryCaseCondition(mCourseid, "2", "3");
                    break;
                case DIFFICULTY:
                    list = help.queryCaseCondition(mCourseid, "4", "5");
                    break;
            }
            if (list == null || list.isEmpty()) {
                dismissDialog(mDialog);
                T.showToast(mContext, "生成试卷失败");
                return;
            }
            outLinePager(list);
            ArrayList<QuestionCaseVo> listData = CreateText();
            if (mCourseid.equals(DataMessageVo.PATTERN_THREE)) {
                Intent intent = CaseFreedomTestActivity.start_Intent(mContext, mCourseid, listData);
                startActivity(intent);
            } else {
                Intent intent = FreedomTestActivity.start_Intent(mContext, mCourseid, listData);
                startActivity(intent);
            }
            dismissDialog(mDialog);
        }


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
    private ArrayList<QuestionCaseVo> getPagerData(List<QuestionCaseVo> onlyData, List<QuestionCaseVo> moreData, int only, int more) {
        ArrayList<QuestionCaseVo> dataContent = new ArrayList<>();
        //单选(随机打乱数据)
        Collections.shuffle(onlyData);
        if (only >= onlyData.size()) {
            only = onlyData.size();
        }
        for (int i = 0; i < only; i++) {
            QuestionCaseVo datasBean = onlyData.get(i);
            dataContent.add(datasBean);
        }
        //多选(随机打乱数据)
        Collections.shuffle(moreData);
        if (more > moreData.size()) {
            more = moreData.size();
        }
        for (int i = 0; i < more; i++) {
            QuestionCaseVo bean = moreData.get(i);
            dataContent.add(bean);
        }
        return dataContent;
    }


}
