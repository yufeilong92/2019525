package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
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

import com.andview.refreshview.callback.IFooterCallBack;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.GmInfomEvalueAdapter;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.SuppertUtil;
import com.xuechuan.xcedu.utils.TimeSampUtil;
import com.xuechuan.xcedu.vo.EvalueNewVo;

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
public class GmEvaluateAdapter extends BaseRecyclerAdapter<GmEvaluateAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<EvalueNewVo.DatasBean> mData;
    private final LayoutInflater mInflater;
    /**
     * 点击
     */
    private onItemClickListener onItemclickListener;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setOnItemClickListener(onItemClickListener clickListener) {
        this.onItemclickListener = clickListener;
    }
    /**
     * 模式
     */
    private String mSelectType;
    private GmReadColorManger mColorManger;

    public GmEvaluateAdapter(Context mContext, List<EvalueNewVo.DatasBean> mData) {
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

    public void doChangerColor(GmReadColorManger colorManger) {
        this.mColorManger = colorManger;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.text_evlaues_recycler_item, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onClick(View v) {
        if (onItemclickListener!=null){
            onItemclickListener.onClickListener(v.getTag(),v.getId());
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, boolean isItem) {
        final EvalueNewVo.DatasBean vo = mData.get(position);
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
        doChangerColor(holder);
        if (!StringUtil.isEmpty(vo.getHeadicon())) {
            MyAppliction.getInstance().displayImages(holder.mIvGmEvaluateHear, vo.getHeadicon(), true);
        } else {
            holder.mIvGmEvaluateHear.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.common_icon_defaultpicture_mini));
        }
        holder.mTvGmEvalueUserName.setText(vo.getNickname());
        holder.mChbGmEvaluaIssupper.setText(String.valueOf(vo.getSupportcount()));
        holder.mChbGmEvaluaIssupper.setChecked(vo.isIssupport());
        holder.mTvGmEvalueContent.setText(vo.getContent());
        String stamp = TimeSampUtil.getStringTimeStamp(vo.getCreatetime());
        holder.mTvGmEvalueTime.setText(stamp);
        holder.mTvGmEvalueEvalue.setText(String.valueOf(vo.getCommentcount()));
        //点赞选择
        doCbEvent(holder, vo);
        if (vo.getChildren() == null || vo.getChildren().isEmpty()) {
            holder.mLiGmAddComment.setVisibility(View.GONE);
            return;
        }
        holder.mLiGmAddComment.setVisibility(View.VISIBLE);
        holder.mTvInfomLookHuifu.setVisibility(vo.getChildren().size() > 2 ? View.VISIBLE : View.GONE);
        //处理评价评价
        doCommonCommom(holder, vo);

        holder.mChbGmEvaluaIssupper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (clickChbListener != null) {
                    clickChbListener.onClickChbListener(vo, isChecked, position);
                }
            }
        });

    }

    private void doCommonCommom(ViewHolder holder, EvalueNewVo.DatasBean vo) {
        GmInfomEvalueAdapter adapter = new GmInfomEvalueAdapter(mContext, vo.getChildren());
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        adapter.doChangeColor(mColorManger);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvGmAddEvaluate.setLayoutManager(manager);
        holder.mRlvGmAddEvaluate.setAdapter(adapter);


    }

    private void doCbEvent(final ViewHolder holder, final EvalueNewVo.DatasBean vo) {
        holder.mChbGmEvaluaIssupper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SuppertUtil mUtils = SuppertUtil.getInstance(mContext);
                int number = Integer.parseInt(holder.mChbGmEvaluaIssupper.getText().toString());
                if (isChecked) {
                    holder.mChbGmEvaluaIssupper.setText(String.valueOf(number + 1));
                    mUtils.submitSupport(String.valueOf(vo.getId()), "true", DataMessageVo.USERTYPEQC);
                } else {
                    if (number == 0) {
                        holder.mChbGmEvaluaIssupper.setText(String.valueOf(0));
                    } else {
                        holder.mChbGmEvaluaIssupper.setText(String.valueOf(number - 1));
                    }
                    mUtils.submitSupport(String.valueOf(vo.getId()), "false", DataMessageVo.USERTYPEQC);
                }

            }
        });
    }

    private void doChangerColor(ViewHolder holder) {
        if (mColorManger != null) {
            holder.mLlGmitemRoot.setBackgroundColor(mColorManger.getmLayoutBgColor());
            holder.mTvGmEvalueUserName.setTextColor(mColorManger.getmTextTitleColor());
            holder.mTvGmEvalueContent.setTextColor(mColorManger.getmTextTitleColor());
            holder.mTvGmEvalueTime.setTextColor(mColorManger.getmTextFuColor());
            holder.mTvGmEvalueEvalue.setTextColor(mColorManger.getmTextFuColor());
            holder.mChbGmEvaluaIssupper.setTextColor(mColorManger.getmTextFuColor());
            holder.mTvGmEvalueSuppernumber.setTextColor(mColorManger.getmTextFuColor());
            //评论布局
            holder.mLiGmAddComment.setBackgroundColor(mColorManger.getmLayoutBgColor());
            //评论显示
            holder.mRlvGmAddEvaluate.setBackgroundColor(mColorManger.getmCutLineColor());
            //回复布局
            holder.mLiGmLookHuifu.setBackgroundColor(mColorManger.getmCutLineColor());
            holder.mTvInfomLookHuifu.setTextColor(mColorManger.getmTextFuColor());
            holder.mViewGmLine.setBackgroundColor(mColorManger.getmCutLineColor());
            holder.mRlvGmAddEvaluate.setBackgroundColor(mColorManger.getmCutLineColor());
        }
    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvGmEvaluateHear;
        public TextView mTvGmEvalueUserName;
        public TextView mTvGmEvalueContent;
        public TextView mTvGmEvalueTime;
        public TextView mTvGmEvalueEvalue;
        public CheckBox mChbGmEvaluaIssupper;
        public TextView mTvGmEvalueSuppernumber;
        public RecyclerView mRlvGmAddEvaluate;
        public TextView mTvInfomLookHuifu;
        public LinearLayout mLiGmLookHuifu;
        public LinearLayout mLiGmAddComment;
        public View mViewGmLine;
        public LinearLayout mLlGmitemRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvGmEvaluateHear = (ImageView) itemView.findViewById(R.id.iv_gm_evaluate_hear);
            this.mTvGmEvalueUserName = (TextView) itemView.findViewById(R.id.tv_gm_evalue_user_name);
            this.mTvGmEvalueContent = (TextView) itemView.findViewById(R.id.tv_gm_evalue_content);
            this.mTvGmEvalueTime = (TextView) itemView.findViewById(R.id.tv_gm_evalue_time);
            this.mTvGmEvalueEvalue = (TextView) itemView.findViewById(R.id.tv_gm_evalue_evalue);
            this.mChbGmEvaluaIssupper = (CheckBox) itemView.findViewById(R.id.chb_gm_evalua_issupper);
            this.mTvGmEvalueSuppernumber = (TextView) itemView.findViewById(R.id.tv_gm_evalue_suppernumber);
            this.mRlvGmAddEvaluate = (RecyclerView) itemView.findViewById(R.id.rlv_gm_add_evaluate);
            this.mTvInfomLookHuifu = (TextView) itemView.findViewById(R.id.tv_infom_look_huifu);
            this.mLiGmLookHuifu = (LinearLayout) itemView.findViewById(R.id.li_gm_look_huifu);
            this.mLiGmAddComment = (LinearLayout) itemView.findViewById(R.id.li_gm_add_comment);
            this.mViewGmLine = (View) itemView.findViewById(R.id.view_gm_line);
            this.mLlGmitemRoot = (LinearLayout) itemView.findViewById(R.id.ll_gmitem_root);

        }
    }


}
