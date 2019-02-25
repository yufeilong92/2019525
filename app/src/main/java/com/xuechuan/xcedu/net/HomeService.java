package com.xuechuan.xcedu.net;

import android.content.Context;
import android.content.Intent;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: 首页接口
 * @author: L-BackPacker
 * @date: 2018/4/18 9:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeService extends BaseHttpServcie {
    private Context mContext;
    private static HomeService service;
    private ArrayList<GetParamVo> vos;

    public HomeService(Context mContext) {
        this.mContext = mContext;
    }

    public static HomeService getInstance(Context context) {
        if (service == null) {
            service = new HomeService(context);
        }
        return service;
    }

    /**
     * 请求热词
     *
     * @param num          数量
     * @param callBackView
     */
    public void requestHost(String num, StringCallBackView callBackView) {
        ArrayList<GetParamVo> vos = getGetParamList();
        GetParamVo vo = getParamVo();
        vo.setParam("num");
        vo.setValue(num);
        vos.add(vo);
        String url = getUrl(R.string.http_getHotkey);
        requestHttpServiceGet(mContext, url, vos, true, callBackView);
    }

    /**
     * @param province     省
     * @param callBackView
     */
    public void requestInfom(String province, StringCallBackView callBackView) {
        ArrayList<GetParamVo> list = getGetParamList();
        GetParamVo vo = getParamVo();
        vo.setParam("provincecode");
        vo.setValue(province);
        list.add(vo);
        String url = getUrl(R.string.http_infom);
        requestHttpServiceGet(mContext, url, list, true, callBackView);

    }

    /**
     * 请求文章
     *
     * @param staffid
     * @param callBackView
     */
    public void requestArticleList(int staffid, int page, StringCallBackView callBackView) {
        if (page <= 0) {
            page = 1;
        }
        ArrayList<GetParamVo> list = getGetParamList();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(staffid));
        list.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("page");
        paramVo1.setValue(String.valueOf(page));
        list.add(paramVo1);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("pagesize");
        paramVo2.setValue("10");
        list.add(paramVo2);
        String url = getUrl(R.string.http_WenZhan);
        requestHttpServiceGet(mContext, url, list, true, callBackView);
    }

    /**
     * 请求文章资讯列表数据
     *
     * @param provinceCode
     * @param page
     * @param callBackView
     */
    public void requestAdvisoryList(String provinceCode, int page, StringCallBackView callBackView) {
        if (page <= 0) {
            page = 1;
        }
        ArrayList<GetParamVo> list = getGetParamList();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("page");
        paramVo.setValue(String.valueOf(page));
        list.add(paramVo);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("pagesize");
        paramVo2.setValue("10");
        list.add(paramVo2);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("provincecode");
        paramVo1.setValue(provinceCode);
        list.add(paramVo1);
        String url = getUrl(R.string.http_infom);
        requestHttpServiceGet(mContext, url, list, true, callBackView);
    }


    /**
     * 请求主页内容
     *
     * @param provice      省份
     * @param callBackView
     */
    public void requestHomePager(String provice, StringCallBackView callBackView) {
        ArrayList<GetParamVo> list = getGetParamList();
        GetParamVo vo = getParamVo();
        vo.setParam("provincecode");
        vo.setValue(provice);
        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        if (userInfom == null) {
            MyAppliction.getInstance().startLogin(mContext);
            T.showToast(mContext, mContext.getResources().getString(R.string.please_login));
            return;
        }
        int id = userInfom.getData().getUser().getId();
        GetParamVo vo1 = getParamVo();
        vo1.setParam("staffid");
        vo1.setValue(String.valueOf(id));
        list.add(vo1);
        list.add(vo);
//        String url = getUrl(R.string.http_HomePage);
        String url = getUrl(R.string.http_app_home_get);
        requestHttpServiceGet(mContext, url, list, true, callBackView);
    }

    /**
     * 规范章节列表数据
     *
     * @param callBackView
     */
    public void requestChapterList(int page, StringCallBackView callBackView) {
        if (page <= 0) {
            page = 1;
        }
        String url = getUrl(mContext, R.string.http_chapter);
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("page");
        paramVo.setValue(String.valueOf(page));
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("pagesize");
        paramVo1.setValue("10");
        listParamVo.add(paramVo1);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);

    }

    /**
     * 规范章节数据数据
     *
     * @param chapterid    编号
     * @param callBackView
     */
    public void requestarticle(String chapterid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("chapterid");
        paramVo.setValue(chapterid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_article);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * .获取教材科目
     */
    public void requestCourse(int page, StringCallBackView callBackView) {
        if (page <= 0) {
            page = 1;
        }
        String url = getUrl(mContext, R.string.http_Course);
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("page");
        paramVo.setValue(String.valueOf(page));
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("pagesize");
        paramVo1.setValue("10");
        listParamVo.add(paramVo1);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 教材章节
     *
     * @param courseid     科目
     * @param callBackView
     */
    public void requesthCapter(String courseid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_jiaocaichapter);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 0. 获取文章评论
     *
     * @param articleid
     * @param callBackView
     */
    public void requestArticleCommentList(String articleid, int page, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        if (page <= 0) {
            page = 1;
        }
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("articleid");
        paramVo.setValue(articleid);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
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

        String url = getUrl(mContext, R.string.http_articlecomment);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取文章tag
     *
     * @param callBackView
     */
    public void requestArticletagList(StringCallBackView callBackView) {
        String url = getUrl(mContext, R.string.http_getarticletag);
        requestHttpServiceGet(mContext, url, null, true, callBackView);
    }

    /**
     * 根据文章tagid获取文章列表
     */
    public void requestArticleWithTagaList(int page, String tagid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        UserBean user = login.getData().getUser();
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("tagid");
        paramVo.setValue(tagid);
        listParamVo.add(paramVo);
        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("page");
        paramVo3.setValue(String.valueOf(page));
        listParamVo.add(paramVo3);
        GetParamVo paramVo4 = getParamVo();
        paramVo4.setParam("pagesize");
        paramVo4.setValue("10");
        listParamVo.add(paramVo4);
        String url = getUrl(mContext, R.string.http_getarticlelistbytag);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取搜索内容
     */
    public void requestResultList(int page, String key, String usetype, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        UserBean user = login.getData().getUser();
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("usetype");
        paramVo.setValue(usetype);
        listParamVo.add(paramVo);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("key");
        paramVo2.setValue(key);
        listParamVo.add(paramVo2);
        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("page");
        paramVo3.setValue(String.valueOf(page));
        listParamVo.add(paramVo3);
        GetParamVo paramVo4 = getParamVo();
        paramVo4.setParam("pagesize");
        paramVo4.setValue("10");
        listParamVo.add(paramVo4);
//        String url = getUrl(mContext, R.string.http_getresult);
        String url = getUrl(mContext, R.string.http_app_home_getresult);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }


    /**
     * 获取搜索内容
     */
    public void requestGetDetail(String articleid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        UserBean user = login.getData().getUser();
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("articleid");
        paramVo.setValue(articleid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_getdetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }


    /**
     * 获取评论的评论
     *
     * @param articleid    科目
     * @param callBackView
     */
    public void requestCommentCommentList(int page, String articleid, String commentid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        if (page <= 0) {
            page = 1;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("page");
        paramVo3.setValue(String.valueOf(page));
        GetParamVo paramVo4 = getParamVo();
        paramVo4.setParam("pagesize");
        paramVo4.setValue("10");
//        GetParamVo paramVo = getParamVo();
//        paramVo.setParam("articleid");
//        paramVo.setValue(articleid);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("commentid");
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        paramVo1.setValue(commentid);
        listParamVo.add(paramVo3);
        listParamVo.add(paramVo4);
        listParamVo.add(paramVo2);
        listParamVo.add(paramVo1);
//        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_homecommentcomment);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }


    private ArrayList<GetParamVo> getGetParamList() {
        if (vos == null) {
            vos = new ArrayList<>();
        } else {
            vos.clear();
        }
        return vos;
    }

    private String getUrl(int str) {
        return mContext.getResources().getString(str);
    }


    public void updataLog(String content, StringCallBackView callBackView) {
        String url = mContext.getResources().getString(R.string.http_boli_updata);
        JSONObject object = new JSONObject();
        UserInfomVo infom = MyAppliction.getInstance().getUserInfom();
        if (infom == null) {
            T.showToast(mContext, "用户没有登陆，请登陆");
            return;
        }
        try {
            object.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestHttpServciePost(mContext, url, object, true, callBackView);
    }

    /**
     * 获取评论升级V2
     *
     * @param page         页数
     * @param tagrgetype   枚举类型article 文章，question问题，video视频
     * @param targetid     根据targettype类型传递主体（文章，问题，视频）编号
     * @param callBackView
     */
    public void requestCommentCommentData(int page, String tagrgetype, String targetid, StringCallBackView callBackView) {
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
        paramVo.setParam("targettype");
        paramVo.setValue(tagrgetype);
        listParamVo.add(paramVo);

        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("targetid");
        paramVo1.setValue(targetid);

        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));

        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("page");
        paramVo3.setValue(String.valueOf(page));

        GetParamVo paramVo4 = getParamVo();
        paramVo4.setParam("pagesize");
        paramVo4.setValue("10");

        listParamVo.add(paramVo1);
        listParamVo.add(paramVo2);
        listParamVo.add(paramVo3);
        listParamVo.add(paramVo4);
        String url = getUrl(mContext, R.string.http_currency_comment);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 请求获取所有在售网课列表
     *
     * @param page
     * @param view
     */
    public void requestHomeNetAll(int page, StringCallBackView view) {
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
        listParamVo.add(paramVo);
        addPage(listParamVo, page);
        String url = getUrl(mContext, R.string.http_app_home_getproductlist);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /***
     * 请求在售网课详情
     * @param productid
     * @param view
     */
    public void requestNetDetail(String productid, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("productid");
        paramVo.setValue(productid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_app_home_getproductdetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }


}
