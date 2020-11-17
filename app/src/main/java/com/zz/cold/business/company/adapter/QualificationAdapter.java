package com.zz.cold.business.company.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.bean.QualificationBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class QualificationAdapter extends BaseQuickAdapter<QualificationBean, BaseViewHolder> {
    public QualificationAdapter(@LayoutRes int layoutResId, @Nullable List<QualificationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final QualificationBean item) {

    }
}