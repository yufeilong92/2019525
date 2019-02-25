package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.vo.CaseCardVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.weight.CaseGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.bank
 * @Description: 案例分析答题卡
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 5:19
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CaseAnswerAdapter extends RecyclerView.Adapter<CaseAnswerAdapter.CaseViewHolder> {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;
    private GmReadColorManger mColorManger;
    private List<DoBankSqliteVo> mUserDoBankLists;

    public CaseAnswerAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(int parentid, int childid);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void doChangerColor(GmReadColorManger colorManger) {
        this.mColorManger = colorManger;
        notifyDataSetChanged();
    }

    public void doUserData(List<DoBankSqliteVo> data) {
        this.mUserDoBankLists = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_case_answer, null);
        CaseViewHolder holder = new CaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CaseViewHolder holder, int position) {
        doChangerColorEvent(mColorManger, holder);
        CaseCardVo vo = (CaseCardVo) mListDatas.get(position);
        holder.mTvCaseCardContent.setVisibility(View.VISIBLE);
        StringBuffer buffer = new StringBuffer();
        buffer.append("第");
        buffer.append(String.valueOf(position + 1));
        buffer.append("题");
        holder.mTvCaseCardContent.setText(buffer.toString());
        holder.mGvPopContent.setVisibility(View.VISIBLE);
        List<CaseCardVo> list = vo.getList();
        setCaseAdapter(holder, list, position);
    }

    /**
     * @param holder
     * @param list      数据
     * @param paraentid 父类id
     */
    public void setCaseAdapter(@NonNull CaseViewHolder holder, List<CaseCardVo> list,
                               final int paraentid) {
        GmCaseGridViewAdapter adapter = new GmCaseGridViewAdapter(mContext, list);
        adapter.doEventColor(mColorManger);
        holder.mGvPopContent.setAdapter(adapter);
        adapter.doEventListDatas(mUserDoBankLists);
        adapter.setOnItemClickListener(new GmCaseGridViewAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(int postion) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(paraentid, postion);
                }
            }
        });
    }

    private void doChangerColorEvent(GmReadColorManger mColorManger, CaseViewHolder holder) {
        if (mColorManger == null) return;
        holder.mGvPopContent.setBackgroundColor(mColorManger.getmLayoutBgColor());
        holder.mTvCaseCardContent.setTextColor(mColorManger.getmTextTitleColor());

    }

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class CaseViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvCaseCardContent;
        public CaseGridView mGvPopContent;

        public CaseViewHolder(View itemView) {
            super(itemView);
            this.mTvCaseCardContent = (TextView) itemView.findViewById(R.id.tv_case_card_content);
            this.mGvPopContent = (CaseGridView) itemView.findViewById(R.id.gv_pop_content);
        }
    }


}
