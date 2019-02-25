package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.VideoLookSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

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
public class VideoSqlteHelp {
    private Context mContext;
    private static volatile VideoSqlteHelp _instance = null;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private VideoSqlteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static VideoSqlteHelp getInstance(Context context) {
        if (_instance == null) {
            synchronized (VideoSqlteHelp.class) {
                if (_instance == null) {
                    _instance = new VideoSqlteHelp(context);
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

    public void addVideoItem(VideoLookSqliteVo sqliteVo) {
        if (empty()) return;
        VideoLookSqliteVo vo = queryIsAdd(sqliteVo.getKid(), sqliteVo.getCharpterid());
        if (vo == null) {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.insert(DataMessageVo.USER_INFOM_TABLE_VIDEOLOOK, null, values);
        } else {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_VIDEOLOOK, values,
                    "id=?", new String[]{String.valueOf(vo.getId())});
        }
    }

    private synchronized VideoLookSqliteVo queryIsAdd(int course, int chapterid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_VIDEOLOOK, null,
                "courseid=? and chapterid=?", new String[]{String.valueOf(course),
                        String.valueOf(chapterid)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            VideoLookSqliteVo vo = getVideoLookVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    private synchronized ContentValues getContentValues(VideoLookSqliteVo sqliteVo) {
        ContentValues values = new ContentValues();
        values.put("chapterid", sqliteVo.getCharpterid());
        values.put("kid", sqliteVo.getKid());
        values.put("userid", sqliteVo.getUserid());
        values.put("videoid", sqliteVo.getVideoId());
        values.put("progres", sqliteVo.getProgres());
        return values;
    }

    private synchronized VideoLookSqliteVo getVideoLookVo(DbQueryUtil mDbQueryUtil) {
        VideoLookSqliteVo vo = new VideoLookSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        int courseid = mDbQueryUtil.queryInt("kid");
        String videoid = mDbQueryUtil.queryString("videoid");
        String userid = mDbQueryUtil.queryString("userid");
        int progres = mDbQueryUtil.queryInt("progres");
        vo.setId(id);
        vo.setCharpterid(chapterid);
        vo.setKid(courseid);
        vo.setProgres(progres);
        vo.setUserid(userid);
        vo.setVideoId(videoid);
        return vo;
    }

}
