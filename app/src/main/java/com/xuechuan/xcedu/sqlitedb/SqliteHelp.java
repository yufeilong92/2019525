package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.EncryptionUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.vo.SqliteVo.DeleteSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuesitonExamRaltionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionChapterSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionTagRelationSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.TagSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.10 上午 10:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SqliteHelp {
    private static volatile SqliteHelp _singleton;
    private Context mContext;
    private final DbQueryUtil mQueryUtil;

    private SqliteHelp(Context context) {
        this.mContext = context;
        mQueryUtil = DbQueryUtil.get_Instance();
    }

    public static SqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (SqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new SqliteHelp(context);
                }
            }
        }
        return _singleton;
    }

    /**
     * 获取想要打开本地的数据库
     *
     * @param path
     * @param dbNmae
     * @return
     */
    public SQLiteDatabase acquireSqliteDb(String path, String dbNmae) {
        String concat = path.concat(dbNmae);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(new File(concat), null,null);
        return database;
    }

    /**
     * 获取想要打开本地的数据库
     *
     * @param path
     * @return
     */
    public SQLiteDatabase acquireSqliteDb(String path) {
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(new File(path), null,null);
        return database;
    }

    /**
     * 输入要查询的数据库管理器
     *
     * @param sqliteManger
     */
    public Cursor findUserInfomAll(SQLiteDatabase sqliteManger, String dbName) {
        Cursor query = sqliteManger.query(dbName, null, null, null, null, null, null);
        return query;
    }

    //添加操作
    public void findQuestionSAll(SQLiteDatabase mSqLiteDatabase) {
        List<QuestionSqliteVo> question = findQuestion(mSqLiteDatabase);
        List<DeleteSqliteVo> deleteSqliteVos = finAllQuestioDelete(mSqLiteDatabase);
        List<QuestionChapterSqliteVo> QuestionChapterSqliteVos = finAllQuestionChapter(mSqLiteDatabase);
        List<QuestionTagRelationSqliteVo> allQuestionTagRealtion = findAllQuestionTagRealtion(mSqLiteDatabase);
        List<TagSqliteVo> tagAndTagAll = findTagAndTagAll(mSqLiteDatabase);
        List<QuesitonExamRaltionSqliteVo> questionExamRealtion = findQuestionExamRealtion(mSqLiteDatabase);
        mSqLiteDatabase.close();
        DatabaseContext context = new DatabaseContext(mContext);
        UserInfomOpenHelp help = new UserInfomOpenHelp(context);
        SQLiteDatabase mDb = help.getWritableDatabase(DataMessageVo.SQLITEPASSWORD);
        mDb.beginTransaction();
        long l = System.currentTimeMillis();
        L.e("开始插入时间=" + l);
        //添加问题章节
        try {
            if (question != null && !question.isEmpty()) {
                ArrayList<QuestionSqliteVo> list = new ArrayList<>();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < question.size(); i++) {
                    QuestionSqliteVo questionSqliteVo = question.get(i);
                    String d = EncryptionUtil.D(questionSqliteVo.getQuestion());
                    questionSqliteVo.setQuestionString(d);
                    String key = EncryptionUtil.D(questionSqliteVo.getKeywords());
                    questionSqliteVo.setKeywordsString(key);
                    String explain = EncryptionUtil.D(questionSqliteVo.getExplained());
                    questionSqliteVo.setExplainString(explain);
                    list.add(questionSqliteVo);
                    if (i == (question.size() - 1)) {
                        buffer.append(questionSqliteVo.getQuestion_id());
                    } else {
                        buffer.append(questionSqliteVo.getQuestion_id());
                        buffer.append(",");
                    }
                }
                mDb.delete(DataMessageVo.USER_QUESTION_TABLE_QUESTION, "question_id in (" + buffer.toString() + ")", null);
                for (int i = 0; i < list.size(); i++) {
                    QuestionSqliteVo vo = list.get(i);
                    ContentValues values = QuestionSqliteHelp.setValues(vo);
                    mDb.insert(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, values);
                }
                buffer = null;
            }

            if (deleteSqliteVos != null && !deleteSqliteVos.isEmpty()) {
                mDb.delete(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, null);
                for (int i = 0; i < deleteSqliteVos.size(); i++) {
                    DeleteSqliteVo vo = deleteSqliteVos.get(i);
                    ContentValues values = DeleteSqliteHelp.setContentValues(vo);
                    mDb.insert(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, values);
                }
            }
            if (QuestionChapterSqliteVos != null && !QuestionChapterSqliteVos.isEmpty()) {
                mDb.delete(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, null);
                for (int i = 0; i < QuestionChapterSqliteVos.size(); i++) {
                    QuestionChapterSqliteVo vo = QuestionChapterSqliteVos.get(i);
                    ContentValues values = QuestionChapterSqliteHelp.setValues(vo);
                    mDb.insert(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, values);
                }
            }
            if (allQuestionTagRealtion != null && !allQuestionTagRealtion.isEmpty()) {
                mDb.delete(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null, null);
                for (int i = 0; i < allQuestionTagRealtion.size(); i++) {
                    QuestionTagRelationSqliteVo vo = allQuestionTagRealtion.get(i);
                    ContentValues values = QuestionTagreLationSqliteHelp.setValues(vo);
                    mDb.insert(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null, values);

                }
            }
            if (tagAndTagAll != null && !tagAndTagAll.isEmpty()) {
                mDb.delete(DataMessageVo.USER_QUESTIONTABLE_TAG, null, null);
                for (int i = 0; i < tagAndTagAll.size(); i++) {
                    TagSqliteVo vo = tagAndTagAll.get(i);
                    ContentValues values = TagSqliteHelp.setContentValues(vo);
                    mDb.insert(DataMessageVo.USER_QUESTIONTABLE_TAG, null, values);
                }
            }
            if (questionExamRealtion != null && !questionExamRealtion.isEmpty()) {
                mDb.delete(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, null);
                for (int i = 0; i < questionExamRealtion.size(); i++) {
                    QuesitonExamRaltionSqliteVo vo = questionExamRealtion.get(i);
                    ContentValues values = new ContentValues();
                    values.put("examid", vo.getExamid());
                    values.put("questionid", vo.getQuestionid());
                    mDb.insert(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, values);
                }
            }
            if (deleteSqliteVos != null && !deleteSqliteVos.isEmpty()) {
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < deleteSqliteVos.size(); i++) {
                    DeleteSqliteVo vo = deleteSqliteVos.get(i);
                    if (i == (deleteSqliteVos.size() - 1)) {
                        buffer.append(vo.getDelect_id());
                    } else {
                        buffer.append(vo.getDelect_id());
                        buffer.append(",");
                    }
                }
                mDb.delete(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, "questionid in(" + buffer.toString() + ")",
                        null);
                //问题标签对应关系表
                mDb.delete(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, "questionid  in(" + buffer.toString() + ")"
                        , null);
                //问题详情表
                mDb.delete(DataMessageVo.USER_QUESTION_TABLE_QUESTION, "question_id in(" + buffer.toString() + ")",
                        null);
                buffer = null;
            }
            mDb.setTransactionSuccessful();
            mDb.endTransaction();
            long l1 = System.currentTimeMillis();
            L.e("结束日插入时间=" + l1);
            L.e("时差" + (l1 - l));
        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
        } finally {
            question=null;
            deleteSqliteVos=null;
            QuestionChapterSqliteVos=null;
            allQuestionTagRealtion=null;
            tagAndTagAll=null;
            questionExamRealtion=null;
            mDb.close();
        }

    }

    //    覆盖操作
    public void findTWOQuestionSAll(SQLiteDatabase mSqLiteDatabase) {
        List<QuestionSqliteVo> question = findQuestion(mSqLiteDatabase);
        List<DeleteSqliteVo> deleteSqliteVos = finAllQuestioDelete(mSqLiteDatabase);
        List<QuestionChapterSqliteVo> QuestionChapterSqliteVos = finAllQuestionChapter(mSqLiteDatabase);
        List<QuestionTagRelationSqliteVo> allQuestionTagRealtion = findAllQuestionTagRealtion(mSqLiteDatabase);
        List<TagSqliteVo> tagAndTagAll = findTagAndTagAll(mSqLiteDatabase);
        List<QuesitonExamRaltionSqliteVo> questionExamRealtion = findQuestionExamRealtion(mSqLiteDatabase);
        mSqLiteDatabase.close();
        DatabaseContext context = new DatabaseContext(mContext);
        UserInfomOpenHelp help = new UserInfomOpenHelp(context);
        SQLiteDatabase mDb = help.getWritableDatabase(DataMessageVo.SQLITEPASSWORD);
        mDb.beginTransaction();
        long l = System.currentTimeMillis();
        L.e("开始插入时间=" + l);
        //添加问题章节
        try {
            if (question != null && !question.isEmpty()) {
                ArrayList<QuestionSqliteVo> list = new ArrayList<>();
                for (int i = 0; i < question.size(); i++) {
                    QuestionSqliteVo questionSqliteVo = question.get(i);
                    String d = EncryptionUtil.D(questionSqliteVo.getQuestion());
                    questionSqliteVo.setQuestionString(d);
                    String key = EncryptionUtil.D(questionSqliteVo.getKeywords());
                    questionSqliteVo.setKeywordsString(key);
                    String explain = EncryptionUtil.D(questionSqliteVo.getExplained());
                    questionSqliteVo.setExplainString(explain);
                    list.add(questionSqliteVo);
                }

                mDb.delete(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, null);
                for (int i = 0; i < list.size(); i++) {
                    QuestionSqliteVo vo = list.get(i);
                    ContentValues values = QuestionSqliteHelp.setValues(vo);
                    mDb.insert(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, values);
                }
            }
            if (deleteSqliteVos != null && !deleteSqliteVos.isEmpty()) {
                mDb.delete(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, null);
                for (int i = 0; i < deleteSqliteVos.size(); i++) {
                    DeleteSqliteVo vo = deleteSqliteVos.get(i);
                    ContentValues values = DeleteSqliteHelp.setContentValues(vo);
                    mDb.insert(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, values);
                }
            }
            if (QuestionChapterSqliteVos != null && !QuestionChapterSqliteVos.isEmpty()) {
                mDb.delete(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, null);
                for (int i = 0; i < QuestionChapterSqliteVos.size(); i++) {
                    QuestionChapterSqliteVo vo = QuestionChapterSqliteVos.get(i);
                    ContentValues values = QuestionChapterSqliteHelp.setValues(vo);
                    mDb.insert(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, values);
                }
            }
            if (allQuestionTagRealtion != null && !allQuestionTagRealtion.isEmpty()) {
                mDb.delete(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null, null);
                for (int i = 0; i < allQuestionTagRealtion.size(); i++) {
                    QuestionTagRelationSqliteVo vo = allQuestionTagRealtion.get(i);
                    ContentValues values = QuestionTagreLationSqliteHelp.setValues(vo);
                    mDb.insert(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null, values);
                }
            }
            if (tagAndTagAll != null && !tagAndTagAll.isEmpty()) {
                mDb.delete(DataMessageVo.USER_QUESTIONTABLE_TAG, null, null);
                for (int i = 0; i < tagAndTagAll.size(); i++) {
                    TagSqliteVo vo = tagAndTagAll.get(i);
                    ContentValues values = TagSqliteHelp.setContentValues(vo);
                    mDb.insert(DataMessageVo.USER_QUESTIONTABLE_TAG, null, values);
                }
            }
            if (questionExamRealtion != null && !questionExamRealtion.isEmpty()) {
                mDb.delete(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, null);
                for (int i = 0; i < questionExamRealtion.size(); i++) {
                    QuesitonExamRaltionSqliteVo vo = questionExamRealtion.get(i);
                    ContentValues values = new ContentValues();
                    values.put("examid", vo.getExamid());
                    values.put("questionid", vo.getQuestionid());
                    mDb.insert(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, values);

                }
            }
            if (deleteSqliteVos != null && !deleteSqliteVos.isEmpty()) {
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < deleteSqliteVos.size(); i++) {
                    DeleteSqliteVo vo = deleteSqliteVos.get(i);
                    if (i == (deleteSqliteVos.size() - 1)) {
                        buffer.append(vo.getDelect_id());
                    } else {
                        buffer.append(vo.getDelect_id());
                        buffer.append(",");
                    }
                }
                //考试表
                mDb.delete(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, "questionid in(" + buffer.toString() + ")",
                        null);
                //问题标签对应关系表
                mDb.delete(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, "questionid in(" + buffer.toString() + ")"
                        , null);
                //问题详情表
                mDb.delete(DataMessageVo.USER_QUESTION_TABLE_QUESTION, "question_id in (" + buffer.toString() + ")",
                        null);
            }
            mDb.setTransactionSuccessful();
            mDb.endTransaction();
            long l1 = System.currentTimeMillis();
            L.e("结束日插入时间=" + l1);
            L.e("时差" + (l1 - l));
        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
        } finally {
            question=null;
            deleteSqliteVos=null;
            QuestionChapterSqliteVos=null;
            allQuestionTagRealtion=null;
            tagAndTagAll=null;
            questionExamRealtion=null;
            mDb.close();
        }


    }

    private boolean quesryExamRaltionVo(SQLiteDatabase mDb, int examid, int questionid) {
        Cursor query = mDb.query(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, "examid=? and questionid=?",
                new String[]{String.valueOf(examid), String.valueOf(questionid)}, null, null, null);
        while (query.moveToNext()) {
            query.close();
            return true;
        }
        query.close();
        return false;
    }

    private QuesitonExamRaltionSqliteVo quesryExamOverRaltionVo(SQLiteDatabase mDb, int examid, int questionid, DbQueryUtil dbQueryUtil) {
        Cursor query = mDb.query(DataMessageVo.USER_QUESTIONTABLE_EXAMR_ELATION, null, "examid=? and questionid=?",
                new String[]{String.valueOf(examid), String.valueOf(questionid)}, null, null, null);
        dbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuesitonExamRaltionSqliteVo vo = QuestionExamrelarionSqlitHelp.queryData(dbQueryUtil, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 查询tag biao
     *
     * @param id
     * @return
     */
    private boolean quesrytagIsAdd(SQLiteDatabase mdb, int id) {

        Cursor query = mdb.query(DataMessageVo.USER_QUESTIONTABLE_TAG, null, "tagid=?",
                new String[]{String.valueOf(id)}, null, null, null);
        while (query.moveToNext()) {
            query.close();
            return true;
        }
        query.close();
        return false;
    }

    /**
     * 查询tag biao
     *
     * @param id
     * @param dbQueryUtil
     * @return
     */
    private TagSqliteVo quesrytagOverIsAdd(SQLiteDatabase mdb, int id, DbQueryUtil dbQueryUtil) {
        Cursor query = mdb.query(DataMessageVo.USER_QUESTIONTABLE_TAG, null, "tagid=?",
                new String[]{String.valueOf(id)}, null, null, null);
        dbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            TagSqliteVo vo = TagSqliteHelp.getTagSqliteVo(dbQueryUtil, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 查询是问题否添加
     *
     * @return
     */
    public boolean queryQuestionIsAdd(SQLiteDatabase database, int questionid) {
        Cursor query = database.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, "question_id=?",
                new String[]{String.valueOf(questionid)}, null, null, null);
        while (query.moveToNext()) {
            query.close();
            return true;
        }
        query.close();
        return false;

    }

    /**
     * 查询是问题否添加
     *
     * @return
     */
    public QuestionSqliteVo queryQuestionIsOverAdd(SQLiteDatabase database, int questionid, DbQueryUtil dbQueryUtil) {
        Cursor query = database.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, "question_id=?",
                new String[]{String.valueOf(questionid)}, null, null, null);
        dbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionSqliteVo sqlite = QuestionSqliteHelp.getQuesitonSqlite(dbQueryUtil, true);
            query.close();
            return sqlite;

        }
        query.close();
        return null;
    }

    /**
     * 查询用户数据
     *
     * @param questionid
     * @param tagid
     * @return
     */
    private boolean quesryIsAdd(SQLiteDatabase database, int questionid, int tagid) {
        Cursor query = database.query(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null,
                "questionid=? and tagid=?", new String[]{String.valueOf(questionid), String.valueOf(tagid)},
                null, null, null);
        while (query.moveToNext()) {
            query.close();
            return true;
        }
        query.close();
        return false;
    }

    /**
     * 查询用户数据
     *
     * @param questionid
     * @param tagid
     * @param dbQueryUtil
     * @return
     */
    private QuestionTagRelationSqliteVo quesryIsTagOverAdd(SQLiteDatabase database, int questionid, int tagid, DbQueryUtil dbQueryUtil) {
        Cursor query = database.query(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null,
                "questionid=? and tagid=?", new String[]{String.valueOf(questionid), String.valueOf(tagid)},
                null, null, null);
        dbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionTagRelationSqliteVo vo = QuestionTagreLationSqliteHelp.getQuestionTagRelationSqliteVo(dbQueryUtil, true);

            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 查询用户数据
     *
     * @param questionid
     * @param tagid
     * @return
     */
    private QuestionTagRelationSqliteVo quesryIsOverAdd(SQLiteDatabase database, int questionid, int tagid) {
        DbQueryUtil util = DbQueryUtil.get_Instance();
        Cursor query = database.query(DataMessageVo.USER_INFOM_TABLE_QUESTION_TAGRELATION, null,
                "questionid=? and tagid=?", new String[]{String.valueOf(questionid), String.valueOf(tagid)},
                null, null, null);
        util.initCursor(query);
        while (query.moveToNext()) {
            QuestionTagRelationSqliteVo vo = QuestionTagreLationSqliteHelp.getQuestionTagRelationSqliteVo(util, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 查询用户删除数据
     *
     * @param type
     * @param delete_id
     * @return
     */
    public boolean queryDeleteVo(SQLiteDatabase mdb, String type, int delete_id) {
        Cursor query = mdb.query(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, "delete_id=? and type=?",
                new String[]{String.valueOf(delete_id), type}, null, null, null);
        while (query.moveToNext()) {
            query.close();
            return true;
        }
        query.close();
        return false;
    }

    /**
     * 查询是章节否添加
     *
     * @param mDb
     * @return
     */
    public boolean queryChapterVo(SQLiteDatabase mDb, int questionid) {
        Cursor query = mDb.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, "questionchapter_id=?",
                new String[]{String.valueOf(questionid)}, null, null, null);
        while (query.moveToNext()) {
            query.close();
            return true;
        }
        query.close();
        return false;
    }

    /**
     * 查询是章节否添加
     *
     * @param mDb
     * @param dbQueryUtil
     * @return
     */
    public QuestionChapterSqliteVo queryChapterOverVo(SQLiteDatabase mDb, int questionid, DbQueryUtil dbQueryUtil) {
        Cursor query = mDb.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, "questionchapter_id=?",
                new String[]{String.valueOf(questionid)}, null, null, null);
        dbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionChapterSqliteVo vo = QuestionChapterSqliteHelp.getQuestionChapterSqliteVo(dbQueryUtil, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    private List<QuesitonExamRaltionSqliteVo> findQuestionExamRealtion(SQLiteDatabase mSqLiteDatabase) {
        Cursor query = mSqLiteDatabase.query(DataMessageVo.t_questionexamrelation, null, null, null, null, null, null);
        DbQueryUtil instance = DbQueryUtil.get_Instance();
        instance.initCursor(query);
        List<QuesitonExamRaltionSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuesitonExamRaltionSqliteVo vo = queryQuesitonExamRaltionData(instance);
            if (vo != null)
                list.add(vo);
        }
        query.close();
        return list;
    }

    private List<QuestionTagRelationSqliteVo> findAllQuestionTagRealtion(SQLiteDatabase mSqLiteDatabase) {
        Cursor query = mSqLiteDatabase.query(DataMessageVo.t_questiontagrelation, null, null, null, null, null, null);
        DbQueryUtil instance = DbQueryUtil.get_Instance();
        instance.initCursor(query);
        List<QuestionTagRelationSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionTagRelationSqliteVo vo = QuestionTagreLationSqliteHelp.getQuestionTagRelationSqliteVo(instance, false);
            if (vo != null)
                list.add(vo);
        }
        query.close();
        return list;
    }

    private List<TagSqliteVo> findTagAndTagAll(SQLiteDatabase mSqLiteDatabase) {
        Cursor query = mSqLiteDatabase.query(DataMessageVo.t_tag, null, null, null, null, null, null);
        DbQueryUtil instance = DbQueryUtil.get_Instance();
        instance.initCursor(query);
        List<TagSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            TagSqliteVo vo = TagSqliteHelp.getTagSqliteVo(instance, false);
            if (vo != null)
                list.add(vo);
        }
        query.close();
        return list;
    }

    private List<QuestionChapterSqliteVo> finAllQuestionChapter(SQLiteDatabase mSqLiteDatabase) {
        Cursor query = mSqLiteDatabase.query(DataMessageVo.t_questionchapter, null, null, null, null, null, null);
        DbQueryUtil instance = DbQueryUtil.get_Instance();
        instance.initCursor(query);
        List<QuestionChapterSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionChapterSqliteVo sqliteVo = QuestionChapterSqliteHelp.getQuestionChapterSqliteVo(instance, false);
            if (sqliteVo != null) {
                list.add(sqliteVo);
            }
        }
        query.close();
        return list;
    }

    private List<DeleteSqliteVo> finAllQuestioDelete(SQLiteDatabase mSqLiteDatabase) {
        Cursor query = mSqLiteDatabase.query(DataMessageVo.t_delete, null, null, null, null, null, null);
        DbQueryUtil instance = DbQueryUtil.get_Instance();
        instance.initCursor(query);
        List<DeleteSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            DeleteSqliteVo vo = DeleteSqliteHelp.getDeleteValue(instance, false);
            if (vo != null) {
                list.add(vo);
            }
        }
        query.close();
        return list;
    }

    private List<QuestionSqliteVo> findQuestion(SQLiteDatabase mSqLiteDatabase) {
        Cursor query = mSqLiteDatabase.query(DataMessageVo.t_question, null, null, null, null, null, null);
        DbQueryUtil instance = DbQueryUtil.get_Instance();
        instance.initCursor(query);
        final List<QuestionSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            final QuestionSqliteVo sqliteVo = QuestionSqliteHelp.getQuesitonSqlite(instance, false);
            if (sqliteVo != null) {
                list.add(sqliteVo);
            }
        }
        query.close();
        return list;
    }

    private QuesitonExamRaltionSqliteVo queryQuesitonExamRaltionData(DbQueryUtil mQueryUtil) {
        QuesitonExamRaltionSqliteVo values = new QuesitonExamRaltionSqliteVo();
        int examid = mQueryUtil.queryInt("examid");
        int questionid = mQueryUtil.queryInt("questionid");
        values.setExamid(examid);
        values.setQuestionid(questionid);
        return values;
    }

}
