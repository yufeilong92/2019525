package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ErrorSqlteHelp.java
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 错题集集合表(未用)
 * @author: YFL
 * @date: 2018/12/25 22:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/12/25 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class UpErrorSqlteHelp {
    private Context mContext;
    private static volatile UpErrorSqlteHelp _instance = null;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private UpErrorSqlteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static UpErrorSqlteHelp getInstance(Context context) {
        if (_instance == null) {
            synchronized (UpErrorSqlteHelp.class) {
                if (_instance == null) {
                    _instance = new UpErrorSqlteHelp(context);
                }
            }
        }
        return _instance;
    }

    private SQLiteDatabase createtable() {
        DatabaseContext context = new DatabaseContext(mContext);
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


    public void addErrorItem(ErrorSqliteVo sqliteVo) {
        if (empty()) return;
        ErrorSqliteVo errorSqliteVo = queryIsAdd(sqliteVo.getCourseid(), sqliteVo.getChapterid(), sqliteVo.getQuesitonid());
        if (errorSqliteVo == null || errorSqliteVo.getQuesitonid() == 0) {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.insert(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, null, values);
        } else {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, values, "id=?", new String[]{String.valueOf(errorSqliteVo.getId())});
        }
    }

    public List<ErrorSqliteVo> queryAllList(){
        if (empty())return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, null, null, null, null, null
                , "time desc","500");
        List<ErrorSqliteVo> mList = new ArrayList<>();
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()){
            ErrorSqliteVo vo = getErrorSqliteVo(mDbQueryUtil);
            mList.add(vo);
        }
        query.close();
        return mList;
    }
    public List<ErrorSqliteVo> queryAllDataList(){
        if (empty())return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, null, null, null, null, null
                , null);
        List<ErrorSqliteVo> mList = new ArrayList<>();
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()){
            ErrorSqliteVo vo = getErrorSqliteVo(mDbQueryUtil);
            mList.add(vo);
        }
        query.close();
        return mList;
    }
    /**
     * 获取用户做题记录
     *
     * @param coursedid
     * @return
     */
    public int queryCountWithCourseid(int coursedid) {
        if (empty()) return 0;
        String sql = "select count(*) from " + DataMessageVo.USER_INFOM_TABLE_UP_ERROR + "  where courseid=" + coursedid;
        SQLiteStatement statement = mSqLiteDatabase.compileStatement(sql);
        long l = statement.simpleQueryForLong();
        return (int) l;

    }

    public ErrorSqliteVo queryVoWithId(int id) {
        return null;
    }

    /**
     * 更新用户作对次数
     *
     * @param number
     * @param id
     */
    public void upErrorRightNumber(int number, int id) {
        if (empty()) return;
        ContentValues values = new ContentValues();
        values.put("rightnumber", number);
        mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, values,
                "id=?", new String[]{String.valueOf(id)});

    }

    /**
     * 获取错题集合
     *
     * @param courseid
     * @return
     */
    public List<ErrorSqliteVo> getErrorLists(int courseid) {
        List<ErrorSqliteVo> list = new ArrayList<>();
        if (empty()) return list;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, null,
                "courseid=?", new String[]{String.valueOf(courseid)}, null, null
                , null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            ErrorSqliteVo vo = getErrorSqliteVo(mDbQueryUtil);
            list.add(vo);
        }
        query.close();
        return list;

    }

    //删除某条数据
    public void delectErrorItem(int id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, "id=?",
                new String[]{String.valueOf(id)});
    }
    //删除某条数据
    public void delectError() {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, null,null);
    }
    /**
     * 删除
     *
     * @param courseid
     * @param chapterid
     * @param questionid
     */
    public void deleteErrorItem(int courseid, int chapterid, int questionid) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, "courseid=? and chapterid=? and quesitonid=?",
                new String[]{String.valueOf(courseid), String.valueOf(chapterid),
                        String.valueOf(questionid)});
    }

    public ErrorSqliteVo queryIsAdd(int courseid, int chapterid, int questionid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, null,
                "courseid=? and chapterid=? and quesitonid=?",
                new String[]{String.valueOf(courseid), String.valueOf(chapterid),
                        String.valueOf(questionid)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            ErrorSqliteVo vo = getErrorSqliteVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 更新作对次数
     *
     * @param id
     * @param rihgtnumber
     */
    public void upDataRightNumber(int id, int rihgtnumber) {
        if (empty()) return;
        ContentValues values = new ContentValues();
        values.put("rightnumber", rihgtnumber);
        mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_UP_ERROR, values, "id=?",
                new String[]{String.valueOf(id)});
    }


    private ContentValues getContentValues(ErrorSqliteVo sqliteVo) {
        ContentValues values = new ContentValues();
        values.put("chapterid", sqliteVo.getChapterid());
        values.put("time", sqliteVo.getTime());
        values.put("rightnumber", sqliteVo.getRightnumber());
        values.put("userid", sqliteVo.getUserid());
        values.put("courseid", sqliteVo.getCourseid());
        values.put("quesitonid", sqliteVo.getQuesitonid());
        values.put("rightallnumber", sqliteVo.getRightAllNumber());
        return values;
    }

    private ErrorSqliteVo getErrorSqliteVo(DbQueryUtil mDbQueryUtil) {
        ErrorSqliteVo vo = new ErrorSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        int courseid = mDbQueryUtil.queryInt("courseid");
        int quesitonid = mDbQueryUtil.queryInt("quesitonid");
        int rightnumber = mDbQueryUtil.queryInt("rightnumber");
        String rightallnumber = mDbQueryUtil.queryString("rightallnumber");
        String time = mDbQueryUtil.queryString("time");
        String userid = mDbQueryUtil.queryString("userid");
        vo.setChapterid(chapterid);
        vo.setId(id);
        vo.setCourseid(courseid);
        vo.setQuesitonid(quesitonid);
        vo.setRightAllNumber(rightallnumber);
        vo.setRightnumber(rightnumber);
        vo.setTime(time);
        vo.setUserid(userid);
        return vo;

    }

}
