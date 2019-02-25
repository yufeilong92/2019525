package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 5:33
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetAllVo extends BaseVo{

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 5
         * name : 2018技术实务精讲班
         * subtitle :
         * years : []
         * tagids : []
         * courseid : 1
         * coverimg :
         * price : 1200.0
         * people : 0
         * ispackage : false
         * ispublish : true
         * description :
         * createtime : 2018-05-07 14:31:10
         * valueaddservice :
         * detailurl : http://192.168.1.111:8081/product.html?id=5
         * hastrysee : false
         * teachers : [{"id":1,"name":"王老师 a","realname":"王老师 a","title":"老师 程序c","description":"简洁001a","headimg":"http://192.168.1.111:8081ceshi001 b","headimg_small":"","createtime":"2018-11-24 14:32:44","score":0}]
         * childrenproduct : []
         * gifts : []
         * chapters : []
         */

        private int id;
        private String name;
        private String subtitle;
        private int courseid;
        private String coverimg;
        private double price;
        private int people;
        private boolean ispackage;
        private boolean ispublish;
        private String description;
        private String createtime;
        private String valueaddservice;
        private String detailurl;
        private boolean hastrysee;
        private List<?> years;
        private List<?> tagids;
        private List<TeachersBean> teachers;
        private List<?> childrenproduct;
        private List<?> gifts;
        private List<?> chapters;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public int getCourseid() {
            return courseid;
        }

        public void setCourseid(int courseid) {
            this.courseid = courseid;
        }

        public String getCoverimg() {
            return coverimg;
        }

        public void setCoverimg(String coverimg) {
            this.coverimg = coverimg;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getPeople() {
            return people;
        }

        public void setPeople(int people) {
            this.people = people;
        }

        public boolean isIspackage() {
            return ispackage;
        }

        public void setIspackage(boolean ispackage) {
            this.ispackage = ispackage;
        }

        public boolean isIspublish() {
            return ispublish;
        }

        public void setIspublish(boolean ispublish) {
            this.ispublish = ispublish;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getValueaddservice() {
            return valueaddservice;
        }

        public void setValueaddservice(String valueaddservice) {
            this.valueaddservice = valueaddservice;
        }

        public String getDetailurl() {
            return detailurl;
        }

        public void setDetailurl(String detailurl) {
            this.detailurl = detailurl;
        }

        public boolean isHastrysee() {
            return hastrysee;
        }

        public void setHastrysee(boolean hastrysee) {
            this.hastrysee = hastrysee;
        }

        public List<?> getYears() {
            return years;
        }

        public void setYears(List<?> years) {
            this.years = years;
        }

        public List<?> getTagids() {
            return tagids;
        }

        public void setTagids(List<?> tagids) {
            this.tagids = tagids;
        }

        public List<TeachersBean> getTeachers() {
            return teachers;
        }

        public void setTeachers(List<TeachersBean> teachers) {
            this.teachers = teachers;
        }

        public List<?> getChildrenproduct() {
            return childrenproduct;
        }

        public void setChildrenproduct(List<?> childrenproduct) {
            this.childrenproduct = childrenproduct;
        }

        public List<?> getGifts() {
            return gifts;
        }

        public void setGifts(List<?> gifts) {
            this.gifts = gifts;
        }

        public List<?> getChapters() {
            return chapters;
        }

        public void setChapters(List<?> chapters) {
            this.chapters = chapters;
        }


    }
}
