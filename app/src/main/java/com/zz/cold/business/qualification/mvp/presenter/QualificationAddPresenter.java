package com.zz.cold.business.qualification.mvp.presenter;


import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.UserInfo;
import com.zz.cold.business.qualification.mvp.Contract;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.MyBasePresenterImpl;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.commonlib.utils.CacheUtility;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by 77 on 2018/8/8.
 */

public class QualificationAddPresenter extends MyBasePresenterImpl<Contract.IGetQualificationAddView> implements Contract.IsetQualificationAddPresenter {

    public QualificationAddPresenter(Contract.IGetQualificationAddView view) {
        super(view);
    }

    @Override
    public void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getQualificationInfo(id), new RequestObserver<JsonT<QualificationBean>>(this) {
            @Override
            protected void onSuccess(JsonT<QualificationBean> jsonT) {
                view.showQualificationInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<QualificationBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void getBusinessType(Map<String, Object> map) {
//        RxNetUtils.request(getApi(ApiService.class).getDicts(map), new RequestObserver<JsonT<List<BusinessType>>>(this) {
//            @Override
//            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
//                view.showBusinessType(jsonT.getData());
//            }
//
//            @Override
//            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
//                super.onFail2(stringJsonT);
//            }
//        }, mDialog);
    }

    @Override
    public void getBusiness2Type(Map<String, Object> map) {
//        RxNetUtils.request(getApi(ApiService.class).getDicts(map), new RequestObserver<JsonT<List<BusinessType>>>(this) {
//            @Override
//            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
//                view.showBusiness2Type(jsonT.getData());
//            }
//
//            @Override
//            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
//                super.onFail2(stringJsonT);
//            }
//        }, mDialog);
    }

    @Override
    public void getQualificationType(Map<String, Object> map) {
//        RxNetUtils.request(getApi(ApiService.class).getDicts(map), new RequestObserver<JsonT<List<BusinessType>>>(this) {
//            @Override
//            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
//                view.showQualificationType(jsonT.getData());
//            }
//
//            @Override
//            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
//                super.onFail2(stringJsonT);
//            }
//        }, mDialog);
    }

    @Override
    public void postImage( List<MultipartBody.Part> imgs) {

        RxNetUtils.request(getApi(ApiService.class).uploadImg(imgs), new RequestObserver<JsonT<ImageBack>>(this) {
            @Override
            protected void onSuccess(JsonT<ImageBack> data) {
                if (data.isSuccess()) {
                    view.showPostImage(data.getData());
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
            RxNetUtils.request(getApi(ApiService.class).editQualificationInfo(map), new RequestObserver<JsonT>(this) {
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
            RxNetUtils.request(getApi(ApiService.class).postQualificationInfo(map), new RequestObserver<JsonT>(this) {
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

