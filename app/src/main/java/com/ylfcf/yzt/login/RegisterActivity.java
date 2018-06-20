package com.ylfcf.yzt.login;

import android.os.Bundle;
import android.view.View;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_toptitlebar_back, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_toptitlebar_back:

                finish();
                break;
            case R.id.tv_login:

                finish();
                break;
        }

    }



}
