package com.zz.cold.business.daily.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.DailyBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class DailyAdapter extends BaseQuickAdapter<DailyBean, BaseViewHolder> {
    public DailyAdapter(@LayoutRes int layoutResId, @Nullable List<DailyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final DailyBean item) {
        holder.setText(R.id.item_title,item.getReportTime()+"");
        holder.setText(R.id.item_IsRegularCheck,"定期检查:"+(item.getIsRegularCheck()==1?"是":"否"));
        holder.setText(R.id.item_IsProhibitedFood,"违禁食品:"+(item.getIsProhibitedFood()==1?"是":"否"));
    }
}