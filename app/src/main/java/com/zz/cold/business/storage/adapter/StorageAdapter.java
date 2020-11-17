package com.zz.cold.business.storage.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.bean.StorageBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class StorageAdapter extends BaseQuickAdapter<StorageBean, BaseViewHolder> {
    public StorageAdapter(@LayoutRes int layoutResId, @Nullable List<StorageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final StorageBean item) {

    }
}