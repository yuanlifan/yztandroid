package com.ylfcf.yzt.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.ylfcf.yzt.http.model.UserRegistInfo;
import com.ylfcf.yzt.utils.ResUtils;
import com.ylfcf.yzt.utils.StringHelper;
import com.ylfcf.yzt.utils.ToastHelper;
import com.ylfcf.yzt.utils.Utils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity {


    @Bind(R.id.tv_validate)
    TextView  mTvValidate;
    @Bind(R.id.et_phone_number)
    EditText  mEtPhoneNumber;
    @Bind(R.id.et_message)
    EditText  mEtMessage;
    @Bind(R.id.et_pwd)
    EditText  mEtPwd;

    @Bind(R.id.iv_detele)
    ImageView mIvDetele;
    @Bind(R.id.iv_detele2)
    ImageView mIvDetele2;
    @Bind(R.id.iv_detele3)
    ImageView mIvDetele3;

    @Bind(R.id.iv_hide)
    ImageView mIvHide;

    private boolean isHide = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initEditText();
    }

    @OnClick({R.id.rl_toptitlebar_back, R.id.tv_register, R.id.tv_validate, R.id.iv_detele, R.id.iv_detele2,
            R.id.iv_detele3, R.id.iv_hide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                if (checkInput()) {
                    verifyCode(StringHelper.getEditTextContent(mEtMessage),
                            StringHelper.getEditTextContent(mEtPhoneNumber));//校验验证码
                }
                break;
            case R.id.tv_validate:
                String PhoneNumber = mEtPhoneNumber.getText().toString();
                System.out.println("手机号：" + PhoneNumber);
                if (checkPhoneInput()) {
                    getAuthCode(StringHelper.getEditTextContent(mEtPhoneNumber));
                }
                break;
            case R.id.iv_detele:
                mEtPhoneNumber.setText("");
                break;
            case R.id.iv_detele2:
                mEtMessage.setText("");
                break;
            case R.id.iv_detele3:
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

    /**
     * 非空验证
     */
    private boolean checkInput() {
        if (StringHelper.isEditTextEmpty(mEtPhoneNumber)) {
            ToastHelper.showAlert(RegisterActivity.this, "手机号码不能为空");
            return false;
        }
        if (StringHelper.isEditTextEmpty(mEtMessage)) {
            ToastHelper.showAlert(RegisterActivity.this, "验证码不能为空");
            return false;
        }
        if (StringHelper.isEditTextEmpty(mEtPwd)) {
            ToastHelper.showAlert(RegisterActivity.this, "账号密码不能为空");
            return false;
        }
        if (StringHelper.getEditTextContent(mEtPwd).length() < AppContact.APP_PASSWORD_LIMIT_LENGTH) {
            ToastHelper.showAlert(RegisterActivity.this, "密码长度最小6位");
            return false;
        }
        return checkPhoneInput();
    }

    /**
     * @param editTextContent 根据手机号获取验证码
     */
    private void getAuthCode(String editTextContent) {
        netHandler.getVerificationCode(editTextContent, "1", new MyCallBack<BaseModel<String>>() {
            @Override
            public void onFailure(Call call, IOException e) {
                mTvValidate.setEnabled(true);
                mTvValidate.setClickable(true);
                ToastHelper.showAlert(RegisterActivity.this,"请求失败,请重试");
            }

            @Override
            public void onSuccess(Call call, BaseModel<String> json) {
                if (json.getError_id() == 0) {
                    mTvValidate.setEnabled(false);
                    mTvValidate.setClickable(false);
                    mCountDownTimer.start();
                    ToastHelper.showAlert(RegisterActivity.this, "短信已发送");
                } else {
                    mTvValidate.setEnabled(true);
                    mTvValidate.setClickable(true);
                    mCountDownTimer.cancel();
                    ToastHelper.showAlert(RegisterActivity.this, json.getMsg());
                }
            }
        });
        ToastHelper.showAlert(RegisterActivity.this, "短信已发送");
    }

    /**
     * @Description 校验验证码
     * @author guozhangyu
     * create by 2017/10/16 14:49
     */
    private void verifyCode(String code, String mobile) {
        netHandler.getCheckAuthCode(mobile, code, new MyCallBack<BaseModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(RegisterActivity.this, "验证码有误");
            }

            @Override
            public void onSuccess(Call call, BaseModel data) {
                if(data.getError_id() == 0) {
                    requestRegister();
                }else if(data.getError_id() == 1101) {
                    ToastHelper.showAlert(RegisterActivity.this, "数据验证失败");
                }else if(data.getError_id() == 1106) {
                    ToastHelper.showAlert(RegisterActivity.this, "短信验证码错误");
                }else {
                    ToastHelper.showAlert(RegisterActivity.this, "验证码有误");
                }
            }
        });

    }

    /**
     * 请求注册
     */
    private void requestRegister() {
        String account = StringHelper.getEditTextContent(mEtPhoneNumber);
        String password = StringHelper.getEditTextContent(mEtPwd);
        netHandler.getUserRegister(account, password, new MyCallBack<BaseModel<UserRegistInfo>>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(RegisterActivity.this, "网络断开");
            }

            @Override
            public void onSuccess(Call call, BaseModel<UserRegistInfo> data) {
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

    private void setRegisterSuccessData(BaseModel<UserRegistInfo> data) {
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
        EventBus.getDefault().post(new RegisterSuccessEvent());//发送注册成功事件,用于关闭登录页面
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
//        finish();
    }

    /**
     * 电话号码判断
     */
    private boolean checkPhoneInput() {
        if (StringHelper.isEditTextEmpty(mEtPhoneNumber)) {
            ToastHelper.showAlert(RegisterActivity.this, "手机号码不能为空");
            return false;
        }
        if (StringHelper.isPhoneEditTextEmpty(mEtPhoneNumber)) {
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

    private void initEditText() {
        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
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

        mEtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!TextUtils.isEmpty(mEtPhoneNumber.getText().toString()) && b) {
                    mIvDetele.setVisibility(View.VISIBLE);
                }else {
                    mIvDetele.setVisibility(View.INVISIBLE);
                }
            }
        });

        mEtMessage.addTextChangedListener(new TextWatcher() {
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

        mEtMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!TextUtils.isEmpty(mEtMessage.getText().toString()) && b) {
                    mIvDetele2.setVisibility(View.VISIBLE);
                }else {
                    mIvDetele2.setVisibility(View.INVISIBLE);
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
                    mIvDetele3.setVisibility(View.INVISIBLE);
                }else {
                    mIvDetele3.setVisibility(View.VISIBLE);
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
                    mIvDetele3.setVisibility(View.VISIBLE);
                }else {
                    mIvDetele3.setVisibility(View.INVISIBLE);
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
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
        super.onDestroy();
    }

}
