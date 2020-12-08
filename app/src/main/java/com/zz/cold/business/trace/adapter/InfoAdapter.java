package com.zz.cold.business.trace.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.InfoBean;
import com.zz.cold.business.qualification.adapter.ImageItemAdapter;


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
        RecyclerView rvImages = holder.getView(R.id.rv_image);
        if (item.getImages()!=null&&item.getImages().size()>0) {
            rvImages.setVisibility(View.VISIBLE);
            rvImages.setLayoutManager(new GridLayoutManager(getContext(), 3));
            ImageItemAdapter adapter = new ImageItemAdapter(R.layout.item_image, item.getImages());
            rvImages.setAdapter(adapter);
        }else {
            rvImages.setVisibility(View.GONE);
        }
    }
}