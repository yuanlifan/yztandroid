package com.ylfcf.yzt.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.ylfcf.yzt.R;
import com.ylfcf.yzt.appconfig.AppContact;
import com.ylfcf.yzt.appconfig.AppSpContact;
import com.ylfcf.yzt.base.BaseActivity;
import com.ylfcf.yzt.home.MainActivity;
import com.ylfcf.yzt.http.MyCallBack;
import com.ylfcf.yzt.http.base.BaseModel;
import com.ylfcf.yzt.http.model.UserLoginModel;
import com.ylfcf.yzt.utils.StringHelper;
import com.ylfcf.yzt.utils.ToastHelper;
import com.ylfcf.yzt.utils.Utils;
import java.io.IOException;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.mEtAccount)
    EditText mEtAccount;
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
                if (isCheckInput()) {
                    if (this.getCurrentFocus() != null) {
                        Utils.hideSoftInput(this, this.getCurrentFocus().getWindowToken());
                    }
                    getUserLogin();
                }
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }

    }
    
    void getUserLogin() {
//        ProgressDialogHelper.showProgress(this);
        String account = StringHelper.getEditTextContent(mEtAccount);
        String password = StringHelper.getEditTextContent(mEtPwd);
        netHandler.getUserLogin(account, password, new MyCallBack<UserLoginModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(LoginActivity.this, "网络断开");
            }

            @Override
            public void onSuccess(Call call, UserLoginModel data) {
                if(data.getError_id() == 0) {
                    if (StringHelper.notEmpty(data.getMsg().getToken()) && StringHelper.notEmpty(
                            data.getMsg().getUser_id()))
                        setLoginSuccessData(data);
                }else if(data.getError_id() == 1101) {
                    ToastHelper.showAlert(LoginActivity.this, "数据验证失败");
                }else if(data.getError_id() == 1102) {
                    ToastHelper.showAlert(LoginActivity.this, "已注册");
                }else {
                    ToastHelper.showAlert(LoginActivity.this, "保存失败");
                }
            }
        });

    }

    private void setLoginSuccessData(UserLoginModel data) {
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

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private boolean isCheckInput() {
        if (StringHelper.isEditTextEmpty(mEtAccount)) {
            ToastHelper.showAlert(LoginActivity.this, "手机号不能为空");
            return false;
        }

        if (StringHelper.isEditTextEmpty(mEtPwd)) {
            ToastHelper.showAlert(LoginActivity.this, "密码不能为空");
            return false;
        }

        if (StringHelper.getEditTextContent(mEtPwd).length() < AppContact.APP_PASSWORD_LIMIT_LENGTH) {
            ToastHelper.showAlert(LoginActivity.this, "密码长度最小6位");
            return false;
        }

        if (!Utils.isMobileNO(StringHelper.getEditTextContent(mEtAccount))) {
            ToastHelper.showAlert(LoginActivity.this, "请输入正确的账号");
            return false;
        }
        return true;
    }
    
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
