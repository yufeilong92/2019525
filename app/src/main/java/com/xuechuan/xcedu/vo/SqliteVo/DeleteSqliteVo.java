package com.xuechuan.xcedu.vo.SqliteVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: 删除表帮助类
 * @author: L-BackPacker
 * @date: 2018.12.11 上午 11:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DeleteSqliteVo {
    private int id;
    private String type;
    /*
    复制时用的
     */
    private int delect_id;

    public int getDelect_id() {
        return delect_id;
    }

    public void setDelect_id(int delect_id) {
        this.delect_id = delect_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
