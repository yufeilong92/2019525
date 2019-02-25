package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.FeedBackModelImpl;
import com.xuechuan.xcedu.mvp.presenter.FeedBackPresenter;
import com.xuechuan.xcedu.mvp.view.FeedBackView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: FeedBackActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 提交反馈
 * @author: L-BackPacker
 * @date: 2018/5/22 9:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener, FeedBackView {

    private EditText mEtMFeedSuggest;
    private EditText mEtMFeedPhone;
    private Button mBtnMFeedSubmit;
    private Context mContext;
    private FeedBackPresenter mPresenter;
    private AlertDialog alertDialog;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_feed_back);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new FeedBackPresenter(new FeedBackModelImpl(), this);
    }

    private void initView() {
        mContext = this;
        mEtMFeedSuggest = (EditText) findViewById(R.id.et_m_feed_suggest);
        mEtMFeedPhone = (EditText) findViewById(R.id.et_m_feed_phone);
        mBtnMFeedSubmit = (Button) findViewById(R.id.btn_m_feed_submit);
        mBtnMFeedSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_m_feed_submit:
                submit();
                break;
        }
    }

    private void submit() {
        String suggest = getTextStr(mEtMFeedSuggest);
        if (StringUtil.isEmpty(suggest)) {
            T.showToast(mContext, getStringWithId(R.string.content_is_empty));
            return;
        }
        String phone = getTextStr(mEtMFeedPhone);
        alertDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit));
        mPresenter.submitFeedBack(mContext, suggest, phone);
    }

    @Override
    public void feedSuccess(String con) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1)
                finish();
            mEtMFeedSuggest.setText(null);
            mEtMFeedPhone.setText(null);
            T.showToast(mContext, getStringWithId(R.string.feedback_success));
        } else {
            T.showToast(mContext, getString(R.string.submit_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void feedError(String con) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
        L.e(con);
    }

}
