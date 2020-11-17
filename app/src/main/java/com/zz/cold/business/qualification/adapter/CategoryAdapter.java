package com.zz.cold.business.qualification.adapter;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.CategoryBean;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CategoryAdapter extends BaseSectionQuickAdapter<CategoryBean, BaseViewHolder> {
   private OnClickListener onBnpjClickListener;

    public CategoryAdapter(int sectionHeadResId, int layoutResId, @Nullable List<CategoryBean> data) {
        super(sectionHeadResId, layoutResId, data);
    }

    public void setOnBnpjClickListener(OnClickListener onBnpjClickListener) {
        this.onBnpjClickListener = onBnpjClickListener;
    }

    @Override
    protected void convertHeader(@NotNull BaseViewHolder baseViewHolder, @NotNull CategoryBean businessProjectBean) {
        baseViewHolder.setText(R.id.title,businessProjectBean.getTitle());
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBnpjClickListener.onHeaderClick(v,baseViewHolder.getAdapterPosition());
            }
        });
        if (businessProjectBean.isSelect()){
            baseViewHolder.setTextColor(R.id.title, Color.parseColor("#0851A2"));
        }else {
            baseViewHolder.setTextColor(R.id.title, Color.parseColor("#444444"));
        }
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CategoryBean businessProjectBean) {
        baseViewHolder.setText(R.id.title,businessProjectBean.getTitle());
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBnpjClickListener.onContentClick(v,baseViewHolder.getAdapterPosition());
            }
        });
        if (businessProjectBean.isSelect()){
            baseViewHolder.setTextColor(R.id.title, Color.parseColor("#0851A2"));
            baseViewHolder.setBackgroundResource(R.id.title, R.drawable.border_radius_3_dae);
        }else {
            baseViewHolder.setTextColor(R.id.title, Color.parseColor("#4A4A4A"));
            baseViewHolder.setBackgroundResource(R.id.title, R.drawable.border_radius_3_gray);
        }
    }

    public interface OnClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        void onHeaderClick(View v,int p);
        void onContentClick(View v,int p);
    }
}
