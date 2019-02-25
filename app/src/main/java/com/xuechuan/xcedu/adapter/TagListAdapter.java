package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.ArticleBean;
import com.xuechuan.xcedu.vo.ArticleVo;
import com.xuechuan.xcedu.vo.TagListVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 文章适配器
 * @author: L-BackPacker
 * @date: 2018/4/19 11:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TagListAdapter extends BaseRecyclerAdapter<TagListAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ArticleVo> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public TagListAdapter(Context mContext, List<ArticleVo> mData) {
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
        View view = mInflater.inflate(R.layout.item_home_all_layout, null);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        ArticleVo bean = mData.get(position);
        holder.mTvItemHomeTitleAll.setText(bean.getTitle());
        holder.mTvItemHomeLookAll.setText(bean.getViewcount() + "");
        holder.mTvItemHomeLaudAll.setText(bean.getSupportcount() + "");
        if (StringUtil.isEmpty(bean.getThumbnailimg())) {
            holder.mIvItemHomeAll.setVisibility(View.GONE);
        } else {
            holder.mIvItemHomeAll.setVisibility(View.VISIBLE);
            MyAppliction.getInstance().displayImages(holder.mIvItemHomeAll,bean.getThumbnailimg(), false);
        }
        holder.mChbIsSupper.setChecked(bean.isIssupport());
        holder.itemView.setTag(bean);
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
        public TextView mTvItemHomeTitleAll;
        public TextView mTvItemHomeLookAll;
        public CheckBox mChbIsSupper;
        public TextView mTvItemHomeLaudAll;
        public ImageView mIvItemHomeAll;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvItemHomeTitleAll = (TextView) itemView.findViewById(R.id.tv_item_home_title_all);
            this.mTvItemHomeLookAll = (TextView) itemView.findViewById(R.id.tv_item_home_look_all);
            this.mChbIsSupper = (CheckBox) itemView.findViewById(R.id.chb_isSupper);
            this.mTvItemHomeLaudAll = (TextView) itemView.findViewById(R.id.tv_item_home_laud_all);
            this.mIvItemHomeAll = (ImageView) itemView.findViewById(R.id.iv_item_home_all);

        }
    }

}
