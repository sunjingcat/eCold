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
        holder.setText(R.id.item_weight,item.getOperationType()==1?("+"+item.getCount()+item.getSpec()):("-"+item.getCount()+item.getSpec()));
        holder.setTextColor(R.id.item_weight,item.getOperationType()!=1? Color.RED :Color.parseColor("FF155917"));
        holder.setText(R.id.item_time,item.getOperationTime()+"");
        holder.setText(R.id.item_status,item.getReviewStatusText()+"");
        holder.getView(R.id.item_remark).setVisibility(View.GONE);
    }
}