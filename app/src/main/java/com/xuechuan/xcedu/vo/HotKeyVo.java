package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 热词
 * @author: L-BackPacker
 * @date: 2018/4/19 14:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HotKeyVo extends BaseVo {

//    private TotalBean total;
    private List<String> datas;

//    public TotalBean getTotal() {
//        return total;
//    }

//    public void setTotal(TotalBean total) {
//        this.total = total;
//    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    public static class TotalBean {
        private int total;
        private int current_total;
        private int skip_total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCurrent_total() {
            return current_total;
        }

        public void setCurrent_total(int current_total) {
            this.current_total = current_total;
        }

        public int getSkip_total() {
            return skip_total;
        }

        public void setSkip_total(int skip_total) {
            this.skip_total = skip_total;
        }
    }
}
