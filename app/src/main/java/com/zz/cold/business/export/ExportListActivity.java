package com.zz.cold.business.export;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.GroupCountBean;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.business.export.adapter.GoodsAdapter;
import com.zz.cold.business.export.mvp.Contract;
import com.zz.cold.business.export.mvp.presenter.ExportListPresenter;
import com.zz.cold.business.qualification.AddQualificationActivity;
import com.zz.cold.business.qualification.QualificationInfoActivity;
import com.zz.cold.business.trace.GoodsActivity;
import com.zz.cold.business.trace.PurchaseActivity;
import com.zz.cold.widget.TabPopWindow;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.ClearEditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 出
 */
public class ExportListActivity extends MyBaseActivity<Contract.IsetExportListPresenter> implements Contract.IGetExportListView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String page;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.et_search)
    ClearEditText et_search;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.text_tab)
    TextView text_tab;
    @BindView(R.id.bt_jin)
    Button bt_jin;

    private GoodsAdapter adapter;
    List<TraceBean> mlist = new ArrayList<>();
    ArrayList<GroupCountBean> tabList = new ArrayList<>();
    private int pagenum = 1;
    private int pagesize = 20;
    private String searchStr = "";
    String tabId;

    @Override
    protected int getContentView() {
        return R.layout.activity_export_list;

    }

    @Override
    public Contract.IsetExportListPresenter initPresenter() {
        return new ExportListPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        page = getIntent().getStringExtra("page");
        mPresenter.getTab(page);
        int type = 1;
        if (page.equals("import")){
            bt_jin.setVisibility(View.VISIBLE);
            type = 1;
        }else {
            bt_jin.setVisibility(View.GONE);
            type = 2;
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoodsAdapter(R.layout.item_goods, mlist,type);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.setClass(ExportListActivity.this, GoodsActivity.class);
                intent.putExtra("id", mlist.get(position).getId());
                intent.putExtra("page", page);
                startActivity(intent);
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                getDate();//搜索方法
                //隐藏软键盘
                @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) context.getSystemService("input_method");
                imm.toggleSoftInput(0, 2);
                return true;
            }
        });

    }

    TabPopWindow tabPopWindow;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.toolbar_subtitle, R.id.text_tab, R.id.bt_jin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                Intent intent = new Intent();
                intent.setClass(ExportListActivity.this, AddQualificationActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_jin:
                startActivity(new Intent(ExportListActivity.this, PurchaseActivity.class));
                break;
            case R.id.text_tab:
                tabPopWindow = new TabPopWindow(this, tabList, tabId);
                tabPopWindow.showAsDropDown(toolbar, 0, 0, Gravity.BOTTOM);
                tabPopWindow.setOnItemClickListener(new TabPopWindow.OnItemClickListener() {
                    @Override
                    public void onSelected(int index) {
                        text_tab.setText(tabList.get(index).getOperatorName() + "");
                        tabId = tabList.get(index).getColdstorageId();
                        pagenum = 1;
                        getDate();
                    }
                });
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pagenum = 1;
        getDate();
        refreshlayout.finishRefresh();
    }

    @Override
    public void showResult(List<TraceBean> data) {
        if (pagenum == 1) {
            mlist.clear();
        }
        mlist.addAll(data);
        adapter.notifyDataSetChanged();
        if (mlist.size() == 0) {
            llNull.setVisibility(View.VISIBLE);
        } else {
            llNull.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pagenum++;
        getDate();
        refreshLayout.finishLoadMore();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getTab(page);
    }

    void getDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pagenum);
        map.put("pageSize", pagesize);
        searchStr = et_search.getText().toString();
        if (!TextUtils.isEmpty(searchStr)) {
            map.put("searchValue", searchStr);
        }
        mPresenter.getData(page,map, tabId);
    }

    @Override
    public void showTabType(List<GroupCountBean> list) {
        if (list == null) return;
        tabList.clear();
        tabList.addAll(list);
        if (list.size() > 0) {
            text_tab.setText(tabList.get(0).getOperatorName() + "");
            tabId = tabList.get(0).getColdstorageId();
            pagenum = 1;
            getDate();
        }
    }
}