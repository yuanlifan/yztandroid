package com.ylfcf.yzt.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.mEtAccount)
    EditText mMEtAccount;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.tv_register)
    TextView mTvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_forget_pwd, R.id.iv_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd:

                break;

            case R.id.iv_login:

                break;

            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

        }

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
