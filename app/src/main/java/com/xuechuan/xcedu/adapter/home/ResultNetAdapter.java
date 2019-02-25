package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.NetAllVo;
import com.xuechuan.xcedu.vo.TeachersBean;
import com.xuechuan.xcedu.weight.FlowLayout;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.home
 * @Description: 搜索网课适配器
 * @author: L-BackPacker
 * @date: 2018.11.22 下午 3:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ResultNetAdapter extends BaseRecyclerAdapter<ResultNetAdapter.ResultNetViewHolde>  {
    private Context mContext;
    private List<?> mListDatas;
    private final LayoutInflater mInflater;

    public ResultNetAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(NetAllVo.DatasBean vo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ResultNetViewHolde getViewHolder(View view) {
        return new ResultNetViewHolde(view);
    }

    @Override
    public ResultNetViewHolde onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_reuslt_net, null);
        ResultNetViewHolde holde = new ResultNetViewHolde(view);
        return holde;
    }

    @Override
    public void onBindViewHolder(ResultNetViewHolde holder, int position, boolean isItem) {
      final   NetAllVo.DatasBean vo = (NetAllVo.DatasBean) mListDatas.get(position);
        holder.mTvHomeItemTitle.setText(vo.getName());
        holder.mFlTeacher.removeAllViews();
        List<TeachersBean> teachers = vo.getTeachers();
        if (teachers != null && !teachers.isEmpty())
            for (int i = 0; i < teachers.size(); i++) {
                TeachersBean teacherVo = teachers.get(i);
                View root = mInflater.inflate(R.layout.item_home_net_teacher, holder.mFlTeacher, false);
                ImageView imagHead = (ImageView) root.findViewById(R.id.iv_home_teacher);
                if (!StringUtil.isEmpty(teacherVo.getHeadimg())) {
                    MyAppliction.getInstance().displayImages(imagHead, teacherVo.getHeadimg(), true);
                } else {
                    imagHead.setImageResource(R.drawable.ic_m_hear);
                }
                TextView tvName = (TextView) root.findViewById(R.id.tv_home_teacherName);
                tvName.setText(teacherVo.getName());
                holder.mFlTeacher.addView(root);
            }

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
    public int getAdapterItemCount() {
        return mListDatas.size();
    }


    public class ResultNetViewHolde extends RecyclerView.ViewHolder {
        public TextView mTvHomeItemTitle;
        public TextView mTvHomeItemTag;
        public FlowLayout mFlTeacher;
        public TextView mTvHomeItemLastTitle;
        public TextView mTvHomeItemNumber;

        public ResultNetViewHolde(View itemView) {
            super(itemView);
            this.mTvHomeItemTitle = (TextView) itemView.findViewById(R.id.tv_home_item_Title);
            this.mTvHomeItemTag = (TextView) itemView.findViewById(R.id.tv_home_item_tag);
            this.mFlTeacher = (FlowLayout) itemView.findViewById(R.id.fl_teacher);
            this.mTvHomeItemLastTitle = (TextView) itemView.findViewById(R.id.tv_home_item_last_title);
            this.mTvHomeItemNumber = (TextView) itemView.findViewById(R.id.tv_home_item_number);
        }
    }
}
