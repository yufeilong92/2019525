package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 文章集合
 * @author: L-BackPacker
 * @date: 2018/4/20 9:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ArticleListVo extends BaseVo {
//    private TotalVo total;
    private List<ArticleVo> datas;

//    public TotalVo getTotal() {
//        return total;
//    }

//    public void setTotal(TotalVo total) {
//        this.total = total;
//    }

    public List<ArticleVo> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleVo> datas) {
        this.datas = datas;
    }
}
