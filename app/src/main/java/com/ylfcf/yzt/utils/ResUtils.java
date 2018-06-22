package com.ylfcf.yzt.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.ylfcf.yzt.MainApp;


/**
 * Description:  获取资源工具类
 */
public class ResUtils {

  public static  int getColor(@ColorRes int color){
    return ContextCompat.getColor(MainApp.getInstance(), color);
  }


  //获取资源字符文件
  public static String getString(@StringRes int strRes){
    return MainApp.getInstance().getString(strRes);
  }

  //获取Drawable资源
  public static Drawable getDrawable(int drawableRes) {
    return ContextCompat.getDrawable(MainApp.getInstance(), drawableRes);
  }
}
