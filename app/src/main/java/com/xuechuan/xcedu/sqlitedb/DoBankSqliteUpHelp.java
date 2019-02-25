package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.UpdataOrDeleteVo;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 做题帮助类(用与上传)
 * @author: L-BackPacker
 * @date: 2018.12.14 上午 10:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DoBankSqliteUpHelp {
    private static volatile DoBankSqliteUpHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private DoBankSqliteUpHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static DoBankSqliteUpHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (DoBankSqliteUpHelp.class) {
                if (_singleton == null) {
                    _singleton = new DoBankSqliteUpHelp(context);
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
                mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_UP_DO_TABLE, null, values);
            } else {
                ContentValues values = getContentValues(vo);
                mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_UP_DO_TABLE, values, "id=?",
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
    public synchronized ContentValues getContentValues(DoBankSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("question_id", vo.getQuestion_id());
        values.put("isright", vo.getIsright());
        values.put("mockkeyid", vo.getMockkeyid());
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

    public synchronized ArrayList<DoBankSqliteVo> finDAllUserDoText() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_UP_DO_TABLE, null, null, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        ArrayList<DoBankSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            DoBankSqliteVo doBankVo = getDoBankVo(mDbQueryUtil);
            if (doBankVo != null) {
                list.add(doBankVo);
            }
        }
        query.close();
        return list;
    }


    public synchronized DoBankSqliteVo queryWQid(int qusstion_id) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_UP_DO_TABLE, null, "question_id=?", new String[]{String.valueOf(qusstion_id)}
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
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_UP_DO_TABLE, "question_id=?", new String[]{String.valueOf(question_id)});

    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    public void delelteTable() {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_UP_DO_TABLE, null, null);
    }

    public synchronized List<UpdataOrDeleteVo> queryAllDoBank() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_UP_DO_TABLE,
                new String[]{"id","question_id","isright"},
                null, null, null, null, "time desc","500");
        mDbQueryUtil.initCursor(query);
        List<UpdataOrDeleteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            UpdataOrDeleteVo vo = getUpDataOrDeleteVo(mDbQueryUtil);
            list.add(vo);
        }
        query.close();
        return list;

    }

    public void deleteItem(int id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_UP_DO_TABLE,
                "id=?", new String[]{String.valueOf(id)});
    }

    public synchronized DoBankSqliteVo getDoBankVo(DbQueryUtil mDbQueryUtil) {
        DoBankSqliteVo vo = new DoBankSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int question_id = mDbQueryUtil.queryInt("question_id");
        int mockkeyid = mDbQueryUtil.queryInt("mockkeyid");
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
        String analysis = mDbQueryUtil.queryString("analysis");
        int parent_id = mDbQueryUtil.queryInt("parent_id");
        int child_id = mDbQueryUtil.queryInt("child_id");
        int ismos = mDbQueryUtil.queryInt("ismos");
        String mos = mDbQueryUtil.queryString("mos");
        String time = mDbQueryUtil.queryString("time");
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

    private synchronized UpdataOrDeleteVo getUpDataOrDeleteVo(DbQueryUtil mDbQueryUtil) {
        UpdataOrDeleteVo vo = new UpdataOrDeleteVo();
        int id = mDbQueryUtil.queryInt("id");
        int question_id = mDbQueryUtil.queryInt("question_id");
        int isright = mDbQueryUtil.queryInt("isright");
        vo.setId(id);
        vo.setQuestion_id(question_id);
        vo.setRight(isright);
        return vo;
    }


}
