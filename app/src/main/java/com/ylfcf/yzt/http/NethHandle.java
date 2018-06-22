package com.ylfcf.yzt.http;


import android.os.Build;

import com.ylfcf.yzt.appconfig.AppSpContact;
import com.ylfcf.yzt.appconfig.URLConfig;
import com.ylfcf.yzt.http.base.BaseModel;
import com.ylfcf.yzt.http.model.UserLoginModel;
import com.ylfcf.yzt.utils.SharedPreferencesHelper;


/**
 * @author yangjinxin  create by 2017/8/22 19:32
 */
public class NethHandle {
    //采取单例模式
    private static NethHandle nethHandle = new NethHandle();
    private String signature, timestamp;

    public static NethHandle getNethHandle() {
        if (nethHandle == null) {
            nethHandle = new NethHandle();
        }
        return nethHandle;
    }

    private String getPhone() {
        return "手机型号: " + Build.MODEL + "\n手机API版本信息："
                + Build.VERSION.SDK_INT + "\n手机具体系统版本"
                + Build.VERSION.RELEASE + "\n手机厂商：" + android.os.Build.MANUFACTURER;
    }

//    private NethHandle() {
//        signature = DESutils.getSignature(getSignatureList("tel", getPhone(), "timestamp", String.valueOf(System.currentTimeMillis())));
//        timestamp = String.valueOf(System.currentTimeMillis());
//    }
//
//    private String getUserToken() {
//        return SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_TOKEN);
//    }
//
//    public String getUserId() {
//        return SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LOGIN_ID);
//    }

    private HttpManager getBaseParam() {
        return HttpManager.getHttpManager();
    }
//
//    private HttpManager getUserParam() {
//        return HttpManager.getHttpManager().addParam("signature", signature).addParam("timestamp", timestamp)
//                .addParam("token", getUserToken()).addParam("uid", getUserId());
//    }

    /**
     * 用户登录
     */
    public void getUserLogin(String phone, String password, MyCallBack<UserLoginModel> callBack) {
        getBaseParam().addParam("phone", phone).addParam("pwd", password).post(URLConfig.getBaseUrl() + "/users/login", callBack);
    }

    /**
     * 用户注册
     */
    public void getUserRegister(String phone, String password, MyCallBack<UserLoginModel> callBack) {
        getBaseParam().addParam("phone", phone).addParam("pwd", password).post(URLConfig.getBaseUrl() + "/users/register", callBack);
    }

    public void removeAllMessage() {
        HttpManager.getHttpManager().removeAllMessage();
    }

}
