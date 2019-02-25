package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 收藏表(主操作表)
 * @author: L-BackPacker
 * @date: 2018.12.25 下午 4:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CollectSqliteHelp {
    private static volatile CollectSqliteHelp _singleton;
    private Context mContext;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private CollectSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static CollectSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (CollectSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new CollectSqliteHelp(context);
                }
            }
        }
        return _singleton;
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

    public void addCoolectItem(CollectSqliteVo vo) {
        if (empty()) return;
        CollectSqliteVo add = queryIsAdd(vo.getCourseid(), vo.getChapterid(), vo.getQuestion_id());
        if (add == null || add.getCourseid() == 0) {
            ContentValues value = getContentValue(vo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_COLLECT_TABLE, null, value);
        } else {
            ContentValues value = getContentValue(vo);
            mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_COLLECT_TABLE, value, "id=?", new String[]{String.valueOf(add.getId())});
        }
    }

    public void deleteItem(int id) {
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_COLLECT_TABLE, "id=?", new String[]{String.valueOf(id)});
    }

    public void deleteItem(int couresid, int chapterid, int questionid) {
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_COLLECT_TABLE,
                "courseid=? and chapterid=? and question_id=?",
                new String[]{String.valueOf(couresid), String.valueOf(chapterid), String.valueOf(questionid)});
    }

    private CollectSqliteVo queryIsAdd(int couresid, int chapterid, int questionid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_COLLECT_TABLE, null, "courseid=? and chapterid=? and question_id=?",
                new String[]{String.valueOf(couresid), String.valueOf(chapterid), String.valueOf(questionid)},
                null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            CollectSqliteVo vo = getCollectTableVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    public CollectSqliteVo queryCollectVo(int couresid, int chapterid, int questionid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_COLLECT_TABLE, null, "courseid=? and chapterid=? and question_id=?",
                new String[]{String.valueOf(couresid), String.valueOf(chapterid), String.valueOf(questionid)},
                null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            CollectSqliteVo vo = getCollectTableVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 获取用户做题记录
     *
     * @param coursedid
     * @return
     */
    public int queryCountWithCourseid(int coursedid) {
        if (empty()) return 0;
        String sql = "select count(*) from " + DataMessageVo.USER_QUESTION_COLLECT_TABLE + "  where courseid=" + coursedid;
        SQLiteStatement statement = mSqLiteDatabase.compileStatement(sql);
        long l = statement.simpleQueryForLong();
        return (int) l;

    }

    /**
     * 获取用户收藏
     *
     * @param courseid
     * @return
     */
    public List<CollectSqliteVo> getAllCollectWithCourseid(int courseid) {
        List<CollectSqliteVo> list = new ArrayList<>();
        if (empty()) return list;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_COLLECT_TABLE, null,
                "courseid=?", new String[]{String.valueOf(courseid)}, null,
                null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            CollectSqliteVo vo = getCollectTableVo(mDbQueryUtil);
            list.add(vo);
        }
        query.close();
        return list;
    }

    private ContentValues getContentValue(CollectSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("question_id", vo.getQuestion_id());
        values.put("collectable", vo.getCollectable());
        values.put("questiontype", vo.getQuestiontype());
        values.put("chapterid", vo.getChapterid());
        values.put("time", vo.getTime());
        values.put("courseid", vo.getCourseid());
        return values;
    }

    private CollectSqliteVo getCollectTableVo(DbQueryUtil mDbQueryUtil) {
        CollectSqliteVo vo = new CollectSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int question_id = mDbQueryUtil.queryInt("question_id");
        int collectable = mDbQueryUtil.queryInt("collectable");
        int questiontype = mDbQueryUtil.queryInt("questiontype");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        String time = mDbQueryUtil.queryString("time");
        int courseid = mDbQueryUtil.queryInt("courseid");
        vo.setId(id);
        vo.setQuestion_id(question_id);
        vo.setChapterid(chapterid);
        vo.setCollectable(collectable);
        vo.setCourseid(courseid);
        vo.setTime(time);
        vo.setQuestiontype(questiontype);
        return vo;

    }

}
