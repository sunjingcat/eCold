package com.zz.cold.business.qualification.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
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
        holder.setText(R.id.item_title,item.getOperatorName()+"");
        holder.setText(R.id.item_socialCreditCode,"社会信用代码:"+item.getSocialCreditCode()+"");
        holder.setText(R.id.item_contact,"负责人:"+item.getContact()+"");

    }
}