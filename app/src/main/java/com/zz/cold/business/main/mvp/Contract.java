package com.zz.cold.business.main.mvp;

import com.zz.cold.bean.MapListBean;
import com.zz.cold.bean.UserBasicBean;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;

public class Contract {


    public interface IGetMapView extends BaseView {
        void showResult(List<MapListBean> listBeans);
        void showUserInfo(UserBasicBean userInfo);
        void showError();

    }
}
