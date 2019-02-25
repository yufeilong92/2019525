package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.AtricleImodel;
import com.xuechuan.xcedu.mvp.view.AtricleView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: Atriclepresenter.java
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 适配
 * @author: YFL
 * @date: 2018/5/1 12:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/1 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class AtriclePresenter {
    private AtricleImodel atricleViewImodel;
    private AtricleView atricleView;

    public AtriclePresenter(AtricleImodel atricleViewImodel, AtricleView atricleView) {
        this.atricleViewImodel = atricleViewImodel;
        this.atricleView = atricleView;
    }

    public void getAtricleContent(Context context, String id) {
        if (StringUtil.isEmpty(id)) {
            return;
        }
        atricleViewImodel.getAtricleContent(context, id, new RequestResulteView() {
            @Override
            public void success(String result) {
                atricleView.Success(result);
            }
            @Override
            public void error(String result) {
                atricleView.Error(result);
            }
        });
    }


}
