package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionChapterSqliteVo;

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
 * @date: 2018.12.11 下午 2:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QuestionChapterSqliteHelp {
    private static volatile QuestionChapterSqliteHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private QuestionChapterSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static QuestionChapterSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (QuestionChapterSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new QuestionChapterSqliteHelp(context);
                }
            }
        }
        return _singleton;
    }

/*    public void initOpenUserInfom() {
        DbPathUitl instance = DbPathUitl.get_Instance(mContext);
        String dbPath = instance.getDbPath();
        if (StringUtil.isEmpty(dbPath)) return;
        String concat = dbPath.concat(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER);
        File file = new File(concat);
        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file, null);
        if (mSqLiteDatabase == null) {
            createtable();
        }
    }*/

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

    public synchronized void addQestionChapterItem(QuestionChapterSqliteVo vo) {
        if (vo == null) return;
        if (empty()) return;
/*        String sql = "insert into " + DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER +
                "(courseid,chaptername,mold,questionnum,sort,parentid) values (" +
                vo.getCourseid() + ",'" +
                vo.getChaptername() + "'," +
                vo.getMold() + "," +
                vo.getQuestionnum() + "," +
                vo.getSort() + "," +
                vo.getParentid() + ");";
        mSqLiteDatabase.execSQL(sql);*/
        try {
//            mSqLiteDatabase.beginTransaction();
            if (queryChapterVo(vo.getQuestionchapterid())) return;
            ContentValues values = setValues(vo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, values);
//            mSqLiteDatabase.setTransactionSuccessful();
//            mSqLiteDatabase.endTransaction();
        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
            e.printStackTrace();
        }
    }

    /**
     * 批量添加数据
     *
     * @param list
     */
    public void addListQuestionChaper(ArrayList<QuestionChapterSqliteVo> list) {
        if (empty()) return;
        try {
//            mSqLiteDatabase.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                QuestionChapterSqliteVo sqliteVo = list.get(i);
                if (queryChapterVo(sqliteVo.getQuestionchapterid())) continue;
                ContentValues values = setValues(sqliteVo);
                mSqLiteDatabase.insert(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, values);
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

    public static ContentValues setValues(QuestionChapterSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("questionchapter_id", vo.getQuestionchapterid());
        values.put("courseid", vo.getCourseid());
        values.put("chaptername", vo.getChaptername());
        values.put("mold", vo.getMold());
        values.put("questionnum", vo.getQuestionnum());
        values.put("sort", vo.getSort());
        values.put("parentid", vo.getParentid());
        return values;
    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    public QuestionChapterSqliteVo findQuestionChapterItemAll(int idParamer) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, "id=?",
                new String[]{String.valueOf(idParamer)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionChapterSqliteVo sqliteVo = getQuestionChapterSqliteVo(mDbQueryUtil, true);
            query.close();
            return sqliteVo;
        }
        query.close();
        return null;
    }

    public void UpDateQestionChaperItem(QuestionChapterSqliteVo vo) {
        if (empty()) return;
        String sql = "update " + DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER +
                "  set courseid=" + vo.getCourseid() +
                ",chaptername ='" + vo.getChaptername() +
                "',mold=" + vo.getMold() +
                ",questionnum=" + vo.getQuestionnum() +
                ",sort=" + vo.getSort() +
                ",parentid=" + vo.getParentid() +
                "  where  " + ");";
        mSqLiteDatabase.execSQL(sql);

    }

    public List<QuestionChapterSqliteVo> findQuestionChapterItemAll() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, null,
                null, null, null, null);
        mDbQueryUtil.initCursor(query);
        List<QuestionChapterSqliteVo> list = new ArrayList<>();

        while (query.moveToNext()) {
            QuestionChapterSqliteVo sqliteVo = getQuestionChapterSqliteVo(mDbQueryUtil, true);
            list.add(sqliteVo);
        }
        query.close();
        return list;
    }

    /**
     * 查找类型并不排序
     *
     * @param couserid
     * @param mold
     * @param parentid
     * @return
     */
    public List<QuestionChapterSqliteVo> findQuestionChapterItemAllWiteCoureseid(String couserid, String mold, String parentid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null,
                "courseid =? and mold=? and parentid=?",
                new String[]{String.valueOf(couserid), String.valueOf(mold), String.valueOf(parentid)}, null, null, "sort desc,questionchapter_id asc");
        mDbQueryUtil.initCursor(query);
        List<QuestionChapterSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionChapterSqliteVo sqliteVo = getQuestionChapterSqliteVo(mDbQueryUtil, true);
            list.add(sqliteVo);
        }
        query.close();
        return list;
    }

    /**
     * @param mCouersid
     * @param parentid  父类id
     * @param mold      1 为章节练习
     * @return
     */
    public List<QuestionChapterSqliteVo> QueryCourseidZWmoidParenid(String mCouersid, int parentid, int mold) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null,
                "courseid=? and parentid=? and mold=?",
                new String[]{String.valueOf(mCouersid), String.valueOf(parentid), String.valueOf(mold)}, null, null, "sort desc,questionchapter_id asc");
        mDbQueryUtil.initCursor(query);
        List<QuestionChapterSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionChapterSqliteVo sqliteVo = getQuestionChapterSqliteVo(mDbQueryUtil, true);
            list.add(sqliteVo);
        }
        query.close();
        return list;
    }

    /**
     * 查询历年真题
     *
     * @return
     */
    public List<QuestionChapterSqliteVo> queryCalendarYearList(String courseid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null,
                "mold=? and courseid=?",
                new String[]{String.valueOf(DataMessageVo.CALENDARYEAR), courseid}, null, null, "sort desc,questionchapter_id asc");
        mDbQueryUtil.initCursor(query);
        List<QuestionChapterSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            QuestionChapterSqliteVo sqliteVo = getQuestionChapterSqliteVo(mDbQueryUtil, true);
            list.add(sqliteVo);
        }
        query.close();
        return list;

    }

    /**
     * 查询独家密卷真题
     *
     * @param courseid
     * @return
     */
    public List<QuestionChapterSqliteVo> queryExclusiveList(String courseid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null,
                "mold=? and courseid=?",
                new String[]{String.valueOf(DataMessageVo.EXCLUSIVE), courseid}, null, null, "sort desc,questionchapter_id asc");
        List<QuestionChapterSqliteVo> list = new ArrayList<>();
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionChapterSqliteVo vo = getQuestionChapterSqliteVo(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;

    }

    /**
     * 对莫个字段经行求和操作
     *
     * @param fieldname
     * @param mCouersid
     * @param parentid
     * @param mold
     * @return
     */
    public String queryQuestionSum(String fieldname, String mCouersid, int parentid, int mold) {
        if (empty()) return "0";
        Cursor cursor = mSqLiteDatabase.rawQuery("select sum (" + fieldname + ") from " + DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER +
                " where courseid =" + Integer.parseInt(mCouersid) + "  and parentid=" + parentid + "  and  mold= " + mold + ";", null);
        while (cursor.moveToNext()) {
            String string = cursor.getString(0);
            cursor.close();
            return string;
        }
        cursor.close();
        return "0";
    }

    /**
     * 查询章节名字
     * @param chapter_id
     * @return
     */
    public QuestionChapterSqliteVo queryCharpterWithChapterid(int chapter_id) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null,
                "questionchapter_id=?", new String[]{String.valueOf(chapter_id)},
                null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            QuestionChapterSqliteVo vo = getQuestionChapterSqliteVo(mDbQueryUtil, true);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 查询是否添加
     *
     * @param questionid
     * @return
     */
    public boolean queryChapterVo(int questionid) {
        if (empty()) return false;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QEUSTION_TABLE_QUESTION_CHAPTER, null, "questionchapter_id=?",
                new String[]{String.valueOf(questionid)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            return true;
        }
        return false;
    }

    /**
     * 查询适配器
     *
     * @param mDbQueryUtil
     * @param local
     * @return
     */
    public synchronized static QuestionChapterSqliteVo getQuestionChapterSqliteVo(DbQueryUtil mDbQueryUtil, boolean local) {
        QuestionChapterSqliteVo sqliteVo = new QuestionChapterSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        if (local) {
            int questionchapter_id = mDbQueryUtil.queryInt("questionchapter_id");
            sqliteVo.setQuestionchapterid(questionchapter_id);
            sqliteVo.setId(id);
        } else {
            sqliteVo.setQuestionchapterid(id);
        }
        int courseid = mDbQueryUtil.queryInt("courseid");
        String chaptername = mDbQueryUtil.queryString("chaptername");
        int mold = mDbQueryUtil.queryInt("mold");
        int questionnum = mDbQueryUtil.queryInt("questionnum");
        int sort = mDbQueryUtil.queryInt("sort");
        int parentid = mDbQueryUtil.queryInt("parentid");
        sqliteVo.setParentid(parentid);
        sqliteVo.setChaptername(chaptername);
        sqliteVo.setCourseid(courseid);
        sqliteVo.setMold(mold);
        sqliteVo.setQuestionnum(questionnum);
        sqliteVo.setSort(sort);
        return sqliteVo;
    }
}
