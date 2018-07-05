package com.ylfcf.yzt.personcenter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseActivity;
import com.ylfcf.yzt.base.BasePagerAdapter;
import com.ylfcf.yzt.base.BaseViewPagerFragment;
import com.ylfcf.yzt.home.itemfragment.YztCategotyItemFragment;
import com.ylfcf.yzt.personcenter.itemfragment.PersonalOrderItemFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalOrderFormActivity extends BaseActivity {

    @Bind(R.id.tabs)
    SlidingTabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager        mViewPager;

    private ArrayList<BaseViewPagerFragment> fragments    = new ArrayList<>();
    private String[]                         mTitles      = {"全部",  "待付款", "待发货", "已发货", "待评价"};
    private int                              mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_order_form);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initFragments();
        BasePagerAdapter adapter = new BasePagerAdapter(getSupportFragmentManager(), fragments);
        adapter.setTitles(mTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setSnapOnTabClick(true);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mCurrentPage = position;
                // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
//                mViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    private void initFragments() {
        fragments.clear();
        for (int i = 0; i < 5; i++) {
            fragments.add(new PersonalOrderItemFragment());
        }
    }


}
