package com.ylfcf.yzt.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ylfcf.yzt.MainApp;
import com.ylfcf.yzt.appconfig.AppContact;
import com.ylfcf.yzt.appconfig.AppSpContact;
import com.ylfcf.yzt.login.LoginActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;


/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      15/8/14 23:21
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 15/8/14      ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class Utils {
    private static final String TAG = "Utils===";

    /**
     * 是否可以加载更多数据
     *
     * @param currentPage 当前页
     * @param totalSize   总大小
     * @return true:可以加载更多数据 false:没有更多数据
     */
    public static boolean canLoadMoreData(int currentPage, int totalSize) {
        return canLoadMoreData(currentPage, AppContact.PAGE_SIZE, totalSize);
    }

    /**
     * 是否可以加载更多数据
     *
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @param totalSize   总大小
     * @return true:可以加载更多数据 false:没有更多数据
     */
    public static boolean canLoadMoreData(int currentPage, int pageSize, int totalSize) {
        return currentPage * pageSize < totalSize;
    }

    /**
     * 是否网络图片
     *
     * @param pictureUrl 图片地址
     * @return true:网络图片 false:不是网络图片
     */
    public static boolean isWebPicture(String pictureUrl) {
        return StringHelper.notEmpty(pictureUrl) && pictureUrl.startsWith(AppContact.APP_WEB_START_PREFIX);
    }

    /**
     * 是否本地图片
     *
     * @param pictureUrl 图片地址
     * @return true:本地图片 false:不是本地图片
     */
    public static boolean isLocalPicture(String pictureUrl) {
        return StringHelper.notEmpty(pictureUrl) && pictureUrl.startsWith(AppContact.APP_LOCAL_PICTURE_START_PREFIX);
    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 把密度转换为像素
     */
    public static int dip2px(Context context, float dp) {
        final float scale = getScreenDensity(context);
        return (int) (dp * scale + 0.5);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
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


    /**
     * 隐藏输入法软键盘
     *
     * @param context     上下文
     * @param windowToken The token of the window that is making the request
     */
    public static void hideSoftInput(Context context, IBinder windowToken) {
        if (windowToken != null) {
            InputMethodManager inputManger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManger.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    //点击空白区域隐藏键盘，只要调用这个方法，传入编辑框，就Ok了
    public static void hideSoftInputOutofEditArea(EditText... editors) {
        InputMethodManager imm = (InputMethodManager) MainApp.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        for (EditText anEditor : editors) {
            if (imm != null && imm.isActive() && anEditor.isFocused() && imm.isAcceptingText()) {
                anEditor.setError(null);
                imm.hideSoftInputFromWindow(anEditor.getWindowToken(), 0);
            }
        }
    }


    /**
     * 清除用户登录信息
     */
    public static void onClearLogoutUserData() {
        SharedPreferencesHelper mSharedPreferencesHelper = SharedPreferencesHelper.getInstance();
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LOGIN_ID, "");
        String userId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LOGIN_ID);
        Log.i(TAG, "onClearLogoutUserData: -userId：" + userId);
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LOGIN_PWD, "");
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_TOKEN, "");
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_NICK_NAME, "");
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_AVATAR, "");
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1]\\d{10}";//"[1]"代表第1位为数字1，"\\d{10}"代表后面是可以是0～9的数字，有10位。
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 用户是否登录
     *
     * @return true:已登录 false:未登录
     */
    public static boolean isUserLogin() {
        String token = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_TOKEN);
        return StringHelper.notEmpty(token);
    }

    /**
     * 用户是否登录
     *
     * @return true:已登录 false:未登录
     */
    public static boolean isUserLoginToLoginActivity(Context context) {
        if (!isUserLogin()) {
            context.startActivity(new Intent(context, LoginActivity.class));
            return false;
        }
        return true;
    }

    /**
     * 生成随机字符串
     *
     * @param length 生成字符串的长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取sha1值
     *
     * @param str 需要sha1的字符串
     * @return sha1之后的字符串
     */
    public static String getSha1(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置SwipeRefreshLayout 进度条颜色
     *
     * @param mSwipeRefreshLayout
     */
    public static void setProgressColor(SwipeRefreshLayout mSwipeRefreshLayout) {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    /**
     * 隐藏软键盘
     *
     * @param act
     */
    public static void hideTheSoftKeyboard(Activity act) {
        WeakReference<Activity> act_wf = new WeakReference(act);
        if (act_wf.get() == null || act_wf.get().getCurrentFocus() == null) {
            return;
        }
        Log.i(TAG, "dispatchTouchEvent: ---:隐藏软键盘！");
        ((InputMethodManager) act_wf.get().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(act_wf.get().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 延迟时间取消ProgressDialog
     */
    public static void delayedDismiss(Handler mHandler, int time, long reqTime, int msgWhat) {
        if ((System.currentTimeMillis() - reqTime) <= 1500) {
            mHandler.sendEmptyMessageDelayed(msgWhat, time);
        }
    }

    /**
     * 将数字转换成中文  eg.123456 : 一十二万三千四百五十六;
     */
    public static String num2ChineseStr(int num) {
        // 单位数组
        String[] units = new String[]{"十", "百", "千", "万", "十", "百", "千", "亿"};

        // 中文大写数字数组
        String[] numeric = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String temp = num + "";
        String res = "";

        // 遍历一行中所有数字
        for (int k = -1; temp.length() > 0; k++) {
            // 解析最后一位
            int j = Integer.parseInt(temp.substring(temp.length() - 1, temp.length()));
            String rtemp = numeric[j];

            // 数值不是0且不是个位 或者是万位或者是亿位 则去取单位
            if (j != 0 && k != -1 || k % 8 == 3 || k % 8 == 7) {
                rtemp += units[k % 8];
            }
            // 拼在之前的前面
            res = rtemp + res;
            // 去除最后一位
            temp = temp.substring(0, temp.length() - 1);
        }
        // 去除后面连续的零零..
        while (res.endsWith(numeric[0])) {
            res = res.substring(0, res.lastIndexOf(numeric[0]));
        }
        // 将零零替换成零
        while (res.indexOf(numeric[0] + numeric[0]) != -1) {
            res = res.replaceAll(numeric[0] + numeric[0], numeric[0]);
        }
        // 将 零+某个单位 这样的窜替换成 该单位 去掉单位前面的零
        for (int m = 1; m < units.length; m++) {
            res = res.replaceAll(numeric[0] + units[m], units[m]);
        }
        // 这里打印一下 可以改成写文件
        System.out.println(num + "--获取对应中文：" + res);
        return res;
    }


    /**
     * 将数字文件按照行转换成中文  eg.123456 : 一十二万三千四百五十六;
     */

    public static String numFile2ChineseStr(String fileName) throws IOException {
        File f = new File(fileName);
        if (!f.exists()) {
            f.createNewFile();
        }
        // 单位数组
        String[] units = new String[]{"十", "百", "千", "万", "十", "百", "千", "亿"};

        // 中文大写数字数组
//        String[] numeric = new String[] {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[] numeric = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

        // 读文件
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String temp = "";
        temp = br.readLine();
        String res = "";

        while (null != temp) {
            // 遍历一行中所有数字
            for (int k = -1; temp.length() > 0; k++) {
                // 解析最后一位
                int j = Integer.parseInt(temp.substring(temp.length() - 1, temp.length()));
                String rtemp = numeric[j];
                // 数值不是0且不是个位 或者是万位或者是亿位 则去取单位
                if (j != 0 && k != -1 || k % 8 == 3 || k % 8 == 7) {
                    rtemp += units[k % 8];
                }
                // 拼在之前的前面
                res = rtemp + res;
                // 去除最后一位
                temp = temp.substring(0, temp.length() - 1);
            }
            // 去除后面连续的零零..
            while (res.endsWith(numeric[0])) {
                res = res.substring(0, res.lastIndexOf(numeric[0]));
            }
            // 将零零替换成零
            while (res.indexOf(numeric[0] + numeric[0]) != -1) {
                res = res.replaceAll(numeric[0] + numeric[0], numeric[0]);
            }
            // 将 零+某个单位 这样的窜替换成 该单位 去掉单位前面的零
            for (int m = 1; m < units.length; m++) {
                res = res.replaceAll(numeric[0] + units[m], units[m]);
            }
            // 这里打印一下 可以改成写文件
            System.out.println(temp + "--获取对应中文：" + res);
            res = "";
            temp = br.readLine();
        }
        return res;
    }

    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    @TargetApi(JELLY_BEAN)
    public static void copy(String content, Context context) {
        if (Build.VERSION.SDK_INT > JELLY_BEAN) {
            // 得到剪贴板管理器
            ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setPrimaryClip(new ClipData(ClipData.newPlainText("expressno", content)));
        } else {
            Log.i(TAG, "copy: 手机版本过低，不能复制");
        }

    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getPrimaryClip().toString().trim();
    }

    /**
     * 正则表达式验证
     *
     * @param mContext
     * @param regex
     * @param checkStr
     * @return
     */
    public static boolean checkStringByRegex(Context mContext, String regex, String checkStr) {
        try {
            boolean result = Pattern.matches(regex, checkStr);
            if (!result) {
                Toast.makeText(mContext, "用户名只能由少于12个汉字、数字或者字母组成！", Toast.LENGTH_SHORT).show();
            }
            return result;
        } catch (Exception e) {
//            ToastHelper.showIconAndTextToastCenter(false, e.getMessage());
            ToastHelper.showAlert(mContext, e.getMessage());
        }
        return false;
    }


    /**
     * 点击是否为编辑EditText区域
     *
     * @param ev
     * @param view
     * @return
     */
    public static boolean isEditTextArea(MotionEvent ev, View view) {
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        return ev.getRawX() > loc[0] && ev.getRawY() > loc[1] && ev.getRawX() < (loc[0] + view.getWidth())
                && ev.getRawY() < (loc[1] + view.getHeight());
    }

    /**
     * An {@code int} value that may be updated atomically.
     */
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1000);

    /**
     * 动态生成View ID
     * API LEVEL 17 以上View.generateViewId()生成
     * API LEVEL 17 以下需要手动生成
     */
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

    public static int getVersionCode() {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = MainApp.getContext().getPackageManager().getPackageInfo(MainApp.getContext().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 创建指定数量的随机字符串
     *
     * @param length
     * @return
     */
    public static String createRandom(int length) {
        String retStr = "";
        String strTable = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }


}
