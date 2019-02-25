package com.xuechuan.xcedu.net;

import android.content.Context;

import com.easefun.polyvsdk.danmaku.StringUtils;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net.view
 * @Description: 网络
 * @author: L-BackPacker
 * @date: 2018/5/13 15:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetService extends BaseHttpServcie {
    private Context mContext;
    private static NetService service;

    public NetService(Context mContext) {
        this.mContext = mContext;
    }

    public static NetService getInstance(Context context) {
        if (service == null) {
            service = new NetService(context);
        }
        return service;
    }

    /***
     * 获取我的网课
     * @param view
     */
    public void requestClassAndproductsList(StringCallBackView view) {
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
//        String url = getUrl(R.string.http_getclassandproducts);
        String url = getUrl(R.string.http_video2getclass);

        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /***
     * 获取我的网课
     * @param view
     */
    public void requestAllproductList(StringCallBackView view) {
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
        String url = getUrl(R.string.http_getproduct);
//        String url = getUrl(R.string.http_getVideov3productdetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 根据课程编号获取课程详情
     *
     * @param classid
     * @param view
     */
    public void requestProductdetail(String classid, StringCallBackView view) {
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
        paramVo1.setParam("classid");
        paramVo1.setValue(classid);
        listParamVo.add(paramVo1);
//        String url = getUrl(R.string.http_getproductdetail);
        String url = getUrl(R.string.http_getVideov3productdetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 网课进度提交
     *
     * @param videoId   当前视频编号
     * @param classid   所属课程（产品）
     * @param progress  进度
     * @param watchTime 用户观看时长
     * @param view
     */
    public void SubmitViewProgres(String videoId, String classid, String progress, int watchTime, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("videoid", videoId);
            obj.put("classid", classid);
            obj.put("progress", progress);
            obj.put("watchtime", watchTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = getUrl(R.string.http_playprogresspostv2);
        requestHttpServciePost(mContext, url, obj, true, view);

    }

    /**
     * 网课下载视频提交
     *
     * @param videoid 当前视频编号
     * @param classid 所属课程（产品）
     * @param view
     */
    public void submitDownLoadVideo(String videoid, String classid, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("videoid", videoid);
            obj.put("classid", classid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(R.string.http_videodownloadpost);
        requestHttpServciePost(mContext, url, obj, true, view);

    }

    /**
     * 获取视屏的评价
     *
     * @param videoid
     * @param page
     * @param view
     */
    public void requestVideoEvalue(String videoid, int page, StringCallBackView view) {
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
        paramVo1.setParam("videoid");
        paramVo1.setValue(videoid);
        addPage(listParamVo, page);
        listParamVo.add(paramVo1);
        String url = getUrl(R.string.http_getVideo_evalue);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 获取视屏的评价的评价
     *
     * @param videoid
     * @param page
     * @param view
     */
    public void requestVideoEvalueCom(String videoid, String commentid, int page, StringCallBackView view) {
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
        paramVo1.setParam("video");
        paramVo1.setValue(videoid);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("commentid");
        paramVo2.setValue(commentid);
        addPage(listParamVo, page);
        listParamVo.add(paramVo2);
        listParamVo.add(paramVo1);
        String url = getUrl(R.string.http_getvideocommentcomment);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    private String getUrl(int str) {
        return mContext.getResources().getString(str);
    }

    /**
     *
     * @param page 页数
     * @param productid 课程编号
     * @param backView 返回
     */
    public void requestNetGetProductComment(int page, String  productid, StringCallBackView backView) {
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
        paramVo1.setParam("productid");
        paramVo1.setValue(productid);
        listParamVo.add(paramVo1);
        addPage(listParamVo, page);
        String url = getUrl(R.string.http_app_home_getproductdetailcomment);
        requestHttpServiceGet(mContext, url, listParamVo, true, backView);

    }
    /***
     * 获取我的网课
     * @param view
     */
    public void requestMyClass(StringCallBackView view) {
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
        String url = getUrl(R.string.http_app_net_myclass);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

}
