package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.ErrorCollectVo;
import com.xuechuan.xcedu.vo.ErrorOrCollectItemVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuesitonExamRaltionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionTagRelationSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.11 上午 11:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QuestionSqliteHelp {
    private static volatile QuestionSqliteHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private QuestionSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static QuestionSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (QuestionSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new QuestionSqliteHelp(context);
                }
            }
        }
        return _singleton;
    }

/*
    public void initOpenUserInfom() {
        DbPathUitl instance = DbPathUitl.get_Instance(mContext);
        String dbPath = instance.getDbPath();
        if (StringUtil.isEmpty(dbPath)) return;
        String concat = dbPath.concat(DataMessageVo.USER_QUESTION_TABLE_QUESTION);
        File file = new File(concat);
        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file, null);
        if (mSqLiteDatabase == null) {
            createtable();
        }
    }
*/

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


    /**
     * 返回搜索得题干集合
     *
     * @param ChapterId 章节io
     * @param Couersid  科目id
     * @return
     */
    public List<QuestionSqliteVo> getChapterQuestionListData(int ChapterId, String Couersid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                "chapter_id= ? and courseid= ?", new String[]{String.valueOf(ChapterId), Couersid},
                null, null, "sort desc,question_id asc");
        List<QuestionSqliteVo> list = new ArrayList<>();
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionSqliteVo vo = getQuesitonSqlite(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;
    }


    /**
     * 返回搜索得题干集合
     *
     * @param ChapterId 章节io
     * @param Couersid  科目id
     * @return
     */
    public List<QuestionCaseVo> queryAllChapterQuestionListData(int ChapterId, String Couersid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"}, "chapter_id= ? and courseid= ?",
                new String[]{String.valueOf(ChapterId), Couersid},
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        List<QuestionCaseVo> mList = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            mList.add(vo);
        }
        query.close();
        return mList;
    }

    /**
     * 返回搜索得题干集合
     *
     * @return
     */
    public List<QuestionCaseVo> queryAllListChildData(int questionid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"}, "parent_id=?",
                new String[]{String.valueOf(questionid)},
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        List<QuestionCaseVo> mList = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            mList.add(vo);
        }
        query.close();
        return mList;
    }


    /**
     * 返回搜索得题干集合
     *
     * @param questionid 问题id
     * @param Couersid   科目id
     * @return
     */
    public QuestionCaseVo queryAllQuestionQuestionListData(int questionid, String Couersid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"}, "question_id= ? and courseid= ?",
                new String[]{String.valueOf(questionid), Couersid},
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 返回搜索得题干集合
     *
     * @return
     */
    public List<QuestionCaseVo> queryAllQuestionQuestionListData(List<ErrorCollectVo> mData, String courseid) {
        ArrayList<QuestionCaseVo> list = new ArrayList<>();
        if (empty()) return list;
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            ErrorCollectVo vo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(vo.getQuestionid());
                buffer1.append(courseid);
            } else {
                buffer.append(vo.getQuestionid());
                buffer1.append(courseid);
                buffer.append(",");
                buffer1.append(",");
            }


        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"}
                , "question_id in(" + buffer.toString() + ") " +
                        "and courseid in(" + buffer1.toString() + ") ",
                null,
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;
    }

    /**
     * 返回搜索得题干集合
     *
     * @param paranid  父id
     * @param Couersid 科目id
     * @return
     */
    public List<QuestionCaseVo> queryAllQuestionQuestWithParaintId(int paranid, String Couersid) {
        List<QuestionCaseVo> vos = new ArrayList<>();
        if (empty()) return vos;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"}, "parent_id= ? and courseid= ?",
                new String[]{String.valueOf(paranid), Couersid},
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            vos.add(vo);
        }
        query.close();
        return vos;
    }

    public ArrayList<QuestionSqliteVo> queryCalendarYearLists() {
        return null;
    }


    /**
     * 添加数据
     */
    public synchronized void addQuestionItem(QuestionSqliteVo vo) {
        if (vo == null) return;
        if (empty()) return;
        try {
//            mSqLiteDatabase.beginTransaction();
            if (queryIsAdd(vo.getQuestion_id())) return;
            ContentValues values = setValues(vo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, values);
//            mSqLiteDatabase.setTransactionSuccessful();
//            mSqLiteDatabase.endTransaction();
        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
        }

    }

    /**
     * @param list
     */
    public void addListQuestionData(ArrayList<QuestionSqliteVo> list) {
        if (empty()) return;
        try {
//            mSqLiteDatabase.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                QuestionSqliteVo vo = list.get(i);
                if (queryIsAdd(vo.getQuestion_id())) continue;
                ContentValues values = setValues(vo);
                mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, values);
            }
//            mSqLiteDatabase.setTransactionSuccessful();
//            mSqLiteDatabase.endTransaction();
            mSqLiteDatabase.close();

        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
        }
    }

    /**
     * 查询是否添加
     *
     * @param questionid
     * @return
     */
    public boolean queryIsAdd(int questionid) {
        if (empty()) return false;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, "question_id=?",
                new String[]{String.valueOf(questionid)}, null, null, null);
        while (query.moveToNext()) {
            return true;
        }
        return false;
    }


    /**
     * 删除莫个数据
     *
     * @param paraentid
     */
    public void deleteItemData(int paraentid) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_TABLE_QUESTION, "parent_id=?",
                new String[]{String.valueOf(paraentid)});

    }

    /**
     * 更具题id 获取相应题
     *
     * @param mData
     * @return
     */
    public ArrayList<QuestionSqliteVo> queryQuestionVoWithTags(ArrayList<QuestionTagRelationSqliteVo> mData) {
        if (empty()) return null;
        ArrayList<QuestionSqliteVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            QuestionTagRelationSqliteVo examRaltionSqliteVo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(examRaltionSqliteVo.getQuestionid());
            } else {
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer.append(",");
            }

        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                "question_id in(" + buffer.toString() + ")", null,
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionSqliteVo vo = getQuesitonSqlite(mDbQueryUtil, true);
            list.add(vo);
        }
        buffer = null;
        query.close();
        return list;
    }

    /**
     * 更具题id 获取相应题
     *
     * @param mData
     * @return
     */
    public List<QuestionCaseVo> queryQuestionVoWithTagCase(List<QuestionTagRelationSqliteVo> mData) {
        if (empty()) return null;
        List<QuestionCaseVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            QuestionTagRelationSqliteVo examRaltionSqliteVo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(examRaltionSqliteVo.getQuestionid());
            } else {
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer.append(",");
            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "question_id in (" + buffer.toString() + ")",
                null,
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            list.add(vo);
        }
        buffer = null;
        query.close();
        return list;
    }

    /**
     * 更具题id 获取相应题
     *
     * @param mData
     * @return
     */
    public List<QuestionCaseVo> queryQuestionVoWithTagOneCase(List<QuestionTagRelationSqliteVo> mData) {
        if (empty()) return null;
        List<QuestionCaseVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            QuestionTagRelationSqliteVo examRaltionSqliteVo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer1.append(5);
            } else {
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer.append(",");
                buffer1.append(5);
                buffer1.append(",");
            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "question_id in (" + buffer.toString() + ") and  questiontype in (" + buffer1.toString() + ")",
                null,
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            list.add(vo);
        }
        buffer = null;
        query.close();
        return list;
    }

    /**
     * 更具题id 获取相应题
     *
     * @param mData
     * @return
     */
    public List<QuestionCaseVo> queryQuestionVoWithCollectOneCase(List<ErrorCollectVo> mData) {
        if (empty()) return null;
        List<QuestionCaseVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            ErrorCollectVo vo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(vo.getQuestionid());
                buffer1.append(5);
            } else {
                buffer.append(vo.getQuestionid());
                buffer.append(",");
                buffer1.append(5);
                buffer1.append(",");
            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "question_id in (" + buffer.toString() + ") and  questiontype in (" + buffer1.toString() + ")",
                null,
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            list.add(vo);
        }
        buffer = null;
        query.close();
        return list;
    }

    public List<QuestionCaseVo> queryQuestionChildListVos(List<QuestionCaseVo> mParentLists) {
        List<QuestionCaseVo> mChildList = new ArrayList<>();
        if (empty()) return mChildList;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mParentLists.size(); i++) {
            QuestionCaseVo parentVo = mParentLists.get(i);
            if (i == (mParentLists.size() - 1)) {
                buffer.append(parentVo.getQuestion_id());
            } else {
                buffer.append(parentVo.getQuestion_id());
                buffer.append(",");

            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "parent_id in (" + buffer.toString() + ")",
                null,
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            mChildList.add(vo);
        }
        buffer = null;
        query.close();
        return mChildList;

    }

    public List<QuestionCaseVo> queryQuestionChildListOneVos(List<ErrorCollectVo> mParentLists) {
        List<QuestionCaseVo> mChildList = new ArrayList<>();
        if (empty()) return mChildList;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mParentLists.size(); i++) {
            ErrorCollectVo parentVo = mParentLists.get(i);
            if (i == (mParentLists.size() - 1)) {
                buffer.append(parentVo.getQuestionid());
            } else {
                buffer.append(parentVo.getQuestionid());
                buffer.append(",");

            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "parent_id in (" + buffer.toString() + ")",
                null,
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            mChildList.add(vo);
        }
        buffer = null;
        query.close();
        return mChildList;

    }

    /**
     * 更具题id 获取相应题
     *
     * @param mData
     * @return
     */
    public List<QuestionCaseVo> queryQuestionVoWithTagMainCase(List<QuestionTagRelationSqliteVo> mData) {
        if (empty()) return null;
        List<QuestionCaseVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            QuestionTagRelationSqliteVo examRaltionSqliteVo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer1.append(5);
            } else {
                buffer1.append(5);
                buffer1.append(",");
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer.append(",");
            }

        }
//        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
//                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"}, "question_id=? and questiontype=?",
//                new String[]{String.valueOf(examRaltionSqliteVo.getQuestionid()), String.valueOf(5)},
//                null, null, "sort desc,question_id asc");
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "question_id in(" + buffer.toString() + ") and questiontype in(" + buffer1.toString() + ")",
                null,
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            list.add(vo);
        }
        buffer1 = null;
        buffer = null;
        query.close();
        return list;
    }

    /**
     * 更具题id 获取相应题
     *
     * @param id 题干id
     * @return
     */
    public QuestionCaseVo queryQuestionCaseWithId(int id) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "question_id=?", new String[]{String.valueOf(id)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 更具题id 获取相应题
     *
     * @param id 题干id
     * @return
     */
    public QuestionSqliteVo queryQuestionVoWithId(int id) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                "id=?", new String[]{String.valueOf(id)}, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionSqliteVo vo = getQuesitonSqlite(mDbQueryUtil, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 更具题id 获取相应题
     *
     * @param questionid 题干id
     * @return
     */
    public QuestionSqliteVo queryQuestionVoWithQuestion(int questionid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                "question_id=?", new String[]{String.valueOf(questionid)}, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionSqliteVo vo = getQuesitonSqlite(mDbQueryUtil, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    public int queryCountWithCourseid(int coursedid) {
        if (empty()) return 0;
        String sql = "select count(*) from " + DataMessageVo.USER_QUESTION_TABLE_QUESTION + "  where courseid=" + coursedid;
        SQLiteStatement statement = mSqLiteDatabase.compileStatement(sql);
        long l = statement.simpleQueryForLong();
        return (int) l;

    }

    public int queryCountWithCourseOid(int coursedid) {
        if (empty()) return 0;
        String sql = "select count(*) from " + DataMessageVo.USER_QUESTION_TABLE_QUESTION + "  where courseid=" + coursedid + " and parent_id=0";
        SQLiteStatement statement = mSqLiteDatabase.compileStatement(sql);
        long l = statement.simpleQueryForLong();
        return (int) l;

    }

    /**
     * 获取所有题信息
     *
     * @param mData
     * @return
     */
    public List<QuestionSqliteVo> getQuestionList(List<ErrorOrCollectItemVo> mData) {
        if (empty()) return null;
        if (mData == null || mData.isEmpty()) return null;
        List<QuestionSqliteVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();

        for (int i = 0; i < mData.size(); i++) {
            ErrorOrCollectItemVo vo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(vo.getQuestionid());
                buffer1.append(vo.getCourseid());
            } else {
                buffer.append(vo.getQuestionid());
                buffer1.append(vo.getCourseid());
                buffer.append(",");
                buffer1.append(",");
            }
        }
/*        Cursor query = mSqLiteDatabase.
                query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                        "question_id=? and courseid=?",
                        new String[]{String.valueOf(vo.getQuestionid()), String.valueOf(vo.getCourseid())},
                        null, null, null);  */
        Cursor query = mSqLiteDatabase.
                query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                        "question_id in(" + buffer.toString() + ") and courseid in (" + buffer1.toString() + ")",
                        null,
                        null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionSqliteVo sqliteVo = getQuesitonSqlite(mDbQueryUtil, true);
            if (sqliteVo != null)
                list.add(sqliteVo);
        }
        buffer = null;
        buffer1 = null;
        query.close();
        return list;
    }

    public List<QuestionSqliteVo> getQuestionListLick(int page, String key) {
        ArrayList<QuestionSqliteVo> questionSqliteVos = new ArrayList<>();
        if (empty()) return questionSqliteVos;
       /* String sql = "select * from " + DataMessageVo.USER_QUESTION_TABLE_QUESTION + " where questionString like '%" + key + "%'";
        Cursor cursor = mSqLiteDatabase.rawQuery(sql, null);*/
        Cursor cursor = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, "parent_id=? and  questionString like ? ", new String[]{String.valueOf(0), "%" + key + "%"},
                null, null, null, "" + page + ",30");
        mDbQueryUtil.initCursor(cursor);
        while (cursor.moveToNext()) {
            QuestionSqliteVo vo = getQuesitonSqlite(mDbQueryUtil, true);
            questionSqliteVos.add(vo);
        }
        cursor.close();
        return questionSqliteVos;

    }

    /**
     * 获取所有题信息
     *
     * @param mData
     * @return
     */
    public ArrayList<QuestionSqliteVo> getQuestionList(SQLiteDatabase mSqLiteDatabase, List<ErrorOrCollectItemVo> mData) {
        if (empty()) return null;
        if (mData == null || mData.isEmpty()) return null;
        ArrayList<QuestionSqliteVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            ErrorOrCollectItemVo vo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(vo.getQuestionid());
                buffer1.append(vo.getCourseid());
            } else {
                buffer.append(vo.getQuestionid());
                buffer1.append(vo.getCourseid());
                buffer.append(",");
                buffer1.append(",");
            }
        }
/*        Cursor query = mSqLiteDatabase.
                query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                        "question_id=? and courseid=?",
                        new String[]{String.valueOf(vo.getQuestionid()), String.valueOf(vo.getCourseid())},
                        null, null, null);  */
        Cursor query = mSqLiteDatabase.
                query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                        "question_id in (" + buffer.toString() + ") and courseid in (" + buffer1.toString() + ")",
                        null,
                        null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionSqliteVo sqliteVo = getQuesitonSqlite(mDbQueryUtil, true);
            if (sqliteVo != null)
                list.add(sqliteVo);
        }
        buffer = null;
        buffer1 = null;
        query.close();
        return list;
    }

    /**
     * 更具题id 获取相应题
     *
     * @param mData
     * @return
     */
    public List<QuestionCaseVo> queryQuestionExamVo(List<QuesitonExamRaltionSqliteVo> mData) {
        if (empty()) return null;
        List<QuestionCaseVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            QuesitonExamRaltionSqliteVo examRaltionSqliteVo = mData.get(i);
            if (i == (mData.size() - 1)) {
                buffer.append(examRaltionSqliteVo.getQuestionid());
            } else {
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer.append(",");
            }
        }
 /*       Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                "question_id=?", new String[]{String.valueOf(examRaltionSqliteVo.getQuestionid())},
                null, null, "sort desc,question_id asc", null);*/
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "question_id in(" + buffer.toString() + ")", null,
                null, null, "sort desc,question_id asc", null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;

    }

    /**
     * 更具题id 获取相应题
     *
     * @param mData
     * @return
     */
    public List<QuestionCaseVo> queryQuestionCaseVoMainOne(List<QuesitonExamRaltionSqliteVo> mData) {
        if (empty()) return null;
        List<QuestionCaseVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            QuesitonExamRaltionSqliteVo examRaltionSqliteVo = mData.get(i);
            /*Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                    "question_id=?", new String[]{String.valueOf(examRaltionSqliteVo.getQuestionid())},
                    null, null, "sort desc,question_id asc", null);*/
            if (i == (mData.size() - 1)) {
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer1.append(5);
            } else {
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer1.append(5);
                buffer.append(",");
                buffer1.append(",");
            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "question_id in (" + buffer.toString() + ") and questiontype in (" + buffer1.toString() + ")",
                null,
                null, null, "sort desc,question_id asc");

        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            list.add(vo);
        }
        buffer = null;
        buffer1 = null;
        query.close();
        return list;

    }

    /**
     * 更具题id 获取相应题
     *
     * @return
     */
    public List<QuestionCaseVo> queryQuestionCaseVoChildOne(int parentid, int chapter_id, int courseid) {
        List<QuestionCaseVo> childs = new ArrayList<>();
        if (empty()) return childs;
            /*Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                    "question_id=?", new String[]{String.valueOf(examRaltionSqliteVo.getQuestionid())},
                    null, null, "sort desc,question_id asc", null);*/
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "parent_id=? and chapter_id=? and courseid=?",
                new String[]{String.valueOf(parentid), String.valueOf(chapter_id), String.valueOf(courseid)},
                null, null, "sort desc,question_id asc");

        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            childs.add(vo);
        }
        query.close();
        return childs;

    }

    /**
     * 更具题id 获取相应题
     *
     * @return
     */
    public List<QuestionCaseVo> queryQuestionCaseVoChildTwo(List<QuestionCaseVo> mainlist, int courseid) {
        List<QuestionCaseVo> childs = new ArrayList<>();
        if (empty()) return childs;
            /*Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                    "question_id=?", new String[]{String.valueOf(examRaltionSqliteVo.getQuestionid())},
                    null, null, "sort desc,question_id asc", null);*/
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        for (int i = 0; i < mainlist.size(); i++) {
            QuestionCaseVo vo = mainlist.get(i);
            if (i == (mainlist.size() - 1)) {
                buffer.append(vo.getQuestion_id());
                buffer1.append(vo.getChapter_id());
                buffer2.append(courseid);
            } else {
                buffer.append(vo.getQuestion_id());
                buffer.append(",");
                buffer1.append(vo.getChapter_id());
                buffer1.append(",");
                buffer2.append(courseid);
                buffer2.append(",");
            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "parent_id in(" + buffer.toString() + ") and chapter_id in ("
                        + buffer1.toString() + ") and" +
                        " courseid in(" + buffer2.toString() + ")",
                null,
                null, null, "sort desc,question_id asc");

        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            childs.add(vo);
        }
        query.close();
        return childs;

    }

    /**
     * 更具题id 获取相应题
     *
     * @return
     */
    public List<QuestionCaseVo> queryQuestionCaseVoChildJianOne(List<QuestionCaseVo> mParentLists) {
        List<QuestionCaseVo> childs = new ArrayList<>();
        if (empty()) return childs;
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        for (int i = 0; i < mParentLists.size(); i++) {
            QuestionCaseVo vo = mParentLists.get(i);
            if (i == (mParentLists.size() - 1)) {
                buffer.append(vo.getQuestion_id());
                buffer1.append(4);
            } else {
                buffer.append(vo.getQuestion_id());
                buffer.append(",");
                buffer1.append(4);
                buffer1.append(",");
            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "parent_id in(" + buffer.toString() + ")and questiontype in(" + buffer1.toString() + ")",
                null,
                null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext())

        {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            childs.add(vo);
        }
        query.close();
        return childs;

    }

    /**
     * 更具题id 获取相应题
     *
     * @return
     */
    public List<QuestionCaseVo> queryQuestionCaseVoChildJianOne(int parentid, int chapter_id, int courseid) {
        List<QuestionCaseVo> childs = new ArrayList<>();
        if (empty()) return childs;
            /*Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                    "question_id=?", new String[]{String.valueOf(examRaltionSqliteVo.getQuestionid())},
                    null, null, "sort desc,question_id asc", null);*/
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"}, "parent_id=? and chapter_id=? and courseid=? and questiontype=?",
                new String[]{String.valueOf(parentid), String.valueOf(chapter_id), String.valueOf(courseid),
                        String.valueOf(4)},
                null, null, "sort desc,question_id asc");

        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            childs.add(vo);
        }
        query.close();
        return childs;

    }

    /**
     * 更具题id 获取相应题
     *
     * @param mData
     * @return
     */
    public List<QuestionCaseVo> queryQuestionCaseVo(List<QuesitonExamRaltionSqliteVo> mData) {
        if (empty()) return null;
        List<QuestionCaseVo> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            QuesitonExamRaltionSqliteVo examRaltionSqliteVo = mData.get(i);
            /*Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null,
                    "question_id=?", new String[]{String.valueOf(examRaltionSqliteVo.getQuestionid())},
                    null, null, "sort desc,question_id asc", null);*/
            if (i == (mData.size() - 1)) {
                buffer.append(examRaltionSqliteVo.getQuestionid());
            } else {
                buffer.append(examRaltionSqliteVo.getQuestionid());
                buffer.append(",");

            }
        }
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "question_id in (" + buffer.toString() + ")",
                null,
                null, null, null);

        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            list.add(vo);
        }
        buffer = null;
        query.close();
        return list;

    }

    /**
     * 查询某科目下所有题目
     *
     * @param couresid
     * @return
     */
    public ArrayList<QuestionCaseVo> queryAllQuesitonWithCaseCouresid(String couresid) {
        if (empty()) return null;
//        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, "courseid=?", new String[]{couresid}, null, null, "sort desc,question_id asc");
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "courseid=?", new String[]{couresid}, null, null,
                "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        ArrayList<QuestionCaseVo> mList = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            mList.add(vo);
        }
        query.close();
        return mList;
    }

    /**
     * 查询某科目下所有题目
     *
     * @param couresid
     * @return
     */
    public ArrayList<QuestionSqliteVo> queryAllQuesitonWithCouresid(String couresid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, "courseid=?", new String[]{couresid}, null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        ArrayList<QuestionSqliteVo> mList = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionSqliteVo vo = getQuesitonSqlite(mDbQueryUtil, true);
            mList.add(vo);
        }
        query.close();
        return mList;
    }

    /**
     * 查询某科目下所有问题id, 父类id,类型，主键id
     *
     * @param couresid
     * @return
     */
    public List<QuestionCaseVo> queryAllQuesitonCaseWithCouresid(String couresid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "courseid=?", new String[]{couresid}, null, null, "sort desc,question_id asc");
        mDbQueryUtil.initCursor(query);
        List<QuestionCaseVo> mList = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            mList.add(vo);
        }
        query.close();
        return mList;
    }

    /**
     * @param courseid    科目id
     * @param diffculyMin 最小值
     * @param diffculyMax 最大值
     * @return
     */
    public ArrayList<QuestionSqliteVo> queryCondition(String courseid, String diffculyMin,
                                                      String diffculyMax) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                null, "courseid=? and difficulty>=? and difficulty<=?",
                new String[]{String.valueOf(courseid), diffculyMin, diffculyMax}, null, null, null);
        mDbQueryUtil.initCursor(query);
        ArrayList<QuestionSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionSqliteVo vo = getQuesitonSqlite(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;
    }

    /**
     * @param courseid    科目id
     * @param diffculyMin 最小值
     * @param diffculyMax 最大值
     * @return
     */
    public ArrayList<QuestionCaseVo> queryCaseCondition(String courseid, String diffculyMin,
                                                        String diffculyMax) {
        if (empty()) return null;
/*        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                null, "courseid=? and difficulty>=? and difficulty<=?",
                new String[]{String.valueOf(courseid), diffculyMin, diffculyMax}, null, null, null);*/
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION,
                new String[]{"id", "question_id", "parent_id", "questiontype", "difficulty", "chapter_id"},
                "courseid=? and difficulty>=? and difficulty<=?", new String[]{String.valueOf(courseid), diffculyMin, diffculyMax},
                null, null, "sort desc,question_id asc");

        mDbQueryUtil.initCursor(query);
        ArrayList<QuestionCaseVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionCaseVo vo = getQuesitonCaseVo(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;
    }

    /**
     * @param vo
     * @return
     */
    public static ContentValues setValues(QuestionSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("question_id", vo.getQuestion_id());
        values.put("question", vo.getQuestion());
        values.put("questionString", vo.getQuestionString());
        values.put("explainString", vo.getExplainString());
        values.put("keywordsString", vo.getKeywordsString());
        values.put("questionimg", vo.getQuestionimg());
        values.put("isreadcom", vo.getIsreadcom());
        values.put("parent_id", vo.getParent_id());
        values.put("questiontype", vo.getQuestiontype());
        values.put("option_a", vo.getOption_a());
        values.put("option_b", vo.getOption_b());
        values.put("option_c", vo.getOption_c());
        values.put("option_d", vo.getOption_d());
        values.put("option_e", vo.getOption_e());
        values.put("option_f", vo.getOption_f());
        values.put("option_g", vo.getOption_g());
        values.put("option_h", vo.getOption_h());
        values.put("choice_answer", vo.getChoice_answer());
        values.put("explain", vo.getExplained());
        values.put("explainimg", vo.getExplainimg());
        values.put("chapter_id", vo.getChapter_id());
        values.put("question_mold", vo.getQuestion_mold());
        values.put("sort", vo.getSort());
        values.put("courseid", vo.getCourseid());
        values.put("keywords", vo.getKeywords());
        values.put("difficulty", vo.getDifficulty());
        values.put("right_rate", vo.getRight_rate());
//        values.put("wrong_rate", vo.getRight_rate());
        values.put("score", vo.getScore());
        values.put("ext_int1", vo.getExt_int1());
        values.put("ext_int2", vo.getExt_int2());
        values.put("ext_double1", vo.getExt_double1());
        values.put("ext_double2", vo.getExt_double2());
        values.put("ext_string1", vo.getExt_string1());
        values.put("ext_string2", vo.getExt_string2());
        return values;
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public List<QuestionSqliteVo> findQuestionAll() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_QUESTION, null, null, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        List<QuestionSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionSqliteVo vo = getQuesitonSqlite(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;
    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    /**
     * @param mQueryUtil
     * @param locad      查询是否时本地
     * @return
     */
    private static QuestionCaseVo getQuesitonCaseVo(DbQueryUtil mQueryUtil, boolean locad) {
        QuestionCaseVo sqliteVo = new QuestionCaseVo();
        int id = mQueryUtil.queryInt("id");
        if (locad) {
            int question_id = mQueryUtil.queryInt("question_id");
            sqliteVo.setQuestion_id(question_id);
            sqliteVo.setId(id);
        } else {
            sqliteVo.setQuestion_id(id);
        }
        int parent_id = mQueryUtil.queryInt("parent_id");
        int chapter_id = mQueryUtil.queryInt("chapter_id");
        int questiontype = mQueryUtil.queryInt("questiontype");
        int difficulty = mQueryUtil.queryInt("difficulty");
        sqliteVo.setParent_id(parent_id);
        sqliteVo.setChapter_id(chapter_id);
        sqliteVo.setDifficulty(difficulty);
        sqliteVo.setQuestiontype(questiontype);
        return sqliteVo;
    }

    /**
     * @param mQueryUtil
     * @param locad      查询是否时本地
     * @return
     */
    public synchronized static QuestionSqliteVo getQuesitonSqlite(DbQueryUtil mQueryUtil,
                                                                  boolean locad) {
        QuestionSqliteVo sqliteVo = new QuestionSqliteVo();
        int id = mQueryUtil.queryInt("id");
        if (locad) {
            int question_id = mQueryUtil.queryInt("question_id");
            sqliteVo.setQuestion_id(question_id);
            sqliteVo.setId(id);
            String questionString = mQueryUtil.queryString("questionString");
            String keywordsString = mQueryUtil.queryString("keywordsString");
            String explainString = mQueryUtil.queryString("explainString");
            sqliteVo.setQuestionString(questionString);
            sqliteVo.setKeywordsString(keywordsString);
            sqliteVo.setExplainString(explainString);
        } else {
            sqliteVo.setQuestion_id(id);
        }
        byte[] questions = mQueryUtil.queryBLOB("question");
        String questionimg = mQueryUtil.queryString("questionimg");
        int isreadcom = mQueryUtil.queryInt("isreadcom");
        int parent_id = mQueryUtil.queryInt("parent_id");
        int questiontype = mQueryUtil.queryInt("questiontype");
        String option_a = mQueryUtil.queryString("option_a");
        String option_b = mQueryUtil.queryString("option_b");
        String option_c = mQueryUtil.queryString("option_c");
        String option_d = mQueryUtil.queryString("option_d");
        String option_e = mQueryUtil.queryString("option_e");
        String option_f = mQueryUtil.queryString("option_f");
        String option_g = mQueryUtil.queryString("option_g");
        String option_h = mQueryUtil.queryString("option_h");
        String choice_answer = mQueryUtil.queryString("choice_answer");
        byte[] explained = mQueryUtil.queryBLOB("explain");
        String explainimg = mQueryUtil.queryString("explainimg");
        int chapter_id = mQueryUtil.queryInt("chapter_id");
        int question_mold = mQueryUtil.queryInt("question_mold");
        int sort = mQueryUtil.queryInt("sort");
        int courseid = mQueryUtil.queryInt("courseid");
        byte[] keywords = mQueryUtil.queryBLOB("keywords");
        int difficulty = mQueryUtil.queryInt("difficulty");
        double wrong_rate = mQueryUtil.querydouble("right_rate");
//        double wrong_rate = mQueryUtil.querydouble("wrong_rate");
        double score = mQueryUtil.querydouble("score");
        int ext_int1 = mQueryUtil.queryInt("ext_int1");
        int ext_int2 = mQueryUtil.queryInt("ext_int2");
        double ext_double1 = mQueryUtil.querydouble("ext_double1");
        double ext_double2 = mQueryUtil.querydouble("ext_double2");
        String ext_string1 = mQueryUtil.queryString("ext_string1");
        String ext_string2 = mQueryUtil.queryString("ext_string2");

        sqliteVo.setChapter_id(chapter_id);
        sqliteVo.setChoice_answer(choice_answer);
        sqliteVo.setDifficulty(difficulty);
        sqliteVo.setExplained(explained);
        sqliteVo.setExplainimg(explainimg);
        sqliteVo.setExt_int1(ext_int1);
        sqliteVo.setExt_int2(ext_int2);
        sqliteVo.setExt_double1(ext_double1);
        sqliteVo.setExt_double2(ext_double2);
        sqliteVo.setExt_string1(ext_string1);
        sqliteVo.setExt_string2(ext_string2);
        sqliteVo.setIsreadcom(isreadcom);
        sqliteVo.setKeywords(keywords);
        sqliteVo.setOption_a(option_a);
        sqliteVo.setOption_b(option_b);
        sqliteVo.setOption_c(option_c);
        sqliteVo.setOption_d(option_d);
        sqliteVo.setOption_e(option_e);
        sqliteVo.setOption_f(option_f);
        sqliteVo.setOption_g(option_g);
        sqliteVo.setOption_h(option_h);
        sqliteVo.setParent_id(parent_id);
        sqliteVo.setQuestion(questions);

        sqliteVo.setQuestion_mold(question_mold);
        sqliteVo.setQuestionimg(questionimg);
        sqliteVo.setQuestiontype(questiontype);

        sqliteVo.setScore(score);
        sqliteVo.setRight_rate(wrong_rate);
        sqliteVo.setSort(sort);
        sqliteVo.setCourseid(courseid);
        return sqliteVo;
    }

}
