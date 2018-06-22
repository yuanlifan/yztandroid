package com.ylfcf.yzt.appconfig;


import com.ylfcf.yzt.BuildConfig;

/**
 * @author yangjinxin  create by 2017/8/24 14:57
 * @Description
 */
public class URLConfig {

    public static String getBaseUrl() {
        return BuildConfig.DEBUG ? DEFAULT_TEST_SERVER : DEFAULT_SERVER;
    }

//    public static String getBaseMallUrl() {
//        return BuildConfig.DEBUG ? RNG_MALL_URL_TEST : RNG_MALL_URL;
//    }

    //Url拼接地址
    public static final String HTTP                = "http://";
    public static final String HTTPS               = "https://";
    public static final String DEFAULT_SERVER      = HTTPS + "devapi.benyuansh.com/api/v1";
    public static final String DEFAULT_TEST_SERVER = HTTPS + "devapi.benyuansh.com/api/v1";
//    public static final String RNG_MALL_URL_TEST   = "http://testshop.royalgroups.cn/Mobile/Index/index/uid/";
//    public static final String RNG_MALL_URL        ="http://shop.royalgroups.cn/Mobile/Index/index/uid/";
//    public static final String RNG_IMG_URL         = HTTP + "img.royalgroups.cn/";
}
