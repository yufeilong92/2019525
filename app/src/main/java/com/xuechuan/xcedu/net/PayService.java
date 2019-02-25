package com.xuechuan.xcedu.net;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.vo.Sites;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: 请求支付
 * @author: L-BackPacker
 * @date: 2018/5/22 17:48
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PayService extends BaseHttpServcie {
    private Context mContext;
    private static PayService service;

    public PayService(Context mContext) {
        this.mContext = mContext;
    }

    public static PayService getInstance(Context context) {
        if (service == null)
            service = new PayService(context);
        return service;
    }


    /**
     * 订单下单
     */
    public void submitPayForm(String usebalance,
                              List<Integer> products, String ordersource,
                              String remark, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("staffid",user.getId());
        jsonObject.addProperty("ordersource",ordersource);
        jsonObject.addProperty("usebalance",usebalance);
        jsonObject.addProperty("remark",remark);
        JsonArray array = new JsonArray();
        for (int i = 0; i < products.size(); i++) {
            array.add(products.get(i));
        }
        jsonObject.add("products",array);
        String url = getUrl(mContext, R.string.http_makeorderpost);
        requestHttpServciePost(mContext, url, jsonObject,
                true, view);
    }

    /**
     * 订单支付
     */
    public void submitPay(String ordernum, int paytype, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("ordernum", ordernum);
            object.put("paytype", paytype);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_pay);
        requestHttpServciePost(mContext, url, object, true, view);

    }


    /**
     * 订单下单
     */
    public void submitVideoPayForm(String usebalance,
                              List<Integer> products, String ordersource,
                              String remark,int addressid, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("staffid",user.getId());
        jsonObject.addProperty("ordersource",ordersource);
        jsonObject.addProperty("usebalance",usebalance);
        jsonObject.addProperty("remark",remark);
        jsonObject.addProperty("addressid",addressid);
        JsonArray array = new JsonArray();
        for (int i = 0; i < products.size(); i++) {
            array.add(products.get(i));
        }
        jsonObject.add("products",array);
        String url = getUrl(mContext, R.string.http_makeVideoorderpost);
        requestHttpServciePost(mContext, url, jsonObject,
                true, view);
    }


}
