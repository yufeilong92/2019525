package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.EveryDayHistoryVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.bank
 * @Description: 历史记录
 * @author: L-BackPacker
 * @date: 2018.12.29 下午 4:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;

    public HistoryAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(EveryDayHistoryVo.DatasBean  o);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(mInflater.inflate(R.layout.item_history_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        final EveryDayHistoryVo.DatasBean vo = (EveryDayHistoryVo.DatasBean) mListDatas.get(position);
        holder.mTvHistoryKey.setText(vo.getKeyword());
        holder.mTvHistoryTime.setText(TimeUtil.getYMDT(vo.getDate()));
        holder.mBtnHistoryExercise.setOnClickListener(new View.OnClickListener() {
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

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvHistoryTime;
        public TextView mTvHistoryKey;
        public Button mBtnHistoryExercise;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            this.mTvHistoryTime = (TextView) itemView.findViewById(R.id.tv_history_time);
            this.mTvHistoryKey = (TextView) itemView.findViewById(R.id.tv_history_key);
            this.mBtnHistoryExercise = (Button) itemView.findViewById(R.id.btn_history_exercise);
        }
    }


}
