package com.ylfcf.yzt.personcenter.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ylfcf.yzt.R;

import java.util.List;

/**
 * Created by yjx on 2018/6/26.
 */
public class PersonalOrderItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<String> mList;

    public PersonalOrderItemAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        mList =data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }


}
