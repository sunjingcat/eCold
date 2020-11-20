package com.zz.cold.business.daily;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.DailyBean;
import com.zz.cold.bean.StorageBean;
import com.zz.cold.bean.TemperatureBean;
import com.zz.cold.bean.WarehouseBean;
import com.zz.cold.business.daily.adapter.TemperatureEditAdapter;
import com.zz.cold.business.daily.mvp.Contract;
import com.zz.cold.business.daily.mvp.presenter.DailyAddPresenter;

import com.zz.cold.business.qualification.AddQualificationActivity;
import com.zz.cold.business.qualification.adapter.ImageDeleteItemAdapter;
import com.zz.cold.utils.PostUtils;
import com.zz.cold.utils.TimeUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 日报
 */
public class AddDailyActivity extends MyBaseActivity<Contract.IsetDailyAddPresenter> implements Contract.IGetDailyAddView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<ImageBack> images = new ArrayList<>();
    ArrayList<WarehouseBean> warehouses = new ArrayList<>();

    TemperatureEditAdapter temperatureEditAdapter;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.text_reportTime)
    TextView text_reportTime;
    String reportTime = "";
    int isRegularCheck = -1;
    int isProhibitedFood = -1;
    String timeType = "";

    @BindView(R.id.text_regularCheckRemark)
    EditText text_regularCheckRemark;

    @BindView(R.id.ll_regularCheckRemark)
    LinearLayout ll_regularCheckRemark;

    @BindView(R.id.text_isRegularCheck)
    TextView text_isRegularCheck;

    @BindView(R.id.text_prohibitedFoodRemark)
    EditText text_prohibitedFoodRemark;

    @BindView(R.id.ll_prohibitedFoodRemark)
    LinearLayout ll_prohibitedFoodRemark;

    @BindView(R.id.text_isProhibitedFood)
    TextView text_isProhibitedFood;

    @BindView(R.id.rg_time)
    RadioGroup rg_time;
    DailyBean DailyBean;
    String id;

    @Override
    protected int getContentView() {
        return R.layout.activity_daily_add;

    }

    @Override
    public DailyAddPresenter initPresenter() {
        return new DailyAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);


        rv.setLayoutManager(new LinearLayoutManager(this));
        temperatureEditAdapter = new TemperatureEditAdapter(R.layout.item_edit_temperature, warehouses);
        rv.setAdapter(temperatureEditAdapter);

        mPresenter.getWarehouseData();
        rg_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_am:
                        timeType = "am";
                        break;
                    case R.id.rb_pm:
                        timeType = "pm";
                        break;
                }
            }
        });
    }

    @OnClick({R.id.toolbar_subtitle, R.id.text_reportTime, R.id.text_isProhibitedFood, R.id.text_isRegularCheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData();
                break;
            case R.id.text_isProhibitedFood:
                showSelectPopWindow(1);
                break;
            case R.id.text_isRegularCheck:
                showSelectPopWindow(2);
                break;
            case R.id.text_reportTime:
                DatePickDialog dialog = new DatePickDialog(AddDailyActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog.setYearLimt(20);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {

                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        reportTime = time;
                        text_reportTime.setText(time);
                    }
                });
                dialog.show();
                break;

        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }



    void postData() {
        Map<String, Object> params = new HashMap<>();
        params.put("isProhibitedFood", isProhibitedFood);
        params.put("isRegularCheck", isRegularCheck);
        params.put("reportTime", reportTime);
        params.put("prohibitedFoodRemark", getText(text_prohibitedFoodRemark));
        params.put("regularCheckRemark", getText(text_regularCheckRemark));
        params.put("timeType", timeType);

        mPresenter.submitData(params);
    }

    @Override
    public void showWarehouseData(List<WarehouseBean> data) {
        warehouses.clear();
        warehouses.addAll(data);
        temperatureEditAdapter.notifyDataSetChanged();
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

    }


    @Override
    public void showImage(List<ImageBack> list) {
        if (list == null) return;
        images.clear();
        images.addAll(list);

    }

    SelectPopupWindows selectPopupWindows;

    void showSelectPopWindow(int flag) {
        UIAdjuster.closeKeyBoard(this);

        String[] array = {"是", "否"};
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                if (flag == 2) {
                    text_isRegularCheck.setText(msg);
                    if (index==0){
                        isRegularCheck = 1;
                    }else {
                        isRegularCheck = 0;
                    }
                } else {
                    text_isProhibitedFood.setText(msg);
                    if (index==0){
                        isProhibitedFood = 1;
                    }else {
                        isProhibitedFood = 0;
                    }
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }
}