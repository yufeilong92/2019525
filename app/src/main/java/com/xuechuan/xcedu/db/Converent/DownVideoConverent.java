package com.xuechuan.xcedu.db.Converent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.Db.UserLookVo;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db.Converent
 * @Description: 下载数据库
 * @author: L-BackPacker
 * @date: 2018/5/18 10:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DownVideoConverent implements PropertyConverter<List<DownVideoVo>, String> {
    @Override
    public List<DownVideoVo> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null)
            return null;
        else {
            Type type = new TypeToken<ArrayList<DownVideoVo>>() {
            }.getType();
            ArrayList<DownVideoVo> list = new Gson().fromJson(databaseValue, type);
            return list;
        }
    }

    @Override
    public String convertToDatabaseValue(List<DownVideoVo> entityProperty) {
        if (entityProperty == null)
            return null;
        else
            return new Gson().toJson(entityProperty);
    }
}
