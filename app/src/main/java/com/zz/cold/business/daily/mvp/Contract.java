package com.zz.cold.business.daily.mvp;

import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.DailyBean;
import com.zz.cold.bean.TemperatureBean;
import com.zz.cold.bean.WarehouseBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetDailyAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getWarehouseData();

        void postImage(String localPath, List<MultipartBody.Part> imgs);

        void getImage(String type,String modelId);
    }

    public interface IGetDailyAddView extends BaseView {
        void showWarehouseData(List<WarehouseBean> data);

        void showResult();
        void showPostImage(String localPath,ImageBack imageBack);

        void showImage(List<ImageBack> list);

    }

}
