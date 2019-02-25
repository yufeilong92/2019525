package com.xuechuan.xcedu.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.view.StringCallBackUpView;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: 我的个人
 * @author: L-BackPacker
 * @date: 2018/5/22 10:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MeService extends BaseHttpServcie {
private Context mContext;
    private static MeService service;

    public MeService(Context mContext) {
        this.mContext = mContext;
    }

    public static MeService getInstance(Context context) {
        if (service == null)
            service = new MeService(context);
        return service;
    }

    /**
     * 兑换码兑换
     *
     * @param code
     */
    public void requestExchange(String code, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_exchangepost);
        requestHttpServciePost(mContext, url, object,
                true, view);

    }

    /**
     * sn码正版授权验证
     *
     * @param code
     */
    public void requestSncode(String code, StringCallBackView view) {
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
        paramVo1.setParam("code");
        paramVo1.setValue(code);
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_get_sncode);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);

    }

    /**
     * 用户建议提交接口
     *
     * @param content
     */
    public void submitAdvice(String content, String link, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("content", content);
            object.put("link", link);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_post_advice);
        requestHttpServciePost(mContext, url, object, true, view);

    }

    /**
     * 获取订单信息
     *
     * @param orderstate
     */
    public void requestOrder(int page, String orderstate, StringCallBackView view) {
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
        paramVo1.setParam("orderstate");
        paramVo1.setValue(orderstate);
        listParamVo.add(paramVo1);

        addPage(listParamVo, page);

//        String url = getUrl(mContext, R.string.http_get_order);
        String url = getUrl(mContext, R.string.http_get_v2order);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 获取我的通知
     */
    public void requestMembernotification(StringCallBackView view) {
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

        String url = getUrl(mContext, R.string.http_m_membernotification);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 标记订单状态（取消订单，删除订单）
     *
     * @param ordernunm 订单编号
     * @param usetype   delete删除订单，cancel取消订单
     * @param view
     */
    public void submitMarkorder(String ordernunm, String usetype, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("ordernum", ordernunm);
            object.put("usetype", usetype);

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String url = getUrl(mContext, R.string.http_m_markorder);
        String url = getUrl(mContext, R.string.http_m_v2markorder);
        requestHttpServciePost(mContext, url, object, true, view);

    }

    /**
     * 用户信息修改接口
     *
     * @param nickname 匿名
     * @param gender   1，男2女
     * @param birthday 生日
     * @param province 省
     * @param city     市
     * @param view
     */
    public void submitChangememberinfo(String nickname,
                                       int gender, String birthday,
                                       String province,
                                       String city,
                                       StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("nickname", nickname);
            object.put("gender", gender);
            object.put("birthday", birthday);
            object.put("province", province);
            object.put("city", city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_m_submitInfom);
        requestHttpServciePost(mContext, url, object, true, view);
    }

    /**
     * 修改用户头像接口
     *
     * @param view
     */
    public void submitchangeheadimg(StringCallBackView view) {

        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JsonObject object = new JsonObject();
        object.addProperty("staffid", user.getId());
        String url = getUrl(mContext, R.string.http_m_submitInfom);
        requestHttpServciePost(mContext, url, object, true, view);
    }

    /**
     * 获取我的信息
     */
    public void requestmemberinfo(StringCallBackView view) {
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
        String url = getUrl(mContext, R.string.http_m_infomone);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    public void requestAppUpdata(final StringCallBackView view) {
        String url = getUrl(mContext, R.string.http_upApp);
        String hear = getUrl(mContext, R.string.app_content_heat);
        url = hear.concat(url);
        OkGo.<String>get(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("====", "onSuccess: " + response.body().toString());
                        view.onSuccess(response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("====", "onError: " + response.message());
                        view.onSuccess(response.message().toString());
                    }
                });
    }

    /**
     * 获取我的消息
     *
     * @param view
     */
    public void requestMyMsg(int page, StringCallBackView view) {
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
        String url = getUrl(mContext, R.string.http_my_msg);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 个人中心绑定微信
     */
    public void submitBindWechat(String code, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JsonObject object = new JsonObject();
        object.addProperty("staffid", user.getId());
        object.addProperty("code", code);
        String url = getUrl(mContext, R.string.http_bindwechat);
        requestHttpServciePost(mContext, url, object, true, view);

    }

    /**
     * 个人中心推出登陆
     */
    public void requestOutLogin(StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JsonObject object = new JsonObject();
        object.addProperty("staffid", user.getId());
        String url = getUrl(mContext, R.string.http_out_login);
        requestHttpServciePost(mContext, url, object, true, view);

    }


    /**
     * 获取系统通知
     */
    public void requestSystemMsg(int page, StringCallBackView view) {
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
        String url = getUrl(mContext, R.string.http_system_msg);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    //    删除我的消息
    public void submitDelMyMsg(List<Integer> ids, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JsonObject object = new JsonObject();
        object.addProperty("staffid", user.getId());
        JsonArray array = new JsonArray();
        for (int i = 0; i < ids.size(); i++) {
            array.add(ids.get(i));
        }
        object.add("ids", array);

        String url = getUrl(mContext, R.string.http_deletenotify);
        requestHttpServciePost(mContext, url, object, true, view);
    }

    /**
     * 删除系统通知
     *
     * @param ids
     * @param view
     */
    public void submitDelSystemMsg(List<Integer> ids, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JsonObject object = new JsonObject();
        object.addProperty("staffid", user.getId());
        JsonArray array = new JsonArray();
        for (int i = 0; i < ids.size(); i++) {
            array.add(ids.get(i));
        }
        object.add("ids", array);
        String url = getUrl(mContext, R.string.http_delsystem);
        requestHttpServciePost(mContext, url, object, true, view);
    }


    /**
     * 用户修改密码
     *
     * @param view
     */
    //    删除我的消息
    public void submitMyPsw(String oldpas, String newpas, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JsonObject object = new JsonObject();
        object.addProperty("staffid", user.getId());
        object.addProperty("oldpwd", oldpas);
        object.addProperty("newpwd", newpas);
        String url = getUrl(mContext, R.string.http_changerpwd);
        requestHttpServciePost(mContext, url, object, true, view);
    }

    /**
     * 获取用户所有地址
     */
    public void requestAllAddress(int page, StringCallBackView view) {
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
        String url = getUrl(mContext, R.string.http_allAddress);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * @param addressid   用户地址编号，新增时传0，修改时为目标标识
     * @param province    省份
     * @param city        市
     * @param area        区
     * @param address     详细地址
     * @param post        邮编
     * @param receivename 收件人
     * @param telphone    收件人联系方式
     * @param isdefault
     * @param view
     */
    public void submitCreateUpAddress(int addressid, String province, String city, String area
            , String address, String post, String receivename, String telphone, boolean isdefault, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("addressid", addressid);
            object.put("province", province);
            object.put("city", city);
            object.put("area", area);
            object.put("address", address);
            object.put("post", post);
            object.put("receivename", receivename);
            object.put("telphone", telphone);
            object.put("isdefault", isdefault);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_create_address);
        requestHttpServciePost(mContext, url, object, true, view);
    }

    /**
     * @param addressid 用户地址编号，新增时传0，修改时为目标标识
     * @param view
     */
    public void submitdefaultOrDelectAddress(int addressid, int tag, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("addressid", addressid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "";
        if (tag == 0) {
            url = getUrl(mContext, R.string.http_setdefaultaddresspost);
        } else if (tag == 1) {
            url = getUrl(mContext, R.string.http_deleteaddresspost);
        }
//      String url = getUrl(mContext, R.string.http_create_address);
        requestHttpServciePost(mContext, url, object, true, view);
    }

    /**
     * 请求用户增值服务信息
     */
    public void requestChangerInfon(String code, StringCallBackView view) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("code");
        paramVo.setValue(code);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_changerifon);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 获取会员默认地址
     */
    public void requestUserDefaultAddress(StringCallBackView view) {
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
        String url = getUrl(mContext, R.string.http_getdefaultaddress);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 根据快递单号获取物流信息 (非有赞订单)
     */
    public void requestGetorDerTraces(String compname, String code, StringCallBackView view) {

        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("compname");
        paramVo.setValue(compname);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("code");
        paramVo1.setValue(code);
        listParamVo.add(paramVo1);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_getordertraces);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 根据有赞订单号获取有赞订单物流信息
     */
    public void requestYzOrdertraces(String ordernum, StringCallBackView view) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("ordernum");
        paramVo.setValue(ordernum);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_getordertraces);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 根据有赞订单号获取有赞订单物流信息
     */
    public void requestConfirmAddress(String ordernum, int addressid, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("ordernum");
        paramVo.setValue(ordernum);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("addressid");
        paramVo2.setValue(String.valueOf(addressid));
        listParamVo.add(paramVo2);
        listParamVo.add(paramVo1);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_getordertraces);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 根据订单编号获取订单详情
     */
    public void requestGetOrderDetail (String ordernum, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("ordernum");
        paramVo.setValue(ordernum);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_getOrderdetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 根据退款Id获取退款状态
     */
    public void requestGetRefundInfom (String refundid, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("refund_id");
        paramVo.setValue(refundid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_refund_get);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     *
     * @param ordernum  订单编号
     * @param oid 子单号
     * @param view
     */
    public void submitConfirmReceive(String  ordernum, int oid, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("ordernum", ordernum);
            object.put("oid", oid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_confirmreceive);
//      String url = getUrl(mContext, R.string.http_create_address);
        requestHttpServciePost(mContext, url, object, true, view);
    }
    /**
     *
     * @param code 快递单号
     * @param view
     */
    public void submitOrderTraces (int code, StringCallBackView view) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("oid");
        paramVo.setValue(String.valueOf(code));
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_getordertraces);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 获取待地址确认赠品列表
     * @param view
     */
    public void requestWaitAddressOrder( StringCallBackView view) {
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
        String url = getUrl(mContext, R.string.httP_order_waitaddressorder);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     *
     * @param addressid 地址编号
     */
    public void submitConfirmAddress(int addressid, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("addressid", addressid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_order_wait_confirmaddress);
        requestHttpServciePost(mContext, url, object, true, view);
    }
}
