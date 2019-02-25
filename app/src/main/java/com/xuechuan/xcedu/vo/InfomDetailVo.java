package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 资讯详情Vo
 * @author: L-BackPacker
 * @date: 2018/6/1 10:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class InfomDetailVo extends BaseVo {

    /**
     * data : {"id":16,"issupport":true,"publishdate":"2018-05-30 18:11:34","supportcount":3,"title":"徐州市消防项目：徐州市市民文化活动中心(肖庄)地块","type":0,"viewcount":0}
     */

    private ArticleBean data;

    public ArticleBean getData() {
        return data;
    }

    public void setData(ArticleBean data) {
        this.data = data;
    }


}
