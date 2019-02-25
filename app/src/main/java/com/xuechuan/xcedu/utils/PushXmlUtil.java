package com.xuechuan.xcedu.utils;

import android.content.Context;

import com.xuechuan.xcedu.vo.ProvincesVo;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: Xml解析
 * @author: L-BackPacker
 * @date: 2018/4/10 15:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PushXmlUtil {

    /**
     * 解析后的数据集合
     */
    private ArrayList<ProvincesVo> mProvinces = null;
    private static PushXmlUtil pushXmlUtil;
    //要解析的xml名字
    private String XML = "province.xml";

    public PushXmlUtil() {
        if (mProvinces == null) {
            mProvinces = new ArrayList<>();
        } else {
            mProvinces.clear();
        }
    }

    public static PushXmlUtil getInstance() {
        if (pushXmlUtil == null) {
            pushXmlUtil = new PushXmlUtil();
        }
        return pushXmlUtil;
    }

    public ArrayList<ProvincesVo> pushXml(Context mContext) {
        if (mProvinces.size() > 0) {
            mProvinces.clear();
        }
        SAXReader reader = new SAXReader();
        try {
            InputStream inputStream = mContext.getResources().getAssets().open(XML);
            Document read = reader.read(inputStream);
            Element rootElement = read.getRootElement();
            for (Iterator i = rootElement.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
                ProvincesVo provinces = new ProvincesVo();
                for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    if (node.getName().equals("name")) {
                        provinces.setName(node.getText());
                    }
                    if (node.getName().equals("code")) {
                        provinces.setCode(node.getText());
                    }
                }
                mProvinces.add(provinces);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mProvinces;
    }

    public String getLocationCode(Context context, String province) {
        if (StringUtil.isEmpty(province)) {
            return null;
        }
        ArrayList<ProvincesVo> vos = pushXml(context);
        for (ProvincesVo vo : vos
                ) {
            if (vo.getName().contains(province)) {
                return vo.getCode();
            }
        }
        return null;
    }

    public String getLocationProvice(Context context, String code) {
        if (StringUtil.isEmpty(code)) {
            return null;
        }
        ArrayList<ProvincesVo> vos = pushXml(context);
        for (ProvincesVo vo : vos
                ) {
            if (vo.getCode().contains(code)) {
                return vo.getName();
            }
        }
        return null;
    }
}
