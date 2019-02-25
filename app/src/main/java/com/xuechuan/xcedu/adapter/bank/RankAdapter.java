package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.TimeTools;
import com.xuechuan.xcedu.vo.EveryDayRankVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.bank
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.29 下午 3:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;

    public RankAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RankViewHolder(mInflater.inflate(R.layout.rank_recycler_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        EveryDayRankVo.DatasBean vo = (EveryDayRankVo.DatasBean) mListDatas.get(position);
        if (position == 0) {
            setTextColor(holder,true);
            showHine(holder, true, false);
            setImg(holder, R.mipmap.qb_mei_fst);
        } else if (position == 1) {
            setTextColor(holder,true);
            showHine(holder, true, false);
            setImg(holder, R.mipmap.qb_mei_snd);
        } else if (position == 2) {
            setTextColor(holder,true);
            showHine(holder, true, false);
            setImg(holder, R.mipmap.qb_mei_thrd);
        } else {
            setTextColor(holder,false);
            showHine(holder, false, true);
            holder.mTvRankNumber.setText(String.valueOf(position + 1));
        }
        if (StringUtil.isEmpty(vo.getHeadicon())) {
            holder.mIvRankHead.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.common_icon_defaultpicture_mini));
        } else {
            MyAppliction.getInstance().displayImages(holder.mIvRankHead, vo.getHeadicon(), true);
        }
        holder.mTvRankNickname.setText(vo.getNickname());
        String time = "--:--:--";
        if (vo.getDuration() != 0) {
            time = TimeTools.getCountTimeByLongOne(vo.getDuration());
        }
        holder.mTvRankTime.setText(time);

    }

    private void setTextColor(RankViewHolder holder, boolean main) {
        holder.mTvRankTime.setTextColor(mContext.getResources().getColor(main ? R.color.rank_main : R.color.white));
    }

    private void setImg(RankViewHolder holder, int id) {
        holder.mIvRankPai.setImageDrawable(mContext.getResources().getDrawable(id));
    }

    private void showHine(RankViewHolder holder, boolean main, boolean fu) {
        holder.mIvRankPai.setVisibility(main ? View.VISIBLE : View.GONE);
        holder.mTvRankNumber.setVisibility(fu ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class RankViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvRankPai;
        public TextView mTvRankNumber;
        public ImageView mIvRankHead;
        public TextView mTvRankNickname;
        public TextView mTvRankTime;

        public RankViewHolder(View itemView) {
            super(itemView);
            this.mIvRankPai = (ImageView) itemView.findViewById(R.id.iv_rank_pai);
            this.mTvRankNumber = (TextView) itemView.findViewById(R.id.tv_rank_number);
            this.mIvRankHead = (ImageView) itemView.findViewById(R.id.iv_rank_head);
            this.mTvRankNickname = (TextView) itemView.findViewById(R.id.tv_rank_nickname);
            this.mTvRankTime = (TextView) itemView.findViewById(R.id.tv_rank_time);
        }
    }

}
