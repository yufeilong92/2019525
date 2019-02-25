package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.ArticleVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 文章列表适配器
 * @author: L-BackPacker
 * @date: 2018/4/19 19:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ArticleListAdapter extends BaseRecyclerAdapter<ArticleListAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<ArticleVo> mData;
    private final LayoutInflater mInflater;

    public ArticleListAdapter(Context mContext, List<ArticleVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private String mKey;
    public void setKey(String key){
        this.mKey=key;
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
        ArticleVo vo = mData.get(position);
        if (StringUtil.isEmpty(vo.getThumbnailimg())) {
            holder.mIvItemHomeAll.setVisibility(View.GONE);
        } else {
            holder.mIvItemHomeAll.setVisibility(View.VISIBLE);
            MyAppliction.getInstance().displayImages(holder.mIvItemHomeAll,vo.getThumbnailimg(),false);
        }
        if (StringUtil.isEmpty(mKey)){
            holder.mTvItemHomeTitleAll.setText(vo.getTitle());
        }else {
            Spanned spanned = StringUtil.repaceStr(vo.getTitle(), mKey, null);
            holder.mTvItemHomeTitleAll.setText(spanned);
        }

        holder.mTvItemHomeLookAll.setText(vo.getStringViewcount());
        holder.mTvItemHomeLaudAll.setText(vo.getSupportcount());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvItemHomeTitleAll;
        public ImageView mIvItemHomeAll;
        public TextView mTvItemHomeLookAll;
        public TextView mTvItemHomeLaudAll;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvItemHomeTitleAll = (TextView) itemView.findViewById(R.id.tv_item_home_title_all);
            this.mIvItemHomeAll = (ImageView) itemView.findViewById(R.id.iv_item_home_all);
            this.mTvItemHomeLookAll = (TextView) itemView.findViewById(R.id.tv_item_home_look_all);
            this.mTvItemHomeLaudAll = (TextView) itemView.findViewById(R.id.tv_item_home_laud_all);
        }
    }
}
