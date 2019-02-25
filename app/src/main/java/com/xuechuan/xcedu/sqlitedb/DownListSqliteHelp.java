package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DownSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 下载列表
 * @author: L-BackPacker
 * @date: 2018.12.11 上午 8:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DownListSqliteHelp {
    private static volatile DownListSqliteHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private DownListSqliteHelp(Context context) {
        this.mContext = context;
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static DownListSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (DownListSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new DownListSqliteHelp(context);
                }
            }
        }
        return _singleton;
    }

    public void initOpenUserInfom() {
        DbPathUitl instance = DbPathUitl.get_Instance(mContext);
        String dbPath = instance.getDbPath();
        if (StringUtil.isEmpty(dbPath)) return;
        String concat = dbPath.concat(DataMessageVo.USER_VIDEO_DOWN_TABLE);
        File file = new File(concat);
        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file, null,null);
        if (mSqLiteDatabase == null) {
            createtable();
        }
    }

    private SQLiteDatabase createtable() {
        UserInfomOpenHelp userInfomOpenHelp = new UserInfomOpenHelp(mContext);
        return userInfomOpenHelp.getWritableDatabase(DataMessageVo.SQLITEPASSWORD);
    }

    public void addDownItem(DownSqliteVo vo) {
        if (empty()) return;
        DownSqliteVo sqliteVo = findALLWithKidPidZid(vo.getKid(), vo.getPid(), vo.getZid());
        if (sqliteVo == null) {
            ContentValues values = new ContentValues();
            values.put("staffid", vo.getStaffid());
            values.put("kid", vo.getKid());
            values.put("urlImg", vo.getUrlImg());
            values.put("kName", vo.getkName());
            values.put("time", vo.getTime());
            values.put("pid", vo.getPid());
            values.put("pName", vo.getpName());
            values.put("zid", vo.getZid());
            values.put("vid", vo.getVid());
            values.put("VideoOid", vo.getVideoOid());
            values.put("bitRate", vo.getBitRate());
            values.put("duration", vo.getDuration());
            values.put("fileSize", vo.getFileSize());
            values.put("title", vo.getTitle());
            values.put("percent", vo.getPercent());
            values.put("total", vo.getTotal());
            values.put("status", vo.getStatus());
            values.put("psort", vo.getPsort());
            values.put("zsort", vo.getZsort());
            mSqLiteDatabase.insert(DataMessageVo.USER_VIDEO_DOWN_TABLE, null, values);
        } else {
            ContentValues values = new ContentValues();
            values.put("staffid", isEmptyDB(sqliteVo.getStaffid(), vo.getStaffid()));
            values.put("kid", isEmptyDB(sqliteVo.getKid(), vo.getKid()));
            values.put("urlImg", isEmptyDB(sqliteVo.getUrlImg(), vo.getUrlImg()));
            values.put("kName", isEmptyDB(sqliteVo.getkName(), vo.getkName()));
            values.put("time", isEmptyDB(sqliteVo.getTime(), vo.getTime()));
            values.put("pid", isEmptyDB(sqliteVo.getPid(), vo.getPid()));
            values.put("pName", isEmptyDB(sqliteVo.getpName(), vo.getpName()));
            values.put("zid", isEmptyDB(sqliteVo.getZid(), vo.getZid()));
            values.put("vid", isEmptyDB(sqliteVo.getVid(), vo.getVid()));
            values.put("VideoOid", isEmptyDB(sqliteVo.getVideoOid(), vo.getVideoOid()));
            values.put("bitRate", isEmptyDB(sqliteVo.getBitRate(), vo.getBitRate()));
            values.put("duration", isEmptyDB(sqliteVo.getDuration(), vo.getDuration()));
            values.put("fileSize", vo.getFileSize());
            values.put("title", isEmptyDB(sqliteVo.getTitle(), vo.getTitle()));
            values.put("percent", vo.getPercent());
            values.put("total", vo.getTotal());
            values.put("status", isEmptyDB(sqliteVo.getStatus(), vo.getStatus()));
            values.put("psort", isEmptyDB(sqliteVo.getPsort(), vo.getPsort()));
            values.put("zsort", isEmptyDB(sqliteVo.getZsort(), vo.getZsort()));
            mSqLiteDatabase.update(DataMessageVo.USER_VIDEO_DOWN_TABLE, values, "id=?",
                    new String[]{String.valueOf(sqliteVo.getId())});
        }

    }


    public DownSqliteVo findALLWithKidPidZid(String kidParamer, String PidParamer, String zidParamer) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_VIDEO_DOWN_TABLE, null, "kid=? AND pid=? and zid=?",
                new String[]{kidParamer, PidParamer, zidParamer}, null, null, null);
        mDbQueryUtil.initCursor(query);
        DownSqliteVo vo = new DownSqliteVo();
        if (query.moveToFirst()) {
            while (query.moveToNext()) {
                int id = mDbQueryUtil.queryInt("id");
                String staffid = mDbQueryUtil.queryString("staffid");
                String kid = mDbQueryUtil.queryString("kid");
                String urlImg = mDbQueryUtil.queryString("urlImg");
                String kName = mDbQueryUtil.queryString("kName");
                String time = mDbQueryUtil.queryString("time");
                String pid = mDbQueryUtil.queryString("pid");
                String pName = mDbQueryUtil.queryString("pName");
                String zid = mDbQueryUtil.queryString("zid");
                String vid = mDbQueryUtil.queryString("vid");
                String VideoOid = mDbQueryUtil.queryString("VideoOid");
                String bitRate = mDbQueryUtil.queryString("bitRate");
                String duration = mDbQueryUtil.queryString("duration");
                String title = mDbQueryUtil.queryString("title");
                Long fileSize = mDbQueryUtil.queryLong("fileSize");
                Long total = mDbQueryUtil.queryLong("total");
                String status = mDbQueryUtil.queryString("status");
                String psort = mDbQueryUtil.queryString("psort");
                String zsort = mDbQueryUtil.queryString("zsort");
                Long percent = mDbQueryUtil.queryLong("percent");
                vo.setId(id);
                vo.setStaffid(staffid);
                vo.setBitRate(bitRate);
                vo.setDuration(duration);
                vo.setFileSize(fileSize);
                vo.setKid(kid);
                vo.setkName(kName);
                vo.setPercent(percent);
                vo.setPid(pid);
                vo.setpName(pName);
                vo.setPsort(psort);
                vo.setStatus(status);
                vo.setTime(time);
                vo.setTitle(title);
                vo.setTotal(total);
                vo.setUrlImg(urlImg);
                vo.setVid(vid);
                vo.setVideoOid(VideoOid);
                vo.setZid(zid);
                vo.setZsort(zsort);

            }
        }
        return vo;

    }

    public void deleteZIItem(String kid, String zid) {
        if (StringUtil.isEmpty(kid) || StringUtil.isEmpty(zid)) return;
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_VIDEO_DOWN_TABLE, "kid=?and zid=?",
                new String[]{kid, zid});

    }

    public void deleteKitem(String kid, String pid, String zid) {
        if (StringUtil.isEmpty(kid) || StringUtil.isEmpty(pid) || StringUtil.isEmpty(zid)) {
            return;
        }
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_VIDEO_DOWN_TABLE, "kid=?andpid=?andzid=?",
                new String[]{kid, pid, zid});
    }

    public boolean isAdd(DownSqliteVo vo) {
        if (vo == null)
            return false;
        DownSqliteVo sqliteVo = findALLWithKidPidZid(vo.getKid(), vo.getPid(), vo.getZid());
        if (sqliteVo == null) return false;
        return true;

    }

    public void addUpDataItem(DownSqliteVo sqliteVo, long current, long total) {
        if (sqliteVo == null) return;
        DownSqliteVo zid = findALLWithKidPidZid(sqliteVo.getKid(), sqliteVo.getPid(), sqliteVo.getZid());
        if (zid != null) {
            ContentValues values = new ContentValues();
            if (current == 0) {
                values.put("status", "2");
            } else if (current == total) {
                values.put("status", "0");
            } else {
                values.put("status", "1");
            }
            values.put("percent", current);
            values.put("total", total);
            mSqLiteDatabase.update(DataMessageVo.USER_VIDEO_DOWN_TABLE, values, "id=?", new String[]{String.valueOf(sqliteVo.getId())});
        }

    }


    private boolean empty() {
        if (mSqLiteDatabase == null)
            return true;
        if (mSqLiteDatabase.isReadOnly())
            return true;
        return false;
    }

    private String isEmptyDB(String paramer, String paramers) {
        return StringUtil.isEmpty(paramers) ? paramer : paramers;
    }


}
