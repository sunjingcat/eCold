package com.zz.cold.business.storage.mvp;

import com.zz.cold.bean.EquipmentBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.StorageBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetStorageAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getData(String id);

        void postImage(String localPath,List<MultipartBody.Part> imgs);

        void getImage(String type,String modelId);
    }

    public interface IGetStorageAddView extends BaseView {
        void showStorageInfo(StorageBean data);

        void showResult();
        void showPostImage(String localPath,ImageBack imageBack);

        void showImage(List<ImageBack> list);

    }

    public interface IsetEquipmentAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getData(String id);

        void postImage(int position,String files);
        void uploadStorageImgs(String id,String files);

        void getImage(String type,String modelId);
    }

    public interface IGetEquipmentAddView extends BaseView {
        void showEquipmentInfo(EquipmentBean data);

        void showSubmitResult(String id);

        void showResult();
        void showPostImage(int position,String id);


        void showImage(List<ImageBack> list);

    }

}
