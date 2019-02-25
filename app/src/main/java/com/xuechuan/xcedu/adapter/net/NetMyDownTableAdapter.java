package com.xuechuan.xcedu.adapter.net;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 弹框下载器
 * @author: L-BackPacker
 * @date: 2018/5/15 15:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetMyDownTableAdapter extends BaseRecyclerAdapter<NetMyDownTableAdapter.ViewHolder> {
    private final int kId;
    private Context mContext;
    private List<ChaptersBeanVo> mData;
    private LayoutInflater mInflater;


    public NetMyDownTableAdapter(Context mContext, List<ChaptersBeanVo> mData, int id) {
        this.mContext = mContext;
        this.mData = mData;
        this.kId=id;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(ChaptersBeanVo obj, int position);

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
        View view = mInflater.inflate(R.layout.item_net_mydowntable_rlv, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position, boolean isItem) {
        ChaptersBeanVo vo = mData.get(position);
        holder.mIvNetGoorbuy.setImageResource(R.mipmap.ic_more_go);
        holder.mTvNetTitle.setText(vo.getChaptername());
        List<VideosBeanVo> videos = vo.getVideos();
        if (videos != null && !videos.isEmpty()) {
            holder.mRlvNetBookJie.setVisibility(View.VISIBLE);
            bindjieAdaper(holder, videos, vo);
        } else {
            holder.mRlvNetBookJie.setVisibility(View.GONE);
        }
        hine();

    }

    private void hine() {
    /*holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (holder.mRlvNetBookJie.getVisibility() == View.GONE) {
                holder.mRlvNetBookJie.setVisibility(View.VISIBLE);
                holder.mIvNetGoorbuy.setImageResource(R.mipmap.ic_spread_gray);
            } else {
                holder.mRlvNetBookJie.setVisibility(View.GONE);
                holder.mIvNetGoorbuy.setImageResource(R.mipmap.ic_more_go);
            }
        }
    });*/
    }

    private void bindjieAdaper(ViewHolder holder, final List<VideosBeanVo> videos, final ChaptersBeanVo aftherPosition) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvNetBookJie.setLayoutManager(manager);
        NetMyDownTablejiEAdapter adapter = new NetMyDownTablejiEAdapter(mContext, videos, kId);
        holder.mRlvNetBookJie.setAdapter(adapter);
        adapter.setClickListener(new NetMyDownTablejiEAdapter.onItemClickListener() {
            @Override
            public void onClickListener(VideosBeanVo vo, int position) {
                if (clickListener != null) {
                    clickListener.onClickListener(aftherPosition, position);
                }
            }
        });

    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvNetTitle;
        public ImageView mIvNetGoorbuy;
        public RecyclerView mRlvNetBookJie;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mRlvNetBookJie = (RecyclerView) itemView.findViewById(R.id.rlv_net_downbook_jie);
            this.mTvNetTitle = (TextView) itemView.findViewById(R.id.tv_net_downtitle);
            this.mIvNetGoorbuy = (ImageView) itemView.findViewById(R.id.iv_net_downgoorbuy);
        }
    }


}
