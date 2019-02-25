package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
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
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.utils.StringUtil;
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
public class InfomAddEvalueAdapter extends RecyclerView.Adapter<InfomAddEvalueAdapter.ViewHolder> implements View.OnClickListener {

    private final String mSelectType;
    private Context mContext;
    private List<EvalueNewVo.DatasBean> mData;
    private final LayoutInflater mInflater;
    private onItemClickListener clickListener;

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


    public InfomAddEvalueAdapter(Context mContext, List<EvalueNewVo.DatasBean> mData, String mSelectType) {
        this.mContext = mContext;
        this.mData = mData;
        this.mSelectType = mSelectType;
        mInflater = LayoutInflater.from(mContext);
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
        ForegroundColorSpan colorSpan = null;
        if (!StringUtil.isEmpty(mSelectType)) {
            if (mSelectType.equals(AnswerActivity.mSelectViewBgYJ)) {//夜间
                colorSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.night_text_color));
                holder.mTvAddEvlaue.setTextColor(getLayoutColor(R.color.night_text_color));
                holder.mRlRoot.setBackgroundColor(getLayoutColor(R.color.night_line_bg));
            } else if (mSelectType.equals(AnswerActivity.mSelectViewBgHY)) {//护眼
                colorSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_title_color));
                holder.mRlRoot.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
                holder.mTvAddEvlaue.setTextColor(getLayoutColor(R.color.text_fu_color));
            } else {//正常
                colorSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_title_color));
                holder.mRlRoot.setBackgroundColor(getLayoutColor(R.color.gray_line));
                holder.mTvAddEvlaue.setTextColor(getLayoutColor(R.color.text_title_color));
            }
        } else {
            colorSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_title_color));
            holder.mRlRoot.setBackgroundColor(getLayoutColor(R.color.gray_line));
            holder.mTvAddEvlaue.setTextColor(getLayoutColor(R.color.text_fu_color));
        }

        EvalueNewVo.DatasBean datasBean = mData.get(position);
        StringBuffer buffer = new StringBuffer();
        buffer.append(datasBean.getNickname());
        buffer.append(": ");
        buffer.append(datasBean.getContent());
        String trim = buffer.toString().trim();
    /*    if (trim.length() > 120) {
            trim = trim.substring(0, 120);
            trim += "...";
        }*/
        int i = trim.indexOf(":") + 1;
        SpannableString data = new SpannableString(trim);
        data.setSpan(colorSpan, 0, i, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        data.setSpan(new StyleSpan(Typeface.BOLD), 0, i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mTvAddEvlaue.setText(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
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

    private int getLayoutColor(int id) {
        return mContext.getResources().getColor(id);
    }

}
