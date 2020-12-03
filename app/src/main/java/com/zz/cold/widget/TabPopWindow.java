package com.zz.cold.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.contrarywind.view.WheelView;
import com.zz.cold.R;
import com.zz.cold.bean.GroupCountBean;

import java.util.ArrayList;


/**
 * Created by user on 2017-09-25.
 */

public class TabPopWindow extends PopupWindow {

    private static final String TAG = "FinishProjectPopupWindows";
    private ArrayList<GroupCountBean> PLANETS ;
    private View mView;
    private RecyclerView rv;
    private OnItemClickListener listener;

    public TabPopWindow(final Activity context, ArrayList<GroupCountBean> strings,String select) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.tab_popwindow, null);
        PLANETS = strings;
        for (GroupCountBean groupCountBean:strings){
            if (groupCountBean.getColdstorageId().equals(select)){
                groupCountBean.setSelect(true);
            }else {
                groupCountBean.setSelect(false);
            }
        }
        rv = (RecyclerView) mView.findViewById(R.id.rv_tab);
        rv.setLayoutManager(new LinearLayoutManager(context));
        TabAdapter tabAdapter = new TabAdapter(R.layout.item_tab,PLANETS);
        rv.setAdapter(tabAdapter);
        tabAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                listener.onSelected(position);
                backgroundAlpha(context, 1f);
                dismiss();
            }
        });
        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.Animation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x70000000);
//        设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        backgroundAlpha(context, 0.5f);
        this.setOutsideTouchable(true);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onSelected(int index);
    }

}
