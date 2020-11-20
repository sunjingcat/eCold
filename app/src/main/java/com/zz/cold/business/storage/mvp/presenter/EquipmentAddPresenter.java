package com.zz.cold.business.storage.mvp.presenter;


import com.zz.cold.bean.EquipmentBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.StorageBean;
import com.zz.cold.business.storage.mvp.Contract;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.MyBasePresenterImpl;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by 77 on 2018/8/8.
 */

public class EquipmentAddPresenter extends MyBasePresenterImpl<Contract.IGetEquipmentAddView> implements Contract.IsetEquipmentAddPresenter {

    public EquipmentAddPresenter(Contract.IGetEquipmentAddView view) {
        super(view);
    }

    @Override
    public void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getEquipmentInfo(id), new RequestObserver<JsonT<EquipmentBean>>(this) {
            @Override
            protected void onSuccess(JsonT<EquipmentBean> jsonT) {
                view.showEquipmentInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<EquipmentBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }


    @Override
    public void postImage(String localPath, List<MultipartBody.Part> imgs) {

        RxNetUtils.request(getApi(ApiService.class).uploadImg(imgs), new RequestObserver<JsonT<ImageBack>>(this) {
            @Override
            protected void onSuccess(JsonT<ImageBack> data) {
                if (data.isSuccess()) {
                    view.showPostImage(localPath,data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<ImageBack> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void getImage(String type, String modelId) {
        RxNetUtils.request(getApi(ApiService.class).getModelImages(type, modelId), new RequestObserver<JsonT<List<ImageBack>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ImageBack>> data) {
                if (data.isSuccess()) {
                    view.showImage(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<ImageBack>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void submitData(Map<String, Object> map) {
        if (map.containsKey("id")) {
            RxNetUtils.request(getApi(ApiService.class).editEquipmentInfo(map), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT jsonT) {
                    view.showResult();
                }

                @Override
                protected void onFail2(JsonT stringJsonT) {
                    super.onFail2(stringJsonT);
                    view.showToast(stringJsonT.getMessage());
                }
            }, mDialog);
        } else {
            RxNetUtils.request(getApi(ApiService.class).postEquipmentInfo(map), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT jsonT) {
                    view.showResult();
                }

                @Override
                protected void onFail2(JsonT stringJsonT) {
                    super.onFail2(stringJsonT);
                    view.showToast(stringJsonT.getMessage());
                }
            }, mDialog);
        }
    }

}

