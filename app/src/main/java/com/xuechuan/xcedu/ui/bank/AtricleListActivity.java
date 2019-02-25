package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.multilevel.treelist.Node;
import com.multilevel.treelist.OnTreeNodeClickListener;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.AtricleTreeAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.AtricleContentModelImpl;
import com.xuechuan.xcedu.mvp.presenter.AtriclePresenter;
import com.xuechuan.xcedu.mvp.view.AtricleView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;
import com.xuechuan.xcedu.vo.SkillTextVo;


import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AtricleTextListActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 章节练习首页
 * @author: L-BackPacker
 * @date: 2018/4/27 16:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/27
 */
public class AtricleListActivity extends BaseActivity implements AtricleView {

    private RecyclerView mRlvTreeContent;
    private Context mContext;
    /**
     * 科目编号
     */
    private static String COURSEID = "courseid";
    private String mOid;
    private ArrayList<Node> mNodeLists;
    private AlertDialog mAlertDialog;
    private AtriclePresenter atriclePresenter;
    /**
     * 记录用户选择的试题位置
     */
    private String mselectPostion = "-1";
    private AtricleTreeAdapter mAdapter;

    /**
     * @param context
     * @param courseid 科目id
     * @return
     */
    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, AtricleListActivity.class);
        intent.putExtra(COURSEID, courseid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_atricle_tree);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(COURSEID);
        }
        initView();
        initData();

    }

    private void initData() {
        atriclePresenter = new AtriclePresenter(new AtricleContentModelImpl(), this);
        mAlertDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        atriclePresenter.getAtricleContent(mContext, mOid);
        mNodeLists = new ArrayList<>();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                atriclePresenter.getAtricleContent(mContext, mOid);
//                mAlertDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
//                mAdapter.notifyDataSetChanged();
            }
        }, 500);
    }

    private Node mNode;

    private void setAdapter(List<SkillTextVo.DatasBean> datas) {
        setGridLayoutManger(mContext,mRlvTreeContent,1);
//        mRlvTreeContent.addItemDecoration(new DividerItemDecoration(this, GridLayoutManager.VERTICAL));
        mAdapter = new AtricleTreeAdapter(mRlvTreeContent, this, mNodeLists,
                0, R.mipmap.ic_spread_gray, R.mipmap.ic_more_go, mOid, datas);

        mRlvTreeContent.setAdapter(mAdapter);
        mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                if (!node.isRoot()) {
                    mselectPostion = String.valueOf(node.getpId());
                    ChildrenBeanVo vo = (ChildrenBeanVo) node.getBean();
                    Intent intent = AnswerActivity.newInstances(AtricleListActivity.this, String.valueOf(node.getId()),
                            mOid, vo.getRnum());
                    mNode = node;
                    startActivity(intent);
                } else {
                    List children = node.getChildren();
                    if (children == null || children.isEmpty()) {
                        Intent intent = AnswerActivity.newInstances(AtricleListActivity.this, String.valueOf(node.getpId()),
                                mOid, 0);
                        startActivity(intent);
                        mselectPostion = "-1";
                        return;
                    }
                    mselectPostion = String.valueOf(node.getId());

                }
            }
        });

    }

    private void initView() {
        mContext = this;
        mRlvTreeContent = (RecyclerView) findViewById(R.id.rlv_tree_content);
    }

    @Override
    public void Success(String content) {
        dismissDialog(mAlertDialog);
        Gson gson = new Gson();
        SkillTextVo vo = gson.fromJson(content, SkillTextVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<SkillTextVo.DatasBean> datas = vo.getDatas();
            clearData();
            addListData(datas);
            setAdapter(datas);
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        }
    }

    private void addListData(List<SkillTextVo.DatasBean> datas) {
        for (int i = 0; i < datas.size(); i++) {
            SkillTextVo.DatasBean bean = datas.get(i);
            if (mselectPostion.equals(" -1"))
                mNodeLists.add(new Node(bean.getId() + "", bean.getParentid() + "", false, bean.getTitle(), bean));
            else if (mselectPostion.equals(String.valueOf(bean.getId())))
                mNodeLists.add(new Node(bean.getId() + "", bean.getParentid() + "", true, bean.getTitle(), bean));
            else
                mNodeLists.add(new Node(bean.getId() + "", bean.getParentid() + "", false, bean.getTitle(), bean));

            if (!bean.isIsend()) {
                List<ChildrenBeanVo> vos = bean.getChildren();
                bindData(vos);
            }

        }
    }

    private void bindData(List<ChildrenBeanVo> vos) {
        for (int i = 0; i < vos.size(); i++) {
            ChildrenBeanVo vo = vos.get(i);
            mNodeLists.add(new Node(vo.getId() + "", vo.getParentid() + "", false, vo.getTitle(), vo));
            if (!vo.isIsend()) {
                bindData(vo.getChildren());
            }
        }
    }

    @Override
    public void Error(String content) {
        dismissDialog(mAlertDialog);
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    private void clearData() {
        if (mNodeLists == null) {
            mNodeLists = new ArrayList<>();
        } else {
            mNodeLists.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mselectPostion = "-1";
    }

}
