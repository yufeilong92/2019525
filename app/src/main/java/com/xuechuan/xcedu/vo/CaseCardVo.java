package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 答题卡数据 和页面逻辑数据
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 5:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CaseCardVo implements Serializable {
    private int type;//1 为没有简答和阅读， 2为有 用与答题卡
    private List<CaseCardVo> list;
    private int maintype; //1为主题。2 为副题；  用于展示
    private QuestionSqliteVo vo;
    private QuestionCaseVo casevo;//重组数据

    public QuestionCaseVo getCasevo() {
        return casevo;
    }

    public void setCasevo(QuestionCaseVo casevo) {
        this.casevo = casevo;
    }

    public int getMaintype() {
        return maintype;
    }

    public void setMaintype(int maintype) {
        this.maintype = maintype;
    }

    public QuestionSqliteVo getVo() {
        return vo;
    }

    public void setVo(QuestionSqliteVo vo) {
        this.vo = vo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CaseCardVo> getList() {
        return list;
    }

    public void setList(List<CaseCardVo> list) {
        this.list = list;
    }
}
