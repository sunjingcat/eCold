package com.zz.cold.business.trace.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.TraceBean;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class TraceAdapter extends BaseQuickAdapter<TraceBean, BaseViewHolder> {
    public TraceAdapter(@LayoutRes int layoutResId, @Nullable List<TraceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final TraceBean item) {
        holder.setText(R.id.item_title,item.getGoodsName()+"");
        holder.setText(R.id.item_socialCreditCode,"批号:"+item.getProductionDate()+"");
        holder.setText(R.id.item_contact,"库存量:"+item.getCount()+"");

    }
}