package com.xuechuan.xcedu.adapter.me;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.GiftVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/26 15:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyOrderDetailAdapter extends RecyclerView.Adapter<MyOrderDetailAdapter.ViewHodle> {
    private Context mContext;
    private List mData;
    private final LayoutInflater inflater;
    private onItemClickListener clickListener;


    public interface onItemClickListener {
        public void onClickListener(GiftVo obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private onRefundProgressClickListener refundprogressclicklistener;

    public interface onRefundProgressClickListener {
        public void onRefundProgressClickListener(GiftVo obj, int position);
    }

    public void setRefundProgressClickListener(onRefundProgressClickListener clickListener) {
        this.refundprogressclicklistener = clickListener;
    }

    private onLookLogisticsItemClickListener LookLogisticsclickListener;

    public interface onLookLogisticsItemClickListener {
        public void onLookLogisticsClickListener(GiftVo obj, int position);
    }

    public void setLookLogisticsClickListener(onLookLogisticsItemClickListener clickListener) {
        this.LookLogisticsclickListener = clickListener;
    }

    private onSureGoodsItemClickListener SureGoodsclickListener;

    public interface onSureGoodsItemClickListener {
        public void onSureGoodsClickListener(GiftVo obj, int position);
    }

    public void setSureGoodsClickListener(onSureGoodsItemClickListener clickListener) {
        this.SureGoodsclickListener = clickListener;
    }


    private onApplyRefundItemClickListener ApplyRefundclickListener;

    public interface onApplyRefundItemClickListener {
        public void onApplyRefundClickListener(GiftVo obj, int position);
    }

    public void setApplyRefundClickListener(onApplyRefundItemClickListener clickListener) {
        this.ApplyRefundclickListener = clickListener;
    }


    public MyOrderDetailAdapter(Context mContext, List mData) {
        this.mContext = mContext;
        this.mData = mData;
        inflater = LayoutInflater.from(mContext);
    }

    private boolean isShow = false;

    /**
     * 是否显示查看物流
     *
     * @param isShow
     */
    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
        notifyDataSetChanged();
    }

    private boolean mIsMainEmpty = false;

    /**
     * 主商品是否为空
     *
     * @param isMainEmpty
     */
    public void setMainIsEmpty(boolean isMainEmpty) {
        this.mIsMainEmpty = isMainEmpty;
    }

    /**
     * 是否显示状态
     */
    private boolean isProgerssShow = false;

    /**
     * 是否显示已签收等状态
     *
     * @param isShow
     */
    public void setProgressIsShow(boolean isShow) {
        this.isProgerssShow = isShow;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_detail, null);
        return new ViewHodle(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodle holder, int position) {
        GiftVo vo = (GiftVo) mData.get(position);
        showProgressStuta(holder);
        if (StringUtil.isEmpty(vo.getCoverimg())) {
            holder.mIvTakeBook.setImageResource(R.mipmap.defaultimg);
        } else {
            MyAppliction.getInstance().displayImages(holder.mIvTakeBook, vo.getCoverimg(), false);
        }
        switch (vo.getState()) {
            case 0://代发货
                ivStatus(holder, isProgerssShow, R.mipmap.ic_order_delivery, vo.isMainProducts());
                showbtn(holder, false, false, false, false, false);
                break;
            case 1://待确认
                ivStatus(holder, isProgerssShow, R.mipmap.ic_wait_confirmed, vo.isMainProducts());
                showbtn(holder, false, false, false, false, false);
                break;
            case 11://待出库
                ivStatus(holder, isProgerssShow, R.mipmap.ic_out_storage, vo.isMainProducts());
                showbtn(holder, false, false, false, false, false);
                break;
            case 12://待收货
                ivStatus(holder, isProgerssShow, R.mipmap.wait_goods, vo.isMainProducts());
                showbtn(holder, true, true, false, false, false);
                break;
            case 13://已签收
                ivStatus(holder, isProgerssShow, R.mipmap.sing, vo.isMainProducts());
                showbtn(holder, false, false, false, false, false);
                break;
            default:
                ivStatus(holder, false, 0, vo.isMainProducts());
                showbtn(holder, false, false, false, false, false);

        }
        if (vo.isIsgift()) {//是赠品
            holder.mIvGoodStatus.setVisibility(View.VISIBLE);
            holder.mIvTakeGift.setVisibility(View.VISIBLE);
        } else {//不是赠品
            holder.mIvTakeGift.setVisibility(View.GONE);
            if (mIsMainEmpty){//主商品为空
                holder.mIvGoodStatus.setVisibility(View.VISIBLE);
            }else {//不为空
                holder.mIvGoodStatus.setVisibility(View.GONE);
            }

        }
        setListenter(holder, vo, position);
        holder.mTvTakeTitle.setText(vo.getName());
        holder.mTvTakeValue.setText(String.valueOf(vo.getPrice()));
        if (mIsMainEmpty) {//主商品是否是空的
            showPrice(holder, mIsMainEmpty ? View.VISIBLE : View.INVISIBLE);
            holder.mTvTakeNumber.setText(String.valueOf(vo.getNum()));
            return;
        }
        showPrice(holder, vo.isMainProducts() ? View.VISIBLE : View.INVISIBLE);
        holder.mTvTakeNumber.setText(String.valueOf(vo.getNum()));

    }

    /**
     * 显示发货按钮状态
     *
     * @param holder
     */
    private void showProgressStuta(@NonNull ViewHodle holder) {
        holder.mLlApplyStatus.setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (isProgerssShow) {//是否显示发货进度
            holder.mIvGoodStatus.setVisibility(View.VISIBLE);
            holder.mIvTakeGift.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示价格
     *
     * @param holder
     * @param i
     */
    private void showPrice(@NonNull ViewHodle holder, int i) {
        holder.mTvTakeValue.setVisibility(i);
        holder.mTv_Y.setVisibility(i);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * @param look     查看物流
     * @param sure     确认收货
     * @param apply    申请退款
     * @param progres  退款进度
     */
    private void showbtn(ViewHodle hodle, boolean look, boolean sure, boolean apply, boolean progres, boolean confirm) {
        hodle.mBtnProgressLogistics.setVisibility(look ? View.VISIBLE : View.GONE);
        hodle.mBtnProgressRefund.setVisibility(apply ? View.VISIBLE : View.GONE);
        hodle.mBtnProgressSure.setVisibility(sure ? View.VISIBLE : View.GONE);
        hodle.mBtnRefundProgress.setVisibility(progres ? View.VISIBLE : View.GONE);
        hodle.mBtnRefundWaitConfirm.setVisibility(confirm ? View.VISIBLE : View.GONE);

    }

    /**
     *
     * @param hodle
     * @param ishow 是显示
     * @param id
     * @param isMain 是否是主商品
     */
    private void ivStatus(ViewHodle hodle, boolean ishow, int id, boolean isMain) {
        if (mIsMainEmpty) {//主商品
            showStutasImg(hodle, ishow, id);
            return;
        }
        if (isMain) {
            return;
        }
        showStutasImg(hodle, ishow, id);
    }

    /**
     * 显示状态图片
     *
     * @param hodle
     * @param ishow
     * @param id
     */
    private void showStutasImg(ViewHodle hodle, boolean ishow, int id) {
        hodle.mIvGoodStatus.setVisibility(ishow ? View.VISIBLE : View.GONE);
        if (id != 0)
            hodle.mIvGoodStatus.setImageResource(id);
    }

    private void setListenter(ViewHodle hodle, final GiftVo vo, final int postiont) {
        hodle.mBtnRefundProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//退款进度
                if (refundprogressclicklistener != null) {
                    refundprogressclicklistener.onRefundProgressClickListener(vo, postiont);
                }
            }
        });
        hodle.mBtnProgressSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//确认收货
                if (SureGoodsclickListener != null) {
                    SureGoodsclickListener.onSureGoodsClickListener(vo, postiont);
                }
            }
        });
        hodle.mBtnProgressRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//申请退款
                if (ApplyRefundclickListener != null) {
                    ApplyRefundclickListener.onApplyRefundClickListener(vo, postiont);
                }
            }
        });
        hodle.mBtnProgressLogistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//查看物流
                if (LookLogisticsclickListener != null) {
                    LookLogisticsclickListener.onLookLogisticsClickListener(vo, postiont);
                }
            }
        });
        hodle.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClickListener(vo, postiont);
                }
            }
        });


    }

    public class ViewHodle extends RecyclerView.ViewHolder {
        public ImageView mIvTakeBook;
        public ImageView mIvTakeGift;
        public TextView mTvTakeTitle;
        public TextView mTv_Y;
        public TextView mTvTakeValue;
        public TextView mTvTakeNumber;
        public ImageView mIvGoodStatus;
        public Button mBtnProgressLogistics;
        public Button mBtnProgressRefund;
        public Button mBtnProgressSure;
        public Button mBtnRefundProgress;
        public Button mBtnRefundWaitConfirm;
        public LinearLayout mLlApplyStatus;


        public ViewHodle(View itemView) {
            super(itemView);
            this.mIvTakeBook = (ImageView) itemView.findViewById(R.id.iv_take_book);
            this.mIvTakeGift = (ImageView) itemView.findViewById(R.id.iv_take_gift);
            this.mTvTakeTitle = (TextView) itemView.findViewById(R.id.tv_take_title);
            this.mTvTakeValue = (TextView) itemView.findViewById(R.id.tv_take_value);
            this.mTvTakeNumber = (TextView) itemView.findViewById(R.id.tv_take_number);
            this.mIvGoodStatus = (ImageView) itemView.findViewById(R.id.iv_good_status);
            this.mBtnProgressLogistics = (Button) itemView.findViewById(R.id.btn_progress_logistics);
            this.mBtnProgressRefund = (Button) itemView.findViewById(R.id.btn_progress_refund);
            this.mBtnProgressSure = (Button) itemView.findViewById(R.id.btn_progress_sure);
            this.mBtnRefundProgress = (Button) itemView.findViewById(R.id.btn_refund_progress);
            this.mBtnRefundWaitConfirm = (Button) itemView.findViewById(R.id.btn_refund_wait_confirm);
            this.mLlApplyStatus = (LinearLayout) itemView.findViewById(R.id.ll_apply_status);
            this.mTv_Y = (TextView) itemView.findViewById(R.id.tv_Y);

        }
    }


}
