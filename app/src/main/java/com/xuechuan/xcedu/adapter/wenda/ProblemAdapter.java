package com.xuechuan.xcedu.adapter.wenda;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.ui.ImagerActivity;
import com.xuechuan.xcedu.ui.wenda.CreateProblemActivity;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.ProblemDetailVo;
import com.xuechuan.xcedu.weight.MyAlbumWallGridView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.wenda
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.11 下午 2:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder> {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;
    private boolean finish;

    public ProblemAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public void finishStatus(boolean finish) {
        this.finish = finish;
        notifyDataSetChanged();
    }

    /**
     * 设置监听
     */
    private OnSolveClickListener onSolveClickListener;

    public interface OnSolveClickListener {
        public void onClickSolve(ProblemDetailVo.DatasBean vo);
    }

    public void setOnSolveClickListener(OnSolveClickListener onSolveClickListener) {
        this.onSolveClickListener = onSolveClickListener;
    }

    @NonNull
    @Override
    public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProblemViewHolder(mInflater.inflate(R.layout.item_problem_detail_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemViewHolder holder, int position) {
        final ProblemDetailVo.DatasBean vo = (ProblemDetailVo.DatasBean) mListDatas.get(position);
        boolean isadmin = vo.isIsadmin();
        if (isadmin) {
            show(holder, true);
            if (StringUtil.isEmpty(vo.getTeacherimg())) {
                holder.mImgTeacherHead.setImageResource(R.mipmap.common_icon_defaultpicture_mini);
            } else {
                MyAppliction.getInstance().displayImages(holder.mImgTeacherHead, vo.getTeacherimg(), false);
            }
            holder.mTvLiftMainContent.setText(vo.getContent());
            holder.mTvTeacherNike.setText(vo.getTeachername());
            if (vo.getImgs() == null || vo.getImgs().isEmpty()) {
                holder.mGvLiftImg.setVisibility(View.GONE);
            } else {
                final List<String> list = vo.getImgs();
                ImagerAdapter adapter = new ImagerAdapter(mContext, list);
                holder.mGvLiftImg.setAdapter(adapter);
                holder.mGvLiftImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = ImagerActivity.start_Intent(mContext, list.get(position), ImagerActivity.NET);
                        mContext.startActivity(intent);
                    }
                });
            }
          /*  if (position == mListDatas.size() - 1) {
                if (vo.getProblemstatus() !=3)
                    holder.mLlLiftMainButtom.setVisibility(View.VISIBLE);
            } else {
                holder.mLlLiftMainButtom.setVisibility(View.GONE);
            }*/
            if (finish) {
                holder.mLlLiftMainButtom.setVisibility(View.GONE);
            } else {
                holder.mLlLiftMainButtom.setVisibility(View.VISIBLE);
            }
            holder.mRlProblemAsked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//追问
                    Intent intent = CreateProblemActivity.start_Intent(mContext, vo.getProblemid(), false);
                    mContext.startActivity(intent);
                }
            });
            holder.mRlProblemResolved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//已解决
                    if (onSolveClickListener != null) {
                        onSolveClickListener.onClickSolve(vo);
                    }
                }
            });

        } else {
            show(holder, false);
            holder.mTvRightMainContent.setText(vo.getContent());
            if (vo.getImgs() == null || vo.getImgs().isEmpty()) {
                holder.mGvRightImg.setVisibility(View.GONE);
            } else {
                final List<String> list = vo.getImgs();
                ImagerAdapter adapter = new ImagerAdapter(mContext, list);
                holder.mGvRightImg.setAdapter(adapter);
                holder.mGvRightImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = ImagerActivity.start_Intent(mContext, list.get(position), ImagerActivity.NET);
                        mContext.startActivity(intent);
                    }
                });
            }
        }

    }

    private void show(ProblemViewHolder holder, boolean show) {
        holder.mLlTeacherLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.mLlLiftMain.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.mLlRightMain.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class ProblemViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgTeacherHead;
        public TextView mTvTeacherNike;
        public LinearLayout mLlTeacherLayout;
        public TextView mTvLiftMainContent;
        public MyAlbumWallGridView mGvLiftImg;
        public RelativeLayout mRlProblemAsked;
        public RelativeLayout mRlProblemResolved;
        public LinearLayout mLlLiftMainButtom;
        public LinearLayout mLlLiftMain;
        public TextView mTvRightMainContent;
        public MyAlbumWallGridView mGvRightImg;
        public LinearLayout mLlRightMain;

        public ProblemViewHolder(View itemView) {
            super(itemView);
            this.mImgTeacherHead = (ImageView) itemView.findViewById(R.id.img_teacher_head);
            this.mTvTeacherNike = (TextView) itemView.findViewById(R.id.tv_teacher_nike);
            this.mLlTeacherLayout = (LinearLayout) itemView.findViewById(R.id.ll_teacher_layout);
            this.mTvLiftMainContent = (TextView) itemView.findViewById(R.id.tv_lift_main_content);
            this.mGvLiftImg = (MyAlbumWallGridView) itemView.findViewById(R.id.gv_lift_img);
            this.mRlProblemAsked = (RelativeLayout) itemView.findViewById(R.id.rl_problem_asked);
            this.mRlProblemResolved = (RelativeLayout) itemView.findViewById(R.id.rl_problem_resolved);
            this.mLlLiftMainButtom = (LinearLayout) itemView.findViewById(R.id.ll_lift_main_buttom);
            this.mLlLiftMain = (LinearLayout) itemView.findViewById(R.id.ll_lift_main);
            this.mTvRightMainContent = (TextView) itemView.findViewById(R.id.tv_right_main_content);
            this.mGvRightImg = (MyAlbumWallGridView) itemView.findViewById(R.id.gv_right_img);
            this.mLlRightMain = (LinearLayout) itemView.findViewById(R.id.ll_right_main);
        }
    }

}
