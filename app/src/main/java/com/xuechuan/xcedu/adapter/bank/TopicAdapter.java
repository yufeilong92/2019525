package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.bank
 * @Description: 答题卡布局
 * @author: L-BackPacker
 * @date: 2018.12.13 下午 2:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TopicAdapter extends RecyclerView.Adapter {
    public static final int ANSWER_LAYOUT = 106;
    public static int ANSWER_GM = ANSWER_LAYOUT;

    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;
    private ArrayList<DoBankSqliteVo> mDoLists;
    private GmReadColorManger mColorManger;

    public TopicAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public void doEventListDatas(ArrayList<DoBankSqliteVo> list) {
        this.mDoLists = list;
        notifyDataSetChanged();
    }

    public void doChangerColor(GmReadColorManger colorManger) {
        this.mColorManger = colorManger;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ANSWER_LAYOUT) {//答题卡布局
            return new AnswerViewHolder(mInflater.inflate(R.layout.pop_item_answer, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (ANSWER_GM == ANSWER_LAYOUT) {
            AnswerViewHolder holdere = (AnswerViewHolder) holder;
            setAnswerLayou(holdere);
        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                ANSWER_GM = ANSWER_LAYOUT;
                break;
        }

        return ANSWER_GM;

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setAnswerLayou(AnswerViewHolder holder) {
        if (mColorManger != null) {
            holder.mTvPopNew.setTextColor(mColorManger.getmTextTitleColor());
            holder.mTvPopCount.setTextColor(mColorManger.getmTextTitleColor());
            holder.mTvTextPopRight.setTextColor(mColorManger.getmTextFuColor());
            holder.mTvTextPopError.setTextColor(mColorManger.getmTextFuColor());
            holder.mTvTextPopRegression.setTextColor(mColorManger.getmTextFuColor());
            holder.mBtnPopAnswerSumbit.setTextColor(mColorManger.getmTextTitleColor());
            holder.mTvLine.setTextColor(mColorManger.getmTextTitleColor());
            holder.mLlPopLayout.setBackgroundColor(mColorManger.getmLayoutBgColor());
        }
        GmGridViewAdapter adapter = new GmGridViewAdapter(mContext, mListDatas);
        holder.mGvPopContent.setAdapter(adapter);
        adapter.doEventListDatas(mDoLists);
        adapter.notifyDataSetChanged();


    }

    private class AnswerViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvPopNew;
        public TextView mTvPopCount;
        public TextView mTvTextPopRight;
        public TextView mTvLine;
        public TextView mTvTextPopError;
        public TextView mTvTextPopRegression;
        public Button mBtnPopAnswerSumbit;
        public RecyclerView mRlvPopContent;
        public GridView mGvPopContent;
        public LinearLayout mLlPopLayout;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            this.mTvPopNew = (TextView) itemView.findViewById(R.id.tv_pop_new);
            this.mTvPopCount = (TextView) itemView.findViewById(R.id.tv_pop_count);
            this.mTvTextPopRight = (TextView) itemView.findViewById(R.id.tv_text_pop_right);
            this.mTvTextPopError = (TextView) itemView.findViewById(R.id.tv_text_pop_error);
            this.mTvLine = (TextView) itemView.findViewById(R.id.tv_line);
            this.mTvTextPopRegression = (TextView) itemView.findViewById(R.id.tv_text_pop_regression);
            this.mBtnPopAnswerSumbit = (Button) itemView.findViewById(R.id.btn_pop_answer_sumbit);
            this.mRlvPopContent = (RecyclerView) itemView.findViewById(R.id.rlv_pop_content);
            this.mGvPopContent = (GridView) itemView.findViewById(R.id.gv_pop_content);
            this.mLlPopLayout = (LinearLayout) itemView.findViewById(R.id.ll_pop_layout);

        }
    }


}
