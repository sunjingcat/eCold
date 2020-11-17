package com.zz.cold.business.storage.mvp.presenter;


import com.zz.cold.bean.StorageBean;
import com.zz.cold.business.storage.mvp.Contract;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.MyBasePresenterImpl;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;

import java.util.Map;

/**
 * Created by 77 on 2018/8/8.
 */

public class StorageAddPresenter extends MyBasePresenterImpl<Contract.IGetStorageAddView> implements Contract.IsetStorageAddPresenter {

    public StorageAddPresenter(Contract.IGetStorageAddView view) {
        super(view);
    }

    @Override
    public void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getStorageInfo(id), new RequestObserver<JsonT<StorageBean>>(this) {
            @Override
            protected void onSuccess(JsonT<StorageBean> jsonT) {
                view.showStorageInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<StorageBean> stringJsonT) {
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
    public void getStorageType(Map<String, Object> map) {
//        RxNetUtils.request(getApi(ApiService.class).getDicts(map), new RequestObserver<JsonT<List<BusinessType>>>(this) {
//            @Override
//            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
//                view.showStorageType(jsonT.getData());
//            }
//
//            @Override
//            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
//                super.onFail2(stringJsonT);
//            }
//        }, mDialog);
    }

    @Override
    public void postImage(int position, String file) {

//        RxNetUtils.request(getApi(ApiService.class).uploadImg(file), new RequestObserver<JsonT<String>>(this) {
//            @Override
//            protected void onSuccess(JsonT<String> data) {
//                if (data.isSuccess()) {
//                    view.showPostImage(position,data.getData());
//                } else {
//
//                }
//            }
//
//            @Override
//            protected void onFail2(JsonT<String> userInfoJsonT) {
//                super.onFail2(userInfoJsonT);
//                view.showToast(userInfoJsonT.getMessage());
//            }
//        }, mDialog);
    }

    @Override
    public void getImage(String type, String modelId) {
//        RxNetUtils.request(getApi(ApiService.class).getImageBase64(type, modelId), new RequestObserver<JsonT<List<ImageBack>>>(this) {
//            @Override
//            protected void onSuccess(JsonT<List<ImageBack>> data) {
//                if (data.isSuccess()) {
//                    view.showImage(data.getData());
//                } else {
//
//                }
//            }
//
//            @Override
//            protected void onFail2(JsonT<List<ImageBack>> userInfoJsonT) {
//                super.onFail2(userInfoJsonT);
//                view.showToast(userInfoJsonT.getMessage());
//            }
//        }, mDialog);
    }

    @Override
    public void submitData(Map<String, Object> map) {
//        if (map.containsKey("id")) {
//            RxNetUtils.request(getApi(ApiService.class).editStorageInfo(map), new RequestObserver<JsonT<String>>(this) {
//                @Override
//                protected void onSuccess(JsonT<String> jsonT) {
//                    view.showSubmitResult( jsonT.getData());
////                    view.showToast(jsonT.getMessage());
//                }
//
//                @Override
//                protected void onFail2(JsonT stringJsonT) {
//                    super.onFail2(stringJsonT);
//                    view.showToast(stringJsonT.getMessage());
//                }
//            }, mDialog);
//        } else {
//            RxNetUtils.request(getApi(ApiService.class).postStorageInfo(map), new RequestObserver<JsonT<String>>(this) {
//                @Override
//                protected void onSuccess(JsonT<String> jsonT) {
//                    view.showSubmitResult((String) jsonT.getData());
////                    view.showToast(jsonT.getMessage());
//                }
//
//                @Override
//                protected void onFail2(JsonT stringJsonT) {
//                    super.onFail2(stringJsonT);
//                    view.showToast(stringJsonT.getMessage());
//                }
//            }, mDialog);
//        }
    }

    @Override
    public void uploadStorageImgs(String id, String files) {

//        RxNetUtils.request(getApi(ApiService.class).uploadStorageImgs(id,files), new RequestObserver<JsonT>(this) {
//            @Override
//            protected void onSuccess(JsonT data) {
//                if (data.isSuccess()) {
//                    view.showResult();
//                } else {
//
//                }
//            }
//
//            @Override
//            protected void onFail2(JsonT userInfoJsonT) {
//                super.onFail2(userInfoJsonT);
//                view.showToast(userInfoJsonT.getMessage());
//            }
//        }, mDialog);

    }

}

