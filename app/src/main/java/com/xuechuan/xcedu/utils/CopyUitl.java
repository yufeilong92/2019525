package com.xuechuan.xcedu.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.vo.DownVideoDbVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.vo.UserInfomVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 数据拷贝
 * @author: L-BackPacker
 * @date: 2018.12.08 上午 11:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CopyUitl {

    private volatile static CopyUitl _instance;

    private CopyUitl() {
    }

    public static CopyUitl get_Instance() {
        if (_instance == null) {
            synchronized (CopyUitl.class) {
                if (_instance == null) {
                    _instance = new CopyUitl();
                }
            }
        }
        return _instance;
    }

    /**
     * 拷贝数据
     *
     * @param vo
     * @param dbvo 要添加数据
     */
    public void copy(DownVideoDb vo, DownVideoDbVo dbvo) {
        dbvo.setkName(vo.getKName());
        dbvo.setDownlist(vo.getDownlist());
        dbvo.setId(vo.getId());
        dbvo.setKid(vo.getKid());
        dbvo.setStaffid(vo.getStaffid());
        dbvo.setTime(vo.getTime());
        dbvo.setUrlImg(vo.getUrlImg());
        dbvo.setVid(vo.getVid());
    }

    /**
     * 还原拷贝数据
     *
     * @param vo
     * @param dbvo 要添加数据
     */
    public void copy(DownVideoDbVo vo, DownVideoDb dbvo) {
        dbvo.setKName(vo.getkName());
        dbvo.setDownlist(vo.getDownlist());
        dbvo.setId(vo.getId());
        dbvo.setKid(vo.getKid());
        dbvo.setStaffid(vo.getStaffid());
        dbvo.setTime(vo.getTime());
        dbvo.setUrlImg(vo.getUrlImg());
        dbvo.setVid(vo.getVid());
    }


    public UserInfomSqliteVo setCopyUserInfom(UserInfomDb dbInfom) {
        EncryptionUtil util = EncryptionUtil.getInstance();
        UserInfomSqliteVo sqliteVo = new UserInfomSqliteVo();
        sqliteVo.setToken(dbInfom.getToken());
        sqliteVo.setUserNextGo(String.valueOf(dbInfom.getUserNextGo()));
        sqliteVo.setShowDayOrNight(dbInfom.getShowDayOrNight());
        sqliteVo.setColligatebook(String.valueOf(dbInfom.getColligateBook()));
        sqliteVo.setCasebook(String.valueOf(dbInfom.getCaseBook()));
        sqliteVo.setSkillbook(String.valueOf(dbInfom.getSkillBook()));
        String s = util.putTContent(dbInfom.getVo().getData());
        Log.e("=========", "加密: " + s);
        sqliteVo.setCopy(1);
        sqliteVo.setUserinfome(StringUtil.isEmpty(s) ? null : s);
        return sqliteVo;
    }

    public UserInfomSqliteVo setCopyUserInfom(UserInfomVo.DataBean dbInfom) {
        EncryptionUtil util = EncryptionUtil.getInstance();
        UserInfomSqliteVo sqliteVo = new UserInfomSqliteVo();
        sqliteVo.setSaffid(dbInfom.getUser().getId());
        sqliteVo.setToken(dbInfom.getUser().getToken());
        String s = util.putTContent(dbInfom);
        Log.e("=========", "加密: " + s);
        sqliteVo.setCopy(1);
        sqliteVo.setUserinfome(StringUtil.isEmpty(s) ? null : s);
        return sqliteVo;
    }

    public UserInfomSqliteVo setCopyUserInfom(UserInfomDb dbInfom, String userPhone, String userpaw) {
        EncryptionUtil util = EncryptionUtil.getInstance();
        UserInfomSqliteVo sqliteVo = new UserInfomSqliteVo();
        sqliteVo.setToken(dbInfom.getToken());
        sqliteVo.setUserNextGo(String.valueOf(dbInfom.getUserNextGo()));
        sqliteVo.setShowDayOrNight(dbInfom.getShowDayOrNight());
        sqliteVo.setColligatebook(String.valueOf(dbInfom.getColligateBook()));
        sqliteVo.setCasebook(String.valueOf(dbInfom.getCaseBook()));
        sqliteVo.setSkillbook(String.valueOf(dbInfom.getSkillBook()));
        String s = util.putTContent(dbInfom.getVo());
        sqliteVo.setCopy(1);
        sqliteVo.setUserinfome(s);
        sqliteVo.setUserName(userpaw);
        sqliteVo.setUserPhone(userPhone);
        return sqliteVo;
    }

}
