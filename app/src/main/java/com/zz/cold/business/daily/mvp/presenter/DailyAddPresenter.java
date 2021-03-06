package com.zz.cold.business.daily.mvp.presenter;


import com.zz.cold.bean.DailyBean;
import com.zz.cold.bean.DailyPost;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.StorageBean;
import com.zz.cold.bean.TemperatureBean;
import com.zz.cold.bean.WarehouseBean;
import com.zz.cold.business.daily.mvp.Contract;
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

public class DailyAddPresenter extends MyBasePresenterImpl<Contract.IGetDailyAddView> implements Contract.IsetDailyAddPresenter {

    public DailyAddPresenter(Contract.IGetDailyAddView view) {
        super(view);
    }



    @Override
    public void postImage(int position, List<MultipartBody.Part> imgs) {

        RxNetUtils.request(getApi(ApiService.class).uploadImg(imgs), new RequestObserver<JsonT<ImageBack>>(this) {
            @Override
            protected void onSuccess(JsonT<ImageBack> data) {
                if (data.isSuccess()) {
                    view.showPostImage(position,data.getData());
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
    public void submitData(WarehouseBean requestBody) {

            RxNetUtils.request(getApi(ApiService.class).postDailyInfo(requestBody), new RequestObserver<JsonT>(this) {
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

    @Override
    public void getWarehouseData() {
        RxNetUtils.request(getApi(ApiService.class).getWarehouseAll(), new RequestObserver<JsonT<List<StorageBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<StorageBean>> data) {
                if (data.isSuccess()) {
                    view.showWarehouseData(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<StorageBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

}

