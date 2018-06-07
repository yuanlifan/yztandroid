package com.ylfcf.yzt.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author yangjinxin  create by 2017/8/29 13:20
 * @Description
 */
public class BasePagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<BaseViewPagerFragment> mFragments;
    public        String[]                         mTitles;


    public BasePagerAdapter(FragmentManager fm, ArrayList<BaseViewPagerFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public void setTitles(String[] titles) {
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size() == 0 ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles != null && position < mTitles.length ? mTitles[position] : "";
    }

}
