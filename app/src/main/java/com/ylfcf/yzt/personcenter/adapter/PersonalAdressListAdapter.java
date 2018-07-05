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
public class PersonalAdressListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<String> mList;

    public PersonalAdressListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        mList =data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_editor = helper.getView(R.id.tv_editor);
        TextView tv_delete = helper.getView(R.id.tv_delete);
        LinearLayout ll_item_bottom = helper.getView(R.id.ll_item_bottom);

        if(item.equals("1")) {
            ll_item_bottom.setVisibility(View.INVISIBLE);
        }else {
            ll_item_bottom.setVisibility(View.VISIBLE);
        }

    }

    public void initEditor() {
        for (int i = 0; i < mList.size(); i++) {
            mList.set(i, "0");
        }
         PersonalAdressListAdapter.this.notifyDataSetChanged();
    }

    public void closeEditor() {
        for (int i = 0; i < mList.size(); i++) {
            mList.set(i, "1");
        }
        PersonalAdressListAdapter.this.notifyDataSetChanged();
    }

}
