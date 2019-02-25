package com.xuechuan.xcedu.adapter.bank;

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
import com.xuechuan.xcedu.adapter.home.AtricleTreeAdapter;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.sqlitedb.DoLogProgressSqliteHelp;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;
import com.xuechuan.xcedu.vo.Db.UserLookVo;
import com.xuechuan.xcedu.vo.SkillTextVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoLogProgreeSqliteVo;

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
public class AtricleNewTreeAdapter extends TreeRecyclerAdapter {
    private String mOid;
    private List<SkillTextVo.DatasBean> mData;
    private DoLogProgressSqliteHelp mProgressSqliteHelp;
    private int mCoursdId = 0;

    public AtricleNewTreeAdapter(RecyclerView mTree, Context context, List<Node> datas,
                                 int defaultExpandLevel, int iconExpand, int iconNoExpand,
                                 String moid, List<SkillTextVo.DatasBean> list) {
        super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
        this.mData = list;
        this.mOid = moid;
    }

    public AtricleNewTreeAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel) {
        super(mTree, context, datas, defaultExpandLevel);
    }

    public void setHelp(DoLogProgressSqliteHelp progressSqliteHelp) {
        this.mProgressSqliteHelp = progressSqliteHelp;
        notifyDataSetChanged();
    }

    public void setCourseId(int courseId) {
        this.mCoursdId = courseId;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(Node node, RecyclerView.ViewHolder holder, int position) {
/*        DbHelperAssist assist = DbHelperAssist.getInstance(mContext);
        List<UserLookVo> lookVos = new ArrayList<>();
        UserLookVo userLookVo = new UserLookVo();*/

        AtricleNewTreeAdapter.TreeViewHolder viewHolder = (AtricleNewTreeAdapter.TreeViewHolder) holder;
        viewHolder.mTvAtricleTree.setText(node.getName());

        TextPaint paint = viewHolder.mTvAtricleTree.getPaint();
        if (node.getIcon() == -1) {
            if (node.getBean() instanceof ChildrenBeanVo) {
                ChildrenBeanVo vo = (ChildrenBeanVo) node.getBean();
                DoLogProgreeSqliteVo look = mProgressSqliteHelp.findLookWithTidChapterId(vo.get_id(), mCoursdId,
                        DataMessageVo.CHAPTER_ONE);
                if (look != null && !StringUtil.isEmpty(look.getNumber())) {
                    String number = look.getNumber();
                    int a = Integer.parseInt(number);
                    viewHolder.mTvLook.setText(String.valueOf(a));
                } else {
                    viewHolder.mTvLook.setText(String.valueOf(vo.getRnum()));
                }
                viewHolder.mIcon.setVisibility(View.GONE);
                paint.setFakeBoldText(false);
                viewHolder.mIvTreeMark.setVisibility(View.INVISIBLE);
                viewHolder.mTvBankQuestionNumber.setVisibility(View.INVISIBLE);
                viewHolder.mLiLook.setVisibility(View.VISIBLE);

                viewHolder.mTvCout.setText(String.valueOf(vo.getQnum()));
            } else if (node.getBean() instanceof SkillTextVo.DatasBean) {
                viewHolder.mLiLook.setVisibility(View.GONE);
                viewHolder.mIcon.setVisibility(View.VISIBLE);
                viewHolder.mTvBankQuestionNumber.setVisibility(View.VISIBLE);
                viewHolder.mIvTreeMark.setVisibility(View.VISIBLE);
                SkillTextVo.DatasBean bean = (SkillTextVo.DatasBean) node.getBean();
                StringBuffer buffer = new StringBuffer();
                buffer.append("(共");
                buffer.append(StringUtil.isEmpty(bean.getQuestionNumber()) ? "0" : bean.getQuestionNumber());
                buffer.append("题)");
                viewHolder.mTvBankQuestionNumber.setText(buffer.toString());
            }

//            setdata(node, assist, lookVos, userLookVo, viewHolder);
        } else {
            paint.setFakeBoldText(true);
            viewHolder.mLiLook.setVisibility(View.GONE);
            viewHolder.mIcon.setVisibility(View.VISIBLE);
            viewHolder.mTvBankQuestionNumber.setVisibility(View.VISIBLE);
            viewHolder.mIvTreeMark.setVisibility(View.VISIBLE);
            viewHolder.mIcon.setImageResource(node.getIcon());
            if (node.getBean() instanceof SkillTextVo.DatasBean) {
                SkillTextVo.DatasBean bean = (SkillTextVo.DatasBean) node.getBean();
                StringBuffer buffer = new StringBuffer();
                buffer.append("(共");
                buffer.append(bean.getQuestionNumber());
                buffer.append("题)");
                viewHolder.mTvBankQuestionNumber.setText(buffer.toString());
            }
        }
    }

    private void setdata(Node node, DbHelperAssist assist, List<UserLookVo> lookVos, UserLookVo userLookVo, AtricleTreeAdapter.TreeViewHolder viewHolder) {
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
        AtricleNewTreeAdapter.TreeViewHolder holder = new AtricleNewTreeAdapter.TreeViewHolder(View.inflate(mContext, R.layout.item_atricle_tree, null));
        return holder;
    }

    public class TreeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvTreeMark;
        public TextView mTvAtricleTree;
        public TextView mTvBankQuestionNumber;
        public ImageView mIcon;
        public TextView mTvLook;
        public TextView mTvCout;
        public LinearLayout mLiLook;

        public TreeViewHolder(View itemView) {
            super(itemView);
            this.mIvTreeMark = (ImageView) itemView.findViewById(R.id.iv_tree_mark);
            this.mTvAtricleTree = (TextView) itemView.findViewById(R.id.tv_atricle_tree);
            this.mTvBankQuestionNumber = (TextView) itemView.findViewById(R.id.tv_bankQuestionNumber);
            this.mIcon = (ImageView) itemView.findViewById(R.id.icon);
            this.mTvLook = (TextView) itemView.findViewById(R.id.tv_look);
            this.mTvCout = (TextView) itemView.findViewById(R.id.tv_cout);
            this.mLiLook = (LinearLayout) itemView.findViewById(R.id.li_look);
        }
    }


}
