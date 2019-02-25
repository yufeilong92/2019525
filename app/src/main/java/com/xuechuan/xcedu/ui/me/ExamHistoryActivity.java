package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.easefun.polyvsdk.PolyvSDKUtil;
import com.google.android.exoplayer.C;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.me.ExamHistoryAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.ExamHistoryContract;
import com.xuechuan.xcedu.mvp.model.ExamHistoryModel;
import com.xuechuan.xcedu.mvp.presenter.ExamHistoryPresenter;
import com.xuechuan.xcedu.sqlitedb.DoBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoKaoShiSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.KaoShiSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionChapterSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.ui.bank.newBankActivity.CaseExamtActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.MockExamActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ExamHistoryDetailVo;
import com.xuechuan.xcedu.vo.ExamHistoryVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.KaoShiSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ExamHistoryActivity
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 试卷记录
 * @author: L-BackPacker
 * @date: 2019.01.09 下午 2:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.09
 */

public class ExamHistoryActivity extends BaseActivity implements ExamHistoryContract.View {

    private RecyclerView mRlvNetContent;
    private ImageView mIvNetEmpty;
    private XRefreshView mXrfvContent;
    private List mArrary;
    private long lastRefreshTime;
    private boolean isRefresh;
    private Context mContext;
    private ExamHistoryAdapter adapter;
    private ExamHistoryPresenter mPresenter;
    public int mPostion = -1;

    private AlertDialog mDialogShowDialog;
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_history);
        initView();
    }
*/

    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.meExamHistoryActivity.paramt_key";
    private static String PARAMT1_KEY = "com.xuechuan.xcedu.ui.meExamHistoryActivity.paramt_key1";
    private QuestionChapterSqliteHelp mChapterSqliteHelp;
    private AlertDialog mShowDialog;
    private QuestionSqliteHelp mQuestionSqliteHelp;
    private GmTextUtil mGmTextUtil;
    private DoBankSqliteHelp mDoBankSqliteHelp;
    private KaoShiSqliteHelp mKaoShiSqliteHelp;
    private DoKaoShiSqlteHelp mDoKaoShiSqlteHelp;

    public static Intent start_Intent(Context context, String paramt, String paramt1) {
        Intent intent = new Intent(context, ExamHistoryActivity.class);
        intent.putExtra(PARAMT_KEY, paramt);
        intent.putExtra(PARAMT1_KEY, paramt1);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exam_history);
        initView();
        initUtils();
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXrfvContent.startRefresh();
    }

    private boolean doOpenNetWork() {
        return PolyvSDKUtil.isOpenNetwork(mContext);
    }

    private void initUtils() {
        mQuestionSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);
        mGmTextUtil = GmTextUtil.get_Instance(mContext);
        mDoBankSqliteHelp = DoBankSqliteHelp.get_Instance(mContext);
        mKaoShiSqliteHelp = KaoShiSqliteHelp.get_Instance(mContext);
        mDoKaoShiSqlteHelp = DoKaoShiSqlteHelp.getInstance(mContext);

    }

    private void initXrfresh() {
        mXrfvContent.restoreLastRefreshTime(lastRefreshTime);
        mXrfvContent.setPullLoadEnable(true);
        mXrfvContent.setAutoLoadMore(true);
        mXrfvContent.setPullRefreshEnable(true);
        mXrfvContent.setEmptyView(mIvNetEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXrfvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        mPresenter.requestExamHistoryTwo(mContext, getNowPage() + 1);
    }


    private void loadNewData() {
    /*    if (!doOpenNetWork()) {
            doBendiData();
            return;
        }*/
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestExamHistoryOne(mContext, 1);
    }

    private void doBendiData() {
        List<KaoShiSqliteVo> vos = mKaoShiSqliteHelp.queryAllItem();
        if (vos == null || vos.isEmpty()) {
            return;
        }
        List<ExamHistoryVo.DatasBean> list = new ArrayList<>();
        for (int i = 0; i < vos.size(); i++) {
            ExamHistoryVo.DatasBean bean = new ExamHistoryVo.DatasBean();
            KaoShiSqliteVo vo = vos.get(i);
            bean.setId(vo.getId());
            bean.setChapterid(vo.getChapter_id());
            bean.setScore(vo.getUsertime());
            bean.setUsetime(vo.getUsertime());
            bean.setTimekey(vo.getTimekey());
            list.add(bean);
        }
        addListData(list);
        mXrfvContent.stopRefresh();
        mXrfvContent.setLoadComplete(true);
        adapter.notifyDataSetChanged();

    }

    private void bindAdapterData() {
        setGridLayoutManger(mContext, mRlvNetContent, 1);
        adapter = new ExamHistoryAdapter(mContext, mArrary);
        adapter.doChapterHelp(mChapterSqliteHelp);
        mRlvNetContent.setAdapter(adapter);
        adapter.setOnItemClickListener(new ExamHistoryAdapter.OnItemClickListener() {


            @Override
            public void onClickItem(ExamHistoryVo.DatasBean o, int type, int postion, int courseid) {
                mPostion = postion;
                switch (type) {
                    case 1://重做
                        doAgainExan(o, courseid);
                        break;
                    case 2://删除
                        showDelete(o);
                        break;
                    case 3://解析
                        if (mDoBankSqliteHelp != null) {
                            mDoBankSqliteHelp.delelteTable();
                        }
                        mDialogShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
                        showJiexi(o, courseid);
                        break;
                }
            }

            /**
             * 查看解析
             * @param o
             * @param courseid
             */
            private void showJiexi(ExamHistoryVo.DatasBean o, int courseid) {
                switch (courseid) {
                    case 1:
                    case 2:
                        mPresenter.requestExamDetail(mContext, o.getId());
                        break;
                    case 3:
                        break;

                }


            }

            /**
             * 重新做题
             * @param o
             * @param courseid
             */
            private void doAgainExan(ExamHistoryVo.DatasBean o, int courseid) {
                switch (courseid) {
                    case 1:
                    case 2:
                        Intent intent = MockExamActivity.start_Intent(
                                mContext, String.valueOf(courseid), String.valueOf(o.getChapterid()), 0);
                        startActivity(intent);
                        break;
                    case 3:

                        break;

                }

            }

            private void showDelete(final ExamHistoryVo.DatasBean o) {
                DialogUtil dialogUtil = DialogUtil.getInstance();
                dialogUtil.showTitleDialog(mContext, getStringWithId(R.string.suredelete),
                        getStringWithId(R.string.sure), getStringWithId(R.string.cancel), true);
                dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        mPresenter.deleteExamHistory(mContext, o.getId());
                    }

                    @Override
                    public void onCancelClickListener() {
                        mPostion = -1;
                    }
                });
            }
        });

    }

    private void initData() {
        mChapterSqliteHelp = QuestionChapterSqliteHelp.get_Instance(mContext);
        mPresenter = new ExamHistoryPresenter();
        mPresenter.initModelView(new ExamHistoryModel(), this);
    }

    private void initView() {
        mContext = this;
        mRlvNetContent = (RecyclerView) findViewById(R.id.rlv_net_content);
        mIvNetEmpty = (ImageView) findViewById(R.id.iv_net_empty);
        mXrfvContent = (XRefreshView) findViewById(R.id.xrfv_content);
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
    public void ExamHistorySuccessOne(String success) {
        mXrfvContent.stopRefresh();
        isRefresh = false;
        ExamHistoryVo vo = Utils.getGosnT(success, ExamHistoryVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ExamHistoryVo.DatasBean> datas = vo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXrfvContent.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setLoadComplete(false);
                mXrfvContent.setPullLoadEnable(true);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mArrary.size()) {
                mXrfvContent.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            T_ERROR(mContext);
        }
    }

    @Override
    public void ExamHistoryErrrorOne(String msg) {
        mXrfvContent.stopRefresh();
        isRefresh = false;
        T_ERROR(mContext);
    }

    @Override
    public void ExamHistorySuccessTwo(String success) {
        isRefresh = false;
        ExamHistoryVo vo = Utils.getGosnT(success, ExamHistoryVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ExamHistoryVo.DatasBean> datas = vo.getDatas();
//                    clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXrfvContent.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setLoadComplete(false);
                mXrfvContent.setPullLoadEnable(true);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mArrary.size()) {
                mXrfvContent.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXrfvContent.setLoadComplete(false);
            T_ERROR(mContext);
        }
    }

    @Override
    public void ExamHistoryErrrorTwo(String msg) {
        isRefresh = false;
        mXrfvContent.setLoadComplete(false);
        T_ERROR(mContext);
    }

    @Override
    public void deleteHistorySuccess(String success) {
        dismissDialog(mShowDialog);
        ResultVo vo = Utils.getGosnT(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (mPostion != -1) {
                mArrary.remove(mPostion);
                mRlvNetContent.setItemAnimator(new DefaultItemAnimator());
                adapter.notifyDataSetChanged();
                mPostion = -1;
//                DelectSuceessActivity.newInstance(mContext, DelectSuceessActivity.DELECTSUCCESS);
            }

        } else {
            mPostion = -1;
            T_ERROR(mContext);
        }

    }

    @Override
    public void deleteHistoryError(String msg) {
        dismissDialog(mShowDialog);
        mPostion = -1;
        T_ERROR(mContext);
    }

    private static String TAG = "【" + ExamHistoryActivity.class + "】==";

    @Override
    public void ExamHistoryDetailSuccess(String success) {
        ExamHistoryDetailVo vo = Utils.getGosnT(success, ExamHistoryDetailVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ExamHistoryDetailVo.DatasBean> datas = vo.getDatas();
            if (datas == null || datas.isEmpty()) return;
            doDoBankVoEvent(datas);

        } else {
            T_ERROR(mContext);
        }

        dismissDialog(mDialogShowDialog);
    }

    private void doDoBankVoEvent(List<ExamHistoryDetailVo.DatasBean> datas) {
        int courseid = 0;
        int chapterid = 0;
        for (int i = 0; i < datas.size(); i++) {
            ExamHistoryDetailVo.DatasBean bean = datas.get(i);
            String choice = bean.getChoice();
            QuestionSqliteVo sqliteVo = mQuestionSqliteHelp.queryQuestionVoWithQuestion(bean.getQuestionid());
            if (sqliteVo == null) {
                continue;
            } else {
                if (courseid == 0)
                    courseid = sqliteVo.getCourseid();
                if (chapterid == 0) {
                    chapterid = sqliteVo.getChapter_id();
                }
                if (StringUtil.isEmpty(choice)) {
                    continue;
                } else {
                    if (sqliteVo.getQuestiontype() == 2) {//单选
                        doRadioEvent(choice, sqliteVo);
                    } else if (sqliteVo.getQuestiontype() == 3) {//多选
                        List<String> list = mGmTextUtil.getAnswerKeyList(choice);
                        doMoreEvent(choice, list, sqliteVo);
                    } else if (sqliteVo.getQuestiontype() == 4) {
                        doReadEvent(bean, sqliteVo);
                    }
                }
            }
        }
        switch (courseid) {
            case 1://技术
                Intent intent = MockExamActivity.start_Intent(
                        mContext, String.valueOf(courseid), String.valueOf(chapterid), 1);
                startActivity(intent);
                break;
            case 2://案例
                Intent intent2 = CaseExamtActivity.start_Intent(mContext, String.valueOf(courseid),
                        String.valueOf(chapterid),1);
                startActivity(intent2);
                break;
            case 3://综合
                Intent intent1 = MockExamActivity.start_Intent(
                        mContext, String.valueOf(courseid), String.valueOf(chapterid), 1);
                startActivity(intent1);
                break;
            default:

        }

    }

    /**
     * 阅读
     *
     * @param bean
     * @param sqliteVo
     */
    private void doReadEvent(ExamHistoryDetailVo.DatasBean bean, QuestionSqliteVo sqliteVo) {
        DoBankSqliteVo vo = new DoBankSqliteVo();
        vo.setChapterid(sqliteVo.getChapter_id());
        vo.setQuestion_id(sqliteVo.getQuestion_id());
        vo.setCourseid(sqliteVo.getCourseid());
        vo.setIsDo(1);
        vo.setQuestiontype(sqliteVo.getQuestiontype());
        vo.setIsright(1);
        vo.setIsAnalySis(1);
        vo.setAnalysis(bean.getAnswer());
        vo.setChild_id(sqliteVo.getQuestion_id());
        mDoBankSqliteHelp.addDoBankItem(vo);
    }

    /**
     * 多选
     *
     * @param choice
     * @param list     用户选中结果
     * @param sqliteVo
     */
    private void doMoreEvent(String choice, List<String> list, QuestionSqliteVo sqliteVo) {
        if (list == null || list.isEmpty()) return;
        DoBankSqliteVo vo = new DoBankSqliteVo();
        String choice_answer = sqliteVo.getChoice_answer();
        //题结果
        List<String> keyList = mGmTextUtil.getAnswerKeyList(choice_answer);
        if (list.size() > keyList.size()) {
            vo.setIsright(3);
        } else if (keyList.size() == list.size()) {
            if (keyList.containsAll(list)) {
                vo.setIsright(1);
            } else {
                vo.setIsright(3);
            }
        } else if (list.size() < keyList.size()) {
            if (keyList.containsAll(list)) {
                vo.setIsright(2);
            } else {
                vo.setIsright(3);
            }
        }
        vo.setQuestiontype(sqliteVo.getQuestiontype());
        vo.setIsDo(1);
        vo.setSelectA(choice.contains("A") ? 1 : 0);
        vo.setSelectB(choice.contains("B") ? 1 : 0);
        vo.setSelectC(choice.contains("C") ? 1 : 0);
        vo.setSelectD(choice.contains("D") ? 1 : 0);
        vo.setSelectE(choice.contains("E") ? 1 : 0);
        vo.setCourseid(sqliteVo.getCourseid());
        vo.setQuestion_id(sqliteVo.getQuestion_id());
        vo.setChapterid(sqliteVo.getChapter_id());
        mDoBankSqliteHelp.addDoBankItem(vo);

    }

    /**
     * 单选
     *
     * @param check
     * @param sqliteVo
     */
    private void doRadioEvent(String check, QuestionSqliteVo sqliteVo) {
        DoBankSqliteVo vo = new DoBankSqliteVo();
        int right = 0;
        List<String> list = mGmTextUtil.getAnswerKeyList(sqliteVo.getChoice_answer());
        if (mGmTextUtil.keyIsRight(list, check)) {
            right = 1;
        } else {//错误
            right = 3;
        }
        vo.setIsright(right);
        vo.setIsDo(1);
        if (check.equalsIgnoreCase("A")) {
            vo.setSelectA(1);
        }
        if (check.equalsIgnoreCase("B")) {
            vo.setSelectB(1);
        }
        if (check.equalsIgnoreCase("C")) {
            vo.setSelectC(1);
        }
        if (check.equalsIgnoreCase("D")) {
            vo.setSelectD(1);
        }
        if (check.equalsIgnoreCase("E")) {
            vo.setSelectE(1);
        }
        vo.setQuestiontype(sqliteVo.getQuestiontype());
        vo.setCourseid(sqliteVo.getCourseid());
        vo.setChapterid(sqliteVo.getChapter_id());
        vo.setQuestion_id(sqliteVo.getQuestion_id());
        mDoBankSqliteHelp.addDoBankItem(vo);
    }

    @Override
    public void ExamHistoryDetailError(String msg) {
        dismissDialog(mDialogShowDialog);
        T_ERROR(mContext);

    }
}
