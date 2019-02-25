package com.xuechuan.xcedu.adapter.wenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.HankPickVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.wenda
 * @Description: 精选
 * @author: L-BackPacker
 * @date: 2019/1/9 21:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class HandpickAdapter extends RecyclerView.Adapter<HandpickAdapter.WenDaoViewHolder> {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;

    public HandpickAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(HankPickVo.DatasBean o);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public WenDaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WenDaoViewHolder(mInflater.inflate(R.layout.item_hankpick_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull WenDaoViewHolder holder, int position) {
        final HankPickVo.DatasBean vo = (HankPickVo.DatasBean) mListDatas.get(position);
        holder.mTvHankpickTitle.setText(vo.getContent());
        if (StringUtil.isEmpty(vo.getLastcomment())) {
            holder.mTvHankpickContent.setVisibility(View.GONE);
        } else {
            holder.mTvHankpickContent.setVisibility(View.VISIBLE);
        }
        holder.mTvHankpickContent.setText(vo.getLastcomment());
        List<String> imgs = vo.getImgs();
        if (imgs == null || imgs.isEmpty()) {
            holder.mLlHankpickLayout.setVisibility(View.GONE);
        } else {
            holder.mLlHankpickLayout.setVisibility(View.VISIBLE);
            switch (imgs.size()) {
                case 1:
                    MyAppliction.getInstance().displayImages(holder.mImgHankpickOne, imgs.get(0), false);
                    showImg(holder, true, false, false);
                    break;
                case 2:
                    MyAppliction.getInstance().displayImages(holder.mImgHankpickOne, imgs.get(0), false);
                    MyAppliction.getInstance().displayImages(holder.mImgHankpickTwo, imgs.get(1), false);
                    showImg(holder, true, true, false);
                    break;
                case 3:
                    MyAppliction.getInstance().displayImages(holder.mImgHankpickOne, imgs.get(0), false);
                    MyAppliction.getInstance().displayImages(holder.mImgHankpickTwo, imgs.get(1), false);
                    MyAppliction.getInstance().displayImages(holder.mImgHankpickThree, imgs.get(2), false);
                    showImg(holder, true, true, true);
                    break;
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(vo);
                }
            }
        });

    }

    private void showImg(WenDaoViewHolder holder, boolean one, boolean two, boolean three) {
        holder.mImgHankpickOne.setVisibility(one ? View.VISIBLE : View.INVISIBLE);
        holder.mImgHankpickTwo.setVisibility(two ? View.VISIBLE : View.INVISIBLE);
        holder.mImgHankpickThree.setVisibility(three ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class WenDaoViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvHankpickTitle;
        public ImageView mImgHankpickOne;
        public ImageView mImgHankpickTwo;
        public ImageView mImgHankpickThree;
        public LinearLayout mLlHankpickLayout;
        public TextView mTvHankpickContent;

        public WenDaoViewHolder(View itemView) {
            super(itemView);
            this.mTvHankpickTitle = (TextView) itemView.findViewById(R.id.tv_hankpick_title);
            this.mImgHankpickOne = (ImageView) itemView.findViewById(R.id.img_hankpick_one);
            this.mImgHankpickTwo = (ImageView) itemView.findViewById(R.id.img_hankpick_two);
            this.mImgHankpickThree = (ImageView) itemView.findViewById(R.id.img_hankpick_three);
            this.mLlHankpickLayout = (LinearLayout) itemView.findViewById(R.id.ll_hankpick_layout);
            this.mTvHankpickContent = (TextView) itemView.findViewById(R.id.tv_hankpick_content);
        }
    }


}
