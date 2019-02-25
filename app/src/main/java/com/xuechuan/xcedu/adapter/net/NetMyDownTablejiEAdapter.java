package com.xuechuan.xcedu.adapter.net;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 节点适配器
 * @author: L-BackPacker
 * @date: 2018/5/15 15:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetMyDownTablejiEAdapter extends BaseRecyclerAdapter<NetMyDownTablejiEAdapter.ViewHolder> {
    private final int kid;
    private Context mContext;
    private List<VideosBeanVo> mData;
    private final LayoutInflater mInflater;

    public NetMyDownTablejiEAdapter(Context mContext, List<VideosBeanVo> mData, int kid) {
        this.mContext = mContext;
        this.mData = mData;
        this.kid = kid;
        mInflater = LayoutInflater.from(mContext);

    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(VideosBeanVo vo, int position);
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
        View view = mInflater.inflate(R.layout.item_net_mydowntable_rlv_jie, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, boolean isItem) {
        final VideosBeanVo vo = mData.get(position);
        holder.mTvNetTitle.setText(vo.getVideoname());
//        DownVideoDb dbList = DbHelperDownAssist.getInstance().queryUserDownInfomWithKid(String.valueOf(kid));
        DownVideoDb dbList = MyAppliction.getInstance().getDownDao().queryUserDownInfomWithKid(String.valueOf(kid));
        holder.mTvNetTitle.setTextColor(mContext.getResources().getColor(R.color.black));
        if (dbList == null) {
            holder.mTvNetTitle.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            for (DownVideoVo videoVo : dbList.getDownlist()) {//遍历是否有已经缓存的
                if (videoVo.getPid().equals(String.valueOf(vo.getChapterid())) && videoVo.getZid().equals(String.valueOf(vo.getVideoid()))) {
                    String status = videoVo.getStatus();
                    if (status.equals("0")) {
                        holder.mTvPoPDownStatus.setText("已下载");
                    } else if (status.equals("1")) {
                        holder.mTvPoPDownStatus.setText("等待缓存");
                    } else if (status.equals("2")) {
                        holder.mTvPoPDownStatus.setText("等待缓存");
                    }
                    holder.mTvNetTitle.setTextColor(mContext.getResources().getColor(R.color.hint_text));
                }
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isEmpty(holder.mTvPoPDownStatus.getText().toString().trim())) {
                    T.showToast(mContext, "已经添加到缓存队列了");
                    return;
                }
                if (clickListener != null) {
                    clickListener.onClickListener(vo, position);
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
        public TextView mTvPoPDownStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvNetTitle = (TextView) itemView.findViewById(R.id.tv_net_mydowntitle);
            this.mTvPoPDownStatus = (TextView) itemView.findViewById(R.id.tv_pop_down_status);
        }
    }


}
