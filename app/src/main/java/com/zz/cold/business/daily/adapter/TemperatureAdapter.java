package com.zz.cold.business.daily.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.bean.TemperatureBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */
//温度
public class TemperatureAdapter extends BaseQuickAdapter<TemperatureBean, BaseViewHolder> {
    public TemperatureAdapter(@LayoutRes int layoutResId, @Nullable List<TemperatureBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final TemperatureBean item) {
        //名称，温度

    }
}