package com.ylfcf.yzt.home.fragment;


import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
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

    @Bind(R.id.tabs)
    SlidingTabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager        mViewPager;
    @Bind(R.id.tv_pingdao)
    TextView         mTvPingdao;
    @Bind(R.id.iv_arrow)
    ImageView        mIvArrow;

    private ArrayList<BaseViewPagerFragment> fragments    = new ArrayList<>();
    private String[]                         mTitles      = {"首页", "绿色", "智慧", "健康", "轻奢", "财富", "生活"};
    private boolean                          isVisible    = false;
    private int                              mCurrentPage = 0;
    private CustomPopWindow mPopWindow;
    private Animation mOperatingAnim;

    @Override
    protected void initData() {
        initFragments();
        BasePagerAdapter adapter = new BasePagerAdapter(getChildFragmentManager(), fragments);
        adapter.setTitles(mTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setSnapOnTabClick(true);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mCurrentPage = position;
                // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
                mViewPager.setCurrentItem(position, false);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });

    }

    private void initFragments() {
        fragments.clear();
        for (int i = 0; i < 7; i++) {
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
                .showAsDropDown(mTabLayout,0,0);
        mPopWindow.mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isVisible = false;
                mTvPingdao.setVisibility(View.INVISIBLE);
                mTabLayout.setClickable(true);
                startAnim(mIvArrow, true);
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
                mViewPager.setCurrentItem(pos,false);
                if(mPopWindow != null) {
                    mPopWindow.dissmiss();
                    mPopWindow = null;
                }
            }
        });
        return tv;
    }

    private void startAnim(ImageView arrowImg, boolean isVisible) {
        if(isVisible) {
            mOperatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anim_arrow2);
        }else {
            mOperatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anim_arrow);
        }
        LinearInterpolator lin = new LinearInterpolator();
        mOperatingAnim.setInterpolator(lin);
        mOperatingAnim.setFillAfter(true);
        arrowImg.setAnimation(mOperatingAnim);
        arrowImg.startAnimation(mOperatingAnim);
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
            mTabLayout.setClickable(true);
            if(mPopWindow != null) {
                mPopWindow.dissmiss();
                mPopWindow = null;
            }
            startAnim(mIvArrow, true);
        }else {
            mTvPingdao.setVisibility(View.VISIBLE);
            isVisible = true;
            showPopupWindow();
            mTabLayout.setClickable(false);

            startAnim(mIvArrow,false);
        }

    }

}
