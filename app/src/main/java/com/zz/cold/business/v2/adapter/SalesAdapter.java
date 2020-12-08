package com.zz.cold.business.v2.adapter;

import android.graphics.Color;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.StorageAccount;
import com.zz.cold.bean.TraceBean;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class SalesAdapter extends BaseQuickAdapter<StorageAccount, BaseViewHolder> {
    int type;
    public SalesAdapter(@LayoutRes int layoutResId, @Nullable List<StorageAccount> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder holder, final StorageAccount item) {
        holder.setText(R.id.item_title,item.getGoodsName()+"");
        holder.setText(R.id.item_operatorName,"批号："+item.getBatchNumber()+"");
        holder.setText(R.id.item_count,type==1?("+"+item.getCount()+item.getSpec()):("-"+item.getCount()+item.getSpec()));
        holder.setTextColor(R.id.item_count,type==1? Color.RED :Color.parseColor("FF155917"));
        holder.setText(R.id.item_time,item.getOperationTime()+"");
    }
}