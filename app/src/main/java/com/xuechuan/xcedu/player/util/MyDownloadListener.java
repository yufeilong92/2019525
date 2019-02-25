package com.xuechuan.xcedu.player.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.easefun.polyvsdk.PolyvDownloaderErrorReason;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderProgressListener;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.player.Db.PolyvDownloadInfo;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;

import java.lang.ref.WeakReference;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.player.util
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/21 17:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyDownloadListener implements IPolyvDownloaderProgressListener {
    private final DownVideoDb downVideDb;
    private long total;
    private WeakReference<Context> contextWeakReference;
    private DownVideoVo downloadInfo;
    private final DbHelperDownAssist mDao;

    public MyDownloadListener(Context context, DownVideoDb db, DownVideoVo downloadInfo) {
        this.contextWeakReference = new WeakReference<>(context);
        this.downloadInfo = downloadInfo;
        this.downVideDb = db;
        mDao = MyAppliction.getInstance().getDownDao();
//        mDao = DbHelperDownAssist.getInstance();
    }

    @Override
    public void onDownloadSuccess() {
        if (total == 0)
            total = 1;
//        mDao.addUpDataItem(downVideDb, downloadInfo, total, total);
    }

    @Override
    public void onDownloadFail(@NonNull PolyvDownloaderErrorReason errorReason) {
        String errorMsg = PolyvErrorMessageUtils.getDownloaderErrorMessage(errorReason.getType());
        if (contextWeakReference.get() != null)
            Toast.makeText(contextWeakReference.get(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDownload(long current, long total) {
        this.total = total;
    }
}