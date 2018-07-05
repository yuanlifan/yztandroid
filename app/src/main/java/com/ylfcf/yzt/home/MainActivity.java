package com.ylfcf.yzt.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylfcf.yzt.R;
import com.ylfcf.yzt.appconfig.AppSpContact;
import com.ylfcf.yzt.base.BaseActivity;
import com.ylfcf.yzt.home.fragment.YztCategoryFragment;
import com.ylfcf.yzt.home.fragment.YztHomepageFragment;
import com.ylfcf.yzt.home.fragment.YztMsgFragment;
import com.ylfcf.yzt.home.fragment.YztPersonalFragment;
import com.ylfcf.yzt.home.fragment.YztShoppingFragment;
import com.ylfcf.yzt.login.LoginActivity;
import com.ylfcf.yzt.utils.StringHelper;
import com.ylfcf.yzt.utils.ToastHelper;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

/**
 * @author yangjinxin  create by 2017/7/11 19:34
 * @Description 首页
 */

public class MainActivity extends BaseActivity {

    //    @Bind(R.id.iv_user_avatar)
//    ImageView    mIvUserAvatar;//顶部用户头像
    @Bind(R.id.tabs)
    LinearLayout tabs;
    @Bind(R.id.iv_bottom_map)
    ImageView    mIv_bottom_map;
    @Bind(R.id.iv_bottom_find)
    ImageView    mIv_bottom_find;
    @Bind(R.id.iv_bottom_shop)
    ImageView    mIv_bottom_shop;
    @Bind(R.id.iv_bottom_help)
    ImageView    mIv_bottom_help;
    @Bind(R.id.iv_bottom_person)
    ImageView    mIv_bottom_person;
    @Bind(R.id.tool_bar)
    Toolbar      mToolbar;
    @Bind(R.id.tv_title)
    TextView     mTvTitle;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private YztHomepageFragment mYztHomepageFragment;
    private YztCategoryFragment mYztCategoryFragment;
    private YztMsgFragment      mYztMsgFragment;
    private YztShoppingFragment mYztShoppingFragment;
    private YztPersonalFragment mYztPersonalFragment;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpViewComponent();//初始化所有
    }

    private void setUpViewComponent() {
        initFragments();
        changeFragment(0);
        setViewClick(mIv_bottom_map, R.drawable.tab_firstpage_selected);
    }

    @OnClick(R.id.iv_bottom_map)
    void onBbClick() {
        changeFragment(0);
        mToolbar.setVisibility(View.GONE);
        setViewClick(mIv_bottom_map, R.drawable.tab_firstpage_selected);
    }

    @OnClick(R.id.iv_bottom_find)
    void onFindClick() {
        changeFragment(1);
        mToolbar.setVisibility(View.VISIBLE);
        mTvTitle.setText("发现");
        setViewClick(mIv_bottom_find, R.drawable.tab_find_selected);
    }

    @OnClick(R.id.iv_bottom_shop)
    void onCenterClick() {
        changeFragment(2);
        mToolbar.setVisibility(View.VISIBLE);
        mTvTitle.setText("分类");
        setViewClick(mIv_bottom_shop, R.drawable.tab_category_selected);
    }

    @OnClick(R.id.iv_bottom_help)
    void onMsgClick() {
        changeFragment(3);
        mToolbar.setVisibility(View.GONE);
        mTvTitle.setText("购物车");
        setViewClick(mIv_bottom_help, R.drawable.tab_shopping_selected);
    }

    @OnClick(R.id.iv_bottom_person)
    void onProfileClick() {
        if(TextUtils.isEmpty(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_USER_ID))||
                TextUtils.isEmpty(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_USER_TOKEN))) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else {
            changeFragment(4);
            mToolbar.setVisibility(View.GONE);
            mTvTitle.setText("个人");
            setViewClick(mIv_bottom_person, R.drawable.tab_person_selected);
        }
    }

    public void setViewClick(ImageView rb, int resId) {
        setView(mIv_bottom_map, R.drawable.tab_firstpage_unselected);
        setView(mIv_bottom_find, R.drawable.tab_find_unselected);
        setView(mIv_bottom_shop, R.drawable.tab_category_unselected);
        setView(mIv_bottom_help, R.drawable.tab_shopping_unselected);
        setView(mIv_bottom_person, R.drawable.tab_person_unselected);
        setView(rb, resId);
    }

    private void setView(ImageView rb, int resId) {
        rb.setImageResource(resId);
    }

    /**
     * 切换Fragment
     */
    public void changeFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        hideFragment(transaction);
        if (position >= fragments.size() || fragments.get(position) == null) {
            Fragment fragment = getFragment(position);
            fragments.set(position, fragment);
            transaction.add(R.id.vp_main, fragment).commitAllowingStateLoss();
        } else {
            Fragment fragment = fragments.get(position);
            transaction.show(fragment).commitAllowingStateLoss();
//            if (currentPosition == position) {
//                switch (position) {
//                    case 0:
//                        ((RNGbbFragment) fragment).refreshData();
//                        break;
//                }
//            }
            currentPosition = position;
        }
    }

    /**
     * 隐藏Fragment
     */
    private void hideFragment(FragmentTransaction transaction) {
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
    }

    /**
     * 根据点击获取显示的Fragment
     */
    private Fragment getFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            mYztHomepageFragment = new YztHomepageFragment();
            fragment = mYztHomepageFragment;
        } else if (position == 1) {
            mYztMsgFragment = new YztMsgFragment();
            fragment = mYztMsgFragment;
        } else if (position == 2) {
            mYztCategoryFragment = new YztCategoryFragment();
            fragment = mYztCategoryFragment;
        } else if (position == 3) {
            mYztShoppingFragment = new YztShoppingFragment();
            fragment = mYztShoppingFragment;
        } else if (position == 4) {
            mYztPersonalFragment = new YztPersonalFragment();
            fragment = mYztPersonalFragment;
        }
        return fragment;
    }

    /**
     * 初始化集合
     */
    private void initFragments() {
        fragments.add(mYztHomepageFragment);
        fragments.add(mYztMsgFragment);
        fragments.add(mYztCategoryFragment);
        fragments.add(mYztShoppingFragment);
        fragments.add(mYztPersonalFragment);
    }

    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU://消费实体MENU按键
                return true;
            case KeyEvent.KEYCODE_BACK:
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    ToastHelper.showBottomAlert(this, "再按一次退出程序");
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
