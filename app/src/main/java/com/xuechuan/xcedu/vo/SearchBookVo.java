package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 4:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SearchBookVo  extends BaseVo{


    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 1
         * alias : 1ygbein47xogg
         * item_id : 403649709
         * youzanurl : https://h5.youzan.com/v2/showcase/goods?alias=1ygbein47xogg
         * price : 6900.0
         * title : 【学川商城】必备四大规范之GB 50261-2017 自动喷水灭火系统施工及验收规范
         * img : https://img.yzcdn.cn/upload_files/2018/01/01/FqZuj21YkYYhuWnXmqTlvq-HdTft.jpg?imageView2/0/w/240/h/240/q/75
         */

        private int id;
        private String alias;
        private String item_id;
        private String youzanurl;
        private double price;
        private String title;
        private String img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getYouzanurl() {
            return youzanurl;
        }

        public void setYouzanurl(String youzanurl) {
            this.youzanurl = youzanurl;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
