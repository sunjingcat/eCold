package com.zz.cold.business.export.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.bean.GoodsBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class GoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    public GoodsAdapter(@LayoutRes int layoutResId, @Nullable List<GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final GoodsBean item) {

    }
}