package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 网课
 * @author: L-BackPacker
 * @date: 2018/5/14 9:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetHomeVo extends BaseVo {

    private List<CoursesBeanVo> datas;

    public List<CoursesBeanVo> getDatas() {
        return datas;
    }

    public void setDatas(List<CoursesBeanVo> datas) {
        this.datas = datas;
    }
}
