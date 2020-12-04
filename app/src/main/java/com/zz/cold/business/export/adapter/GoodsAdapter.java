package com.zz.cold.business.export.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.TraceBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class GoodsAdapter extends BaseQuickAdapter<TraceBean, BaseViewHolder> {
    int type;
    public GoodsAdapter(@LayoutRes int layoutResId, @Nullable List<TraceBean> data,int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder holder, final TraceBean item) {
        holder.setText(R.id.item_title,item.getGoodsName()+"");
        holder.setText(R.id.item_operatorName,item.getOperatorName()+"");
        holder.setText(R.id.item_count,type==1?("+"+item.getCount()+item.getSpec()):("-"+item.getCount()+item.getSpec()));
        holder.setTextColor(R.id.item_count,type==1? Color.RED :Color.GREEN);
        holder.setText(R.id.item_time,item.getPurchaseTime()+"");
    }
}