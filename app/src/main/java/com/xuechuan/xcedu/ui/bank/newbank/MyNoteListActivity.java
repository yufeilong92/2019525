package com.xuechuan.xcedu.ui.bank.newbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.MyNoteAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.MyNoteContract;
import com.xuechuan.xcedu.mvp.model.MyNoteModel;
import com.xuechuan.xcedu.mvp.presenter.MyNotePresenter;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.EncryptionUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.MyNoteListVo;
import com.xuechuan.xcedu.vo.RecyclerSelectVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MyNoteListActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 我的笔记
 * @author: L-BackPacker
 * @date: 2019.01.02 下午 1:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.02
 */

public class MyNoteListActivity extends BaseActivity implements MyNoteContract.View, View.OnClickListener {

    private static String TAG = "【" + MyNoteListActivity.class + "】==";
    private RecyclerView mRlvNetContent;
    private ImageView mIvNetEmpty;
    private XRefreshView mXrfvContent;
    private ArrayList mArray;
    private Context mContext;
    boolean isRefresh = false;
    private MyNotePresenter mPresenter;
    private TextView mTvNoteMake;
    private ArrayList<RecyclerSelectVo> mSelectDatas;
    private TextView mTvNoteCancle;
    private ImageView mIvNoteBack;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note_list);
        initView();
    }
*/

    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.bankMyNoteListActivity.courseid";
    private static String PARAMT1_KEY = "com.xuechuan.xcedu.ui.bankMyNoteListActivity.paramt_key1";
    private String mCourseId;
    private MyNoteAdapter adapter;
    private QuestionSqliteHelp mSqliteHelp;


    public static Intent start_Intent(Context context, String courseid, String paramt1) {
        Intent intent = new Intent(context, MyNoteListActivity.class);
        intent.putExtra(PARAMT_KEY, courseid);
        intent.putExtra(PARAMT1_KEY, paramt1);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_note_list);
        if (getIntent() != null) {
            mCourseId = getIntent().getStringExtra(PARAMT_KEY);
        }
        initView();
        initData();
        initUilts();
        clearData();
        mSelectDatas = new ArrayList<>();
        bindAdapter();
        initxfvView();
        mXrfvContent.startRefresh();
    }

    private void initUilts() {
        mSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);
    }

    private void initData() {
        mPresenter = new MyNotePresenter();
        mPresenter.initModelView(new MyNoteModel(), this);
    }

    private long lastRefreshTime;

    private void initxfvView() {
        mXrfvContent.restoreLastRefreshTime(lastRefreshTime);
        mXrfvContent.setPullRefreshEnable(true);
        mXrfvContent.setPullLoadEnable(true);
        mXrfvContent.setAutoLoadMore(true);
        mXrfvContent.setEmptyView(mIvNetEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXrfvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                RefreshData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }
        });

    }

    private void bindAdapter() {
        setGridLayoutManger(mContext, mRlvNetContent, 1);
        adapter = new MyNoteAdapter(mContext, mArray);
        mRlvNetContent.setAdapter(adapter);
        adapter.setOnCbClickListener(new MyNoteAdapter.OnCbClickListener() {
            @Override
            public void onCbClickItem(Object o, int postion, boolean isClick) {
                RecyclerSelectVo vo = mSelectDatas.get(postion);
                vo.setSelect(isClick);
            }
        });
        adapter.setOnItemClickListener(new MyNoteAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(Object o, int postion) {
                MyNoteListVo.DatasBean vo = (MyNoteListVo.DatasBean) o;
                QuestionSqliteVo sqliteVo = mSqliteHelp.queryQuestionVoWithQuestion(vo.getQuestionid());
                if (sqliteVo == null || StringUtil.isEmpty(sqliteVo.getQuestionString())) {
                    T.showToast(mContext, "该题已经被删除");
                    mPresenter.deleteNote(mContext, vo.getId());
                    mArray.remove(postion);
                    adapter.notifyDataSetChanged();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < vo.getTags().size(); i++) {
                    MyNoteListVo.DatasBean.TagsBean bean = vo.getTags().get(i);
                    if (i == vo.getTags().size() - 1) {
                        buffer.append(bean.getTagname());
                    } else {
                        buffer.append(bean.getTagname());
                        buffer.append(",");
                    }
                }
                Intent intent = NoteMakeActivity.start_Intent(mContext, vo.getQuestionid(),
                        vo.getQuestionid(), Integer.parseInt(mCourseId),
                        vo.getContent(), sqliteVo.getQuestionString(), buffer.toString(), 2);
                intent.putExtra(NoteMakeActivity.CSTR_EXTRA_TITLE_STR, "笔记编辑");
                startActivity(intent);
            }
        });
    }

    private void RefreshData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestMyNoteListOne(mContext, mCourseId, 1);
    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestMyNoteListMore(mContext, mCourseId, getNowPage() + 1);
    }

    private void initView() {
        mContext = this;
        mRlvNetContent = (RecyclerView) findViewById(R.id.rlv_net_content);
        mIvNetEmpty = (ImageView) findViewById(R.id.iv_net_empty);
        mXrfvContent = (XRefreshView) findViewById(R.id.xrfv_content);
        mTvNoteMake = (TextView) findViewById(R.id.tv_note_make);
        mTvNoteMake.setOnClickListener(this);
        mTvNoteCancle = (TextView) findViewById(R.id.tv_note_cancle);
        mTvNoteCancle.setOnClickListener(this);
        mIvNoteBack = (ImageView) findViewById(R.id.iv_note_back);
    }

    private void clearData() {
        if (mArray == null) {
            mArray = new ArrayList();
        } else {
            mArray.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArray == null) {
            clearData();
        }
        mArray.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArray == null || mArray.isEmpty())
            return 0;
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }


    @Override
    public void MyNoteSuccessOne(String success) {
        mXrfvContent.stopRefresh();
        isRefresh = false;
        MyNoteListVo listVo = Utils.getGosnT(success, MyNoteListVo.class);
        if (listVo.getStatus().getCode() == 200) {
            List<MyNoteListVo.DatasBean> datas = listVo.getDatas();
            clearData();
            if (datas == null || datas.isEmpty()) {
                adapter.notifyDataSetChanged();
                mXrfvContent.setLoadComplete(true);
                return;
            } else {
                addListData(datas);
            }
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setLoadComplete(false);
                mXrfvContent.setPullLoadEnable(true);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (listVo.getTotal().getTotal() == datas.size()) {
                mXrfvContent.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            T_ERROR(mContext);
        }

    }

    @Override
    public void MyNoteErrorOne(String error) {
        mXrfvContent.stopRefresh();
        isRefresh = false;
        T_ERROR(mContext);
    }

    @Override
    public void MyNoteSuccessMore(String success) {
        isRefresh = false;
        MyNoteListVo listVo = Utils.getGosnT(success, MyNoteListVo.class);
        if (listVo.getStatus().getCode() == 200) {
            List<MyNoteListVo.DatasBean> datas = listVo.getDatas();
            if (datas == null || datas.isEmpty()) {
                mXrfvContent.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            } else {
                addListData(datas);
            }
            //判断是否能整除
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setLoadComplete(false);
                mXrfvContent.setPullLoadEnable(true);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (listVo.getTotal().getTotal() == mArray.size()) {
                mXrfvContent.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();


        } else {
            T_ERROR(mContext);
            mXrfvContent.setLoadComplete(true);
        }
    }

    @Override
    public void MyNoteErrorMore(String error) {
        isRefresh = false;
        T_ERROR(mContext);
        mXrfvContent.setLoadComplete(false);
    }

    @Override
    public void DeleteNoteSuccess(String succes) {
        ResultVo vo = Utils.getGosnT(succes, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {

        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void DeleteNoteError(String msg) {
        T_ERROR(mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_note_make://编辑
                String trim = mTvNoteMake.getText().toString().trim();
                if (mArray == null || mArray.isEmpty()) {
                    T.showToast(mContext, "暂无内容可编辑");
                    return;
                }
                if (trim.equals(getStringWithId(R.string.edit))) {//编辑
                    mSelectDatas.clear();
                    for (int i = 0; i < mArray.size(); i++) {
                        MyNoteListVo.DatasBean noteVo = (MyNoteListVo.DatasBean) mArray.get(i);
                        RecyclerSelectVo vo = new RecyclerSelectVo();
                        vo.setOpen(true);
                        vo.setSelect(false);
                        vo.setId(noteVo.getId());
                        mSelectDatas.add(vo);
                    }
                    if (adapter != null) {
                        adapter.setSelectDataVo(mSelectDatas);
                    }
                    setBackView(true);
                } else {//删除
                    deleteNote();
                }
                break;
            case R.id.tv_note_cancle://取消
                setBackView(false);
                for (int i = 0; i < mSelectDatas.size(); i++) {
                    RecyclerSelectVo vo = mSelectDatas.get(i);
                    vo.setOpen(false);
                }
                adapter.setSelectDataVo(mSelectDatas);
                adapter.notifyDataSetChanged();
                break;

        }
    }

    private void setBackView(boolean show) {
        mTvNoteCancle.setVisibility(show ? View.VISIBLE : View.GONE);
        mIvNoteBack.setVisibility(show ? View.GONE : View.VISIBLE);
        mTvNoteMake.setText(show ? getStringWithId(R.string.delect) : getStringWithId(R.string.edit));
    }

    private void deleteNote() {
        if (mSelectDatas.isEmpty() || mSelectDatas == null) {
            T.showToast(mContext, "请选择要删除的笔记");
            return;
        }
        boolean show = false;
        for (int i = 0; i < mSelectDatas.size(); i++) {
            RecyclerSelectVo vo = mSelectDatas.get(i);
            if (vo.isSelect()) {
                show = true;
                break;
            }

        }
        if (!show) {
            T.showToast(mContext, "请选择要删除的笔记");
            return;
        }

        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, "是否删除当前笔记",
                getStringWithId(R.string.sure), getStringWithId(R.string.cancel), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                for (int i = 0; i < mSelectDatas.size(); i++) {
                    RecyclerSelectVo vo = mSelectDatas.get(i);
                    if (vo.isSelect()) {
                        mPresenter.deleteNote(mContext, vo.getId());
                        mArray.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
                if (mArray == null || mArray.isEmpty()) {
                    mSelectDatas.clear();
                    setBackView(false);
                }
            }

            @Override
            public void onCancelClickListener() {
            }
        });


    }

}
