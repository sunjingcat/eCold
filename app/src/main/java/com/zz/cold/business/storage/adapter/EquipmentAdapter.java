package com.zz.cold.business.storage.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.EquipmentBean;
import com.zz.cold.bean.StorageBean;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class EquipmentAdapter extends BaseQuickAdapter<EquipmentBean, BaseViewHolder> {
    public EquipmentAdapter(@LayoutRes int layoutResId, @Nullable List<EquipmentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final EquipmentBean item) {
        holder.setText(R.id.item_title,item.getEquipmentName()+"");

    }
}