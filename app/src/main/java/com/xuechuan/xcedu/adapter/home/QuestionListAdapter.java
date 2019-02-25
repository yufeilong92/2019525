package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.ResultQuesitonVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;


import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 查询结果
 * @author: L-BackPacker
 * @date: 2018/4/19 19:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QuestionListAdapter extends BaseRecyclerAdapter<QuestionListAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<QuestionSqliteVo> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public QuestionListAdapter(Context mContext, List<QuestionSqliteVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private String mKey;

    public void setKeyStr(String key) {
        this.mKey = key;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_questionlist_layout, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        QuestionSqliteVo vo = mData.get(position);
        if (StringUtil.isEmpty(mKey)) {
            holder.mTvItemInfomlistTitel.setText(vo.getQuestionString());
        } else {
            Spanned str = StringUtil.repaceStr(vo.getQuestionString(), mKey, null);
            holder.mTvItemInfomlistTitel.setText(str);
        }
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);

    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener(v.getTag(), v.getId());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvItemInfomlistTitel;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvItemInfomlistTitel = (TextView) itemView.findViewById(R.id.tv_result_title);
        }
    }

}
