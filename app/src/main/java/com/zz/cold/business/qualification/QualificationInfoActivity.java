package com.zz.cold.business.qualification;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.business.qualification.adapter.ImageItemAdapter;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 * 冷库资质详情
 */
public class QualificationInfoActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<ImageBack> images = new ArrayList<>();
    ImageItemAdapter adapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;
    QualificationBean qualificationBean;
    String id;
    private CustomDialog customDialog;
    
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_coldstorageType)
    TextView tv_coldstorageType;
    @BindView(R.id.tv_operatorName)
    TextView tv_operatorName;

    @BindView(R.id.tv_socialCreditCode)
    TextView tv_socialCreditCode;

    @BindView(R.id.tv_licenseNumber)
    TextView tv_licenseNumber;

    @BindView(R.id.tv_contact)
    TextView tv_contact;

    @BindView(R.id.tv_contactInformation)
    TextView tv_contactInformation;

    @BindView(R.id.tv_loginName)
    TextView tv_loginName;

    @BindView(R.id.tv_password)
    TextView tv_password;
    @Override
    protected int getContentView() {
        return R.layout.activity_qualification_info;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rvImages.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageItemAdapter(R.layout.item_image, images);
        rvImages.setAdapter(adapter);
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            getData(id);
            getImages(id);
        }
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @OnClick({R.id.toolbar_subtitle, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:

                Intent intent = new Intent();
                intent.setClass(QualificationInfoActivity.this, AddQualificationActivity.class);
                intent.putExtra("id", qualificationBean.getId());
                startActivity(intent);
                break;
            case R.id.tv_delete:

                if (qualificationBean == null) return;
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
                                deleteDate(qualificationBean.getId());
                            }
                        });
                customDialog = builder.create();
                customDialog.show();
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

    public void showResult(QualificationBean data) {
        id = data.getId();
        tv_operatorName.setText(data.getOperatorName()+"");
        tv_socialCreditCode.setText(data.getSocialCreditCode()+"");
        tv_licenseNumber.setText(data.getLicenseNumber()+"");
        tv_contact.setText(data.getContact()+"");
        tv_contactInformation.setText(data.getContactInformation()+"");
        tv_loginName.setText(data.getLoginName()+"");
        tv_password.setText(data.getPassword()+"");
        tv_location.setText(data.getAddress()+"");
        tv_coldstorageType.setText(data.getColdstorageType1Text()+""+data.getColdstorageType2Text());
    }

    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getQualificationInfo(id), new RequestObserver<JsonT<QualificationBean>>(this) {
            @Override
            protected void onSuccess(JsonT<QualificationBean> jsonT) {
                qualificationBean = jsonT.getData();
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<QualificationBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }
    void getImages(String id) {
        RxNetUtils.request(getApi(ApiService.class).getModelImages("coldstorage",id), new RequestObserver<JsonT<List<ImageBack>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ImageBack>> jsonT) {
                if (jsonT.getData()!=null){
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

    void deleteDate(String id) {
        RxNetUtils.request(getApi(ApiService.class).removeQualificationInfo(id), new RequestObserver<JsonT>() {
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

}