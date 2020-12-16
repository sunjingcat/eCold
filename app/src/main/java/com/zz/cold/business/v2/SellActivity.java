package com.zz.cold.business.v2;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.codbking.widget.utils.UIAdjuster;
import com.zz.cold.MainActivity;
import com.zz.cold.R;
import com.zz.cold.base.BasePresenter;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.SellPost;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 * 售卖
 */
public class SellActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.text_Name)
    TextView text_Name;
    @BindView(R.id.text_buyerName)
    EditText text_buyerName;
    @BindView(R.id.text_buyerAddress)
    EditText text_buyerAddress;
    @BindView(R.id.text_buyerContact)
    EditText text_buyerContact;
    @BindView(R.id.text_buyerRemark)
    EditText text_buyerRemark;
    @BindView(R.id.text_operationCount)
    EditText text_operationCount;
    String id;


    @Override
    protected int getContentView() {
        return R.layout.activity_sell;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        text_Name.setText(name + "");
    }

    @OnClick({R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData();
                break;

        }
    }

    void postData() {
        Map<String, Object> params = new HashMap<>();
        params.put("operationCount",getText(text_operationCount));
        params.put("buyerAddress",getText(text_buyerAddress));
        params.put("buyerName",getText(text_buyerName));
        params.put("buyerContact",getText(text_buyerContact));
        params.put("buyerRemark",getText(text_buyerRemark));
        submitData(params);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    public void showResult() {
        startActivity(new Intent(this, MainActivity.class));
    }

    void submitData(Map<String, Object> params ){
        RxNetUtils.request(getApi(ApiService.class).postSell(id,params), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT jsonT) { showResult();
            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
                showToast(stringJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }
}