package com.zz.cold.business.v2.adapter;

import android.graphics.Color;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.TraceBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class GoodsAdapter extends BaseQuickAdapter<TraceBean, BaseViewHolder> {
    public GoodsAdapter(@LayoutRes int layoutResId, @Nullable List<TraceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final TraceBean item) {
        holder.setText(R.id.item_title,item.getGoodsName()+"");
        holder.setText(R.id.item_operatorName,item.getOperatorName()+"");
        holder.setText(R.id.item_count,item.getCount()+item.getSpec());
        holder.setText(R.id.item_time,item.getPurchaseTime()+"");
    }
}