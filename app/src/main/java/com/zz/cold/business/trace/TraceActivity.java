package com.zz.cold.business.trace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.zz.cold.bean.TraceBean;
import com.zz.cold.business.trace.adapter.TraceAdapter;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.ClearEditText;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import androidx.annotation.NonNull;
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

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 *追溯
 */
public class TraceActivity extends MyBaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.et_search)
    ClearEditText et_search;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private TraceAdapter adapter;
    List<TraceBean> mlist = new ArrayList<>();
    private int pagenum = 1;
    private int pagesize = 20;
    private String searchStr = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_trace;

    }
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TraceAdapter(R.layout.item_qualification, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.setClass(TraceActivity.this, GoodsActivity.class);
                intent.putExtra("id",mlist.get(position).getId());
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

    @Override
    protected void onResume() {
        super.onResume();
        getDate();
    }

    @OnClick({R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:

                Intent intent = new Intent();
                intent.setClass(TraceActivity.this, PendingCompanyActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
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
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pagenum++;
        getDate();
        refreshLayout.finishLoadMore();
    }
    void getDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pagenum);
        map.put("pageSize", pagesize);
        searchStr = et_search.getText().toString();
        if (!TextUtils.isEmpty(searchStr)) {
            map.put("searchValue", searchStr);
        }
        RxNetUtils.request(getApi(ApiService.class).getTraceList(map), new RequestObserver<JsonT<List<TraceBean>>>() {
            @Override
            protected void onSuccess(JsonT<List<TraceBean>> jsonT) {
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<TraceBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }


}