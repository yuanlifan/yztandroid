package com.ylfcf.yzt.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangjinxin on 2017/06/11.
 */

public class ThreadUtil {

    /**
     * 从当前线程中获取Looper对象，作为handler执行任务的对象，如果是在主线程中new的该构造函数，那么就获取到了主线程
     * 的Looper对象，如果是在是子线程中new的，那么就去子线程中获取Looper对象，而子线程又没有Looper对象，因此构造函数就抛一个运行时异常。
     */
//        private static Handler sHandler = new Handler();
    /**
     * 通过构造函数传入Looper对象后，Handler就不会从当前线程中获取Lopper对象了，而是直接使用传入进来的Looper对象执行任务
     * 这样子的话，不管当前Handler是在哪个线程中new的,该Handler执行的任务都是由其传入的Looper决定。
     */
    private static Handler  sHandler   = new Handler(Looper.getMainLooper());
    private static Executor sExecutors = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()+1,2* Runtime.getRuntime().availableProcessors()+1,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
    public static void runOnSubThread(Runnable runnable){
     sExecutors.execute(runnable);
    }

    /**
     * 保证该任务是在主线程中被调用了
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable){
        sHandler.post(runnable);
    }

}
