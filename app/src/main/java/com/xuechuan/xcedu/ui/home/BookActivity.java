package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.BookHomeAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.BookHomeVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BookActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 教材页面
 * @author: L-BackPacker
 * @date: 2018/4/19 16:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class BookActivity extends BaseActivity implements View.OnClickListener {
    private static String PARAMP = "PARAMP";
    private static String PARAMP1 = "PARAMP";
    private String params;
    private String params1;
    private Context mContext;
    private RecyclerView mRlvBookContent;
    private AlertDialog showDialog;

    public static Intent newInstance(Context context, String param, String param1) {
        Intent intent = new Intent(context, BookActivity.class);
        intent.putExtra(PARAMP, param);
        intent.putExtra(PARAMP, param1);
        return intent;
    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_book);
//        initView();
//    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_book);
        if (getIntent() != null) {
            params = getIntent().getStringExtra(PARAMP);
            params1 = getIntent().getStringExtra(PARAMP1);
        }
        initView();
        initData();
    }



    private void bindAdapter(List<BookHomeVo.DatasBean> vo) {
        BookHomeAdapter bookHomeAdapter = new BookHomeAdapter(mContext, vo);
        setGridLayoutManger(mContext,mRlvBookContent,2);
        mRlvBookContent.setAdapter(bookHomeAdapter);

        bookHomeAdapter.setClickListener(new BookHomeAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                BookHomeVo.DatasBean vo = (BookHomeVo.DatasBean) obj;
                Intent intent = BookInfomActivity.newInstance(mContext, String.valueOf(vo.getId()));
                intent.putExtra(BookInfomActivity.CSTR_EXTRA_TITLE_STR, vo.getName());
                startActivity(intent);
            }
        });


    }

    private void initData() {
        HomeService service = new HomeService(mContext);
        showDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        service.requestCourse(1, new StringCallBackView() {
            @Override
            public void onSuccess(String message) {
                dismissDialog(showDialog);
//                String message = response.body().toString();
                L.d("教程首页内容", message);
                Gson gson = new Gson();
                BookHomeVo vo = gson.fromJson(message, BookHomeVo.class);
                if (vo.getStatus().getCode() == 200) {
                    List<BookHomeVo.DatasBean> datas = vo.getDatas();
                    bindAdapter(datas);

                } else {
                    T.showToast(mContext, getStringWithId(R.string.net_error));
//                    T.showToast(mContext, vo.getStatus().getMessage());
                }

            }

            @Override
            public void onError(String response) {
                dismissDialog(showDialog);
                L.w(response);
                T.showToast(mContext, getStringWithId(R.string.net_error));
            }
        });
    }

    private void initView() {
        mContext = this;
        mRlvBookContent = (RecyclerView) findViewById(R.id.rlv_book_content);
        mRlvBookContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


    }
}
