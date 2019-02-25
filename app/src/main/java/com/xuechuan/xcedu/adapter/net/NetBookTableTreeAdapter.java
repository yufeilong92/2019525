package com.xuechuan.xcedu.adapter.net;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.callback.IFooterCallBack;
import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeListViewAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 课程表
 * @author: L-BackPacker
 * @date: 2018/5/15 9:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetBookTableTreeAdapter extends TreeListViewAdapter {


    public NetBookTableTreeAdapter(ListView mTree, Context context, List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_net_table_list, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (node.getpId().equals("-1")) {
            holder.rootView.setClickable(false);
            holder.mChbNetPlay.setVisibility(View.GONE);
        }else {

            holder.mChbNetPlay.setVisibility(View.VISIBLE);
        }
        if (node.getIcon() == -1) {
            Object o = node.getBean();
            if (o!=null){
                VideosBeanVo bean = (VideosBeanVo)o;
                if (bean.isIstrysee()) {
                    holder.mIvNetGoorbuy.setVisibility(View.INVISIBLE);
                }else {
                    holder.mIvNetGoorbuy.setImageResource(R.mipmap.ic_login_password);
                }
            }
        } else {

            holder.mIvNetGoorbuy.setVisibility(View.VISIBLE);
            holder.mIvNetGoorbuy.setImageResource(node.getIcon());
        }
        holder.mTvNetTitle.setText(node.getName());
        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public CheckBox mChbNetPlay;
        public TextView mTvNetTitle;
        public ImageView mIvNetGoorbuy;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mChbNetPlay = (CheckBox) rootView.findViewById(R.id.chb_net_play);
            this.mTvNetTitle = (TextView) rootView.findViewById(R.id.tv_net_title);
            this.mIvNetGoorbuy = (ImageView) rootView.findViewById(R.id.iv_net_goorbuy);
        }

    }
}
