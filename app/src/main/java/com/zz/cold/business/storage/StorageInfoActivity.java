package com.zz.cold.business.storage;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.EquipmentBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.StorageBean;
import com.zz.cold.business.qualification.AddQualificationActivity;
import com.zz.cold.business.qualification.adapter.ImageItemAdapter;
import com.zz.cold.business.storage.adapter.EquipmentAdapter;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 * 贮存条件详情
 */
public class StorageInfoActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<ImageBack> images = new ArrayList<>();
    ArrayList<EquipmentBean> equipmentBeans = new ArrayList<>();
    ImageItemAdapter adapter;
    EquipmentAdapter equipmentAdapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;
    @BindView(R.id.rv)
    RecyclerView rv;
    StorageBean storageBean;
    String id;
    @BindView(R.id.text_warehouseCode)
    TextView text_warehouseCode;

    @BindView(R.id.text_warehouseName)
    TextView text_warehouseName;

    @BindView(R.id.text_temperatureName)
    TextView text_temperatureName;

    @BindView(R.id.text_isAccord)
    TextView text_isAccord;
    private CustomDialog customDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_storage_info;

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

        rv.setLayoutManager(new LinearLayoutManager(this));
        equipmentAdapter = new EquipmentAdapter(R.layout.item_simple, equipmentBeans);
        rv.setAdapter(equipmentAdapter);

        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }
        equipmentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @OnClick({R.id.toolbar_subtitle, R.id.tv_delete, R.id.tv_equipment_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                if (storageBean == null) return;
                Intent intent = new Intent();
                intent.setClass(StorageInfoActivity.this, AddStorageActivity.class);
                intent.putExtra("id", storageBean.getId());
                startActivityForResult(intent, 5001);
                break;
            case R.id.tv_delete:
                if (storageBean == null) return;
                CustomDialog.Builder builder = new CustomDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定删除？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteDate(storageBean.getId());
                            }
                        });
                customDialog = builder.create();
                customDialog.show();
                break;
            case R.id.tv_equipment_add:
                if (storageBean == null) return;
                startActivityForResult(new Intent().setClass(StorageInfoActivity.this, AddEquipmentActivity.class).putExtra("warehouseId", storageBean.getId()), 2001);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    public void showResult(StorageBean data) {
        storageBean = data;
        text_warehouseCode.setText(data.getWarehouseCode() + "");
        text_warehouseName.setText(data.getWarehouseName() + "");
        text_temperatureName.setText(data.getTemperatureName() + "");
        text_isAccord.setText(data.getIsAccord() == 0 ? "否" : "是");
        if (data.getEquipmentList() != null) {
            equipmentBeans.clear();
            equipmentBeans.addAll(data.getEquipmentList());
            equipmentAdapter.notifyDataSetChanged();
        }
    }

    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getStorageInfo(id), new RequestObserver<JsonT<StorageBean>>(this) {
            @Override
            protected void onSuccess(JsonT<StorageBean> jsonT) {
                storageBean = jsonT.getData();
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<StorageBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    void deleteDate(String id) {
        RxNetUtils.request(getApi(ApiService.class).removeStorageInfo(id), new RequestObserver<JsonT>() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getData(id);
        }

    }

}