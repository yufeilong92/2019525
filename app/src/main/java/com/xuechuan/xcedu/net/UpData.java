package com.xuechuan.xcedu.net;

import android.content.Context;

import com.google.gson.JsonObject;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.UpDataService;
import com.xuechuan.xcedu.net.view.StringCallBackUpView;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/28 15:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UpData extends UpDataService {
    private Context mContext;
    private static UpData service;

    public UpData(Context mContext) {
        this.mContext = mContext;
    }

    public static UpData getInstance(Context context) {
        if (service == null) {
            service = new UpData(context);
        }
        return service;
    }

    /**
     * 修改用户头像接口
     *
     * @param view
     */
    public void submitchangeheadimg(List<String> listPath, StringCallBackUpView view) {

        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        if (userInfom == null) {
            MyAppliction.getInstance().startLogin(mContext);
            T.showToast(mContext, mContext.getResources().getString(R.string.please_login));
            return;
        }
        UserBean user = userInfom.getData().getUser();
        JsonObject object = new JsonObject();
        object.addProperty("staffid", user.getId());
        String url = mContext.getResources().getString(R.string.http_m_hear);
        upImgHear(mContext, listPath, url, object, true, view);
    }

    /**
     * @param listPath  图片路径
     * @param problemid 问题id
     * @param tags      标签
     * @param content   问题
     * @param backView  返回
     */
    public void SubmitProblem(List<String> listPath, int problemid, String tags, String content, StringCallBackUpView backView) {
        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        if (userInfom == null) {
            MyAppliction.getInstance().startLogin(mContext);
            T.showToast(mContext, mContext.getResources().getString(R.string.please_login));
            return;
        }
        UserBean user = userInfom.getData().getUser();
        JsonObject object = new JsonObject();
        object.addProperty("staffid", user.getId());
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("problemid");
        paramVo.setValue(String.valueOf(problemid));
        listParamVo.add(paramVo);

        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("content");
        paramVo1.setValue(content);
        listParamVo.add(paramVo1);

        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("tags");
        paramVo2.setValue(tags);
        listParamVo.add(paramVo2);


        String url = mContext.getResources().getString(R.string.http_app_submit_problem);
        upProblemImgHear(mContext, listPath, url,object,true,listParamVo,backView);
    }
}
