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
import com.xuechuan.xcedu.event.NetMyPlayEvent;
import com.xuechuan.xcedu.event.NetMyPlayTrySeeEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.ItemSelectVo;
import com.xuechuan.xcedu.vo.SelectVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 用户购买的课程表
 * @author: L-BackPacker
 * @date: 2018/5/15 15:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetMyTableAdapter extends BaseRecyclerAdapter<NetMyTableAdapter.ViewHolder> {
    private Context mContext;
    private List<ChaptersBeanVo> mData;
    private LayoutInflater mInflater;
    //    用户选中集合
    public static List<SelectVo> mSelect = new ArrayList<>();

    public NetMyTableAdapter(Context mContext, List<ChaptersBeanVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        init(mData);
        mInflater = LayoutInflater.from(mContext);
    }

    public void refreshData(String vid, String name) {
        int Zid = -1;
        int Bvid = -1;
        for (int i = 0; i < mData.size(); i++) {
            ChaptersBeanVo vo = mData.get(i);
            for (int k = 0; k < vo.getVideos().size(); k++) {
                VideosBeanVo beanVo = vo.getVideos().get(k);
                if (beanVo.getVid().equals(vid) && beanVo.getVideoname().equals(name)) {
                    Bvid = k;
                    Zid = i;
                    break;
                }
            }
        }
        if (Zid == -1 && Bvid == -1) {
            return;
        }
        init(mData);
        SelectVo vo1 = mSelect.get(Zid);
        ItemSelectVo itemSelect = vo1.getData().get(Bvid);
        itemSelect.setSelect(true);
        notifyDataSetChanged();
    }

    private void init(List<ChaptersBeanVo> mData) {
        if (mSelect.size() > 0) {
            mSelect.clear();
        }
        for (int i = 0; i < mData.size(); i++) {
            ChaptersBeanVo vo = mData.get(i);
            SelectVo selectVo = new SelectVo();
            selectVo.setParenid(i + "");
            ArrayList<ItemSelectVo> list = new ArrayList<>();
            for (int j = 0; j < vo.getVideos().size(); j++) {
                VideosBeanVo beanVo = vo.getVideos().get(j);
                ItemSelectVo itemSelect = new ItemSelectVo();
                itemSelect.setId(j + "");
                itemSelect.setSelect(false);
                itemSelect.setVideoid(beanVo.getVid());
                list.add(itemSelect);
            }
            selectVo.setData(list);
            mSelect.add(selectVo);
        }
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_net_mytable_rlv, null);
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
            if (position == 0) {
                VideosBeanVo videosBeanVo = videos.get(0);
                EventBus.getDefault().postSticky(new NetMyPlayTrySeeEvent(videosBeanVo));
            }
            holder.mRlvNetBookJie.setVisibility(View.VISIBLE);
            if (mSelect.size() <= 0) {
                init(mData);
            }
            bindjieAdaper(holder, videos, position);
        } else {
            holder.mRlvNetBookJie.setVisibility(View.GONE);
        }

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

    private void bindjieAdaper(ViewHolder holder, List<VideosBeanVo> videos, final int aftherPosition) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvNetBookJie.setLayoutManager(manager);
        NetMyTablejiEAdapter adapter = new NetMyTablejiEAdapter(mContext, videos, aftherPosition, mSelect);
        holder.mRlvNetBookJie.setAdapter(adapter);
        adapter.setClickListener(new NetMyTablejiEAdapter.onItemClickListener() {
            @Override
            public void onClickListener(VideosBeanVo vo, int position) {
                init(mData);
                SelectVo vo1 = mSelect.get(aftherPosition);
                ItemSelectVo itemSelect = vo1.getData().get(position);
                itemSelect.setSelect(true);
                EventBus.getDefault().postSticky(new NetMyPlayEvent(vo));
                notifyDataSetChanged();

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
            this.mRlvNetBookJie = (RecyclerView) itemView.findViewById(R.id.rlv_net_book_jie);
            this.mTvNetTitle = (TextView) itemView.findViewById(R.id.tv_net_title);
            this.mIvNetGoorbuy = (ImageView) itemView.findViewById(R.id.iv_net_goorbuy);
        }
    }


}
