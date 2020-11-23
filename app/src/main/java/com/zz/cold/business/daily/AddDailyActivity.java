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

import androidx.annotation.Nullable;
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
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.DailyBean;
import com.zz.cold.bean.PLocation;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 日报
 */
public class AddDailyActivity extends MyBaseActivity<Contract.IsetDailyAddPresenter> implements Contract.IGetDailyAddView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<TemperatureBean> temperatures = new ArrayList<>();

    TemperatureEditAdapter temperatureEditAdapter;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.text_reportTime)
    TextView text_reportTime;
    String reportTime = "";
    int isRegularCheck = -1;
    int isProhibitedFood = -1;
    String timeType = "am";

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
        temperatureEditAdapter = new TemperatureEditAdapter(R.layout.item_edit_temperature, temperatures);
        rv.setAdapter(temperatureEditAdapter);
        temperatureEditAdapter.setOnclick(new TemperatureEditAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < temperatures.size(); i++) {
                    if (!TextUtils.isEmpty(temperatures.get(i).getEnclosureId())) {
                        localPath.add(temperatures.get(i).getEnclosureId());
                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(AddDailyActivity.this, option + 1000); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {

            }
        });
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
        WarehouseBean warehouseBean = new WarehouseBean();
        warehouseBean.setIsProhibitedFood(isProhibitedFood);
        warehouseBean.setIsRegularCheck(isRegularCheck);
        warehouseBean.setReportTime(reportTime);
        warehouseBean.setProhibitedFoodRemark(getText(text_prohibitedFoodRemark));
        warehouseBean.setRegularCheckRemark(getText(text_regularCheckRemark));
        warehouseBean.setTimeType(timeType);
        warehouseBean.setColdchainWarehouseDailyList(temperatures);

        mPresenter.submitData(warehouseBean);
    }

    @Override
    public void showWarehouseData(List<StorageBean> data) {
        temperatures.clear();
        for (StorageBean storageBean : data) {
            TemperatureBean temperatureBean = new TemperatureBean(storageBean.getId(), "", "", storageBean.getWarehouseName());
            temperatures.add(temperatureBean);
        }

        temperatureEditAdapter.notifyDataSetChanged();
    }

    @Override
    public void showResult() {
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void showPostImage(int position , ImageBack imageBack) {
        if (imageBack == null&&position>=temperatures.size()) return;
        temperatures.get(position).setEnclosureId(imageBack.getId());
        temperatures.get(position).setEnclosureURL(imageBack.getDownloadUrl() );
        temperatureEditAdapter.notifyDataSetChanged();
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
                    if (index == 0) {
                        isRegularCheck = 1;
                        ll_regularCheckRemark.setVisibility(View.VISIBLE);
                    } else {
                        isRegularCheck = 0;
                        ll_regularCheckRemark.setVisibility(View.GONE);
                    }
                } else {
                    text_isProhibitedFood.setText(msg);
                    if (index == 0) {
                        isProhibitedFood = 1;
                        ll_prohibitedFoodRemark.setVisibility(View.VISIBLE);
                    } else {
                        isProhibitedFood = 0;
                        ll_prohibitedFoodRemark.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode >= 1000) {
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

                                    mPresenter.postImage(requestCode - 1000, getImageBody(file.getPath()));
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                }
                            }).launch();

                }

            }
        }
    }
}