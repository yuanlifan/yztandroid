package com.ylfcf.yzt.login;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.os.Bundle;
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
    @Bind(R.id.view_pager)
    CustomViewPager viewPager;
    @Bind(R.id.ci_img)
    CircleIndicator ciImg;


    public BasePagerAdapter mAdapter;
    private ArrayList<BaseViewPagerFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFirstLauncher = SharedPreferencesHelper.getInstance().getBoolean(AppSpContact.SP_KEY_FIRST_LAUNCHER, true);
//        int versionCode = SharedPreferencesHelper.getInstance().getInt(AppSpContact.SP_KEY_VERSION_CODE);  //大版本更新使用
//        if (versionCode == 0) {
//            SharedPreferencesHelper.getInstance().putInt(AppSpContact.SP_KEY_VERSION_CODE, Utils.getVersionCode());
//            Utils.onClearLogoutUserData();
//            isFirstLauncher = true;
//        } else {
//            if (Utils.getVersionCode() > versionCode) {
//                Utils.onClearLogoutUserData();
//                isFirstLauncher = true;
//            } else {
//                SharedPreferencesHelper.getInstance().putInt(AppSpContact.SP_KEY_VERSION_CODE, versionCode);
//            }
//        }
        if (!isFirstLauncher) {//不是第一次启动
//            String loginId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LOGIN_ID);
//            if (StringHelper.isEmpty(loginId)) {
//                Intent intent = new Intent(this, LoadingNextActivity.class);
//                startActivity(intent);
//            } else {
//                Intent intent = new Intent(this, LoadingActivity.class);
//                startActivity(intent);
//            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_launcher);
            ButterKnife.bind(this);
            setUpViewComponent();
            SharedPreferencesHelper.getInstance().putBoolean(AppSpContact.SP_KEY_FIRST_LAUNCHER, false);
        }
    }

    private void setUpViewComponent() {
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
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(mAdapter);
        viewPager.setScrollable(true);
        ciImg.setViewCount(5, 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (ciImg != null) {
                    ciImg.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
