package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 商品地址确认
 * @author: L-BackPacker
 * @date: 2018/8/30 16:33
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class WaitAddressVo extends BaseVo {


    /**
     * data : {"gifts":[{"oid":34,"giftid":4,"giftname":"预售丨2018版《注册消防工程师考试一本通》，通关必备！","itemtype":0,"price":0,"num":0,"coverimg":"https://img.yzcdn.cn/upload_files/2018/07/14/FiYAbH2xeADbo98c9jcaeB5ZNkzJ.jpg","express_company":"","express_number":"","state":1},{"oid":33,"giftid":3,"giftname":"现货发售丨注册消防工程师综合案例分析","itemtype":0,"price":0,"num":0,"coverimg":"https://img.yzcdn.cn/upload_files/2017/05/22/FuyWLeN5Zm9oXwVFbLM60qAdGaHx.png","express_company":"","express_number":"","state":1},{"oid":6,"giftid":4,"giftname":"预售丨2018版《注册消防工程师考试一本通》，通关必备！","itemtype":0,"price":0,"num":0,"coverimg":"https://img.yzcdn.cn/upload_files/2018/07/14/FiYAbH2xeADbo98c9jcaeB5ZNkzJ.jpg","express_company":"","express_number":"","state":1},{"oid":5,"giftid":3,"giftname":"现货发售丨注册消防工程师综合案例分析","itemtype":0,"price":0,"num":0,"coverimg":"https://img.yzcdn.cn/upload_files/2017/05/22/FuyWLeN5Zm9oXwVFbLM60qAdGaHx.png","express_company":"","express_number":"","state":1}],"address_id":26,"receiver_name":"于飞龙","receiver_mobile":"18317837561","receiver_state":"河南省","receiver_city":"郑州市","receiver_district":"金水区","receiver_address":"白庙小区701"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * gifts : [{"oid":34,"giftid":4,"giftname":"预售丨2018版《注册消防工程师考试一本通》，通关必备！","itemtype":0,"price":0,"num":0,"coverimg":"https://img.yzcdn.cn/upload_files/2018/07/14/FiYAbH2xeADbo98c9jcaeB5ZNkzJ.jpg","express_company":"","express_number":"","state":1},{"oid":33,"giftid":3,"giftname":"现货发售丨注册消防工程师综合案例分析","itemtype":0,"price":0,"num":0,"coverimg":"https://img.yzcdn.cn/upload_files/2017/05/22/FuyWLeN5Zm9oXwVFbLM60qAdGaHx.png","express_company":"","express_number":"","state":1},{"oid":6,"giftid":4,"giftname":"预售丨2018版《注册消防工程师考试一本通》，通关必备！","itemtype":0,"price":0,"num":0,"coverimg":"https://img.yzcdn.cn/upload_files/2018/07/14/FiYAbH2xeADbo98c9jcaeB5ZNkzJ.jpg","express_company":"","express_number":"","state":1},{"oid":5,"giftid":3,"giftname":"现货发售丨注册消防工程师综合案例分析","itemtype":0,"price":0,"num":0,"coverimg":"https://img.yzcdn.cn/upload_files/2017/05/22/FuyWLeN5Zm9oXwVFbLM60qAdGaHx.png","express_company":"","express_number":"","state":1}]
         * address_id : 26
         * receiver_name : 于飞龙
         * receiver_mobile : 18317837561
         * receiver_state : 河南省
         * receiver_city : 郑州市
         * receiver_district : 金水区
         * receiver_address : 白庙小区701
         */

        private int address_id;
        private String receiver_name;
        private String receiver_mobile;
        private String receiver_state;
        private String receiver_city;
        private String receiver_district;
        private String receiver_address;
        /**
         * 有改动 gifts-->books
         */
        private List<GiftVo> books;

        public List<GiftVo> getBooks() {
            return books;
        }

        public void setBooks(List<GiftVo> books) {
            this.books = books;
        }

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getReceiver_mobile() {
            return receiver_mobile;
        }

        public void setReceiver_mobile(String receiver_mobile) {
            this.receiver_mobile = receiver_mobile;
        }

        public String getReceiver_state() {
            return receiver_state;
        }

        public void setReceiver_state(String receiver_state) {
            this.receiver_state = receiver_state;
        }

        public String getReceiver_city() {
            return receiver_city;
        }

        public void setReceiver_city(String receiver_city) {
            this.receiver_city = receiver_city;
        }

        public String getReceiver_district() {
            return receiver_district;
        }

        public void setReceiver_district(String receiver_district) {
            this.receiver_district = receiver_district;
        }

        public String getReceiver_address() {
            return receiver_address;
        }

        public void setReceiver_address(String receiver_address) {
            this.receiver_address = receiver_address;
        }

        public List<GiftVo> getGifts() {
            return getBooks();
        }

        public void setGifts(List<GiftVo> gifts) {
            this.books = gifts;
        }


    }
}
