package com.xuechuan.xcedu.utils;

import android.util.Log;

import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.MyDownOverListVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/3 15:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SaveSortUtil {

    private static SaveSortUtil service;

    public SaveSortUtil() {

    }

    public static SaveSortUtil getInstance() {
        if (service == null) {
            service = new SaveSortUtil();
        }
        return service;
    }

/*    public DownVideoDb sort(DownVideoDb db) {
        List<DownVideoVo> downlist = db.getDownlist();
        if (downlist != null && !downlist.isEmpty()) {
            //找到有几篇
            ArrayList<SortData> list = new ArrayList<>();
            for (int i = 0; i < downlist.size(); i++) {
                DownVideoVo vo = downlist.get(i);
                SortData data = new SortData();
                data.setSort(vo.getPsort());
                data.setChapterid(vo.getPid());
                //篇数据

                if (list.isEmpty()) {
                    list.add(data);
                } else {
                    boolean isadd = false;
                    for (int k = 0; k < list.size(); k++) {
                        SortData sortData = list.get(k);
                        if (sortData.getChapterid().equalsIgnoreCase(data.getChapterid()) && sortData.getSort() == data.getSort()) {
                            isadd = true;
                        }

                    }
                    if (isadd) {
                        continue;
                    } else {
                        list.add(data);
                    }
                }

            }
            //倒序排序
            Collections.sort(list, new Comparator<SortData>() {
                @Override
                public int compare(SortData o1, SortData o2) {
                    return o2.getSort() - o1.getSort();
                }
            });

            ArrayList<DownVideoVo> downLists = new ArrayList<DownVideoVo>();

            //该篇下章节数据
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    SortData sortData = list.get(i);
                    //排序后找到篇
                    //临时保存
                    ArrayList<DownVideoVo> downVideoVos = new ArrayList<DownVideoVo>();
                    for (int k = 0; k < downlist.size(); k++) {
                        //对该篇下进行排序
                        DownVideoVo vo = downlist.get(k);
                        if (sortData.getSort() == vo.getPsort() && sortData.getChapterid().equalsIgnoreCase(vo.getPid())) {
                            downVideoVos.add(vo);
                        }
                    }
                    sortData(downVideoVos);
                    downLists.addAll(downVideoVos);

                }
            }
            db.setDownlist(downLists);
        }
        return db;
    }*/

    /**
     * 显示排序
     *
     * @param db
     * @return
     */
    public MyDownOverListVo psort(DownVideoDb db) {
        if (db == null || db.getDownlist() == null || db.getDownlist().isEmpty()) return null;
        List<DownVideoVo> downlist = db.getDownlist();
        //记录章节
        MyDownOverListVo my = new MyDownOverListVo();
        if (downlist != null && !downlist.isEmpty()) {
            //找到有几篇
            ArrayList<MyDownOverListVo.PdownListVo> listPVos = new ArrayList<>();
            for (int i = 0; i < downlist.size(); i++) {
                MyDownOverListVo.PdownListVo pVo = new MyDownOverListVo.PdownListVo();
                DownVideoVo vo = downlist.get(i);
                pVo.setChapterid(vo.getPid());
                pVo.setpName(vo.getpName());
                pVo.setSort(vo.getPsort());
                SortData data = new SortData();
                data.setSort(vo.getPsort());
                data.setChapterid(vo.getPid());
                //篇数据
                if (listPVos.isEmpty()) {
                    listPVos.add(pVo);
                } else {
                    boolean ispadd = false;
                    for (int k = 0; k < listPVos.size(); k++) {
                        MyDownOverListVo.PdownListVo pvo1 = listPVos.get(k);
                        if (pvo1.getChapterid().equalsIgnoreCase(data.getChapterid()) && pvo1.getSort().equals( data.getSort())) {
                            ispadd = true;
                        }

                    }
                    if (ispadd) {
                        continue;
                    } else {
                        listPVos.add(pVo);
                    }
                }

            }

            Collections.sort(listPVos, new Comparator<MyDownOverListVo.PdownListVo>() {
                @Override
                public int compare(MyDownOverListVo.PdownListVo o1, MyDownOverListVo.PdownListVo o2) {
                    int i = Integer.parseInt(o2.getSort()) -Integer.parseInt( o1.getSort());
                    if (i == 0) {
                        int k = Integer.parseInt(o1.getChapterid());
                        int l = Integer.parseInt(o2.getChapterid());
                        int j = k-l;
                        return j;
                    }
                    return i;
//                    return o2.getSort() - o1.getSort();
                }
            });
//            listPVos = TextSorting(listPVos);

            Log.d("====", "倒序排完的篇:" + listPVos);
            ArrayList<DownVideoVo> downLists = new ArrayList<DownVideoVo>();

            //该篇下章节数据
            if (!listPVos.isEmpty()) {
                for (int i = 0; i < listPVos.size(); i++) {
                    //排序后找到篇
                    MyDownOverListVo.PdownListVo pdownListVo = listPVos.get(i);
                    ArrayList<MyDownOverListVo.zDownListVo> zDownListVos = new ArrayList<>();
                    //临时保存
                    for (int k = 0; k < downlist.size(); k++) {
                        //对该篇下进行排序
                        DownVideoVo vo = downlist.get(k);
                        if (pdownListVo.getSort() .equals(vo.getPsort()) && pdownListVo.getChapterid().equalsIgnoreCase(vo.getPid())) {
                            zDownListVos.add(copyData(vo));
                        }
                    }
//                    zDownListVos = chaptersSort(zDownListVos);
                    Collections.sort(zDownListVos, new Comparator<MyDownOverListVo.zDownListVo>() {
                        @Override
                        public int compare(MyDownOverListVo.zDownListVo o1, MyDownOverListVo.zDownListVo o2) {
                            int i = Integer.parseInt(o2.getZsort()) -Integer.parseInt( o1.getZsort());
                            if (i == 0) {
                                int k = Integer.parseInt(o1.getVideoOid());
                                int l = Integer.parseInt(o2.getVideoOid());
                                int j = k- l;
                                return j;
                            }
                            return i;
// return o2.getZsort() - o1.getZsort();
                        }
                    });
                    pdownListVo.setzDownlist(zDownListVos);
                }
            }
            copyDataMu(my, db);
            my.setDownlist(listPVos);
        }
        Log.d("==", "sort: " + my);
        return my;
    }

    private void copyDataMu(MyDownOverListVo my, DownVideoDb db) {
        my.setKid(db.getKid());
        my.setkName(db.getKName());
        my.setStaffid(db.getStaffid());
        my.setTime(db.getTime());
        my.setUrlImg(db.getUrlImg());
        my.setVid(db.getVid());
    }

    private MyDownOverListVo.zDownListVo copyData(DownVideoVo vo) {
        MyDownOverListVo.zDownListVo zVo = new MyDownOverListVo.zDownListVo();
        zVo.setPid(vo.getPid());
        zVo.setpName(vo.getpName());
        zVo.setBitRate(vo.getBitRate());
        zVo.setDuration(vo.getDuration());
        zVo.setFileSize(vo.getFileSize());
        zVo.setPercent(vo.getPercent());
        zVo.setPsort(vo.getPsort());
        zVo.setStatus(vo.getStatus());
        zVo.setTitle(vo.getTitle());
        zVo.setTotal(vo.getTotal());
        zVo.setVid(vo.getVid());
        zVo.setVideoOid(vo.getVideoOid());
        zVo.setZid(vo.getZid());
        zVo.setZsort(vo.getZsort());
        return zVo;
    }

    /**
     * 对章节排序
     *
     * @param downlist
     */
    private void sortData(ArrayList<DownVideoVo> downlist) {
        Collections.sort(downlist, new Comparator<DownVideoVo>() {
            @Override
            public int compare(DownVideoVo o1, DownVideoVo o2) {
/*                如果要按照升序排序，
                则o1 小于o2，返回-1（负数），相等返回0，01大于02返回1（正数）
                如果要按照降序排序
                则o1 小于o2，返回1（正数），相等返回0，01大于02返回-1（负数）*/
                return Integer.parseInt(o1.getVideoOid()) - Integer.parseInt(o2.getVideoOid());
//                return o2.getVideoOid().compareTo(o1.getVideoOid());
            }
        });
    }

    /**
     * 排序篇
     *
     * @param argLists
     * @return
     */
    public static ArrayList<MyDownOverListVo.PdownListVo> TextSorting(ArrayList<MyDownOverListVo.PdownListVo> argLists) {
        MyDownOverListVo.PdownListVo[] args = getArr(argLists);
        //冒泡排序算法
        for (int i = 0; i < args.length - 1; i++) {

            for (int j = i + 1; j < args.length; j++) {

                if (Integer.parseInt(args[i].getChapterid()) > Integer.parseInt(args[j].getChapterid())) {

                    MyDownOverListVo.PdownListVo temp = args[i];

                    args[i] = args[j];

                    args[j] = temp;

                }
            }
        }
        return getlist(args);
    }

    /**
     * 对章节进行排序
     *
     * @param listPVos
     * @return
     */
    public static ArrayList<MyDownOverListVo.zDownListVo> chaptersSort(ArrayList<MyDownOverListVo.zDownListVo> listPVos) {
        MyDownOverListVo.zDownListVo[] args = getZArr(listPVos);
        //冒泡排序算法
        for (int i = 0; i < args.length - 1; i++) {

            for (int j = i + 1; j < args.length; j++) {

                if (Integer.parseInt(args[i].getVideoOid()) > Integer.parseInt(args[j].getVideoOid())) {

                    MyDownOverListVo.zDownListVo temp = args[i];

                    args[i] = args[j];

                    args[j] = temp;

                }
            }
        }
        return getZlist(args);
    }

    /**
     * 排序篇
     *
     * @param list
     * @return
     */
    public static MyDownOverListVo.PdownListVo[] getArr(ArrayList<MyDownOverListVo.PdownListVo> list) {
        MyDownOverListVo.PdownListVo[] downVideoVos = new MyDownOverListVo.PdownListVo[list.size()];
        for (int i = 0; i < list.size(); i++) {
            downVideoVos[i] = list.get(i);
        }
        return downVideoVos;
    }

    /**
     * 排序篇
     *
     * @param list
     * @return
     */
    public static ArrayList<MyDownOverListVo.PdownListVo> getlist(MyDownOverListVo.PdownListVo[] list) {
        ArrayList<MyDownOverListVo.PdownListVo> objects = new ArrayList<MyDownOverListVo.PdownListVo>();
        for (int i = 0; i < list.length; i++) {
            objects.add(list[i]);
        }
        return objects;
    }

    /**
     * 排序章
     *
     * @param list
     * @return
     */
    public static MyDownOverListVo.zDownListVo[] getZArr(ArrayList<MyDownOverListVo.zDownListVo> list) {
        MyDownOverListVo.zDownListVo[] downVideoVos = new MyDownOverListVo.zDownListVo[list.size()];
        for (int i = 0; i < list.size(); i++) {
            downVideoVos[i] = list.get(i);
        }
        return downVideoVos;
    }

    /**
     * 排序章
     *
     * @param list
     * @return
     */
    public static ArrayList<MyDownOverListVo.zDownListVo> getZlist(MyDownOverListVo.zDownListVo[] list) {
        ArrayList<MyDownOverListVo.zDownListVo> objects = new ArrayList<MyDownOverListVo.zDownListVo>();
        for (int i = 0; i < list.length; i++) {
            objects.add(list[i]);
        }
        return objects;
    }


    private class SortData {
        private String sort;
        private String chapterid;

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getChapterid() {
            return chapterid;
        }

        public void setChapterid(String chapterid) {
            this.chapterid = chapterid;
        }
    }
}
