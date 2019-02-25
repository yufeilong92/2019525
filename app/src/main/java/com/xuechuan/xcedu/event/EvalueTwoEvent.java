package com.xuechuan.xcedu.event;

import com.xuechuan.xcedu.vo.EvalueNewVo;
import com.xuechuan.xcedu.vo.EvalueVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.Event
 * @Description: 二级评价
 * @author: L-BackPacker
 * @date: 2018/5/7 17:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvalueTwoEvent {
    EvalueNewVo.DatasBean bean;

    public EvalueTwoEvent(EvalueNewVo.DatasBean bean) {
        this.bean = bean;
    }

    public EvalueNewVo.DatasBean getBean() {
        return bean;
    }
}
