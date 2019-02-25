package com.xuechuan.xcedu.net;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.SubmiteExamVo;
import com.xuechuan.xcedu.vo.UpQuestionProgressVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: 题目
 * @author: L-BackPacker
 * @date: 2018/4/20 16:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BankService extends BaseHttpServcie {
    private Context mContext;
    private static BankService service;

    public BankService(Context mContext) {
        this.mContext = mContext;
    }

    public static BankService getInstance(Context context) {
        if (service == null) {
            service = new BankService(context);
        }
        return service;
    }

    /**
     * 获取科目编号章节列表
     *
     * @param couresid
     * @param callBackView
     */
    public void requestChapterList(String couresid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(couresid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_getchapter);
//        String url = getUrl(mContext, R.string.http_getCnapter);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 根据科目获取所有练习题题号
     *
     * @param coruresid    科目编号
     * @param callBackView
     */
    public void requestCourseQuestionIdsList(String coruresid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(coruresid);
        listParamVo.add(paramVo);
//        String url = getUrl(mContext, R.string.http_coursequestionids);
        String url = getUrl(mContext, R.string.http_GetCourseQuestionIds);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 题库首页获取错题和收藏数
     *
     * @param courseid
     * @param callBackView
     */
    public void requestErrSetandFav(String courseid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_errsetandfav);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取所有练习题库题号
     *
     * @param chapterid
     * @param callBackView
     */
    public void requestChapterQuestionids(String chapterid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("chapterid");
        paramVo.setValue(chapterid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_chapterquestionids);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取科目获取题库标签
     *
     * @param couresid
     * @param callBackView
     */
    public void requestionTags(String couresid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(couresid);
        listParamVo.add(paramVo);
//        String url = getUrl(mContext, R.string.http_questiontags);
        String url = getUrl(mContext, R.string.http_getquestiontags);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取用户错题/收藏题统计信息
     *
     * @param couresid
     * @param tagtype      类型err 或者 fav
     * @param callBackView
     */
    public void requestQuestionTagScount(String couresid, String tagtype, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();

        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(couresid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("tagtype");
        paramVo2.setValue(tagtype);
        listParamVo.add(paramVo2);
//        String url = getUrl(mContext, R.string.http_questiontagscount);
        String url = getUrl(mContext, R.string.http_GetQuestionTagsCount);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 根据标签编号获取用户收藏题或者错题题号
     *
     * @param coursid
     * @param tagtype
     * @param tagid
     * @param callBackView
     */
    public void requestQuestionTagids(String coursid, String tagtype, String tagid, StringCallBackView
            callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(coursid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("tagtype");
        paramVo1.setValue(tagtype);
        listParamVo.add(paramVo1);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("tagid");
        paramVo3.setValue(tagid);
        listParamVo.add(paramVo3);
//        String url = getUrl(mContext, R.string.http_questiontagids);
        String url = getUrl(mContext, R.string.http_GetQuestionTagIds);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);

    }


    /**
     * 根据题编号获取题目详情
     *
     * @param questionid   问题id
     * @param rnum         当前集合数据顺序
     * @param targetid     当前集合的id
     * @param qt           做题类型
     * @param callBackView 返回数据
     */
    public void requestDetail(String questionid, int rnum, int targetid, int qt, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("questionid");
        paramVo.setValue(questionid);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("rnum");
        paramVo1.setValue(String.valueOf(rnum));
        listParamVo.add(paramVo1);

        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));

        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("targetid");
        paramVo3.setValue(String.valueOf(targetid));

        GetParamVo paramVo4 = getParamVo();
        paramVo4.setParam("qt");
        paramVo4.setValue(String.valueOf(qt));
        listParamVo.add(paramVo4);
        listParamVo.add(paramVo3);
        listParamVo.add(paramVo2);
        listParamVo.add(paramVo);
//        String url = getUrl(mContext, R.string.http_datail);
        String url = getUrl(mContext, R.string.http_GetDetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
        L.e("requestDetail");

    }

    /***
     * 获取题目评论
     * @param questionid 编号
     * @param callBackView
     */
    public void reqiestQuestionCmment(String questionid, int page, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        if (page <= 0) {
            page = 1;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("questionid");
        paramVo1.setValue(questionid);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("page");
        paramVo2.setValue(String.valueOf(page));
        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("pagesize");
        paramVo3.setValue("10");
        listParamVo.add(paramVo3);
        listParamVo.add(paramVo2);
        listParamVo.add(paramVo1);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_questioncmment);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * .获取题目评论的评论
     *
     * @param questionid   编号
     * @param commentid    评论编号
     * @param callBackView
     */
    public void requestCommentComment(int page, String questionid, String commentid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("questionid");
        paramVo.setValue(questionid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("commentid");
        paramVo1.setValue(commentid);
        listParamVo.add(paramVo1);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");

        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("page");
        paramVo3.setValue(String.valueOf(page));
        GetParamVo paramVo4 = getParamVo();
        paramVo4.setParam("pagesize");
        paramVo4.setValue("10");
        listParamVo.add(paramVo3);
        listParamVo.add(paramVo4);
        String url = getUrl(mContext, R.string.http_commentcomment);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 用户是否购买此科目
     *
     * @param courseid
     * @param callBackView
     */
    public void requestIsBought(String courseid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
   /*     GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);*/
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_isbought);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);

    }

    /**
     * 根据 tagid 获取下面所有题号
     *
     * @param courseid     科目编号
     * @param tagid        标签编号
     * @param callBackView
     */
    public void requestUestionIdSbyTagid(String courseid, String tagid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("tagid");
        paramVo1.setValue(tagid);
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_questionidsbytagid);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);

    }

    /**
     * 获取模拟考试试卷
     *
     * @param courseid     科目编号
     * @param callBackView
     */
    public void requestExamchapte(String courseid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_examchapter);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);

    }

    /**
     * 提交收藏
     *
     * @param isfav        true 收藏 false 取消
     * @param targetid     练习题编号
     * @param callBackView
     */
    public void subimtfavpost(String targetid, boolean isfav, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("targetid", targetid);
            obj.put("isfav", isfav);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_favpost);
        requestHttpServciePost(mContext, url, obj, true, callBackView);
    }


    /**
     * 提交收藏
     *
     * @param targetid     练习题编号
     * @param callBackView
     */
    public void subimtDelectQuestionPost(String targetid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("targetid", targetid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_questionremoveerrsetpost);
        requestHttpServciePost(mContext, url, obj, true, callBackView);
    }


    /**
     * 提交做题记录
     *
     * @param isRight      true 对 false 错
     * @param targetid     练习题编号
     * @param callBackView
     */
    public void subimtQuestionHispost(String questionid, boolean isRight, int rnum, int targetid, int qt, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("targetid", targetid);
            obj.put("isright", isRight);
            obj.put("questionid", questionid);
            obj.put("rnum", rnum);
            obj.put("qt", qt);

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String url = getUrl(mContext, R.string.http_questionhispost);
        String url = getUrl(mContext, R.string.http_questionshispost);
        requestHttpServciePost(mContext, url, obj, true, callBackView);
    }

    /**
     * 获取网课产品列表
     *
     * @param callBackView
     */
    public void requestBankProduct(StringCallBackView callBackView) {
        String url = getUrl(mContext, R.string.http_questionbankproduct);
        requestHttpServiceGet(mContext, url, null, true, callBackView);

    }

    /**
     * 请求题库更新地址
     *
     * @param code
     * @param callBackView
     */
    public void requestBankGrade(String code, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("oldcode");
        paramVo1.setValue(code);
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_app_bank_getupgrade);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 请求用户购买情况
     *
     * @param callBackView
     */
    public void reqeustUserBuy(StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_isbought);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取题库科目首页每日一练
     *
     * @param callBackView
     */
    public void reqeustDateExercis(StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_app_day_exercise);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取每一日的详情
     *
     * @param id
     * @param callBackView
     */
    public void requestEveryDetail(int id, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("id");
        paramVo2.setValue(String.valueOf(id));
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_app_day_exercisedetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取每一日的排行
     *
     * @param id
     * @param callBackView
     */
    public void requestEveryRank(int id, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("id");
        paramVo2.setValue(String.valueOf(id));
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_app_day_paihang);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取历史每日一练
     *
     * @param courseid
     * @param callBackView
     */
    public void requestEveryHistory(int courseid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("courseid");
        paramVo2.setValue(String.valueOf(courseid));
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_app_history_dayexercise);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 提交用户做题记录
     *
     * @param exerciserid
     * @param duration
     * @param accuracy
     * @param view
     */
    public void submitUserDoTestlog(int exerciserid, int duration, String accuracy, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("exerciseid", exerciserid);
            obj.put("duration", duration);
            obj.put("accuracy", accuracy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_app_submit_day_exrcise);
        requestHttpServciePost(mContext, url, obj, true, view);
    }

    /**
     * 获取单道题笔记
     *
     * @param question
     * @param backView
     */
    public void requestNoteQuestion(int question, StringCallBackView backView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("questionid");
        paramVo1.setValue(String.valueOf(question));
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_app_getnotebyquestion);
        requestHttpServiceGet(mContext, url, listParamVo, true, backView);

    }

    /**
     * 获取笔记列表
     *
     * @param callBackView
     */
    public void requestMyNoteLists(String courseid, int page, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("courseid");
        paramVo1.setValue(String.valueOf(courseid));
        listParamVo.add(paramVo1);
        addPage(listParamVo, page);
        String url = getUrl(mContext, R.string.http_app_question_notelist);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取笔记总数
     *
     * @param callBackView
     */
    public void requestMyNoteCount(StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_app_question_notecount);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 题库笔记提交
     *
     * @param id       笔记编号，如果是新增时传0，修改时传标识
     * @param question
     * @param content
     * @param view
     */
    public void submitNoteContent(int id, int question, String content, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("id", id);
            obj.put("staffid", user.getId());
            obj.put("questionid", question);
            obj.put("content", content);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_app_questionnotpost);
        requestHttpServciePost(mContext, url, obj, true, view);

    }

    /**
     * 删除笔记
     *
     * @param question
     * @param view
     */
    public void submitDeleteNote(int question, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("id", question);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_app_quesiton_delete_note);
        requestHttpServciePost(mContext, url, obj, true, view);

    }

    /**
     * 提交用户做题记录
     *
     * @param score      //得分
     * @param chapterid  试卷编号
     * @param usertime   做题耗时（单位：秒）
     * @param finishtime 完成作答时间（）2019-10-10
     * @param details    做题详情集合
     * @param view
     */
    public void submiteExamPost(String score, int chapterid, int usertime, String finishtime, List<SubmiteExamVo> details, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("score", score);
            obj.put("chapterid", chapterid);
            obj.put("usetime", usertime);
            obj.put("finishtime", finishtime);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < details.size(); i++) {
                SubmiteExamVo vo = details.get(i);
                JSONObject jsonObj = getJsonObj();
                jsonObj.put("questionid", vo.getQuestion());
                jsonObj.put("choice", vo.getChoice());
                jsonObj.put("score", vo.getCore());
                jsonObj.put("answer", vo.getAnswer());
                jsonArray.put(jsonObj);
            }
            obj.put("details", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_app_exampost);
        requestHttpServciePost(mContext, url, obj, true, view);
    }


    /**
     * 提交做题记录
     *
     * @param view
     */
    public void submitDoBank(String history, String time, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("history", history);
            obj.put("date", time);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_app_questionpost);
        requestHttpServciePost(mContext, url, obj, true, view);

    }


    /**
     * 提交做题记录
     *
     * @param view
     */
    public void submitErrorLog(String add, String delete, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("add", add);
            obj.put("delete", delete);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_app_WrongQuestionPost);
        requestHttpServciePost(mContext, url, obj, true, view);

    }

    /**
     * 提交收藏记录
     *
     * @param view
     */
    public void submitCollectLog(String add, String delete, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("add", add);
            obj.put("delete", delete);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_app_FavoritePost);
        requestHttpServciePost(mContext, url, obj, true, view);
    }

    /**
     * 请求所有错题
     *
     * @param callBackView
     */
    public void requestErrorList(StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_app_geterrorsetquestion);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 请求所有收藏
     *
     * @param callBackView
     */
    public void requestCollectList(StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_app_GetFavoriteQuestion);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取进度
     *
     * @param callBackView
     */
    public void requestProgressList(StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_app_GetQuestionDoneQueue);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 提交进度
     *
     * @param view
     */
    public void submitQuestionProgressLog(int courseid, ArrayList<UpQuestionProgressVo> mData, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
//            obj.put("courseid", courseid);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < mData.size(); i++) {
                UpQuestionProgressVo vo = mData.get(i);
                JSONObject jsonObj = getJsonObj();
                jsonObj.put("qt", vo.getQt());
                jsonObj.put("rnum", vo.getRnum());
                jsonObj.put("targetid", vo.getTagetid());
                jsonObj.put("courseid",courseid);
                jsonArray.put(jsonObj);
            }
            obj.put("datas", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_app_QuestionQueuePost);
        requestHttpServciePost(mContext, url, obj, true, view);
    }

    /**
     * 获取试卷做题记录列表
     *
     * @param callBackView
     */
    public void requestExamHistoruList(int page,StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        addPage(listParamVo,page);
        String url = getUrl(mContext, R.string.http_app_GetExamHistory);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取试卷做题记录详情
     *
     * @param callBackView
     */
    public void requestExamHistoruDetail(int history, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("historyid");
        paramVo1.setValue(String.valueOf(history));
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_app_GetExamHistoryDetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 删除试卷
     *
     * @param callBackView
     */
    public void deleteHistoryItem(int history, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("historyid", history);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = getUrl(mContext, R.string.http_app_DeleteExamHistoryPost);
        requestHttpServciePost(mContext, url, obj, true, callBackView);
    }

}
