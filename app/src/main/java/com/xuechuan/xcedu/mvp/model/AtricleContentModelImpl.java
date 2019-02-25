package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AtricleContentModelImpl.java
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 数据
 * @author: YFL
 * @date: 2018/5/1 12:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/1 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class AtricleContentModelImpl implements AtricleImodel  {


    @Override
    public void getAtricleContent(Context context, String id,final RequestResulteView view) {
        BankService service = new BankService(context);
        service.requestChapterList(id, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                view.success(response);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }
        });
    }
}
