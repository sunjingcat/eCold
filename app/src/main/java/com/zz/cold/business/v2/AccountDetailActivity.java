package com.zz.cold.business.v2;

import android.text.TextUtils;

import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.InfoBean;
import com.zz.cold.bean.StorageAccount;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.WmsBean;
import com.zz.cold.business.trace.adapter.InfoAdapter;
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
 *
 */
public class AccountDetailActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_info)
    RecyclerView rv_info;
    private InfoAdapter infoAdapter;
    List<InfoBean> infoList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_account_detail;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv_info.setLayoutManager(new LinearLayoutManager(this));
        infoAdapter = new InfoAdapter(R.layout.item_info, infoList);
        rv_info.setAdapter(infoAdapter);
        String id = getIntent().getStringExtra("id");
        getData(id);
    }

    public void showResult(StorageAccount data) {

        if (data == null) return;
        infoList.add(new InfoBean("商品名称", data.getGoodsName() + ""));
        infoList.add(new InfoBean("冷库名称", data.getOperatorName() + ""));
        infoList.add(new InfoBean("购买数量", data.getCount() + ""));
        infoList.add(new InfoBean("购买人姓名", data.getBuyerContact() + ""));
        infoList.add(new InfoBean("购买人家庭住址", data.getBuyerAddress() + ""));
        infoList.add(new InfoBean("购买人联系方式", data.getBuyerContact() + ""));
        infoList.add(new InfoBean("购买人用途", data.getReviewRemark() + ""));

        infoAdapter.notifyDataSetChanged();

    }

    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getStorageAccount(id), new RequestObserver<JsonT<StorageAccount>>(this) {
            @Override
            protected void onSuccess(JsonT<StorageAccount> jsonT) {
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<StorageAccount> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }
}