package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 所有地址Vo
 * @author: L-BackPacker
 * @date: 2018/8/16 11:33
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AllAddressVo  extends BaseVo{

    private List<AddressItemsBean> datas;

    public List<AddressItemsBean> getDatas() {
        return datas;
    }

    public void setDatas(List<AddressItemsBean> datas) {
        this.datas = datas;
    }
}
