package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.AnswerTableResultAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.vo.AnswerResultEvent;
import com.xuechuan.xcedu.vo.QuestionAllVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
/**
 * @Title:  AnswerResultActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 解答结果页
 * @author: L-BackPacker
 * @date:   2018.10.16 下午 2:12
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.10.16
 */
public class AnswerResultActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvResultNumber;
    private TextView mTvAnswerTime;
    private TextView mTvAnswerLv;
    private RecyclerView mRlvAnswerResultBag;
    private Button mBtnAnswerAgain;
    private Button mBtnAnswerJiexi;
    private Context mContext;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_result);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_answer_result);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mTvResultNumber = (TextView) findViewById(R.id.tv_result_number);
        mTvAnswerTime = (TextView) findViewById(R.id.tv_answer_time);
        mTvAnswerLv = (TextView) findViewById(R.id.tv_answer_lv);
        mRlvAnswerResultBag = (RecyclerView) findViewById(R.id.rlv_answer_result_bag);
        mBtnAnswerAgain = (Button) findViewById(R.id.btn_answer_again);
        mBtnAnswerJiexi = (Button) findViewById(R.id.btn_answer_jiexi);

        mBtnAnswerAgain.setOnClickListener(this);
        mBtnAnswerJiexi.setOnClickListener(this);
        mContext = this;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMain(AnswerResultEvent event) {
        List<QuestionAllVo.DatasBean> beans = event.getmTextDetial();
        initAdapter(beans);
    }
    public void initAdapter(List<QuestionAllVo.DatasBean> beans){
        setGridLayoutManger(mContext,mRlvAnswerResultBag,6);
        AnswerTableResultAdapter adapter = new AnswerTableResultAdapter(AnswerResultActivity.this, beans);
        mRlvAnswerResultBag.setAdapter(adapter);
        adapter.setClickListener(new AnswerTableResultAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                 

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_answer_again:

                break;
            case R.id.btn_answer_jiexi:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
