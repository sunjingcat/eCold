package com.zz.cold.business.mine.mvp.presenter;


import com.zz.cold.net.ApiService;
import com.zz.cold.bean.UserBasicBean;
import com.zz.cold.business.mine.mvp.Contract;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.MyBasePresenterImpl;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;

/**
 * Created by 77 on 2018/8/8.
 */

public class MineInfoPresenter extends MyBasePresenterImpl<Contract.IMineInfoView> implements Contract.IsetMineInfoPresenter {

    public MineInfoPresenter(Contract.IMineInfoView view) {
        super(view);
    }


    @Override
    public void getMineInfo() {
        RxNetUtils.request(getApi(ApiService.class).getUserDetail(), new RequestObserver<JsonT<UserBasicBean>>(this) {
            @Override
            protected void onSuccess(JsonT<UserBasicBean> data) {
                if (data.isSuccess()) {
                    view.showUserInfo(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<UserBasicBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void logout() {
        RxNetUtils.request(getApi(ApiService.class).logout(), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showIntent();
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
                view.showIntent();
            }
        }, mDialog);
    }
}

