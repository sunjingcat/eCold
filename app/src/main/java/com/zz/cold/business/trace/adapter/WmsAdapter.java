package com.zz.cold.business.trace.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.WmsBean;

import java.util.List;

/**
 * 出入库
 */

public class WmsAdapter extends BaseQuickAdapter<WmsBean, BaseViewHolder> {
    public WmsAdapter(@LayoutRes int layoutResId, @Nullable List<WmsBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, final WmsBean item) {

    }
}