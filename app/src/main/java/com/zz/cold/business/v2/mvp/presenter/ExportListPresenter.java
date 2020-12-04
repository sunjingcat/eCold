package com.zz.cold.business.v2.mvp.presenter;


import com.zz.cold.bean.GroupCountBean;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.business.v2.mvp.Contract;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.MyBasePresenterImpl;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by 77 on 2018/8/8.
 */

public class ExportListPresenter extends MyBasePresenterImpl<Contract.IGetExportListView> implements Contract.IsetExportListPresenter {

    public ExportListPresenter(Contract.IGetExportListView view) {
        super(view);
    }

    @Override
    public void getData(String type,Map<String,Object> map, String id) {
        if (type.equals("import")) {
            RxNetUtils.request(getApi(ApiService.class).importList(id, map), new RequestObserver<JsonT<List<TraceBean>>>(this) {
                @Override
                protected void onSuccess(JsonT<List<TraceBean>> jsonT) {
                    view.showResult(jsonT.getData());
                }

                @Override
                protected void onFail2(JsonT<List<TraceBean>> stringJsonT) {
                    super.onFail2(stringJsonT);
                }
            }, mDialog);
        }else {
            RxNetUtils.request(getApi(ApiService.class).exportList(id, map), new RequestObserver<JsonT<List<TraceBean>>>(this) {
                @Override
                protected void onSuccess(JsonT<List<TraceBean>> jsonT) {
                    view.showResult(jsonT.getData());
                }

                @Override
                protected void onFail2(JsonT<List<TraceBean>> stringJsonT) {
                    super.onFail2(stringJsonT);
                }
            }, mDialog);
        }
    }

    @Override
    public void getTab(String type) {
        if (type.equals("import")) {
            RxNetUtils.request(getApi(ApiService.class).selectImportColdstorageGroupCount(), new RequestObserver<JsonT<List<GroupCountBean>>>(this) {
                @Override
                protected void onSuccess(JsonT<List<GroupCountBean>> jsonT) {
                    view.showTabType(jsonT.getData());
                }

                @Override
                protected void onFail2(JsonT<List<GroupCountBean>> stringJsonT) {
                    super.onFail2(stringJsonT);
                }
            }, mDialog);
        } else {
            RxNetUtils.request(getApi(ApiService.class).selectExportColdstorageGroupCount(), new RequestObserver<JsonT<List<GroupCountBean>>>(this) {
                @Override
                protected void onSuccess(JsonT<List<GroupCountBean>> jsonT) {
                    view.showTabType(jsonT.getData());
                }

                @Override
                protected void onFail2(JsonT<List<GroupCountBean>> stringJsonT) {
                    super.onFail2(stringJsonT);
                }
            }, mDialog);
        }

    }

}

