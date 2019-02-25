package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;
import com.xuechuan.xcedu.vo.RecyclerSelectVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 节点下节点数据
 * @author: L-BackPacker
 * @date: 2018/4/23 11:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BookJieJieAdapter extends RecyclerView.Adapter<BookJieJieAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ChildrenBeanVo> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;
    private ArrayList<RecyclerSelectVo> mSelectVo;

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener(v.getTag(), v.getId());
        }
    }

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public BookJieJieAdapter(Context mContext, List<ChildrenBeanVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void initSelectVo(ArrayList<RecyclerSelectVo> mSelectVo) {
        this.mSelectVo = mSelectVo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home_jiejie_layout, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChildrenBeanVo vo = mData.get(position);
        if (mSelectVo != null && !mSelectVo.isEmpty()) {
            RecyclerSelectVo selectVo = mSelectVo.get(position);
            holder.mTvBookhomeJiejieTitle.setTextColor(mContext.getResources().getColor(selectVo.isSelect() ? R.color.red_text : R.color.text_title_color));
        }
        holder.mTvBookhomeJiejieTitle.setText(vo.getTitle());
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvBookhomeJiejieTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvBookhomeJiejieTitle = (TextView) itemView.findViewById(R.id.tv_bookhome_jiejie_title);

        }
    }

}
