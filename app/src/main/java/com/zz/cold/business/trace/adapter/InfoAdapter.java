package com.zz.cold.business.trace.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.InfoBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class InfoAdapter extends BaseQuickAdapter<InfoBean, BaseViewHolder> {
    public InfoAdapter(@LayoutRes int layoutResId, @Nullable List<InfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final InfoBean item) {
        holder.setText(R.id.item_title,item.getTitle()+"");
        holder.setText(R.id.item_content,item.getValue()+"");

    }
}