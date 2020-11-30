package com.zz.cold.business.daily.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.DailyBean;
import com.zz.cold.bean.TemperatureBean;
import com.zz.cold.utils.GlideUtils;
import com.zz.cold.utils.ImagePreview;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */
//温度
public class TemperatureAdapter extends BaseQuickAdapter<DailyBean.Temperature, BaseViewHolder> {
    public TemperatureAdapter(@LayoutRes int layoutResId, @Nullable List<DailyBean.Temperature> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final DailyBean.Temperature item) {
        //名称，温度
        holder.setText(R.id.item_title,item.getTemperatureName()+"");
        holder.setText(R.id.item_content,item.getTemperatureValue()+"℃");
        GlideUtils.loadImage(getContext(), item.getDownloadUrl(),  holder.getView(R.id.image));
        holder.getView(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = item.getDownloadUrl();
                ImagePreview.preview(getContext(), str);
            }
        });
    }
}