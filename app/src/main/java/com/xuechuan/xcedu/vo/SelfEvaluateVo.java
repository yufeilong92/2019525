package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 自评vo
 * @author: L-BackPacker
 * @date: 2019.01.05 下午 8:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class SelfEvaluateVo {
    //对应小题数据
    private QuestionCaseVo casevo;
    //自评得分
    private String score;
    //是否自评
    private boolean isAble;

    public boolean isAble() {
        return isAble;
    }

    public void setAble(boolean able) {
        isAble = able;
    }

    public QuestionCaseVo getCasevo() {
        return casevo;
    }

    public void setCasevo(QuestionCaseVo casevo) {
        this.casevo = casevo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
