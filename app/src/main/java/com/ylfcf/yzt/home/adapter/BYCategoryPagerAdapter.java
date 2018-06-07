package com.ylfcf.yzt.home.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;

import com.ylfcf.yzt.base.BasePagerAdapter;
import com.ylfcf.yzt.base.BaseViewPagerFragment;

import java.util.ArrayList;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by yjx on 2018/6/7.
 */

public class BYCategoryPagerAdapter extends BasePagerAdapter implements TabAdapter {

    public BYCategoryPagerAdapter(FragmentManager fm, ArrayList<BaseViewPagerFragment> fragments) {
        super(fm, fragments);
    }

    @Override
    public TabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public TabView.TabTitle getTitle(int position) {
        return new TabView.TabTitle.Builder()
                .setContent(mTitles[position])
                .setTextSize(14)
                .setTextColor(Color.parseColor("#dc3f3f"), Color.parseColor("#333333"))
                .build();
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }
}
