package com.zz.cold.business.daily.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.cold.R;
import com.zz.cold.bean.TemperatureBean;
import com.zz.cold.bean.WarehouseBean;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */
//温度
public class TemperatureEditAdapter extends BaseQuickAdapter<WarehouseBean, BaseViewHolder> implements View.OnTouchListener, View.OnFocusChangeListener {

    private int selectedEditTextPosition = -1;
    public TemperatureEditAdapter(@LayoutRes int layoutResId, @Nullable List<WarehouseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final WarehouseBean item) {
        EditText editText = holder.getView(R.id.item_content);
        int position = holder.getAdapterPosition();
        editText.setOnTouchListener(this);
        editText.setOnFocusChangeListener(this);
        editText.setTag(position);

        if (selectedEditTextPosition != -1 && position == selectedEditTextPosition) { // 保证每个时刻只有一个EditText能获取到焦点
            editText.requestFocus();
        } else {
            editText.clearFocus();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            EditText editText = (EditText) v;
            selectedEditTextPosition = (int) editText.getTag();
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (hasFocus) {
            editText.addTextChangedListener(mTextWatcher);
        } else {
            editText.removeTextChangedListener(mTextWatcher);
        }
    }
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (selectedEditTextPosition != -1) {
                Log.w("MyEditAdapter", "onTextPosiotion " + selectedEditTextPosition);
                WarehouseBean itemTest = (WarehouseBean) getItem(selectedEditTextPosition);
                itemTest.setContent(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}