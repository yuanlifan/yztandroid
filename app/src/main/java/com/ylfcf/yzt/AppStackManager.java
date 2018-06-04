package com.ylfcf.yzt;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import java.util.Stack;

/**
 * @author guozhangyu  create by 2017/11/29 14:22
 * @Description
 */
public class AppStackManager {

    // Activity栈
    private static Stack<Activity> activityStack;
    // 单例模式
    private static AppStackManager instance;

    /**
     * 单一实例
     */
    public static AppStackManager getAppManager() {
        if (instance == null) {
            instance = new AppStackManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

//    /**
//     * 重新登录Activity
//     */
//    public void LoginActivity() {
//        for (int i = 0; i < activityStack.size(); i++) {
//            if (null != activityStack.get(i) && !activityStack.get(i).getClass().getName().equals(LoginActivity.class.getName())) {
//                activityStack.get(i).finish();
//            }
//        }
//    }
//
//    /**
//     * 返回首页Activity
//     */
//    public void MainActivity() {
//        for (int i = 0; i < activityStack.size(); i++) {
//            if (null != activityStack.get(i) && !activityStack.get(i).getClass().getName().equals(MainActivity.class.getName())) {
//                activityStack.get(i).finish();
//            }
//        }
//    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
