package com.zz.cold.business.daily;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;

import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.DailyBean;

import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.TemperatureBean;
import com.zz.cold.business.daily.adapter.TemperatureAdapter;
import com.zz.cold.business.qualification.AddQualificationActivity;
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
 * 日报详情
 */
public class DailyInfoActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<DailyBean.Temperature> amList = new ArrayList<>();
    ArrayList<DailyBean.Temperature> pmList = new ArrayList<>();

    TemperatureAdapter amAdapter;
    TemperatureAdapter pmAdapter;


    @BindView(R.id.rv_am)
    RecyclerView rvAm;

    @BindView(R.id.rv_pm)
    RecyclerView rvPm;
    DailyBean dailyBean;
    String id;


    @BindView(R.id.text_isRegularCheck)
    TextView text_isRegularCheck;

    @BindView(R.id.text_isProhibitedFood)
    TextView text_isProhibitedFood;


    @BindView(R.id.text_regularCheckRemark)
    TextView text_regularCheckRemark;

    @BindView(R.id.ll_regularCheckRemark)
    LinearLayout ll_regularCheckRemark;


    @BindView(R.id.text_prohibitedFoodRemark)
    TextView text_prohibitedFoodRemark;

    @BindView(R.id.ll_prohibitedFoodRemark)
    LinearLayout ll_prohibitedFoodRemark;

    private CustomDialog customDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_daily_info;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        rvAm.setLayoutManager(new LinearLayoutManager(this));
        amAdapter = new TemperatureAdapter(R.layout.item_temperature, amList);
        rvAm.setAdapter(amAdapter);

        rvPm.setLayoutManager(new LinearLayoutManager(this));
        pmAdapter = new TemperatureAdapter(R.layout.item_temperature, pmList);
        rvPm.setAdapter(pmAdapter);

        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @OnClick({R.id.toolbar_subtitle, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                if (dailyBean == null) return;
                Intent intent = new Intent();
                intent.setClass(DailyInfoActivity.this, AddDailyActivity.class);
                intent.putExtra("id", dailyBean.getId());
                startActivity(intent);
                break;
            case R.id.tv_delete:
                if (dailyBean == null) return;
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
                                deleteDate(dailyBean.getId());
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

    public void showResult(DailyBean data) {
        amList.clear();
        amList.addAll(data.getTemperatureAmList());
        amAdapter.notifyDataSetChanged();

        pmList.clear();
        pmList.addAll(data.getTemperatureAmList());
        pmAdapter.notifyDataSetChanged();

        text_isRegularCheck.setText(data.getIsRegularCheck()==1?"是":"否");
        text_isProhibitedFood.setText(data.getIsProhibitedFood()==1?"是":"否");

        ll_regularCheckRemark.setVisibility(data.getIsRegularCheck()==1?View.VISIBLE:View.GONE);
        ll_prohibitedFoodRemark.setVisibility(data.getIsProhibitedFood()==1?View.VISIBLE:View.GONE);
        text_regularCheckRemark.setText(data.getRegularCheckRemark()+"");
        text_prohibitedFoodRemark.setText(data.getProhibitedFoodRemark()+"");

    }

    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getDailyInfo(id), new RequestObserver<JsonT<DailyBean>>(this) {
            @Override
            protected void onSuccess(JsonT<DailyBean> jsonT) {
                dailyBean = jsonT.getData();
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<DailyBean> stringJsonT) {
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

}