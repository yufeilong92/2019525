package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.ErrOrColListVo;
import com.xuechuan.xcedu.vo.ErrorCollectVo;
import com.xuechuan.xcedu.vo.TaglistsBeanVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 错题收藏列表适配器
 * @author: L-BackPacker
 * @date: 2018/5/3 10:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmErrorTextAdapter extends RecyclerView.Adapter<GmErrorTextAdapter.ViewHodler> implements View.OnClickListener {

    private Context mContext;
    private List<ErrorCollectVo> mData;
    private final LayoutInflater mInflater;

    public GmErrorTextAdapter(Context mContext, List<ErrorCollectVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

   public void refreshData(List<ErrorCollectVo> mData){
       this.mData=mData;
       notifyDataSetChanged();
   }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener((ErrorCollectVo) v.getTag(), v.getId());
        }
    }

    public interface onItemClickListener {
        public void onClickListener(ErrorCollectVo vo, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_error_layout, null);
        ViewHodler hodler = new ViewHodler(view);
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        ErrorCollectVo bean = mData.get(position);
        holder.mTvErrorTitle.setText(bean.getChapter_name());
        int size = bean.getChild().size();
        holder.mTvLookcount.setText(String.valueOf(size));
        int i = bean.getNum() > bean.getChild().size()
                ? bean.getChild().size(): bean.getNum();
        holder.mTvLooknumber.setText(String.valueOf(i));
        holder.itemView.setTag(bean);
        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        public TextView mTvErrorTitle;
        public TextView mTvErrorNumber;
        public TextView mTvLooknumber;
        public TextView mTvLookcount;

        public ViewHodler(View itemView) {
            super(itemView);
            this.mTvErrorTitle = (TextView) itemView.findViewById(R.id.tv_error_title);
            this.mTvErrorNumber = (TextView) itemView.findViewById(R.id.tv_error_number);
            this.mTvLooknumber = (TextView) itemView.findViewById(R.id.tv_looknumber);
            this.mTvLookcount = (TextView) itemView.findViewById(R.id.tv_lookcount);

        }
    }

}
