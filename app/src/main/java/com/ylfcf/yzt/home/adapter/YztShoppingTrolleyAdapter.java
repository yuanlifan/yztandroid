package com.ylfcf.yzt.home.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ylfcf.yzt.R;

import java.util.List;

/**
 * Created by yjx on 2018/6/27.
 */
public class YztShoppingTrolleyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<String> mList;

    public YztShoppingTrolleyAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        mList =data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        CheckBox checkBox = helper.getView(R.id.cb_choose_item);
        if(item.equals("1")) {
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }

        TextView tv_remove_trolley = helper.getView(R.id.tv_remove_trolley);
        final TextView tv_number_trolley = helper.getView(R.id.tv_number_trolley);
        TextView tv_add_trolley = helper.getView(R.id.tv_add_trolley);

        tv_remove_trolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int parseInt = Integer.parseInt(tv_number_trolley.getText().toString());
                if(parseInt > 1) {
                    parseInt--;
                }
                tv_number_trolley.setText(parseInt + "");
            }
        });

        tv_add_trolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int parseInt = Integer.parseInt(tv_number_trolley.getText().toString());
                parseInt++;
                tv_number_trolley.setText(parseInt + "");
            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                boolean isAll = true;
                if(isChecked) {
                    mList.set(helper.getPosition(), "1");
                    for (String s : mList) {
                        if(s.equals("0")) {
                            isAll = false;
                        }
                    }
                    if(isAll) {
                        if(mOnChooseAllChangeListener != null) {
                            mOnChooseAllChangeListener.isChooseAll(true);
                        }
                    }
                }else {
                    for (String s : mList) {
                        if(s.equals("0")) {
                            isAll = false;
                        }
                    }
                    mList.set(helper.getPosition(), "0");
                    if(isAll) {
                        if(mOnChooseAllChangeListener != null) {
                            mOnChooseAllChangeListener.isChooseAll(false);
                        }
                    }
                }
            }
        });

    }

    public void setAllChoose() {
        for (int i = 0; i < mList.size(); i++) {
            mList.set(i,"1");
        }
        this.notifyDataSetChanged();
    }

    public void setAllUnchoose() {
        for (int i = 0; i < mList.size(); i++) {
            mList.set(i,"0");
        }
        this.notifyDataSetChanged();
    }

    private OnChooseAllChangeListener mOnChooseAllChangeListener;

    public interface OnChooseAllChangeListener {

        public void isChooseAll(boolean isAllChoose);

    }

    public void setOnChooseAllChangeListener(OnChooseAllChangeListener onChooseAllChangeListener) {
        mOnChooseAllChangeListener = onChooseAllChangeListener;
    }


}
