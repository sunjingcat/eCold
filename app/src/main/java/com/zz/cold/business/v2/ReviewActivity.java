package com.zz.cold.business.v2;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zz.cold.R;
import com.zz.cold.base.BasePresenter;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.business.qualification.adapter.FmPagerAdapter;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待审核
 */
public class ReviewActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    public FmPagerAdapter pagerAdapter;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"待审核", "已提交"};
    int rewardsPunishmentsType = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_review;//共用
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        toolbarTitle.setText("审批");
        for (int i = 0; i < titles.length; i++) {
            fragments.add(new ReviewFragment());
            tablayout.addTab(tablayout.newTab());
        }

        tablayout.setupWithViewPager(viewpager, false);
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);

        for (int i = 0; i < titles.length; i++) {
            tablayout.getTabAt(i).setText(titles[i]);
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                rewardsPunishmentsType = tab.getPosition() + 1;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


}
