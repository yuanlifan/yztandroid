package com.ylfcf.yzt.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.appconfig.AppContact;
import com.ylfcf.yzt.appconfig.AppSpContact;
import com.ylfcf.yzt.base.BaseActivity;
import com.ylfcf.yzt.home.MainActivity;
import com.ylfcf.yzt.http.MyCallBack;
import com.ylfcf.yzt.http.model.UserLoginModel;
import com.ylfcf.yzt.utils.ResUtils;
import com.ylfcf.yzt.utils.StringHelper;
import com.ylfcf.yzt.utils.ToastHelper;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity {


    @Bind(R.id.et_number)
    EditText mEtNumber;
    @Bind(R.id.message_test)
    EditText mMessageTest;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.tv_validate)
    TextView mTvValidate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_toptitlebar_back, R.id.tv_register, R.id.tv_login, R.id.tv_validate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_toptitlebar_back:

                finish();
                break;
            case R.id.tv_register:
//                requestRegister();
                break;
            case R.id.tv_login:

                break;
            case R.id.tv_validate:
                String PhoneNumber = mEtNumber.getText().toString();
                System.out.println("手机号：" + PhoneNumber);
                if (checkPhoneInput()) {
                    getAuthCode(StringHelper.getEditTextContent(mEtNumber));
                }
                break;

        }
    }

    private void getAuthCode(String editTextContent) {
        mTvValidate.setEnabled(false);
        mTvValidate.setClickable(false);
        mCountDownTimer.start();
        ToastHelper.showAlert(RegisterActivity.this, "短信已发送");
    }

    private void requestRegister() {
        String account = StringHelper.getEditTextContent(mEtNumber);
        String password = StringHelper.getEditTextContent(mEtPwd);
        netHandler.getUserRegister(account, password, new MyCallBack<UserLoginModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(RegisterActivity.this, "网络断开");
            }

            @Override
            public void onSuccess(Call call, UserLoginModel data) {
                if (data.getError_id() == 0) {
                    if (StringHelper.notEmpty(data.getMsg().getToken()) && StringHelper.notEmpty(
                            data.getMsg().getUser_id()))
                        setRegisterSuccessData(data);
                } else if (data.getError_id() == 1101) {
                    ToastHelper.showAlert(RegisterActivity.this, "数据验证失败");
                } else if (data.getError_id() == 1102) {
                    ToastHelper.showAlert(RegisterActivity.this, "已注册");
                } else {
                    ToastHelper.showAlert(RegisterActivity.this, "保存失败");
                }
            }
        });
    }

    private void setRegisterSuccessData(UserLoginModel data) {
        if (StringHelper.notEmpty(data.getMsg().getToken())) {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_TOKEN,
                    data.getMsg().getToken());
        } else {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_TOKEN, "");
        }

        if (StringHelper.notEmpty(data.getMsg().getUser_id())) {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_ID,
                    data.getMsg().getUser_id());
        } else {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_ID, "");
        }

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    /**
     * 电话号码判断
     */
    private boolean checkPhoneInput() {
        if (StringHelper.isEditTextEmpty(mEtNumber)) {
            ToastHelper.showAlert(RegisterActivity.this, "手机号码不能为空");
            return false;
        }
        if (StringHelper.isPhoneEditTextEmpty(mEtNumber)) {
            ToastHelper.showAlert(RegisterActivity.this, "请输入正确的账号");
            return false;
        }
        return true;
    }

    //验证倒计时
    private CountDownTimer mCountDownTimer =
            new CountDownTimer(AppContact.APP_SEND_CODE_TIMER, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTvValidate.setText(String.format(getString(R.string.send_code_down_count_tips),
                            millisUntilFinished / 1000));
                    mTvValidate.setTextColor(ResUtils.getColor(R.color.white));
                }

                @Override
                public void onFinish() {
                    mTvValidate.setText(R.string.send_code);
//                    mTvValidate.setSelected(false);
                    mTvValidate.setEnabled(true);
                    mTvValidate.setClickable(true);
                    mTvValidate.setTextColor(ResUtils.getColor(R.color.by_red_main));
                }
            };

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


}
