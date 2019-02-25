package com.xuechuan.xcedu.adapter.net;

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
import com.xuechuan.xcedu.vo.ItemSelectVo;
import com.xuechuan.xcedu.vo.SelectVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 未购买节适配器
 * @author: L-BackPacker
 * @date: 2018/5/15 15:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetTablejiEAdapter extends BaseRecyclerAdapter<NetTablejiEAdapter.ViewHolder> {
    private final int mFatherPosition;
    private final List<SelectVo> mSelect;
    private Context mContext;
    private List<VideosBeanVo> mData;
    private final LayoutInflater mInflater;

    public NetTablejiEAdapter(Context mContext, List<VideosBeanVo> mData, int fatherPosition, List<SelectVo> mSelect) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFatherPosition = fatherPosition;
        this.mSelect = mSelect;
        mInflater = LayoutInflater.from(mContext);
    }

    private NetMyTablejiEAdapter.onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(VideosBeanVo vo, int position);
    }

    public void setClickListener(NetMyTablejiEAdapter.onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_net_table_rlv_jie, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position, boolean isItem) {
        final int mPostion=position;
        final VideosBeanVo vo = mData.get(mPostion);
        SelectVo selectVo = mSelect.get(mFatherPosition);
        ItemSelectVo itemSelect = selectVo.getData().get(mPostion);
        holder.mChbNetPlay.setVisibility(View.VISIBLE);
        if (itemSelect.isSelect()) {
            holder.mChbNetPlay.setChecked(true);
        } else {
            holder.mChbNetPlay.setChecked(false);
        }
        if (vo.isIstrysee()) {
//            holder.mIvNetGoorbuy.setVisibility(View.GONE);
            holder.mIvNetGoorbuy.setImageResource(R.mipmap.bt_sht);

        } else {
            holder.mIvNetGoorbuy.setImageResource(R.mipmap.ic_login_password);
        }
        holder.mTvNetTitle.setText(vo.getVideoname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener!=null){
                    clickListener.onClickListener(vo,mPostion);
                }

            }
        });


    }


    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mChbNetPlay;
        public TextView mTvNetTitle;
        public ImageView mIvNetGoorbuy;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mChbNetPlay = (CheckBox) itemView.findViewById(R.id.chb_net_play_jie);
            this.mTvNetTitle = (TextView) itemView.findViewById(R.id.tv_net_title_jie);
            this.mIvNetGoorbuy = (ImageView) itemView.findViewById(R.id.iv_net_goorbuy_jie);
        }
    }


}
