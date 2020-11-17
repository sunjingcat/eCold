package com.zz.cold.business.qualification;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 *冷库资质详情
 */
public class QualificationInfoActivity extends MyBaseActivity  {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<String> images = new ArrayList<>();
    ImageItemAdapter adapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;
    QualificationBean qualificationBean;
    String id;
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

    @OnClick({R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:

                Intent intent = new Intent();
                intent.setClass(QualificationInfoActivity.this, AddQualificationActivity.class);
                intent.putExtra("id",qualificationBean.getId());
                startActivity(intent);
                break;
        }
    }
    public void showResult(QualificationBean data) {


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

}