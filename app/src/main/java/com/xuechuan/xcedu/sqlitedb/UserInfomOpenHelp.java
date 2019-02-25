package com.xuechuan.xcedu.sqlitedb;

import android.content.Context;

import com.xuechuan.xcedu.base.DataMessageVo;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 穿件本地数据库
 * @author: L-BackPacker
 * @date: 2018.12.10 上午 8:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserInfomOpenHelp extends SQLiteOpenHelper {


    private static volatile UserInfomOpenHelp _singleton;
    private Context mContext;

    public static UserInfomOpenHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (UserInfomOpenHelp.class) {
                if (_singleton == null) {
                    _singleton = new UserInfomOpenHelp(context);
                }
            }
        }
        return _singleton;
    }

    public UserInfomOpenHelp(Context context) {
        super(context, DataMessageVo.USER_INFOM_DATABASE_NAME, null, DataMessageVo.USER_INFOM_DATABASE_VERISON);
        SQLiteDatabase.loadLibs(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataMessageVo.kaoshi);
        db.execSQL(DataMessageVo.domockbank);
        db.execSQL(DataMessageVo.userinfom);
        db.execSQL(DataMessageVo.dolog);
        db.execSQL(DataMessageVo.dotextprogress);
        db.execSQL(DataMessageVo.error);
        db.execSQL(DataMessageVo.videolook);
        db.execSQL(DataMessageVo.delete);
        db.execSQL(DataMessageVo.question);
        db.execSQL(DataMessageVo.questionChapter);
        db.execSQL(DataMessageVo.questiontagrelagtion);
        db.execSQL(DataMessageVo.tag);
        db.execSQL(DataMessageVo.questionexamrelation);
        db.execSQL(DataMessageVo.doBank);
        db.execSQL(DataMessageVo.updobank);
        db.execSQL(DataMessageVo.collect_table);
        db.execSQL(DataMessageVo.updata_collect_table);
        db.execSQL(DataMessageVo.note_table);
        db.execSQL(DataMessageVo.updataprogress);
        db.execSQL(DataMessageVo.uperror);
        db.execSQL(DataMessageVo.submitlog);
        db.execSQL(DataMessageVo.updeleteerror);
        db.execSQL(DataMessageVo.updata_deletecollect_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
