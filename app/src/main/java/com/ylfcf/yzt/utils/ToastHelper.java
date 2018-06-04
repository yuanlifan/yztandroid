package com.ylfcf.yzt.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ylfcf.yzt.R;

/**
 * Description: 弹出信息工具类
 */
public class ToastHelper {

    public static void showAlert(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showAlert(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showBottomAlert(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showCustomToast(Context context, String content, int icon, int duration) {
        if (content.length() > 8) {
            content = new StringBuffer(content).insert(8, "\n").toString();
        }
        Toast toast = Toast.makeText(context, content, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        TextView tv = (TextView) toastView.getChildAt(0);
        tv.setGravity(Gravity.CENTER);
        ImageView imageCodeProject = new ImageView(context);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
        int dpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                context.getResources().getDisplayMetrics());
        if (icon == 0) {
            p.setMargins(11 * dpx, 40 * dpx, 11 * dpx, 40 * dpx);
            tv.requestLayout();
        } else {
            p.setMargins(0, 20 * dpx, 0, 0);
            tv.requestLayout();
            imageCodeProject.setImageResource(icon);
            toastView.addView(imageCodeProject, 0);
        }
        toast.show();
    }


//    //自定义文本居中Toast
//    public static Toast showOnleyTextToastCenter(String content, int time) {
//        Context context = MainApp.getInstance();
//        Toast toast = new Toast(context);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.toast_only_text, null);
//        TextView txt = (TextView) view.findViewById(R.id.tv_dialog_content);
//        txt.setText(content);
//        toast.setDuration(time);
//        toast.setView(view);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//        return toast;
//    }
//
//    //自定义Icon+文本居中Toast
//    public static Toast showIconAndTextToastCenter(boolean isSuccess, String content) {
//        Context context = MainApp.getInstance();
//        Toast toast = new Toast(context);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.toast_icon_text, null);
//        TextView txt = (TextView) view.findViewById(R.id.tv_toast_content);
//        ImageView icon = (ImageView) view.findViewById(R.id.img_toast_icon);
//        txt.setText(content);
//        if (isSuccess)
//            icon.setImageResource(R.drawable.icon_succeed);
//        else
//            icon.setImageResource(R.drawable.icon_wrong);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(view);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//        return toast;
//    }
//
//    public static Toast showIconToastCenter(@DrawableRes int img) {
//        Context context = MainApp.getInstance();
//        Toast toast = new Toast(context);
//        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflate.inflate(R.layout.toast_icon, null); //找到自己定义的布局文件
//        ImageView tv = (ImageView) view.findViewById(R.id.tv_msg);
//        Bitmap source= BitmapFactory.decodeResource(context.getResources(),img);
//        tv.setImageBitmap(source);
//        toast.setView(view);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//        return toast;
//    }
}
