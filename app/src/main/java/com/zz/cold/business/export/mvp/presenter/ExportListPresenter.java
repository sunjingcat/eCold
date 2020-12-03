package com.zz.cold.business.export.mvp.presenter;


import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.GroupCountBean;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.business.export.mvp.Contract;
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
    public void getData(Map<String,Object> map, String id) {
        RxNetUtils.request(getApi(ApiService.class).importExportList(id,map), new RequestObserver<JsonT<List<GoodsBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<GoodsBean>> jsonT) {
                view.showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<GoodsBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
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

