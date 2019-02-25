package com.xuechuan.xcedu.XceuAppliciton;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.XceuAppliciton
 * @Description: 常量管理类
 * @author: L-BackPacker
 * @date: 2018/4/10 8:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MessageData {
    private String SDKBUNCH = "2fwHUwyZ+PQ5ux1cTKmW8jbas6ipgXCEA2X/k/6TQag4qoZCEewdWIs/S0fIxKlPrc33enaEKN4pa+1JARRFr8OTvxhvJJZv1YkSadU0o4Tep+XFYjs0+gwwdpvrB8KRigCsQn7mvTDQ+KRaHCfFEQ==";
    private String SDKSECRETKEY = "VXtlHmwfS2oYm0CZ";
    private String SDKVECTOR = "2u9gDPKdX6GyQJKU";

    public MessageData() {
    }

    public static final MessageData getInstance() {
        return new MessageData();
    }

    public String getSdkBunc() {
        return SDKBUNCH;
    }

    public String getSdkSecretkey() {
        return SDKSECRETKEY;
    }

    public String getSdkVector() {
        return SDKVECTOR;
    }
}

