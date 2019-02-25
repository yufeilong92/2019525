package com.xuechuan.xcedu.adapter.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.net
 * @Description: 年份
 * @author: L-BackPacker
 * @date: 2018.11.29 下午 5:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetYearAdapter extends RecyclerView.Adapter<NetYearAdapter.YearViewholder> {
    private Context mContext;
    private List<Integer> mListDatas;
    private final LayoutInflater mInflater;
    private Integer mYear;

    public NetYearAdapter(Context mContext, List<Integer> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);

    }

    public void setYear(Integer year) {
        mYear = year;
        notifyDataSetChanged();
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(int o);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public YearViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YearViewholder(mInflater.inflate(R.layout.item_net_year, null));
    }

    @Override
    public void onBindViewHolder(@NonNull YearViewholder holder, int position) {
        final int integer = mListDatas.get(position);
        holder.mTvYearNet.setText(String.valueOf(integer));
        holder.mTvYearNet.setTextColor(mYear.equals(integer) ?mContext.getResources().getColor(R.color.red_text ) : mContext.getResources().getColor(R.color.text_title_color ));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(integer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class YearViewholder extends RecyclerView.ViewHolder {
        public TextView mTvYearNet;

        public YearViewholder(View itemView) {
            super(itemView);
            this.mTvYearNet = (TextView) itemView.findViewById(R.id.tv_year_net);

        }
    }

}
