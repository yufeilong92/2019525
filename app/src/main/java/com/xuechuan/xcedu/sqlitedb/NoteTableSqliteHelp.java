package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.NoteTbaleSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

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
public class NoteTableSqliteHelp {
    private static volatile NoteTableSqliteHelp _singleton;
    private Context mContext;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private NoteTableSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static NoteTableSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (NoteTableSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new NoteTableSqliteHelp(context);
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

    public void addCoolectItem(NoteTbaleSqliteVo vo) {
        if (empty()) return;
        NoteTbaleSqliteVo sqliteVo = queryIsAdd(vo.getCourseid(), vo.getChapterid(), vo.getQuestion_id());
        if (sqliteVo == null|| StringUtil.isEmpty(sqliteVo.getTime())) {
            ContentValues value = getContentValue(vo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_NOTE_TABLE, null, value);

        } else {
            ContentValues value = getContentValue(vo);
            mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_NOTE_TABLE, value, "id=?", new String[]{String.valueOf(sqliteVo.getId())});

        }

    }

    private NoteTbaleSqliteVo queryIsAdd(int couresid, int chapterid, int questionid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_NOTE_TABLE, null, "courseid=? and chapterid=? and question_id=?",
                new String[]{String.valueOf(couresid), String.valueOf(chapterid), String.valueOf(questionid)},
                null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            NoteTbaleSqliteVo vo = getCollectTableVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    private ContentValues getContentValue(NoteTbaleSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("question_id", vo.getQuestion_id());
        values.put("note", vo.getNote());
        values.put("questiontype", vo.getQuestiontype());
        values.put("chapterid", vo.getChapterid());
        values.put("time", vo.getTime());
        values.put("courseid", vo.getCourseid());
        return values;
    }

    private NoteTbaleSqliteVo getCollectTableVo(DbQueryUtil mDbQueryUtil) {
        NoteTbaleSqliteVo vo = new NoteTbaleSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int question_id = mDbQueryUtil.queryInt("question_id");
        String note = mDbQueryUtil.queryString("note");
        int questiontype = mDbQueryUtil.queryInt("questiontype");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        String time = mDbQueryUtil.queryString("time");
        int courseid = mDbQueryUtil.queryInt("courseid");
        vo.setId(id);
        vo.setQuestion_id(question_id);
        vo.setChapterid(chapterid);
        vo.setNote(note);
        vo.setCourseid(courseid);
        vo.setTime(time);
        vo.setQuestiontype(questiontype);
        return vo;

    }

}
