package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmTextColorUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.CaseCardVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: GridViewAdapter.java
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 卡片适配器
 * @author: YFL
 * @date: 2018/5/9 22:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/9 星期三
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class GmCaseExamAdapter extends BaseAdapter {
    private Context mContext;
    private List<?> mData;
    private final LayoutInflater mInflater;
    private List<DoBankSqliteVo> mDoLists;
    private final GmTextColorUtil mUtil;
    private GmReadColorManger mColorManger;
    /**
     * 当期坐标
     */
    private int mCurrentPostion = -1;

    public GmCaseExamAdapter(Context mContext, List<?> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
        mUtil = GmTextColorUtil.get_Instance(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(int postion);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void doEventListDatas(List<DoBankSqliteVo> list) {
        if (list == null || list.isEmpty()) return;
        this.mDoLists = list;
        notifyDataSetChanged();
    }

    public void doEventColor(GmReadColorManger colorManger) {
        if (colorManger == null) return;
        this.mColorManger = colorManger;
        notifyDataSetChanged();
    }

    public void doCurrentPostion(int postion) {
        this.mCurrentPostion = postion;
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData == null ? 0 : position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_layout_case_gridview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CaseCardVo bean = (CaseCardVo) mData.get(position);
        QuestionCaseVo vo = bean.getCasevo();
        holder.mTvPopGrdviewSelect.setText(String.valueOf(position + 1));
        DoBankSqliteVo mVo = null;
        if (mDoLists != null && !mDoLists.isEmpty()) {
            for (int i = 0; i < mDoLists.size(); i++) {
                DoBankSqliteVo sqliteVo = mDoLists.get(i);
                if (sqliteVo.getQuestion_id() == vo.getQuestion_id()) {
                    mVo = sqliteVo;
                    break;
                }
            }
        }
        if (bean.getCasevo().getQuestiontype() == 4 ) {
            showEvalues(holder, true);
            holder.mTvPopGrdviewNumberIndex.setText(String.valueOf(position + 1));
            holder.mTvPopGrdviewNumber.setText("0分");
        } else {
            showEvalues(holder, false);
        }
        //0为未选，1为正确 ，2 为漏选 ，3为错误
        if (mVo != null) {
            if (mVo.getIsDo() == 0) {
                mUtil.status(GmTextColorUtil.TextColorStatus.GRAY);
            }
          /*  if (mVo.getIsAnalySis() == 1) {//是否是解析
                mUtil.status(GmTextColorUtil.TextColorStatus.BLACE);
            } else*/
          if (mVo.getIsright() == 0) {//未做
                mUtil.status(GmTextColorUtil.TextColorStatus.GRAY);
            } else if (mVo.getIsright() == 1) {//正确
                mUtil.status(GmTextColorUtil.TextColorStatus.GREED);
            } else if (mVo.getIsright() == 2) {//漏选
                mUtil.status(GmTextColorUtil.TextColorStatus.MISS);
            } else if (mVo.getIsright() == 3) {//错误
                mUtil.status(GmTextColorUtil.TextColorStatus.RED);
            } else if (mVo.getIsright() == 4) {
                mUtil.status(GmTextColorUtil.TextColorStatus.QianGray);
            }
         /*   if (mVo.getIsright() == 4) {//自评布局
                showEvalues(holder, true);
            } else {
                showEvalues(holder, false);
            }*/
            if (mVo.getQuestiontype() == 4) {
                mUtil.status(GmTextColorUtil.TextColorStatus.QianGray);
                showEvalues(holder, true);
                holder.mTvPopGrdviewNumberIndex.setText(String.valueOf(position + 1));
                String mos = mVo.getMos();
                holder.mTvPopGrdviewNumber.setText(StringUtil.isEmpty(mos) ? "0分" : mVo.getMos().concat("分"));
            }

        } else {//未作题
            if (bean.getCasevo().getQuestiontype() == 4) {
                mUtil.status(GmTextColorUtil.TextColorStatus.QianGray);
            } else
                mUtil.status(GmTextColorUtil.TextColorStatus.GRAY);
        }

        if (mCurrentPostion == position) {
            holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn);
            holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.mLlPopGradviewEvalues.setBackgroundResource(R.drawable.bg_select_answer_btn);
        } else {
            holder.mTvPopGrdviewSelect.setBackgroundResource(mUtil.getTextBg());
            holder.mTvPopGrdviewSelect.setTextColor(mUtil.getTextColor());
            holder.mLlPopGradviewEvalues.setBackgroundResource(mUtil.getTextBg());
        }
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(position);
                }
            }
        });
        return convertView;
    }

    private void showEvalues(ViewHolder holder, boolean show) {
        holder.mLlPopGradviewEvalues.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.mTvPopGrdviewSelect.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public static class ViewHolder {
        public View rootView;
        public TextView mTvPopGrdviewSelect;
        public TextView mTvPopGrdviewNumberIndex;
        public TextView mTvPopGrdviewNumber;
        public LinearLayout mLlPopGradviewEvalues;


        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mTvPopGrdviewSelect = (TextView) rootView.findViewById(R.id.tv_pop_grdview_select);
            this.mTvPopGrdviewNumberIndex = (TextView) rootView.findViewById(R.id.tv_pop_grdview_number_index);
            this.mTvPopGrdviewNumber = (TextView) rootView.findViewById(R.id.tv_pop_grdview_number);
            this.mLlPopGradviewEvalues = (LinearLayout) rootView.findViewById(R.id.ll_pop_gradview_evalues);
        }
    }


}
