package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.29 上午 11:57
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyClassVo extends BaseVo {

    /**
     * data : {"years":[2018,2017],"tags":[{"id":1,"tagname":"技术实务"},{"id":2,"tagname":"综合能力"},{"id":3,"tagname":"案例分析"},{"id":4,"tagname":"公用模块"}],"classes":[{"lastview":{"productid":0,"videoid":0,"progress":0,"videoname":""},"id":6,"name":"2018综合能力精讲班","subtitle":"","years":[2018],"tagids":[2],"courseid":2,"coverimg":"http://192.168.1.111:8081/upload/image/201806/19/4dadafeeecf64a14837ea6f181307b42.png","price":1200,"people":0,"ispackage":false,"ispublish":false,"description":"","createtime":"2018-05-07 14:31:51","valueaddservice":"","detailurl":"","hastrysee":false,"teachers":[{"id":1,"name":"付老师","realname":"付老师","title":"简介","description":"老师简介","headimg":"http://192.168.1.111:8081/upload/teacher/whm.png","headimg_small":"","createtime":"2018-11-27 00:00:00","score":0}],"childrenproduct":[],"gifts":[],"chapters":[]},{"lastview":{"productid":0,"videoid":0,"progress":0,"videoname":""},"id":5,"name":"2018技术实务精讲班","subtitle":"","years":[2018],"tagids":[1],"courseid":1,"coverimg":"http://192.168.1.111:8081/upload/image/201806/19/55debdb547ad4415ad2721973775264d.png","price":1200,"people":0,"ispackage":false,"ispublish":false,"description":"","createtime":"2018-05-07 14:31:10","valueaddservice":"","detailurl":"","hastrysee":false,"teachers":[{"id":1,"name":"付老师","realname":"付老师","title":"简介","description":"老师简介","headimg":"http://192.168.1.111:8081/upload/teacher/whm.png","headimg_small":"","createtime":"2018-11-27 00:00:00","score":0}],"childrenproduct":[],"gifts":[],"chapters":[]},{"lastview":{"productid":0,"videoid":0,"progress":0,"videoname":""},"id":7,"name":"2018案例分析精讲班","subtitle":"","years":[2018],"tagids":[3],"courseid":3,"coverimg":"http://192.168.1.111:8081/upload/image/201806/19/6fa08729cbb5481688d81438c381cc34.png","price":1500,"people":0,"ispackage":false,"ispublish":false,"description":"","createtime":"2018-05-07 14:32:56","valueaddservice":"","detailurl":"","hastrysee":false,"teachers":[{"id":1,"name":"付老师","realname":"付老师","title":"简介","description":"老师简介","headimg":"http://192.168.1.111:8081/upload/teacher/whm.png","headimg_small":"","createtime":"2018-11-27 00:00:00","score":0}],"childrenproduct":[],"gifts":[],"chapters":[]},{"lastview":{"productid":0,"videoid":0,"progress":0,"videoname":""},"id":14,"name":"2017技术实务全程提高班","subtitle":"","years":[2017],"tagids":[1],"courseid":1,"coverimg":"http://192.168.1.111:8081/upload/image/201806/19/188d2b712b03413ea2967ca100cdd954.png","price":1200,"people":0,"ispackage":false,"ispublish":false,"description":"","createtime":"2018-05-14 20:03:18","valueaddservice":"","detailurl":"","hastrysee":false,"teachers":[{"id":1,"name":"付老师","realname":"付老师","title":"简介","description":"老师简介","headimg":"http://192.168.1.111:8081/upload/teacher/whm.png","headimg_small":"","createtime":"2018-11-27 00:00:00","score":0}],"childrenproduct":[],"gifts":[],"chapters":[]},{"lastview":{"productid":0,"videoid":0,"progress":0,"videoname":""},"id":29,"name":"2018年技术实务考前解题班","subtitle":"","years":[2018],"tagids":[1],"courseid":1,"coverimg":"http://192.168.1.111:8081/upload/image/201806/28/9d85abda2e444990ae19687f72d24cd2.png","price":666,"people":0,"ispackage":false,"ispublish":false,"description":"","createtime":"2018-06-28 10:33:34","valueaddservice":"","detailurl":"","hastrysee":false,"teachers":[{"id":1,"name":"付老师","realname":"付老师","title":"简介","description":"老师简介","headimg":"http://192.168.1.111:8081/upload/teacher/whm.png","headimg_small":"","createtime":"2018-11-27 00:00:00","score":0}],"childrenproduct":[],"gifts":[],"chapters":[]},{"lastview":{"productid":0,"videoid":0,"progress":0,"videoname":""},"id":32,"name":"2018年技术实务规范讲解班","subtitle":"","years":[2018],"tagids":[1],"courseid":1,"coverimg":"http://192.168.1.111:8081/upload/image/201806/28/7d89826b65e5412f9e7cd28f7d3af259.png","price":1000,"people":0,"ispackage":false,"ispublish":false,"description":"","createtime":"2018-06-28 10:41:18","valueaddservice":"","detailurl":"","hastrysee":false,"teachers":[{"id":1,"name":"付老师","realname":"付老师","title":"简介","description":"老师简介","headimg":"http://192.168.1.111:8081/upload/teacher/whm.png","headimg_small":"","createtime":"2018-11-27 00:00:00","score":0}],"childrenproduct":[],"gifts":[],"chapters":[]}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        //年份
        private List<Integer> years;
        //
        private List<TagsBean> tags;
        private List<ClassBean> classes;

        public List<Integer> getYears() {
            return years;
        }

        public void setYears(List<Integer> years) {
            this.years = years;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public List<ClassBean> getClasses() {
            return classes;
        }

        public void setClasses(List<ClassBean> classes) {
            this.classes = classes;
        }

    }
}
