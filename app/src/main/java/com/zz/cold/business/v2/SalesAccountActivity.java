package com.zz.cold.business.v2;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.zz.cold.bean.StorageAccount;
import com.zz.cold.business.trace.PurchaseActivity;
import com.zz.cold.business.v2.adapter.SalesAdapter;
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

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 * 销售台账
 */
public class SalesAccountActivity extends MyBaseActivity implements OnRefreshListener, OnLoadMoreListener {

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

    private SalesAdapter adapter;
    List<StorageAccount> mlist = new ArrayList<>();
    private int pagenum = 1;
    private int pagesize = 20;
    private String searchStr = "";

    int imported = -1;
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

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    protected int getContentView() {
        return R.layout.activity_sell_list;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SalesAdapter(R.layout.item_sales, mlist, 1);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.setClass(SalesAccountActivity.this, AccountDetailActivity.class);
                intent.putExtra("id", mlist.get(position).getId());
                intent.putExtra("page", "sales");
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
        toolbar_title.setText("销售台账");
    }

    @OnClick({R.id.toolbar_subtitle, R.id.bt_jin, R.id.et_imported, R.id.et_beginTime, R.id.et_endTime, R.id.bt_ok, R.id.bt_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                drawer.openDrawer(GravityCompat.END);
                break;
            case R.id.et_imported:
                showSelectPopWindow();
                break;
            case R.id.et_beginTime:
                DatePickDialog dialog = new DatePickDialog(SalesAccountActivity.this);
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
                DatePickDialog dialog2 = new DatePickDialog(SalesAccountActivity.this);
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
                pagenum = 1;
                getDate();
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

    public void showResult(List<StorageAccount> data) {
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
        if (!TextUtils.isEmpty(beginTime)) {
            map.put("beginTime", beginTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            map.put("endTime", endTime);
        }
        String goodsName = et_goodsName.getText().toString();
        if (!TextUtils.isEmpty(goodsName)) {
            map.put("goodsName", goodsName);
        }
        String batchNumber = et_batchNumber.getText().toString();
        if (!TextUtils.isEmpty(batchNumber)) {
            map.put("batchNumber", batchNumber);
        }
        String entryPort = et_entryPort.getText().toString();
        if (!TextUtils.isEmpty(entryPort)) {
            map.put("entryPort", entryPort);
        }
        if (imported > -1) {
            map.put("imported", imported);
        }
        RxNetUtils.request(getApi(ApiService.class).salesAccount(map), new RequestObserver<JsonT<List<StorageAccount>>>() {
            @Override
            protected void onSuccess(JsonT<List<StorageAccount>> jsonT) {
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<StorageAccount>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        pagenum = 1;
        getDate();
    }

    SelectPopupWindows selectPopupWindows;

    void showSelectPopWindow() {
        UIAdjuster.closeKeyBoard(this);

        String[] array = {"国产", "进口"};
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                et_imported.setText(msg);
                if (index == 0) {
                    imported = 1;
                } else {
                    imported = 2;
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }
}