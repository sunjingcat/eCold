package com.zz.cold.business.trace.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.PendingCompanyBean;
import com.zz.cold.bean.PendingGoods;
import com.zz.cold.business.qualification.adapter.ImageDeleteItemAdapter;
import com.zz.cold.business.trace.GoodsActivity;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class PendingGoodsAdapter extends BaseQuickAdapter<PendingGoods, BaseViewHolder> {
    public Onclick onclick;
    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }
    public interface  Onclick{
        void onclickOk(View v, int option);
        void onclickNo(View v, int option);
    }
    public PendingGoodsAdapter(@LayoutRes int layoutResId, @Nullable List<PendingGoods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final PendingGoods item) {
        holder.setText(R.id.item_title,item.getGoodsName()+"");
        holder.setText(R.id.item_count,item.getOperationType()==1?"进货"+item.getCount():"出货"+item.getCount());
        holder.setText(R.id.item_time,item.getOperationTime()+"");
        holder.setText(R.id.item_operatorName,item.getColdstorageUserName()+"");
        holder.getView(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onclickOk(v,holder.getAdapterPosition());
            }
        });
        holder.getView(R.id.bt_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onclickNo(v,holder.getAdapterPosition());
            }
        });

        holder.getView(R.id.item_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), GoodsActivity.class)
                        .putExtra("id",item.getGoodsId())
                        .putExtra("page","review")
                );
            }
        });

    }

}