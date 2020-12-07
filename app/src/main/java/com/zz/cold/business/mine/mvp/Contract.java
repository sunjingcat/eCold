package com.zz.cold.business.mine.mvp;

import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.UserBasicBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

public class Contract {
    public interface IsetMineInfoPresenter extends BasePresenter {
        void getMineInfo();
        void getCompany(String id);
        void logout();
    }
    public interface IMineInfoView extends BaseView {
        void showUserInfo(UserBasicBean userInfo);
        void showCompany(QualificationBean qualificationBean);
        void showIntent();
    }



}
