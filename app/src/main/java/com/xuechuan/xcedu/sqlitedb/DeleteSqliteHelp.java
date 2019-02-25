package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DeleteSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 删除表帮助类。。
 * @author: L-BackPacker
 * @date: 2018.12.11 上午 11:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DeleteSqliteHelp {
    private static volatile DeleteSqliteHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private DeleteSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static DeleteSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (DeleteSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new DeleteSqliteHelp(context);
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

    public void init(SQLiteDatabase database) {
        mSqLiteDatabase = database;
    }

    /**
     * 添加数据
     *
     * @param vo
     */
    public synchronized void addItemDelete(DeleteSqliteVo vo) {
        if (vo == null) return;
        if (empty()) return;
        try {
//            mSqLiteDatabase.beginTransaction();
            if (queryDeleteVo(vo.getType(), vo.getId())) return;
            ContentValues values = setContentValues(vo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, values);
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
     * 批量差插入
     *
     * @param list
     */
    public void addListDelete(ArrayList<DeleteSqliteVo> list) {
        if (empty()) return;
        try {
//            mSqLiteDatabase.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                DeleteSqliteVo vo = list.get(i);
                if (queryDeleteVo(vo.getType(), vo.getId())) continue;
                ContentValues values = setContentValues(vo);
                mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, values);
            }
//            mSqLiteDatabase.setTransactionSuccessful();
//            mSqLiteDatabase.endTransaction();
//            mSqLiteDatabase.close();
        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
            e.printStackTrace();
        }
    }

    public List<DeleteSqliteVo> queryAllQuestion() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, null, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        List<DeleteSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            DeleteSqliteVo vo = getDeleteValue(mDbQueryUtil, true);
            list.add(vo);
        }
        query.close();
        return list;
    }

    public void deleteTable() {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, null);
    }

    /**
     * 查询用户数据
     *
     * @param type
     * @param delete_id
     * @return
     */
    public boolean queryDeleteVo(String type, int delete_id) {
        if (empty()) return false;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, "delete_id=? and type=?",
                new String[]{String.valueOf(delete_id), type}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            return true;
        }
        return false;
    }

    public static ContentValues setContentValues(DeleteSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("delete_id", vo.getDelect_id());
        values.put("type", vo.getType());
        return values;
    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    /**
     * 更新数据
     *
     * @param vo
     */
    public void updataItemDelete(DeleteSqliteVo vo) {
        if (vo == null) return;
        if (empty()) return;
        String sql = "update " + DataMessageVo.USER_QUESTION_TABLE_DELETE +
                " set type='" + vo.getType() + "' where id =" + vo.getId() + ";";
        mSqLiteDatabase.execSQL(sql);
    }

    public DeleteSqliteVo findItemDeleteWithId(DeleteSqliteVo vo) {
        if (empty()) return null;
        if (vo == null) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, "id=?"
                , new String[]{String.valueOf(vo.getId())}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            DeleteSqliteVo sqliteVo = new DeleteSqliteVo();
            int id = mDbQueryUtil.queryInt("id");
            String type = mDbQueryUtil.queryString("type");
            sqliteVo.setId(id);
            sqliteVo.setType(type);
            query.close();
            return sqliteVo;
        }
        query.close();
        return null;

    }

    public ArrayList<DeleteSqliteVo> findItemDeleteAll() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_TABLE_DELETE, null, null, null, null
                , null, null);
        mDbQueryUtil.initCursor(query);
        ArrayList<DeleteSqliteVo> sqliteVos = new ArrayList<>();
        if (query.moveToNext()) {
            DeleteSqliteVo vo = getDeleteValue(mDbQueryUtil, true);
            sqliteVos.add(vo);
        }
        query.close();
        return sqliteVos;

    }

    /**
     * @param mDbQueryUtil
     * @param loacd        是否本地
     * @return
     */
    public synchronized static final DeleteSqliteVo getDeleteValue(DbQueryUtil mDbQueryUtil, boolean loacd) {
        DeleteSqliteVo vo = new DeleteSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        if (loacd) {
            int deletet_id = mDbQueryUtil.queryInt("deletet_id");
            vo.setDelect_id(deletet_id);
            vo.setId(id);
        } else {
            vo.setDelect_id(id);
        }
        String type = mDbQueryUtil.queryString("type");
        vo.setType(type);
        return vo;
    }

    private boolean empty() {
        if (mSqLiteDatabase == null)
            return true;
        if (mSqLiteDatabase.isReadOnly())
            return true;
        return false;
    }


}
