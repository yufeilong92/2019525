package com.xuechuan.xcedu.ui.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.net.NetHomeAllBookAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.NetAllModelImpl;
import com.xuechuan.xcedu.mvp.presenter.NetAllPresenter;
import com.xuechuan.xcedu.mvp.view.NetAllView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.CoursesBeanVo;
import com.xuechuan.xcedu.vo.NetHomeVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetAllBookActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 网课购买页
 * @author: L-BackPacker
 * @date: 2018/5/14 20:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */
public class NetAllBookActivity extends BaseActivity implements NetAllView {

    private RecyclerView mRlvAllNetContent;
    private NetAllPresenter mPresenter;
    private Context mContext;
    private AlertDialog dialog;


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_all_book);
        initView();
        initData();
    }*/

    private void initData() {
        mPresenter = new NetAllPresenter(new NetAllModelImpl(), this);
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresenter.requestAllPresenterList(mContext);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_all_book);
        initView();
        initData();
    }

    private void initView() {
        mContext = this;
        mRlvAllNetContent = (RecyclerView) findViewById(R.id.rlv_all_net_content);

    }

    @Override
    public void PriductSuccess(String con) {
       dismissDialog(dialog);
        L.d(con);
        Gson gson = new Gson();
        NetHomeVo vo = gson.fromJson(con, NetHomeVo.class);
        if (vo.getStatus().getCode() == 200) {
            bindAdaper(vo.getDatas());
        } else {
            T.showToast(mContext, getStringWithId(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    private void bindAdaper(List<CoursesBeanVo> datas) {
        setGridLayoutManger(mContext,mRlvAllNetContent,1);
        mRlvAllNetContent.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.VERTICAL));
        NetHomeAllBookAdapter adapter = new NetHomeAllBookAdapter(mContext, datas);
        mRlvAllNetContent.setAdapter(adapter);
        adapter.setClickListener(new NetHomeAllBookAdapter.onItemClickListener() {
            @Override
            public void onClickListener(CoursesBeanVo obj, int position) {
                Intent intent = NetBookInfomActivity.newInstance(mContext, String.valueOf(obj.getId()));
                intent.putExtra(NetBookInfomActivity.CSTR_EXTRA_TITLE_STR, obj.getName());
                startActivity(intent);
            }
        });

    }

    @Override
    public void PriductError(String con) {
        dismissDialog(dialog);
        T.showToast(mContext,getStringWithId(R.string.net_error));
        L.e(con);
    }

}
