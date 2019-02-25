package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.os.Environment;

import com.xuechuan.xcedu.vo.EncrypBaseVo;
import com.xuechuan.xcedu.vo.EncrypVo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/***
 *
 */
public class FileUtil {
    //SD卡根路径
    private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();

    //手机的缓存根目录
    private static String mDataRootPath = "";

    //保存图片的目录
    private final static String IMG_PATH = "/xuechuan/image";
    private final static String IMG_SUFFIX = ".png";


    public FileUtil(Context context) {
        mDataRootPath = context.getCacheDir().getPath();
    }

    //获取图片存储的根目录
    public static String getImgDIrectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? mSdRootPath + IMG_PATH : mDataRootPath + IMG_PATH;
    }

    public static String getImgpath(String imgName) {
        File file = new File(getImgDIrectory());
        if (!file.exists()) {
            file.mkdirs();
        }
        return getImgDIrectory() + imgName + IMG_SUFFIX;
    }

    //图片保存到本地，--返回图片的路径
    public static String saveImg(ByteArrayOutputStream bos) {
        FileOutputStream fos = null;
        String path = getImgpath(String.valueOf(System.currentTimeMillis()));
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            fos.write(bos.toByteArray());
            fos.flush();
        } catch (IOException e) {
            path = "";
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return path;

    }

    private static final String TAGNAME = "tag.txt";
    private static String DBPATHTAGPATH = "/android/tag/";

    private static String getDbEncryptionPathTag() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? mSdRootPath + DBPATHTAGPATH : mDataRootPath + DBPATHTAGPATH;
    }

    private static String getDbEncryptionTag() {
        File file = new File(getDbEncryptionPathTag());
        if (!file.exists()) {
            file.mkdirs();
        }
        return getDbEncryptionPathTag() + TAGNAME;
    }

    public static void saveContent(String content) {
        EncryptionUtil encryptionUtil = EncryptionUtil.getInstance();
        content = encryptionUtil.putStringContent(content);
        String path = getDbEncryptionTag();
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EncrypVo getSaveEncryTag() {
        StringBuffer buffer = new StringBuffer();
        String dbEncryptionTag = getDbEncryptionTag();

        try {
            FileInputStream stream = new FileInputStream(dbEncryptionTag);
            byte[] bytes = new byte[1024];
            int read = stream.read(bytes);
            while (read > 0) {
                buffer.append(new String(bytes, 0, read));
                read = stream.read(bytes);
            }
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (StringUtil.isEmpty(buffer.toString())) {
            return null;
        } else {
            EncryptionUtil encryptionUtil = EncryptionUtil.getInstance();
            String con = encryptionUtil.getContent(buffer.toString());
            EncrypVo vo = Utils.getGosnT(con, EncrypVo.class);
            return vo;
        }
    }
    public static void saveEncrypContent(EncrypBaseVo vo) {
        EncryptionUtil encryptionUtil = EncryptionUtil.getInstance();
        String content = encryptionUtil.putTContent(vo);
        String path = getDbEncryptionTag();
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EncrypBaseVo getEncrypEncryTag() {
        StringBuffer buffer = new StringBuffer();
        String dbEncryptionTag = getDbEncryptionTag();
        try {
            FileInputStream stream = new FileInputStream(dbEncryptionTag);
            byte[] bytes = new byte[1024];
            int read = stream.read(bytes);
            while (read > 0) {
                buffer.append(new String(bytes, 0, read));
                read = stream.read(bytes);
            }
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (StringUtil.isEmpty(buffer.toString())) {
            return null;
        } else {
            EncryptionUtil encryptionUtil = EncryptionUtil.getInstance();
            EncrypBaseVo content = encryptionUtil.getTContent(buffer.toString(), EncrypBaseVo.class);
            return content;
        }
    }



}
