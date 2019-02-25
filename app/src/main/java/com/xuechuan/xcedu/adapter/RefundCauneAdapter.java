package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.SelectRefundVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 退款原因
 * @author: L-BackPacker
 * @date: 2018/8/22 11:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RefundCauneAdapter extends RecyclerView.Adapter<RefundCauneAdapter.ViewHolde> implements View.OnClickListener {
    private Context mContext;
    private List<SelectRefundVo> mData;
    private final LayoutInflater mInflater;
    private onItemClickListener clickListener;

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener((SelectRefundVo) v.getTag(), v.getId());
        }

    }

    public interface onItemClickListener {
        public void onClickListener(SelectRefundVo obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RefundCauneAdapter(Context mContext, List<SelectRefundVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.refund_cause_item, null);
        view.setOnClickListener(this);
        ViewHolde holde = new ViewHolde(view);
        return holde;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
        SelectRefundVo vo = mData.get(position);
        holder.mChbRefundCase.setChecked(vo.isSelect());
        holder.mTvRefundCause.setText(vo.getTitle());
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolde extends RecyclerView.ViewHolder {
        public TextView mTvRefundCause;
        public CheckBox mChbRefundCase;

        public ViewHolde(View itemView) {
            super(itemView);
            this.mTvRefundCause = (TextView) itemView.findViewById(R.id.tv_refund_cause);
            this.mChbRefundCase = (CheckBox) itemView.findViewById(R.id.chb_refund_case);
        }
    }


}
