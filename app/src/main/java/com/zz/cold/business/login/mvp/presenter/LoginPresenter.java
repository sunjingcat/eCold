package com.zz.cold.business.login.mvp.presenter;

import com.zz.cold.net.ApiService;
import com.zz.cold.bean.UserInfo;
import com.zz.cold.business.login.mvp.Contract;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.MyBasePresenterImpl;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.commonlib.utils.CacheUtility;

import java.util.Map;

public class LoginPresenter extends MyBasePresenterImpl<Contract.IGetLoginView> implements Contract.IsetLoginPresenter {

    public LoginPresenter(Contract.IGetLoginView view) {
        super(view);
    }
    @Override
    public void setAccount(Map<String, Object> params) {
        RxNetUtils.request(getApi(ApiService.class).login(params), new RequestObserver<JsonT<UserInfo>>(this) {
            @Override
            protected void onSuccess(JsonT<UserInfo> login_data) {
                if (login_data.isSuccess()) {
                    CacheUtility.saveToken(login_data.getData().getLoginToken());
                    CacheUtility.saveRole(login_data.getData().getRole());
                    view.showIntent();
                }else {
                    view.showToast(login_data.getMessage());
                }
            }
            @Override
            protected void onFail2(JsonT<UserInfo> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);

    }



}
