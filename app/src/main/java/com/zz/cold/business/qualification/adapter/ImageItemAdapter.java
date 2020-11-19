package com.zz.cold.business.qualification.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.utils.GlideUtils;
import com.zz.cold.utils.ImagePreview;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class ImageItemAdapter extends BaseQuickAdapter<ImageBack, BaseViewHolder> {
    public ImageItemAdapter(@LayoutRes int layoutResId, @Nullable List<ImageBack> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final ImageBack item) {
        GlideUtils.loadImage(getContext(), item.getDownloadUrl(), (ImageView) holder.getView(R.id.image));

        holder.getView(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = item.getDownloadUrl();
//                Bitmap s1 = GlideUtils.base64ToBitmap(str);
//                String s = BASE64.saveBitmap(getContext(),s1);
                ImagePreview.preview(getContext(), str);
            }
        });
    }
}
