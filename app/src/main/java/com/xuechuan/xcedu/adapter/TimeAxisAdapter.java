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
import com.xuechuan.xcedu.vo.OrderTracesVo;
import com.xuechuan.xcedu.vo.SelectRefundVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 时间轴
 * @author: L-BackPacker
 * @date: 2018/8/22 15:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TimeAxisAdapter extends RecyclerView.Adapter<TimeAxisAdapter.ViewHolde> {
    private Context mContext;
    private List<OrderTracesVo.DataBeanX.ResponseBean.OrderexpresslistBean.DataBean> mData;
    private final LayoutInflater mInflater;

    public TimeAxisAdapter(Context mContext, List<OrderTracesVo.DataBeanX.ResponseBean.OrderexpresslistBean.DataBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.time_axis_item, null);
        ViewHolde hodle = new ViewHolde(view);
        return hodle;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
        OrderTracesVo.DataBeanX.ResponseBean.OrderexpresslistBean.DataBean bean = mData.get(position);
        String[] split = bean.getTime().split(" ");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            if (i == split.length - 1) {
                buffer.append(split[i]);
            } else {
                buffer.append(split[i] + "\n");
            }
        }
        holder.mTvTimeData.setText(buffer.toString());
        if (position == 0) {
            holder.mTvTimeUp.setVisibility(View.INVISIBLE);
        }
        holder.mTvTimeAddress.setText(bean.getContext());

        if ((mData.size() - 1) == position) {
            holder.mChnTimeSelect.setChecked(true);
            holder.mTvTimeDown.setVisibility(View.INVISIBLE);
        } else {
            holder.mChnTimeSelect.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolde extends RecyclerView.ViewHolder {
        public TextView mTvTimeData;
        public TextView mTvTimeUp;
        public CheckBox mChnTimeSelect;
        public TextView mTvTimeDown;
        public TextView mTvTimeAddress;

        public ViewHolde(View itemView) {
            super(itemView);
            this.mTvTimeData = (TextView) itemView.findViewById(R.id.tv_time_data);
            this.mTvTimeUp = (TextView) itemView.findViewById(R.id.tv_time_up);
            this.mChnTimeSelect = (CheckBox) itemView.findViewById(R.id.chn_time_select);
            this.mTvTimeDown = (TextView) itemView.findViewById(R.id.tv_time_down);
            this.mTvTimeAddress = (TextView) itemView.findViewById(R.id.tv_time_address);

        }
    }


}
