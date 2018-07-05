package com.ylfcf.yzt.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.appconfig.AppContact;
import com.ylfcf.yzt.appconfig.AppSpContact;
import com.ylfcf.yzt.appevent.RegisterSuccessEvent;
import com.ylfcf.yzt.base.BaseActivity;
import com.ylfcf.yzt.home.MainActivity;
import com.ylfcf.yzt.http.MyCallBack;
import com.ylfcf.yzt.http.base.BaseModel;
import com.ylfcf.yzt.http.model.UserLoginInfo;
import com.ylfcf.yzt.utils.StringHelper;
import com.ylfcf.yzt.utils.ToastHelper;
import com.ylfcf.yzt.utils.Utils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.mEtAccount)
    EditText  mEtAccount;
    @Bind(R.id.et_pwd)
    EditText  mEtPwd;
    @Bind(R.id.tv_register)
    TextView  mTvRegister;
    @Bind(R.id.iv_detele)
    ImageView mIvDetele;
    @Bind(R.id.iv_detele2)
    ImageView mIvDetele2;
    @Bind(R.id.iv_hide)
    ImageView mIvHide;

    private boolean isHide = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initEditText();
    }

    @OnClick({R.id.tv_forget_pwd, R.id.iv_login, R.id.tv_register,R.id.iv_detele, R.id.iv_hide, R.id.iv_detele2
            ,R.id.tv_login_sms, R.id.rl_toptitlebar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd:
                //忘记密码
                break;
            case R.id.iv_login:
                if (isCheckInput()) {
                    if (this.getCurrentFocus() != null) {
                        Utils.hideSoftInput(this, this.getCurrentFocus().getWindowToken());
                    }
                    getUserLogin();
                }
                break;
            case R.id.tv_login_sms:
                //todo 短信登录

                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.iv_detele:
                mEtAccount.setText("");
                break;
            case R.id.iv_detele2:
                mEtPwd.setText("");
                break;
            case R.id.iv_hide:
                mEtPwd.setFocusable(true);
                mEtPwd.setFocusableInTouchMode(true);
                mEtPwd.requestFocus();
                if(isHide) {
                    //显示
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mIvHide.setImageDrawable(getResources().getDrawable(R.drawable.password_show_icon));
                    mEtPwd.setSelection(mEtPwd.length());
                    isHide = false;
                }else {
                    //隐藏
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mIvHide.setImageDrawable(getResources().getDrawable(R.drawable.password_hide_icon));
                    mEtPwd.setSelection(mEtPwd.length());
                    isHide = true;
                }
                break;
            case R.id.rl_toptitlebar_back:
                hideSoftInput();
                finish();
                break;
        }

    }

    void getUserLogin() {
//        ProgressDialogHelper.showProgress(this);
        String account = StringHelper.getEditTextContent(mEtAccount);
        String password = StringHelper.getEditTextContent(mEtPwd);
        netHandler.getUserLogin(account, password, new MyCallBack<BaseModel<UserLoginInfo>>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(LoginActivity.this, "网络断开");
            }

            @Override
            public void onSuccess(Call call, BaseModel<UserLoginInfo> data) {
                if (data.getError_id() == 0) {
                    if (StringHelper.notEmpty(data.getMsg().getToken()) && StringHelper.notEmpty(
                            data.getMsg().getUser_id()))
                        setLoginSuccessData(data);
                } else if (data.getError_id() == 1103) {
                    ToastHelper.showAlert(LoginActivity.this, "该手机号未注册");
                } else if (data.getError_id() == 1104) {
                    ToastHelper.showAlert(LoginActivity.this, "您输入的手机号或密码不正确");
                } else {
                    ToastHelper.showAlert(LoginActivity.this, "保存失败");
                }
            }
        });

    }

    private void setLoginSuccessData(BaseModel<UserLoginInfo> data) {
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


    private void initEditText() {
        mEtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone_number = charSequence.toString();
                if(TextUtils.isEmpty(phone_number)) {
                    mIvDetele.setVisibility(View.INVISIBLE);
                }else {
                    mIvDetele.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEtAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!TextUtils.isEmpty(mEtAccount.getText().toString()) && b) {
                    mIvDetele.setVisibility(View.VISIBLE);
                }else {
                    mIvDetele.setVisibility(View.INVISIBLE);
                }
            }
        });
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone_number = charSequence.toString();
                if(TextUtils.isEmpty(phone_number)) {
                    mIvDetele2.setVisibility(View.INVISIBLE);
                }else {
                    mIvDetele2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEtPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!TextUtils.isEmpty(mEtPwd.getText().toString()) && b) {
                    mIvDetele2.setVisibility(View.VISIBLE);
                }else {
                    mIvDetele2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void hideSoftInput() {
        if (getWindow() != null && getWindow().getCurrentFocus() != null) {
            Utils.hideSoftInput(this, getWindow().getCurrentFocus().getWindowToken());
        }
    }

    public void onEvent(RegisterSuccessEvent event) {
        if (event != null) //注册成功，关闭登陆页面
            finish();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
