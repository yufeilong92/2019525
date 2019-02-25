package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.umeng.debug.log.E;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmTextColorUtil;
import com.xuechuan.xcedu.utils.SharedSeletResultListUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.CaseCardVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.QuestionsBean;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;

import java.util.ArrayList;
import java.util.HashMap;
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
public class GmGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<?> mData;
    private final LayoutInflater mInflater;
    private List<DoBankSqliteVo> mDoLists;
    private final GmTextColorUtil mUtil;
    private GmReadColorManger mColorManger;
    /**
     * 当期坐标
     */
    private int mCurrentPostion=-1;

    public GmGridViewAdapter(Context mContext, List<?> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
        mUtil = GmTextColorUtil.get_Instance(mContext);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_layout_gridview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        QuestionCaseVo vo = (QuestionCaseVo) mData.get(position);
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
        //0为未选，1为正确 ，2 为漏选 ，3为错误
        if (mVo != null) {
            if (mVo.getIsDo() == 0) {
                mUtil.status(GmTextColorUtil.TextColorStatus.GRAY);
            }
            if (mVo.getIsright() == 0) {
                mUtil.status(GmTextColorUtil.TextColorStatus.GRAY);
            } else if (mVo.getIsright() == 1) {
                mUtil.status(GmTextColorUtil.TextColorStatus.GREED);
            } else if (mVo.getIsright() == 2) {
                mUtil.status(GmTextColorUtil.TextColorStatus.MISS);
            } else if (mVo.getIsright() == 3) {
                mUtil.status(GmTextColorUtil.TextColorStatus.RED);
            }

        } else {//未作题
            mUtil.status(GmTextColorUtil.TextColorStatus.GRAY);
        }

        if (mCurrentPostion==position){
            holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn);
            holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            holder.mTvPopGrdviewSelect.setBackgroundResource(mUtil.getTextBg());
            holder.mTvPopGrdviewSelect.setTextColor(mUtil.getTextColor());
        }
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView mTvPopGrdviewSelect;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mTvPopGrdviewSelect = (TextView) rootView.findViewById(R.id.tv_pop_grdview_select);
        }
    }
}
