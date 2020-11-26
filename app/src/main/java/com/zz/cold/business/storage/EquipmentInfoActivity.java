package com.zz.cold.business.storage;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.EquipmentBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.StorageBean;
import com.zz.cold.business.qualification.adapter.ImageDeleteItemAdapter;
import com.zz.cold.business.qualification.adapter.ImageItemAdapter;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 * 设备详情
 */
public class EquipmentInfoActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<ImageBack> images = new ArrayList<>();
    ImageItemAdapter adapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;

    @BindView(R.id.text_equipmentName)
    TextView text_equipmentName;
    @BindView(R.id.text_equipmentRemark)
    TextView text_equipmentRemark;
    String id;
    EquipmentBean equipmentBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_equipment_info;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageItemAdapter(R.layout.item_image, images);
        rvImages.setAdapter(adapter);
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @OnClick({R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                if (equipmentBean == null) return;
                Intent intent = new Intent();
                intent.setClass(EquipmentInfoActivity.this, AddEquipmentActivity.class);
                intent.putExtra("id", equipmentBean.getId());
                intent.putExtra("warehouseId", equipmentBean.getWarehouseId());
                startActivityForResult(intent, 5001);
                break;


        }
    }

    public void showEquipmentInfo(EquipmentBean data) {
        text_equipmentName.setText(data.getEquipmentName() + "");
        text_equipmentRemark.setText(data.getEquipmentRemark() + "");
    }

    void getData(String id) {
        getImages(id);
        RxNetUtils.request(getApi(ApiService.class).getEquipmentInfo(id), new RequestObserver<JsonT<EquipmentBean>>(this) {
            @Override
            protected void onSuccess(JsonT<EquipmentBean> jsonT) {
                equipmentBean = jsonT.getData();
                showEquipmentInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<EquipmentBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    void deleteDate(String id) {
        RxNetUtils.request(getApi(ApiService.class).removeEquipment(id), new RequestObserver<JsonT>() {
            @Override
            protected void onSuccess(JsonT jsonT) {
                showToast("删除成功");
                finish();
            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
                ToastUtils.showShort(stringJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    void getImages(String id) {
        RxNetUtils.request(getApi(ApiService.class).getModelImages("equipment", id), new RequestObserver<JsonT<List<ImageBack>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ImageBack>> jsonT) {
                if (jsonT.getData() != null) {
                    images.clear();
                    images.addAll(jsonT.getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFail2(JsonT<List<ImageBack>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }
}