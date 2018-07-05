package com.ylfcf.yzt.personcenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseActivity;
import com.ylfcf.yzt.base.BaseFragment;
import com.ylfcf.yzt.personcenter.fragment.PersonalRevisePhone1Fragment;
import com.ylfcf.yzt.personcenter.fragment.PersonalRevisePhone2Fragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author yangjx
 * @purpose 修改手机号页面
 *
 */
public class PersonalRevisePhoneActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView    mTvTitle;
    @Bind(R.id.vp_main)
    FrameLayout mVpMain;

    private int                     currentPosition = 0;
    private ArrayList<BaseFragment> fragments       = new ArrayList<>();
    private BaseFragment mRevisePhone1Fragment;
    private BaseFragment mRevisePhone2Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_revise_phone);
        ButterKnife.bind(this);
        mTvTitle.setText("修改手机号");
        initFragments();
        changeFragment(0);
    }

    /**
     * 初始化集合
     */
    private void initFragments() {
        fragments.add(mRevisePhone1Fragment);
        fragments.add(mRevisePhone2Fragment);
    }

    /**
     * 切换Fragment
     */
    public void changeFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        hideFragment(transaction);
        if (position >= fragments.size() || fragments.get(position) == null) {
            BaseFragment fragment = getFragment(position);
            fragments.set(position, fragment);
            transaction.add(R.id.vp_main, fragment).commitAllowingStateLoss();
        } else {
            Fragment fragment = fragments.get(position);
            transaction.show(fragment).commitAllowingStateLoss();
            currentPosition = position;
        }
    }

    /**
     * 根据点击获取显示的Fragment
     */
    private BaseFragment getFragment(int position) {
        BaseFragment fragment = null;
        if (position == 0) {
            mRevisePhone1Fragment = new PersonalRevisePhone1Fragment();
            fragment = mRevisePhone1Fragment;
        } else if (position == 1) {
            mRevisePhone2Fragment = new PersonalRevisePhone2Fragment();
            fragment = mRevisePhone2Fragment;
        }
        return fragment;
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

    @OnClick(R.id.rl_toptitlebar_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
