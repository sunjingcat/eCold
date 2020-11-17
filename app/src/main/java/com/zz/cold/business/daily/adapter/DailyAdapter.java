package com.zz.cold.business.daily.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.bean.DailyBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class DailyAdapter extends BaseQuickAdapter<DailyBean, BaseViewHolder> {
    public DailyAdapter(@LayoutRes int layoutResId, @Nullable List<DailyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final DailyBean item) {

    }
}