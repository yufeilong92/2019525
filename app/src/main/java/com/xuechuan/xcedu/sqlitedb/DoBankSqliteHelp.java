package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 做题帮助类(临时)
 * @author: L-BackPacker
 * @date: 2018.12.14 上午 10:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DoBankSqliteHelp {
    private static volatile DoBankSqliteHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private DoBankSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static DoBankSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (DoBankSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new DoBankSqliteHelp(context);
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
                mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_DO_BANK_TABLE, null, values);
            } else {
                ContentValues values = getContentCaseValues(qid, vo);
                mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_DO_BANK_TABLE, values, "id=?",
                        new String[]{String.valueOf(qid.getId())});
            }
        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
            e.printStackTrace();
        }
    }

    private ContentValues getContentCaseValues(DoBankSqliteVo old, DoBankSqliteVo newData) {
        ContentValues values = new ContentValues();
        values.put("question_id", newData.getQuestion_id() == 0 ? old.getQuestion_id() : newData.getQuestion_id());
        values.put("mockkeyid", newData.getQuestion_id() == 0 ? old.getMockkeyid() : newData.getMockkeyid());
        values.put("isright", newData.getIsright() == 0 ? old.getIsright() : newData.getIsright());
        values.put("isdo", newData.getIsDo() == 0 ? old.getIsDo() : newData.getIsDo());
        values.put("selectA", newData.getSelectA() == 0 ? old.getSelectA() : newData.getSelectA());
        values.put("selectB", newData.getSelectB() == 0 ? old.getSelectB() : newData.getSelectB());
        values.put("selectC", newData.getSelectC() == 0 ? old.getSelectC() : newData.getSelectC());
        values.put("selectD", newData.getSelectD() == 0 ? old.getSelectD() : newData.getSelectD());
        values.put("selectE", newData.getSelectE() == 0 ? old.getSelectE() : newData.getSelectE());
        values.put("questiontype", newData.getQuestiontype() == 0 ? old.getQuestiontype() : newData.getQuestiontype());
        values.put("chapterid", newData.getChapterid() == 0 ? old.getChapterid() : newData.getChapterid());
        values.put("courseid",newData.getCourseid()==0?old.getCourseid():newData.getCourseid());
        values.put("analysis",StringUtil.isEmpty(newData.getAnalysis())?old.getAnalysis(): newData.getAnalysis());
        values.put("parent_id",newData.getParent_id()==0?old.getParent_id():newData.getParent_id());
        values.put("child_id",newData.getChild_id()==0?old.getChild_id():newData.getChild_id());
        values.put("mos", StringUtil.isEmpty(newData.getMos())?old.getMos():newData.getMos());
        values.put("ismos",newData.getIsmos()==0?old.getIsmos():newData.getIsmos());
        values.put("time",StringUtil.isEmpty( newData.getTime())?old.getTime():newData.getTime());
        values.put("isanalysis",newData.getIsAnalySis()==0?old.getIsAnalySis():newData.getIsAnalySis());
        values.put("islook",newData.getIslook()==0?old.getIslook():newData.getIslook());
        return values;
    }

    /**
     * 只针对于自评类型
     *
     * @param vo
     */
    public void addDoBankItemOne(DoBankSqliteVo vo) {
        if (empty()) return;
        try {
            DoBankSqliteVo qid = queryWQid(vo.getQuestion_id());
            if (qid == null || qid.getIsDo() == 0) {
                ContentValues values = getContentValues(vo);
                mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_DO_BANK_TABLE, null, values);
            } else {
                ContentValues values = getUpDataContentValues(qid, vo);
                mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_DO_BANK_TABLE, values, "id=?",
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
    public ContentValues getUpDataContentValues(DoBankSqliteVo qid, DoBankSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("question_id", vo.getQuestion_id() == 0 ? qid.getQuestion_id() : vo.getQuestion_id());
        values.put("mockkeyid", vo.getMockkeyid() == 0 ? qid.getMockkeyid() : vo.getMockkeyid());
        values.put("isright", vo.getIsright() == 0 ? qid.getIsright() : vo.getIsright());
        values.put("analysis", StringUtil.isEmpty(vo.getAnalysis()) ? qid.getAnalysis() : vo.getAnalysis());
        values.put("isdo", vo.getIsDo() == 0 ? qid.getIsDo() : vo.getIsDo());
        values.put("selectA", vo.getSelectA());
        values.put("selectB", vo.getSelectB());
        values.put("selectC", vo.getSelectC());
        values.put("selectD", vo.getSelectD());
        values.put("selectE", vo.getSelectE());
        values.put("questiontype", vo.getQuestiontype() == 0 ? qid.getQuestiontype() : vo.getQuestiontype());
        values.put("chapterid", vo.getChapterid() == 0 ? qid.getChapterid() : vo.getChapterid());
        values.put("courseid", vo.getCourseid() == 0 ? qid.getCourseid() : vo.getCourseid());
        values.put("parent_id", vo.getParent_id() == 0 ? qid.getParent_id() : vo.getParent_id());
        values.put("child_id", vo.getChild_id() == 0 ? qid.getChild_id() : vo.getChild_id());
        values.put("mos", vo.getMos());
        values.put("ismos", vo.getIsmos() == 0);
        values.put("time", vo.getTime());
        values.put("isanalysis", vo.getIsAnalySis());
        values.put("islook", vo.getIslook() == 0 ? qid.getIslook() : vo.getIslook());
        return values;
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
        values.put("analysis", vo.getAnalysis());
        values.put("parent_id", vo.getParent_id());
        values.put("child_id", vo.getChild_id());
        values.put("mos", vo.getMos());
        values.put("ismos", vo.getIsmos());
        values.put("time", vo.getTime());
        values.put("isanalysis", vo.getIsAnalySis());
        values.put("islook", vo.getIslook());
        return values;
    }


    public List<DoBankSqliteVo> finDAllUserDoText() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DO_BANK_TABLE, null, null, null, null, null, null);
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
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_DO_BANK_TABLE, null, "question_id=?", new String[]{String.valueOf(qusstion_id)}
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

    public void deleteBankWithQuestid(int question_id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_DO_BANK_TABLE, "question_id=?", new String[]{String.valueOf(question_id)});

    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    public void delelteTable() {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_DO_BANK_TABLE, null, null);
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
