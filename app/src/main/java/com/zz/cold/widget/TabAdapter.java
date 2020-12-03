package com.zz.cold.widget;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.GroupCountBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class TabAdapter extends BaseQuickAdapter<GroupCountBean , BaseViewHolder> {
    public TabAdapter(@LayoutRes int layoutResId, @Nullable List<GroupCountBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final GroupCountBean item) {
        holder.setText(R.id.item_title,item.getOperatorName()+"");
        holder.setVisible(R.id.item_select,item.isSelect());

    }
}