package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.EncryptionUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.vo.UserInfomVo;

import net.sqlcipher.database.SQLiteDatabase;


/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 个人信息表操作类
 * @author: L-BackPacker
 * @date: 2018.12.10 上午 11:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserInfomDbHelp {
    private static volatile UserInfomDbHelp _singleton;
    private Context mContext;
    private final DbQueryUtil mQueryUtil;
    private SQLiteDatabase mSqLiteDatabase;

    private UserInfomDbHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mQueryUtil = DbQueryUtil.get_Instance();
    }

    public static UserInfomDbHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (UserInfomDbHelp.class) {
                if (_singleton == null) {
                    _singleton = new UserInfomDbHelp(context);
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


    public synchronized UserInfomSqliteVo findUserInfomVo() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_USER, null, null, null, null
                , null, null);
        mQueryUtil.initCursor(query);
        if (query.moveToFirst()) {
            UserInfomSqliteVo vo = getInfomVo(mQueryUtil);
            query.close();
            return vo;
        }
        query.close();
        return null;
    }

    public UserInfomSqliteVo getInfomVo(DbQueryUtil mQueryUtil) {
        UserInfomSqliteVo vo = new UserInfomSqliteVo();
        int id = mQueryUtil.queryInt("id");
        int id1 = mQueryUtil.queryInt("saffid");
        int copy = mQueryUtil.queryInt("copy");
        int questionversion = mQueryUtil.queryInt("questionversion");
        int bankversion = mQueryUtil.queryInt("bankversion");
        String userinfomvo = mQueryUtil.queryString("userinfomvo");
        String bankversionname = mQueryUtil.queryString("bankversionname");
        String moid = mQueryUtil.queryString("moid");
        String skillbook = mQueryUtil.queryString("skillbook");
        String colligatebook = mQueryUtil.queryString("colligatebook");
        String casebook = mQueryUtil.queryString("casebook");
        String showDayOrNight = mQueryUtil.queryString("showDayOrNight");
        String userNextGo = mQueryUtil.queryString("userNextGo");
        String token = mQueryUtil.queryString("token");
        String userphone = mQueryUtil.queryString("userphone");
        String username = mQueryUtil.queryString("username");
        String deletenumber = mQueryUtil.queryString("deletenumber");
        String fonesize = mQueryUtil.queryString("fontsize");
        String bankuptime = mQueryUtil.queryString("bankuptime");
        vo.setDeletenumber(deletenumber);
        vo.setBankuptime(bankuptime);
        vo.setFontSize(fonesize);
        vo.setId(id);
        vo.setSaffid(id1);
        vo.setCopy(copy);
        vo.setUserinfome(userinfomvo);
        vo.setMoid(moid);
        vo.setSkillbook(skillbook);
        vo.setColligatebook(colligatebook);
        vo.setCasebook(casebook);
        vo.setShowDayOrNight(showDayOrNight);
        vo.setUserNextGo(userNextGo);
        vo.setToken(token);
        vo.setUserPhone(userphone);
        vo.setUserName(username);
        vo.setQuestionversion(questionversion);
        vo.setBankversion(bankversion);
        vo.setBankversionname(bankversionname);
        return vo;
    }

    public synchronized void addUserInfom(UserInfomSqliteVo vo) {
        if (empty()) return;
        UserInfomSqliteVo userInfomVo = findUserInfomVo();
        if (userInfomVo == null || StringUtil.isEmpty(userInfomVo.getToken()) || StringUtil.isEmpty(userInfomVo.getUserinfome())) {
            ContentValues values = setValues(vo);
            mSqLiteDatabase.insert(DataMessageVo.USER_INFOM_TABLE_USER, null, values);

        } else {
            ContentValues values = setValuesCopy(userInfomVo, vo);
            mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_USER, values, "id=?",
                    new String[]{String.valueOf(userInfomVo.getId())});

        }


    }

    private ContentValues setValues(UserInfomSqliteVo vo) {
        String userBuy = EncryptionUtil.getInstance().getUserBuy(DataMessageVo.NOBUY);
        ContentValues values = new ContentValues();
        values.put("saffid", vo.getSaffid());
        values.put("copy", vo.getCopy());
        values.put("userinfomvo", vo.getUserinfome());
        values.put("moid", vo.getMoid());
        values.put("skillbook", StringUtil.isEmpty(vo.getSkillbook()) ? userBuy : vo.getSkillbook());
        values.put("colligatebook", StringUtil.isEmpty(vo.getSkillbook()) ? userBuy : vo.getColligatebook());
        values.put("casebook", StringUtil.isEmpty(vo.getSkillbook()) ? userBuy : vo.getCasebook());
        values.put("showDayOrNight", vo.getShowDayOrNight());
        values.put("userNextGo", vo.getUserNextGo());
        values.put("token", vo.getToken());
        values.put("userphone", vo.getUserPhone());
        values.put("username", vo.getUserName());
        values.put("questionversion", vo.getQuestionversion());
        values.put("bankversion", vo.getBankversion());
        values.put("bankversionname", vo.getBankversionname());
        values.put("deletenumber", StringUtil.isEmpty(vo.getDeletenumber()) ? "3" : vo.getDeletenumber());
        values.put("fontsize", vo.getFontSize());
        values.put("bankuptime", vo.getBankuptime());
        return values;
    }

    /**
     * @param userInfomVo 元数据
     * @param vo          要添加得数据
     * @return
     */
    private ContentValues setValuesCopy(UserInfomSqliteVo userInfomVo, UserInfomSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("saffid", vo.getSaffid());
        values.put("copy", vo.getCopy() == 0 ? userInfomVo.getCopy() : vo.getCopy());
        values.put("userinfomvo", StringUtil.isEmpty(vo.getUserinfome()) ? userInfomVo.getUserinfome() : vo.getUserinfome());
        values.put("moid", StringUtil.isEmpty(vo.getMoid()) ? userInfomVo.getMoid() : vo.getMoid());
        values.put("skillbook", StringUtil.isEmpty(vo.getSkillbook()) ? userInfomVo.getSkillbook() : vo.getSkillbook());
        values.put("colligatebook", StringUtil.isEmpty(vo.getColligatebook()) ? userInfomVo.getColligatebook() : vo.getColligatebook());
        values.put("casebook", StringUtil.isEmpty(vo.getCasebook()) ? userInfomVo.getCasebook() : vo.getCasebook());
        values.put("showDayOrNight", StringUtil.isEmpty(vo.getShowDayOrNight()) ? userInfomVo.getShowDayOrNight() : vo.getShowDayOrNight());
        values.put("userNextGo", StringUtil.isEmpty(vo.getUserNextGo()) ? userInfomVo.getUserNextGo() : vo.getUserNextGo());
        values.put("token", StringUtil.isEmpty(vo.getToken()) ? userInfomVo.getToken() : vo.getToken());
        values.put("userphone", StringUtil.isEmpty(vo.getUserPhone()) ? userInfomVo.getUserPhone() : vo.getUserPhone());
        values.put("username", StringUtil.isEmpty(vo.getUserName()) ? userInfomVo.getUserName() : vo.getUserName());
        values.put("questionversion", vo.getQuestionversion() == 0 ? userInfomVo.getQuestionversion() : vo.getQuestionversion());
        values.put("bankversion", vo.getBankversion() == 0 ? userInfomVo.getBankversion() : vo.getBankversion());
        values.put("bankversionname", StringUtil.isEmpty(vo.getBankversionname()) ? userInfomVo.getBankversionname() : vo.getBankversionname());
        values.put("deletenumber", StringUtil.isEmpty(vo.getDeletenumber()) ? userInfomVo.getDeletenumber() : vo.getDeletenumber());
        values.put("fontsize", StringUtil.isEmpty(vo.getFontSize()) ? userInfomVo.getFontSize() : vo.getFontSize());
        values.put("bankuptime", StringUtil.isEmpty(vo.getBankuptime()) ? userInfomVo.getBankuptime() : vo.getBankuptime());
        return values;
    }

    private String isEmptyDB(String paramer, String paramers) {
        return StringUtil.isEmpty(paramers) ? paramer : paramers;
    }

    public void UpDataUserToken(UserInfomVo.DataBean vo, String signtoken) {
        if (empty()) return;
        String s = EncryptionUtil.getInstance().putTContent(vo);
        if (StringUtil.isEmpty(s)) {
            s = null;
        }
        UserInfomSqliteVo infomVo = findUserInfomVo();
        String sql = "update " + DataMessageVo.USER_INFOM_TABLE_USER +
                " set userinfomvo='" + s
                + "', token='" + signtoken +
                "'  where id=" + infomVo.getId() + ";";
        mSqLiteDatabase.execSQL(sql);
    }

    /**
     * 更新版本号
     *
     * @param version
     */
    public synchronized void upDateAddQuestion(int version) {
        if (empty()) return;
        UserInfomSqliteVo vo = findUserInfomVo();
        String sql = "update " + DataMessageVo.USER_INFOM_TABLE_USER +
                " set questionversion=" + version + " where id=" + vo.getId() + ";";
        mSqLiteDatabase.execSQL(sql);
    }

    /**
     * 更新版本号
     *
     * @param version
     */
    public synchronized void upDateAddGrad(int version, String vername,String banktime) {
        if (empty()) return;
        UserInfomSqliteVo vo = findUserInfomVo();
        ContentValues values = new ContentValues();
        values.put("bankversion", version);
        values.put("bankversionname", vername);
        values.put("bankuptime", banktime);
        mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_USER, values, "id=?",
                new String[]{String.valueOf(vo.getId())});
    }

    /**
     * 更新用户是否购买
     *
     * @param skill 技术实务
     * @param col   综合能力
     * @param cases 案例分析
     */
    public synchronized void upDateBuy(String skill, String col, String cases) {
        if (empty()) return;
        UserInfomSqliteVo vo = findUserInfomVo();
        ContentValues values = new ContentValues();
        values.put("skillbook", skill);
        values.put("colligatebook", col);
        values.put("casebook", cases);
        mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_USER, values, "id=?"
                , new String[]{String.valueOf(vo.getId())});

    }

    /**
     * 更新用户模式
     *
     * @param day
     */
    public synchronized void upDataDayOrNightOrEye(String day) {
        if (empty()) return;
        UserInfomSqliteVo vo = findUserInfomVo();
        ContentValues values = new ContentValues();
        values.put("showDayOrNight", day);
        mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_USER, values, "id=?"
                , new String[]{String.valueOf(vo.getId())});
    }

    /**
     * 更新用户选择字体
     *
     * @param fone
     */
    public synchronized void upDataFoneSize(String fone) {
        if (empty()) return;
        UserInfomSqliteVo vo = findUserInfomVo();
        ContentValues values = new ContentValues();
        values.put("fontsize", fone);
        mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_USER, values, "id=?"
                , new String[]{String.valueOf(vo.getId())});
    }

    /**
     * 更新用户数据是否下一题
     */
    public synchronized void upDataUserNext() {
        if (empty()) return;
        UserInfomSqliteVo vo = findUserInfomVo();
        if (vo == null) return;
        ContentValues values = new ContentValues();
        values.put("userNextGo", "go");
        mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_USER, values, "id=?",
                new String[]{String.valueOf(vo.getId())});

    }

    /**
     * 删除用户是否下一步
     */
    public synchronized void delectUserNext() {
        if (empty()) return;
        UserInfomSqliteVo vo = findUserInfomVo();
        if (vo == null) return;
        ContentValues values = new ContentValues();
        values.put("userNextGo", "");
        mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_USER, values, "id=?",
                new String[]{String.valueOf(vo.getId())});
    }

    /**
     * 更新用户错误数
     *
     * @param number
     */
    public synchronized void upDataErrorNumber(String number) {
        if (empty()) return;
        UserInfomSqliteVo vo = findUserInfomVo();
        ContentValues values = new ContentValues();
        values.put("deletenumber", number);
        mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_USER, values, "id=?"
                , new String[]{String.valueOf(vo.getId())});
    }

    private boolean empty() {
        if (mSqLiteDatabase == null)
            return true;
        if (mSqLiteDatabase.isReadOnly())
            return true;
        return false;
    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

}
