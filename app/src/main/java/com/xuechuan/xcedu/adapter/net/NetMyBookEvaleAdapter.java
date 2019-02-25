package com.xuechuan.xcedu.adapter.net;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.InfomAddEvalueAdapter;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.ui.EvalueTwoActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.SuppertUtil;
import com.xuechuan.xcedu.utils.TimeSampUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.EvalueNewVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 评价
 * @author: L-BackPacker
 * @date: 2018/5/21 15:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetMyBookEvaleAdapter extends BaseRecyclerAdapter<NetMyBookEvaleAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<EvalueNewVo.DatasBean> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;


    public interface onItemClickListener {
        public void onClickListener(EvalueNewVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public NetMyBookEvaleAdapter(Context mContext, List<EvalueNewVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }


    private onItemChbClickListener clickChbListener;

    public interface onItemChbClickListener {
        public void onClickChbListener(EvalueNewVo.DatasBean bean, boolean isChick, int position);
    }

    public void setChbClickListener(onItemChbClickListener clickListener) {
        this.clickChbListener = clickListener;
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {

        View view = mInflater.inflate(R.layout.item_net_book_evaluate, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, boolean isItem) {
        final EvalueNewVo.DatasBean bean = mData.get(position);
        holder.mTvEvalueUserName.setText(bean.getNickname());
        holder.mChbEvaluaIssupper.setText(bean.getSupportcount() + "");
        holder.mChbEvaluaIssupper.setChecked(bean.isIssupport());
        holder.mTvEvalueContent.setText(bean.getContent());
        String ymdt = TimeUtil.getYMDT(bean.getCreatetime());
        String stamp = TimeSampUtil.getStringTimeStamp(bean.getCreatetime());
        L.e(stamp);
        holder.mTvEvalueTime.setText(stamp);
        if (!StringUtil.isEmpty(bean.getHeadicon())) {
            MyAppliction.getInstance().displayImages(holder.mIvEvaluateHear, bean.getHeadicon(), true);
        } else {
            holder.mIvEvaluateHear.setImageResource(R.mipmap.common_icon_defaultpicture_mini);
        }
        holder.mChbEvaluaIssupper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int number = Integer.parseInt(holder.mChbEvaluaIssupper.getText().toString().trim());
                SuppertUtil util = SuppertUtil.getInstance(mContext);
                if (isChecked) {
                    holder.mChbEvaluaIssupper.setText((number + 1) + "");
                    util.submitSupport(String.valueOf(bean.getId()), "true", DataMessageVo.USERTYPEVC);
                } else {
                    if (number <= 0) {
                        holder.mChbEvaluaIssupper.setText(0 + "");
                    } else {
                        holder.mChbEvaluaIssupper.setText((number - 1) + "");
                    }
                    util.submitSupport(String.valueOf(bean.getId()), "false", DataMessageVo.USERTYPEVC);
//                    notifyItemChanged(position);
                }

            /*
                if (clickChbListener != null) {
                    clickChbListener.onClickChbListener(bean, isChecked, position);
                }
                */

            }
        });
        holder.mTvEvalueEvalue.setText(bean.getCommentcount() + "");
        if (bean.getChildren() == null || bean.getChildren().isEmpty()) {
            holder.mLiAddComment.setVisibility(View.GONE);
        } else {
            holder.mLiAddComment.setVisibility(View.VISIBLE);
            if (bean.getChildren().size() >= 2)
                holder.mTvInfomLookHuifu.setVisibility(View.VISIBLE);
            else
                holder.mTvInfomLookHuifu.setVisibility(View.GONE);
            InfomAddEvalueAdapter adapter = new InfomAddEvalueAdapter(mContext, bean.getChildren(), null);
            GridLayoutManager manager = new GridLayoutManager(mContext, 1);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            holder.mRlvAddEvaluate.setLayoutManager(manager);
            holder.mRlvAddEvaluate.setAdapter(adapter);
            adapter.setClickListener(new InfomAddEvalueAdapter.onItemClickListener() {
                @Override
                public void onClickListener(Object obj, int position) {
                    Intent intent = EvalueTwoActivity.newInstance(mContext, String.valueOf(bean.getTargetid()),
                            String.valueOf(bean.getId()),
                            DataMessageVo.USERTYPEVC,
                            DataMessageVo.VIDEO);
                    mContext.startActivity(intent);
                }
            });
        }
        holder.itemView.setTag(bean);
        holder.itemView.setId(position);

    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener((EvalueNewVo.DatasBean) v.getTag(), v.getId());
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvEvaluateHear;
        public TextView mTvEvalueUserName;
        public TextView mTvEvalueContent;
        public TextView mTvEvalueTime;
        public TextView mTvEvalueEvalue;
        public CheckBox mChbEvaluaIssupper;
        public TextView mTvEvalueSuppernumber;
        public RecyclerView mRlvAddEvaluate;
        public TextView mTvInfomLookHuifu;
        public LinearLayout mLiAddComment;
        public LinearLayout mLlItemRoot;
        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvEvaluateHear = (ImageView) itemView.findViewById(R.id.iv_evaluate_hear);
            this.mTvEvalueUserName = (TextView) itemView.findViewById(R.id.tv_evalue_user_name);
            this.mTvEvalueContent = (TextView) itemView.findViewById(R.id.tv_evalue_content);
            this.mTvEvalueTime = (TextView) itemView.findViewById(R.id.tv_evalue_time);
            this.mTvEvalueEvalue = (TextView) itemView.findViewById(R.id.tv_evalue_evalue);
            this.mChbEvaluaIssupper = (CheckBox) itemView.findViewById(R.id.chb_evalua_issupper);
            this.mTvEvalueSuppernumber = (TextView) itemView.findViewById(R.id.tv_evalue_suppernumber);
            this.mRlvAddEvaluate = (RecyclerView) itemView.findViewById(R.id.rlv_add_evaluate);
            this.mTvInfomLookHuifu = (TextView) itemView.findViewById(R.id.tv_infom_look_huifu);
            this.mLiAddComment = (LinearLayout) itemView.findViewById(R.id.li_add_comment);
            this.mLlItemRoot = (LinearLayout) itemView.findViewById(R.id.ll_item_root);
        }
    }

}
