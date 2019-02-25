package com.xuechuan.xcedu.db.Converent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuechuan.xcedu.vo.Db.UserLookVo;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db
 * @Description: 用户查看转换
 * @author: L-BackPacker
 * @date: 2018/5/9 10:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserLookConverent implements PropertyConverter<List<UserLookVo>, String> {

    private final Gson mGson;

    public UserLookConverent() {
        mGson = new Gson();
    }

    @Override
    public List<UserLookVo> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        } else {
            Type type = new TypeToken<ArrayList<UserLookVo>>() {
            }.getType();
            ArrayList<UserLookVo> list = new Gson().fromJson(databaseValue, type);
            return list;
        }
    }
    @Override
    public String convertToDatabaseValue(List<UserLookVo> entityProperty) {
        if (entityProperty == null) {
            return null;
        } else {
            return new Gson().toJson(entityProperty);
        }
    }
}
