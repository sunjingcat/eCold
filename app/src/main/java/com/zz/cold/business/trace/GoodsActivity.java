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
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.InfoBean;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.WmsBean;
import com.zz.cold.business.v2.SellActivity;
import com.zz.cold.business.trace.adapter.InfoAdapter;
import com.zz.cold.business.trace.adapter.WmsAdapter;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.cold.widget.InputDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
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
 * 售
 */
public class GoodsActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rv_info)
    RecyclerView rv_info;
    @BindView(R.id.bt_action)
    Button bt_action;
    @BindView(R.id.bt_action2)
    Button bt_action2;
    @BindView(R.id.ll_jc)
    LinearLayout ll_jc;
    @BindView(R.id.ll_rv)
    LinearLayout ll_rv;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;


    private WmsAdapter adapter;
    private InfoAdapter infoAdapter;
    List<WmsBean> mlist = new ArrayList<>();
    List<InfoBean> infoList = new ArrayList<>();

    String id;
    String reviewId;

    TraceBean traceBean;
    String page = "";

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
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new WmsAdapter(R.layout.item_wms, mlist);
        rv.setAdapter(adapter);

        rv_info.setLayoutManager(new LinearLayoutManager(this));
        infoAdapter = new InfoAdapter(R.layout.item_info, infoList);
        rv_info.setAdapter(infoAdapter);
        page = getIntent().getStringExtra("page");
        id = getIntent().getStringExtra("id");
        reviewId = getIntent().getStringExtra("reviewId");
        int  reviewStatus = getIntent().getIntExtra("reviewStatus",0);
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }
        if (!TextUtils.isEmpty(page)) {
            if (page.equals("import")) {
                bt_action.setVisibility(View.VISIBLE);
                bt_action.setText("进");
            } else if (page.equals("export")) {
                bt_action.setVisibility(View.VISIBLE);
                bt_action.setText("出");
            } else if (page.equals("sell")) {
                bt_action.setVisibility(View.VISIBLE);
                bt_action.setText("售");
            } else if (page.equals("review")) {
                if (reviewStatus==1) {
                    bt_action.setVisibility(View.VISIBLE);
                    bt_action2.setVisibility(View.VISIBLE);
                    bt_action.setText("通过");
                }else {
                    bt_action.setVisibility(View.GONE);
                    bt_action2.setVisibility(View.GONE);
                }
                ll_rv.setVisibility(View.GONE);
            }else {
                bt_action.setVisibility(View.GONE);
                bt_action2.setVisibility(View.GONE);
            }
        }else {
            bt_action.setVisibility(View.GONE);
            bt_action2.setVisibility(View.GONE);
        }
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    ll_jc.setVisibility(View.GONE);
                } else {
                    ll_jc.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }



    public void showResult(TraceBean data) {

        if (data == null) return;
        traceBean = data;
        infoList.clear();
        infoList.add(new InfoBean("商品名称", data.getGoodsName() + ""));
        infoList.add(new InfoBean("进货品种", data.getGoodsType1Text() + "" + data.getGoodsType2Text() + data.getGoodsType3Text()));

        infoList.add(new InfoBean("生产日期", data.getProductionDate() + ""));
        infoList.add(new InfoBean("批号", data.getBatchNumber() + ""));
        infoList.add(new InfoBean("所在冷库名称", data.getOperatorName() + ""));
        infoList.add(new InfoBean("所在冷库存量", data.getCount() + ""+data.getSpec()));
        infoList.add(new InfoBean("商品总库存量", data.getAllCount() + ""+data.getSpec()));
        infoList.add(new InfoBean("进货时间", data.getPurchaseTime() + ""));
        infoList.add(new InfoBean("供货单位", data.getSupplierName() + ""));
        infoList.add(new InfoBean("供货者地址", data.getSupplierAddress() + ""));
        infoList.add(new InfoBean("联系方式", data.getSupplierContact() + ""));
        infoList.add(new InfoBean("产地", data.getProductionAddress() + ""));
        infoList.add(new InfoBean("原产国", data.getOriginCountry() + ""));
        infoList.add(new InfoBean("运输方式", data.getTransportModeText() + ""));
        infoList.add(new InfoBean("保质期", data.getPeriod() + ""));
        infoList.add(new InfoBean("商品备注", data.getGoodsRemark() + ""));
        infoList.add(new InfoBean("是否进口", data.getIsImportedText() + ""));
        if (!TextUtils.isEmpty(data.getIsImportedText()) && data.getIsImportedText().equals("进口")) {
            infoList.add(new InfoBean("入境口岸", data.getEntryPort() + ""));
            infoList.add(new InfoBean("进口企业注册号", data.getImportRegistNum() + ""));
            infoList.add(new InfoBean("进口时间", data.getImportTime() + ""));
           InfoBean sphsjc = new InfoBean("食品核酸检测", data.getIsSphsjcText() + "");
            sphsjc.setImages(data.getSphsjcEnclosureList());
            infoList.add(sphsjc);
            InfoBean crjjyjyzm = new InfoBean("出入境检验检疫证明", data.getIsCrjjyjyzmText() + "");
            crjjyjyzm.setImages(data.getCrjjyjyzmEnclosureList());
            infoList.add(crjjyjyzm);
            InfoBean   bgd = new InfoBean("报关单", data.getIsBgdText() + "");
            bgd.setImages(data.getBgdEnclosureList());
            infoList.add(bgd);
            InfoBean  xdzm = new InfoBean("消毒证明", data.getIsXdzmText() + "");
            xdzm.setImages(data.getXdzmEnclosureList());
            infoList.add(xdzm);
            InfoBean ffzzwjc = new InfoBean("非非洲猪瘟检测报告", data.getIsFfzzwjcText() + "");
            ffzzwjc.setImages(data.getFfzzwjcEnclosureList());
            infoList.add(ffzzwjc);
            InfoBean mddhsjc = new InfoBean("目的地核酸检测", data.getIsMddhsjcText() + "");
            mddhsjc.setImages(data.getMddhsjcEnclosureList());
            infoList.add(mddhsjc);
        }
        infoAdapter.notifyDataSetChanged();
        if (data.getColdchainGoodsAccountcList() != null) {
            mlist.clear();
            mlist.addAll(data.getColdchainGoodsAccountcList());
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.bt_action, R.id.bt_action2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_action:
                if (page.equals("import")) {
                    startActivityForResult(new Intent(GoodsActivity.this, DeliverActivity.class)
                            .putExtra("operationType", 1)
                            .putExtra("id", id)
                            .putExtra("isThird", traceBean.getIsThird())
                            .putExtra("name", traceBean.getGoodsName()), 1001);
                } else if (page.equals("export")) {
                    Intent intent = new Intent(GoodsActivity.this, DeliverActivity.class)
                            .putExtra("id", id)
                            .putExtra("operationType", 2)
                            .putExtra("name", traceBean.getGoodsName());
                    startActivityForResult(intent, 1002);
                } else if (page.equals("sell")) {
                    Intent intent = new Intent(GoodsActivity.this, SellActivity.class)
                            .putExtra("id", id)
                            .putExtra("name", traceBean.getGoodsName());
                    startActivityForResult(intent, 1003);
                } else if (page.equals("review")) {
                    ask(2, reviewId);
                }
                break;
            case R.id.bt_action2:
                askRefuse(3, reviewId);
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
    private CustomDialog customDialog2;
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
    void ask2(String msg) {

        CustomDialog.Builder builder = new CustomDialog.Builder(this)
                .setTitle("提示")
                .setMessage(msg+"")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        customDialog2 = builder.create();
        customDialog2.show();
    }
    void reviewGoods(int reviewStatus, String id, String reviewRemark) {
        Map<String, Object> map = new HashMap<>();
        map.put("reviewStatus", reviewStatus);
        map.put("reviewRemark", reviewRemark);

        RxNetUtils.request(getApi(ApiService.class).reviewGoods(id, map), new RequestObserver<JsonT>() {
            @Override
            protected void onSuccess(JsonT jsonT) {
                finish();

            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
                if (!TextUtils.isEmpty(stringJsonT.getMessage())){
                    ask2(stringJsonT.getMessage());
                }
            }
        }, LoadingUtils.build(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
        if (customDialog2 != null && customDialog2.isShowing()) {
            customDialog2.dismiss();
        }
        if (inputDialog != null && inputDialog.isShowing()) {
            inputDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getData(id);
        }
    }
}