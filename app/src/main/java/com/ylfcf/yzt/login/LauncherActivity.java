package com.ylfcf.yzt.login;

import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.appconfig.AppSpContact;
import com.ylfcf.yzt.base.BasePagerAdapter;
import com.ylfcf.yzt.base.BaseViewPagerFragment;
import com.ylfcf.yzt.home.MainActivity;
import com.ylfcf.yzt.utils.SharedPreferencesHelper;
import com.ylfcf.yzt.utils.StringHelper;
import com.ylfcf.yzt.utils.Utils;
import com.ylfcf.yzt.view.CircleIndicator;
import com.ylfcf.yzt.view.CustomViewPager;

import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author yangjinxin  create by 2017/8/29 17:30
 * @Description 启动页
 */
public class LauncherActivity extends FragmentActivity {

    public BasePagerAdapter mAdapter;
    private ArrayList<BaseViewPagerFragment> mFragments = new ArrayList<>();
    private Handler mHandler = new Handler();
    private CustomViewPager mViewPager;
    private CircleIndicator mCiImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFirstLauncher = SharedPreferencesHelper.getInstance().getBoolean(AppSpContact.SP_KEY_FIRST_LAUNCHER, true);
        if (!isFirstLauncher) {//不是第一次启动
            initWindow();
            setContentView(R.layout.activity_splash);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                    LauncherActivity.this.finish();
                }
            }, 2000);
        } else {
            setContentView(R.layout.activity_launcher);
            setUpViewComponent();
            SharedPreferencesHelper.getInstance().putBoolean(AppSpContact.SP_KEY_FIRST_LAUNCHER, false);
        }
    }

    private void initWindow() {
        //控制底部虚拟键盘
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //让虚拟键盘一直不显示
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
            window.setAttributes(params);
        }
    }

    private void setUpViewComponent() {
        mViewPager = findViewById(R.id.view_pager);
        mCiImg = findViewById(R.id.ci_img);
        setUpFragments();
        setUpViewPager();
    }

    private void setUpFragments() {
        mFragments.clear();
        for (int i = 1; i <= 5; i++) {
            mFragments.add(LauncherImageFragment.newInstance(i));
        }
    }

    private void setUpViewPager() {
        mAdapter = new BasePagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setScrollable(true);
        mCiImg.setViewCount(5, 0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mCiImg != null) {
                    mCiImg.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
