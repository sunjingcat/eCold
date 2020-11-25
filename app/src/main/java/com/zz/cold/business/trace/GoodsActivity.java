package com.zz.cold.business.trace;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.InfoBean;
import com.zz.cold.bean.PendingGoods;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.WmsBean;
import com.zz.cold.business.trace.adapter.InfoAdapter;
import com.zz.cold.business.trace.adapter.WmsAdapter;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.cold.utils.TabUtils;
import com.zz.cold.widget.InputDialog;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.rv_info)
    RecyclerView rv_info;
    @BindView(R.id.bt_ru)
    Button bt_ru;
    @BindView(R.id.bt_chu)
    Button bt_chu;
    @BindView(R.id.ll_review)
    LinearLayout ll_review;


    private WmsAdapter adapter;
    private InfoAdapter infoAdapter;
    List<WmsBean> mlist = new ArrayList<>();
    List<InfoBean> infoList = new ArrayList<>();

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

        rv_info.setLayoutManager(new LinearLayoutManager(this));
        infoAdapter = new InfoAdapter(R.layout.item_info, infoList);
        rv_info.setAdapter(infoAdapter);
        String from = getIntent().getStringExtra("from");
        if (!TextUtils.isEmpty(from)) {
            ll_review.setVisibility(View.VISIBLE);
        } else {
            ll_review.setVisibility(View.GONE);
        }
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }
        if (CacheUtility.getRole() == 2) {
            bt_ru.setVisibility(View.VISIBLE);
            bt_chu.setVisibility(View.VISIBLE);
        } else {
            bt_ru.setVisibility(View.GONE);
            bt_chu.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    public void showResult(TraceBean data) {

        if (data == null) return;
        traceBean = data;
        infoList.add(new InfoBean("商品名称", data.getGoodsName() + ""));
        infoList.add(new InfoBean("进货品种", data.getGoodsType1Text() + "" + data.getGoodsType2Text() + data.getGoodsType3Text()));
        infoList.add(new InfoBean("生产日期/批次", data.getProductionDate() + ""));
        infoList.add(new InfoBean("规格", data.getSpec() + ""));
        infoList.add(new InfoBean("入库数量", data.getCount() + ""));
        infoList.add(new InfoBean("进货时间", data.getPurchaseTime() + ""));
        infoList.add(new InfoBean("供货单位", data.getSupplierName() + ""));
        infoList.add(new InfoBean("供货者地址", data.getSupplierAddress() + ""));
        infoList.add(new InfoBean("联系方式", data.getSupplierContact() + ""));
        infoList.add(new InfoBean("运输方式", data.getTransportModeText() + ""));
        infoList.add(new InfoBean("保质期", data.getPeriod() + ""));
        infoList.add(new InfoBean("是否进口", data.getIsImportedText() + ""));
        if (!TextUtils.isEmpty(data.getIsImportedText()) && data.getIsImportedText().equals("进口")) {
            infoList.add(new InfoBean("入境口岸", data.getEntryPort() + ""));
            infoList.add(new InfoBean("食品核酸检测", data.getIsSphsjcText() + ""));
            infoList.add(new InfoBean("人员核酸检测", data.getIsRyhsjcText() + ""));
            infoList.add(new InfoBean("车辆核酸检测", data.getIsClhsjcText() + ""));
            infoList.add(new InfoBean("消毒证明", data.getIsXdzmText() + ""));
            infoList.add(new InfoBean("非非洲猪瘟检测报告", data.getIsFfzzwjcText() + ""));
        }
        infoAdapter.notifyDataSetChanged();

        if (data.getColdchainGoodsAccountcList() != null) {
            mlist.clear();
            mlist.addAll(data.getColdchainGoodsAccountcList());
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.bt_ru, R.id.bt_chu, R.id.bt_ok, R.id.bt_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_ru:
                startActivity(new Intent(GoodsActivity.this, DeliverActivity.class)
                        .putExtra("operationType", 1)
                        .putExtra("id", id)
                        .putExtra("name", traceBean.getGoodsName()));
                break;
            case R.id.bt_chu:
                if (traceBean == null) return;
                Intent intent = new Intent(GoodsActivity.this, DeliverActivity.class)
                        .putExtra("id", id)
                        .putExtra("operationType", 2)
                        .putExtra("name", traceBean.getGoodsName());
                startActivity(intent);
                break;
            case R.id.bt_ok:
                ask(1, traceBean.getId());
                break;
            case R.id.bt_no:
                askRefuse(0, traceBean.getId());
                break;
        }
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

    private CustomDialog customDialog;
    private InputDialog inputDialog;

    void ask(int reviewStatus, String id) {

        CustomDialog.Builder builder = new CustomDialog.Builder(this)
                .setTitle("审核通过确认")
                .setMessage("是否通过该条" + "请求?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reviewGoods(reviewStatus, id, "");
                    }
                });
        customDialog = builder.create();
        customDialog.show();
    }

    void askRefuse(int reviewStatus, String id) {
        InputDialog.Builder builder = new InputDialog.Builder(this)
                .setTitle("审核不通过确认")
                .setMessage("")
                .setNegativeButton("取消", new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, String msg) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, String msg) {
                        reviewGoods(reviewStatus, id, msg);
                    }
                });
        inputDialog = builder.create();
        inputDialog.show();
    }

    void reviewGoods(int reviewStatus, String id, String reviewRemark) {
        Map<String, Object> map = new HashMap<>();
        map.put("reviewStatus", reviewStatus);
        map.put("reviewRemark", reviewRemark);

        RxNetUtils.request(getApi(ApiService.class).reviewGoods(id, map), new RequestObserver<JsonT>() {
            @Override
            protected void onSuccess(JsonT jsonT) {
                getData(id);
            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
        if (inputDialog != null && inputDialog.isShowing()) {
            inputDialog.dismiss();
        }
    }
}