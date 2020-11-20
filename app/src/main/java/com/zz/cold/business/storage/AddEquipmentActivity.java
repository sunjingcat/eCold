package com.zz.cold.business.storage;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
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
import com.zz.cold.business.storage.mvp.presenter.EquipmentAddPresenter;
import com.zz.cold.business.storage.mvp.presenter.StorageAddPresenter;
import com.zz.cold.utils.PostUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 贮存设备edit
 */
public class AddEquipmentActivity extends MyBaseActivity<Contract.IsetEquipmentAddPresenter> implements Contract.IGetEquipmentAddView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<ImageBack> images = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;

    String warehouseId;

    @BindView(R.id.text_equipmentName)
    EditText text_equipmentName;
    @BindView(R.id.text_equipmentRemark)
    EditText text_equipmentRemark;

    @Override
    protected int getContentView() {
        return R.layout.activity_equipment_add;

    }

    @Override
    public EquipmentAddPresenter initPresenter() {
        return new EquipmentAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageDeleteItemAdapter(this, images);
        rvImages.setAdapter(adapter);
        warehouseId = getIntent().getStringExtra("warehouseId");

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
                        .start(AddEquipmentActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                if (TextUtils.isEmpty(warehouseId)) {
                    setResult();
                } else {
                    postData();
                }

                break;

        }
    }

    void setResult() {
        EquipmentBean equipmentBean = new EquipmentBean(getText(text_equipmentName), getText(text_equipmentRemark), PostUtils.getImageIds(images));
        setResult(RESULT_OK, new Intent().putExtra("equipment", equipmentBean));
        finish();
    }

    void postData() {
        Map<String, Object> params = new HashMap<>();
        params.put("equipmentName", getText(text_equipmentName));
        params.put("equipmentRemark", getText(text_equipmentRemark));
        params.put("enclosureIds", PostUtils.getImageIds(images));
        params.put("warehouseId", warehouseId);
        mPresenter.submitData(params);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @Override
    public void showEquipmentInfo(EquipmentBean data) {

    }


    @Override
    public void showResult() {
        setResult();

    }

    @Override
    public void showPostImage(String localPath, ImageBack imageBack) {
        if (imageBack == null) return;
        imageBack.setPath(localPath);
        images.add(imageBack);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showImage(List<ImageBack> list) {
        if (list == null) return;
        images.clear();
        images.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1101:
                    ArrayList<String> selectImages = data.getStringArrayListExtra(
                            ImageSelectorUtils.SELECT_RESULT);
                    for (String path : selectImages) {
                        Luban.with(this)
                                .load(path)
                                .ignoreBy(100)
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {
                                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                    }

                                    @Override
                                    public void onSuccess(File file) {

                                        mPresenter.postImage(file.getPath(), getImageBody(file.getPath()));
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        // TODO 当压缩过程出现问题时调用
                                    }
                                }).launch();

                    }
                    break;
            }
        }

    }
}