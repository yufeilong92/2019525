package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.QuesitonExamRaltionSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.12 下午 4:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QuestionExamrelarionSqlitHelp {
    private static volatile QuestionExamrelarionSqlitHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private QuestionExamrelarionSqlitHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static QuestionExamrelarionSqlitHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (QuestionExamrelarionSqlitHelp.class) {
                if (_singleton == null) {
                    _singleton = new QuestionExamrelarionSqlitHelp(context);
                }
            }
        }
        return _singleton;
    }

    private SQLiteDatabase createtable() {
        DatabaseContext context = new DatabaseContext(mContext);
//        UserInfomOpenHelp userInfomOpenHelp = new UserInfomOpenHelp(context);
        UserInfomOpenHelp userInfomOpenHelp = UserInfomOpenHelp.get_Instance(context);
        return userInfomOpenHelp.getWritableDatabase(DataMessageVo.SQLITEPASSWORD);
    }

    private boolean empty() {
        if (mSqLiteDatabase == null)
            return true;
        if (mSqLiteDatabase.isReadOnly())
            return true;
        return false;
    }

    public void addQuesitonRaltionItem(QuesitonExamRaltionSqliteVo vo) {
        if (empty()) return;
        if (quesryExamRaltionVo(vo.getExamid(), vo.getQuestionid())) return;
        ContentValues values = new ContentValues();
        values.put("examid", vo.getExamid());
        values.put("questionid", vo.getQuestionid());
        mSqLiteDatabase.insert(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, values);
    }

    public void addlistQuesitonRaltionItem(ArrayList<QuesitonExamRaltionSqliteVo> vo) {
        if (empty()) return;
//        mSqLiteDatabase.beginTransaction();
        for (int i = 0; i < vo.size(); i++) {
            QuesitonExamRaltionSqliteVo vo1 = vo.get(i);
            if (quesryExamRaltionVo(vo1.getExamid(), vo1.getQuestionid())) continue;
            ContentValues values = new ContentValues();
            values.put("examid", vo1.getExamid());
            values.put("questionid", vo1.getQuestionid());
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, values);
        }
//        mSqLiteDatabase.setTransactionSuccessful();
//        mSqLiteDatabase.endTransaction();
        mSqLiteDatabase.close();
    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    /**
     * 根据真题id 查询相应题号id
     * @param examid
     * @return
     */
    public List<QuesitonExamRaltionSqliteVo> queryExamRaltionVo(String examid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, "examid=?",
                new String[]{examid}, null, null, null);
        mDbQueryUtil.initCursor(query);
        List<QuesitonExamRaltionSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuesitonExamRaltionSqliteVo vo = queryData(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;
    }



    public List<QuesitonExamRaltionSqliteVo> findAllExamRaltion() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, null, null, null,
                null, null);
        List<QuesitonExamRaltionSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuesitonExamRaltionSqliteVo vo = new QuesitonExamRaltionSqliteVo();
            int examid = mDbQueryUtil.queryInt("examid");
            int id = mDbQueryUtil.queryInt("id");
            int questionid = mDbQueryUtil.queryInt("questionid");
            vo.setExamid(examid);
            vo.setQuestionid(questionid);
            vo.setId(id);
            list.add(vo);
        }
        query.close();
        return list;
    }

    public boolean quesryExamRaltionVo(int examid, int questionid) {
        if (empty()) return false;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, "examid=? and questionid=?",
                new String[]{String.valueOf(examid), String.valueOf(questionid)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            return true;
        }
        return false;
    }

    public synchronized static QuesitonExamRaltionSqliteVo queryData(DbQueryUtil mQueryUtil, boolean b) {
        QuesitonExamRaltionSqliteVo values = new QuesitonExamRaltionSqliteVo();
        int examid = mQueryUtil.queryInt("examid");
        int questionid = mQueryUtil.queryInt("questionid");
        if (b) {
            int id = mQueryUtil.queryInt("id");
            values.setId(id);
        }
        values.setExamid(examid);
        values.setQuestionid(questionid);
        return values;
    }
}
