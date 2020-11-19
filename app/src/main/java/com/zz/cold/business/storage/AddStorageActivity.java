package com.zz.cold.business.storage;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.EquipmentBean;
import com.zz.cold.bean.ImageBack;

import com.zz.cold.bean.StorageBean;
import com.zz.cold.business.qualification.AddQualificationActivity;
import com.zz.cold.business.qualification.adapter.ImageDeleteItemAdapter;

import com.zz.cold.business.storage.adapter.EquipmentAdapter;
import com.zz.cold.business.storage.mvp.Contract;
import com.zz.cold.business.storage.mvp.presenter.StorageAddPresenter;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *贮存条件edit
 */
public class AddStorageActivity extends MyBaseActivity<Contract.IsetStorageAddPresenter> implements Contract.IGetStorageAddView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<ImageBack> images = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;
    StorageBean storageBean;
    String id;
    @BindView(R.id.rv)
    RecyclerView rv;
    ArrayList<EquipmentBean> equipmentBeans = new ArrayList<>();
    EquipmentAdapter equipmentAdapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_storage_add;

    }

    @Override
    public StorageAddPresenter initPresenter() {
        return new StorageAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rvImages.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new ImageDeleteItemAdapter(this, images);
        rvImages.setAdapter(adapter);

        rv.setLayoutManager(new LinearLayoutManager(this));
        equipmentAdapter = new EquipmentAdapter(R.layout.item_simple, equipmentBeans);
        rv.setAdapter(equipmentAdapter);


        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            mPresenter.getData(id);
        }
        adapter.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    if (!TextUtils.isEmpty(images.get(i).getPath())) {
                        localPath.add(images.get(i).getPath());
                    } else {
                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - images.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(AddStorageActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });
    }
    @OnClick({R.id.toolbar_subtitle,  R.id.ll_equipment_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:

                break;
            case R.id.ll_equipment_add:
                if (storageBean == null) return;
                startActivityForResult(new Intent().setClass(AddStorageActivity.this, AddEquipmentActivity.class).putExtra("id", storageBean.getId()), 2001);
                break;
        }
    }
    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @Override
    public void showStorageInfo(StorageBean data) {
        storageBean = data;
    }

    @Override
    public void showSubmitResult(String id) {

    }

    @Override
    public void showResult() {

    }

    @Override
    public void showPostImage(int position, String id) {

    }

    @Override
    public void showImage(List<ImageBack> list) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 2001:
                    String name = data.getStringExtra("name");
                    EquipmentBean equipmentBean = new EquipmentBean();
                    equipmentBean.setId(name);
                    equipmentBeans.add(equipmentBean);
                    equipmentAdapter.notifyDataSetChanged();
                    break;
            }
        }

    }
}