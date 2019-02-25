package com.xuechuan.xcedu.sqlitedb;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.easefun.polyvsdk.PolyvDevMountInfo;

import java.io.File;
import java.io.IOException;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.10 上午 11:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DbPathUitl {
    private static volatile DbPathUitl _singleton;
    private Context mContext;

    private DbPathUitl(Context context) {
        this.mContext = context;
    }

    public static DbPathUitl get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (DbPathUitl.class) {
                if (_singleton == null) {
                    _singleton = new DbPathUitl(context);
                }
            }
        }
        return _singleton;
    }

    public String getDbPath() {
        //判断是否存在sd卡
        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
        if (!sdExist) {//如果不存在,
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
            return null;
        }
        //如果存在
        //获取sd卡路径
        //可移除的存储介质（例如 SD 卡），需要写入特定目录/storage/sdcard1/Android/data/包名/。
        String externalSDCardPath = PolyvDevMountInfo.getInstance().getExternalSDCardPath();
        if (!TextUtils.isEmpty(externalSDCardPath)) {
            StringBuilder dirPath = new StringBuilder();

            dirPath.append(externalSDCardPath).append(File.separator).append("Android").append(File.separator).append("data")
                    .append(File.separator).append(getPackageName()).append(File.separator).append("xuechuan/databases/");

            String path = dirPath.toString().trim();
            File saveDir = new File(path);
            if (!saveDir.exists()) {
                saveDir.mkdirs();//创建下载目录
            }
            //数据库文件是否创建成功
            boolean isFileCreateSuccess = false;
            //设置下载存储目录
            //数据库文件是否创建成功
            //判断文件是否存在，不存在则创建该文件
            File dbFile = new File(dirPath.toString().trim());
            if (!dbFile.exists()) {
                try {
                    isFileCreateSuccess = dbFile.createNewFile();//创建文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                isFileCreateSuccess = true;
            }
            //返回数据库文件对象
            if (isFileCreateSuccess)
                return dbFile.getAbsolutePath();
            else {
                return null;
            }
        }
        //如果没有可移除的存储介质（例如 SD 卡），那么一定有内部（不可移除）存储介质可用，都不可用的情况在前面判断过了。
        String pathname = PolyvDevMountInfo.getInstance().getInternalSDCardPath()
                + File.separator + "xuechuan/databases/";
        File saveDir = new File(pathname);
        //数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        if (!saveDir.exists()) {
            saveDir.mkdirs();//创建下载目录
        }
        //数据库文件是否创建成功
        //判断文件是否存在，不存在则创建该文件
        String pathname1 = PolyvDevMountInfo.getInstance().getInternalSDCardPath()
                + File.separator + "xuechuan/databases/" ;
        File dbFile = new File(pathname1);
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();//创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            isFileCreateSuccess = true;
        }
        //返回数据库文件对象
        if (isFileCreateSuccess)
            return pathname1;
        else {
            return null;
        }
    }
}
