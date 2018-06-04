package com.ylfcf.yzt.utils;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Description: 字符串帮助类
 */
public class StringHelper {
    public static String getStr(String str) {
        return isEmpty(str) ? "" : str;
    }

    public static String getEditTextContent(EditText edt) {
        return edt.getText().toString().trim();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("null") || str.trim().equals("");
    }

    public static boolean isEditTextEmpty(EditText edt) {
        return isEmpty(getEditTextContent(edt));
    }

    public static boolean isPhoneEditTextEmpty(EditText edt) {
        return isEmpty(getEditTextContent(edt)) || getEditTextContent(edt).length() != 11;
    }

    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getTextViewContent(TextView tv) {
        return tv.getText().toString().trim();
    }

    public static String getTextLikeCount(long count) {
        String likeText = String.valueOf(count);
        if (count > 1000 && count <= 10000) {
            likeText = String.format("%1$.1f K", count / 1000f);
        } else if (count > 10000 && count <= 10000000) {
            likeText = String.format("%1$.2f 万", count / 10 * 1000f);
        } else if (count > 10000000) {
            likeText = String.format("%1$.2f 千万", count / 10 * 1000 * 1000f);
        }
        return likeText;
    }
}
