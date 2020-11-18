package com.zz.cold.business.trace.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.bean.PendingBean;
import com.zz.cold.bean.TraceBean;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class PendingAdapter extends BaseQuickAdapter<PendingBean, BaseViewHolder> {
    public PendingAdapter(@LayoutRes int layoutResId, @Nullable List<PendingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final PendingBean item) {

    }
}