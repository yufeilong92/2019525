package com.xuechuan.xcedu.utils;

import com.xuechuan.xcedu.base.DataMessageVo;

import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 加密工具
 * @author: L-BackPacker
 * @date: 2018/4/27 13:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EncryptionUtil {
    private static EncryptionUtil s_SharedTextListUtil;


    /**
     * 获取唯一的instance
     *
     * @return
     */
    public static EncryptionUtil getInstance() {
        return s_SharedTextListUtil = new EncryptionUtil();
    }

    /**
     * 获取加密后的类型
     *
     * @param content
     * @return
     */
    public String putStringContent(String content) {
        String str = "";
        try {
            str = SerializableUtil.obj2Str(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取加密的数据
     *
     * @param content
     * @return
     */
    public String getContent(String content) {
        String data = "";
        try {
            Object o = SerializableUtil.str2Obj(content);
            if (o != null) {
                data = (String) o;
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取加密的数据
     *
     * @param content
     * @return
     */
    public <T> T getTContent(String content, Class<T> c) {
        T data = null;
        try {
            Object o = SerializableUtil.str2Obj(content);
            if (o != null) {
                data = (T) o;
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return c.cast(data);
    }

    /**
     * 获取加密后的类型
     *
     * @param content
     * @return
     */
    public <T> String putTContent(T content) {
        String str = "";
        try {
            str = SerializableUtil.obj2Str(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String D(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0
                ) {
            return null;
        }
        //取“_xuechuanedu.cn_”转化成byte[]
        String key = "_xuechuanedu.cn_";
        byte[] arrayOfByte = key.getBytes();
        int i = 0;
        while (i < paramArrayOfByte.length) {
            //重新计算byte[i]的值
            paramArrayOfByte[i] = ((byte) (paramArrayOfByte[i] ^ arrayOfByte[(i % arrayOfByte.length)]));
            i += 1;
        }

        //转化计算后的byte[]成字符串类型
        String s = new String(paramArrayOfByte);
        return s;
    }


    public String getUserBuy(String buy) {
        String s = putStringContent(buy.concat(DataMessageVo.BUYSKILL));
        String md5String = Md5.getMD5String(s);
        return md5String;
    }

}
