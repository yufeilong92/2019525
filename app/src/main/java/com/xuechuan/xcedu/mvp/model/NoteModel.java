package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.NoteContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 5:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NoteModel implements NoteContract.Model {
    @Override
    public void SubmiteQuestionNote(Context context, int quesitonid, String content, RequestResulteView resulteView) {

    }

    @Override
    public void requestQuesitonNoteList(Context context, int questionid, RequestResulteView resulteView) {

    }

    @Override
    public void requestionQuestionNoteNumber(Context context, int questionid, RequestResulteView RequestResulteView) {

    }
}
