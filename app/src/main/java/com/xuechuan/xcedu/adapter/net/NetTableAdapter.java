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
import com.xuechuan.xcedu.event.NetPlayEvent;
import com.xuechuan.xcedu.event.NetPlayTrySeeEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.T;
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
 * @Description: 未购买适配器
 * @author: L-BackPacker
 * @date: 2018/5/15 15:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetTableAdapter extends BaseRecyclerAdapter<NetTableAdapter.ViewHolder> {
    private Context mContext;
    private List<ChaptersBeanVo> mData;
    private final LayoutInflater mInflater;
    //    用户选中集合
    public static List<SelectVo> mSelect = new ArrayList<>();

    public NetTableAdapter(Context mContext, List<ChaptersBeanVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
        init(mData);
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
                ItemSelectVo itemSelect = new ItemSelectVo();
                itemSelect.setId(j + "");
                itemSelect.setSelect(false);
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
        View view = mInflater.inflate(R.layout.item_net_table_rlv, null);
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
                if (videosBeanVo.isIstrysee()) {
                    EventBus.getDefault().postSticky(new NetPlayTrySeeEvent(videosBeanVo));
                }
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

    private void bindjieAdaper(ViewHolder holder, List<VideosBeanVo> videos,final int fatherPosition) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvNetBookJie.setLayoutManager(manager);
        NetTablejiEAdapter adapter = new NetTablejiEAdapter(mContext, videos,fatherPosition,mSelect);
        holder.mRlvNetBookJie.setAdapter(adapter);
        adapter.setClickListener(new NetMyTablejiEAdapter.onItemClickListener() {
            @Override
            public void onClickListener(VideosBeanVo vo, int position) {
                if (vo.isIstrysee()) {
                    init(mData);
                    SelectVo vo1 = mSelect.get(fatherPosition);
                    ItemSelectVo itemSelect = vo1.getData().get(position);
                    itemSelect.setSelect(true);
                    notifyDataSetChanged();
                    EventBus.getDefault().postSticky(new NetPlayEvent(vo));
                } else {
                    T.showToast(mContext, "该视频不提供试看");
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
            this.mRlvNetBookJie = (RecyclerView) itemView.findViewById(R.id.rlv_net_book_jie);
            this.mTvNetTitle = (TextView) itemView.findViewById(R.id.tv_net_title);
            this.mIvNetGoorbuy = (ImageView) itemView.findViewById(R.id.iv_net_goorbuy);
        }
    }


}
