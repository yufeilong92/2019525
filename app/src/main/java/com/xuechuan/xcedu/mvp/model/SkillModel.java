package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SkillModel.java
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: YFL
 * @date: 2018/5/4 00:02
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/4 星期五
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface SkillModel {
    public void reqeustErrOrCoNumber(Context context, String courseid, RequestResulteView view);
    public void getBuyInfom(Context context, String courseid, RequestResulteView view);

}
