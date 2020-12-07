package com.zz.cold.business.v2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.bean.PendingCompanyBean;
import com.zz.cold.bean.PendingGoods;
import com.zz.cold.business.trace.PendingGoodsActivity;
import com.zz.cold.business.trace.adapter.PendingAdapter;
import com.zz.cold.business.trace.adapter.PendingGoodsAdapter;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.cold.widget.InputDialog;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.zz.cold.net.RxNetUtils.getApi;


/**
 * 审核列表
 */
@SuppressLint("ValidFragment")
public class ReviewFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private PendingGoodsAdapter adapter;
    List<PendingGoods> mlist = new ArrayList<>();
    private int pagenum = 1;
    private int pagesize = 20;
    Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pending_company, container, false);

        unbinder = ButterKnife.bind(this, view);
        init(view);
        return view;
    }

    @SuppressLint("ValidFragment")
    public ReviewFragment() {
    }

    private void init(View view) {
        toolbar.setVisibility(View.GONE);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PendingGoodsAdapter(R.layout.item_pending_goods, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        adapter.setOnclick(new PendingGoodsAdapter.Onclick() {
            @Override
            public void onclickOk(View v, int option) {
                ask(2, mlist.get(option));
            }

            @Override
            public void onclickNo(View v, int option) {
                askRefuse(3, mlist.get(option));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
        if (inputDialog != null && inputDialog.isShowing()) {
            inputDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pagenum = 1;
        getDate();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pagenum = 1;
        getDate();
        refreshlayout.finishRefresh();
    }

    public void showResult(List<PendingGoods> data) {
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

        RxNetUtils.request(getApi(ApiService.class).getPendingGoodsList(map), new RequestObserver<JsonT<List<PendingGoods>>>() {
            @Override
            protected void onSuccess(JsonT<List<PendingGoods>> jsonT) {
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<PendingGoods>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(getActivity()));
    }
    private CustomDialog customDialog;
    private InputDialog inputDialog;

    void ask(int reviewStatus, PendingGoods data) {
        String msg = data.getOperationType() == 1 ? "进货" : "出货";
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity())
                .setTitle("审核通过确认")
                .setMessage("是否通过该条" + msg + "请求?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reviewGoods(reviewStatus, data, "");
                    }
                });
        customDialog = builder.create();
        customDialog.show();
    }

    void askRefuse(int reviewStatus, PendingGoods data) {
        InputDialog.Builder builder = new InputDialog.Builder(getActivity())
                .setTitle("审核不通过确认")
                .setMessage("")
                .setNegativeButton("取消", new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, String msg) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, String msg) {
                        reviewGoods(reviewStatus, data, msg);
                    }
                });
        inputDialog = builder.create();
        inputDialog.show();
    }

    void reviewGoods(int reviewStatus, PendingGoods data, String reviewRemark) {
        Map<String, Object> map = new HashMap<>();
        map.put("reviewStatus", reviewStatus);
        map.put("reviewRemark", reviewRemark);
        RxNetUtils.request(getApi(ApiService.class).reviewGoods(data.getId(), map), new RequestObserver<JsonT>() {
            @Override
            protected void onSuccess(JsonT jsonT) {
                getDate();
            }
            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(getActivity()));
    }
}