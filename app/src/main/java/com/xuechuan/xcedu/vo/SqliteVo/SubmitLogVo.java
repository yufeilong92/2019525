package com.xuechuan.xcedu.vo.SqliteVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: 进度提交记录时间表
 * @author: L-BackPacker
 * @date: 2019.01.08 上午 10:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class SubmitLogVo {
    private int saffid;
    private String dobanktime;
    private String errortime;
    private String collecttime;
    private String progresstime;
    private int id;
    private int error;
    private int collect;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaffid() {
        return saffid;
    }

    public void setSaffid(int saffid) {
        this.saffid = saffid;
    }

    public String getDobanktime() {
        return dobanktime;
    }

    public void setDobanktime(String dobanktime) {
        this.dobanktime = dobanktime;
    }

    public String getErrortime() {
        return errortime;
    }

    public void setErrortime(String errortime) {
        this.errortime = errortime;
    }

    public String getCollecttime() {
        return collecttime;
    }

    public void setCollecttime(String collecttime) {
        this.collecttime = collecttime;
    }

    public String getProgresstime() {
        return progresstime;
    }

    public void setProgresstime(String progresstime) {
        this.progresstime = progresstime;
    }
}
