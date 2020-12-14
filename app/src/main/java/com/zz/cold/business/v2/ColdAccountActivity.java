package com.zz.cold.business.v2;


import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.google.android.material.tabs.TabLayout;
import com.zz.cold.R;
import com.zz.cold.base.BasePresenter;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.business.qualification.adapter.FmPagerAdapter;
import com.zz.cold.business.trace.PurchaseActivity;
import com.zz.cold.utils.TimeUtils;
import com.zz.cold.widget.TabPopWindow;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 冷库台账
 */
public class ColdAccountActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    public FmPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"我的冷库", "第三方冷库"};
    int type = 1;

    int imported = -1;
    int operationType = -1;
    String beginTime = "";
    String endTime = "";
    @BindView(R.id.et_goodsName)
    EditText et_goodsName;
    @BindView(R.id.et_batchNumber)
    EditText et_batchNumber;
    @BindView(R.id.et_entryPort)
    EditText et_entryPort;

    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.et_operationType)
    TextView et_operationType;
    @BindView(R.id.et_beginTime)
    TextView et_beginTime;
    @BindView(R.id.et_endTime)
    TextView et_endTime;
    @BindView(R.id.et_imported)
    TextView et_imported;

    @BindView(R.id.bt_ok)
    Button bt_ok;
    @BindView(R.id.bt_cancel)
    Button bt_cancel;

    @Override
    protected int getContentView() {
        return R.layout.activity_account;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    ColdAccountFragment coldAccountFragment1;
    ColdAccountFragment coldAccountFragment2;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        coldAccountFragment1 = new ColdAccountFragment(1);
        coldAccountFragment2 = new ColdAccountFragment(1);
        for (int i = 0; i < titles.length; i++) {
            tablayout.addTab(tablayout.newTab());
        }
        fragments.add(coldAccountFragment1);
        fragments.add(coldAccountFragment2);
        tablayout.setupWithViewPager(viewpager, false);
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);

        for (int i = 0; i < titles.length; i++) {
            tablayout.getTabAt(i).setText(titles[i]);
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition() + 1;
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.toolbar_subtitle, R.id.et_imported, R.id.et_operationType, R.id.et_beginTime, R.id.et_endTime, R.id.bt_ok, R.id.bt_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                drawer.openDrawer(GravityCompat.END);
                break;
            case R.id.et_imported:
                showSelectPopWindow(1);
                break;
            case R.id.et_operationType:
                showSelectPopWindow(2);
                break;
            case R.id.et_beginTime:
                DatePickDialog dialog = new DatePickDialog(ColdAccountActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog.setYearLimt(20);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {

                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        beginTime = time;
                        et_beginTime.setText(time);
                    }
                });
                dialog.show();
                break;
            case R.id.et_endTime:
                DatePickDialog dialog2 = new DatePickDialog(ColdAccountActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog2.setYearLimt(20);
                //设置标题
                dialog2.setTitle("选择时间");
                //设置类型
                dialog2.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog2.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog2.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog2.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {

                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        endTime = time;
                        et_endTime.setText(time);
                    }
                });
                dialog2.show();
                break;
            case R.id.bt_ok:
                UIAdjuster.closeKeyBoard(this);
                drawer.closeDrawers();
                if (type == 1) {
                    coldAccountFragment1.setSearchStr(imported, operationType, getText(et_beginTime), getText(et_endTime), getText(et_goodsName), getText(et_batchNumber), getText(et_entryPort));
                }else {
                    coldAccountFragment2.setSearchStr(imported, operationType, getText(et_beginTime), getText(et_endTime), getText(et_goodsName), getText(et_batchNumber), getText(et_entryPort));

                }
                et_goodsName.setText("");
                et_batchNumber.setText("");
                et_entryPort.setText("");
                et_beginTime.setText("");
                beginTime = "";
                et_endTime.setText("");
                endTime = "";
                imported = -1;
                et_imported.setText("");

                break;
            case R.id.bt_cancel:
                UIAdjuster.closeKeyBoard(this);
                drawer.closeDrawers();
                break;

        }
    }

    SelectPopupWindows selectPopupWindows;


    void showSelectPopWindow(int type) {
        UIAdjuster.closeKeyBoard(this);
        String[] array = {"国产", "进口"};
        String[] array2 = {"进货", "出货"};
        selectPopupWindows = new SelectPopupWindows(this, type == 1 ? array : array2);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                if (type == 1) {
                    et_imported.setText(msg);
                    if (index == 0) {
                        imported = 1;
                    } else {
                        imported = 2;
                    }
                } else {
                    et_operationType.setText(msg);
                    if (index == 0) {
                        operationType = 1;
                    } else {
                        operationType = 2;
                    }
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }
}
