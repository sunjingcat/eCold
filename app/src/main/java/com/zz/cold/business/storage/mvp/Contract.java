package com.zz.cold.business.storage.mvp;

import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.StorageBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

public class Contract {
    public interface IsetStorageAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getData(String id);

        void getBusinessType(Map<String, Object> map);
        void getBusiness2Type(Map<String, Object> map);
        void getStorageType(Map<String, Object> map);

        void postImage(int position,String files);
        void uploadStorageImgs(String id,String files);

        void getImage(String type,String modelId);
    }

    public interface IGetStorageAddView extends BaseView {
        void showStorageInfo(StorageBean data);

        void showSubmitResult(String id);

        void showResult();
        void showPostImage(int position,String id);
//
//        void showBusinessType(List<BusinessType> list);
//        void showBusiness2Type(List<BusinessType> list);
//        void showStorageType(List<BusinessType> list);

        void showImage(List<ImageBack> list);

    }

}
