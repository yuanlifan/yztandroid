package com.ylfcf.yzt.personcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalSafeActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_safe);
        ButterKnife.bind(this);
        mTvTitle.setText("账号安全");
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.ll_revise_phone, R.id.ll_revise_password, R.id.tv_exit_system, R.id.rl_toptitlebar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_revise_phone:
                //修改手机号码
                startActivity(new Intent(PersonalSafeActivity.this, PersonalRevisePhoneActivity.class));
                break;
            case R.id.ll_revise_password:
                //修改密码
                startActivity(new Intent(PersonalSafeActivity.this, PersonalRevisePasswordActivity.class));
                break;
            case R.id.tv_exit_system:
                //安全退出
                break;
            case R.id.rl_toptitlebar_back:
                finish();
                break;
        }
    }
}
