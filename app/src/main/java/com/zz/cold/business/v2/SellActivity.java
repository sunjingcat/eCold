package com.zz.cold.business.v2;

import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.codbking.widget.utils.UIAdjuster;
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
    TextView text_buyerRemark;
    @BindView(R.id.text_operationCount)
    EditText text_operationCount;
    String id;
    String buyerRemark;

    @Override
    protected int getContentView() {
        return R.layout.activity_deliver;

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

    @OnClick({R.id.toolbar_subtitle,R.id.text_buyerRemark})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData();
                break;
            case R.id.text_buyerRemark:
                showSelectPopWindow();
                break;
        }
    }

    void postData() {
        SellPost params = new SellPost();
        params.setId(id);
        params.setOperationCount(getText(text_operationCount));
        params.setBuyerName(getText(text_buyerName));
        params.setBuyerAddress(getText(text_buyerAddress));
        params.setBuyerContact(getText(text_buyerContact));
        submitData(params);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    public void showResult() {
        setResult(RESULT_OK);
        finish();
    }

    SelectPopupWindows selectPopupWindows;

    void showSelectPopWindow() {
        UIAdjuster.closeKeyBoard(this);

        String[] array = {"自己吃", "饭店使用"};
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                text_buyerRemark.setText(msg);
                if (index == 0) {
                    buyerRemark = "A";
                } else {
                    buyerRemark = "B";
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }
    void submitData(SellPost params){
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