package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.ProvincesVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 地址适配器
 * @author: L-BackPacker
 * @date: 2018/4/14 14:19
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AddressAdapter extends BaseRecyclerAdapter<AddressAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<ProvincesVo> mData;
    private Context mContext;
    private final LayoutInflater mInflater;
    private int mSelectPositon = -1;
    public AddressOnClickListener onClickListener;
    private String mAddress = null;

    public interface AddressOnClickListener {
        public void onClickListener(ProvincesVo vo, int position);
    }

    public void setOnClickListener(AddressOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public AddressAdapter(Context mContext, ArrayList<ProvincesVo> mData) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_address_select, null);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        ProvincesVo vo = mData.get(position);
        if (vo == null) {
            return;
        }
        holder.mTvAddress.setText(vo.getName());
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
        if (mAddress != null && vo.getName() != null && mAddress.equals(vo.getName())) {
            holder.mTvAddress.setTextColor(Color.RED);
        } else {
            holder.mTvAddress.setTextColor(Color.BLACK);
        }

    }

    public void setSelectItem(String mAddress, int position) {
        if (position != -1) {
            mSelectPositon = position;
        }
        if (!StringUtil.isEmpty(mAddress)) {
            this.mAddress = mAddress;
        }
    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClickListener((ProvincesVo) v.getTag(), v.getId());
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvAddress;

        public ViewHolder(View rootView) {
            super(rootView);
            this.mTvAddress = (TextView) rootView.findViewById(R.id.tv_address);
        }
    }


}
