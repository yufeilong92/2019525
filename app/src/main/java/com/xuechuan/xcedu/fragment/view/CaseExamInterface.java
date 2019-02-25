package com.xuechuan.xcedu.fragment.view;

import android.widget.EditText;

import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.vo.CaseCardVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.fragment.view
 * @Description: 案例分析考试
 * @author: L-BackPacker
 * @date: 2019.01.05 下午 1:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface CaseExamInterface {
    /**
     * 保存用做题数据
     *
     * @param vo
     */
    public void saveUserDoLog(DoBankSqliteVo vo);

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
    public void doRightGo(int postion);

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

    /**
     * 保存错题记录记录
     */
    public void doErrorLog(ErrorSqliteVo vo);

    //改变副题
    public void changerSmallQuestion(int postion);

    //切换主题
    public void changerMainQuesiton();

    /**
     * 用户是否交卷
     *
     * @return
     */
    public boolean submitAble();

    /**
     * 初始化 bar
     *
     * @param isShow
     */
    public void doEventHine(boolean isShow);

    /**
     * 是否是主观题
     *
     * @return
     */
    public boolean getMainQuesiton();

    /**
     * 获取当前总数
     *
     * @return
     */
    public int getFuPostionList();

    /**
     * 根据id获取相应题信息
     *
     * @param id
     * @return
     */
    public QuestionSqliteVo getQuestionVo(int id);

    /**
     * 收藏
     */
    public void changerCllect();

    /**
     * 是否自评
     *
     * @return
     */
    public boolean getmEvalueteAble();

    /**
     * 获取相应主题下面的小题
     *
     * @return
     */
    public List<CaseCardVo> getFuDataLists();

    /**
     * 滑动监听
     */
    public void MoveItem(int postion);

    /**
     * 更新用户评分
     *
     * @param vo
     */
    public void upDataEvalues(DoBankSqliteVo vo);

    /**
     * 语音输入
     *
     * @param editText
     */
    public void videoInput(EditText editText);
}
