package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.11 下午 2:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ProblemDetailVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * content : 测试图片
         * id : 17
         * imgs : ["http://192.168.1.111:8081/upload/prolemimg/201901/11/4245b15ba47c42c3baabd43f26043ef9.jpg","http://192.168.1.111:8081/upload/prolemimg/201901/11/1c6f2151a1294369b106b682f9141215.jpg","http://192.168.1.111:8081/upload/prolemimg/201901/11/f9a6277eb0994f60a0a696b5551619ef.jpg"]
         * isadmin : false
         * lastcomment :
         * problemid : 17
         * problemstatus : 0
         * shareurl :
         * teacherimg :
         * teachername :
         */

        private String content;
        private int id;
        private boolean isadmin;
        private String lastcomment;
        private int problemid;
        private int problemstatus;
        private String shareurl;
        private String teacherimg;
        private String teachername;
        private List<String> imgs;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsadmin() {
            return isadmin;
        }

        public void setIsadmin(boolean isadmin) {
            this.isadmin = isadmin;
        }

        public String getLastcomment() {
            return lastcomment;
        }

        public void setLastcomment(String lastcomment) {
            this.lastcomment = lastcomment;
        }

        public int getProblemid() {
            return problemid;
        }

        public void setProblemid(int problemid) {
            this.problemid = problemid;
        }

        public int getProblemstatus() {
            return problemstatus;
        }

        public void setProblemstatus(int problemstatus) {
            this.problemstatus = problemstatus;
        }

        public String getShareurl() {
            return shareurl;
        }

        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }

        public String getTeacherimg() {
            return teacherimg;
        }

        public void setTeacherimg(String teacherimg) {
            this.teacherimg = teacherimg;
        }

        public String getTeachername() {
            return teachername;
        }

        public void setTeachername(String teachername) {
            this.teachername = teachername;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
