package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.SharedSeletResultListUtil;
import com.xuechuan.xcedu.vo.QuestionAllVo;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 答题卡按钮
 * @author: L-BackPacker
 * @date: 2018/5/2 18:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerTableResultAdapter extends RecyclerView.Adapter<AnswerTableResultAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<QuestionAllVo.DatasBean> mData;
    private final LayoutInflater mInflater;
    private onItemClickListener clickListener;
    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public AnswerTableResultAdapter(Context mContext, List<QuestionAllVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.answer_layout_table, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        QuestionAllVo.DatasBean bean = mData.get(position);
        List<UseSelectItemInfomVo> user = SharedSeletResultListUtil.getInstance().getUser();
        holder.mTvPopAnswerSelect.setText((position + 1) + "");
        String id = String.valueOf(bean.getId());
        for (int i = 0; i < user.size(); i++) {
            UseSelectItemInfomVo vo = user.get(i);
//            判断是否做过
            if (id.equalsIgnoreCase(String.valueOf(vo.getId()))) {//做过
                String status = vo.getItemStatus();
                if (status.equals("0")) {//正确
                    holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_n);
                    holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.text_tab_right));
                } else if (status.equals("1")) {//错误
                    holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.btn_answer_bg_error);
                    holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.red_text));
                } else if (status.equals("2")) {
                    holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_miss);
                    holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.text_tab_miss_color));
                } else {
                    holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
                    holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
                }
                break;
            } else {//没有
                holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
                holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
            }
        }
        holder.itemView.setTag(bean);
        holder.itemView.setId(position);

    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener(v.getTag(), v.getId());
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvPopAnswerSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvPopAnswerSelect = (TextView) itemView.findViewById(R.id.tv_pop_answer_select);

        }
    }


}
