package com.ylfcf.yzt.home.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.flyco.tablayout.SlidingTabLayout;
import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseFragment;
import com.ylfcf.yzt.base.BasePagerAdapter;
import com.ylfcf.yzt.base.BaseViewPagerFragment;
import com.ylfcf.yzt.home.itemfragment.YztCategotyItemFragment;
import com.ylfcf.yzt.utils.ToastHelper;
import com.ylfcf.yzt.view.CustomPopWindow;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class YztHomepageFragment extends BaseFragment {

    @Bind(R.id.sliding_tab)
    SlidingTabLayout mSlidingTab;
    @Bind(R.id.view_pager)
    ViewPager        mViewPager;
    @Bind(R.id.tv_pingdao)
    TextView         mTvPingdao;
    @Bind(R.id.iv_arrow)
    ImageView        mIvArrow;

    private ArrayList<BaseViewPagerFragment> fragments = new ArrayList<>();
    private String[] mTitles = {"推荐", "新品", "众筹", "福利社", "限时购", "居家", "配件", "服装", "电器", "洗护", "饮食", "文体"};
    private boolean isVisible = false;
    private int mCurrentPage = 0;
    private CustomPopWindow mPopWindow;

    @Override
    protected void initData() {
        initFragments();
        mViewPager.setAdapter(new BasePagerAdapter(getChildFragmentManager(), fragments));
        mSlidingTab.setViewPager(mViewPager, mTitles);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initFragments() {
        fragments.clear();
        for (int i = 0; i < 12; i++) {
            fragments.add(new YztCategotyItemFragment());
        }
    }

    private void showPopupWindow() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_layout, null, false);
        GridLayout gridLayout = view.findViewById(R.id.grid_layout);
        for (int i = 0; i < mTitles.length; i++) {
            if(i == mCurrentPage) {
                gridLayout.addView(newTextViewItem(mTitles[i], true, i));
            }else {
                gridLayout.addView(newTextViewItem(mTitles[i], false, i));
            }
        }
        mPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(view)//显示的布局，还可以通过设置一个View
                // .size(600,400)
                // 设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)
                //是否获取焦点，默认为ture
                .setOutsideTouchable(true)
                .setAnimationStyle(R.style.dialog_style)
                //是否PopupWindow 以外触摸dissmiss
                .create()//创建PopupWindow
                .showAsDropDown(mSlidingTab,0,0);
        mPopWindow.mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isVisible = false;
                mTvPingdao.setVisibility(View.INVISIBLE);
                mSlidingTab.setClickable(true);
                mTvPingdao.setClickable(true);
            }
        });
    }

    private TextView newTextViewItem(final String content, boolean isCurrentItem, final int pos) {
        //往GridLayout添加字条目
        int margin = 10;
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels/4 - 2*(margin + 10);
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setMargins(margin + 10,margin,margin + 10,margin);
        final TextView tv = new TextView(getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundResource(R.drawable.selector_tv_bg);
        tv.setPadding(5,5,5,5);
        tv.setTextSize(14);
        tv.setLayoutParams(layoutParams);
        if(isCurrentItem) {
            tv.setSelected(true);
            tv.setTextColor(getResources().getColor(R.color.red_a3));
        }else {
            tv.setSelected(false);
            tv.setTextColor(getResources().getColor(R.color.primary_text));
        }
        tv.setText(content);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showAlert(mContext, content);
                mViewPager.setCurrentItem(pos);
                if(mPopWindow != null) {
                    mPopWindow.dissmiss();
                    mPopWindow = null;
                }
            }
        });

        return tv;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_yzt_homepage;
    }

    @OnClick(R.id.iv_arrow)
    public void onClick() {
        if(isVisible) {
            mTvPingdao.setVisibility(View.INVISIBLE);
            isVisible = false;
            mSlidingTab.setClickable(true);
            mTvPingdao.setClickable(true);
            if(mPopWindow != null) {
                mPopWindow.dissmiss();
                mPopWindow = null;
            }
        }else {
            mTvPingdao.setVisibility(View.VISIBLE);
            isVisible = true;
            showPopupWindow();
            mSlidingTab.setClickable(false);
            mTvPingdao.setClickable(false);
        }
    }

}
