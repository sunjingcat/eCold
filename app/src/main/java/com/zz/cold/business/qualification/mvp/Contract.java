package com.zz.cold.business.qualification.mvp;

import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.QualificationBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetQualificationAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getData(String id);
        void getColdStorageType();

        void postImage(String localPath,List<MultipartBody.Part> imgs);

        void getImage(String type,String modelId);
    }

    public interface IGetQualificationAddView extends BaseView {
        void showQualificationInfo(QualificationBean data);
        void showResult();
        void showPostImage(String localPath,ImageBack imageBack);

        void showColdStorageType(List<DictBean> list);

        void showImage(List<ImageBack> list);

    }

}
