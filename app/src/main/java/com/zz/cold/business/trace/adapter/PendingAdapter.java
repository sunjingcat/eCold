package com.zz.cold.business.trace.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
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

    }
}