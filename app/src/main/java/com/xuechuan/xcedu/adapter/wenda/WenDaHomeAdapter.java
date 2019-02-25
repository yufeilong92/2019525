package com.xuechuan.xcedu.adapter.wenda;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.ui.wenda.ProblemDetailActivity;
import com.xuechuan.xcedu.vo.HankPickVo;
import com.xuechuan.xcedu.vo.MyProblemVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.wenda
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019/1/9 20:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class WenDaHomeAdapter extends RecyclerView.Adapter {
    private final FragmentManager mFragmentManger;
    private Context mContext;
    private LayoutInflater mInflater;
    /**
     * 提问布局
     */
    private final int TIWENVIEW = 110;
    /**
     * 精选布局
     */
    private final int JINGXUANVIEW = 111;
    private int GMVIEW = TIWENVIEW;
    private boolean show = true;
    private List mHankPickLists;
    private List mMyProblemList;
    private List mWaitLists;
    private int mNumber = 0;
    /**
     * 主副
     */
    private boolean main = true;
    private JingXuanViewHolder mViewHolder;

    /**
     * 是否显示待解答布局
     *
     * @param show
     */
    public void ShowOneView(boolean show) {
        this.show = show;
        notifyDataSetChanged();
    }

    public void changerHankpicList(List mListDatas, int number) {
        this.mHankPickLists = mListDatas;
        this.mNumber = number;
        notifyDataSetChanged();
    }

    public void changerMyProlemList(List mListDatas) {
        this.mMyProblemList = mListDatas;
        notifyDataSetChanged();
    }

    public void changeWaitLists(List waites) {
        this.mWaitLists = waites;
        notifyDataSetChanged();
    }

    public WenDaHomeAdapter(Context mContext, FragmentManager manager) {
        this.mContext = mContext;
        this.mFragmentManger = manager;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnRefreshClickListener onRefreshClickListener;

    public interface OnRefreshClickListener {
        public void onClickItem(int type);
    }

    public void setOnRefreshClickListener(OnRefreshClickListener onItemClickListener) {
        this.onRefreshClickListener = onItemClickListener;
    }

    public void showEmptyImager(boolean show) {
        if (mViewHolder != null) {
            mViewHolder.mIvEmpty.setVisibility(show ? View.VISIBLE : View.GONE);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TIWENVIEW) {
            return new WenDaViewHolder(mInflater.inflate(R.layout.item_wenda_one, null));
        } else if (viewType == JINGXUANVIEW) {
            return new JingXuanViewHolder(mInflater.inflate(R.layout.item_jingxuan_two, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WenDaViewHolder) {
            WenDaViewHolder viewHolder = (WenDaViewHolder) holder;
            doWenDa(viewHolder);

        } else if (holder instanceof JingXuanViewHolder) {
            mViewHolder = (JingXuanViewHolder) holder;
            doJingXuan(mViewHolder);

        }
    }

    //待解决
    private void doWenDa(WenDaViewHolder viewHolder) {
        SolveDaAdapter adapter = new SolveDaAdapter(mContext, mWaitLists);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        viewHolder.mRlvWendaContent.setLayoutManager(manager);
        viewHolder.mRlvWendaContent.setAdapter(adapter);
        viewHolder.mTvWendaDaiNumber.setText(String.valueOf(mWaitLists.size()));
        adapter.setOnItemClickListener(new SolveDaAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(MyProblemVo.DatasBean o) {
                Intent intent = ProblemDetailActivity.start_Intent(mContext, o.getId(), o.getProblemstatus() == 3,
                        o.getContent());
                intent.putExtra(ProblemDetailActivity.CSTR_EXTRA_TITLE_STR, "问题详情");
                mContext.startActivity(intent);
            }
        });
    }

    //精选
    private void doJingXuan(JingXuanViewHolder viewHolder) {
        llClickListener(viewHolder.mLlWendaTwoJingxuan, viewHolder, 1);
        llClickListener(viewHolder.mLlWendatwoMy, viewHolder, 2);
        if (main) {//主界面
            if (mHankPickLists == null || mHankPickLists.isEmpty()) {
                showImgEmpty(viewHolder, false, false, true);
                return;
            }
            showImgEmpty(viewHolder, true, false, false);
            GridLayoutManager manager = new GridLayoutManager(mContext, 1);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            HandpickAdapter adapter = new HandpickAdapter(mContext, mHankPickLists);
            viewHolder.mTvWendatwoJingxuanNumber.setText(String.valueOf(mNumber));
            viewHolder.mRlvHankpickContent.setLayoutManager(manager);
            viewHolder.mRlvHankpickContent.setAdapter(adapter);
            adapter.setOnItemClickListener(new HandpickAdapter.OnItemClickListener() {
                @Override
                public void onClickItem(HankPickVo.DatasBean o) {
                    Intent intent = ProblemDetailActivity.start_Intent(mContext, o.getId(), o.getProblemstatus() == 3,
                            o.getContent());
                    intent.putExtra(ProblemDetailActivity.CSTR_EXTRA_TITLE_STR, "问题详情");
                    mContext.startActivity(intent);
                }
            });
        } else {//副界面
            if (mMyProblemList == null || mMyProblemList.isEmpty()) {
                showImgEmpty(viewHolder, false, false, true);
                return;
            }
            showImgEmpty(viewHolder, false, true, false);
            HandpickAdapter MyProblemadapter = new HandpickAdapter(mContext, mMyProblemList);
            GridLayoutManager manager1 = new GridLayoutManager(mContext, 1);
            manager1.setOrientation(GridLayoutManager.VERTICAL);
            viewHolder.mRlvMyprobleContent.setLayoutManager(manager1);
            viewHolder.mRlvMyprobleContent.setAdapter(MyProblemadapter);
            MyProblemadapter.setOnItemClickListener(new HandpickAdapter.OnItemClickListener() {
                @Override
                public void onClickItem(HankPickVo.DatasBean o) {
                    Intent intent = ProblemDetailActivity.start_Intent(mContext, o.getId(), o.getProblemstatus() == 3,
                            o.getContent());
                    intent.putExtra(ProblemDetailActivity.CSTR_EXTRA_TITLE_STR, "问题详情");
                    mContext.startActivity(intent);
                }
            });
        }
    /*    if (mHankPickLists == null || mHankPickLists.isEmpty()) {
            if (viewHolder.mRlvMyprobleContent.getVisibility() == View.GONE)
                showImgEmpty(viewHolder, false, false, true);
        } else {
            showImgEmpty(viewHolder, true, false, false);
            GridLayoutManager manager = new GridLayoutManager(mContext, 1);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            HandpickAdapter adapter = new HandpickAdapter(mContext, mHankPickLists);
            viewHolder.mTvWendatwoJingxuanNumber.setText(String.valueOf(mHankPickLists.size()));
            viewHolder.mRlvHankpickContent.setLayoutManager(manager);
            viewHolder.mRlvHankpickContent.setAdapter(adapter);
            adapter.setOnItemClickListener(new HandpickAdapter.OnItemClickListener() {
                @Override
                public void onClickItem(HankPickVo.DatasBean o) {
                    Intent intent = ProblemDetailActivity.start_Intent(mContext, o.getId(), o.getProblemstatus() == 3);
                    intent.putExtra(ProblemDetailActivity.CSTR_EXTRA_TITLE_STR, "问题详情");
                    mContext.startActivity(intent);
                }
            });
        }

        if (mMyProblemList == null || mMyProblemList.isEmpty()) {
            if (viewHolder.mRlvHankpickContent.getVisibility() == View.GONE)
                showImgEmpty(viewHolder, false, false, true);
        } else {
            showImgEmpty(viewHolder, false, true, false);
            HandpickAdapter MyProblemadapter = new HandpickAdapter(mContext, mMyProblemList);
            GridLayoutManager manager1 = new GridLayoutManager(mContext, 1);
            manager1.setOrientation(GridLayoutManager.VERTICAL);
            viewHolder.mRlvMyprobleContent.setLayoutManager(manager1);
            viewHolder.mRlvMyprobleContent.setAdapter(MyProblemadapter);
            MyProblemadapter.setOnItemClickListener(new HandpickAdapter.OnItemClickListener() {
                @Override
                public void onClickItem(HankPickVo.DatasBean o) {
                    Intent intent = ProblemDetailActivity.start_Intent(mContext, o.getId(), o.getProblemstatus() == 3);
                    intent.putExtra(ProblemDetailActivity.CSTR_EXTRA_TITLE_STR, "问题详情");
                    mContext.startActivity(intent);
                }
            });
        }
*/
    }

    private void showImgEmpty(JingXuanViewHolder viewHolder, boolean hank, boolean my, boolean show) {
        viewHolder.mRlvHankpickContent.setVisibility(hank ? View.VISIBLE : View.GONE);
        viewHolder.mRlvMyprobleContent.setVisibility(my ? View.VISIBLE : View.GONE);
        viewHolder.mIvEmpty.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void llClickListener(LinearLayout layout, final JingXuanViewHolder holder, final int type) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case 1://精选
                        main = true;
                        show(true, holder);
                        if (onRefreshClickListener != null) {
                            onRefreshClickListener.onClickItem(1);
                        }
                        break;
                    case 2://我的
                        main = false;
                        if (onRefreshClickListener != null) {
                            onRefreshClickListener.onClickItem(2);
                        }
                        show(false, holder);
                        break;

                }
            }
        });
    }


    private void show(boolean show, JingXuanViewHolder holder) {
        holder.mViewWendatwoLineOne.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        holder.mViewWendatwoLineTwo.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        holder.mRlvHankpickContent.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.mRlvMyprobleContent.setVisibility(show ? View.GONE : View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return show ? 2 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (show) {

            switch (position) {
                case 0:
                    GMVIEW = TIWENVIEW;
                    break;
                case 1:
                    GMVIEW = JINGXUANVIEW;
                    break;
            }
        } else {
            GMVIEW = JINGXUANVIEW;
        }
        return GMVIEW;
    }

    public class WenDaViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvWendaDaiNumber;
        public RecyclerView mRlvWendaContent;
        public LinearLayout mLlWendaoOneRoot;

        public WenDaViewHolder(View itemView) {
            super(itemView);
            this.mTvWendaDaiNumber = (TextView) itemView.findViewById(R.id.tv_wenda_dai_number);
            this.mRlvWendaContent = (RecyclerView) itemView.findViewById(R.id.rlv_wenda_content);
            this.mLlWendaoOneRoot = (LinearLayout) itemView.findViewById(R.id.ll_wendao_one_root);

        }
    }

    public class JingXuanViewHolder extends RecyclerView.ViewHolder {
        public View mViewWendatwoLineOne;
        public TextView mTvWendatwoJingxuanNumber;
        public LinearLayout mLlWendaTwoJingxuan;
        public View mViewWendatwoLineTwo;
        public TextView mTvWendatwoMyNumber;
        public LinearLayout mLlWendatwoMy;
        public RecyclerView mRlvHankpickContent;
        public RecyclerView mRlvMyprobleContent;
        public ImageView mIvEmpty;

        public JingXuanViewHolder(View itemView) {
            super(itemView);
            this.mViewWendatwoLineOne = (View) itemView.findViewById(R.id.view_wendatwo_line_one);
            this.mTvWendatwoJingxuanNumber = (TextView) itemView.findViewById(R.id.tv_wendatwo_jingxuan_number);
            this.mLlWendaTwoJingxuan = (LinearLayout) itemView.findViewById(R.id.ll_wenda_two_jingxuan);
            this.mViewWendatwoLineTwo = (View) itemView.findViewById(R.id.view_wendatwo_line_two);
            this.mTvWendatwoMyNumber = (TextView) itemView.findViewById(R.id.tv_wendatwo_my_number);
            this.mLlWendatwoMy = (LinearLayout) itemView.findViewById(R.id.ll_wendatwo_my);
            this.mRlvHankpickContent = (RecyclerView) itemView.findViewById(R.id.rlv_hankpick_content);
            this.mRlvMyprobleContent = (RecyclerView) itemView.findViewById(R.id.rlv_myproble_content);
            this.mIvEmpty = (ImageView) itemView.findViewById(R.id.iv_empty);

        }
    }

}
