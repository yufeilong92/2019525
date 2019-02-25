package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.easefun.polyvsdk.PolyvDevMountInfo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 读取保利视频日志
 * @author: L-BackPacker
 * @date: 2018/7/6 14:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class FileReadUtil {
    private Context mContext;
    private static FileReadUtil service;

    public FileReadUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static FileReadUtil getInstance(Context context) {
        if (service == null) {
            service = new FileReadUtil(context);
        }
        return service;
    }

    public String loadFromSdFile() {
        String path1 = "";
        String path2 = "";
        String path3 = "";

        if (Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED)) {
            path1 = PolyvDevMountInfo.getInstance().getInternalSDCardPath() + File.separator + "polyvdownload/polyvlog/";
            File file = new File(path1);
            //记录位置
            ArrayList<Integer> listPosition = new ArrayList<>();
            //去除-排队
            ArrayList<String> listName = new ArrayList<>();
            if (file.exists()) {//是否存在
                int posttion = -1;
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    return null;
                }
                for (int i = 0; i < files.length; i++) {
                    File file1 = files[i];
                    String name = file1.getName();
                    String replace = name.replace("-", "");
                    listPosition.add(i);
                    listName.add(replace);
                }
                String max = Collections.max(listName);
                //找到最大值获取路径
                Log.d("===", "loadFromSdFile: " + max);
                if (listName.size() > 0)
                    for (int i = 0; i < listName.size(); i++) {
                        if (listName.get(i).equals(max)) {
                            posttion = i;
                        }
                    }
                if (posttion != -1) {
                    File file1 = files[posttion];
                    path2 = file1.getName();
                    String[] list = file1.list();
                    ArrayList<String> strings = new ArrayList<>();
                    ArrayList<String> nameLists = new ArrayList<>();
                    if (list == null || list.length == 0) {
                        return null;
                    }
                    for (int i = 0; i < list.length; i++) {
                        String name = list[i];
                        String substring = name.substring(10, name.length() - 4);
                        strings.add(substring);
                        nameLists.add(name);
                    }
                    String max1 = Collections.max(strings);
                    int postion = -1;
                    for (int i = 0; i < strings.size(); i++) {
                        if (strings.get(i).equals(max1)) {
                            postion = i;
                        }
                    }
                    if (postion != -1) {
                        path3 = nameLists.get(postion);
                    }
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(path1+"/");
                    buffer.append(path2+"/");
                    buffer.append(path3);
                    return readFile(buffer.toString());
                }

            } else {
                return null;
            }
        }
        return null;

    }

    public String readFile(String path) {
        File targetFile = new File(path);
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(targetFile));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String readline = "";
            StringBuffer sb = new StringBuffer();
            while ((readline = reader.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }
            reader.close();
            System.out.println("读取成功：" + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
