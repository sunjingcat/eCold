package com.zz.cold.business.trace.mvp.presenter;


import com.zz.cold.bean.ExportPost;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.TracePostBean;
import com.zz.cold.business.trace.mvp.Contract;
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

public class DeliverPresenter extends MyBasePresenterImpl<Contract.IGetDeliverView> implements Contract.IsetDeliverPresenter {

    public DeliverPresenter(Contract.IGetDeliverView view) {
        super(view);
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
    public void submitData(ExportPost tracePostBean) {
//        if (map.containsKey("id")) {
//            RxNetUtils.request(getApi(ApiService.class).editPurchaseInfo(map), new RequestObserver<JsonT>(this) {
//                @Override
//                protected void onSuccess(JsonT jsonT) {
//                    view.showResult();
//                }
//
//                @Override
//                protected void onFail2(JsonT stringJsonT) {
//                    super.onFail2(stringJsonT);
//                    view.showToast(stringJsonT.getMessage());
//                }
//            }, mDialog);
//        } else {
            RxNetUtils.request(getApi(ApiService.class).postGoodsAccountJian(tracePostBean), new RequestObserver<JsonT>(this) {
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
//        }
    }


}

