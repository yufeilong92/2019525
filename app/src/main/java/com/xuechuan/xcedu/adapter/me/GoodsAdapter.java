package com.xuechuan.xcedu.adapter.me;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.GiftVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 发货确认适配器
 * @author: L-BackPacker
 * @date: 2018/8/20 15:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<GiftVo> mData;
    private final LayoutInflater mInflater;

    public GoodsAdapter(Context mContext, List<GiftVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    private boolean isShowStatus = false;

    public void setIsShowStatus(boolean isShow) {
        isShowStatus = isShow;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener((GiftVo) v.getTag(), v.getId());
        }
    }

    public interface onItemClickListener {
        public void onClickListener(GiftVo obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_take_layout, null);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GiftVo vo = mData.get(position);
        MyAppliction instance = MyAppliction.getInstance();
        if (StringUtil.isEmpty(vo.getCoverimg())) {
            holder.mIvTakeBook.setImageResource(R.mipmap.defaultimg);
        } else
            instance.displayImages(holder.mIvTakeBook, vo.getCoverimg(), false);

        holder.mIvGoodStatus.setVisibility(isShowStatus ? View.VISIBLE : View.GONE);
        holder.mTvTakeValue.setText(String.valueOf(vo.getPrice()));
        holder.mTvTakeTitle.setText(vo.getName());
        holder.mTvTakeNumber.setText(String.valueOf(vo.getNum()));
        holder.mTvY.setVisibility(vo.isMainProducts()?View.VISIBLE:View.INVISIBLE);
        holder.mTvTakeValue.setVisibility(vo.isMainProducts()?View.VISIBLE:View.INVISIBLE);
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvTakeBook;
        public ImageView mIvTakeGift;
        public TextView mTvTakeTitle;
        public TextView mTvY;
        public TextView mTvTakeValue;
        public TextView mTvTakeNumber;
        public ImageView mIvGoodStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvTakeBook = (ImageView) itemView.findViewById(R.id.iv_take_book);
            this.mIvTakeGift = (ImageView) itemView.findViewById(R.id.iv_take_gift);
            this.mTvTakeTitle = (TextView) itemView.findViewById(R.id.tv_take_title);
            this.mTvY = (TextView) itemView.findViewById(R.id.tv_Y);
            this.mTvTakeValue = (TextView) itemView.findViewById(R.id.tv_take_value);
            this.mTvTakeNumber = (TextView) itemView.findViewById(R.id.tv_take_number);
            this.mIvGoodStatus = (ImageView) itemView.findViewById(R.id.iv_good_status);
        }
    }



}
