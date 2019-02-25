package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 二级评价内容
 * @author: L-BackPacker
 * @date: 2018/6/2 8:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvalueInfomDataVo extends BaseVo {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private TargetcommentBeanVo targetcomment;
        @SerializedName("total")
        private TotalBeanVo totalX;
        private List<CommentcommentsVo> commentcomments;

        public TargetcommentBeanVo getTargetcomment() {
            return targetcomment;
        }

        public void setTargetcomment(TargetcommentBeanVo targetcomment) {
            this.targetcomment = targetcomment;
        }

        public TotalBeanVo getTotalX() {
            return totalX;
        }

        public void setTotalX(TotalBeanVo totalX) {
            this.totalX = totalX;
        }

        public List<CommentcommentsVo> getCommentcomments() {
            return commentcomments;
        }

        public void setCommentcomments(List<CommentcommentsVo> commentcomments) {
            this.commentcomments = commentcomments;
        }

    }
}
