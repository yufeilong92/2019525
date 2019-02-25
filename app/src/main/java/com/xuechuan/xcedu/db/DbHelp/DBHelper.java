package com.xuechuan.xcedu.db.DbHelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easefun.polyvsdk.PolyvDevMountInfo;
import com.xuechuan.xcedu.db.DaoMaster;
import com.xuechuan.xcedu.db.DaoSession;
import com.xuechuan.xcedu.db.DownVideoDbDao;
import com.xuechuan.xcedu.db.UpDataNoteDb;
import com.xuechuan.xcedu.db.UpDataNoteDbDao;
import com.xuechuan.xcedu.db.UserInfomDbDao;
import com.xuechuan.xcedu.utils.FileUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.EncrypBaseVo;

import org.greenrobot.greendao.database.Database;

import java.io.File;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db.DbHelp
 * @Description: db帮助类
 * @author: L-BackPacker
 * @date: 2018/5/9 11:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DBHelper {
    private static final String TAG = "DBHelper";
    private static MyOpenHelper mHelper;
    private static SQLiteDatabase db;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    private static Context mContext;

    /**
     * 设置greenDao,在application的onCreate方法初始化
     */
    public static void initDb(Context context) {
        mContext = context;
        if (mHelper != null && mDaoSession != null) {
            return;
        }
        /**context 上下文
         * test-db 数据库名称
         */
        try {
            DatabaseContext databaseContext = new DatabaseContext(context);
            mHelper = new MyOpenHelper(databaseContext, "userinfom.db", null);
            db = mHelper.getReadableDatabase();
            mDaoMaster = new DaoMaster(db);
            mDaoSession = mDaoMaster.newSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static DaoSession getDaoSession() {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    public static int getDaoVersion() {
        return mDaoMaster.getSchemaVersion();
    }

    public static DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            //此处不可用 DaoMaster.DevOpenHelper, 那是开发辅助类，我们要自定义一个，方便升级
            try {
                DatabaseContext databaseContext = new DatabaseContext(mContext);
                mHelper = new MyOpenHelper(databaseContext, "userinfom.db", null);
                db = mHelper.getReadableDatabase();
                mDaoMaster = new DaoMaster(db);
                mDaoSession = mDaoMaster.newSession();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDaoMaster;
    }

    /**
     * 默认DaoMaster.OpenHelper，数据库升级删除之前所有的数据
     * 自定义MyOpenHelper，实际开发中，数据库升级要保存原先的数据
     */
    private static class MyOpenHelper extends DaoMaster.DevOpenHelper {

        public MyOpenHelper(Context context, String name) {
            super(context, name);
            mContext = context;
        }

        public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
            mContext = context;
        }

        @Override
        public void onCreate(Database db) {
            super.onCreate(db);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            super.onUpgrade(db, oldVersion, newVersion);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UpDataNoteDb noteDb = new UpDataNoteDb();
                    noteDb.setOldVersion(1);
                    noteDb.setNewVersion(2);
                    String data = "更新数据排序";
                    noteDb.setUpatanote(data);
                    noteDb.setIsupdata(false);
                    getDaoSession().getUpDataNoteDbDao().insert(noteDb);
                }
            }).start();


        }
    }

}
