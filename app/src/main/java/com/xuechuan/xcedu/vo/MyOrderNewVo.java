package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 我的订单新
 * @author: L-BackPacker
 * @date: 2018/8/27 10:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyOrderNewVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * createtime : 2018-08-27 10:16:39
         * details : [{"coverimg":"http://192.168.1.111:8081/upload/image/201806/28/a521c73086a94750a89edf454c16496b.png","itemtype":60,"num":1,"price":0,"productid":37,"productname":"测试产品","state":12}]
         * discounts : 0.0
         * gifts : [{"coverimg":"https://img.yzcdn.cn/upload_files/2017/05/22/FuyWLeN5Zm9oXwVFbLM60qAdGaHx.png","express_company":"","express_number":"","giftid":3,"giftname":"现货发售丨注册消防工程师综合案例分析","itemtype":0,"num":1,"price":0,"state":0},{"coverimg":"https://img.yzcdn.cn/upload_files/2018/07/14/FiYAbH2xeADbo98c9jcaeB5ZNkzJ.jpg","express_company":"","express_number":"","giftid":4,"giftname":"预售丨2018版《注册消防工程师考试一本通》，通关必备！","itemtype":0,"num":1,"price":0,"state":0}]
         * ordernum : 18082710163970733
         * ordertype : 2
         * state : -1
         * totalprice : 0.0
         * yzstate :
         */

        private String createtime;
        private double discounts;
        private String ordernum;
        /**
         * 1- 有赞订单 2 -内部订单
         */
        private int ordertype;
        private int state;
        private double totalprice;
        private String yzstate;
        private List<DetailsBeanMainVo> details;
        /**
         *有改动 gifts -->books
         */
        private List<GiftVo> books;

        public List<GiftVo> getBooks() {
            return books;
        }

        public void setBooks(List<GiftVo> books) {
            this.books = books;
        }

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

        public int getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(int ordertype) {
            this.ordertype = ordertype;
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

        public String getYzstate() {
            return yzstate;
        }

        public void setYzstate(String yzstate) {
            this.yzstate = yzstate;
        }

        public List<DetailsBeanMainVo> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBeanMainVo> details) {
            this.details = details;
        }

        public List<GiftVo> getGifts() {
            return getBooks();
        }

        public void setGifts(List<GiftVo> gifts) {
            this.books = gifts;
        }




    }
}
