package com.zz.cold;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zz.cold.bean.MainShowData;
import com.zz.cold.business.daily.DailyActivity;
import com.zz.cold.business.trace.PendingGoodsActivity;
import com.zz.cold.business.v2.ExportListActivity;
import com.zz.cold.business.trace.TraceActivity;
import com.zz.cold.business.v2.SellListActivity;
import com.zz.lib.core.http.utils.ToastUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.business.mine.MineActivity;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.cold.utils.UpdateManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.cold.net.RxNetUtils.getApi;

public class MainActivity extends MyBaseActivity {

    private long mExitTime = 0;
    @BindView(R.id.tv_employCount)
    TextView tv_employCount;
    @BindView(R.id.tv_warehouseCount)
    TextView tv_warehouseCount;
    @BindView(R.id.tv_goodsCount)
    TextView tv_goodsCount;
    @BindView(R.id.tv_goodsNoNucleicCount)
    TextView tv_goodsNoNucleicCount;
    @BindView(R.id.tv_noReportedCount)
    TextView tv_noReportedCount;
    @BindView(R.id.tv_goodsImportCount)
    TextView tv_goodsImportCount;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        new UpdateManager(this).checkUpdate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMainData();
    }

    @Override
    protected void initToolBar() {
    }

    @OnClick({R.id.main_group_1, R.id.main_group_2, R.id.main_group_3, R.id.main_group_4, R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.main_group_1:

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ExportListActivity.class);
                intent.putExtra("page","import");
                startActivity(intent);
                break;
            case R.id.main_group_2:
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, ExportListActivity.class);
                intent1.putExtra("page","export");
                startActivity(intent1);
                break;
            case R.id.main_group_3:
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this, SellListActivity.class);
                startActivity(intent2);
                break;
            case R.id.main_group_4:
                Intent intent3 = new Intent();
                intent3.setClass(MainActivity.this, PendingGoodsActivity.class);
                startActivity(intent3);
                break;
            case R.id.toolbar_subtitle:
                startActivity(new Intent(MainActivity.this, MineActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                ToastUtils.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void getMainData() {
        RxNetUtils.request(getApi(ApiService.class).getMainData(), new RequestObserver<JsonT<MainShowData>>(this) {
            @Override
            protected void onSuccess(JsonT<MainShowData> jsonT) {

                if (jsonT.isSuccess()) {
                    MainShowData data = jsonT.getData();
                    tv_employCount.setText(data.getEmployCount() + "");
                    tv_goodsCount.setText(data.getGoodsCount() + "");
                    tv_warehouseCount.setText(data.getWarehouseCount() + "");
                    tv_goodsImportCount.setText(data.getGoodsImportCount() + "");
                    tv_goodsNoNucleicCount.setText(data.getGoodsNoNucleicCount() + "");
                    tv_noReportedCount.setText(data.getNoReportedCount() + "");
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<MainShowData> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
            }
        }, null);
    }


}
