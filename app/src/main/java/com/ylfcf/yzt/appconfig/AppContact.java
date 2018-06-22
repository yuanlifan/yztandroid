package com.ylfcf.yzt.appconfig;

import android.os.Environment;

/**
 * @author yangjinxin  create by 2017/8/28 15:57
 * @Description
 */
public class AppContact {
    public static final String PARAM_KEY_ID                   = "id";//用户id

    // 手机网络类型
    public static final int    NETTYPE_WIFI                   = 0x01;
    public static final int    NETTYPE_CMWAP                  = 0x02;
    public static final int    NETTYPE_CMNET                  = 0x03;

    public static final int    PAGE_SIZE                      = 20;//每页请求数量
    public static final String APP_WEB_START_PREFIX           = "http://";//网址开始前缀
    public static final String APP_LOCAL_PICTURE_START_PREFIX = "file://";//本地文件开始前缀
    public static final int APP_PASSWORD_LIMIT_LENGTH = 6;//密码最小长度为6位
    public static final long APP_SEND_CODE_TIMER = 60 * 1000;//验证码发送间隔时间60S


}
