package com.xuechuan.xcedu.event;

import com.xuechuan.xcedu.vo.DayHomeBeanVo;
import com.xuechuan.xcedu.vo.DayHomeVo;
import com.xuechuan.xcedu.vo.NoteListVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.event
 * @Description: 每日一练首页
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 4:57
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DayHomeEvent {
    private int type;//1为每日一练，2为笔记总数
    private List<DayHomeBeanVo> data;
     private List<NoteListVo.DatasBean>notes;

    public DayHomeEvent(int type, List<DayHomeBeanVo> data, List<NoteListVo.DatasBean> notes) {
        this.type = type;
        this.data = data;
        this.notes = notes;
    }

    public int getType() {
        return type;
    }

    public List<NoteListVo.DatasBean> getNotes() {
        return notes;
    }

    public  List<DayHomeBeanVo> getData() {
        return data;
    }
}
