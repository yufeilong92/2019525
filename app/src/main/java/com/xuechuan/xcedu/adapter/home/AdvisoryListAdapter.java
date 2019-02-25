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
import com.xuechuan.xcedu.vo.AdvisoryVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 资讯列表适配器
 * @author: L-BackPacker
 * @date: 2018/4/19 19:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AdvisoryListAdapter extends BaseRecyclerAdapter<AdvisoryListAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<AdvisoryVo> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
        }

        public void setClickListener(onItemClickListener clickListener) {
            this.clickListener = clickListener;
    }

    public AdvisoryListAdapter(Context mContext, List<AdvisoryVo> mData) {
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
        View view = mInflater.inflate(R.layout.item_infomlist_layout, null);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        AdvisoryVo vo = mData.get(position);
        if (StringUtil.isEmpty(vo.getThumbnailimg())){
            holder.mIvItemInfomlistContent.setVisibility(View.GONE);
        }else {
            holder.mIvItemInfomlistContent.setVisibility(View.VISIBLE);
            MyAppliction.getInstance().displayImages(holder.mIvItemInfomlistContent,vo.getThumbnailimg(),false);
        }
        holder.mTvItemInfomlistTitel.setText(vo.getTitle());
        holder.mTvItemInfomlistTime.setText(vo.getPublishdate());
        holder.mTvItemInfomSource.setText(vo.getSource());
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);

    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
         if (clickListener!=null){
             clickListener.onClickListener(v.getTag(),v.getId());
         }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvItemInfomlistTitel;
        public ImageView mIvItemInfomlistContent;
        public TextView mTvItemInfomlistTime;
        public TextView mTvItemInfomSource;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvItemInfomlistTitel = (TextView) itemView.findViewById(R.id.tv_item_infomlist_titel);
            this.mIvItemInfomlistContent = (ImageView) itemView.findViewById(R.id.iv_item_infomlist_content);
            this.mTvItemInfomlistTime = (TextView) itemView.findViewById(R.id.tv_item_infomlist_time);
            this.mTvItemInfomSource = (TextView) itemView.findViewById(R.id.tv_item_infom_source);


        }
    }

}
