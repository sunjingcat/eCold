package com.zz.cold.business.trace.adapter;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.WmsBean;

import java.util.List;

/**
 * 未通过
 */

public class HisAdapter extends BaseQuickAdapter<WmsBean, BaseViewHolder> {
    public HisAdapter(@LayoutRes int layoutResId, @Nullable List<WmsBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, final WmsBean item) {
        holder.setText(R.id.item_title,item.getGoodsName()+"");
        holder.setText(R.id.item_weight,item.getOperationType()==1?("+"+item.getCount()+item.getSpec()):("-"+item.getCount()+item.getSpec()));
        holder.setTextColor(R.id.item_weight,item.getOperationType()==1? Color.RED :Color.parseColor("#FF155917"));
        holder.setText(R.id.item_time,item.getReviewTime()+"");
        holder.setText(R.id.item_status,item.getReviewStatusText()+"");
        holder.setText(R.id.item_remark,item.getOperatorName()+"");
        holder.getView(R.id.item_remark).setVisibility(View.VISIBLE);

    }
}