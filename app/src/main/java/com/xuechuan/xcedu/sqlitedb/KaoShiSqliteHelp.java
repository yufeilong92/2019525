package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.KaoShiSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 考试管连表
 * @author: L-BackPacker
 * @date: 2019.01.15 下午 1:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class KaoShiSqliteHelp {
    private static volatile KaoShiSqliteHelp _singleton;
    private Context mContext;
    private final SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private KaoShiSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static KaoShiSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (KaoShiSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new KaoShiSqliteHelp(context);
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

    public void addExamLogItem(KaoShiSqliteVo sqliteVo) {
        if (empty()) return;
        KaoShiSqliteVo vo = queryIsAdd(sqliteVo.getTimekey());
        if (vo == null || sqliteVo.getTimekey() == 0) {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, null, values);
        } else {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, values,
                    "id=?", new String[]{String.valueOf(vo.getId())});
        }
    }

    public List<KaoShiSqliteVo> queryAllItem() {
        List<KaoShiSqliteVo> lists = new ArrayList<>();
        if (empty()) return lists;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, null, null, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            KaoShiSqliteVo vo = getKaoShiVo(mDbQueryUtil);
            lists.add(vo);
        }
        query.close();
        return lists;
    }

    public void deleteItem(String time) {
        if (empty()) return;
        if (StringUtil.isEmpty(time)) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, "timekey=?",
                new String[]{String.valueOf(time)});
    }

    private KaoShiSqliteVo queryIsAdd(int timekey) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOTEXT_MOCKEXAM_TABLE, null, "timekey=?",
                new String[]{String.valueOf(timekey)}, null, null, null);

        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            KaoShiSqliteVo vo = getKaoShiVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    private KaoShiSqliteVo getKaoShiVo(DbQueryUtil mDbQueryUtil) {
        KaoShiSqliteVo vo = new KaoShiSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int timekey = mDbQueryUtil.queryInt("timekey");
        String saffid = mDbQueryUtil.queryString("saffid");
        int isDo = mDbQueryUtil.queryInt("isDo");
        int chapter_id = mDbQueryUtil.queryInt("chapter_id");
        int usetime = mDbQueryUtil.queryInt("usetime");
        double socre = mDbQueryUtil.querydouble("socre");
        vo.setChapter_id(chapter_id);
        vo.setSocre(socre);
        vo.setUsertime(usetime);
        vo.setId(id);
        vo.setTimekey(timekey);
        vo.setIsDo(isDo);
        vo.setSaffid(saffid);
        return vo;
    }

    private ContentValues getContentValues(KaoShiSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("timekey", vo.getTimekey());
        values.put("saffid", vo.getSaffid());
        values.put("isDo", vo.getIsDo());
        values.put("chapter_id", vo.getChapter_id());
        values.put("usetime", vo.getUsertime());
        values.put("socre", vo.getSocre());
        return values;
    }
}
