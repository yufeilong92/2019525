package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/15 9:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class KeyboardUtil {
    private Context mContext;
    private static KeyboardUtil service;

    public KeyboardUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static KeyboardUtil getInstance(Context context) {
        if (service == null) {
            service = new KeyboardUtil(context);
        }
        return service;
    }

    private onKeySendClickListener clickSendListener;

    public interface onKeySendClickListener {
        public void onSendClickListener();
    }

    public void setSendClickListener(onKeySendClickListener clickListener) {
        this.clickSendListener = clickListener;
    }

    public void setEditTextSendKey(EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (clickSendListener != null) {
                        clickSendListener.onSendClickListener();
                        return true;
                    }
                }
                return false;
            }
        });
    }


}
