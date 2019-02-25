package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.ExamBeanVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionChapterSqliteVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 模拟考试
 * @author: L-BackPacker
 * @date: 2018/5/4 14:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmExamOldAdapter extends RecyclerView.Adapter<GmExamOldAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<QuestionChapterSqliteVo> mData;
    private final LayoutInflater mInflater;


    private onItemClickListener clickListener;

    @Override
    public void onClick(View v) {
        if (clickListener!=null){
            clickListener.onClickListener(v.getTag(),v.getId());
        }
    }

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public GmExamOldAdapter(Context mContext, List<QuestionChapterSqliteVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_exam_layout, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionChapterSqliteVo vo = mData.get(position);
        holder.mTvExamTitle.setText(vo.getChaptername());
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvExamTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvExamTitle = (TextView) itemView.findViewById(R.id.tv_exam_title);
        }
    }

}
