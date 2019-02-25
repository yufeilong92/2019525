package com.xuechuan.xcedu.base;

import android.content.Context;
import android.content.Intent;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.StringSort;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.HttpInfomVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/12 13:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OkGetHttpService extends BaseHttpServcie {

    private final HttpInfomVo infomVo;
    private static OkGetHttpService okGetHttpService;

    public OkGetHttpService() {
        infomVo = getInfomData();
    }

    public static OkGetHttpService getInstance() {
        if (okGetHttpService == null)
            okGetHttpService = new OkGetHttpService();
        return okGetHttpService;
    }

    /**
     * @param context
     * @param obj          请求参数
     * @param callBackView
     */

    public void sendRequestGetWithToken(Context context, final String url, final ArrayList<GetParamVo> obj, final StringCallBackView callBackView) {
        UserInfomVo userInfomVo = MyAppliction.getInstance().getUserInfom();
        if (userInfomVo == null) {
            MyAppliction.getInstance().startLogin(context);
            T.showToast(context, context.getString(R.string.please_login));
            return;
        }

        UserBean user = userInfomVo.getData().getUser();

        int id = user.getId();
        infomVo.setStaffid(String.valueOf(id));
        String token = user.getToken();
        infomVo.setToken(token);
        requestGetWithToken(context, url, obj, callBackView);
    }

    /***
     *
     * @param context
     * @param obj 请求参数
     * @param callBackView
     */
    private void requestGetWithToken(Context context, String url, ArrayList<GetParamVo> obj, final StringCallBackView callBackView) {

        if (obj == null) {
            return;
        }
        addParams(context, obj);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < obj.size(); i++) {
            GetParamVo paramVo = obj.get(i);
            if (i == 0) {
                buffer.append("?"+paramVo.getParam() + "=" + paramVo.getValue());
            } else {
                buffer.append("&" + paramVo.getParam() + "=" + paramVo.getValue());
            }
        }
        url = url.concat(buffer.toString());
        StringSort sort = new StringSort();
        String signature = sort.getOrderMd5Data(obj);
        L.e(signature);
//        sendRequestGetHttp(context, url, infomVo.getStaffid(), infomVo.getTimeStamp(), infomVo.getNonce(), signature, callBackView);
    }



    /**
     * 添加参数
     *
     * @param context
     * @param list
     */
    private void addParams(Context context, ArrayList<GetParamVo> list) {
        GetParamVo paramVo = new GetParamVo();
        String newType = Utils.getNewType(context);
        paramVo.setParam(DataMessageVo.HTTP_AC);
        paramVo.setValue(newType);
        list.add(paramVo);

        String versionName = Utils.getVersionName(context);
        GetParamVo paramVo1 = new GetParamVo();
        paramVo1.setParam(DataMessageVo.HTTP_VERSION_NAME);
        paramVo1.setValue(versionName);
        list.add(paramVo1);

        int code = Utils.getVersionCode(context);
        GetParamVo paramVo2 = new GetParamVo();
        paramVo2.setParam(DataMessageVo.HTTP_VERSION_CODE);
        paramVo2.setValue(String.valueOf(code));
        list.add(paramVo2);

        GetParamVo paramVo3 = new GetParamVo();
        paramVo3.setParam(DataMessageVo.HTTP_DEVICE_PLATFORM);
        paramVo3.setValue("android");
        list.add(paramVo3);

        String model = Utils.getSystemModel();
        GetParamVo paramVo4 = new GetParamVo();
        paramVo4.setParam(DataMessageVo.HTTP_DEVICE_TYPE);
        paramVo4.setValue(model);
        list.add(paramVo4);

        String brand = Utils.getDeviceBrand();
        GetParamVo paramVo5 = new GetParamVo();
        paramVo5.setParam(DataMessageVo.HTTP_DEVICE_BRAND);
        paramVo5.setValue(brand);
        list.add(paramVo5);

        String systemVersion = Utils.getSystemVersion();
        GetParamVo paramVo6 = new GetParamVo();
        paramVo6.setParam(DataMessageVo.HTTP_OS_VERSION);
        paramVo6.setValue(systemVersion);
        list.add(paramVo6);

        String dp = Utils.getdp(context);
        GetParamVo paramVo7 = new GetParamVo();
        paramVo7.setParam(DataMessageVo.HTTP_RESOLUTION);
        paramVo7.setValue(dp);
        list.add(paramVo7);

        String dpi = Utils.getdpi(context);
        GetParamVo paramVo8 = new GetParamVo();
        paramVo8.setParam(DataMessageVo.HTTP_DPI);
        paramVo8.setValue(dpi);
        list.add(paramVo8);

    }
}
