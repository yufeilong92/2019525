package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 错误信息
 * @author: L-BackPacker
 * @date: 2018/5/3 10:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrorNewVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("total")
        private int totalX;
        private List<TaglistsBeanVo> taglists;

        public int getTotalX() {
            return totalX;
        }

        public void setTotalX(int totalX) {
            this.totalX = totalX;
        }

        public List<TaglistsBeanVo> getTaglists() {
            return taglists;
        }

        public void setTaglists(List<TaglistsBeanVo> taglists) {
            this.taglists = taglists;
        }


    }
}
