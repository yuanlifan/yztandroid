package com.ylfcf.yzt.appconfig;

import android.os.Environment;

/**
 * @author yangjinxin  create by 2017/8/28 15:57
 * @Description
 */
public class AppContact {
    public static final String PARAM_KEY_ID                   = "id";//用户id
    public static final String PARAM_WEIBO_ID                 = "weibo_id";//微博id
    public static final String PARAM_KEY_FORUM_POST_ID        = "forumPostId";//回帖的帖子ID
    // 手机网络类型
    public static final int    NETTYPE_WIFI                   = 0x01;
    public static final int    NETTYPE_CMWAP                  = 0x02;
    public static final int    NETTYPE_CMNET                  = 0x03;
    public static final String PARAM_KEY_IS_HOME_LOGIN        = "isHomeLogin";//是否首页登录
    public static final int    PAGE_SIZE                      = 20;//每页请求数量
    public static final int    APP_PASSWORD_LIMIT_LENGTH      = 6;//密码最小长度为6位
    public static final String APP_WEB_START_PREFIX           = "http://";//网址开始前缀
    public static final String APP_LOCAL_PICTURE_START_PREFIX = "file://";//本地文件开始前缀
    public static final String PARAM_KEY_HOME_PAGER_INDEX     = "homeIndex";//主页选中项
    public static final long   APP_SEND_CODE_TIMER            = 60 * 1000;//验证码发送间隔时间60S
    public static final String FILE_DATA_ROOT_PATH            = Environment.getExternalStorageDirectory().getPath() + "/royalclub/";
    public static final String FILE_DATA_PICTURE_PATH         = FILE_DATA_ROOT_PATH + "picture/";
    public static final String FILE_DATA_AVATAR_PATH          = FILE_DATA_ROOT_PATH + "avatar/";
    public static final String FILE_DATA_AUDIO_PATH           = FILE_DATA_ROOT_PATH + "audio/";
    /** 启动页显示图片 */
    public static final String LOADING_IMAGE_PATH             = FILE_DATA_PICTURE_PATH + "loadingimage.jpeg";
    public static final String APP_USER_INFO_SCHEME           = "com.royalclub.rng.userinfo://";
    public static final String APP_WEB_LINK_SCHEME            = "http://";
    public static final String IS_FIRST_COMMENT               = "isfirstcomment";
    public static final String PARAM_KEY_PICTURES             = "pictures";//图片数组
    public static final String PARAM_KEY_PICTURE_INDEX        = "pictureIndex";//图片第几张
    public static final String PARAM_KEY_TYPE                 = "type";//主播类型
    public static final String PARAM_KEY_TAB                  = "tab";//标签类型
    public static final String PARAM_KEY_ZHUBO_ID             = "zhuboid";//用户id
    public static final int    REQUEST_INSTALL                = 100;//安装请求码

}
