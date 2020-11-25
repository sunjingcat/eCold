package com.zz.cold.business.trace.mvp;

import com.zz.cold.bean.CategoryBean;
import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.TraceBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetPurchaseAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getData(String id);
        void getType(String type);
        void getGoodsType();


        void postImage(int requestCode,String localPath,List<MultipartBody.Part> imgs);

        void getImage(String type,String modelId);
    }

    public interface IGetPurchaseAddView extends BaseView {
        void showInfo(TraceBean data);
        void showResult();
        void showPostImage(int requestCode,String localPath,ImageBack imageBack);

        void showImage(List<ImageBack> list);
        void showType(String type,List<DictBean> list);
        void showGoods(List<CategoryBean> list);
    }

    public interface IsetDeliverPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void postImage(String localPath,List<MultipartBody.Part> imgs);
    }

    public interface IGetDeliverView extends BaseView {
        void showResult();
        void showPostImage(String localPath,ImageBack imageBack);

    }

}
