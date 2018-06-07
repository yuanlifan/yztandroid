package com.ylfcf.yzt.home.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseFragment;
import com.ylfcf.yzt.base.BasePagerAdapter;
import com.ylfcf.yzt.base.BaseViewPagerFragment;
import com.ylfcf.yzt.home.adapter.BYCategoryPagerAdapter;
import com.ylfcf.yzt.home.itemfragment.YztCategotyItemFragment;
import com.ylfcf.yzt.view.verticalviewpager.VerticalViewPager;
import com.ylfcf.yzt.view.verticalviewpager.transforms.DefaultTransformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class YztCategoryFragment extends BaseFragment {


    @Bind(R.id.tablayout)
    VerticalTabLayout mTablayout;
    @Bind(R.id.viewpager)
    VerticalViewPager mViewpager;

    private ArrayList<BaseViewPagerFragment> fragments = new ArrayList<>();
    private String[]arr = {"绿色", "智慧", "健康", "轻奢", "财富", "生活"};

    @Override
    protected void initData() {
        initFragments();
        BasePagerAdapter pagerAdapter = new BYCategoryPagerAdapter(getChildFragmentManager(), fragments);
        pagerAdapter.setTitles(arr);
        mViewpager.setOffscreenPageLimit(fragments.size()); //ViewPager的缓存页面数量
        mViewpager.setAdapter(pagerAdapter);
        mViewpager.setPageTransformer(false, new DefaultTransformer());
        mTablayout.setupWithViewPager(mViewpager);
    }

    private void initFragments() {
        fragments.clear();
        for (int i = 0; i < 6; i++) {
            fragments.add(new YztCategotyItemFragment());
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_yzt_category;
    }

}
