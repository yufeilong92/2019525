package com.xuechuan.xcedu.adapter.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.vo.HomeInfomBean;
import com.xuechuan.xcedu.vo.TeachersBean;
import com.xuechuan.xcedu.weight.FlowLayout;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.net
 * @Description: 网课详情页
 * @author: L-BackPacker
 * @date: 2018.11.24 下午 5:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetHomeInfomAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Object> mListDatas;
    private final LayoutInflater mInfalter;
    private String url = "https://appres.xuechuanedu.cn/product.html?id=5";
    private static String TAG = "【" + NetHomeInfomAdapter.class + "】==";
    /**
     * 上面界面
     */
    private int HOME_IMAGE = 100;
    /**
     * 下面界面
     */
    private int HOME_WEB = 101;
    private int HOME_ALL = HOME_IMAGE;
    private HomeInfomBean.DataBean vo;

    public NetHomeInfomAdapter(Context mContext, List<Object> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInfalter = LayoutInflater.from(mContext);
    }

    public void setAdapterView(HomeInfomBean.DataBean vo) {
        this.vo = vo;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HOME_IMAGE) {
            return new NetHomeFistViewHolder(mInfalter.inflate(R.layout.item_net_home_fist, null));
        } else if (viewType == HOME_WEB) {
            return new NetHomeLastViewHolder(mInfalter.inflate(R.layout.item_net_home_last, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NetHomeFistViewHolder) {
            NetHomeFistViewHolder fistViewHolder = (NetHomeFistViewHolder) holder;
            setFistViewHolde(fistViewHolder);
        } else if (holder instanceof NetHomeLastViewHolder) {
            NetHomeLastViewHolder lastViewHolder = (NetHomeLastViewHolder) holder;
            setLastViewHolde(lastViewHolder);
        }

    }

    /**
     * 设置增值监听
     */
    private OnZnZClickListener onZnZClickListener;

    public interface OnZnZClickListener {
        public void onClickZnZ(List<GiftVo> gifts);
    }

    public void setOnZnZClickListener(OnZnZClickListener onZnZClickListener) {
        this.onZnZClickListener = onZnZClickListener;
    }

    /**
     * 设置赠品监听
     */
    private OnZnPClickListener onZnPClickListener;

    public interface OnZnPClickListener {
        public void onClickZnP(List<GiftVo> gifts);
    }

    public void setOnZnPClickListener(OnZnPClickListener onZnPClickListener) {
        this.onZnPClickListener = onZnPClickListener;
    }

    /**
     * 首页展示模块
     *
     * @param fistViewHolder
     */
    private void setFistViewHolde(NetHomeFistViewHolder fistViewHolder) {
        if (vo == null) return;
        if (StringUtil.isEmpty(vo.getCoverimg())) {
            fistViewHolder.mIvHomeBookInfomImage.setImageResource(R.mipmap.s_n);
        } else {
            MyAppliction.getInstance().displayImages(fistViewHolder.mIvHomeBookInfomImage, vo.getCoverimg(), false);
        }
        fistViewHolder.mTvHomeBookInfomTitle.setText(vo.getName());
        fistViewHolder.mTvBInfTagOne.setText(vo.getValueaddservice());
        //判断增值服务
        fistViewHolder.mLlNetPopZenz.setVisibility(StringUtil.isEmpty(vo.getValueaddservice()) ? View.GONE : View.VISIBLE);
        StringBuffer buffer = new StringBuffer();
        final List<GiftVo> gifts = vo.getGifts();
        if (gifts != null && !gifts.isEmpty()) {
            for (int i = 0; i < gifts.size(); i++) {
                GiftVo bean = gifts.get(i);
                buffer.append(bean.getName());
                if (i != gifts.size() - 1)
                    buffer.append(",");
            }
        }
        String text = buffer.toString();
        fistViewHolder.mTvNetInfomComplimentary.setText(text);
        fistViewHolder.mTvNetInfomComplimentary.setVisibility(StringUtil.isEmpty(text) ? View.INVISIBLE : View.VISIBLE);
        //是否有赠品布局
        fistViewHolder.mLlNetPopZnP.setVisibility(gifts == null || gifts.isEmpty() ? View.GONE : View.VISIBLE);
        fistViewHolder.mViewLineOne.setVisibility(gifts == null || gifts.isEmpty() ? View.GONE : View.VISIBLE);
        List<TeachersBean> teachers = vo.getTeachers();
        fistViewHolder.mFlowNetInfomLayoutTeacher.removeAllViews();
        if (teachers != null && !teachers.isEmpty()) {
            for (int i = 0; i < teachers.size(); i++) {
                TeachersBean teachersBean = teachers.get(i);
                View itemvo = LayoutInflater.from(mContext).inflate(R.layout.item_home_net_teacher, fistViewHolder.mFlowNetInfomLayoutTeacher, false);
                ImageView imagHead = (ImageView) itemvo.findViewById(R.id.iv_home_teacher);
                if (!StringUtil.isEmpty(teachersBean.getHeadimg())) {
                    MyAppliction.getInstance().displayImages(imagHead, teachersBean.getHeadimg(), true);
                } else {
                    imagHead.setImageResource(R.drawable.ic_m_hear);
                }
                TextView tvName = (TextView) itemvo.findViewById(R.id.tv_home_teacherName);
                tvName.setText(teachersBean.getName());
                fistViewHolder.mFlowNetInfomLayoutTeacher.addView(itemvo);
            }
        }

        fistViewHolder.mLlNetPopZnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//赠品
                if (onZnPClickListener != null) {
                    onZnPClickListener.onClickZnP(gifts);
                }
            }
        });

    }

    /**
     * 首页web模块展示模块
     *
     * @param lastViewHolder
     */
    private void setLastViewHolde(NetHomeLastViewHolder lastViewHolder) {
        if (vo == null) return;
        lastViewHolder.mWebComfion.loadUrl(vo.getDetailurl());
        WebSettings settings = lastViewHolder.mWebComfion.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                HOME_ALL = HOME_IMAGE;
                break;
            case 1:
                HOME_ALL = HOME_WEB;
                break;
        }
        return HOME_ALL;
    }

    public class NetHomeFistViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvHomeBookInfomImage;
        public ImageView mIvHomeBookInfomBack;
        public TextView mTvHomeBookInfomTitle;
        public TextView mTvBInfTagOne;
        public LinearLayout mLlNetPopZenz;
        public TextView mTvNetInfomComplimentary;
        public LinearLayout mLlNetPopZnP;
        public FlowLayout mFlowNetInfomLayoutTeacher;
        public View mViewLine;
        public View mViewLineOne;

        public NetHomeFistViewHolder(View itemView) {
            super(itemView);
            this.mViewLine = (View) itemView.findViewById(R.id.view_line);
            this.mViewLineOne = (View) itemView.findViewById(R.id.view_line_one);
            this.mIvHomeBookInfomImage = (ImageView) itemView.findViewById(R.id.iv_home_book_Infom_image);
            this.mIvHomeBookInfomBack = (ImageView) itemView.findViewById(R.id.iv_home_book_infom_back);
            this.mTvHomeBookInfomTitle = (TextView) itemView.findViewById(R.id.tv_home_book_infom_title);
            this.mTvBInfTagOne = (TextView) itemView.findViewById(R.id.tv_b_inf_tag_one);
            this.mLlNetPopZenz = (LinearLayout) itemView.findViewById(R.id.ll_net_pop_zenz);
            this.mTvNetInfomComplimentary = (TextView) itemView.findViewById(R.id.tv_net_infom_complimentary);
            this.mLlNetPopZnP = (LinearLayout) itemView.findViewById(R.id.ll_net_pop_znp);
            this.mFlowNetInfomLayoutTeacher = (FlowLayout) itemView.findViewById(R.id.flow_net_infom_layout_teacher);

        }
    }

    public class NetHomeLastViewHolder extends RecyclerView.ViewHolder {
        public WebView mWebComfion;

        public NetHomeLastViewHolder(View itemView) {
            super(itemView);
            this.mWebComfion = (WebView) itemView.findViewById(R.id.web_comfion);
        }
    }


}
