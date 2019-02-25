package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AtricleViewImodel.java
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description:  获取数据
 * @author: YFL
 * @date: 2018/5/1 12:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/1 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface AtricleImodel {

    public void getAtricleContent(Context context, String id, RequestResulteView view);

}
