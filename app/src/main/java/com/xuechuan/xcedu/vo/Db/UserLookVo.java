package com.xuechuan.xcedu.vo.Db;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 用户查看记录
 * @author: L-BackPacker
 * @date: 2018/5/9 10:48
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserLookVo {
    /**
     * 章节id
     */
    private String chapterId;
    /**
     * 第几题(/第几次正确)
     */
    private String nextId;
    /**
     * 总数
     */
    private String Count;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }
}
