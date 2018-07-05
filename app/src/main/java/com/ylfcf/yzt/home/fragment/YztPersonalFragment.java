package com.ylfcf.yzt.home.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseFragment;
import com.ylfcf.yzt.personcenter.PersonalAdressListActivity;
import com.ylfcf.yzt.personcenter.PersonalOrderFormActivity;
import com.ylfcf.yzt.personcenter.PersonalSafeActivity;
import com.ylfcf.yzt.personcenter.PersonalAdressActivity;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class YztPersonalFragment extends BaseFragment {

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_yzt_personal;
    }

    @OnClick({R.id.iv_personal_item1, R.id.iv_personal_item2, R.id.iv_personal_item3, R.id.iv_personal_item4, R.id.iv_personal_item5,
            R.id.iv_personal_item6, R.id.ll_order_form})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_personal_item1:
                //todo 优惠券

                break;
            case R.id.iv_personal_item2:
                //todo 帮助中心

                break;
            case R.id.iv_personal_item3:
                //账号安全
                startActivity(new Intent(mContext, PersonalSafeActivity.class));
                break;
            case R.id.iv_personal_item4:
                //地址管理
                startActivity(new Intent(mContext, PersonalAdressListActivity.class));
                break;
            case R.id.iv_personal_item5:
                //todo 联系客服

                break;
            case R.id.iv_personal_item6:
                //todo 关于我们

                break;
            case R.id.ll_order_form:
                //订单列表页面
                startActivity(new Intent(mContext, PersonalOrderFormActivity.class));
                break;
        }

    }
}
