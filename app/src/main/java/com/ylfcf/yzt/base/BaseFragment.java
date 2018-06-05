package com.ylfcf.yzt.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.ylfcf.yzt.http.NethHandle;
import com.ylfcf.yzt.utils.SharedPreferencesHelper;

import butterknife.ButterKnife;


/**
 * @author guozhangyu  create by 2017/8/29 13:22
 * @Description
 */
public abstract class BaseFragment extends Fragment {
    protected SharedPreferencesHelper mSharedPreferencesHelper;
    public    Context                 mContext;
    protected NethHandle netHandler = NethHandle.getNethHandle();
    public Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        mSharedPreferencesHelper = SharedPreferencesHelper.getInstance();//实例化SharedPreferencesHelper
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResId(), container, false);//加载Fragment布局文件
        ButterKnife.bind(this, rootView);//绑定布局
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    //todo 页面数据的初始化
    protected abstract void initData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivity = getActivity();
        } catch (Exception e) {

        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName()); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
    }

    public void onDestroy() {
        ButterKnife.unbind(this);
        netHandler.removeAllMessage();
        super.onDestroy();
    }

    /**
     * 获取Fragment布局文件的R.layout int
     */
    protected abstract int getLayoutResId();
}
