package com.zz.cold.business.trace.mvp;

import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.ImageBack;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetPurchaseAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getData(String id);


        void postImage(String localPath,List<MultipartBody.Part> imgs);

        void getImage(String type,String modelId);
    }

    public interface IGetPurchaseAddView extends BaseView {
        void showPurchaseInfo(GoodsBean data);
        void showResult();
        void showPostImage(String localPath,ImageBack imageBack);

        void showImage(List<ImageBack> list);

    }

}
