package com.zz.cold.business.trace.adapter;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.PendingCompanyBean;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class PendingAdapter extends BaseQuickAdapter<PendingCompanyBean, BaseViewHolder> {
    public PendingAdapter(@LayoutRes int layoutResId, @Nullable List<PendingCompanyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final PendingCompanyBean item) {
        holder.setText(R.id.item_title,item.getOperatorName()+"");
        holder.setText(R.id.item_reviewCount,item.getReviewCount()>0?(item.getReviewCount()+"条待审核"):"");
        holder.setText(R.id.item_Contact,item.getContact()+"");
        holder.setText(R.id.item_ContactInformation,item.getContactInformation()+"");

    }
}