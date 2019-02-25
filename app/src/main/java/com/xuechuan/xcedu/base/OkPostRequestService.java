package com.xuechuan.xcedu.base;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.StringSort;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.HttpInfomVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/12 15:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OkPostRequestService extends BaseHttpServcie {
    public String ACITON = "com.xuechaun.OkTextPostRequest";

    private final HttpInfomVo infomVo;
    private static OkPostRequestService okTextPostRequest;

    public OkPostRequestService() {
        infomVo = MyAppliction.getInstance().getHttpInfomInstance();

    }



    public static OkPostRequestService getInstance() {
        if (okTextPostRequest == null)
            okTextPostRequest = new OkPostRequestService();
        return okTextPostRequest;
    }

    /**
     * POST请求 带有token
     *
     * @param context
     * @param url
     * @param callBackView
     */

    public void sendRequestPostWithToken(Context context, final String url, final JSONObject params, final StringCallBackView callBackView) {
        UserInfomVo vo = MyAppliction.getInstance().getUserInfom();
        if (vo == null) {
            T.showToast(context, context.getString(R.string.please_login));
            return;
        }
        addParams(context,params);
        MediaType parse = MediaType.parse(DataMessageVo.HTTPAPPLICAITON);
        UserBean user = vo.getData().getUser();
        infomVo.setStaffid(String.valueOf(user.getId()));
        infomVo.setToken(user.getToken());
        StringSort sort = new StringSort();
        String signature = sort.getOrderMd5Data(params);
        HttpInfomVo infomVo = getInfomData();
        RequestBody requestBody = RequestBody.create(parse, params.toString());
//        sendRequestPostHttp(context,url,infomVo.getStaffid(),infomVo.getTimeStamp(),infomVo.getTimeStamp()
//        ,signature,requestBody,callBackView);
    }

    /***
     * 登陆请求 带有token
     * @param context
     * @param url
     * @param param
     * @param callBackView
     */
    protected void requestPostWithOutToken(Context context, String url, JSONObject param, final StringCallBackView callBackView) {
        String time = String.valueOf(new Date().getTime());
        String random8 = String.valueOf(Utils.getRandom8());
        addParams(context, param);
        MediaType parse = MediaType.parse(DataMessageVo.HTTPAPPLICAITON);
        RequestBody requestBody = RequestBody.create(parse, param.toString());
//        sendRequestPostHttp(context, url, null, time, random8, null, requestBody, callBackView);
    }

    /**
     * 添加参数
     *
     * @param context
     * @param param
     */
    private void addParams(Context context, JSONObject param) {
        try {
            String newType = Utils.getNewType(context);
            param.put(DataMessageVo.HTTP_AC, newType);
            String versionName = Utils.getVersionName(context);
            param.put(DataMessageVo.HTTP_VERSION_NAME, versionName);
            int code = Utils.getVersionCode(context);
            param.put(DataMessageVo.HTTP_VERSION_CODE, String.valueOf(code));
            param.put(DataMessageVo.HTTP_DEVICE_PLATFORM, "android");
            String model = Utils.getSystemModel();
            param.put(DataMessageVo.HTTP_DEVICE_TYPE, model);
            String brand = Utils.getDeviceBrand();
            param.put(DataMessageVo.HTTP_DEVICE_BRAND, brand);
            String systemVersion = Utils.getSystemVersion();
            param.put(DataMessageVo.HTTP_OS_VERSION, systemVersion);
            String dp = Utils.getdp(context);
            param.put(DataMessageVo.HTTP_RESOLUTION, dp);
            String dpi = Utils.getdpi(context);
            param.put(DataMessageVo.HTTP_DPI, dpi);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
