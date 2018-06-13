package com.ylfcf.yzt.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjx on 2018/6/12.
 */

public class SearchUtils {

    /**
     * 读取历史纪录
     * @param historyKey
     * @return
     */
    public static List<String> readHistory(String historyKey){
        String history = SharedPreferencesHelper.getInstance().getString(historyKey, "");
        String[] histroys = history.split(";");
        List<String> list = new ArrayList<String>();
        if(histroys.length > 0){
            for (int i = 0; i < histroys.length; i++) {
                if(histroys[i] != null && histroys[i].length()>0){
                    list.add(histroys[i]);
                }
            }
        }
        return list;
    }

    /**
     * 插入历史纪录
     * @param historyKey
     * @param value
     */
    public static void insertHistory(String historyKey, String value) {
        String history = (String) SharedPreferencesHelper.getInstance().getString(historyKey, "");
        int historyLength = (int) SharedPreferencesHelper.getInstance().getInt(historyKey + "Length", -1);
        boolean repeat = false;
        if (history != null && history.length() > 0) {
            String[] historys = history.split(";");
            for (int i = 0; i < historys.length; i++) {
                if (historys[i].equals(value)) {
                    repeat = true;
                }
            }
            if (repeat) {
                return;
            }else{
                if (historyLength == -1){
                    //当达到限定长度后，清除第一条数据没有数据默认为9
                    if (historys.length < 9) {
                        SharedPreferencesHelper.getInstance().putString(historyKey, value + ";" + history);
                    }else{
                        SharedPreferencesHelper.getInstance().putString(historyKey, value + ";" + history.substring(0, history.lastIndexOf(";")));
                    }
                }else{
                    if (historys.length < historyLength) {
                        SharedPreferencesHelper.getInstance().putString(historyKey, value + ";" + history);
                    }else{
                        SharedPreferencesHelper.getInstance().putString(historyKey, value + ";" + history.substring(0, history.lastIndexOf(";")));
                    }
                }
            }
        } else {
            SharedPreferencesHelper.getInstance().putString(historyKey, value);
        }
    }

    public static void clearHistory(String historyKey) {
        SharedPreferencesHelper.getInstance().putString(historyKey, "");
    }

    /**
     * 设置历史记录长度
     * @param context
     * @param historyKey
     * @param length
     */
    public static void setHistoryLength(Context context,String historyKey,int length){
        SharedPreferencesHelper.getInstance().putInt(historyKey + "Length", length);
    }
}
