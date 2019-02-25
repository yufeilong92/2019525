package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer.C;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.SelfEvaluateVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.bank
 * @Description: 画廊布局
 * @author: L-BackPacker
 * @date: 2019.01.05 下午 5:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;
    private GmReadColorManger colorManger;
    private int mPostion;

    public GalleryAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public void doChangerColor(GmReadColorManger colorManger) {
        this.colorManger = colorManger;
        notifyDataSetChanged();
    }

    public void doPostion(int postion) {
        this.mPostion = postion;
        notifyDataSetChanged();
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(int postion);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置监听
     */
    private OnAddEvalueClickListener onAddEvaluesClickListener;

    public interface OnAddEvalueClickListener {
        public void onClickItem(QuestionCaseVo vo, int postion);
    }

    public void setAddEvaluesClickListener(OnAddEvalueClickListener onAddEvaluesClickListener) {
        this.onAddEvaluesClickListener = onAddEvaluesClickListener;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryViewHolder(mInflater.inflate(R.layout.item_gm_gallery_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, final int position) {
        final SelfEvaluateVo vo = (SelfEvaluateVo) mListDatas.get(position);
        if (vo.isAble()) {//已一评分
            showHine(holder, true, false, false);
        } else {//未评分
            if (mPostion == position) {//是否当前
                showHine(holder, false, true, false);
            } else {
                showHine(holder, false, false, true);
            }
        }

        if (mPostion == position) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.mLlGmGalleryRoot.setBackground(mContext.getResources().getDrawable(R.drawable.case_evalue_li_bg_s));
            } else {
                holder.mLlGmGalleryRoot.setBackgroundResource(R.drawable.case_evalue_li_bg_s);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.mLlGmGalleryRoot.setBackground(mContext.getResources().getDrawable(R.drawable.case_evalue_li_bg_n));
            } else {
                holder.mLlGmGalleryRoot.setBackgroundResource(R.drawable.case_evalue_li_bg_n);
            }
        }
        holder.mTvGmGalleryNumber.setText(vo.getScore());
        holder.mTvGmGalleryPostion.setText(String.valueOf(position + 1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPostion == position) {
                    if (onAddEvaluesClickListener != null) {
                        onAddEvaluesClickListener.onClickItem(vo.getCasevo(), position);
                    }
                } else {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClickItem(position);
                    }
                }
            }
        });

    }

    /**
     * 是否评价结果
     *
     * @param holder
     */
    private void showHine(GalleryViewHolder holder, boolean score, boolean add, boolean gray) {
        holder.mLlGmGalleryLayout.setVisibility(score ? View.VISIBLE : View.GONE);
        holder.mLlGmGalleryOneLayout.setVisibility(add ? View.VISIBLE : View.GONE);
        holder.mLlGmGalleryTwoLayout.setVisibility(gray ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvGmGalleryPostion;
        public TextView mTvGmGalleryNumber;
        public TextView mTvGmGalleryUnit;
        public LinearLayout mLlGmGalleryLayout;
        public TextView mTvGmGalleryAdd;
        public TextView mTvGmGalleryAddUnit;
        public LinearLayout mLlGmGalleryOneLayout;
        public LinearLayout mLlGmGalleryTwoLayout;
        public LinearLayout mLlGmGalleryRoot;


        public GalleryViewHolder(View itemView) {
            super(itemView);
            this.mTvGmGalleryPostion = (TextView) itemView.findViewById(R.id.tv_gm_gallery_postion);
            this.mTvGmGalleryNumber = (TextView) itemView.findViewById(R.id.tv_gm_gallery_number);
            this.mTvGmGalleryUnit = (TextView) itemView.findViewById(R.id.tv_gm_gallery_unit);
            this.mLlGmGalleryLayout = (LinearLayout) itemView.findViewById(R.id.ll_gm_gallery_layout);
            this.mTvGmGalleryAdd = (TextView) itemView.findViewById(R.id.tv_gm_gallery_add);
            this.mTvGmGalleryAddUnit = (TextView) itemView.findViewById(R.id.tv_gm_gallery_add_unit);
            this.mLlGmGalleryOneLayout = (LinearLayout) itemView.findViewById(R.id.ll_gm_gallery_one_layout);
            this.mLlGmGalleryTwoLayout = (LinearLayout) itemView.findViewById(R.id.ll_gm_gallery_two_layout);
            this.mLlGmGalleryRoot = (LinearLayout) itemView.findViewById(R.id.ll_gm_gallery_root);
        }
    }


}
