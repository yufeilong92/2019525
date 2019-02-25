package com.xuechuan.xcedu.adapter.bank;

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
import com.xuechuan.xcedu.event.EvalueTwoEvent;
import com.xuechuan.xcedu.ui.EvalueTwoActivity;
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.SuppertUtil;
import com.xuechuan.xcedu.utils.TimeSampUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.EvalueNewVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 评价适配器
 * @author: L-BackPacker
 * @date: 2018/4/25 10:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerEvaluateAdapter extends BaseRecyclerAdapter<AnswerEvaluateAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<EvalueNewVo.DatasBean> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;
    /**
     * 模式
     */
    private String mSelectType;


    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public AnswerEvaluateAdapter(Context mContext, List<EvalueNewVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemChbClickListener clickChbListener;

    public interface onItemChbClickListener {
        public void onClickChbListener(Object obj, boolean isCheck, int position);
    }

    public void setClickChbListener(onItemChbClickListener clickListener) {
        this.clickChbListener = clickListener;
    }

    /**
     * 设置背景
     *
     * @param id
     */
    public void setBGLayout(String id) {
        this.mSelectType = id;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {

        View view = mInflater.inflate(R.layout.item_answer_evaluate, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, boolean isItem) {
        final EvalueNewVo.DatasBean bean = mData.get(position);
        if (!StringUtil.isEmpty(mSelectType)) {
            if (mSelectType.equals(AnswerActivity.mSelectViewBgYJ)) {//夜间
                holder.mLiItemRoot.setBackgroundColor(getLayoutColor(R.color.night_layout_bg));
                holder.mTvEvalueUserName.setTextColor(getLayoutColor(R.color.night_text_color));
                holder.mTvEvalueContent.setTextColor(getLayoutColor(R.color.night_text_color));
                holder.mTvEvalueTime.setTextColor(getLayoutColor(R.color.night_text_color));
                holder.mTvEvalueSuppernumber.setTextColor(getLayoutColor(R.color.night_text_color));
                holder.mTvEvalueEvalue.setTextColor(getLayoutColor(R.color.night_text_color));
                holder.mViewLine.setBackgroundColor(getLayoutColor(R.color.night_line_bg));

                holder.mLiAddComment.setBackgroundColor(getLayoutColor(R.color.night_layout_bg));
                holder.mRlvAddEvaluate.setBackgroundColor(getLayoutColor(R.color.night_layout_bg));
                holder.mTvInfomLookHuifu.setTextColor(getLayoutColor(R.color.night_text_color));
                holder.mLiLookHuifu.setBackgroundColor(getLayoutColor(R.color.night_line_bg));

            } else if (mSelectType.equals(AnswerActivity.mSelectViewBgHY)) {//护眼
                holder.mLiItemRoot.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
                holder.mTvEvalueUserName.setTextColor(getLayoutColor(R.color.black));
                holder.mTvEvalueContent.setTextColor(getLayoutColor(R.color.text_title_color));
                holder.mTvEvalueTime.setTextColor(getLayoutColor(R.color.text_fu_color));
                holder.mTvEvalueSuppernumber.setTextColor(getLayoutColor(R.color.text_fu_color));
                holder.mTvEvalueEvalue.setTextColor(getLayoutColor(R.color.text_fu_color));
                holder.mViewLine.setBackgroundColor(getLayoutColor(R.color.eye_line_bg));

                holder.mLiAddComment.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
                holder.mRlvAddEvaluate.setBackgroundColor(getLayoutColor(R.color.gray_line));
                holder.mTvInfomLookHuifu.setTextColor(getLayoutColor(R.color.text_fu_color));
                holder.mLiLookHuifu.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));

            } else {//正常
                holder.mLiItemRoot.setBackgroundColor(getLayoutColor(R.color.white));
                holder.mTvEvalueUserName.setTextColor(getLayoutColor(R.color.black));
                holder.mTvEvalueContent.setTextColor(getLayoutColor(R.color.text_title_color));
                holder.mTvEvalueTime.setTextColor(getLayoutColor(R.color.text_fu_color));
                holder.mTvEvalueSuppernumber.setTextColor(getLayoutColor(R.color.text_fu_color));
                holder.mTvEvalueEvalue.setTextColor(getLayoutColor(R.color.text_fu_color));
                holder.mViewLine.setBackgroundColor(getLayoutColor(R.color.input_bg));

                holder.mLiAddComment.setBackgroundColor(getLayoutColor(R.color.white));
                holder.mRlvAddEvaluate.setBackgroundColor(getLayoutColor(R.color.gray_line));
                holder.mTvInfomLookHuifu.setTextColor(getLayoutColor(R.color.text_fu_color));
                holder.mLiLookHuifu.setBackgroundColor(getLayoutColor(R.color.gray_line));

            }
        }

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
        holder.mTvEvalueEvalue.setText(bean.getCommentcount() + "");
        holder.mChbEvaluaIssupper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SuppertUtil util = SuppertUtil.getInstance(mContext);
                int number = Integer.parseInt(holder.mChbEvaluaIssupper.getText().toString());
                if (isChecked) {
                    holder.mChbEvaluaIssupper.setText((number + 1) + "");
                    util.submitSupport(String.valueOf(bean.getId()), "true", DataMessageVo.USERTYPEQC);

                } else {
                    if (number == 0) {
                        holder.mChbEvaluaIssupper.setText(0 + "");
                    } else {
                        holder.mChbEvaluaIssupper.setText((number - 1) + "");

                    }
                    util.submitSupport(String.valueOf(bean.getId()), "false", DataMessageVo.USERTYPEQC);
                }
              /*  if (clickChbListener != null) {
                    clickChbListener.onClickChbListener(bean, isChecked, position);
                }*/
            }
        });
        if (bean.getChildren() == null || bean.getChildren().isEmpty()) {
            holder.mLiAddComment.setVisibility(View.GONE);
        } else {
            holder.mLiAddComment.setVisibility(View.VISIBLE);
            if (bean.getChildren().size() >= 2)
                holder.mTvInfomLookHuifu.setVisibility(View.VISIBLE);
            else
                holder.mTvInfomLookHuifu.setVisibility(View.GONE);
            InfomAddEvalueAdapter adapter = new InfomAddEvalueAdapter(mContext, bean.getChildren(),mSelectType);
            GridLayoutManager manager = new GridLayoutManager(mContext, 1);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            holder.mRlvAddEvaluate.setLayoutManager(manager);
            holder.mRlvAddEvaluate.setAdapter(adapter);
            adapter.setClickListener(new InfomAddEvalueAdapter.onItemClickListener() {
                @Override
                public void onClickListener(Object obj, int position) {
                    EventBus.getDefault().postSticky(new EvalueTwoEvent(bean));
                    Intent intent = EvalueTwoActivity.newInstance(mContext, String.valueOf(bean.getTargetid()),
                            String.valueOf(bean.getId()), DataMessageVo.USERTYPEQC,
                            DataMessageVo.QUESTION);
                    mContext.startActivity(intent);
                }
            });
            adapter.setClickListener(new InfomAddEvalueAdapter.onItemClickListener() {
                @Override
                public void onClickListener(Object obj, int position) {
                    EventBus.getDefault().postSticky(new EvalueTwoEvent(bean));
                    Intent intent = EvalueTwoActivity.newInstance(mContext, String.valueOf(bean.getTargetid()),
                            String.valueOf(bean.getId()), DataMessageVo.USERTYPEQC,
                            DataMessageVo.QUESTION);
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
            clickListener.onClickListener(v.getTag(), v.getId());
        }
    }

    private int getLayoutColor(int id) {
        return mContext.getResources().getColor(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvEvaluateHear;
        public TextView mTvEvalueUserName;
        public TextView mTvEvalueContent;
        public TextView mTvEvalueTime;
        public TextView mTvEvalueEvalue;
        public CheckBox mChbEvaluaIssupper;
        public TextView mTvEvalueSuppernumber;
        public LinearLayout mLiItemRoot;
        public View mViewLine;
        public RecyclerView mRlvAddEvaluate;
        public TextView mTvInfomLookHuifu;
        public LinearLayout mLiAddComment;
        public LinearLayout mLiLookHuifu;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvEvaluateHear = (ImageView) itemView.findViewById(R.id.iv_evaluate_hear);
            this.mViewLine = (View) itemView.findViewById(R.id.view_line);
            this.mTvEvalueUserName = (TextView) itemView.findViewById(R.id.tv_evalue_user_name);
            this.mTvEvalueContent = (TextView) itemView.findViewById(R.id.tv_evalue_content);
            this.mTvEvalueTime = (TextView) itemView.findViewById(R.id.tv_evalue_time);
            this.mTvEvalueEvalue = (TextView) itemView.findViewById(R.id.tv_evalue_evalue);
            this.mChbEvaluaIssupper = (CheckBox) itemView.findViewById(R.id.chb_evalua_issupper);
            this.mTvEvalueSuppernumber = (TextView) itemView.findViewById(R.id.tv_evalue_suppernumber);
            this.mRlvAddEvaluate = (RecyclerView) itemView.findViewById(R.id.rlv_add_evaluate);
            this.mTvInfomLookHuifu = (TextView) itemView.findViewById(R.id.tv_infom_look_huifu);
            this.mLiAddComment = (LinearLayout) itemView.findViewById(R.id.li_add_comment);
            this.mViewLine = (View) itemView.findViewById(R.id.view_line);
            this.mLiItemRoot = (LinearLayout) itemView.findViewById(R.id.ll_item_root);
            this.mLiLookHuifu = (LinearLayout) itemView.findViewById(R.id.li_look_huifu);

        }
    }


}
