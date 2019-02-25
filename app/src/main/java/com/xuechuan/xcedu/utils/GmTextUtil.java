package com.xuechuan.xcedu.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.CaseCardVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SubmiteExamVo;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 题库工具
 * @author: L-BackPacker
 * @date: 2018.12.14 下午 4:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmTextUtil {
    private static volatile GmTextUtil _singleton;
    private Context mContext;
    private final GmSelectImgManageUtil mGmSelectUtils;

    private GmTextUtil(Context context) {
        this.mContext = context;
        mGmSelectUtils = GmSelectImgManageUtil.get_Instance(context);
    }

    public static GmTextUtil get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (GmTextUtil.class) {
                if (_singleton == null) {
                    _singleton = new GmTextUtil(context);
                }
            }
        }
        return _singleton;
    }

    /**
     * 获取答案
     *
     * @param key
     * @return
     */
    public List<String> getAnswerKeyList(String key) {
        if (StringUtil.isEmpty(key)) return null;
        ArrayList list = new ArrayList<>();
        getAnswer(key, list);
        return list;
    }

    /**
     * 获取截取答案
     *
     * @param key
     * @param list
     */
    private void getAnswer(String key, ArrayList<String> list) {
        int length = key.length();
        if (length > 1) {
            String substring = key.substring(0, 1);
            list.add(substring.toUpperCase());
            key = key.substring(1, length);
            getAnswer(key, list);
        } else {
            list.add(key.toUpperCase());
        }
    }

    /**
     * 单选判断是否正确
     *
     * @param listkey 正确集合集
     * @param key     正确数
     * @return
     */
    public boolean keyIsRight(List<String> listkey, String key) {
        if (listkey == null || listkey.isEmpty()) return false;
        if (StringUtil.isEmpty(key)) return false;
        for (String s : listkey) {
            if (s.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置图片miss
     *
     * @param imgMiss
     */
    public void setImgMiss(ImageView imgMiss) {
        if (imgMiss == null) return;
        imgMiss.setImageDrawable(mGmSelectUtils.getDrawable(R.mipmap.ic_b_miss));
    }

    /**
     * 设置字体miss
     *
     * @param tv
     */
    public void setTvMiss(TextView tv) {
        if (tv == null) return;
        tv.setTextColor(mContext.getResources().getColor(R.color.text_color_woring));
    }

    /**
     * 设置正确图片
     *
     * @param imgRight
     */
    public void setImgRight(ImageView imgRight, boolean isRight) {
        imgRight.setImageDrawable(isRight ? mGmSelectUtils.getDrawable(R.mipmap.ic_b_text_right) : mGmSelectUtils.getDrawable(R.mipmap.ic_b_erro));
    }

    /**
     * 设置正确文字图片
     *
     * @param tv
     */
    public void setTvRight(TextView tv, boolean isRight) {
        if (tv == null) return;
        tv.setTextColor(isRight ? mContext.getResources().getColor(R.color.text_color_right) : mContext.getResources().getColor(R.color.text_color_error));
    }

    public int getColorDrawable(int id) {
        return mContext.getResources().getColor(id);
    }

    /**
     * 设置背景颜色
     *
     * @param bgAlpha
     */
    public void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    /**
     * @param img
     */
    public void setImgParams20(ImageView img) {
        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.width = 60;
        params.height = 60;
        img.setLayoutParams(params);
    }

    /**
     * @param img
     */
    public void setImgParams10(ImageView img) {
        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.width = 30;
        params.height = 30;
        img.setLayoutParams(params);
    }

    /**
     * 剪切获取数据 考点
     *
     * @param word
     * @return
     */
    public ArrayList<String> getKeyWords(String word) {
        if (StringUtil.isEmpty(word)) return null;
        String[] split = word.split(",");
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        return list;
    }

    /**
     * 获取用户正确率
     *
     * @param list
     * @param allNumber
     * @return
     */
    public String getRihgtAccuracy(List<DoBankSqliteVo> list, int allNumber) {
        int right = 0;
        if (list == null || list.isEmpty()) return "0";
        for (int i = 0; i < list.size(); i++) {
            DoBankSqliteVo vo = list.get(i);
            if (vo.getIsright() == 1) {
                right += 1;
            }
        }
        String s = ArithUtil.divNumber(right, allNumber, 2);
        return s;
    }

    /**
     * 获取分说
     *
     * @param list
     * @return
     */
    public String getUserGrade(List<DoBankSqliteVo> list) {
        if (list == null || list.isEmpty()) return "0";
        double score = 0;
        for (int i = 0; i < list.size(); i++) {
            DoBankSqliteVo vo = list.get(i);
            if (vo.getIsright() == 1) {
                if (vo.getQuestiontype() == 2) {
                    score += 1;
                } else if (vo.getQuestiontype() == 3) {
                    score += 2;
                }
            }
            if (vo.getIsright() == 2) {
                double more = 0;
                String a = null, b = null, c = null, d = null, e = null;
                String select = "select";
                if (vo.getSelectA() == 1) {
                    a = select;
                }
                if (vo.getSelectB() == 1) {
                    b = select;
                }
                if (vo.getSelectC() == 1) {
                    c = select;
                }
                if (vo.getSelectD() == 1) {
                    d = select;
                }
                if (vo.getSelectE() == 1) {
                    e = select;
                }
                if (!StringUtil.isEmpty(a)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(b)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(c)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(d)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(e)) {
                    more += 0.5;
                }
                if (more > 2) {
                    more = 2;
                }
                score += more;
            }
            if (vo.getIsright() == 4) {
                String mos = vo.getMos();
                if (!StringUtil.isEmpty(mos)) {
                    double a = Double.valueOf(mos);
                    score += a;
                }
            }
        }
        DecimalFormat format = new DecimalFormat("0.0");
        return format.format(score);
    }


    /**
     * 获取用户做题记录
     *
     * @param list
     * @return
     */
    public List<SubmiteExamVo> getUserDoLog(List<DoBankSqliteVo> list) {
        List<SubmiteExamVo> vos = new ArrayList<>();
        if (list == null || list.isEmpty()) return vos;
        for (int i = 0; i < list.size(); i++) {
            double score = 0;
            DoBankSqliteVo vo = list.get(i);
            SubmiteExamVo examVo = new SubmiteExamVo();
            //问题
            examVo.setQuestion(vo.getQuestion_id());
            //用户选项
            String choice = getChoice(vo);
            examVo.setChoice(choice);
            //简答题回答
            examVo.setAnswer("");
            if (vo.getIsright() == 1) {
                if (vo.getQuestiontype() == 2) {
                    score += 1;
                } else if (vo.getQuestiontype() == 3) {
                    score += 2;
                }
            }
            if (vo.getIsright() == 2) {
                double more = 0;
                String a = null, b = null, c = null, d = null, e = null;
                String select = "select";
                if (vo.getSelectA() == 1) {
                    a = select;
                }
                if (vo.getSelectB() == 1) {
                    b = select;
                }
                if (vo.getSelectC() == 1) {
                    c = select;
                }
                if (vo.getSelectD() == 1) {
                    d = select;
                }
                if (vo.getSelectE() == 1) {
                    e = select;
                }
                if (!StringUtil.isEmpty(a)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(b)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(c)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(d)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(e)) {
                    more += 0.5;
                }
                if (more > 2) {
                    more = 2;
                }
                score += more;
            } else if (vo.getIsright() == 4) {//用户简答内容
                String mos = vo.getMos();
                if (!StringUtil.isEmpty(mos)) {
                    double a = Double.parseDouble(mos);
                    score += a;
                }
                examVo.setAnswer(vo.getAnalysis());
            }
            examVo.setCore(score);
            vos.add(examVo);
        }
        return vos;
    }

    /**
     * 组合用户选项
     *
     * @param vo
     * @return
     */
    private String getChoice(DoBankSqliteVo vo) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(vo.getSelectA() == 1 ? "A" : "");
        buffer.append(vo.getSelectB() == 1 ? "B" : "");
        buffer.append(vo.getSelectC() == 1 ? "C" : "");
        buffer.append(vo.getSelectD() == 1 ? "D" : "");
        buffer.append(vo.getSelectE() == 1 ? "E" : "");
        return buffer.toString().trim();
    }

    //重组数据
    public void doDataListEvent(ArrayList<CaseCardVo> mCardLists, ArrayList<QuestionSqliteVo> lists) {
        CaseCardVo caseCardVo = new CaseCardVo();
        //题干
        ArrayList<CaseCardVo> vos = new ArrayList<>();
        if (lists == null || lists.isEmpty()) return;
        for (int i = 0; i < lists.size(); i++) {
            QuestionSqliteVo vo = lists.get(i);
            if (vo.getQuestiontype() == 4 || vo.getQuestiontype() == 5) {
                lists.remove(i);
                ArrayList<Integer> integers = groupData(vo.getQuestion_id(), lists);
                if (integers == null || integers.isEmpty()) {
                    caseCardVo.setType(1);
                    caseCardVo.setVo(vo);
                    caseCardVo.setMaintype(1);
                    caseCardVo.setList(vos);
                    if (!mCardLists.contains(caseCardVo))
                        mCardLists.add(caseCardVo);
                    doDataListEvent(mCardLists, lists);
                } else {
                    caseCardVo.setType(2);
                    caseCardVo.setMaintype(1);
                    caseCardVo.setVo(vo);
                    ArrayList<CaseCardVo> integerList = getIntegerList(integers, lists);
                    caseCardVo.setList(integerList);
                    if (!mCardLists.contains(caseCardVo))
                        mCardLists.add(caseCardVo);
                    lists = getDeleteIntegerList(integers, lists);
                    doDataListEvent(mCardLists, lists);
                }
            }
        }

    }

    //获取重复数据
    public ArrayList<CaseCardVo> getIntegerList(ArrayList<Integer> integers, ArrayList<QuestionSqliteVo> datas) {
        if (integers == null || integers.isEmpty()) return null;
        if (datas == null || datas.isEmpty()) return null;
        ArrayList<CaseCardVo> sqliteVos = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            CaseCardVo cardVo = new CaseCardVo();
            QuestionSqliteVo vo = datas.get(i);
            for (int k = 0; k < integers.size(); k++) {
                Integer integer = integers.get(k);
                if (vo.getId() == integer) {
                    cardVo.setVo(vo);
                    cardVo.setMaintype(2);
                    sqliteVos.add(cardVo);
                }
            }
        }
        return sqliteVos;
    }

    //删除重复数据
    public ArrayList<QuestionSqliteVo> getDeleteIntegerList(ArrayList<Integer> integers, ArrayList<QuestionSqliteVo> datas) {
        if (integers == null || integers.isEmpty()) return datas;
        if (datas == null || datas.isEmpty()) return null;
        for (int i = 0; i < datas.size(); i++) {
            QuestionSqliteVo vo = datas.get(i);
            for (int k = 0; k < integers.size(); k++) {
                Integer integer = integers.get(k);
                if (vo.getId() == integer) {
                    datas.remove(i);
                    integers.remove(k);
                    getDeleteIntegerList(integers, datas);
                }
            }
        }
        return datas;
    }

    /**
     * 组合数据
     *
     * @param id    父类id
     * @param mData 数据集
     * @return
     */
    public ArrayList<Integer> groupData(int id, ArrayList<QuestionSqliteVo> mData) {
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            QuestionSqliteVo vo = mData.get(i);
            if (vo.getParent_id() == id) {
                ints.add(vo.getId());
            }
        }
        return ints;
    }

    //重组数据
    public List<CaseCardVo> doDataListCaseEventThree(List<QuestionCaseVo> lists) {
        List<CaseCardVo> mCardLists = new ArrayList<>();
        //主题干集合
        if (lists == null || lists.isEmpty()) return mCardLists;
        int size = lists.size();
        List<QuestionCaseVo> mParentlist = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            QuestionCaseVo vo = lists.get(i);
            if (vo.getQuestiontype() == 5) {
                CaseCardVo caseCardVo = new CaseCardVo();
                caseCardVo.setType(1);
                caseCardVo.setCasevo(vo);
                caseCardVo.setMaintype(1);
                mCardLists.add(caseCardVo);
                mParentlist.add(vo);
            }
        }
        lists.removeAll(mParentlist);
        List<CaseCardVo> mCardListOnes = new ArrayList<>();
        if (mCardLists != null && !mCardLists.isEmpty()) {
            int mainSize = mCardLists.size();
            for (int i = 0; i < mainSize; i++) {
                //主干题
                CaseCardVo vo = mCardLists.get(i);
                QuestionCaseVo parentvo = vo.getCasevo();
                int question_id = parentvo.getQuestion_id();
                ArrayList<CaseCardVo> mChilds = new ArrayList<>();
                if (lists != null && !lists.isEmpty()) {
                    for (int k = 0; k < lists.size(); k++) {
                        //小题干题
                        QuestionCaseVo childvo = lists.get(k);
                        if (question_id == childvo.getParent_id()) {
                            CaseCardVo caseCardVo = new CaseCardVo();
                            caseCardVo.setType(2);
                            caseCardVo.setMaintype(2);
                            caseCardVo.setCasevo(childvo);
                            mChilds.add(caseCardVo);
                        }
                    }
                }
                vo.setList(mChilds);
                mCardListOnes.add(vo);
            }
        }
        mCardLists = null;
        return mCardListOnes;
    }


    //重组数据
    public List<CaseCardVo> getDataListCaseEventFour(List<QuestionCaseVo> mParentList, List<QuestionCaseVo> mChildlists) {
        List<CaseCardVo> mCardLists = new ArrayList<>();
        //主题干集合
        if (mParentList == null || mParentList.isEmpty()) return mCardLists;
        List<CaseCardVo> mCardListOnes = new ArrayList<>();
        if (mParentList != null && !mParentList.isEmpty()) {
            int mainSize = mParentList.size();
            for (int i = 0; i < mainSize; i++) {
                //主干题
                QuestionCaseVo vo = mParentList.get(i);
                CaseCardVo caseCardVo = new CaseCardVo();
                caseCardVo.setType(1);
                caseCardVo.setCasevo(vo);
                caseCardVo.setMaintype(1);
                mCardLists.add(caseCardVo);
                int question_id = vo.getQuestion_id();
                ArrayList<CaseCardVo> mChilds = new ArrayList<>();
                for (int k = 0; k < mChildlists.size(); k++) {
                    //小题干题
                    QuestionCaseVo childvo = mChildlists.get(k);
                    if (question_id == childvo.getParent_id()) {
                        CaseCardVo childVoNew = new CaseCardVo();
                        childVoNew.setType(2);
                        childVoNew.setMaintype(2);
                        childVoNew.setCasevo(childvo);
                        mChilds.add(childVoNew);
                    }
                }

                caseCardVo.setList(mChilds);
                mCardListOnes.add(caseCardVo);
            }
        }
        mCardLists = null;
        return mCardListOnes;
    }

    //重组数据
    public List<CaseCardVo> getDataListCaseEventFive(List<QuestionCaseVo> mParentList, List<QuestionCaseVo> mChildlists) {
        List<CaseCardVo> mCardLists = new ArrayList<>();
        //主题干集合
        if (mParentList == null || mParentList.isEmpty()) return mCardLists;
        List<CaseCardVo> mCardListOnes = new ArrayList<>();
        if (mParentList != null && !mParentList.isEmpty()) {
            int mainSize = mParentList.size();
            for (int i = 0; i < mainSize; i++) {
                //主干题
                QuestionCaseVo vo = mParentList.get(i);
                CaseCardVo caseCardVo = new CaseCardVo();
                caseCardVo.setType(1);
                caseCardVo.setCasevo(vo);
                caseCardVo.setMaintype(1);
                mCardLists.add(caseCardVo);
                int question_id = vo.getQuestion_id();
                ArrayList<CaseCardVo> mChilds = new ArrayList<>();
                for (int k = 0; k < mChildlists.size(); k++) {
                    //小题干题
                    QuestionCaseVo childvo = mChildlists.get(k);
                    if (question_id == childvo.getParent_id()) {
                        CaseCardVo childVoNew = new CaseCardVo();
                        childVoNew.setType(2);
                        childVoNew.setMaintype(2);
                        childVoNew.setCasevo(childvo);
                        mChilds.add(childVoNew);
                    }
                }

                caseCardVo.setList(mChilds);
                if (mChilds != null && !mChilds.isEmpty())
                    mCardListOnes.add(caseCardVo);
            }
        }
        mCardLists = null;
        return mCardListOnes;
    }

    //重组数据
    public void doDataListCaseEventTwo(List<CaseCardVo> mCardLists, List<QuestionCaseVo> lists) {

        //主题干集合
        if (lists == null || lists.isEmpty()) return;
        int size = lists.size();
        for (int i = 0; i < size; i++) {
            QuestionCaseVo vo = lists.get(i);
            if (vo.getQuestiontype() == 5) {
                //删除防止出现死循环
                CaseCardVo caseCardVo = new CaseCardVo();
                caseCardVo.setType(1);
                caseCardVo.setCasevo(vo);
                caseCardVo.setMaintype(1);
                mCardLists.add(caseCardVo);
            }
        }
        if (mCardLists != null && !mCardLists.isEmpty()) {
          /*  int size1 = lists.size();
            int size2 = mCardLists.size();
            for (int i = 0; i < size1; i++) {
                QuestionCaseVo child = lists.get(i);

                for (int k = 0; k < size2; k++) {
                    CaseCardVo vo = mCardLists.get(k);
                    QuestionCaseVo casevo = vo.getCasevo();

                    if (child.getParent_id() == casevo.getQuestion_id()) {
                        CaseCardVo caseCardVo = mCardLists.get(k);
                        List<CaseCardVo> list = caseCardVo.getList();
                        CaseCardVo childVo = new CaseCardVo();
                        childVo.setType(2);
                        childVo.setMaintype(2);
                        childVo.setCasevo(child);
                        if (list == null || list.isEmpty()) {
                            list = new ArrayList<>();
                            list.add(childVo);
                        } else {
                            list.add(childVo);
                        }
                       caseCardVo.setList(list);
                    }
                }
            }*/
            for (QuestionCaseVo list : lists) {
                for (CaseCardVo caseCardVo : mCardLists) {
                    QuestionCaseVo casevo = caseCardVo.getCasevo();
                    if (list.getParent_id() == casevo.getQuestion_id()) {
                        List<CaseCardVo> fuLists = caseCardVo.getList();
                        CaseCardVo vo = new CaseCardVo();
                        vo.setType(2);
                        vo.setMaintype(2);
                        vo.setCasevo(list);
                        if (fuLists == null) {
                            fuLists = new ArrayList<>();
                            fuLists.add(vo);
                        } else {
                            fuLists.add(vo);
                        }
                        caseCardVo.setList(fuLists);
                    }
                }
            }
        }
    }

    //重组数据
    public void doDataListCaseEvent(List<CaseCardVo> mCardLists, List<QuestionCaseVo> lists) {
        CaseCardVo caseCardVo = new CaseCardVo();
        //题干
        ArrayList<CaseCardVo> vos = new ArrayList<>();
        if (lists == null || lists.isEmpty()) return;
        int size = lists.size();
        for (int i = 0; i < size; i++) {
            QuestionCaseVo vo = lists.get(i);
            if (/*vo.getQuestiontype() == 4 ||*/ vo.getQuestiontype() == 5) {
                //删除防止出现死循环
                lists.remove(i);
                ArrayList<Integer> integers = groupCaseData(vo.getQuestion_id(), lists);
                if (integers == null || integers.isEmpty()) {
                    caseCardVo.setType(1);
                    caseCardVo.setCasevo(vo);
                    caseCardVo.setMaintype(1);
                    caseCardVo.setList(vos);
                    if (!mCardLists.contains(caseCardVo))
                        mCardLists.add(caseCardVo);
                    doDataListCaseEvent(mCardLists, lists);
                } else {
                    caseCardVo.setType(2);
                    caseCardVo.setMaintype(2);
                    caseCardVo.setCasevo(vo);
                    List<CaseCardVo> integerList = getIntegerCaseList(integers, lists);
                    caseCardVo.setList(integerList);
                    if (!mCardLists.contains(caseCardVo))
                        mCardLists.add(caseCardVo);
                    //删除防止出现死循环
                    lists = getDeleteIntegerCaseList(integers, lists);
                    doDataListCaseEvent(mCardLists, lists);
                }
            }
            size = lists.size();
        }
    }

    /**
     * @param integers 集合 子类id
     * @param datas    数据
     * @return
     */
    //获取重复数据
    public List<CaseCardVo> getIntegerCaseList(List<Integer> integers, List<QuestionCaseVo> datas) {
        if (integers == null || integers.isEmpty()) return null;
        if (datas == null || datas.isEmpty()) return null;
        List<CaseCardVo> sqliteVos = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            CaseCardVo cardVo = new CaseCardVo();
            QuestionCaseVo vo = datas.get(i);
            for (int k = 0; k < integers.size(); k++) {
                Integer integer = integers.get(k);
                if (vo.getId() == integer) {
                    cardVo.setCasevo(vo);
                    cardVo.setMaintype(2);
                    sqliteVos.add(cardVo);
                }
            }
        }
        return sqliteVos;
    }

    //删除重复数据
    public List<QuestionCaseVo> getDeleteIntegerCaseList(List<Integer> integers, List<QuestionCaseVo> datas) {
        if (integers == null || integers.isEmpty()) return datas;
        if (datas == null || datas.isEmpty()) return null;
        for (int i = 0; i < datas.size(); i++) {
            QuestionCaseVo vo = datas.get(i);
            for (int k = 0; k < integers.size(); k++) {
                Integer integer = integers.get(k);
                if (vo.getId() == integer) {
                    datas.remove(i);
                    integers.remove(k);
                    getDeleteIntegerCaseList(integers, datas);
                }
            }
        }
        return datas;
    }

    //重组数据(只有简答解析)
    public void doGalleryListCaseEvent(List<CaseCardVo> mCardLists, List<QuestionCaseVo> lists) {
        CaseCardVo caseCardVo = new CaseCardVo();
        if (lists == null || lists.isEmpty()) return;
        for (int i = 0; i < lists.size(); i++) {
            QuestionCaseVo vo = lists.get(i);
            if (/*vo.getQuestiontype() == 4 ||*/ vo.getQuestiontype() == 5) {
                //获取题干小题
                List<Integer> integers = groupCaseExamData(vo.getQuestion_id(), lists);
                if (integers != null && !integers.isEmpty()) {
                    caseCardVo.setType(2);
                    caseCardVo.setMaintype(1);
                    caseCardVo.setCasevo(vo);
                    List<CaseCardVo> integerList = getIntegerCaseList(integers, lists);
                    caseCardVo.setList(integerList);
                    if (!mCardLists.contains(caseCardVo))
                        mCardLists.add(caseCardVo);
                    lists = getDeleteIntegerCaseList(integers, lists);
                    doGalleryListCaseEvent(mCardLists, lists);
                }
            }
        }

    }


    /**
     * 组合数据
     *
     * @param id    父类id
     * @param mData 数据集
     * @return
     */
    public ArrayList<Integer> groupCaseData(int id, List<QuestionCaseVo> mData) {
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            QuestionCaseVo vo = mData.get(i);
            if (vo.getParent_id() == id) {
                ints.add(vo.getId());
            }
        }
        return ints;
    }

    /**
     * 组合数据
     *
     * @param id    父类id
     * @param mData 数据集
     * @return
     */
    public List<Integer> groupCaseExamData(int id, List<QuestionCaseVo> mData) {
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            QuestionCaseVo vo = mData.get(i);
            if (vo.getParent_id() == id) {
                if (vo.getQuestiontype() == 4)
                    ints.add(vo.getId());
            }
        }
        return ints;
    }

    //获取题干信息
    public void setQuestionType(TextView tv, int type) {
        switch (type) {
            case 2:
                tv.setText("单选题");
                break;
            case 3:
                tv.setText("多选题");
                break;
            case 4:
                tv.setText("简答题");
                break;
        }
    }
}
