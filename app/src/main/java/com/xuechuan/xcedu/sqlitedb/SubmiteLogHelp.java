package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.SubmitLogVo;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 上午 10:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class SubmiteLogHelp {
    private static volatile SubmiteLogHelp _singleton;
    private Context mContext;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private SubmiteLogHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static SubmiteLogHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (SubmiteLogHelp.class) {
                if (_singleton == null) {
                    _singleton = new SubmiteLogHelp(context);
                }
            }
        }
        return _singleton;
    }

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

    public SubmitLogVo queryAllItem() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_SUBMIT_LOG, null, null, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            SubmitLogVo vo = getSubmitVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    public void addItem(SubmitLogVo logVo) {
        if (empty()) return;
        SubmitLogVo vo = queryIsAdd(logVo.getSaffid());
        if (vo == null) {
            ContentValues values = getContenValues(logVo);
            mSqLiteDatabase.insert(DataMessageVo.USER_INFOM_TABLE_SUBMIT_LOG, null, values);
        } else {
            ContentValues values = getContenValues(vo, logVo);
            mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_SUBMIT_LOG, values, "id=?"
                    , new String[]{String.valueOf(vo.getId())});
        }
    }

    public SubmitLogVo queryIsAdd(int saffid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_SUBMIT_LOG, null,
                "saffid=? ", new String[]{String.valueOf(saffid)}, null
                , null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            SubmitLogVo vo = getSubmitVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }


    private ContentValues getContenValues(SubmitLogVo logVo) {
        ContentValues values = new ContentValues();
        values.put("saffid", logVo.getSaffid());
        values.put("dobanktime", logVo.getDobanktime());
        values.put("errortime", logVo.getErrortime());
        values.put("collecttime", logVo.getCollecttime());
        values.put("progresstime", logVo.getProgresstime());
        values.put("error", logVo.getError());
        values.put("collect", logVo.getCollect());
        return values;
    }

    private ContentValues getContenValues(SubmitLogVo vo, SubmitLogVo logVo) {
        ContentValues values = new ContentValues();
        values.put("saffid", logVo.getSaffid() == 0 ? vo.getSaffid() : logVo.getSaffid());
        values.put("dobanktime", StringUtil.isEmpty(logVo.getDobanktime()) ? vo.getDobanktime() : logVo.getDobanktime());
        values.put("errortime", StringUtil.isEmpty(logVo.getErrortime()) ? vo.getErrortime() : logVo.getErrortime());
        values.put("collecttime", StringUtil.isEmpty(logVo.getCollecttime()) ? vo.getCollecttime() : logVo.getCollecttime());
        values.put("progresstime", StringUtil.isEmpty(logVo.getProgresstime()) ? vo.getProgresstime() : logVo.getProgresstime());
        values.put("error", logVo.getError() == 0 ? vo.getError() : logVo.getError());
        values.put("collect", logVo.getCollect() == 0 ? vo.getCollect() : logVo.getCollect());
        return values;
    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    private SubmitLogVo getSubmitVo(DbQueryUtil mDbQueryUtil) {
        SubmitLogVo vo = new SubmitLogVo();
        int saffid = mDbQueryUtil.queryInt("saffid");
        int id = mDbQueryUtil.queryInt("id");
        String dobanktime = mDbQueryUtil.queryString("dobanktime");
        String errortime = mDbQueryUtil.queryString("errortime");
        String collecttime = mDbQueryUtil.queryString("collecttime");
        String progresstime = mDbQueryUtil.queryString("progresstime");
        int error = mDbQueryUtil.queryInt("error");
        int collect = mDbQueryUtil.queryInt("collect");
        vo.setId(id);
        vo.setError(error);
        vo.setCollect(collect);
        vo.setSaffid(saffid);
        vo.setCollecttime(collecttime);
        vo.setDobanktime(dobanktime);
        vo.setErrortime(errortime);
        vo.setProgresstime(progresstime);
        return vo;
    }
}
