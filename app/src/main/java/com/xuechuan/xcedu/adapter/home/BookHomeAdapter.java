package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
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
import com.xuechuan.xcedu.vo.BookHomeVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 教材首页
 * @author: L-BackPacker
 * @date: 2018/4/23 9:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BookHomeAdapter extends BaseRecyclerAdapter<BookHomeAdapter.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private List<BookHomeVo.DatasBean> mData;
    private final LayoutInflater mInflater;
    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public BookHomeAdapter(Context mContext, List<BookHomeVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_bookhome_layout, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        BookHomeVo.DatasBean datasBean = mData.get(position);
        holder.mTvBookhomeTitle.setText(datasBean.getName());
        if (!StringUtil.isEmpty(datasBean.getCover())){
            MyAppliction.getInstance().displayImages(holder.mIvBookhomeContent,datasBean.getCover(),false);
        }
        holder.itemView.setId(position);
        holder.itemView.setTag(datasBean);
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
        public ImageView mIvBookhomeContent;
        public TextView mTvBookhomeTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvBookhomeContent = (ImageView) itemView.findViewById(R.id.iv_bookhome_content);
            this.mTvBookhomeTitle = (TextView) itemView.findViewById(R.id.tv_bookhome_title);
        }
    }
}
