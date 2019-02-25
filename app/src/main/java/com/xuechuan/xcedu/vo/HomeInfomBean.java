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
 * @date: 2018.11.28 上午 9:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeInfomBean extends BaseVo{


    /**
     * data : {"chapters":[{"chapterid":1061,"chaptername":"第一篇 民用建筑防火案例\t","courseid":3,"sort":0,"videos":[{"chapterid":1061,"istrysee":true,"sort":10,"speaker":"","timelong":0,"vid":"d740a56357236a631a36eba53bea37bf_d","videoid":13531,"videoname":"01 民用建筑的分类和高度计算"},{"chapterid":1061,"istrysee":false,"sort":10,"speaker":"","timelong":0,"vid":"d740a56357e39f1f71fa73ea2cc6c234_d","videoid":13533,"videoname":"02 民用建筑构件的耐火性能和层数"},{"chapterid":1061,"istrysee":false,"sort":9,"speaker":"","timelong":0,"vid":"d740a56357099e9124d6acbcdc121c3e_d","videoid":13530,"videoname":"03 民用建筑总平面布局"},{"chapterid":1061,"istrysee":false,"sort":9,"speaker":"","timelong":0,"vid":"d740a563579624b4839381f0ff944dd7_d","videoid":13532,"videoname":"04 民用建筑平面布置"},{"chapterid":1061,"istrysee":false,"sort":8,"speaker":"","timelong":0,"vid":"d740a5635799e9cba0c9c2efb91ce946_d","videoid":13544,"videoname":"05 民用建筑防火分区"},{"chapterid":1061,"istrysee":false,"sort":7,"speaker":"","timelong":0,"vid":"d740a563576450d175e6a7238e28ca81_d","videoid":13542,"videoname":"06 民用建筑防火分隔"},{"chapterid":1061,"istrysee":false,"sort":6,"speaker":"","timelong":0,"vid":"d740a563572cd82284e163e3b268220d_d","videoid":13541,"videoname":"07 民用建筑疏散总宽度与疏散距离"},{"chapterid":1061,"istrysee":false,"sort":5,"speaker":"","timelong":0,"vid":"d740a56357165f87eb19750caa262f43_d","videoid":13540,"videoname":"08 民用建筑安全出口与疏散门"},{"chapterid":1061,"istrysee":false,"sort":4,"speaker":"","timelong":0,"vid":"d740a563576ecc7903cc1ab424c54adf_d","videoid":13543,"videoname":"09 民用建筑疏散楼梯与消防电梯"},{"chapterid":1061,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a56357e8a0249284c19061dcc9ef_d","videoid":13553,"videoname":"10 民用建筑内部装修和保温系统"}]},{"chapterid":1065,"chaptername":"第二篇 工业建筑防火案例","courseid":3,"sort":0,"videos":[{"chapterid":1065,"istrysee":false,"sort":100,"speaker":"","timelong":0,"vid":"d740a563570d8829d254db563063a7d1_d","videoid":13554,"videoname":"01 工业建筑耐火性能和层数"},{"chapterid":1065,"istrysee":false,"sort":99,"speaker":"","timelong":0,"vid":"d740a563578824f916ac732e915fcee0_d","videoid":13556,"videoname":"02 工业建筑防火间距"},{"chapterid":1065,"istrysee":true,"sort":0,"speaker":"","timelong":0,"vid":"d740a56357173b8a78dcafa94cb90f72_d","videoid":13555,"videoname":"03 工业建筑平面布置"},{"chapterid":1065,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a5635756e25265005c75d2d5f07c_d","videoid":13600,"videoname":"04 工业建筑防火分隔与安全疏散"},{"chapterid":1065,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a563578e03e53fccfe2431afc8ae_d","videoid":13601,"videoname":"05 工业建筑防爆"}]},{"chapterid":1068,"chaptername":"第三篇  消防设施案例分析","courseid":3,"sort":0,"videos":[{"chapterid":1068,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a563573c8fda33bafeec9d93ee0c_d","videoid":13613,"videoname":"01 消防给水（上）"},{"chapterid":1068,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a563572008d6989b19b39cd7c8f1_d","videoid":13614,"videoname":"02  消防给水（下）"}]}],"childrenproduct":[],"courseid":3,"coverimg":"http://192.168.1.111:8081/upload/image/201806/19/6fa08729cbb5481688d81438c381cc34.png","createtime":"2018-05-07 14:32:56","description":"","detailurl":"http://192.168.1.111:8081/product.html?id=7","gifts":[],"hastrysee":false,"id":7,"ispackage":false,"ispublish":true,"name":"2018案例分析精讲班","people":0,"price":1500,"subtitle":"","tagids":[],"teachers":[{"createtime":"2018-11-27 00:00:00","description":"老师简介","headimg":"http://192.168.1.111:8081/upload/teacher/whm.png","headimg_small":"","id":1,"name":"付老师","realname":"付老师","score":0,"title":"简介"}],"valueaddservice":"","years":[]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * chapters : [{"chapterid":1061,"chaptername":"第一篇 民用建筑防火案例\t","courseid":3,"sort":0,"videos":[{"chapterid":1061,"istrysee":true,"sort":10,"speaker":"","timelong":0,"vid":"d740a56357236a631a36eba53bea37bf_d","videoid":13531,"videoname":"01 民用建筑的分类和高度计算"},{"chapterid":1061,"istrysee":false,"sort":10,"speaker":"","timelong":0,"vid":"d740a56357e39f1f71fa73ea2cc6c234_d","videoid":13533,"videoname":"02 民用建筑构件的耐火性能和层数"},{"chapterid":1061,"istrysee":false,"sort":9,"speaker":"","timelong":0,"vid":"d740a56357099e9124d6acbcdc121c3e_d","videoid":13530,"videoname":"03 民用建筑总平面布局"},{"chapterid":1061,"istrysee":false,"sort":9,"speaker":"","timelong":0,"vid":"d740a563579624b4839381f0ff944dd7_d","videoid":13532,"videoname":"04 民用建筑平面布置"},{"chapterid":1061,"istrysee":false,"sort":8,"speaker":"","timelong":0,"vid":"d740a5635799e9cba0c9c2efb91ce946_d","videoid":13544,"videoname":"05 民用建筑防火分区"},{"chapterid":1061,"istrysee":false,"sort":7,"speaker":"","timelong":0,"vid":"d740a563576450d175e6a7238e28ca81_d","videoid":13542,"videoname":"06 民用建筑防火分隔"},{"chapterid":1061,"istrysee":false,"sort":6,"speaker":"","timelong":0,"vid":"d740a563572cd82284e163e3b268220d_d","videoid":13541,"videoname":"07 民用建筑疏散总宽度与疏散距离"},{"chapterid":1061,"istrysee":false,"sort":5,"speaker":"","timelong":0,"vid":"d740a56357165f87eb19750caa262f43_d","videoid":13540,"videoname":"08 民用建筑安全出口与疏散门"},{"chapterid":1061,"istrysee":false,"sort":4,"speaker":"","timelong":0,"vid":"d740a563576ecc7903cc1ab424c54adf_d","videoid":13543,"videoname":"09 民用建筑疏散楼梯与消防电梯"},{"chapterid":1061,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a56357e8a0249284c19061dcc9ef_d","videoid":13553,"videoname":"10 民用建筑内部装修和保温系统"}]},{"chapterid":1065,"chaptername":"第二篇 工业建筑防火案例","courseid":3,"sort":0,"videos":[{"chapterid":1065,"istrysee":false,"sort":100,"speaker":"","timelong":0,"vid":"d740a563570d8829d254db563063a7d1_d","videoid":13554,"videoname":"01 工业建筑耐火性能和层数"},{"chapterid":1065,"istrysee":false,"sort":99,"speaker":"","timelong":0,"vid":"d740a563578824f916ac732e915fcee0_d","videoid":13556,"videoname":"02 工业建筑防火间距"},{"chapterid":1065,"istrysee":true,"sort":0,"speaker":"","timelong":0,"vid":"d740a56357173b8a78dcafa94cb90f72_d","videoid":13555,"videoname":"03 工业建筑平面布置"},{"chapterid":1065,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a5635756e25265005c75d2d5f07c_d","videoid":13600,"videoname":"04 工业建筑防火分隔与安全疏散"},{"chapterid":1065,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a563578e03e53fccfe2431afc8ae_d","videoid":13601,"videoname":"05 工业建筑防爆"}]},{"chapterid":1068,"chaptername":"第三篇  消防设施案例分析","courseid":3,"sort":0,"videos":[{"chapterid":1068,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a563573c8fda33bafeec9d93ee0c_d","videoid":13613,"videoname":"01 消防给水（上）"},{"chapterid":1068,"istrysee":false,"sort":0,"speaker":"","timelong":0,"vid":"d740a563572008d6989b19b39cd7c8f1_d","videoid":13614,"videoname":"02  消防给水（下）"}]}]
         * childrenproduct : []
         * courseid : 3
         * coverimg : http://192.168.1.111:8081/upload/image/201806/19/6fa08729cbb5481688d81438c381cc34.png
         * createtime : 2018-05-07 14:32:56
         * description :
         * detailurl : http://192.168.1.111:8081/product.html?id=7
         * gifts : []
         * hastrysee : false
         * id : 7
         * ispackage : false
         * ispublish : true
         * name : 2018案例分析精讲班
         * people : 0
         * price : 1500.0
         * subtitle :
         * tagids : []
         * teachers : [{"createtime":"2018-11-27 00:00:00","description":"老师简介","headimg":"http://192.168.1.111:8081/upload/teacher/whm.png","headimg_small":"","id":1,"name":"付老师","realname":"付老师","score":0,"title":"简介"}]
         * valueaddservice :
         * years : []
         */

        private int courseid;
        private String coverimg;
        private String createtime;
        private String description;
        private String detailurl;
        private boolean hastrysee;
        private int id;
        private boolean ispackage;
        private boolean ispublish;
        private String name;
        private int people;
        private double price;
        private String subtitle;
        private String valueaddservice;
        private List<ChaptersBeanVo> chapters;
        private List<HomeInfomBean.DataBean> childrenproduct;
        private List<GiftVo> gifts;
//        private List<?> tagids;
        private List<TeachersBean> teachers;
        private List<Integer> years;

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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPeople() {
            return people;
        }

        public void setPeople(int people) {
            this.people = people;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getValueaddservice() {
            return valueaddservice;
        }

        public void setValueaddservice(String valueaddservice) {
            this.valueaddservice = valueaddservice;
        }

        public List<ChaptersBeanVo> getChapters() {
            return chapters;
        }

        public void setChapters(List<ChaptersBeanVo> chapters) {
            this.chapters = chapters;
        }

        public List<HomeInfomBean.DataBean> getChildrenproduct() {
            return childrenproduct;
        }

        public void setChildrenproduct(List<HomeInfomBean.DataBean> childrenproduct) {
            this.childrenproduct = childrenproduct;
        }

        public List<GiftVo> getGifts() {
            return gifts;
        }

        public void setGifts(List<GiftVo> gifts) {
            this.gifts = gifts;
        }

/*        public List<?> getTagids() {
            return tagids;
        }

        public void setTagids(List<?> tagids) {
            this.tagids = tagids;
        }*/

        public List<TeachersBean> getTeachers() {
            return teachers;
        }

        public void setTeachers(List<TeachersBean> teachers) {
            this.teachers = teachers;
        }

        public List<Integer> getYears() {
            return years;
        }

        public void setYears(List<Integer> years) {
            this.years = years;
        }
    }
}
