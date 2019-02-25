package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ErrorSqlteHelp.java
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 用户做题记录表（用于记录正确数）
 * @author: YFL
 * @date: 2018/12/25 22:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/12/25 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class DoKaoShiSqlteHelp {
    private Context mContext;
    private static volatile DoKaoShiSqlteHelp _instance = null;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private DoKaoShiSqlteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static DoKaoShiSqlteHelp getInstance(Context context) {
        if (_instance == null) {
            synchronized (DoKaoShiSqlteHelp.class) {
                if (_instance == null) {
                    _instance = new DoKaoShiSqlteHelp(context);
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

    public void addDoLogItem(DoBankSqliteVo sqliteVo) {
        if (empty()) return;
        DoBankSqliteVo vo = queryIsAdd(sqliteVo.getCourseid(), sqliteVo.getChapterid(), sqliteVo.getQuestion_id());
        if (vo == null) {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_DOTEXT_TABLE, null, values);
        } else {
            ContentValues values = getContentValues(sqliteVo);
            mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_DOTEXT_TABLE, values, "id=?",
                    new String[]{String.valueOf(vo.getId())});
        }

    }

    //根据科目获取用户做题数据
    public ArrayList<DoBankSqliteVo> queryList(int courseid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOTEXT_TABLE, null,
                "courseid=?", new String[]{String.valueOf(courseid)}, null, null,
                null);
        mDbQueryUtil.initCursor(query);
        ArrayList<DoBankSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            DoBankSqliteVo vo = getDoBankVo(mDbQueryUtil);
            list.add(vo);
        }
        query.close();
        return list;
    }

    public DoBankSqliteVo queryIsAdd(int courseid, int chapterid, int questionid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOTEXT_TABLE, null,
                "courseid=? and chapterid=? and question_id=?",
                new String[]{String.valueOf(courseid), String.valueOf(chapterid), String.valueOf(questionid)},
                null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            DoBankSqliteVo vo = getDoBankVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    private ContentValues getContentValues(DoBankSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("question_id", vo.getQuestion_id());
        values.put("mockkeyid", vo.getMockkeyid());
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
        values.put("analysis", vo.getAnalysis());
        values.put("parent_id", vo.getParent_id());
        values.put("child_id", vo.getChild_id());
        values.put("mos", vo.getMos());
        values.put("ismos", vo.getIsmos());
        values.put("time", vo.getTime());
        values.put("isanalysis", vo.getIsAnalySis());
        values.put("islook",vo.getIslook());
        return values;
    }


    public DoBankSqliteVo getDoBankVo(DbQueryUtil mDbQueryUtil) {
        DoBankSqliteVo vo = new DoBankSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int question_id = mDbQueryUtil.queryInt("question_id");
        int isdo = mDbQueryUtil.queryInt("isdo");
        int mockkeyid = mDbQueryUtil.queryInt("mockkeyid");
        int selectA = mDbQueryUtil.queryInt("selectA");
        int selectB = mDbQueryUtil.queryInt("selectB");
        int selectC = mDbQueryUtil.queryInt("selectC");
        int selectD = mDbQueryUtil.queryInt("selectD");
        int selectE = mDbQueryUtil.queryInt("selectE");
        int isright = mDbQueryUtil.queryInt("isright");
        int courseid = mDbQueryUtil.queryInt("courseid");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        int questiontype = mDbQueryUtil.queryInt("questiontype");
        String analysis = mDbQueryUtil.queryString("analysis");
        int parent_id = mDbQueryUtil.queryInt("parent_id");
        int child_id = mDbQueryUtil.queryInt("child_id");
        int ismos = mDbQueryUtil.queryInt("ismos");
        int islook = mDbQueryUtil.queryInt("islook");
        String mos = mDbQueryUtil.queryString("mos");
        String time = mDbQueryUtil.queryString("time");
        int isanalysis = mDbQueryUtil.queryInt("isanalysis");
        vo.setIsAnalySis(isanalysis);
        vo.setIslook(islook);
        vo.setTime(time);
        vo.setParent_id(parent_id);
        vo.setChild_id(child_id);
        vo.setIsmos(ismos);
        vo.setMos(mos);
        vo.setAnalysis(analysis);
        vo.setId(id);
        vo.setMockkeyid(mockkeyid);
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
