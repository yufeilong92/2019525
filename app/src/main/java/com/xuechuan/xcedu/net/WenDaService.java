package com.xuechuan.xcedu.net;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 10:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class WenDaService extends BaseHttpServcie {
    private static volatile WenDaService _singleton;
    private Context mContext;

    private WenDaService(Context context) {
        this.mContext = context;
    }

    public static WenDaService get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (WenDaService.class) {
                if (_singleton == null) {
                    _singleton = new WenDaService(context);
                }
            }
        }
        return _singleton;
    }

    /**
     * 获取我的待解决问题列表
     *
     * @param
     * @param callBackView
     */
    public void requestWaitProblemLists(StringCallBackView callBackView) {
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
        String url = getUrl(mContext, R.string.http_app_getWaitproblem);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取精选问题列表
     *

     * @param callBackView
     */
    public void requestHankpickProbleLists( int page, StringCallBackView callBackView) {
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
        addPage(listParamVo, page);
        String url = getUrl(mContext, R.string.http_app_gethankpickproblem);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取我的问题列表
     *
     * @param callBackView
     */
    public void requestMyProbleLists(int page, StringCallBackView callBackView) {
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
        addPage(listParamVo, page);
        String url = getUrl(mContext, R.string.http_app_getmyproblem);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取我的问题列表
     *

     * @param callBackView
     */
    public void requestProbleDetail( int problemid, StringCallBackView callBackView) {
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
        paramVo1.setParam("problemid");
        paramVo1.setValue(String.valueOf(problemid));
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_app_problemdetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 提交问题评价
     *
     * @param problemid
     * @param score
     * @param comment
     * @param callBackView
     */
    public void submitProblemEvalues( int problemid, int score, String comment, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("problemid", problemid);
            object.put("score", score);
            object.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_app_submit_probleevalues);
        requestHttpServciePost(mContext, url, object, true, callBackView);

    }

}
