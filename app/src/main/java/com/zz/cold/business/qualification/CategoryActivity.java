package com.zz.cold.business.qualification;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.CategoryBean;
import com.zz.cold.business.qualification.adapter.CategoryAdapter;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;


import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryActivity extends MyBaseActivity {

    CategoryAdapter adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    List<CategoryBean> mlist = new ArrayList<>();
    @BindView(R.id.tv_left)
    TextView tvLeft;

    @Override
    protected int getContentView() {
        return R.layout.activity_category;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(R.layout.item_category_title, R.layout.item_category_content, mlist);
        rv.setAdapter(adapter);
        String type = getIntent().getStringExtra("type");
        ArrayList<CategoryBean> categoryBeans = getIntent().getParcelableArrayListExtra("categoryBean");

        mlist.clear();

        initXS();
        tvLeft.setText("货品品种");

        if (categoryBeans != null && categoryBeans.size() > 0) {
            for (CategoryBean projectBean : categoryBeans) {
                for (CategoryBean projectBean1 : mlist) {
                    if (projectBean.getValue().equals(projectBean1.getValue())) {
                        projectBean1.setSelect(true);
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
        adapter.setOnBnpjClickListener(new CategoryAdapter.OnClickListener() {
            @Override
            public void onHeaderClick(View v, int p) {
//
                mlist.get(p).setSelect(!mlist.get(p).isSelect());
                if (!mlist.get(p).isSelect()) {
                    for (int i = 0; i < mlist.size(); i++) {
                        if (mlist.get(i).getFatherValue() == mlist.get(p).getValue()) {
                            mlist.get(i).setSelect(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onContentClick(View v, int p) {
                boolean isSelect = false;
                mlist.get(p).setSelect(!mlist.get(p).isSelect());
                for (int i = 0; i < mlist.size(); i++) {
                    if (mlist.get(i).getFatherValue() == mlist.get(p).getFatherValue()) {
                        if (mlist.get(i).isSelect()) {
                            isSelect = true;
                        }
                    }
                }
                for (int i = 0; i < mlist.size(); i++) {
                    if (mlist.get(i).getValue() == mlist.get(p).getFatherValue() && isSelect) {

                        mlist.get(i).setSelect(isSelect);
                    }

                }

                adapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    void initXS() {
        mlist.add(new CategoryBean(true, "鸡", "1", ""));
        mlist.add(new CategoryBean(false, "白条鸡", "1.1", "1"));
        mlist.add(new CategoryBean(false, "鸡货", "1.2", "1"));
        mlist.add(new CategoryBean(true, "散装食品销售", "2", "0"));
        mlist.add(new CategoryBean(false, "含冷藏冷冻食品", "2.1", "2"));
        mlist.add(new CategoryBean(false, "不含冷藏冷冻食品", "2.2", "2"));
        mlist.add(new CategoryBean(false, "含熟食", "2.3", "2"));
        mlist.add(new CategoryBean(false, "不含熟食", "2.4", "2"));
        mlist.add(new CategoryBean(true, "特殊食品销售", "3", ""));
        mlist.add(new CategoryBean(false, "保健食品", "3.1", "3"));
        mlist.add(new CategoryBean(false, "特殊医学用途配方食品", "3.2", "3"));
        mlist.add(new CategoryBean(false, "婴幼儿配方乳粉", "3.3", "3"));
        mlist.add(new CategoryBean(false, "其他婴幼儿配方食品", "3.4", "3"));
        mlist.add(new CategoryBean(true, "其他类食品销售", "4", ""));
    }


    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        ArrayList<CategoryBean> selectList = new ArrayList<>();
        selectList.clear();
        for (CategoryBean CategoryBean : mlist) {
            if (CategoryBean.isSelect()) {
                selectList.add(CategoryBean);
            }
        }

        Intent intent = new Intent();
        intent.putExtra("bnpj", selectList);
        setResult(RESULT_OK, intent);
        finish();
    }
}
