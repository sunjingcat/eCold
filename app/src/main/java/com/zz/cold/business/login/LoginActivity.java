package com.zz.cold.business.login;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.igexin.sdk.PushManager;
import com.zz.cold.BuildConfig;
import com.zz.cold.MainActivity;
import com.zz.cold.R;
import com.zz.cold.StartpageActivity;
import com.zz.cold.business.login.mvp.presenter.LoginPresenter;
import com.zz.cold.base.MyBaseActivity;

import com.zz.cold.business.login.mvp.Contract;
import com.zz.cold.business.trace.TraceActivity;
import com.zz.lib.commonlib.utils.CacheUtility;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends MyBaseActivity<Contract.IsetLoginPresenter> implements Contract.IGetLoginView {

    @BindView(R.id.log_number)
    EditText logNumber;
    @BindView(R.id.log_password)
    EditText logPassword;
    @BindView(R.id.login_password_show)
    ImageView loginPasswordShow;

    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.boutique_all)
    CheckBox boutiqueAll;
    private boolean mPasswordVisible = true;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }


    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (BuildConfig.DEBUG) {
            logNumber.setText("apptest");
            logPassword.setText("123456");
        }
    }
    @Override
    protected void initToolBar() {

    }
    @OnClick({R.id.login_password_show, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_password_show:
                if (mPasswordVisible) {
                    //设置EditText的密码为可见的
                    logPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPasswordVisible = false;
                    loginPasswordShow.setImageResource(R.drawable.image_login_password_show);
                } else {
                    //设置密码为隐藏的
                    logPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPasswordVisible = true;
                    loginPasswordShow.setImageResource(R.drawable.image_login_password_hide);
                }


                break;
            case R.id.login_btn:
                String number = logNumber.getText().toString();
                String password = logPassword.getText().toString();

                if (TextUtils.isEmpty(number)) {
                    showToast("请填写用户名");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast("请填写密码");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("username", number);
                map.put("password", password);

                mPresenter.setAccount(map);

                break;
        }
    }

    @Override
    public void showIntent() {
        showToast("登录成功");
        PushManager.getInstance().turnOnPush(this);
        Intent intent = new Intent();
        if (CacheUtility.getRole() == 1) {
            intent.setClass(this, MainActivity.class);
        } else {
            intent.setClass(this, TraceActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
