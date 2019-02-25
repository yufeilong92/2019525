package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 用户信息
 * @author: L-BackPacker
 * @date: 2018/4/28 8:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserbuyOrInfomVo  implements Serializable{
    /**
     * 技术实务 true 为购买，false为不购买
     */
    private boolean Skillbook;
    private boolean colligatebook;
    private boolean casebook;
    /**
     * 用户选中展示的状态（夜间，白天）
     */
    private String userSelectShowType;
    /**
     * 用户是否选中自动跳转
     */
    private String  userNextGo;
    /**
     * 用户token
     */
    private String token;
    /**
     * token 过期时间
     */
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSkillbook() {
        return Skillbook;
    }

    public boolean isColligatebook() {
        return colligatebook;
    }

    public boolean isCasebook() {
        return casebook;
    }

    public String getUserSelectShowType() {
        return userSelectShowType;
    }

    public void setUserSelectShowType(String userSelectShowType) {
        this.userSelectShowType = userSelectShowType;
    }

    public String getUserNextGo() {
        return userNextGo;
    }

    public void setUserNextGo(String userNextGo) {
        this.userNextGo = userNextGo;
    }

    public boolean getSkillbook() {
        return Skillbook;
    }

    public void setSkillbook(boolean skillbook) {
        Skillbook = skillbook;
    }

    public boolean getColligatebook() {
        return colligatebook;
    }

    public void setColligatebook(boolean colligatebook) {
        this.colligatebook = colligatebook;
    }

    public boolean getCasebook() {
        return casebook;
    }

    public void setCasebook(boolean casebook) {
        this.casebook = casebook;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
