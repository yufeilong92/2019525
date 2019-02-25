package com.xuechuan.xcedu.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.sqlitedb.QuestionChapterSqliteHelp;
import com.xuechuan.xcedu.utils.TimeTools;
import com.xuechuan.xcedu.vo.ExamHistoryVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionChapterSqliteVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.me
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.09 下午 2:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ExamHistoryAdapter extends BaseRecyclerAdapter<ExamHistoryAdapter.HistoryViewHolder> {

    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;
    private QuestionChapterSqliteHelp mHelp;

    public ExamHistoryAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public void doChapterHelp(QuestionChapterSqliteHelp help) {
        this.mHelp = help;
        notifyDataSetChanged();
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        /**
         * @param o
         * @param type 1为重做，2为删除，3为解析
         */
        public void onClickItem(ExamHistoryVo.DatasBean o, int type, int postion, int courseid);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public HistoryViewHolder getViewHolder(View view) {
        return new HistoryViewHolder(view);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {

        return new HistoryViewHolder(mInflater.inflate(R.layout.item_history_item, null));
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position, boolean isItem) {
        ExamHistoryVo.DatasBean vo = (ExamHistoryVo.DatasBean) mListDatas.get(position);
        QuestionChapterSqliteVo sqliteVo = mHelp.queryCharpterWithChapterid(vo.getChapterid());
        String name = "无";
        int coureid = 0;
        if (sqliteVo != null) {
            name = sqliteVo.getChaptername();
            coureid= sqliteVo.getCourseid();
        }
        holder.mTvExamHistoryTitle.setText(name);
        holder.mTvExamHistoryNum.setText(String.valueOf(vo.getScore()));
        holder.mTvExamHistoryTime.setText(TimeTools.getCountTimeByLongOne(vo.getUsetime()));
        btnOnClicKEvent(vo, holder.mRlExamHistroyAgain, 1, position, coureid);
        btnOnClicKEvent(vo, holder.mRlExamHistroyDelete, 2, position,coureid);
        btnOnClicKEvent(vo, holder.mRlExamHistroyJiexi, 3, position,coureid);
    }

    private void btnOnClicKEvent(final ExamHistoryVo.DatasBean vo, RelativeLayout rl, final int type, final int postion, final int courseid) {
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(vo, type, postion,courseid);
                }
            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return mListDatas.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvExamHistoryTitle;
        public TextView mTvExamHistoryTime;
        public TextView mTvExamHistoryNum;
        public RelativeLayout mRlExamHistroyAgain;
        public RelativeLayout mRlExamHistroyDelete;
        public RelativeLayout mRlExamHistroyJiexi;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            this.mTvExamHistoryTitle = (TextView) itemView.findViewById(R.id.tv_exam_history_title);
            this.mTvExamHistoryTime = (TextView) itemView.findViewById(R.id.tv_exam_history_time);
            this.mTvExamHistoryNum = (TextView) itemView.findViewById(R.id.tv_exam_history_num);
            this.mRlExamHistroyAgain = (RelativeLayout) itemView.findViewById(R.id.rl_exam_histroy_again);
            this.mRlExamHistroyDelete = (RelativeLayout) itemView.findViewById(R.id.rl_exam_histroy_delete);
            this.mRlExamHistroyJiexi = (RelativeLayout) itemView.findViewById(R.id.rl_exam_histroy_jiexi);
        }
    }


}
