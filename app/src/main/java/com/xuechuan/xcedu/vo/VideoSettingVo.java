package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 视频设置vo
 * @author: L-BackPacker
 * @date: 2018/6/12 18:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class VideoSettingVo extends BaseVo {


    /**
     * data : {"polyvideosetting":{"duration":30000,"interval":30000,"lifetime":2000,"style":2,"textalpha":112,"textcolor":"#808080","textsize":14,"tweentime":10000},"youzanappsetting":{"client_id":"5b613e9df350d211d8","homeurl":"https://h5.youzan.com/v2/showcase/homepage?kdt_id=948752"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * 保利设置
         */
        private PolyvideosettingBean polyvideosetting;
        /**
         * 有赞设置
         */
        private YouzanappsettingBean youzanappsetting;

        public PolyvideosettingBean getPolyvideosetting() {
            return polyvideosetting;
        }

        public void setPolyvideosetting(PolyvideosettingBean polyvideosetting) {
            this.polyvideosetting = polyvideosetting;
        }

        public YouzanappsettingBean getYouzanappsetting() {
            return youzanappsetting;
        }

        public void setYouzanappsetting(YouzanappsettingBean youzanappsetting) {
            this.youzanappsetting = youzanappsetting;
        }

        public static class PolyvideosettingBean {

            /**
             * 总时长
             */
            private int duration;
            /**
             *设置跑马灯文本隐藏间隔时间，单位为毫秒。与屏幕内随机位置闪烁的跑马灯样式相关。默认为1000
             */
            private int interval;
            /**
             * 跑马灯文本显示时间，单位为毫秒。与屏幕内随机位置闪烁的跑马灯样式相关
             */
            private int lifetime;
            /**
             * 1从右至左滚动 ，2屏幕内随机位置闪烁 3从右至左渐隐渐现的滚动
             */
            private int style;
            /**
             * 文字透明度
             */
            private int textalpha;
            /**
             *文字颜色
             */
            private String textcolor;
            /**
             *文字大小
             */
            private int textsize;
            /**
             * 渐隐渐显时间
             */
            private int tweentime;

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getInterval() {
                return interval;
            }

            public void setInterval(int interval) {
                this.interval = interval;
            }

            public int getLifetime() {
                return lifetime;
            }

            public void setLifetime(int lifetime) {
                this.lifetime = lifetime;
            }

            public int getStyle() {
                return style;
            }

            public void setStyle(int style) {
                this.style = style;
            }

            public int getTextalpha() {
                return textalpha;
            }

            public void setTextalpha(int textalpha) {
                this.textalpha = textalpha;
            }

            public String getTextcolor() {
                return textcolor;
            }

            public void setTextcolor(String textcolor) {
                this.textcolor = textcolor;
            }

            public int getTextsize() {
                return textsize;
            }

            public void setTextsize(int textsize) {
                this.textsize = textsize;
            }

            public int getTweentime() {
                return tweentime;
            }

            public void setTweentime(int tweentime) {
                this.tweentime = tweentime;
            }
        }

        public static class YouzanappsettingBean {
            /**
             * client_id : 5b613e9df350d211d8
             * homeurl : https://h5.youzan.com/v2/showcase/homepage?kdt_id=948752
             */

            private String client_id;
            private String homeurl;
            /**
             * 查看所有订单
             */
            private String orderlisturl;

            public String getOrderlisturl() {
                return orderlisturl;
            }

            public void setOrderlisturl(String orderlisturl) {
                this.orderlisturl = orderlisturl;
            }

            public String getClient_id() {
                return client_id;
            }

            public void setClient_id(String client_id) {
                this.client_id = client_id;
            }

            public String getHomeurl() {
                return homeurl;
            }

            public void setHomeurl(String homeurl) {
                this.homeurl = homeurl;
            }
        }
    }
}
