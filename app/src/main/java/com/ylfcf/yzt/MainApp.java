package com.ylfcf.yzt;

import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.ylfcf.yzt.utils.SharedPreferencesHelper;

/**
 * @author yangjinxin  create by 2017/7/11 19:55
 * @Description
 */
public class MainApp extends MultiDexApplication {
    private static Context sContext;//获取Application 上下文Contenxt对象 单例
    public static  Handler mMainHandler ;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext(); // 获取Application的上下文
        setUpSharedPreferencesHelper(getApplicationContext());//初始化SharedPreferences
        setUmengConfig();//初始化Umeng设置
        mMainHandler = new Handler(getMainLooper());
    }

    void setUmengConfig() {
        UMShareAPI.get(this);
        setUpWxApi();
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, BuildConfig.UM_KEY, BuildConfig.UM_CHINNEL));
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setDebugMode(true);
    }
    /**
     * 初始化SharedPreferences
     *
     * @param context 上下文
     */
    private void setUpSharedPreferencesHelper(Context context) {
        SharedPreferencesHelper.getInstance().Builder(context);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 获取Application 上下文
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * 获取AppApplication对象
     */
    public static MainApp getInstance() {
        return (MainApp) sContext;
    }

    /**
     * 设置Umeng微信分享Handler
     */
    private void setUpWxApi() {
        PlatformConfig.setWeixin(BuildConfig.SHARE_WEIXIN_ID, BuildConfig.SHARE_WEIXIN_SECRET);
        Config.DEBUG = BuildConfig.DEBUG;
    }

}
