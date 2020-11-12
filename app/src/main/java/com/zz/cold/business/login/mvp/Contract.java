package com.zz.cold.business.login.mvp;



import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.Map;

public class Contract {
    public interface IsetLoginPresenter extends BasePresenter {
        void setAccount(Map<String, Object> params);
    }

    public interface IGetLoginView extends BaseView{

        void showIntent();
    }

}
