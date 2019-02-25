package com.xuechuan.xcedu.adapter.home;

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
import com.xuechuan.xcedu.vo.BookBean;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.home
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 2:19
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeTeacherAdapter extends RecyclerView.Adapter<HomeTeacherAdapter.TeacherViewholde> {
    private Context mContext;
    private List<?> mListDatas;
    private final LayoutInflater mInflater;

    public HomeTeacherAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(BookBean vo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TeacherViewholde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherViewholde(mInflater.inflate(R.layout.item_teacher_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewholde holder, int position) {
        final BookBean vo = (BookBean) mListDatas.get(position);
        if (!StringUtil.isEmpty(vo.getImg())) {
            MyAppliction.getInstance().displayImages(holder.mIvTeacherImg, vo.getImg(), false);
        } else {
            holder.mIvTeacherImg.setImageResource(R.mipmap.s_n);
        }
        holder.mTvTeacherValue.setText(String.valueOf(vo.getPrice()));
        holder.mTvTeacherTitle.setText(vo.getTitle());
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

    public class TeacherViewholde extends RecyclerView.ViewHolder {
        public ImageView mIvTeacherImg;
        public TextView mTvTeacherTitle;
        public TextView mTvTeacherTag;
        public TextView mTvTeacherValue;

        public TeacherViewholde(View itemView) {
            super(itemView);
            this.mIvTeacherImg = (ImageView) itemView.findViewById(R.id.iv_teacher_img);
            this.mTvTeacherTitle = (TextView) itemView.findViewById(R.id.tv_teacher_title);
            this.mTvTeacherTag = (TextView) itemView.findViewById(R.id.tv_teacher_tag);
            this.mTvTeacherValue = (TextView) itemView.findViewById(R.id.tv_teacher_value);
        }
    }

}
