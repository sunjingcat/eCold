package com.zz.cold.business.trace.adapter;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.WmsBean;

import java.util.List;

/**
 * 出入库
 */

public class WmsAdapter extends BaseQuickAdapter<WmsBean, BaseViewHolder> {
    public WmsAdapter(@LayoutRes int layoutResId, @Nullable List<WmsBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, final WmsBean item) {
        holder.setText(R.id.item_title,item.getOperationTypeText()+"");
        holder.setText(R.id.item_weight,item.getCount()+item.getSpec());
        holder.setTextColor(R.id.item_title,R.color.colorThemeLight);
        holder.setTextColor(R.id.item_weight,R.color.colorTextBlack33);
        holder.setTextColor(R.id.item_time,R.color.colorTextBlack33);
        holder.setText(R.id.item_time,item.getReviewRemark()+"");
        holder.setText(R.id.item_status,item.getOperationTime()+"");
        holder.getView(R.id.item_remark).setVisibility(View.GONE);
    }
}