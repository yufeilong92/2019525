package com.xuechuan.xcedu.fragment.view;

import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.fragment.view
 * @Description: 考试
 * @author: L-BackPacker
 * @date: 2019.01.05 下午 1:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface GmOneInterface {
    /**
     * 保存用做题数据
     *
     * @param vo
     */
    public void saveUserDoLog(DoBankSqliteVo vo);

    /**
     * 保存用户考试记录
     *
     * @param vo
     */
    public void saveUserMockExam(DoBankSqliteVo vo);

    /**
     * 获取用户做题记录
     *
     * @param quesiton_id
     * @return
     */
    public DoBankSqliteVo getUserDoLog(int quesiton_id);

    /**
     * 删除用户做题记录
     *
     * @param quesiton_id
     */
    public void deleteUserDolog(int quesiton_id);

    /**
     * 下一题
     */
    public void doRightGo();

    /**
     * 查询用户做数据
     *
     * @param qustion_id
     * @return
     */
    public DoBankSqliteVo queryUserData(int qustion_id);

    /**
     * 该变颜色
     *
     * @param colorManger
     */
    public void changerColor(GmReadColorManger colorManger);

    //是指用户是否提交
    public void setSubmitAble(boolean submit);

    //获取用户是否提交
    public boolean getSubmitAble();

    /**
     * 保存错题记录
     *
     * @param vo
     */
    public void saveErrorLog(ErrorSqliteVo vo);

    /**
     * 根据id获取相应题信息
     *
     * @param id
     * @return
     */
    public QuestionSqliteVo getQuestionVo(int id);

    /**
     * 用户收藏
     *
     */
    public void changerCollect();

}
