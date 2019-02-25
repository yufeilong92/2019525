package com.xuechuan.xcedu.db.Converent;

import com.google.gson.Gson;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db.Converent
 * @Description: 用户信息
 * @author: L-BackPacker
 * @date: 2018/5/9 15:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserConverent implements PropertyConverter<UserInfomVo, String> {
    @Override
    public UserInfomVo convertToEntityProperty(String databaseValue) {
        if (databaseValue == null)
            return null;
        else {
            return new Gson().fromJson(databaseValue, UserInfomVo.class);
        }
    }


    @Override
    public String convertToDatabaseValue(UserInfomVo entityProperty) {
        if (entityProperty == null)
            return null;
        else
            return new Gson().toJson(entityProperty);
    }
}
