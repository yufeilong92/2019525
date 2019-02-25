package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.vo.RecyclerSelectVoOne;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.home
 * @Description: 课程netpop适配器
 * @author: L-BackPacker
 * @date: 2018.11.26 下午 4:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeNetInfomPopAdapter extends RecyclerView.Adapter<HomeNetInfomPopAdapter.NetPopViewHolde> {
    private Context mContext;
    private List<?> mListDatas;
    private final LayoutInflater mInfrom;
    /**
     * 赠品
     */
    public static final String ZENGPINGD_TYPE = "zenpin";
    /**
     * 目录
     */
    public static final String MULU_TYEP = "mului";
    public String mType;
    private String bookName;

    public HomeNetInfomPopAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInfrom = LayoutInflater.from(mContext);
    }

    public void setShowType(String type) {
        this.mType = type;
        notifyDataSetChanged();
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(RecyclerSelectVoOne vo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NetPopViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NetPopViewHolde(mInfrom.inflate(R.layout.item_net_pop_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull NetPopViewHolde holder, int position) {
        if (mType.equals(MULU_TYEP)) {//目录
           final RecyclerSelectVoOne vo = (RecyclerSelectVoOne) mListDatas.get(position);
            holder.mLlZengpingRoot.setVisibility(View.GONE);
            holder.mRabMuluTitle.setVisibility(View.VISIBLE);
            holder.mRabMuluTitle.setChecked(vo.isSelect());
            holder.mRabMuluTitle.setText(vo.getName());
            holder.mRabMuluTitle.setTextColor(vo.isSelect() ? mContext.getResources().getColor(R.color.red_text) :
                    mContext.getResources().getColor(R.color.text_fu_color));
            holder.mRabMuluTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClickItem(vo);
                    }
                }
            });

        } else if (mType.equals(ZENGPINGD_TYPE)) {//赠品
            GiftVo vo = (GiftVo) mListDatas.get(position);
            holder.mLlZengpingRoot.setVisibility(View.VISIBLE);
            holder.mRabMuluTitle.setVisibility(View.GONE);
            holder.mTvNetPopItem.setText(vo.getName());
        }

    }

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class NetPopViewHolde extends RecyclerView.ViewHolder {
        public TextView mTvNetPopItem;
        public LinearLayout mLlZengpingRoot;
        public RadioButton mRabMuluTitle;

        public NetPopViewHolde(View itemView) {
            super(itemView);
            this.mTvNetPopItem = (TextView) itemView.findViewById(R.id.tv_net_pop_item);
            this.mLlZengpingRoot = (LinearLayout) itemView.findViewById(R.id.ll_zengping_root);
            this.mRabMuluTitle = (RadioButton) itemView.findViewById(R.id.rab_mulu_title);
        }
    }

}
