package com.zz.cold.business.storage;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codbking.widget.utils.UIAdjuster;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.EquipmentBean;
import com.zz.cold.bean.ImageBack;

import com.zz.cold.bean.StorageBean;
import com.zz.cold.business.qualification.adapter.ImageDeleteItemAdapter;

import com.zz.cold.business.storage.adapter.EquipmentAdapter;
import com.zz.cold.business.storage.mvp.Contract;
import com.zz.cold.business.storage.mvp.presenter.StorageAddPresenter;
import com.zz.cold.utils.PostUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

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
 * 贮存条件edit
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
    @BindView(R.id.ll_equipment_add)
    LinearLayout ll_equipment_add;

    @BindView(R.id.text_warehouseCode)
    EditText text_warehouseCode;

    @BindView(R.id.text_warehouseName)
    EditText text_warehouseName;

    @BindView(R.id.text_temperatureName)
    EditText text_temperatureName;

    @BindView(R.id.text_isAccord)
    TextView text_isAccord;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    int isAccord = -1;
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
        rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageDeleteItemAdapter(this, images);
        rvImages.setAdapter(adapter);

        rv.setLayoutManager(new LinearLayoutManager(this));
        equipmentAdapter = new EquipmentAdapter(R.layout.item_simple, equipmentBeans);
        rv.setAdapter(equipmentAdapter);


        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            mPresenter.getData(id);
            rv.setVisibility(View.GONE);
            ll_equipment_add.setVisibility(View.GONE);
            toolbar_title.setText("编辑库房信息");
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

    @OnClick({R.id.toolbar_subtitle, R.id.ll_equipment_add, R.id.text_isAccord})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData();
                break;
            case R.id.text_isAccord:
                showSelectPopWindow();
                break;
            case R.id.ll_equipment_add:
                startActivityForResult(new Intent().setClass(AddStorageActivity.this, AddEquipmentActivity.class), 2001);
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
        text_warehouseCode.setText(data.getWarehouseCode() + "");
        text_warehouseName.setText(data.getWarehouseName() + "");
        text_temperatureName.setText(data.getTemperatureName() + "");
        text_isAccord.setText(data.getIsAccord() == 0 ? "否" : "是");
        isAccord = data.getIsAccord();
//        if (data.getEquipmentList() != null) {
//            equipmentBeans.clear();
//            equipmentBeans.addAll(data.getEquipmentList());
//            equipmentAdapter.notifyDataSetChanged();
//        }
    }

    void postData() {
        Map<String, Object> params = new HashMap<>();
        StorageBean storageBean = new StorageBean();
        storageBean.setWarehouseCode(getText(text_warehouseCode));
        storageBean.setWarehouseName(getText(text_warehouseName));
        storageBean.setTemperatureName(getText(text_temperatureName));
        storageBean.setIsAccord(isAccord);

        storageBean.setEnclosureIds(PostUtils.getImageIds(images));
        if (!TextUtils.isEmpty(id)) {
            storageBean.setId( id);
        }else {
            storageBean.setEquipmentList(equipmentBeans);
        }
        mPresenter.submitData(storageBean);
    }

    @Override
    public void showResult() {
        setResult(RESULT_OK);
        finish();

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
                case 2001:
                    EquipmentBean equipmentBean = data.getParcelableExtra("equipment");
                    equipmentBeans.add(equipmentBean);
                    equipmentAdapter.notifyDataSetChanged();
                    break;
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

    SelectPopupWindows selectPopupWindows;

    void showSelectPopWindow() {
        UIAdjuster.closeKeyBoard(this);

        String[] array = {"是", "否"};
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                text_isAccord.setText(msg);
                if (index == 0) {
                    isAccord = 1;
                } else {
                    isAccord = 0;
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }
}