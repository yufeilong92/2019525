package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.SpeciaListAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.SpecaliModellmpl;
import com.xuechuan.xcedu.mvp.presenter.SpecailPresenter;
import com.xuechuan.xcedu.mvp.view.SpecailView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.SpecialDataVo;
/**
 * @Title:  SpecialListActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 专项练习
 * @author: L-BackPacker
 * @date:   2018/7/27 17:07
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/7/27
 */
public class SpecialListActivity extends BaseActivity implements SpecailView {

    private RecyclerView mRlvSpecialContent;
    private Context mContext;
    /**
     * 问题id
     */
    private static String MTYPEOID = "mtypeoid";
    private String mTypeOid;
    private AlertDialog mDialog;
    private SpecailPresenter mPresenter;

    public static Intent newInstance(Context context, String mtypeoid) {
        Intent intent = new Intent(context, SpecialListActivity.class);
        intent.putExtra(MTYPEOID, mtypeoid);
        return intent;
    }
/*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_list);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_special_list);
        if (getIntent() != null) {
            mTypeOid = getIntent().getStringExtra(MTYPEOID);
        }
        initView();
        initData();

    }


    private void initData() {
        mPresenter = new SpecailPresenter(this, new SpecaliModellmpl());
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.getQuestionTags(mContext, mTypeOid);

    }

    private void initView() {
        mContext = this;
        mRlvSpecialContent = (RecyclerView) findViewById(R.id.rlv_special_content);
    }

    @Override
    public void RequestionWithtagsError(String con) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.getQuestionTags(mContext, mTypeOid);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
    }

    @Override
    public void RequestionWithtagsSuccess(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        Gson gson = new Gson();
        SpecialDataVo vo = gson.fromJson(con, SpecialDataVo.class);
        if (vo!=null&&vo.getDatas() != null && !vo.getDatas().isEmpty())
            initAdapter(vo);

    }

    private void initAdapter(SpecialDataVo vo) {
        setGridLayoutManger(mContext,mRlvSpecialContent,1);
        SpeciaListAdapter adapter = new SpeciaListAdapter(mContext, vo.getDatas());
        mRlvSpecialContent.addItemDecoration(new DividerItemDecoration(this, GridLayoutManager.VERTICAL));
        mRlvSpecialContent.setAdapter(adapter);
        adapter.setClickListener(new SpeciaListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                SpecialDataVo.DatasBean bean = (SpecialDataVo.DatasBean) obj;
                Intent intent = AnswerActivity.newInstance(mContext, mTypeOid,
                        String.valueOf(bean.getId()), bean.getRnum());
                startActivity(intent);
            }
        });
    }

}
