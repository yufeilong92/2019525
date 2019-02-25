package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;
import com.xuechuan.xcedu.vo.Db.UserLookVo;
import com.xuechuan.xcedu.vo.SkillTextVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AtricleTreeAdapter.java
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 树状列表
 * @author: YFL
 * @date: 2018/5/1 14:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/1 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class AtricleTreeAdapter extends TreeRecyclerAdapter {
    private String mOid;
    private List<SkillTextVo.DatasBean> mData;

    public AtricleTreeAdapter(RecyclerView mTree, Context context, List<Node> datas,
                                 int defaultExpandLevel, int iconExpand, int iconNoExpand,
                                 String moid, List<SkillTextVo.DatasBean> list) {
        super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
        this.mData = list;
        this.mOid = moid;
    }

    public AtricleTreeAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel) {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public void onBindViewHolder(Node node, RecyclerView.ViewHolder holder, int position) {
/*        DbHelperAssist assist = DbHelperAssist.getInstance(mContext);
        List<UserLookVo> lookVos = new ArrayList<>();
        UserLookVo userLookVo = new UserLookVo();*/


        TreeViewHolder viewHolder = (TreeViewHolder) holder;
        viewHolder.mTvAtricleTree.setText(node.getName());
        TextPaint paint = viewHolder.mTvAtricleTree.getPaint();
        if (node.getIcon() == -1) {
            ChildrenBeanVo vo = (ChildrenBeanVo) node.getBean();
            viewHolder.mIcon.setVisibility(View.GONE);
            paint.setFakeBoldText(false);
            viewHolder.mIvTreeMark.setVisibility(View.INVISIBLE);
            viewHolder.mLiLook.setVisibility(View.VISIBLE);
            viewHolder.mTvLook.setText(String.valueOf(vo.getRnum()));
            viewHolder.mTvCout.setText(String.valueOf(vo.getQnum()));
//            setdata(node, assist, lookVos, userLookVo, viewHolder);
        } else {
            paint.setFakeBoldText(true);
            viewHolder.mLiLook.setVisibility(View.GONE);
            viewHolder.mIcon.setVisibility(View.VISIBLE);
            viewHolder.mIvTreeMark.setVisibility(View.VISIBLE);
            viewHolder.mIcon.setImageResource(node.getIcon());
        }
    }

    private void setdata(Node node, DbHelperAssist assist, List<UserLookVo> lookVos, UserLookVo userLookVo, TreeViewHolder viewHolder) {
        viewHolder.mTvCout.setText(node.getChildren().size() + "");
        if (!StringUtil.isEmpty(mOid)) {
            UserInfomDb userInfomDb = DbHelperAssist.getInstance(mContext).queryWithuuUserInfom();
            if (mOid.equals("1")) {//技术章节
                if (userInfomDb != null) {
                    List<UserLookVo> skillData = userInfomDb.getSkillData();
                    if (skillData != null && !skillData.isEmpty()) {
                        for (int i = 0; i < skillData.size(); i++) {
                            final UserLookVo vo = skillData.get(i);
                            boolean isSameZ = false;
                            int qbun = -1;
                            //获取题干信息 是否有保存的题干
                            for (int k = 0; k < mData.size(); k++) {
                                SkillTextVo.DatasBean bean = mData.get(k);
                                for (int h = 0; h < bean.getChildren().size(); h++) {
                                    ChildrenBeanVo beanVo = bean.getChildren().get(h);
                                    if (!StringUtil.isEmpty(vo.getCount()) && beanVo.getId() == Integer.parseInt(vo.getChapterId())) {
                                        isSameZ = true;
                                        qbun = beanVo.getQnum();

                                    }
                                }
                            }
                            if (isSameZ) {
                                String id = String.valueOf(node.getId());
                                if (id.equals(vo.getChapterId())) {
                                    if (Integer.valueOf(qbun) < Integer.valueOf(vo.getCount())) {
                                        userLookVo.setChapterId(vo.getChapterId());
                                        userLookVo.setNextId(String.valueOf(Integer.valueOf(qbun) - 1));
                                        userLookVo.setCount(String.valueOf(qbun));
                                        lookVos.add(userLookVo);
                                        assist.upDataSkillRecord(lookVos);
                                    }
                                    viewHolder.mTvLook.setText((Integer.valueOf(vo.getNextId()) + 1) + "");
                                    viewHolder.mLiLook.setVisibility(View.VISIBLE);
                                    viewHolder.mTvCout.setText(vo.getCount() + "");
                                }
                            } else {
                                viewHolder.mLiLook.setVisibility(View.GONE);
                            }

                        }
                    }
                }

            } else if (mOid.equals("2")) {//综合
                if (userInfomDb != null) {
                    List<UserLookVo> skillData = userInfomDb.getColoctData();
                    if (skillData != null && !skillData.isEmpty()) {
                        for (int i = 0; i < skillData.size(); i++) {
                            final UserLookVo vo = skillData.get(i);
                            boolean isSameZ = false;
                            int qbun = -1;

                            //获取题干信息 是否有保存的题干
                            for (int k = 0; k < mData.size(); k++) {
                                SkillTextVo.DatasBean bean = mData.get(k);
                                for (int h = 0; h < bean.getChildren().size(); h++) {
                                    ChildrenBeanVo beanVo = bean.getChildren().get(h);
                                    if (!StringUtil.isEmpty(vo.getCount()) && beanVo.getId() == Integer.valueOf(vo.getChapterId())) {
                                        isSameZ = true;
                                        qbun = beanVo.getQnum();
                                    }
                                }
                            }
                            if (isSameZ) {
                                String id = String.valueOf(node.getId());
                                if (id.equals(vo.getChapterId())) {
                                    if (Integer.valueOf(qbun) < Integer.valueOf(vo.getCount())) {
                                        userLookVo.setChapterId(vo.getChapterId());
                                        userLookVo.setNextId(String.valueOf(Integer.valueOf(qbun) - 1));
                                        userLookVo.setCount(String.valueOf(qbun));
                                        lookVos.add(userLookVo);
                                        assist.upDataSkillRecord(lookVos);
                                    }
                                    viewHolder.mTvLook.setText((Integer.valueOf(vo.getNextId()) + 1) + "");
                                    viewHolder.mLiLook.setVisibility(View.VISIBLE);
                                    viewHolder.mTvCout.setText(vo.getCount() + "");
                                }
                            } else {
                                viewHolder.mLiLook.setVisibility(View.GONE);
                            }

                        }
                    }
                }


            } else if (mOid.equals("3")) {//案例
                if (userInfomDb != null) {
                    List<UserLookVo> skillData = userInfomDb.getCaseData();
                    if (skillData != null && !skillData.isEmpty()) {
                        for (int i = 0; i < skillData.size(); i++) {
                            final UserLookVo vo = skillData.get(i);
                            boolean isSameZ = false;
                            int qbun = -1;

                            //获取题干信息 是否有保存的题干
                            for (int k = 0; k < mData.size(); k++) {
                                SkillTextVo.DatasBean bean = mData.get(k);
                                for (int h = 0; h < bean.getChildren().size(); h++) {
                                    ChildrenBeanVo beanVo = bean.getChildren().get(h);
                                    if (!StringUtil.isEmpty(vo.getCount()) && beanVo.getId() == Integer.valueOf(vo.getChapterId())) {
                                        isSameZ = true;
                                        qbun = beanVo.getQnum();

                                    }
                                }
                            }
                            if (isSameZ) {
                                String id = String.valueOf(node.getId());
                                if (id.equals(vo.getChapterId())) {
                                    if (Integer.valueOf(qbun) < Integer.valueOf(vo.getCount())) {
                                        userLookVo.setChapterId(vo.getChapterId());
                                        userLookVo.setNextId(String.valueOf(Integer.valueOf(qbun) - 1));
                                        userLookVo.setCount(String.valueOf(qbun));
                                        lookVos.add(userLookVo);
                                        assist.upDataSkillRecord(lookVos);
                                    }
                                    viewHolder.mTvLook.setText((Integer.valueOf(vo.getNextId()) + 1) + "");
                                    viewHolder.mLiLook.setVisibility(View.VISIBLE);
                                    viewHolder.mTvCout.setText(vo.getCount() + "");
                                }
                            } else {
                                viewHolder.mLiLook.setVisibility(View.GONE);
                            }

                        }
                    }
                }

            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TreeViewHolder holder = new TreeViewHolder(View.inflate(mContext, R.layout.item_atricle_tree, null));
        return holder;
    }

    public class TreeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvTreeMark;
        public TextView mTvAtricleTree;
        public ImageView mIcon;
        public TextView mTvLook;
        public TextView mTvCout;
        public LinearLayout mLiLook;

        public TreeViewHolder(View itemView) {
            super(itemView);
            this.mIvTreeMark = (ImageView) itemView.findViewById(R.id.iv_tree_mark);
            this.mTvAtricleTree = (TextView) itemView.findViewById(R.id.tv_atricle_tree);
            this.mIcon = (ImageView) itemView.findViewById(R.id.icon);
            this.mTvLook = (TextView) itemView.findViewById(R.id.tv_look);
            this.mTvCout = (TextView) itemView.findViewById(R.id.tv_cout);
            this.mLiLook = (LinearLayout) itemView.findViewById(R.id.li_look);
        }
    }

}
