package com.xuechuan.xcedu.vo.SqliteVo;

import com.xuechuan.xcedu.utils.EncryptionUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.UserInfomVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: 用户信息
 * @author: L-BackPacker
 * @date: 2018.12.10 上午 11:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserInfomSqliteVo {
    private int id;
    private int saffid;
    private int copy;
    private String userinfome;
    private UserInfomVo.DataBean userData;
    private String moid;
    private String skillbook;
    private String colligatebook;
    private String casebook;
    private String showDayOrNight;
    private String userNextGo;
    private String token;
    private String userPhone;
    private String userName;
    private int  questionversion;
    private int bankversion;
    private String bankversionname;
    private String deletenumber;
    private String fontSize;
    private String bankuptime;

    public String getBankuptime() {
        return bankuptime;
    }

    public void setBankuptime(String bankuptime) {
        this.bankuptime = bankuptime;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getDeletenumber() {
        return deletenumber;
    }

    public void setDeletenumber(String deletenumber) {
        this.deletenumber = deletenumber;
    }

    public int getBankversion() {
        return bankversion;
    }

    public void setBankversion(int bankversion) {
        this.bankversion = bankversion;
    }

    public String getBankversionname() {
        return bankversionname;
    }

    public void setBankversionname(String bankversionname) {
        this.bankversionname = bankversionname;
    }

    public int getQuestionversion() {
        return questionversion;
    }

    public void setQuestionversion(int questionversion) {
        this.questionversion = questionversion;
    }

    public UserInfomVo.DataBean getUserData() {
        if (!StringUtil.isEmpty(userinfome) && userData == null) {
            UserInfomVo.DataBean dataBean = EncryptionUtil.getInstance().getTContent(userinfome, UserInfomVo.DataBean.class);
            return dataBean;
        }
        return userData;
    }

    public void setUserData(UserInfomVo.DataBean userData) {
        this.userData = userData;
    }

    public int getSaffid() {
        return saffid;
    }

    public void setSaffid(int saffid) {
        this.saffid = saffid;
    }

    public int getCopy() {
        return copy;
    }

    public void setCopy(int copy) {
        this.copy = copy;
    }

    public String getUserinfome() {
        return userinfome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserinfome(String userinfome) {
        this.userinfome = userinfome;
    }

    public String getMoid() {
        return moid;
    }

    public void setMoid(String moid) {
        this.moid = moid;
    }

    public String getSkillbook() {
        return skillbook;
    }

    public void setSkillbook(String skillbook) {
        this.skillbook = skillbook;
    }

    public String getColligatebook() {
        return colligatebook;
    }

    public void setColligatebook(String colligatebook) {
        this.colligatebook = colligatebook;
    }

    public String getCasebook() {
        return casebook;
    }

    public void setCasebook(String casebook) {
        this.casebook = casebook;
    }

    public String getShowDayOrNight() {
        return showDayOrNight;
    }

    public void setShowDayOrNight(String showDayOrNight) {
        this.showDayOrNight = showDayOrNight;
    }

    public String getUserNextGo() {
        return userNextGo;
    }

    public void setUserNextGo(String userNextGo) {
        this.userNextGo = userNextGo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
