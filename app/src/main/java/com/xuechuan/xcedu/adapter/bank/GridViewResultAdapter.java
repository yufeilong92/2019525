package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
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
public class GridViewResultAdapter extends BaseAdapter {
    private Context mContext;
    private List<QuestionsBean> mData;
    private HashMap<Integer, Boolean> mSelectitem;

    public GridViewResultAdapter(Context mContext, List<QuestionsBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mSelectitem = new HashMap<>();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_gridview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        QuestionsBean bean = mData.get(position);
        List<UseSelectItemInfomVo> user = SharedSeletResultListUtil.getInstance().getUser();
        holder.mTvPopGrdviewSelect.setText((position + 1) + "");
        String id = String.valueOf(bean.getId());
        if (user == null || user.isEmpty()) {
            mSelectitem.put(position, false);
            holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
            holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
        } else {
            mSelectitem.put(position, false);
            setBG(position, holder, user, id);
        }
        if (mSelectitem.get(position)) {
            setBGSave(holder, user, id);
        } else {
            holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
            holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
        }


        return convertView;
    }

    private void setBGSave(ViewHolder holder, List<UseSelectItemInfomVo> user, String id) {
        for (int i = 0; i < user.size(); i++) {
            UseSelectItemInfomVo vo = user.get(i);
//            判断是否做过
            if (id.equalsIgnoreCase(String.valueOf(vo.getId()))) {//做过
                String status = vo.getItemStatus();
                if (StringUtil.isEmpty(status)) {
                    holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
                    holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
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
    }

    private void setBG(int position, ViewHolder holder, List<UseSelectItemInfomVo> user, String id) {
        for (int i = 0; i < user.size(); i++) {
            UseSelectItemInfomVo vo = user.get(i);
//            判断是否做过
            if (id.equalsIgnoreCase(String.valueOf(vo.getId()))) {//做过
                String status = vo.getItemStatus();
                mSelectitem.put(position, true);
                if (StringUtil.isEmpty(status)) {
                    holder.mTvPopGrdviewSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
                    holder.mTvPopGrdviewSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
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
