package com.xuechuan.xcedu.adapter.wenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.MyProblemVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.wenda
 * @Description: 待解决
 * @author: L-BackPacker
 * @date: 2019/1/9 21:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class SolveDaAdapter extends RecyclerView.Adapter<SolveDaAdapter.DaiJieDaViewHolder> {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;

    public SolveDaAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(MyProblemVo.DatasBean o);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DaiJieDaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaiJieDaViewHolder(mInflater.inflate(R.layout.item_daijie_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull DaiJieDaViewHolder holder, int position) {
        final MyProblemVo.DatasBean vo = (MyProblemVo.DatasBean) mListDatas.get(position);
        if (position == mListDatas.size() - 1) {
            holder.mSeLineTwo.setVisibility(View.VISIBLE);
        } else {
            holder.mSeLineTwo.setVisibility(View.GONE);
        }

        holder.mTvWendaoItemoneTitle.setText(vo.getContent());
        holder.mViewwendaoItemoneCircle.setVisibility(vo.isIsread() ? View.GONE : View.VISIBLE);
        switch (vo.getProblemstatus()) {
            case 2:
                holder.mTvWendaoItemoneStatus.setText("已回答");
                holder.mTvWendaoItemoneStatus.setTextColor(mContext.getResources().getColor(R.color.green));
                holder.mTvWendaoItemoneStatus.setBackgroundResource(R.drawable.daijie_item_tv_bg);
                break;
            default:
                holder.mTvWendaoItemoneStatus.setText("待回答");
                holder.mTvWendaoItemoneStatus.setTextColor(mContext.getResources().getColor(R.color.tv_orange));
                holder.mTvWendaoItemoneStatus.setBackgroundResource(R.drawable.daijie_item_tv_orange_bg);
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

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class DaiJieDaViewHolder extends RecyclerView.ViewHolder {
        public Space mSeLineOne;
        public View mViewwendaoItemoneCircle;
        public TextView mTvWendaoItemoneTitle;
        public TextView mTvWendaoItemoneStatus;
        public Space mSeLineTwo;

        public DaiJieDaViewHolder(View itemView) {
            super(itemView);
            this.mSeLineOne = (Space) itemView.findViewById(R.id.se_line_one);
            this.mViewwendaoItemoneCircle = (View) itemView.findViewById(R.id.viewwendao_itemone_circle);
            this.mTvWendaoItemoneTitle = (TextView) itemView.findViewById(R.id.tv_wendao_itemone_title);
            this.mTvWendaoItemoneStatus = (TextView) itemView.findViewById(R.id.tv_wendao_itemone_status);
            this.mSeLineTwo = (Space) itemView.findViewById(R.id.se_line_two);
        }
    }

}
