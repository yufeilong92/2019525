package com.xuechuan.xcedu.adapter.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.DownInfomSelectVo;
import com.xuechuan.xcedu.vo.MyDownOverListVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/6 18:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DownDoneInfomHomeAdapter extends RecyclerView.Adapter<DownDoneInfomHomeAdapter.ViewHodler> {
    private Context mContext;
    private List<MyDownOverListVo.PdownListVo> mData;
    private onItemClickListener clickListener;
    private final LayoutInflater mInflater;
    private List<DownInfomSelectVo> mSelectVo;
    public DownDoneInfomHomeAdapter(Context mContext, MyDownOverListVo db, List<DownInfomSelectVo> mDataSelectList) {
        this.mContext = mContext;
        this.mData = db==null?new ArrayList<MyDownOverListVo.PdownListVo>(): db.getDownlist();
        this.mSelectVo=mDataSelectList;
        mInflater = LayoutInflater.from(mContext);

    }
    public interface onItemClickListener {
        public void onClickListener(MyDownOverListVo.zDownListVo vo, int position);
    }


    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    private onItemChbClickListener chbClickListener;

    public interface onItemChbClickListener {
        public void onChecaListener(MyDownOverListVo.zDownListVo db, boolean isCheck, int position);
    }

    public void setChbClickListener(onItemChbClickListener clickListener) {
        this.chbClickListener = clickListener;
    }

    public void setRefreshData( MyDownOverListVo db) {
        if (db != null && db.getDownlist() != null)
            this.mData = db.getDownlist();
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.net_down_list, null);
        ViewHodler hodler = new ViewHodler(inflate);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        MyDownOverListVo.PdownListVo vo = mData.get(position);
        ArrayList<MyDownOverListVo.zDownListVo> downListVos = vo.getzDownlist();
        holder.mTvTitel.setText(vo.getpName());
        if (downListVos == null || downListVos.isEmpty()) {
            holder.mRlvList.setVisibility(View.GONE);
        }
        DownDoneInfomAdapter mInfomAdapter = new DownDoneInfomAdapter(mContext, vo.getzDownlist(), mSelectVo);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mInfomAdapter.setClickListener(new DownDoneInfomAdapter.onItemClickListener() {
            @Override
            public void onClickListener(MyDownOverListVo.zDownListVo vo, int position) {
                if (clickListener != null) {
                    clickListener.onClickListener(vo, position);
                }
            }
        });

        mInfomAdapter.setChbClickListener(new DownDoneInfomAdapter.onItemChbClickListener() {
            @Override
            public void onChecaListener(MyDownOverListVo.zDownListVo db, boolean isCheck, int position) {
                if (chbClickListener!=null){
                    chbClickListener.onChecaListener(db,isCheck,position);
                }
            }
        });
        holder.mRlvList.setLayoutManager(manager);
        holder.mRlvList.setAdapter(mInfomAdapter);


    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        public TextView mTvTitel;
        public LinearLayout mLlLayoutContent;
        public RecyclerView mRlvList;

        public ViewHodler(View itemView) {
            super(itemView);
            this.mTvTitel = (TextView) itemView.findViewById(R.id.tv_titel);
            this.mLlLayoutContent = (LinearLayout) itemView.findViewById(R.id.ll_layout_content);
            this.mRlvList = (RecyclerView) itemView.findViewById(R.id.rlv_list);
        }
    }
}
