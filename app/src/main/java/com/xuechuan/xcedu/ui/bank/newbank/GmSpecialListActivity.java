package com.xuechuan.xcedu.ui.bank.newbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.GmSpeciaListAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.service.SubmiteProgressQuestionService;
import com.xuechuan.xcedu.sqlitedb.DoLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoUpLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.TagSqliteHelp;
import com.xuechuan.xcedu.ui.bank.newBankActivity.CaseSpecialListActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.NewTextActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.SpecialTextActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoLogProgreeSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.TagSqliteVo;
import com.xuechuan.xcedu.vo.UpQuestionProgressVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SpecialListActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 专项练习
 * @author: L-BackPacker
 * @date: 2018/7/27 17:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/7/27
 */
public class GmSpecialListActivity extends BaseActivity {

    private RecyclerView mRlvSpecialContent;
    private Context mContext;
    /**
     * 问题id
     */
    private static String COURESID = "mCouresid";
    private String mCourseId;
    private ImageView mIvNetEmptyContent;
    private DoLogProgressSqliteHelp mProgressSqliteHelp;
    private GmSpeciaListAdapter adapter;
    private DoUpLogProgressSqliteHelp mDoUpLogProgressSqliteHelp;

    public static Intent newInstance(Context context, String mCouresid) {
        Intent intent = new Intent(context, GmSpecialListActivity.class);
        intent.putExtra(COURESID, mCouresid);
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
            mCourseId = getIntent().getStringExtra(COURESID);
        }
        initView();
        initUtils();
        initData();
        submitProgress();

    }

    private void initUtils() {
        mDoUpLogProgressSqliteHelp = DoUpLogProgressSqliteHelp.get_Instance(mContext);
    }

    private void submitProgress() {
        List<DoLogProgreeSqliteVo> datas = mDoUpLogProgressSqliteHelp.queryAllProgress(DataMessageVo.SPECIAL_TWO, Integer.parseInt(mCourseId));
        if (datas == null || datas.isEmpty()) return;
        ArrayList<UpQuestionProgressVo> list = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            DoLogProgreeSqliteVo vo = datas.get(i);
            UpQuestionProgressVo progressVo = new UpQuestionProgressVo();
            progressVo.setId(vo.getId());
            progressVo.setTagetid(vo.getChapterid());
            progressVo.setRnum(StringUtil.isEmpty(vo.getNumber()) ? 0 : Integer.parseInt(vo.getNumber()));
            progressVo.setQt(2);
            list.add(progressVo);
        }
        SubmiteProgressQuestionService.startActionFoo(mContext, list, mCourseId);
    }


    private void initData() {
        mProgressSqliteHelp = DoLogProgressSqliteHelp.get_Instance(mContext);
        TagSqliteHelp help = TagSqliteHelp.get_Instance(mContext);
        List<TagSqliteVo> vos = help.queryTagAllWithCourserid(mCourseId);
        if (vos == null || vos.isEmpty()) {
            mRlvSpecialContent.setVisibility(View.GONE);
            mIvNetEmptyContent.setVisibility(View.VISIBLE);
            return;
        } else {
            mRlvSpecialContent.setVisibility(View.VISIBLE);
            mIvNetEmptyContent.setVisibility(View.GONE);
        }
        initAdapter(vos);

    }

    private void initView() {
        mContext = this;
        mRlvSpecialContent = (RecyclerView) findViewById(R.id.rlv_special_content);
        mIvNetEmptyContent = (ImageView) findViewById(R.id.iv_net_empty_content);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        submitProgress();
    }

    private void initAdapter(List<TagSqliteVo> list) {
        setGridLayoutManger(mContext, mRlvSpecialContent, 1);
        adapter = new GmSpeciaListAdapter(mContext, list);
        adapter.setHelp(mProgressSqliteHelp);
        mRlvSpecialContent.addItemDecoration(new DividerItemDecoration(this, GridLayoutManager.VERTICAL));
        mRlvSpecialContent.setAdapter(adapter);
        adapter.setClickListener(new GmSpeciaListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                TagSqliteVo bean = (TagSqliteVo) obj;
                if (mCourseId.equals(DataMessageVo.PATTERN_THREE)) {
                    Intent intent = CaseSpecialListActivity.start_Intent(mContext, mCourseId, String.valueOf(bean.getTagid()));
                    intent.putExtra(SpecialTextActivity.CSTR_EXTRA_TITLE_STR,bean.getTagname());
                    startActivity(intent);
                } else {
                    Intent intent = SpecialTextActivity.start_Intent(mContext, mCourseId, String.valueOf(bean.getTagid()));
                    intent.putExtra(SpecialTextActivity.CSTR_EXTRA_TITLE_STR,bean.getTagname());
                    startActivity(intent);
                }
            }
        });
    }

}
