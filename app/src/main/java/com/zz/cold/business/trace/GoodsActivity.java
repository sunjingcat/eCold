package com.zz.cold.business.trace;

import android.text.TextUtils;

import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.WmsBean;
import com.zz.cold.business.qualification.adapter.QualificationAdapter;
import com.zz.cold.business.trace.adapter.WmsAdapter;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 * 货品详情
 */
public class GoodsActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;

    private WmsAdapter adapter;
    List<WmsBean> mlist = new ArrayList<>();

    String id;

    TraceBean traceBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_goods;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WmsAdapter(R.layout.item_wms, mlist);
        rv.setAdapter(adapter);
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    public void showResult(TraceBean data) {


    }

    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getTraceInfo(id), new RequestObserver<JsonT<TraceBean>>(this) {
            @Override
            protected void onSuccess(JsonT<TraceBean> jsonT) {
                traceBean = jsonT.getData();
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<TraceBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }
}