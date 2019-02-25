package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ErrorSqlteHelp.java
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 笔记表
 * @author: YFL
 * @date: 2018/12/25 22:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/12/25 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class UpDoBankSqlteHelp {
    private Context mContext;
    private static volatile UpDoBankSqlteHelp _instance = null;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private UpDoBankSqlteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static UpDoBankSqlteHelp getInstance(Context context) {
        if (_instance == null) {
            synchronized (UpDoBankSqlteHelp.class) {
                if (_instance == null) {
                    _instance = new UpDoBankSqlteHelp(context);
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

    public void addDoBankItem(DoBankSqliteVo sqliteVo) {
        if (empty()) return;
        ContentValues values = getContentValues(sqliteVo);
        mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_UP_DO_TABLE, null, values);
    }

    public void delectDoBankItem(int id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_UP_DO_TABLE, "id=?",
                new String[]{String.valueOf(id)});
    }

    public ContentValues getContentValues(DoBankSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("question_id", vo.getQuestion_id());
        values.put("isright", vo.getIsright());
        values.put("isdo", vo.getIsDo());
        values.put("selectA", vo.getSelectA());
        values.put("selectB", vo.getSelectB());
        values.put("selectC", vo.getSelectC());
        values.put("selectD", vo.getSelectD());
        values.put("selectE", vo.getSelectE());
        values.put("questiontype", vo.getQuestiontype());
        values.put("chapterid", vo.getChapterid());
        values.put("courseid", vo.getCourseid());
        return values;
    }

    public DoBankSqliteVo getDoBankVo(DbQueryUtil mDbQueryUtil) {
        DoBankSqliteVo vo = new DoBankSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int question_id = mDbQueryUtil.queryInt("question_id");
        int isdo = mDbQueryUtil.queryInt("isdo");
        int selectA = mDbQueryUtil.queryInt("selectA");
        int selectB = mDbQueryUtil.queryInt("selectB");
        int selectC = mDbQueryUtil.queryInt("selectC");
        int selectD = mDbQueryUtil.queryInt("selectD");
        int selectE = mDbQueryUtil.queryInt("selectE");
        int isright = mDbQueryUtil.queryInt("isright");
        int courseid = mDbQueryUtil.queryInt("courseid");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        int questiontype = mDbQueryUtil.queryInt("questiontype");
        vo.setId(id);
        vo.setChapterid(chapterid);
        vo.setCourseid(courseid);
        vo.setQuestiontype(questiontype);
        vo.setIsDo(isdo);
        vo.setIsright(isright);
        vo.setQuestion_id(question_id);
        vo.setSelectA(selectA);
        vo.setSelectB(selectB);
        vo.setSelectC(selectC);
        vo.setSelectD(selectD);
        vo.setSelectE(selectE);
        return vo;
    }
}
