package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.NoteContract;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 5:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NotePresenter implements NoteContract.Presenter {
    @Override
    public void initModelView(NoteContract.Model model, NoteContract.View view) {

    }

    @Override
    public void SubmiteQuestionNote(Context context, int quesitonid, String content) {

    }

    @Override
    public void requestQuesitonNoteList(Context context, int questionid) {

    }

    @Override
    public void requestionQuestionNoteNumber(Context context, int questionid) {

    }
}
