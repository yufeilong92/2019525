package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.vo.EvalueNewVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 评价界面追加
 * @author: L-BackPacker
 * @date: 2018/7/31 14:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmInfomEvalueAdapter extends RecyclerView.Adapter<GmInfomEvalueAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<EvalueNewVo.DatasBean> mData;
    private final LayoutInflater mInflater;
    private onItemClickListener clickListener;
    private GmReadColorManger mColorManger;

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener(null, 0);
        }
    }

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public GmInfomEvalueAdapter(Context mContext, List<EvalueNewVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    public void doChangeColor(GmReadColorManger colorManger) {
        this.mColorManger = colorManger;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.infom_add_item, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForegroundColorSpan colorSpan = doChangerColor(holder);
        EvalueNewVo.DatasBean vo = mData.get(position);
        StringBuffer buffer = new StringBuffer();
        buffer.append(vo.getNickname());
        buffer.append(": ");
        buffer.append(vo.getContent());
        String content = buffer.toString();
        int i = content.indexOf(":") + 1;
        SpannableString sps = new SpannableString(content);
        sps.setSpan(colorSpan,0,i, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sps.setSpan(new StyleSpan(Typeface.BOLD),0,i,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mTvAddEvlaue.setText(sps);

    }

    private ForegroundColorSpan doChangerColor(ViewHolder holder) {
        ForegroundColorSpan colorSpan = null;
        if (mColorManger != null) {
            colorSpan=new ForegroundColorSpan(mColorManger.getmTextTitleColor());
            holder.mTvAddEvlaue.setTextColor(mColorManger.getmTextTitleColor());
            holder.mRlRoot.setBackgroundColor(mColorManger.getmCutLineColor());

        }else {
            colorSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_title_color));
            holder.mRlRoot.setBackgroundColor(getLayoutColor(R.color.gray_line));
            holder.mTvAddEvlaue.setTextColor(getLayoutColor(R.color.text_fu_color));
        }
        return colorSpan;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    private int getLayoutColor(int id) {
        return mContext.getResources().getColor(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvAddEvlaue;
        public RelativeLayout mRlRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvAddEvlaue = (TextView) itemView.findViewById(R.id.tv_add_evlaue);
            this.mRlRoot = (RelativeLayout) itemView.findViewById(R.id.rl_root);
        }
    }

}
