package com.zz.cold.business.qualification;

import android.Manifest;
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
import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.PLocation;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.business.map.SelectLocationActivity;
import com.zz.cold.business.qualification.adapter.ImageDeleteItemAdapter;
import com.zz.cold.business.qualification.mvp.Contract;
import com.zz.cold.business.qualification.mvp.presenter.QualificationAddPresenter;
import com.zz.cold.utils.PostUtils;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 冷库资质edit
 */
public class AddQualificationActivity extends MyBaseActivity<Contract.IsetQualificationAddPresenter> implements Contract.IGetQualificationAddView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<ImageBack> images = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.et_location)
    TextView etLocation;
    @BindView(R.id.et_coldstorageType)
    TextView et_coldstorageType;
    @BindView(R.id.et_coldstorageType2)
    TextView et_coldstorageType2;
    String coldstorageType1 = "";
    String coldstorageType2 = "";

    @BindView(R.id.et_operatorName)
    EditText etOperatorName;

    @BindView(R.id.et_socialCreditCode)
    EditText et_socialCreditCode;

    @BindView(R.id.et_licenseNumber)
    EditText et_licenseNumber;

    @BindView(R.id.et_contact)
    EditText et_contact;

    @BindView(R.id.et_contactInformation)
    EditText et_contactInformation;

    @BindView(R.id.et_loginName)
    EditText et_loginName;

    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.ll_loginName)
    LinearLayout ll_loginName;
    @BindView(R.id.ll_password)
    LinearLayout ll_password;
    @BindView(R.id.ll_coldstorageType2)
    LinearLayout ll_coldstorageType2;

    QualificationBean qualificationBean;

    String id;
    double lon = 123.6370661238426;
    double lat = 47.216275430241495;
    SelectPopupWindows selectPopupWindows;
    SelectPopupWindows selectPopupWindows2;
    List<DictBean> coldStorageTypes = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_qualification_add;

    }

    @Override
    public QualificationAddPresenter initPresenter() {
        return new QualificationAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageDeleteItemAdapter(this, images);
        rvImages.setAdapter(adapter);
        mPresenter.getColdStorageType();
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            mPresenter.getData(id);
            mPresenter.getImage("coldstorage", id);
            toolbar_title.setText("资质修改");
            ll_loginName.setVisibility(View.GONE);
            ll_password.setVisibility(View.GONE);
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
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(AddQualificationActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @OnClick({R.id.toolbar_subtitle, R.id.et_location, R.id.et_coldstorageType, R.id.et_coldstorageType2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData();
                break;

            case R.id.et_coldstorageType:
                showSelectPopWindow();
                break;
            case R.id.et_coldstorageType2:
                showSelectPopWindow2(coldstorageType1);
                break;
            case R.id.et_location:
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(AddQualificationActivity.this, SelectLocationActivity.class).putExtra("lat", lat).putExtra("lon", lon), 1002);

                    }

                    @Override
                    public void onDenied() {

                    }
                });


                break;

        }
    }

    void postData() {
        Map<String, Object> params = new HashMap<>();
        params.put("operatorName", getText(etOperatorName));
        params.put("socialCreditCode", getText(et_socialCreditCode));
        params.put("licenseNumber", getText(et_licenseNumber));
        params.put("contact", getText(et_contact));
        params.put("contactInformation", getText(et_contactInformation));
        params.put("loginName", getText(et_loginName));
        params.put("password", getText(et_password));
        params.put("coldstorageType1", coldstorageType1);
        params.put("coldstorageType2", coldstorageType2);
        params.put("address", getText(etLocation));
        params.put("latitude", lat);
        params.put("longitude", lon);
        params.put("enclosureIds", PostUtils.getImageIds(images));
        if (!TextUtils.isEmpty(id)) {
            params.put("id", id);
        }

        mPresenter.submitData(params);
    }

    @Override
    public void showQualificationInfo(QualificationBean data) {
        qualificationBean = data;
        etOperatorName.setText(data.getOperatorName() + "");
        et_socialCreditCode.setText(data.getSocialCreditCode() + "");
        et_licenseNumber.setText(data.getLicenseNumber() + "");
        et_contact.setText(data.getContact() + "");
        et_contactInformation.setText(data.getContactInformation() + "");
        et_loginName.setText(data.getLoginName() + "");
        et_loginName.setEnabled(false);
        et_password.setText("******");
        et_password.setEnabled(false);
        etLocation.setText(data.getAddress() + "");
        et_coldstorageType.setText(data.getColdstorageType1Text());
        et_coldstorageType2.setText(data.getColdstorageType2Text());
        coldstorageType1 = data.getColdstorageType1();
        coldstorageType2 = data.getColdstorageType2();
        ll_coldstorageType2.setVisibility(TextUtils.isEmpty(coldstorageType1) ? View.GONE : View.VISIBLE);
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
    public void showColdStorageType(List<DictBean> list) {
        if (list == null) return;
        coldStorageTypes = list;
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
                case 1002:
                    if (data == null) return;
                    PLocation poiInfo = data.getParcelableExtra("location");
                    if (poiInfo == null) return;
                    if (poiInfo.getLocation() == null) return;
                    lat = poiInfo.getLocation().latitude;
                    lon = poiInfo.getLocation().longitude;
                    etLocation.setText(poiInfo.getAddress() + "");
                    break;
                case 1101:
                    ArrayList<String> selectImages = data.getStringArrayListExtra(
                            ImageSelectorUtils.SELECT_RESULT);
                    ArrayList<String> localPath = new ArrayList<>();
                    for (int i = 0; i < images.size(); i++) {
                        if (!TextUtils.isEmpty(images.get(i).getPath())) {
                            localPath.add(images.get(i).getPath());
                        } else {
                        }
                    }
                    ArrayList<String> imagesUpload = new ArrayList<>();

                    for (String strPath : selectImages) {
                        if (!localPath.contains(strPath)) {
                            imagesUpload.add(strPath);
                        }
                    }
                    for (String path : imagesUpload) {
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

                                        mPresenter.postImage(path, getImageBody(file.getPath()));
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

    void showSelectPopWindow() {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < coldStorageTypes.size(); i++) {
            String dictValue = coldStorageTypes.get(i).getDictValue();
            if (!dictValue.contains(".")) {
                list.add(coldStorageTypes.get(i).getDictLabel());
                list1.add(coldStorageTypes.get(i).getDictValue());
            }
        }
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                et_coldstorageType.setText(msg);
                coldstorageType1 = values[index];
                ll_coldstorageType2.setVisibility(View.VISIBLE);
                et_coldstorageType2.setText("");
                coldstorageType2 = "";
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    void showSelectPopWindow2(String type) {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < coldStorageTypes.size(); i++) {
            String dictValue = coldStorageTypes.get(i).getDictValue();
            if (dictValue.contains(".")) {
                String[] split = dictValue.split("\\.");
                if (split.length == 2 && split[0].equals(type)) {
                    list.add(coldStorageTypes.get(i).getDictLabel());
                    list1.add(coldStorageTypes.get(i).getDictValue());
                }
            }
        }
        if (list.size() == 0) return;
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows2 = new SelectPopupWindows(this, array);
        selectPopupWindows2.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows2.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                et_coldstorageType2.setText(msg);
                coldstorageType2 = values[index];
            }

            @Override
            public void onCancel() {
                selectPopupWindows2.dismiss();
            }
        });
    }


}