package com.zz.cold.business.trace.mvp.presenter;


import com.zz.cold.bean.CategoryBean;
import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.TracePostBean;
import com.zz.cold.business.trace.mvp.Contract;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.MyBasePresenterImpl;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by 77 on 2018/8/8.
 */

public class PurchaseAddPresenter extends MyBasePresenterImpl<Contract.IGetPurchaseAddView> implements Contract.IsetPurchaseAddPresenter {

    public PurchaseAddPresenter(Contract.IGetPurchaseAddView view) {
        super(view);
    }

    @Override
    public void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getTraceInfo(id), new RequestObserver<JsonT<TraceBean>>(this) {
            @Override
            protected void onSuccess(JsonT<TraceBean> jsonT) {
                view.showInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<TraceBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void getType(String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("dictType", type);
        RxNetUtils.request(getApi(ApiService.class).getDicts(map), new RequestObserver<JsonT<List<DictBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<DictBean>> jsonT) {
                view.showType(type, jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<DictBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void getGoodsType() {
        Map<String, Object> map = new HashMap<>();

        RxNetUtils.request(getApi(ApiService.class).getGoodsType(map), new RequestObserver<JsonT<List<CategoryBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<CategoryBean>> jsonT) {
                view.showGoods(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<CategoryBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }


    @Override
    public void postImage(int requestCode, String localPath, List<MultipartBody.Part> imgs) {

        RxNetUtils.request(getApi(ApiService.class).uploadImg(imgs), new RequestObserver<JsonT<ImageBack>>(this) {
            @Override
            protected void onSuccess(JsonT<ImageBack> data) {
                if (data.isSuccess()) {
                    view.showPostImage(requestCode, localPath, data.getData());
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
    public void submitData(TracePostBean tracePostBean) {

        RxNetUtils.request(getApi(ApiService.class).confirmGoodsAccount(tracePostBean), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT jsonT) {
                postData(tracePostBean);
            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
                view.showToast(stringJsonT.getMessage());
            }
        }, mDialog);
    }

    public void postData(TracePostBean tracePostBean) {
        RxNetUtils.request(getApi(ApiService.class).postGoodsAccount(tracePostBean), new RequestObserver<JsonT>(this) {
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

