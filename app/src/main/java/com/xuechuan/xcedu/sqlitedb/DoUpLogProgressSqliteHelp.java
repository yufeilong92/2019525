package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoLogProgreeSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 用户做题记录(上传)
 * @author: L-BackPacker
 * @date: 2018.12.21 上午 9:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DoUpLogProgressSqliteHelp {
    private static volatile DoUpLogProgressSqliteHelp _singleton;
    private Context mContext;
    private final SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private DoUpLogProgressSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static DoUpLogProgressSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (DoUpLogProgressSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new DoUpLogProgressSqliteHelp(context);
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

    public void addDoLookItem(DoLogProgreeSqliteVo vo) {
        if (empty()) return;
        DoLogProgreeSqliteVo look = findLookWithTidChapterId(vo.getChapterid(), vo.getKid(), vo.getType());
        if (look == null || StringUtil.isEmpty(look.getNumber())) {
            ContentValues values = setContentValues(vo);
            mSqLiteDatabase.insert(DataMessageVo.USER_INFOM_TABLE_UP_LOOK, null, values);
        } else {
            ContentValues values = setContentValues(vo);
            mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_UP_LOOK, values, "id=?",
                    new String[]{String.valueOf(look.getId())});
        }

    }


    public void deleteLookItem(int chapterid, int kid) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_INFOM_TABLE_UP_LOOK, "chapterid=? and kid=? ",
                new String[]{String.valueOf(chapterid), String.valueOf(kid)});
    }

    public void deleteLookItem(int id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_INFOM_TABLE_UP_LOOK, "id=?", new String[]{String.valueOf(id)});

    }

    /**
     * @param chapterid 章节id
     * @param kid       科id
     */
    public DoLogProgreeSqliteVo findLookWithTidChapterId(int chapterid, int kid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_UP_LOOK, null, "chapterid=? and kid=?  ",
                new String[]{String.valueOf(chapterid), String.valueOf(kid)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            DoLogProgreeSqliteVo vo = getContentValues(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * @param chapterid 章节id
     * @param kid       科id
     */
    public DoLogProgreeSqliteVo findLookWithTidChapterId(int chapterid, int kid, int type) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_UP_LOOK, null, "chapterid=? and kid=? and type=? ",
                new String[]{String.valueOf(chapterid), String.valueOf(kid), String.valueOf(type)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            DoLogProgreeSqliteVo vo = getContentValues(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    public List<DoLogProgreeSqliteVo> queryAllProgress() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_UP_LOOK,
                null, null, null, null,
                null, null, "500");
        mDbQueryUtil.initCursor(query);
        List<DoLogProgreeSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            DoLogProgreeSqliteVo contentValues = getContentValues(mDbQueryUtil);
            if (contentValues != null) {
                list.add(contentValues);
            }
        }
        query.close();
        return list;
    }

    /**
     * @param type     类型
     * @param courseid 科id
     * @return
     */
    public List<DoLogProgreeSqliteVo> queryAllProgress(int type, int courseid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_UP_LOOK,
                null, "type=? and kid=?",
                new String[]{String.valueOf(type), String.valueOf(courseid)}, null,
                null, null, "500");
        mDbQueryUtil.initCursor(query);
        List<DoLogProgreeSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            DoLogProgreeSqliteVo contentValues = getContentValues(mDbQueryUtil);
            if (contentValues != null) {
                list.add(contentValues);
            }
        }
        query.close();
        return list;
    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    private DoLogProgreeSqliteVo getContentValues(DbQueryUtil mDbQueryUtil) {
        DoLogProgreeSqliteVo vo = new DoLogProgreeSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        int textid = mDbQueryUtil.queryInt("textid");
        int type = mDbQueryUtil.queryInt("type");
        int kid = mDbQueryUtil.queryInt("kid");
        String userid = mDbQueryUtil.queryString("userid");
        String number = mDbQueryUtil.queryString("number");
        String allnumber = mDbQueryUtil.queryString("allnumber");
        vo.setChapterid(chapterid);

        vo.setType(type);
        vo.setId(id);
        vo.setKid(kid);
        vo.setAllNumber(allnumber);
        vo.setNumber(number);
        vo.setTextid(textid);
        vo.setUserid(userid);
        return vo;
    }

    private ContentValues setContentValues(DoLogProgreeSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("chapterid", vo.getChapterid());
        values.put("textid", vo.getTextid());
        values.put("kid", vo.getKid());
        values.put("userid", vo.getUserid());
        values.put("number", vo.getNumber());
        values.put("allnumber", vo.getAllNumber());
        values.put("type", vo.getType());
        return values;
    }

    private boolean empty() {
        if (mSqLiteDatabase == null)
            return true;
        if (mSqLiteDatabase.isReadOnly())
            return true;
        return false;
    }
}
