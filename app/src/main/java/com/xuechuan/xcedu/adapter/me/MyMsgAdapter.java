package com.xuechuan.xcedu.adapter.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.ui.EvalueTwoActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.MyMsgVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 我的消息
 * @author: L-BackPacker
 * @date: 2018/5/30 18:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyMsgAdapter extends BaseRecyclerAdapter<MyMsgAdapter.ViewHolder> {

    private Context mContext;
    private List mData;
    private final LayoutInflater mInflater;
    private CommonPopupWindow.LayoutGravity layoutGravity1;
    private CommonPopupWindow showDel;
    private Button btnDel;

    public MyMsgAdapter(Context mContext, List mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemLangClickListener clickLangListener;

    public interface onItemLangClickListener {
        public void onClickLangListener(MyMsgVo.DatasBean datas, int position);
    }

    public void setLangClickListener(onItemLangClickListener clickListener) {
        this.clickLangListener = clickListener;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_my_msg, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position, boolean isItem) {
        final int mPostion = position;
        final MyMsgVo.DatasBean datas = (MyMsgVo.DatasBean) mData.get(mPostion);
        int notifytype = datas.getNotifytype();
        if (notifytype == 1) {
            holder.mTvMPType.setText("回复了你");
            holder.mTvMPLook.setVisibility(View.VISIBLE);
            holder.mTvMyMsgContent.setText(datas.getContent());
        } else if (notifytype == 2) {
            holder.mTvMPType.setText("赞了你");
            holder.mTvMPLook.setVisibility(View.GONE);
            holder.mTvMyMsgContent.setText(datas.getTargetcontent());
        }
        if (!StringUtil.isEmpty(datas.getMemberheadicon()))
            MyAppliction.getInstance().displayImages(holder.mIvMyMsgImg, datas.getMemberheadicon(), true);
        else {
            holder.mIvMyMsgImg.setImageResource(R.mipmap.common_icon_defaultpicture_mini);
        }
        holder.mTvMyMsgName.setText(datas.getMembername());
        holder.mTvMyMsgTime.setText(TimeUtil.getYMDT(datas.getCreatetime()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "";
                String submittype = "";
                if (datas.getSourcetype() == 1 || datas.getSourcetype() == 2) {
                    type = DataMessageVo.USERTYPEAC;
                    submittype = DataMessageVo.ARTICLE;
                } else if (datas.getSourcetype() == 3 || datas.getSourcetype() == 4) {
                    type = DataMessageVo.USERTYPEQC;
                    submittype = DataMessageVo.QUESTION;
                } else if (datas.getSourcetype() == 5 || datas.getSourcetype() == 6) {
                    type = DataMessageVo.USERTYPEVC;
                    submittype = DataMessageVo.VIDEO;
                }
                Intent intent = EvalueTwoActivity.newInstance(mContext, "",
                        String.valueOf(datas.getTargetid()),
                        type,
                        submittype);
                mContext.startActivity(intent);
            }
        });

/*        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

//                showShareLayout(holder.itemView, datas, mPostion);
//                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.input_bg));
                return false;
            }
        });*/
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogUtil dialogUtil = DialogUtil.getInstance();
                dialogUtil.showTitleDialog(mContext, "是否删除", "确定", "取消", true);
                dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        clickLangListener.onClickLangListener(datas, mPostion);
//                        clickLangListener.onClickLangListener(vo, position);
                    }

                    @Override
                    public void onCancelClickListener() {

                    }
                });

//                showShareLayout(holder.itemView, vo, position);
//                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.input_bg));
                return false;
            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvMyMsgImg;
        public TextView mTvMyMsgName;
        public TextView mTvMPType;
        public TextView mTvMyMsgTime;
        public TextView mTvMyMsgContent;
        public TextView mTvMPLook;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvMyMsgImg = (ImageView) itemView.findViewById(R.id.iv_my_msg_img);
            this.mTvMyMsgName = (TextView) itemView.findViewById(R.id.tv_my_msg_name);
            this.mTvMPType = (TextView) itemView.findViewById(R.id.tv_m_p_type);
            this.mTvMyMsgTime = (TextView) itemView.findViewById(R.id.tv_my_msg_time);
            this.mTvMyMsgContent = (TextView) itemView.findViewById(R.id.tv_my_msg_content);
            this.mTvMPLook = (TextView) itemView.findViewById(R.id.tv_m_p_look);
        }


    }

    /**
     * 分享布局
     */
    private void showShareLayout(final View view, final MyMsgVo.DatasBean vo, final int position) {
        layoutGravity1 = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.TO_ABOVE | CommonPopupWindow.LayoutGravity.CENTER_HORI);
        showDel = new CommonPopupWindow(mContext, R.layout.del, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {

            private Button btnCancel;

            @Override
            protected void initView() {
                View view = getContentView();
                btnDel = view.findViewById(R.id.btn_pop_del);
                btnCancel = view.findViewById(R.id.btn_pop_cancel);
            }

            @Override
            protected void initEvent() {
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDel.getPopupWindow().dismiss();
                    }
                });
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickLangListener != null) {
                            showDel.getPopupWindow().dismiss();
                            clickLangListener.onClickLangListener(vo, position);
                        }

                    }
                });
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    }
                });
            }
        };
        showDel.showBashOfAnchor(view, layoutGravity1, 0, 0);
    }


}
