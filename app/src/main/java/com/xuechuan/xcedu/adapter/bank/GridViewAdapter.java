package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.SharedSeletResultListUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.QuestionAllVo;
import com.xuechuan.xcedu.vo.QuestionsBean;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;

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
public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<QuestionsBean> mData;
    private int selectItem = -1;
    private boolean isSubmit = false;
    private HashMap<Integer, Boolean> mIsSelect;

    public GridViewAdapter(Context mContext, List<QuestionsBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        if (mIsSelect==null||mIsSelect.isEmpty()) {
            mIsSelect = new HashMap<>();
        } else {
            mIsSelect.clear();
        }
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

    public void setItemSelect(boolean isSubmit, int postion) {
        this.selectItem = postion;
        this.isSubmit = isSubmit;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        QuestionsBean bean = mData.get(position);
        List<UseSelectItemInfomVo> user = SharedSeletResultListUtil.getInstance().getUser();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_gridview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        mIsSelect.put(position, false);
        holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
        holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
        if (selectItem == position) {//显示当前题
            mIsSelect.put(position, true);
            holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn);
            holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        if (mIsSelect.get(position)) {
            holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn);
            holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            bindViewData(position, holder, bean, user);
        }
        holder.mTvPopGrdviewSelect.setText((position + 1) + "");
        return convertView;
    }

    private void bindViewData(int position, ViewHolder holder, QuestionsBean bean, List<UseSelectItemInfomVo> user) {
        if (isSubmit) {//提交
            String id = String.valueOf(bean.getId());//未做全部设置灰色
            if (user == null || user.isEmpty()) {
                holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
                holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
                return;
            }
            for (int i = 0; i < user.size(); i++) {
                UseSelectItemInfomVo vo = user.get(i);
//            判断是否做过
                if (id.equalsIgnoreCase(String.valueOf(vo.getId()))) {//做过
                    String status = vo.getItemStatus();
                    if (StringUtil.isEmpty(status)) {
                        holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_su);
                        holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (status.equals("0")) {//正确
                        holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_n);
                        holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_tab_right));
                    } else if (status.equals("1")) {//错误
                        holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.btn_answer_bg_error);
                        holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.red_text));
                    } else if (status.equals("2")) {
                        holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_miss);
                        holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_tab_miss_color));
                    } else {
                        holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
                        holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
                    }
                }
            }

        } else {//未提交
            setBg(holder, bean, user);

        }

    }

    private void setBg(@NonNull ViewHolder holder, QuestionsBean bean, List<UseSelectItemInfomVo> user) {
        if (user == null || user.isEmpty()) {
            holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
            holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
            return;
        }
        String id = String.valueOf(bean.getId());

        for (int i = 0; i < user.size(); i++) {
            UseSelectItemInfomVo vo = user.get(i);
            String id1 = String.valueOf(vo.getId());
            if (id.equals(id1)) {//有
                holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_su);
                holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        }
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
