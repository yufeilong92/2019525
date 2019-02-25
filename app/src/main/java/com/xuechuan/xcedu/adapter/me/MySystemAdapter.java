package com.xuechuan.xcedu.adapter.me;

import android.content.Context;
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
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.SystemVo;
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
public class MySystemAdapter extends BaseRecyclerAdapter<MySystemAdapter.ViewHolder> {
    private CommonPopupWindow showDel;
    private Context mContext;
    private List mData;
    private final LayoutInflater mInflater;
    private CommonPopupWindow.LayoutGravity layoutGravity;
    private Button btnDel;
    private Button btnCancel;

    public MySystemAdapter(Context mContext, List mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(SystemVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private onItemLangClickListener clickLangListener;

    public interface onItemLangClickListener {
        public void onClickLangListener(SystemVo.DatasBean obj, int position);
    }

    public void setClickLangListener(onItemLangClickListener clickListener) {
        this.clickLangListener = clickListener;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_my_system, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, boolean isItem) {
        final SystemVo.DatasBean vo = (SystemVo.DatasBean) mData.get(position);
        holder.mIvSystemTitle.setText(vo.getTitle());
        if (!StringUtil.isEmpty(vo.getThumbnail()))
            MyAppliction.getInstance().displayImages(holder.mIvSystemImg, vo.getThumbnail(), false);
        holder.mTvSystemTime.setText(TimeUtil.getYMDT(vo.getCreatetime()));
        holder.mTvSystemContent.setText(vo.getSummary());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClickListener(vo, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogUtil dialogUtil = DialogUtil.getInstance();
                dialogUtil.showTitleDialog(mContext,"是否删除","确定","取消",true);
                dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        clickLangListener.onClickLangListener(vo, position);
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
        public TextView mIvSystemTitle;
        public TextView mTvSystemTime;
        public TextView mTvSystemContent;
        public ImageView mIvSystemImg;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvSystemTitle = (TextView) itemView.findViewById(R.id.iv_system_title);
            this.mTvSystemTime = (TextView) itemView.findViewById(R.id.tv_system_time);
            this.mTvSystemContent = (TextView) itemView.findViewById(R.id.tv_system_content);
            this.mIvSystemImg = (ImageView) itemView.findViewById(R.id.iv_system_img);
        }
    }
    /**
     * 分享布局
     */
    private void showShareLayout(final View view, final SystemVo.DatasBean vo, final int position) {
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.TO_ABOVE | CommonPopupWindow.LayoutGravity.CENTER_HORI);
        showDel = new CommonPopupWindow(mContext, R.layout.del, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
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
        showDel.showBashOfAnchor(view, layoutGravity, 0, 0);
    }


}
