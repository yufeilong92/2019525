package com.xuechuan.xcedu.event;

import com.xuechuan.xcedu.vo.QuestionAllVo;
import com.xuechuan.xcedu.vo.QuestionsBean;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 自由组卷
 * @author: L-BackPacker
 * @date: 2018/5/5 14:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class FreeDataEvent {
   private List<QuestionsBean> data;

    public FreeDataEvent(List<QuestionsBean> data) {
        this.data = data;
    }

    public List<QuestionsBean> getData() {
        return data;
    }
}
