package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 做题帮助类（本地）
 * @author: L-BackPacker
 * @date: 2018.12.14 上午 10:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DoMockBankSqliteHelp {
    private static volatile DoMockBankSqliteHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private DoMockBankSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static DoMockBankSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (DoMockBankSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new DoMockBankSqliteHelp(context);
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

    private boolean empty() {
        if (mSqLiteDatabase == null)
            return true;
        if (mSqLiteDatabase.isReadOnly())
            return true;
        return false;
    }

    public void addDoBankItem(DoBankSqliteVo vo) {
        if (empty()) return;
        try {
            DoBankSqliteVo qid = queryWQid(vo.getQuestion_id());
            if (qid == null || qid.getIsDo() == 0) {
                ContentValues values = getContentValues(vo);
                mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_DOLOG_MOCKEXAM_TABLE, null, values);
            } else {
                ContentValues values = getContentValues(vo);
                mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_DOLOG_MOCKEXAM_TABLE, values, "id=?",
                        new String[]{String.valueOf(qid.getId())});
            }
        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
            e.printStackTrace();
        }
    }

    @NonNull
    public ContentValues getContentValues(DoBankSqliteVo vo) {
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
        values.put("parent_id", vo.getParent_id());
        values.put("child_id", vo.getChild_id());
        values.put("mos", vo.getMos());
        values.put("ismos", vo.getIsmos());
        values.put("time", vo.getTime());
        values.put("analysis", vo.getAnalysis());
        values.put("isanalysis", vo.getIsAnalySis());
        return values;
    }

    public List<DoBankSqliteVo> finDAllUserDoText() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOLOG_MOCKEXAM_TABLE, null, null, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        List<DoBankSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            DoBankSqliteVo doBankVo = getDoBankVo(mDbQueryUtil);
            if (doBankVo != null) {
                list.add(doBankVo);
            }
        }
        query.close();
        return list;
    }


    public DoBankSqliteVo queryWQid(int qusstion_id) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOLOG_MOCKEXAM_TABLE, null, "question_id=?", new String[]{String.valueOf(qusstion_id)}
                , null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            DoBankSqliteVo vo = getDoBankVo(mDbQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    /**
     * 查询用户下面所有错题
     *
     * @param courseid
     * @return
     */
    public List<DoBankSqliteVo> queryErrorListWithCourseId(String courseid) {
        List<DoBankSqliteVo> list = new ArrayList<>();
        if (empty()) return list;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DOLOG_MOCKEXAM_TABLE, null, "courseid=?",
                new String[]{String.valueOf(courseid)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            DoBankSqliteVo vo = getDoBankVo(mDbQueryUtil);
            list.add(vo);
        }
        query.close();
        return list;

    }

    public void deleteBankWithQuestid(int question_id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_DOLOG_MOCKEXAM_TABLE, "question_id=?", new String[]{String.valueOf(question_id)});

    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    /**
     * 获取用户做题记录
     *
     * @param coursedid
     * @return
     */
    public int queryCountWithCourseid(int coursedid) {
        if (empty()) return 0;
        String sql = "select count(*) from " + DataMessageVo.USER_QUESTION_DOLOG_MOCKEXAM_TABLE + "  where courseid=" + coursedid;
        SQLiteStatement statement = mSqLiteDatabase.compileStatement(sql);
        long l = statement.simpleQueryForLong();
        return (int) l;
    }

    /**
     * 获取错题集合
     *
     * @param courseid
     * @return
     */
    public int queryRightCountWithCourseId(int courseid) {
        if (empty()) return 0;
        String sql = "select count(*) from " + DataMessageVo.USER_QUESTION_DOLOG_MOCKEXAM_TABLE + "  where courseid=" + courseid
                + " and isright=1";
        SQLiteStatement statement = mSqLiteDatabase.compileStatement(sql);
        long l = statement.simpleQueryForLong();

        return (int) l;
    }


    public void delelteTable() {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_DOLOG_MOCKEXAM_TABLE, null, null);
    }

    public synchronized DoBankSqliteVo getDoBankVo(DbQueryUtil mDbQueryUtil) {
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
        String time = mDbQueryUtil.queryString("time");
        String analysis = mDbQueryUtil.queryString("analysis");
        int parent_id = mDbQueryUtil.queryInt("parent_id");
        int child_id = mDbQueryUtil.queryInt("child_id");
        int ismos = mDbQueryUtil.queryInt("ismos");
        String mos = mDbQueryUtil.queryString("mos");
        int isanalysis = mDbQueryUtil.queryInt("isanalysis");
        int islook = mDbQueryUtil.queryInt("islook");
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
