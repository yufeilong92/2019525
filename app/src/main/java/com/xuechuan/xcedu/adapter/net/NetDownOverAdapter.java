package com.xuechuan.xcedu.adapter.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.NetDownSelectVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 已经缓存
 * @author: L-BackPacker
 * @date: 2018/5/19 8:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetDownOverAdapter extends RecyclerView.Adapter<NetDownOverAdapter.ViewHodle> implements View.OnClickListener {
    private final List<NetDownSelectVo> mSelectvo;
    private Context mContext;
    private List<DownVideoDb> mData;
    private final LayoutInflater mInflater;
    private onItemClickListener clickListener;

    public NetDownOverAdapter(Context mContext, List<DownVideoDb> mData, List<NetDownSelectVo> selectDoneVos) {
        this.mContext = mContext;
        this.mData = mData;
        this.mSelectvo = selectDoneVos;
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener((DownVideoDb) v.getTag(), v.getId());
        }
    }


    public interface onItemClickListener {
        /**
         * @param db
         * @param position 第几个
         */
        public void onClickListener(DownVideoDb db, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    private onItemChbClickListener chbClickListener;

    public interface onItemChbClickListener {
        public void onChecaListener(DownVideoDb db, boolean isCheck, int position);
    }

    public void setChbClickListener(onItemChbClickListener clickListener) {
        this.chbClickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHodle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.net_down_over, null);
        ViewHodle viewHodle = new ViewHodle(view);
        view.setOnClickListener(this);
        return viewHodle;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHodle holder,  int position) {
        final int mPostion=position;
        final DownVideoDb db = mData.get(mPostion);
        holder.mChbNetDownIng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!holder.mChbNetDownIng.isPressed()) return;
                if (chbClickListener != null) {
                    chbClickListener.onChecaListener(db, isChecked, mPostion);
                }
            }
        });
        final int size = db.getDownlist().size();

        for (int i = 0; i < mSelectvo.size(); i++) {
            NetDownSelectVo vo = mSelectvo.get(i);
            if (db.getKid().equals(vo.getId())) {
                if (vo.isShow()) {
                    holder.mChbNetDownIng.setVisibility(View.VISIBLE);
                    if (vo.isSelect()) {
                        holder.mChbNetDownIng.setChecked(true);
                    } else {
                        holder.mChbNetDownIng.setChecked(false);
                    }
                } else {
                    holder.mChbNetDownIng.setVisibility(View.GONE);
                }
            }
        }

        holder.mTvNetDowningNumber.setText(size + "");
        long count = 0;
        for (DownVideoVo vo : db.getDownlist()) {
            long fileSize = vo.getFileSize();
            count += fileSize;
        }
        final String s = Utils.convertFileSizeB(count);
        holder.mTvNetDowningCout.setText(s);
        MyAppliction.getInstance().displayImages(holder.mIvNetDownImg, db.getUrlImg(), false);
        holder.mTvNetDowningTitle.setText(db.getKName());
        holder.itemView.setTag(db);
        holder.itemView.setId(mPostion);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHodle extends RecyclerView.ViewHolder {
        public CheckBox mChbNetDownIng;
        public ImageView mIvNetDownImg;
        public TextView mTvNetDowningTitle;
        public TextView mTvNetDowningNumber;
        public TextView mTvNetDowningCout;

        public ViewHodle(View itemView) {
            super(itemView);
            this.mChbNetDownIng = (CheckBox) itemView.findViewById(R.id.chb_net_down_ing_over);
            this.mIvNetDownImg = (ImageView) itemView.findViewById(R.id.iv_net_down_img_over);
            this.mTvNetDowningTitle = (TextView) itemView.findViewById(R.id.tv_net_downing_title_over);
            this.mTvNetDowningNumber = (TextView) itemView.findViewById(R.id.tv_net_downing_number_over);
            this.mTvNetDowningCout = (TextView) itemView.findViewById(R.id.tv_net_downing_cout_over);
        }
    }

}
