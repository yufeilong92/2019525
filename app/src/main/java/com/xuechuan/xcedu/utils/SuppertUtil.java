package com.xuechuan.xcedu.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.mvp.contract.SupportContract;
import com.xuechuan.xcedu.mvp.model.SupportModel;
import com.xuechuan.xcedu.mvp.presenter.SupportPresenter;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/1 18:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SuppertUtil implements SupportContract.View {
    private Context mContext;
    private static SuppertUtil service;
    private final SupportPresenter presenter;
    private String IsSupport;



    public SuppertUtil(Context mContext) {
        this.mContext = mContext;
        presenter = new SupportPresenter();
        presenter.initModelView(new SupportModel(), this);
    }

    public static SuppertUtil getInstance(Context context) {
        if (service == null) {
            service = new SuppertUtil(context);
        }
        return service;
    }

    public void submitSupport(String targetid, String isSupport, String usetype) {
        this.IsSupport = isSupport;
        presenter.submitSupport(mContext, targetid, isSupport, usetype);
    }

    @Override
    public void SupportSuc(String con) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
/*            if (!StringUtil.isEmpty(IsSupport) && IsSupport.equals("true")) {
//                vo.getData().getStatusX();
//                vo.getData().getMessage();
//                T.showToast(mContext, "点赞成功");
            } else if (!StringUtil.isEmpty(IsSupport) && IsSupport.equals("false")) {
//                T.showToast(mContext, "取消成功");
            }*/
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void SupportErr(String con) {
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        L.e(con);
    }


}
