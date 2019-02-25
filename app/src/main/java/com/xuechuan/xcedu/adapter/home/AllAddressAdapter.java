package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.AddressItemsBean;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 所有地址的适配器
 * @author: L-BackPacker
 * @date: 2018/8/16 11:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AllAddressAdapter extends BaseRecyclerAdapter<AllAddressAdapter.ViewHolder> {
    private Context mContext;
    private List<AddressItemsBean> mData;
    private final LayoutInflater mInflater;
    private boolean isShow = true;

    public AllAddressAdapter(Context mContext, List<AddressItemsBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    /**
     * 是否显示默认按钮
     *
     * @param isShow
     */
    public void showDetailButton(boolean isShow) {
        this.isShow = isShow;
        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        public void onClickListener(AddressItemsBean obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private onChbItemClickListener clickChbListener;

    public interface onChbItemClickListener {
        public void onChbClickListener(AddressItemsBean obj, boolean isChecked, int position);
    }

    public void setChbClickListener(onChbItemClickListener clickListener) {
        this.clickChbListener = clickListener;
    }

    private onDelecteItemClickListener clickDelecteListener;

    public interface onDelecteItemClickListener {
        public void onDelecteClickListener(AddressItemsBean obj, int position);
    }

    public void setDelecteClickListener(onDelecteItemClickListener clickListener) {
        this.clickDelecteListener = clickListener;
    }

    private onEditItemClickListener clickEditListener;

    public interface onEditItemClickListener {
        public void onEditClickListener(AddressItemsBean obj, int position);
    }

    public void setEditClickListener(onEditItemClickListener clickListener) {
        this.clickEditListener = clickListener;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.all_addres_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position, boolean isItem) {
        final int mPostion = position;
        final AddressItemsBean itemsBean = mData.get(position);
        boolean isdefault = itemsBean.isIsdefault();
        holder.mChbAddressDefault.setChecked(isdefault);
        StringBuffer buffer = new StringBuffer();
        buffer.append(itemsBean.getProvince());
        buffer.append(itemsBean.getCity());
        buffer.append(itemsBean.getArea());
        buffer.append(itemsBean.getAddress());
        holder.mTvAddresDetial.setText(buffer.toString());
        buffer = null;
        holder.mTvAddresName.setText(itemsBean.getReceivename());
        holder.mTvAddressPhone.setText(itemsBean.getTelphone());
        if (isShow)
            holder.mChbAddressDefault.setVisibility(View.VISIBLE);
        else
            holder.mChbAddressDefault.setVisibility(View.GONE);
        holder.mChbAddressDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!holder.mChbAddressDefault.isPressed()) return;
                if (clickChbListener != null)
                    clickChbListener.onChbClickListener(itemsBean, isChecked, mPostion);
            }
        });
        holder.mIvAddressDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickDelecteListener != null)
                    clickDelecteListener.onDelecteClickListener(itemsBean, mPostion);
            }
        });
        holder.mIvAddressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickEditListener != null) {
                    clickEditListener.onEditClickListener(itemsBean, mPostion);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.onClickListener(itemsBean, mPostion);
            }
        });

    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvAddresName;
        public TextView mTvAddressPhone;
        public ImageView mIvAddrssMake;
        public TextView mTvAddresDetial;
        public CheckBox mChbAddressDefault;
        public ImageView mIvAddressDelect;
        public ImageView mIvAddressEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvAddresName = (TextView) itemView.findViewById(R.id.tv_addres_name);
            this.mTvAddressPhone = (TextView) itemView.findViewById(R.id.tv_address_phone);
            this.mIvAddrssMake = (ImageView) itemView.findViewById(R.id.iv_addrss_make);
            this.mTvAddresDetial = (TextView) itemView.findViewById(R.id.tv_addres_detial);
            this.mChbAddressDefault = (CheckBox) itemView.findViewById(R.id.chb_address_default);
            this.mIvAddressDelect = (ImageView) itemView.findViewById(R.id.iv_address_delect);
            this.mIvAddressEdit = (ImageView) itemView.findViewById(R.id.iv_address_edit);
        }
    }


}
