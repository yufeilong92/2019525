package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SearchBookVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.home
 * @Description: 搜索图书适配器
 * @author: L-BackPacker
 * @date: 2018.11.22 下午 2:57
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ResultBookAdapter extends BaseRecyclerAdapter<ResultBookAdapter.ResultBookViewHolde> {
    private Context mContext;
    private List<Object> mListDatas;
    private final LayoutInflater mInflater;

    public ResultBookAdapter(Context mContext, List<Object> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(SearchBookVo.DatasBean vo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ResultBookViewHolde getViewHolder(View view) {
        return new ResultBookViewHolde(view);
    }

    @Override
    public ResultBookViewHolde onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return new ResultBookViewHolde(mInflater.inflate(R.layout.item_result_book, null));
    }

    @Override
    public void onBindViewHolder(ResultBookViewHolde holder, int position, boolean isItem) {
        final SearchBookVo.DatasBean vo = (SearchBookVo.DatasBean) mListDatas.get(position);
        if (StringUtil.isEmpty(vo.getImg())) {
            holder.mIvResultBookImg.setImageResource(R.mipmap.s_n);
        } else {
            MyAppliction.getInstance().displayImages(holder.mIvResultBookImg, vo.getImg(), false);
        }
        holder.mTvResultBookValue.setText(String.valueOf(vo.getPrice()));
        holder.mTvReusltBookTitle.setText(vo.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(vo);
                }
            }
        });

    }

    @Override
    public int getAdapterItemCount() {
        return mListDatas.size();
    }


    public class ResultBookViewHolde extends RecyclerView.ViewHolder {
        public ImageView mIvResultBookImg;
        public TextView mTvReusltBookTitle;
        public TextView mTvReusltBookTag;
        public TextView mTvResultBookValue;

        public ResultBookViewHolde(View itemView) {
            super(itemView);
            this.mIvResultBookImg = (ImageView) itemView.findViewById(R.id.iv_result_book_img);
            this.mTvReusltBookTitle = (TextView) itemView.findViewById(R.id.tv_reuslt_book_title);
            this.mTvReusltBookTag = (TextView) itemView.findViewById(R.id.tv_reuslt_book_tag);
            this.mTvResultBookValue = (TextView) itemView.findViewById(R.id.tv_result_book_value);
        }
    }


}
