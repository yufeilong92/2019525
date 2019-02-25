package com.xuechuan.xcedu.adapter.net;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.ImageUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.TimeSampUtil;
import com.xuechuan.xcedu.vo.EvalueNewVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.28 上午 11:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetCommentAdapter extends BaseRecyclerAdapter<NetCommentAdapter.CommentViewHodler> {
    private Context mContext;
    private List<?> mListDatas;
    private final LayoutInflater mInflater;

    public NetCommentAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem( EvalueNewVo.DatasBean vo );
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CommentViewHodler getViewHolder(View view) {
        return new CommentViewHodler(view);
    }

    @Override
    public CommentViewHodler onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return new CommentViewHodler(mInflater.inflate(R.layout.item_net_comment, null));
    }

    @Override
    public void onBindViewHolder(CommentViewHodler holder, int position, boolean isItem) {
        holder.mTvEvalueContent.setLines(2);
        holder.mTvEvalueContent.setEllipsize(TextUtils.TruncateAt.END);
        final EvalueNewVo.DatasBean vo = (EvalueNewVo.DatasBean) mListDatas.get(position);
        holder.mTvEvalueSuppernumber.setVisibility(View.GONE);
        holder.mTvEvalueEvalue.setVisibility(View.GONE);
        holder.mChbEvaluaIssupper.setVisibility(View.GONE);
        ImageUtil.setImagaImg(holder.mIvEvaluateHear, vo.getHeadicon(), R.drawable.ic_m_hear, false);
        holder.mTvEvalueUserName.setText(vo.getNickname());
        holder.mTvEvalueContent.setText(vo.getContent());
        String stamp = TimeSampUtil.getStringTimeStamp(vo.getCreatetime());
        holder.mTvEvalueTime.setText(stamp);
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

    public class CommentViewHodler extends RecyclerView.ViewHolder {
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

        public CommentViewHodler(View itemView) {
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
        }
    }


}
