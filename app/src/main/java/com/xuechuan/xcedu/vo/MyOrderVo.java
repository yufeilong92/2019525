package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 订单详情
 * @author: L-BackPacker
 * @date: 2018/5/26 14:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyOrderVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean implements Serializable{
        /**
         * 下单时间
         */
        private String createtime;
        /**
         * 	优惠
         */
        private double discounts;
        /**
         * 	订单编号
         */
        private String ordernum;
        /**
         * 状态码
         */
        private int state;
        /**
         * 总价
         */
        private double totalprice;
        /**
         * 购买物品详情
         */
        private List<OrderDetailVo> details;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public double getDiscounts() {
            return discounts;
        }

        public void setDiscounts(double discounts) {
            this.discounts = discounts;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(double totalprice) {
            this.totalprice = totalprice;
        }

        public List<OrderDetailVo> getDetails() {
            return details;
        }

        public void setDetails(List<OrderDetailVo> details) {
            this.details = details;
        }

    }
}
