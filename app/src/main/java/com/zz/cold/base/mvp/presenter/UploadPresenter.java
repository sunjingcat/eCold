package com.zz.cold.base.mvp.presenter;


import com.zz.cold.base.mvp.Contract;
import com.zz.cold.bean.ImageBean;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.MyBasePresenterImpl;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;

import java.util.List;

import okhttp3.MultipartBody;

public class UploadPresenter extends MyBasePresenterImpl<Contract.IGetUploadView> implements Contract.IsetUploadPresenter {

    public UploadPresenter(Contract.IGetUploadView view) {
        super(view);
    }


    @Override
    public void uploadImage(List<MultipartBody.Part> imgs) {
        RxNetUtils.request(getApi(ApiService.class).upload(imgs), new RequestObserver<JsonT<ImageBean>>(this) {

            @Override
            protected void onSuccess(JsonT<ImageBean> jsonT) {
                view.showImage(jsonT.getData().getImageUrl());
            }


        },mDialog);
    }
}
