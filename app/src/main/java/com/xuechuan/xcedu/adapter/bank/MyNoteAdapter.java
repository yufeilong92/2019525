package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.MyNoteListVo;
import com.xuechuan.xcedu.vo.RecyclerSelectVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.bank
 * @Description: 我的笔记
 * @author: L-BackPacker
 * @date: 2019.01.02 下午 2:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class MyNoteAdapter extends BaseRecyclerAdapter<MyNoteAdapter.NoteViewHolder> {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;
    private ArrayList<RecyclerSelectVo> mSelectVo;

    public MyNoteAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;

        mInflater = LayoutInflater.from(mContext);
    }

    public void setSelectDataVo(ArrayList<RecyclerSelectVo> selectdatas) {
        this.mSelectVo = selectdatas;
        notifyDataSetChanged();
    }

    @Override
    public NoteViewHolder getViewHolder(View view) {
        return new NoteViewHolder(view);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return new NoteViewHolder(mInflater.inflate(R.layout.item_note_recycler_item, null));
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(Object o,int postion);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置监听
     */
    private OnCbClickListener onCbClickListener;

    public interface OnCbClickListener {
        public void onCbClickItem(Object o,int postion,boolean isClick);
    }

    public void setOnCbClickListener(OnCbClickListener onCbClickListener) {
        this.onCbClickListener = onCbClickListener;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, final int position, boolean isItem) {
        final MyNoteListVo.DatasBean vo = (MyNoteListVo.DatasBean) mListDatas.get(position);
        List<MyNoteListVo.DatasBean.TagsBean> tags = vo.getTags();
        if (mSelectVo != null && !mSelectVo.isEmpty()) {
            RecyclerSelectVo selectVo = mSelectVo.get(position);
            if (selectVo.isOpen()) {
                holder.mCbNoteSelect.setVisibility(View.VISIBLE);
                holder.mCbNoteSelect.setChecked(selectVo.isSelect());
            } else {
                holder.mCbNoteSelect.setVisibility(View.GONE);
            }
        }
        if (tags == null || tags.isEmpty()) {
            holder.mTvNoteTesting.setText("");
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < tags.size(); i++) {
            MyNoteListVo.DatasBean.TagsBean bean = tags.get(i);
            if (i == tags.size() - 1) {
                buffer.append(bean.getTagname());
            } else {
                buffer.append(bean.getTagname());
                buffer.append(",");
            }
        }
        holder.mTvNoteTesting.setText(buffer.toString());
        holder.mTvNoteTime.setText(TimeUtil.getYMDT(vo.getCreatetime()));
        holder.mTvNoteTitle.setText(vo.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(vo,position);
                }
            }
        });

        holder.mCbNoteSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCbClickListener!=null){
                    onCbClickListener.onCbClickItem(vo,position,isChecked);
                }
            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return mListDatas.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mCbNoteSelect;
        public TextView mTvNoteTitle;
        public TextView mTvNoteTesting;
        public TextView mTvNoteMake;
        public TextView mTvNoteTime;


        public NoteViewHolder(View itemView) {
            super(itemView);
            this.mCbNoteSelect = (CheckBox) itemView.findViewById(R.id.cb_note_select);
            this.mTvNoteTitle = (TextView) itemView.findViewById(R.id.tv_note_title);
            this.mTvNoteTesting = (TextView) itemView.findViewById(R.id.tv_note_testing);
            this.mTvNoteMake = (TextView) itemView.findViewById(R.id.tv_note_make);
            this.mTvNoteTime = (TextView) itemView.findViewById(R.id.tv_note_time);
        }
    }


}
