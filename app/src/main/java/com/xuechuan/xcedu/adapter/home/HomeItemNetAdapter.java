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
import com.xuechuan.xcedu.vo.ClassBean;
import com.xuechuan.xcedu.vo.TeachersBean;
import com.xuechuan.xcedu.weight.FlowLayout;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.home
 * @Description: 首页网课模块
 * @author: L-BackPacker
 * @date: 2018.11.22 上午 10:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeItemNetAdapter extends RecyclerView.Adapter<HomeItemNetAdapter.NetViewHolde> {
    private Context mContext;
    private List<?> mListDatas;
    private final LayoutInflater mInflater;

    public HomeItemNetAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(ClassBean o);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NetViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_item_net, null);
        return new NetViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetViewHolde holder, int position) {
        final ClassBean vo = (ClassBean) mListDatas.get(position);
        List<TeachersBean> teachers = vo.getTeachers();
        holder.mFlTeacher.removeAllViews();
        if (teachers != null && !teachers.isEmpty()) {
            for (int i = 0; i < teachers.size(); i++) {
                TeachersBean teacherVo = teachers.get(i);
                View itemvo = mInflater.inflate(R.layout.item_home_net_teacher, holder.mFlTeacher, false);
                ImageView imagHead = (ImageView) itemvo.findViewById(R.id.iv_home_teacher);
                if (!StringUtil.isEmpty(teacherVo.getHeadimg())) {
                    MyAppliction.getInstance().displayImages(imagHead, teacherVo.getHeadimg(), true);
                } else {
                    imagHead.setImageResource(R.drawable.ic_m_hear);
                }
                TextView tvName = (TextView) itemvo.findViewById(R.id.tv_home_teacherName);
                tvName.setText(teacherVo.getName());
                holder.mFlTeacher.addView(itemvo);
            }
        }
        holder.mTvHomeItemTitle.setText(vo.getName());
        //价格
        holder.mTvHomeItemLastTitle.setText(vo.getPrice() == 0 ? "免费" : "￥" + String.valueOf(vo.getPrice()));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("已报");
        stringBuffer.append(String.valueOf(vo.getPeople()));
        stringBuffer.append("人");
        holder.mTvHomeItemNumber.setText(stringBuffer.toString().trim());
        holder.mTvHomeItemTag.setText(vo.getSubtitle());
        holder.mTvHomeItemTag.setVisibility(StringUtil.isEmpty(vo.getSubtitle()) ? View.GONE : View.VISIBLE);
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

    public class NetViewHolde extends RecyclerView.ViewHolder {
        public TextView mTvHomeItemTitle;
        public TextView mTvHomeItemTag;
        public FlowLayout mFlTeacher;
        public TextView mTvHomeItemLastTitle;
        public TextView mTvHomeItemNumber;

        public NetViewHolde(View itemView) {
            super(itemView);
            this.mTvHomeItemTitle = (TextView) itemView.findViewById(R.id.tv_home_item_Title);
            this.mTvHomeItemTag = (TextView) itemView.findViewById(R.id.tv_home_item_tag);
            this.mFlTeacher = (FlowLayout) itemView.findViewById(R.id.fl_teacher);
            this.mTvHomeItemLastTitle = (TextView) itemView.findViewById(R.id.tv_home_item_last_title);
            this.mTvHomeItemNumber = (TextView) itemView.findViewById(R.id.tv_home_item_number);
        }
    }

}
