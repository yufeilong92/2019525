package com.xuechuan.xcedu.adapter.net;

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
import com.xuechuan.xcedu.vo.ClassBean;
import com.xuechuan.xcedu.vo.LastviewBean;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.net
 * @Description: 网课列表适配
 * @author: L-BackPacker
 * @date: 2018.12.03 下午 4:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmNetListAdapter extends BaseRecyclerAdapter<GmNetListAdapter.GmViewHolder> {
    private Context mContext;
    private List<?> mListDatas;
    private final LayoutInflater mInflater;
    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(ClassBean vo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GmNetListAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);

    }


    @Override
    public GmViewHolder getViewHolder(View view) {
        return new GmViewHolder(view);
    }

    @Override
    public GmViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return new GmViewHolder(mInflater.inflate(R.layout.item_gm_net_list, null));
    }

    @Override
    public void onBindViewHolder(GmViewHolder holder, int position, boolean isItem) {
        final ClassBean vo = (ClassBean) mListDatas.get(position);
        if (StringUtil.isEmpty(vo.getCoverimg())) {
            holder.mIvNetMyitemTitleImg.setImageResource(R.mipmap.s_n);
        } else {
            MyAppliction.getInstance().displayImages(holder.mIvNetMyitemTitleImg, vo.getCoverimg(), false);
        }
        holder.mTvNetMyhomeTitle.setText(vo.getName());
        LastviewBean lastview = vo.getLastview();
        StringBuffer buffer = new StringBuffer();
        buffer.append("上次看到：");
        buffer.append(lastview.getVideoname());
        holder.mTvNetMyhomePricle.setVisibility(StringUtil.isEmpty(lastview.getVideoname()) ? View.GONE : View.VISIBLE);
        holder.mTvNetMyhomePricle.setText(buffer.toString().trim());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(vo);
                }
            }
        });


    }

    @Override
    public int getAdapterItemCount() {
        return mListDatas.size();
    }

    public class GmViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvNetMyitemTitleImg;
        public TextView mTvNetMyhomeTitle;
        public TextView mTvNetMyhomePricle;

        public GmViewHolder(View itemView) {
            super(itemView);
            this.mIvNetMyitemTitleImg = (ImageView) itemView.findViewById(R.id.iv_net_myitem_title_img);
            this.mTvNetMyhomeTitle = (TextView) itemView.findViewById(R.id.tv_net_myhome_title);
            this.mTvNetMyhomePricle = (TextView) itemView.findViewById(R.id.tv_net_myhome_pricle);
        }
    }

}
