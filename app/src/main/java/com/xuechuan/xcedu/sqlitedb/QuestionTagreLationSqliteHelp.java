package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionTagRelationSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 问题
 * @author: L-BackPacker
 * @date: 2018.12.11 下午 2:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QuestionTagreLationSqliteHelp {
    private static volatile QuestionTagreLationSqliteHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private QuestionTagreLationSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static QuestionTagreLationSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (QuestionTagreLationSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new QuestionTagreLationSqliteHelp(context);
                }
            }
        }
        return _singleton;
    }

    private SQLiteDatabase createtable() {
        DatabaseContext context = new DatabaseContext(mContext);
//        UserInfomOpenHelp userInfomOpenHelp =new UserInfomOpenHelp(context);
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

    public void addQuestionTagerlationItem(QuestionTagRelationSqliteVo sqliteVo) {
        if (empty()) return;
/*        String sql = "insert into " + DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION +
                "(questionid ,tagid) values (" +
                sqliteVo.getQuestionid() + "," + sqliteVo.getTagid() + ");";
        mSqLiteDatabase.execSQL(sql);*/
        try {
//            mSqLiteDatabase.beginTransaction();
            if (quesryIsAdd(sqliteVo.getQuestionid(), sqliteVo.getTagid())) return;
            ContentValues values = setValues(sqliteVo);
            mSqLiteDatabase.insert(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null, values);
//            mSqLiteDatabase.setTransactionSuccessful();
//            mSqLiteDatabase.endTransaction();
        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
            e.printStackTrace();
        }


    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    /**
     * 根据题id 查询相应的tagid
     *
     * @param questionid
     * @return
     */
    public QuestionTagRelationSqliteVo queryTagRelation(int questionid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null, "questionid=?",
                new String[]{String.valueOf(questionid)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionTagRelationSqliteVo vo = getQuestionTagRelationSqliteVo(mDbQueryUtil, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 根据题id 查询相应的tagid
     *
     * @param mData
     * @return
     */
    public List<QuestionTagRelationSqliteVo> queryTagRelation(List<ErrorSqliteVo> mData) {
        ArrayList<QuestionTagRelationSqliteVo> list = new ArrayList<>();
        if (empty()) return list;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            ErrorSqliteVo sqliteVo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(sqliteVo.getQuesitonid());
            } else {
                buffer.append(sqliteVo.getQuesitonid());
                buffer.append(",");
            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION,
                null, "questionid in("+buffer.toString()+")",
                null, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionTagRelationSqliteVo vo = getQuestionTagRelationSqliteVo(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;
    }
    /**
     * 根据题id 查询相应的tagid
     *
     * @param mData
     * @return
     */
    public List<QuestionTagRelationSqliteVo> queryTagRelationOne(List<CollectSqliteVo> mData) {
        ArrayList<QuestionTagRelationSqliteVo> list = new ArrayList<>();
        if (empty()) return list;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            CollectSqliteVo sqliteVo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(sqliteVo.getQuestion_id());
            } else {
                buffer.append(sqliteVo.getQuestion_id());
                buffer.append(",");
            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION,
                null, "questionid in("+buffer.toString()+")",
                null, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionTagRelationSqliteVo vo = getQuestionTagRelationSqliteVo(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;
    }
    /**
     * 根据tagid 查询对应的所有题id
     *
     * @param tag
     * @return
     */
    public List<QuestionTagRelationSqliteVo> queryTagRalationWithTag(String tag) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null, "tagid=?",
                new String[]{String.valueOf(tag)}, null, null, null);
        List<QuestionTagRelationSqliteVo> list = new ArrayList<>();
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionTagRelationSqliteVo vo = getQuestionTagRelationSqliteVo(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;

    }

    /**
     * 批量添加
     *
     * @param list
     */
    public void addListQuestionData(List<QuestionTagRelationSqliteVo> list) {
        if (empty()) return;
        try {
//            mSqLiteDatabase.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                QuestionTagRelationSqliteVo sqliteVo = list.get(i);
                if (quesryIsAdd(sqliteVo.getQuestionid(), sqliteVo.getTagid())) continue;
                ContentValues values = setValues(sqliteVo);
                mSqLiteDatabase.insert(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null, values);
            }
//            mSqLiteDatabase.setTransactionSuccessful();
//            mSqLiteDatabase.endTransaction();
            mSqLiteDatabase.close();

        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
            e.printStackTrace();
        }

    }

    private boolean quesryIsAdd(int questionid, int tagid) {
        if (empty()) return false;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null,
                "questionid=? and tagid=?", new String[]{String.valueOf(questionid), String.valueOf(tagid)},
                null, null, null);
        if (query.moveToNext()) {
            return true;
        }
        return false;
    }

    public static ContentValues setValues(QuestionTagRelationSqliteVo sqliteVo) {
        ContentValues values = new ContentValues();
        values.put("questionid", sqliteVo.getQuestionid());
        values.put("tagid", sqliteVo.getTagid());
        return values;
    }

    public void UpdataQuestionTagerRaltionItem(QuestionTagRelationSqliteVo sqliteVo) {
        if (empty()) return;
        String sql = "update " + DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION
                + " set questionid =" + sqliteVo.getQuestionid() + "," +
                "tagid=" + sqliteVo.getTagid() + " where id=" + sqliteVo.getId();
        mSqLiteDatabase.execSQL(sql);
    }

    public void deleteQuesitonRaltion() {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION,
                "", new String[]{});

    }


    public static QuestionTagRelationSqliteVo getQuestionTagRelationSqliteVo(DbQueryUtil mDbQueryUtil, boolean b) {
        QuestionTagRelationSqliteVo sqliteVo = new QuestionTagRelationSqliteVo();
        if (b) {
            int id = mDbQueryUtil.queryInt("id");
            sqliteVo.setId(id);
        }
        int questionid = mDbQueryUtil.queryInt("questionid");
        int tagid = mDbQueryUtil.queryInt("tagid");
        sqliteVo.setQuestionid(questionid);
        sqliteVo.setTagid(tagid);
        return sqliteVo;
    }
}
