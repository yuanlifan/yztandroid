package com.ylfcf.yzt.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.ylfcf.yzt.AppStackManager;
import com.ylfcf.yzt.http.NethHandle;
import com.ylfcf.yzt.utils.SharedPreferencesHelper;
import com.zhy.autolayout.AutoLayoutActivity;


/**
 * @author yangjinxin  create by 2017/8/24 14:27
 * @Description
 */
public class BaseActivity extends AppCompatActivity {

    protected AppStackManager appStackManager = AppStackManager.getAppManager();
    protected NethHandle      netHandler      = NethHandle.getNethHandle();
    protected SharedPreferencesHelper mSharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appStackManager.addActivity(this);//加入栈
        mSharedPreferencesHelper = SharedPreferencesHelper.getInstance();//实例化SharedPreferencesHelper
//        PushAgent.getInstance(this).onAppStart();//友盟推送
    }


    protected void setImmerseLayout(View view) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(this.getBaseContext());
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        netHandler.removeAllMessage();
        super.onDestroy();
    }

}
