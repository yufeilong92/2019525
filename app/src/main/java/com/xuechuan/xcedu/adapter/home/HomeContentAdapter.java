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
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.AdvisoryBean;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 资讯适配器
 * @author: L-BackPacker
 * @date: 2018/4/18 20:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeContentAdapter extends BaseRecyclerAdapter<HomeContentAdapter.ViewHolder> implements View.OnClickListener {

    private List<AdvisoryBean> mData;
    private Context mContext;
    private final LayoutInflater mInflater;

    /**
     * @param mData    数据
     * @param mContext
     */
    public HomeContentAdapter(Context mContext, List<AdvisoryBean> mData) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int  position);

    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_home_layout, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        AdvisoryBean vo = mData.get(position);
        holder.mTvItemHomeTitelContent.setText(vo.getTitle());
        String ymdt = TimeUtil.getYMDT(vo.getPublishdate());
        holder.mTvItemHomeTimeContent.setText(ymdt);
        if (StringUtil.isEmpty(vo.getThumbnailimg())) {
            holder.mIvItemHomeContent.setVisibility(View.GONE);
        } else {
            MyAppliction.getInstance().displayImages(holder.mIvItemHomeContent, vo.getThumbnailimg(), false);
            holder.mIvItemHomeContent.setVisibility(View.VISIBLE);
        }
        holder.mTvItemHomeAddressContent.setText(vo.getSource());
        holder.itemView.setId(position);
        holder.itemView.setTag(vo);

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
        public TextView mTvItemHomeTitelContent;
        public ImageView mIvItemHomeContent;
        public TextView mTvItemHomeTimeContent;
        public TextView mTvItemHomeAddressContent;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvItemHomeTitelContent = (TextView) itemView.findViewById(R.id.tv_item_home_titel_content);
            this.mIvItemHomeContent = (ImageView) itemView.findViewById(R.id.iv_item_home_content);
            this.mTvItemHomeTimeContent = (TextView) itemView.findViewById(R.id.tv_item_home_time_content);
            this.mTvItemHomeAddressContent = (TextView) itemView.findViewById(R.id.tv_item_home_address_content);
        }
    }

}
