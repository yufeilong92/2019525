package com.xuechuan.xcedu.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.easefun.polyvsdk.PolyvSDKUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.net.FileDownService;
import com.xuechuan.xcedu.net.view.BitmapCallBackView;
import com.xuechuan.xcedu.net.view.FileDownView;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.ImageUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.NetworkToolUtil;
import com.xuechuan.xcedu.utils.StringSort;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.HttpInfomVo;
import com.xuechuan.xcedu.vo.TongVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;
import com.xuechuan.xcedu.weight.LogDowbLoadListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: 网络请求
 * @author: L-BackPacker
 * @date: 2018/4/10 14:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BaseHttpServcie {

    public HttpInfomVo getInfomData() {
        HttpInfomVo infomVo = MyAppliction.getInstance().getHttpInfomInstance();
        String time = String.valueOf(new Date().getTime());
        String random8 = String.valueOf(Utils.getRandom8());
        infomVo.setTimeStamp(String.valueOf(time));
        infomVo.setNonce(String.valueOf(random8));
        return infomVo;
    }

    protected void requestHttpServciePost(Context context, final String url, JSONObject params,
                                          boolean isWithToken, final StringCallBackView callBackView) {
        if (!doOpenNetWork(context)){
            callBackView.onError("");
            return;
        }
        UserBean user = null;
        if (isWithToken) {
            UserInfomVo vo = MyAppliction.getInstance().getUserInfom();
            if (vo == null) {
                MyAppliction.getInstance().startLogin(context);
                T.showToast(context, context.getString(R.string.please_login));
                return;
            }
            user = vo.getData().getUser();
        }
        if (params == null) {
            params = new JSONObject();
        }
        addParams(context, params);
        MediaType parse = MediaType.parse(DataMessageVo.HTTPAPPLICAITON);
        HttpInfomVo infomVo = getInfomData();

        String signature = null;
        if (isWithToken) {
            infomVo.setStaffid(String.valueOf(user.getId()));
            infomVo.setToken(user.getToken());
            StringSort sort = new StringSort();
            signature = sort.getOrderMd5Data(params);
        } else {
            infomVo.setToken(null);
            infomVo.setStaffid(null);
        }
        RequestBody requestBody = RequestBody.create(parse,
                params.toString());
        sendRequestPostHttp(context, url, infomVo.getStaffid(), infomVo.getTimeStamp(), infomVo.getNonce()
                , signature, requestBody, callBackView);
    }


    protected void requestHttpServciePost(Context context, final String url, JsonObject params,
                                          boolean isWithToken, final StringCallBackView callBackView) {
        if (!doOpenNetWork(context)){
            callBackView.onError("");
            return;
        }
        UserBean user = null;
        if (isWithToken) {
            UserInfomVo vo = MyAppliction.getInstance().getUserInfom();
            if (vo == null) {
                MyAppliction.getInstance().startLogin(context);
                T.showToast(context, context.getString(R.string.please_login));
                return;
            }
            user = vo.getData().getUser();
        }
        if (params == null) {
            params = new JsonObject();
        }
        addParams(context, params);
        MediaType parse = MediaType.parse(DataMessageVo.HTTPAPPLICAITON);
        HttpInfomVo infomVo = getInfomData();

        String signature = null;
        if (isWithToken) {
            infomVo.setStaffid(String.valueOf(user.getId()));
            infomVo.setToken(user.getToken());
            StringSort sort = new StringSort();
            signature = sort.getOrderMd5Data(params);
        } else {
            infomVo.setToken(null);
            infomVo.setStaffid(null);
        }
        RequestBody requestBody = RequestBody.create(parse,
                params.toString());
        sendRequestPostHttp(context, url, infomVo.getStaffid(), infomVo.getTimeStamp(), infomVo.getNonce()
                , signature, requestBody, callBackView);
    }

    protected void requestHttpServiceGet(Context context, String url, ArrayList<GetParamVo> obj,
                                         boolean isWithToken, final StringCallBackView callBackView) {
        if (!doOpenNetWork(context)){
            callBackView.onError("");
            return;
        }
        if (obj == null) {
            obj = getListParamVo();
        }
        UserBean user = null;
        if (isWithToken) {
            UserInfomVo userInfomVo = MyAppliction.getInstance().getUserInfom();
            if (userInfomVo == null) {
                MyAppliction.getInstance().startLogin(context);
                T.showToast(context, context.getString(R.string.please_login));
                return;
            }
            user = userInfomVo.getData().getUser();
        }
        addParams(context, obj);
        HttpInfomVo infomVo = getInfomData();
        String signature = null;
        if (isWithToken) {
            int id = user.getId();
            infomVo.setStaffid(String.valueOf(id));
            String token = user.getToken();
            infomVo.setToken(token);
            StringSort sort = new StringSort();
            signature = sort.getOrderMd5Data(obj);
        } else {
            infomVo.setToken(null);
            infomVo.setStaffid(null);
        }
        StringBuffer buffer = new StringBuffer();
        if (obj.size() > 0) {
            for (int i = 0; i < obj.size(); i++) {
                GetParamVo paramVo = obj.get(i);
                if (i == 0) {
                    buffer.append("?" + paramVo.getParam() + "=" + paramVo.getValue());
                } else {
                    buffer.append("&" + paramVo.getParam() + "=" + paramVo.getValue());
                }
            }
        }
        url = url.concat(buffer.toString());
        sendRequestGetHttp(context, url, infomVo.getStaffid(), infomVo.getTimeStamp(), infomVo.getNonce(), signature, callBackView);

    }

    /**
     * 发送post请求
     *
     * @param context
     * @param url
     * @param saffid
     * @param time
     * @param nonce
     * @param signature
     * @param requestBody
     * @param callBackView
     */
    private void sendRequestPostHttp(final Context context, String url, String saffid,
                                     String time, String nonce, String signature,
                                     final RequestBody requestBody, final StringCallBackView callBackView) {
        if (!doOpenNetWork(context)){
            callBackView.onError("");
            return;
        }
        String newType = Utils.getNewType(context);
        if (StringUtil.isEmpty(newType)) {
            callBackView.onError(context.getResources().getString(R.string.net_error));
            return;
        }
        if (StringUtil.isEmpty(saffid)) {
            saffid = "0";
        }
        String hear = context.getResources().getString(R.string.app_content_heat);
        url = hear.concat(url);

        OkGo.<String>post(url)
                .tag(context)
                .headers(DataMessageVo.STAFFID, StringUtil.isEmpty(saffid) ? null : saffid)
                .headers(DataMessageVo.TIMESTAMP, StringUtil.isEmpty(time) ? null : time)
                .headers(DataMessageVo.NONCE, StringUtil.isEmpty(nonce) ? null : nonce)
                .headers(DataMessageVo.SIGNATURE, StringUtil.isEmpty(signature) ? null : signature)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String com = response.body().toString();
                            new JsonParser().parse(com);
                            Gson gson = new Gson();
                            TongVo vo = gson.fromJson(com, TongVo.class);
                            if (vo.getStatus().getCode() == 200) {
                                callBackView.onSuccess(response.body().toString());
                            } else if (vo.getStatus().getCode() == 406 || vo.getStatus().getCode() == 403) {
                                T.showToast(context, "检测您已经在其他设备登陆，请重新登陆");
                                MyAppliction.getInstance().startLogin(context);
                            } else {
                                callBackView.onError(response.body().toString());
                            }
                        } catch (Exception e) {
                            L.e("数据异常");
                            e.printStackTrace();
                            callBackView.onError("");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        L.e(response.message());
                        callBackView.onError(response.message() == null ? context.getResources().getString(R.string.net_error) : response.message());
                    }
                });

    }

    private void sendRequestGetHttp(final Context context, String url, String saffid, String time, String nonce, String signature, final StringCallBackView callBackView) {
        if (!doOpenNetWork(context)){
            callBackView.onError("");
            return;
        }
        String newType = Utils.getNewType(context);
        if (StringUtil.isEmpty(newType)) {
            callBackView.onError(context.getResources().getString(R.string.net_error));
            return;
        }
        if (StringUtil.isEmpty(saffid)) {
            saffid = "0";
        }
        String hear = context.getResources().getString(R.string.app_content_heat);
        url = hear.concat(url);
        L.d("请求地址", url);

        OkGo.<String>get(url)
                .tag(context)
                .headers(DataMessageVo.STAFFID, StringUtil.isEmpty(saffid) ? null : saffid)
                .headers(DataMessageVo.TIMESTAMP, StringUtil.isEmpty(time) ? null : time)
                .headers(DataMessageVo.NONCE, StringUtil.isEmpty(nonce) ? null : nonce)
                .headers(DataMessageVo.SIGNATURE, StringUtil.isEmpty(signature) ? null : signature)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String com = response.body().toString();
                            new JsonParser().parse(com);
                            Gson gson = new Gson();
                            TongVo vo = gson.fromJson(com, TongVo.class);
                            if (vo.getStatus().getCode() == 200) {
                                callBackView.onSuccess(response.body().toString());
                            } else if (vo.getStatus().getCode() == 406 || vo.getStatus().getCode() == 403) {
                                T.showToast(context, "检测您已经在其他设备登陆，请重新登陆");
                                MyAppliction.getInstance().startLogin(context);
                            } else {
                                callBackView.onError(response.body().toString());
                            }
                        } catch (Exception e) {
                            L.e("数据异常");
                            e.printStackTrace();
                            callBackView.onError("");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        L.e("数据异常");
                        L.e(response.message());
                        callBackView.onError(response.message() == null ? context.getResources().getString(R.string.net_error) : response.message());
                    }
                });
    }


    protected void requstBitmap(final Context context, String path, final BitmapCallBackView backView) {
        if (!doOpenNetWork(context)){
            backView.onError("");
            return;
        }
        OkGo.<Bitmap>get(path)
                .tag(context)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        Bitmap body = response.body();
                        backView.onSuccess(body);
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        backView.onError(response.message());

                    }
                });
    }

    /**
     * 请求app 适配器
     *
     * @param context 上下文
     * @param path    地址
     * @param view    返回接口
     */
    protected void sendRequestConfig(Context context, String path, final StringCallBackView view) {
        if (!doOpenNetWork(context)){
            view.onError("");
            return;
        }
        String string = context.getResources().getString(R.string.app_content_heat);
        String concat = string.concat(path);
        OkGo.<String>get(concat)
                .tag(context)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        view.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        view.onError(response.message());
                    }
                });

    }

    /**
     * @param context
     * @param path     下载路经
     * @param savePath 下载的文件夹
     * @param view
     */
    protected void sendFileDown(Context context, String path, String savePath, final FileDownView view) {
        GetRequest<File> request = OkGo.<File>get(path)
                .tag(context);
        DownloadTask task = OkDownload.request("", request)
                .extra1(path)
                .folder(savePath)
                .save().register(new LogDowbLoadListener(view));
        task.start();


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

    /**
     * 添加参数
     *
     * @param context
     * @param param
     */
    private void addParams(Context context, JsonObject param) {
        String newType = Utils.getNewType(context);
        param.addProperty(DataMessageVo.HTTP_AC, newType);
        String versionName = Utils.getVersionName(context);
        param.addProperty(DataMessageVo.HTTP_VERSION_NAME, versionName);
        int code = Utils.getVersionCode(context);
        param.addProperty(DataMessageVo.HTTP_VERSION_CODE, String.valueOf(code));
        param.addProperty(DataMessageVo.HTTP_DEVICE_PLATFORM, "android");
        String model = Utils.getSystemModel();
        param.addProperty(DataMessageVo.HTTP_DEVICE_TYPE, model);
        String brand = Utils.getDeviceBrand();
        param.addProperty(DataMessageVo.HTTP_DEVICE_BRAND, brand);
        String systemVersion = Utils.getSystemVersion();
        param.addProperty(DataMessageVo.HTTP_OS_VERSION, systemVersion);
        String dp = Utils.getdp(context);
        param.addProperty(DataMessageVo.HTTP_RESOLUTION, dp);
        String dpi = Utils.getdpi(context);
        param.addProperty(DataMessageVo.HTTP_DPI, dpi);
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

    public GetParamVo getParamVo() {
        return new GetParamVo();
    }

    public ArrayList<GetParamVo> getListParamVo() {
        return new ArrayList<GetParamVo>();
    }

    public String getUrl(Context context, int str) {
        return context.getResources().getString(str);
    }

    /**
     * 是否登录
     *
     * @param context
     * @return
     */
    public UserInfomVo isLogin(Context context) {
        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        if (userInfom == null) {
            T.showToast(context, context.getResources().getString(R.string.please_login));
            MyAppliction.getInstance().startLogin(context);
            return null;
        }
        return userInfom;
    }

    public JSONObject getJsonObj() {
        return new JSONObject();
    }


    public void addPage(ArrayList<GetParamVo> list, int page) {
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("page");
        paramVo.setValue(String.valueOf(page));
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("pagesize");
        paramVo1.setValue("10");
        list.add(paramVo);
        list.add(paramVo1);
    }

    private boolean doOpenNetWork(Context context) {
        return PolyvSDKUtil.isOpenNetwork(context);
    }
}