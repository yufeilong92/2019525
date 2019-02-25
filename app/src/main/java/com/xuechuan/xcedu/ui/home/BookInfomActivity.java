package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.BookJieAdapter;
import com.xuechuan.xcedu.adapter.home.BookOrderAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.contract.BookInfomContract;
import com.xuechuan.xcedu.mvp.model.BookInfomModel;
import com.xuechuan.xcedu.mvp.presenter.BookInfomPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.vo.BookHomePageVo;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BookInfomActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 教材详情页
 * @author: L-BackPacker
 * @date: 2018/4/19 16:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class BookInfomActivity extends BaseActivity implements BookInfomContract.View {
    /**
     * 章
     */
    private RecyclerView mRlvBookinfomZhang;
    /**
     * 节
     */
    private RecyclerView mRlvBookinfomJie;
    /**
     * 类型id
     */
    private static String CEX_INT_ID = "cex_int_id";
    private Context mContext;
    private String mOid;
    private AlertDialog mDialog;
    private BookInfomPresenter mPresenter;

    public static Intent newInstance(Context context, String cex_int_id) {
        Intent intent = new Intent(context, BookInfomActivity.class);
        intent.putExtra(CEX_INT_ID, cex_int_id);
        return intent;
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_book_infom);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(CEX_INT_ID);
        }
        initView();
        initData();
    }
    private void initData() {
        mPresenter = new BookInfomPresenter();
        mPresenter.initModelView(new BookInfomModel(), this);
        mPresenter.RequestCapterListOne(mContext, mOid);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));

    }

    /**
     * 绑定章
     *
     * @param datas
     */
    private void bindOrderData(List<BookHomePageVo.DatasBean> datas) {
        final BookOrderAdapter bookOrderAdapter = new BookOrderAdapter(mContext, datas, BookInfomActivity.this);
        setGridLayoutManger(mContext,mRlvBookinfomZhang,1);
//        mRlvBookinfomZhang.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvBookinfomZhang.setAdapter(bookOrderAdapter);
        bookOrderAdapter.setClickListener(new BookOrderAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                bookOrderAdapter.selectItem(position);
                BookHomePageVo.DatasBean vo = (BookHomePageVo.DatasBean) obj;
                bindJieData(vo.getChildren());
            }
        });
    }


    /**
     * 绑定节数据
     *
     * @param children
     */
    public void bindJieData(List<ChildrenBeanVo> children) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        BookJieAdapter jieOrderAdapter = new BookJieAdapter(mContext,mRlvBookinfomJie, children, manager);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvBookinfomJie.setLayoutManager(manager);
        mRlvBookinfomJie.setAdapter(jieOrderAdapter);
        jieOrderAdapter.setClickListener(new BookJieAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
            }
        });
    }

    private void initView() {
        mRlvBookinfomZhang = (RecyclerView) findViewById(R.id.rlv_bookinfom_zhang);
        mRlvBookinfomJie = (RecyclerView) findViewById(R.id.rlv_bookinfom_jie);
        mContext = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void CapterListSuccess(String success) {
        dismissDialog(mDialog);
        Gson gson = new Gson();
        BookHomePageVo vo = gson.fromJson(success, BookHomePageVo.class);
        if (vo.getStatus().getCode() == 200) {
            bindOrderData(vo.getDatas());
        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void CapterListError(String msg) {
        dismissDialog(mDialog);
        T_ERROR(mContext);
    }
}
