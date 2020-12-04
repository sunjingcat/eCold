package com.zz.cold.business.export.mvp;

import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.GroupCountBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.TraceBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetExportListPresenter extends BasePresenter {
        void getData(String type,Map<String, Object> map,String id);
        void getTab(String type);
    }

    public interface IGetExportListView extends BaseView {
        void showResult(List<TraceBean> list);
        void showTabType(List<GroupCountBean> list);

    }

}
