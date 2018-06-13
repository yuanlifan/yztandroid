package com.ylfcf.yzt.bycategory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.bycategory.BYSearchActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by yjx on 2018/6/13.
 */

public class BySearchTagAdapter extends TagAdapter<String> {

    private Context mContext;

    public BySearchTagAdapter(List<String> datas, Context context) {
        super(datas);
        mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, String history_item) {
        TextView tv_history = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_layout_history, null, false);
        tv_history.setText(history_item);
        return tv_history;
    }

}
