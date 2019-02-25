package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.sqlitedb.DoLogProgressSqliteHelp;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoLogProgreeSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionTagRelationSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.TagSqliteVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 规范
 * @author: L-BackPacker
 * @date: 2018/5/5 16:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmSpeciaListAdapter extends BaseRecyclerAdapter<GmSpeciaListAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<TagSqliteVo> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;
    private DoLogProgressSqliteHelp mProgressHelp;

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener(v.getTag(), v.getId());
        }
    }

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public GmSpeciaListAdapter(Context mContext, List<TagSqliteVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setHelp(DoLogProgressSqliteHelp help) {
        this.mProgressHelp = help;
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_specail_layout, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        TagSqliteVo bean = mData.get(position);
        DoLogProgreeSqliteVo look = mProgressHelp.findLookWithTidChapterId(bean.getTagid(), bean.getCourseid(), DataMessageVo.SPECIAL_TWO);
        if (look != null &&!StringUtil.isEmpty(look.getNumber())) {
            holder.mTvLookSpecail.setText(look.getNumber());
        } else {
            holder.mTvLookSpecail.setText(String.valueOf(bean.getLooknum()));
        }
        holder.mTvCountSpecail.setText(String.valueOf(bean.getQuestionnum()));
        holder.mTvSpecailMark.setText(bean.getTagname());
        holder.itemView.setTag(bean);
        holder.itemView.setId(position);
    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvSpecailMark;
        public TextView mTvSpecailMark;
        public ImageView mIvSpeaclNext;
        public TextView mTvLookSpecail;
        public TextView mTvCountSpecail;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvSpecailMark = (ImageView) itemView.findViewById(R.id.iv_specail_mark);
            this.mTvSpecailMark = (TextView) itemView.findViewById(R.id.tv_specail_mark);
            this.mIvSpeaclNext = (ImageView) itemView.findViewById(R.id.iv_speacl_next);
            this.mTvLookSpecail = (TextView) itemView.findViewById(R.id.tv_look_specail);
            this.mTvCountSpecail = (TextView) itemView.findViewById(R.id.tv_count_specail);
        }
    }


}
