package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 网课列表Vo
 * @author: L-BackPacker
 * @date: 2018/5/14 17:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetBookListVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * chapters : [{"chapterid":1066,"chaptername":"试听","courseid":1,"videos":[{"chapterid":1066,"istrysee":true,"speaker":"孟老师","timelong":454,"vid":"d740a56357adad903a8a886c7018fb2f_d","videoid":14011,"videoname":"【试听】湿式报警阀组"},{"chapterid":1066,"istrysee":true,"speaker":"孟老师","timelong":1177,"vid":"d740a563575a5917ce916c14ead79af3_d","videoid":14012,"videoname":"【试听】洒水喷头"},{"chapterid":1066,"istrysee":true,"speaker":"学川教育\t","timelong":1582,"vid":"d740a5635771d82c106d21de1de6e97d_d","videoid":14188,"videoname":"【试听】第二篇第六章 安全疏散基本参数（上）"}]},{"chapterid":1070,"chaptername":"第一篇 消防基础知识","courseid":1,"videos":[{"chapterid":1070,"istrysee":false,"speaker":"学川教育","timelong":953,"vid":"d740a56357938f8707b74f8c7066da11_d","videoid":14114,"videoname":"01 第一篇第一章 消防基础知识--燃烧条件"},{"chapterid":1070,"istrysee":false,"speaker":"学川教育","timelong":1058,"vid":"d740a56357c9f64ca70c4e62474051e4_d","videoid":14120,"videoname":"07 第一篇第三章 爆炸基础知识（上）"}]}]
         * class : {"courseid":1,"coverimg":"http://192.168.1.111:8080/imgs/201805/07/828aa63ee48b4bb19cbc30e8257be8fb.png","description":"<p>技术实务<br/><\/p>","id":5,"isall":false,"name":"2018技术实务网课","price":1500}
         */

        @SerializedName("class")
        private ClassBean classX;
        private List<ChaptersBean> chapters;

        public ClassBean getClassX() {
            return classX;
        }

        public void setClassX(ClassBean classX) {
            this.classX = classX;
        }

        public List<ChaptersBean> getChapters() {
            return chapters;
        }

        public void setChapters(List<ChaptersBean> chapters) {
            this.chapters = chapters;
        }

        public static class ClassBean {
            /**
             * courseid : 1
             * coverimg : http://192.168.1.111:8080/imgs/201805/07/828aa63ee48b4bb19cbc30e8257be8fb.png
             * description : <p>技术实务<br/></p>
             * id : 5
             * isall : false
             * name : 2018技术实务网课
             * price : 1500.0
             */

            private int courseid;
            private String coverimg;
            private String description;
            private int id;
            private boolean isall;
            private String name;
            private double price;

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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIsall() {
                return isall;
            }

            public void setIsall(boolean isall) {
                this.isall = isall;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }

        public static class ChaptersBean {
            /**
             * chapterid : 1066
             * chaptername : 试听
             * courseid : 1
             * videos : [{"chapterid":1066,"istrysee":true,"speaker":"孟老师","timelong":454,"vid":"d740a56357adad903a8a886c7018fb2f_d","videoid":14011,"videoname":"【试听】湿式报警阀组"},{"chapterid":1066,"istrysee":true,"speaker":"孟老师","timelong":1177,"vid":"d740a563575a5917ce916c14ead79af3_d","videoid":14012,"videoname":"【试听】洒水喷头"},{"chapterid":1066,"istrysee":true,"speaker":"学川教育\t","timelong":1582,"vid":"d740a5635771d82c106d21de1de6e97d_d","videoid":14188,"videoname":"【试听】第二篇第六章 安全疏散基本参数（上）"}]
             */

            private int chapterid;
            private String chaptername;
            private int courseid;
            private List<VideosBean> videos;

            public int getChapterid() {
                return chapterid;
            }

            public void setChapterid(int chapterid) {
                this.chapterid = chapterid;
            }

            public String getChaptername() {
                return chaptername;
            }

            public void setChaptername(String chaptername) {
                this.chaptername = chaptername;
            }

            public int getCourseid() {
                return courseid;
            }

            public void setCourseid(int courseid) {
                this.courseid = courseid;
            }

            public List<VideosBean> getVideos() {
                return videos;
            }

            public void setVideos(List<VideosBean> videos) {
                this.videos = videos;
            }

            public static class VideosBean {
                /**
                 * chapterid : 1066
                 * istrysee : true
                 * speaker : 孟老师
                 * timelong : 454
                 * vid : d740a56357adad903a8a886c7018fb2f_d
                 * videoid : 14011
                 * videoname : 【试听】湿式报警阀组
                 */

                private int chapterid;
                private boolean istrysee;
                private String speaker;
                private int timelong;
                private String vid;
                private int videoid;
                private String videoname;

                public int getChapterid() {
                    return chapterid;
                }

                public void setChapterid(int chapterid) {
                    this.chapterid = chapterid;
                }

                public boolean isIstrysee() {
                    return istrysee;
                }

                public void setIstrysee(boolean istrysee) {
                    this.istrysee = istrysee;
                }

                public String getSpeaker() {
                    return speaker;
                }

                public void setSpeaker(String speaker) {
                    this.speaker = speaker;
                }

                public int getTimelong() {
                    return timelong;
                }

                public void setTimelong(int timelong) {
                    this.timelong = timelong;
                }

                public String getVid() {
                    return vid;
                }

                public void setVid(String vid) {
                    this.vid = vid;
                }

                public int getVideoid() {
                    return videoid;
                }

                public void setVideoid(int videoid) {
                    this.videoid = videoid;
                }

                public String getVideoname() {
                    return videoname;
                }

                public void setVideoname(String videoname) {
                    this.videoname = videoname;
                }
            }
        }
    }
}
