package com.zz.cold.business.daily;

import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.DailyBean;
import com.zz.cold.bean.StorageBean;
import com.zz.cold.bean.TemperatureBean;
import com.zz.cold.business.daily.adapter.TemperatureEditAdapter;
import com.zz.cold.business.daily.mvp.Contract;
import com.zz.cold.business.daily.mvp.presenter.DailyAddPresenter;

import com.zz.cold.business.qualification.adapter.ImageDeleteItemAdapter;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *日报
 */
public class AddDailyActivity extends MyBaseActivity<Contract.IsetDailyAddPresenter> implements Contract.IGetDailyAddView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<ImageBack> images = new ArrayList<>();
    ArrayList<TemperatureBean> temperatures = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    TemperatureEditAdapter temperatureEditAdapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;

    @BindView(R.id.rv)
    RecyclerView rv;
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
        rvImages.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new ImageDeleteItemAdapter(this, images);
        rvImages.setAdapter(adapter);

        rv.setLayoutManager(new LinearLayoutManager(this));
        temperatureEditAdapter = new TemperatureEditAdapter(R.layout.item_edit_temperature, temperatures);
        rv.setAdapter(temperatureEditAdapter);


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
                        .start(AddDailyActivity.this, 1101); // 打开相册
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


    @Override
    public void showDailyInfo(DailyBean data) {
        DailyBean = data;
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
}