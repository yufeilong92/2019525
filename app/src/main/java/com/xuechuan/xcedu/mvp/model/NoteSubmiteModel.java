package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.NoteSubmiteContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.02 下午 4:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class NoteSubmiteModel implements NoteSubmiteContract.Model {
    @Override
    public void submiteNote(Context context,int id, int question, String content, final RequestResulteView view) {
        BankService service = BankService.getInstance(context);
        service.submitNoteContent(id,question, content, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
              view.error(msg);
            }
        });
    }
}
