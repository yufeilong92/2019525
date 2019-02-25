package com.xuechuan.xcedu.utils;

import android.content.Context;

import com.xuechuan.xcedu.vo.DetailsBeanMainVo;
import com.xuechuan.xcedu.vo.GiftVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/27 16:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OrderUtil {


    public static List<GiftVo> getGifvos(List<DetailsBeanMainVo> data) {
        ArrayList<GiftVo> vos = new ArrayList<>();
        if (data == null || data.isEmpty()) return vos;

        for (int i = 0; i < data.size(); i++) {
            DetailsBeanMainVo vo = data.get(i);
            GiftVo giftVo = new GiftVo();
            giftVo.setId(vo.getProductid());
            giftVo.setMainProducts(true);
            giftVo.setState(vo.getState());
            giftVo.setName(vo.getProductname());
            giftVo.setPrice(vo.getPrice());
            giftVo.setNum(vo.getNum());
            giftVo.setItemtype(vo.getItemtype());
            giftVo.setCoverimg(vo.getCoverimg());
            vos.add(giftVo);
        }
        return vos;
    }

}
