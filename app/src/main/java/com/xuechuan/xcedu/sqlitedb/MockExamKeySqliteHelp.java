package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.MockExamKeySqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 考试主键表
 * @author: L-BackPacker
 * @date: 2018.12.26 上午 10:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MockExamKeySqliteHelp {
    private static volatile MockExamKeySqliteHelp _singleton;
    private Context mContext;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private MockExamKeySqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static MockExamKeySqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (MockExamKeySqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new MockExamKeySqliteHelp(context);
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

    public void addMockExamItem(MockExamKeySqliteVo sqliteVo) {
        if (empty()) return;
        MockExamKeySqliteVo vo = queryIsAdd(sqliteVo.getSaffid(), sqliteVo.getTimekey());
        if (vo == null || vo.getTimekey() == 0) {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, null, values);
        }
    }

    public MockExamKeySqliteVo quesryMockKeyVo(long time) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, null, "timekey=?",
                new String[]{String.valueOf(time)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            MockExamKeySqliteVo vo = getMockExamVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    public List<MockExamKeySqliteVo> queryMockExamList() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, null, null, null, null,
                null, " timekey DESC");
        mDbQueryUtil.initCursor(query);
        List<MockExamKeySqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            MockExamKeySqliteVo vo = getMockExamVo(mDbQueryUtil);
            if (vo != null) {
                list.add(vo);
            }
        }
        query.close();
        return list;
    }

    public void delectMockExamItem(int id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, "id=?", new String[]{String.valueOf(id)});
    }

    private MockExamKeySqliteVo queryIsAdd(String saffid, long timekey) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, null, "timekey=? and saffid=?",
                new String[]{String.valueOf(saffid), String.valueOf(timekey)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            MockExamKeySqliteVo vo = getMockExamVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    public ContentValues getContentValues(MockExamKeySqliteVo sqliteVo) {
        ContentValues values = new ContentValues();
        values.put("timekey", sqliteVo.getTimekey());
        values.put("saffid", sqliteVo.getSaffid());
        values.put("isDo", sqliteVo.getIsDo());
        return values;
    }

    public MockExamKeySqliteVo getMockExamVo(DbQueryUtil mDbQueryUtil) {
        MockExamKeySqliteVo sqliteVo = new MockExamKeySqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        Long timekey = mDbQueryUtil.queryLong("timekey");
        String saffid = mDbQueryUtil.queryString("saffid");
        int isDo = mDbQueryUtil.queryInt("isDo");
        sqliteVo.setId(id);
        sqliteVo.setIsDo(isDo);
        sqliteVo.setTimekey(timekey);
        sqliteVo.setSaffid(saffid);
        return sqliteVo;
    }

}
