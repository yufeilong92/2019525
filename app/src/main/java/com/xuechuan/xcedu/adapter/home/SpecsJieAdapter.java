package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.RecyclerSelectVo;
import com.xuechuan.xcedu.vo.SpecasJieVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: t规范节点
 * @author: L-BackPacker
 * @date: 2018/4/20 18:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecsJieAdapter extends BaseRecyclerAdapter<SpecsJieAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<SpecasJieVo.DatasBean> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;


    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public SpecsJieAdapter(Context mContext, List<SpecasJieVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    ArrayList<RecyclerSelectVo> mSelectLists;

    public void initSelectVo(ArrayList<RecyclerSelectVo> mSelectLists) {
        this.mSelectLists = mSelectLists;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_specas_jie, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        final SpecasJieVo.DatasBean vo = mData.get(position);
        RecyclerSelectVo selectVo = mSelectLists.get(position);
        holder.mTvSpecaJieTitle.setTextColor(mContext.getResources().getColor(selectVo.isSelect()?R.color.red_text:R.color.text_title_color));
        holder.mTvSpecaJieTitle.setText(vo.getTitle());
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener(v.getTag(), v.getId());
        }
    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvSpecaJieTitle;

        public ViewHolder(View rootView) {
            super(rootView);
            this.mTvSpecaJieTitle = (TextView) rootView.findViewById(R.id.tv_speca_jie_title);
        }

    }
}
