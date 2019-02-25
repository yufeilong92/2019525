package com.xuechuan.xcedu.adapter.me;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.DetailsBeanMainVo;
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.vo.MyOrderNewVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 我的订单适配器
 * @author: L-BackPacker
 * @date: 2018/5/26 13:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyOrderAdapter extends BaseRecyclerAdapter<MyOrderAdapter.ViewHodler> {
    private Context mContext;
    private List mData;
    private final LayoutInflater mInflater;

    public MyOrderAdapter(Context mContext, List mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(MyOrderNewVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private onItemPayClickListener payClickListener;

    public interface onItemPayClickListener {
        public void onPayClickListener(MyOrderNewVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemPayClickListener clickListener) {
        this.payClickListener = clickListener;
    }

    private onItemCancelClickListener cancelClickListener;

    public interface onItemCancelClickListener {
        public void onCancelClickListener(MyOrderNewVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemCancelClickListener clickListener) {
        this.cancelClickListener = clickListener;
    }

    private onItemDelClickListener delClickListener;

    public interface onItemDelClickListener {
        public void onDelClickListener(MyOrderNewVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemDelClickListener clickListener) {
        this.delClickListener = clickListener;
    }

    private boolean isShow = false;

    /**
     * 是否显示查看状态
     * @param isShow
     */
    public void setIsShowLook(boolean isShow) {
        this.isShow = isShow;
        notifyDataSetChanged();
    }

    private onLookOrderClickListener LookOrderclickListener;

    public interface onLookOrderClickListener {
        public void onLookOrderClickListener();
    }

    public void setLookOrderClickListener(onLookOrderClickListener clickListener) {
        this.LookOrderclickListener = clickListener;
    }

    @Override
    public ViewHodler getViewHolder(View view) {
        return new ViewHodler(view);
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_my_order, null);
        ViewHodler hodler = new ViewHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, final int position, boolean isItem) {
        final MyOrderNewVo.DatasBean vo = (MyOrderNewVo.DatasBean) mData.get(position);
        holder.mTvMyOrderCode.setText(vo.getOrdernum());
        if (position == 0 && isShow) {
            holder.mRlvOrderLookOrder.setVisibility(View.VISIBLE);
        } else {
            holder.mRlvOrderLookOrder.setVisibility(View.GONE);
        }
        switch (vo.getState()) {
            case 10://代付款
                showStatus(holder, true, R.mipmap.ic_order_pay_menoy);
                showBtn(holder, vo.getOrdertype(), true, true, false);
                break;
            case 11://代发货
                showStatus(holder, true, R.mipmap.ic_order_deliver);
                showBtn(holder, vo.getOrdertype(), false, false, false);
                break;
            case 12://待收货
                showStatus(holder, true, R.mipmap.ic_oder_take_goods);
                showBtn(holder, vo.getOrdertype(), false, false, false);
                break;
            case 13://已完成
                showStatus(holder, true, R.mipmap.ic_order_sign);
                showBtn(holder, vo.getOrdertype(), false, false, true);
                break;
          /*  case 20:
                showStatus(holder, true, R.mipmap.ic_order_colse);
                showBtn(holder, false, false, true);
                break;*/
            default:
                showStatus(holder, false, 0);
                showBtn(holder, vo.getOrdertype(), false, false, false);
        }
        if (vo.getDiscounts() == 0) {//显示优惠
            showYlayout(holder, false);
        } else {
            showYlayout(holder, true);
        }
        holder.mTvMyOrderYPrice.setText(vo.getDiscounts() + "");
        holder.mTvMyOrderAllPrice.setText(vo.getTotalprice() + "");
        setlistener(holder, position, vo);
        ArrayList<GiftVo> vos = new ArrayList<>();
        vos.clear();
        List<DetailsBeanMainVo> details = vo.getDetails();
/*        if (details == null || details.isEmpty()) {
            holder.mLiLayoutContent.setVisibility(View.GONE);
            return;
        } else {
            holder.mLiLayoutContent.setVisibility(View.VISIBLE);
        }*/
        if (details != null && !details.isEmpty())
            for (int i = 0; i < details.size(); i++) {
                DetailsBeanMainVo bean = details.get(i);
                GiftVo giftVo = new GiftVo();
                giftVo.setCoverimg(bean.getCoverimg());
                giftVo.setItemtype(bean.getItemtype());
                giftVo.setNum(bean.getNum());
                giftVo.setPrice(bean.getPrice());
                giftVo.setId(bean.getProductid());
                giftVo.setName(bean.getProductname());
                giftVo.setState(bean.getState());
                giftVo.setMainProducts(true);
                vos.add(giftVo);
            }
        if (vo.getGifts() != null && !vo.getGifts().isEmpty())
            vos.addAll(vo.getGifts());
        if (vo.getDetails() == null || vo.getDetails().isEmpty()) {
            holder.mRlvMyoderInfom.setVisibility(View.GONE);
            holder.mRlvMyoderInfomBooks.setVisibility(View.VISIBLE);
            bindViewDataBooks(holder, vo, true, vos);
        } else {
            holder.mRlvMyoderInfom.setVisibility(View.VISIBLE);
            holder.mRlvMyoderInfomBooks.setVisibility(View.GONE);
            bindViewData(holder, vo, false, vos);
        }
    }

    private void bindViewData(ViewHodler holder, final MyOrderNewVo.DatasBean datasBean, boolean isMainEmpty, List<GiftVo> vo) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvMyoderInfom.setLayoutManager(manager);
        MyOrderDetailAdapter adapter = new MyOrderDetailAdapter(mContext, vo);
        holder.mRlvMyoderInfom.setAdapter(adapter);
        adapter.setIsShow(false);
        adapter.setMainIsEmpty(isMainEmpty);
        adapter.setProgressIsShow(false);
        adapter.setClickListener(new MyOrderDetailAdapter.onItemClickListener() {
            @Override
            public void onClickListener(GiftVo vo, int postion) {
                if (clickListener != null) {
                    clickListener.onClickListener(datasBean, 0);
                }
            }
        });

    }

    private void bindViewDataBooks(ViewHodler holder, final MyOrderNewVo.DatasBean datasBean, boolean isMainEmpty, List<GiftVo> vo) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvMyoderInfomBooks.setLayoutManager(manager);
        MyOrderDetailAdapter adapter = new MyOrderDetailAdapter(mContext, vo);
        holder.mRlvMyoderInfomBooks.setAdapter(adapter);
        adapter.setIsShow(false);
        adapter.setMainIsEmpty(isMainEmpty);
        adapter.setProgressIsShow(false);
        adapter.setClickListener(new MyOrderDetailAdapter.onItemClickListener() {
            @Override
            public void onClickListener(GiftVo vo, int postion) {
                if (clickListener != null) {
                    clickListener.onClickListener(datasBean, 0);
                }
            }
        });

    }

    private void showStatus(ViewHodler holder, boolean isShow, int id) {
        holder.mIvMyOrderStatus.setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (id != 0)
            holder.mIvMyOrderStatus.setImageResource(id);
    }

    private void setlistener(ViewHodler holder, final int position, final MyOrderNewVo.DatasBean vo) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClickListener(vo, position);
                }
            }
        });
        holder.mBtnMyOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelClickListener != null) {
                    cancelClickListener.onCancelClickListener(vo, position);
                }
            }
        });
        holder.mBtnMyOrderPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payClickListener != null) {
                    payClickListener.onPayClickListener(vo, position);
                }
            }
        });
        holder.mBtnMyOrderDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delClickListener != null) {
                    delClickListener.onDelClickListener(vo, position);
                }
            }
        });
        holder.mTvOrderLookOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LookOrderclickListener != null) {
                    LookOrderclickListener.onLookOrderClickListener();
                }
            }
        });

    }

    /**
     * 显示状态按钮
     *
     * @param holder
     * @param ordertype
     * @param cancel
     * @param pay
     * @param del
     */
    private void showBtn(ViewHodler holder, int ordertype, boolean cancel, boolean pay, boolean del) {
        switch (ordertype) {
            case 1://有赞订单
                holder.mBtnMyOrderCancel.setVisibility(View.GONE);
                holder.mBtnMyOrderDel.setVisibility(View.GONE);
                holder.mBtnMyOrderPay.setVisibility(View.GONE);
                break;
            case 2://内部订单
                holder.mBtnMyOrderCancel.setVisibility(cancel ? View.VISIBLE : View.GONE);
                holder.mBtnMyOrderDel.setVisibility(del ? View.VISIBLE : View.GONE);
                holder.mBtnMyOrderPay.setVisibility(pay ? View.VISIBLE : View.GONE);
                break;
        }

    }

    private void showYlayout(ViewHodler holder, boolean isShow) {
        holder.mLlMyOrderYh.setVisibility(isShow ? View.VISIBLE : View.GONE);
        holder.mViewLineGray.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        public TextView mTvOrderLookOther;
        public RelativeLayout mRlvOrderLookOrder;
        public TextView mTvMyOrderCode;
        public ImageView mIvMyOrderStatus;
        public RecyclerView mRlvMyoderInfom;
        //只是用于主商品没有情况下展示
        public RecyclerView mRlvMyoderInfomBooks;
        public View mViewLineGray;
        public TextView mTvMyOrderYPrice;
        public LinearLayout mLlMyOrderYh;
        public View mViewLineGra;
        public TextView mTvMyOrderAllPrice;
        public Button mBtnMyOrderCancel;
        public Button mBtnMyOrderPay;
        public Button mBtnMyOrderDel;
        public LinearLayout mLiLayoutContent;


        public ViewHodler(View itemView) {
            super(itemView);
            this.mLiLayoutContent = (LinearLayout) itemView.findViewById(R.id.li_order_layout_content);
            this.mTvOrderLookOther = (TextView) itemView.findViewById(R.id.tv_order_look_other);
            this.mRlvOrderLookOrder = (RelativeLayout) itemView.findViewById(R.id.rlv_order_look_oder);
            this.mTvMyOrderCode = (TextView) itemView.findViewById(R.id.tv_my_order_code);
            this.mIvMyOrderStatus = (ImageView) itemView.findViewById(R.id.iv_my_order_status);
            this.mRlvMyoderInfom = (RecyclerView) itemView.findViewById(R.id.rlv_myoder_infom);
            this.mRlvMyoderInfomBooks = (RecyclerView) itemView.findViewById(R.id.rlv_myoder_infom_books);
            this.mViewLineGray = (View) itemView.findViewById(R.id.view_line_gray);
            this.mTvMyOrderYPrice = (TextView) itemView.findViewById(R.id.tv_my_order_y_price);
            this.mLlMyOrderYh = (LinearLayout) itemView.findViewById(R.id.ll_my_order_yh);
            this.mViewLineGra = (View) itemView.findViewById(R.id.view_line_gra);
            this.mTvMyOrderAllPrice = (TextView) itemView.findViewById(R.id.tv_my_order_all_price);
            this.mBtnMyOrderCancel = (Button) itemView.findViewById(R.id.btn_my_order_cancel);
            this.mBtnMyOrderPay = (Button) itemView.findViewById(R.id.btn_my_order_pay);
            this.mBtnMyOrderDel = (Button) itemView.findViewById(R.id.btn_my_order_del);
        }
    }


}
