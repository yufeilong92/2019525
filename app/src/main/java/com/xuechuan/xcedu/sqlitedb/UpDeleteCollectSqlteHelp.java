package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ErrorSqlteHelp.java
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 更新收藏表
 * @author: YFL
 * @date: 2018/12/25 22:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/12/25 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class UpDeleteCollectSqlteHelp {
    private Context mContext;
    private static volatile UpDeleteCollectSqlteHelp _instance = null;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private UpDeleteCollectSqlteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static UpDeleteCollectSqlteHelp getInstance(Context context) {
        if (_instance == null) {
            synchronized (UpDeleteCollectSqlteHelp.class) {
                if (_instance == null) {
                    _instance = new UpDeleteCollectSqlteHelp(context);
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

    //添加数据
    public void addCollectSqliteVo(CollectSqliteVo sqliteVo) {
        if (empty()) return;
        CollectSqliteVo add = queryIsAdd(sqliteVo.getQuestion_id(), sqliteVo.getChapterid(), sqliteVo.getCourseid());
        if (add == null || add.getCourseid() == 0) {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_DETUP_COLLECT_TABLE, null, values);
        } else {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_DETUP_COLLECT_TABLE, values, "id=?", new String[]{String.valueOf(add.getId())});
        }
    }

    public List<CollectSqliteVo> queryAllList() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DETUP_COLLECT_TABLE, null, null, null, null,
                null, "time desc", "500");
        mDbQueryUtil.initCursor(query);
        List<CollectSqliteVo> lists = new ArrayList<>();
        while (query.moveToNext()) {
            CollectSqliteVo vo = getCollectVo(mDbQueryUtil);
            lists.add(vo);
        }
        query.close();
        return lists;

    }
    public List<CollectSqliteVo> queryAllDataList() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DETUP_COLLECT_TABLE, null, null, null, null,
                null, null);
        mDbQueryUtil.initCursor(query);
        List<CollectSqliteVo> lists = new ArrayList<>();
        while (query.moveToNext()) {
            CollectSqliteVo vo = getCollectVo(mDbQueryUtil);
            lists.add(vo);
        }
        query.close();
        return lists;

    }

    //删除数据
    public void delectItem(int id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_DETUP_COLLECT_TABLE, "id=?",
                new String[]{String.valueOf(id)});
    }
    //删除数据
    public void delect() {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_DETUP_COLLECT_TABLE,null,null);
    }
    public void deleteItem(int couresid, int chapterid, int questionid) {
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_DETUP_COLLECT_TABLE,
                "courseid=? and chapterid=? and question_id=?",
                new String[]{String.valueOf(couresid), String.valueOf(chapterid), String.valueOf(questionid)});
    }

    private CollectSqliteVo queryIsAdd(int questionid, int chapter_id, int courseid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DETUP_COLLECT_TABLE, null, "question_id=? and chapterid=? and courseid=?",
                new String[]{String.valueOf(questionid), String.valueOf(chapter_id), String.valueOf(courseid)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            CollectSqliteVo vo = getCollectVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    private ContentValues getContentValues(CollectSqliteVo sqliteVo) {
        ContentValues values = new ContentValues();
        values.put("question_id", sqliteVo.getQuestion_id());
        values.put("collectable", sqliteVo.getCollectable());
        values.put("questiontype", sqliteVo.getQuestiontype());
        values.put("chapterid", sqliteVo.getChapterid());
        values.put("time", sqliteVo.getTime());
        values.put("courseid", sqliteVo.getCourseid());
        return values;
    }

    private CollectSqliteVo getCollectVo(DbQueryUtil mDbQueryUtil) {
        CollectSqliteVo vo = new CollectSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int question_id = mDbQueryUtil.queryInt("question_id");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        int courseid = mDbQueryUtil.queryInt("courseid");
        int questiontype = mDbQueryUtil.queryInt("questiontype");
        int collectable = mDbQueryUtil.queryInt("collectable");
        String time = mDbQueryUtil.queryString("time");
        vo.setId(id);
        vo.setChapterid(chapterid);
        vo.setCollectable(collectable);
        vo.setCourseid(courseid);
        vo.setQuestion_id(question_id);
        vo.setQuestiontype(questiontype);
        vo.setTime(time);
        return vo;


    }

}
