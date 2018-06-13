package com.ylfcf.yzt.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseFragment;
import com.ylfcf.yzt.base.BasePagerAdapter;
import com.ylfcf.yzt.base.BaseViewPagerFragment;
import com.ylfcf.yzt.bycategory.BYSearchActivity;
import com.ylfcf.yzt.home.adapter.BYCategoryPagerAdapter;
import com.ylfcf.yzt.home.itemfragment.YztCategotyItemFragment;
import com.ylfcf.yzt.view.verticalviewpager.VerticalViewPager;
import com.ylfcf.yzt.view.verticalviewpager.transforms.DefaultTransformer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class YztCategoryFragment extends BaseFragment {


    @Bind(R.id.tablayout)
    VerticalTabLayout mTablayout;
    @Bind(R.id.viewpager)
    VerticalViewPager mViewpager;
    @Bind(R.id.tv_search)
    TextView          mTvSearch;

    private ArrayList<BaseViewPagerFragment> fragments = new ArrayList<>();
    private String[]                         arr       = {"绿色", "智慧", "健康", "轻奢", "财富", "生活"};

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

    @OnClick(R.id.tv_search)
    public void onClick() {
        startActivity(new Intent(mContext, BYSearchActivity.class));
    }

}
