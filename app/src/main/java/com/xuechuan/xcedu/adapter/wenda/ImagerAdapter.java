package com.xuechuan.xcedu.adapter.wenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.wenda
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.11 下午 3:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ImagerAdapter extends BaseAdapter {
    private Context mContext;
    private List<?> mListDatas;

    public ImagerAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
    }

    @Override
    public int getCount() {
        return mListDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mListDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_detail_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String vo = (String) mListDatas.get(position);
        MyAppliction.getInstance().displayImages(holder.mIvDetailContent, vo, false);
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView mIvDetailContent;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mIvDetailContent = (ImageView) rootView.findViewById(R.id.iv_detail_content);
        }

    }
}
