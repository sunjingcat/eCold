package com.zz.cold.business.v2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.base.MyBaseFragment;
import com.zz.cold.bean.GroupCountBean;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.business.v2.adapter.AccountAdapter;
import com.zz.cold.business.v2.mvp.Contract;
import com.zz.cold.business.v2.mvp.presenter.ColdAccountPresenter;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.cold.utils.TimeUtils;
import com.zz.cold.widget.TabPopWindow;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.ClearEditText;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 * 冷库台账
 */
public class ColdAccountFragment extends MyBaseFragment<Contract.IsetExportListPresenter> implements Contract.IGetExportListView , OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.ll_tab)
    LinearLayout ll_tab;
    @BindView(R.id.text_tab)
    TextView text_tab;
    @BindView(R.id.ll_null)
    LinearLayout llNull;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private AccountAdapter adapter;
    List<TraceBean> mlist = new ArrayList<>();
    private int pagenum = 1;
    private int pagesize = 20;
    private String searchStr = "";
    int type = 1;
    int imported = -1;
    int operationType = -1;
    String beginTime = "";
    String endTime = "";
    Unbinder unbinder;
    String tabId = "";
    String goodsName = "";
    String batchNumber = "";
    String entryPort = "";
    ArrayList<GroupCountBean> tabList = new ArrayList<>();
    @Override
    public ColdAccountPresenter initPresenter() {
        return new ColdAccountPresenter(this);
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AccountAdapter(R.layout.item_account, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AccountDetailActivity.class);
                intent.putExtra("id", mlist.get(position).getId());
                intent.putExtra("page", "cold");

                startActivity(intent);
            }
        });

    }
    TabPopWindow tabPopWindow;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.text_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_tab:
                tabPopWindow = new TabPopWindow(getActivity(), tabList, tabId);
                tabPopWindow.showAsDropDown(ll_tab, 0, 0, Gravity.BOTTOM);
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

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pagenum++;
        getDate();
        refreshLayout.finishLoadMore();
    }

    @SuppressLint("ValidFragment")
    public ColdAccountFragment(int type) {
        this.type = type;
    }

    public void setSearchStr(int imported, int operationType, String beginTime, String endTime, String goodsName, String batchNumber, String entryPort) {
        this.imported = imported;
        this.operationType = operationType;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.goodsName = goodsName;
        this.batchNumber = batchNumber;
        this.entryPort = entryPort;
        pagenum =1;
        getDate();
    }

    void getDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pagenum);
        map.put("pageSize", pagesize);

        if (!TextUtils.isEmpty(beginTime)) {
            map.put("beginTime", beginTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            map.put("endTime", endTime);
        }

        if (!TextUtils.isEmpty(goodsName)) {
            map.put("goodsName", goodsName);
        }

        if (!TextUtils.isEmpty(batchNumber)) {
            map.put("batchNumber", batchNumber);
        }

        if (!TextUtils.isEmpty(entryPort)) {
            map.put("entryPort", entryPort);
        }
        if (imported > -1) {
            map.put("isImported", imported);
        }
        if (operationType > -1) {
            map.put("operationType", operationType);
        }
        mPresenter.getData(type==1?"my":"third",map,tabId);
    }

    @Override
    protected int getCreateView() {
        return R.layout.fragment_account_list;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getTab(type==1?"my":"third");
    }


    @Override
    public void onError() {

    }
}