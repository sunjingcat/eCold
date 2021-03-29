package com.zz.cold.business.qualification;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.business.login.LoginActivity;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.OutDateEvent;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 * 密码
 */
public class StoragePasswordActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_password_old)
    EditText edPasswordOld;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_password_again)
    EditText edPasswordAgain;
    @BindView(R.id.bt_ok)
    Button btOk;

    @Override
    protected int getContentView() {
        return R.layout.activity_pass_word;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        edPasswordOld.setVisibility(View.GONE);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        getData();
    }

    void getData() {
        Map<String,Object> map = new HashMap<>();
        String edPasswordOld_ = edPasswordOld.getText().toString();
        String edPassword_ = edPassword.getText().toString();
        String edPasswordAgain_ = edPasswordAgain.getText().toString();
        String id = getIntent().getStringExtra("id");
//        if (TextUtils.isEmpty(edPasswordOld_)){
//            showToast("请输入旧密码");
//            return;
//        }
        if (TextUtils.isEmpty(edPassword_)){
            showToast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(edPasswordAgain_)){
            showToast("请输入确认密码");
            return;
        }
        if (!edPassword_.equals(edPasswordAgain_)){
            showToast("两次新密码不一致");
            return;
        }

        map.put("newPassword",edPassword_);
        RxNetUtils.request(getApi(ApiService.class).resetStoragePwd(id,map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    finish();
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, null);
    }

}
