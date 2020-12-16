package com.zz.cold.business.trace;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.codbking.widget.utils.UIAdjuster;
import com.zz.cold.MainActivity;
import com.zz.cold.R;

import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.business.qualification.QualificationActivity;
import com.zz.cold.business.trace.mvp.Contract;
import com.zz.cold.business.trace.mvp.presenter.DeliverPresenter;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 出库
 */
public class DeliverActivity extends MyBaseActivity<Contract.IsetDeliverPresenter> implements Contract.IGetDeliverView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.text_Name)
    TextView text_Name;
    @BindView(R.id.text_isTransfer)
    TextView text_isTransfer;
    @BindView(R.id.ll_isTransfer)
    LinearLayout ll_isTransfer;
    @BindView(R.id.text_coldstorageId)
    TextView text_coldstorageId;
    @BindView(R.id.ll_coldstorageId)
    LinearLayout ll_coldstorageId;
    @BindView(R.id.text_operationCount)
    EditText text_operationCount;
    @BindView(R.id.text_operationRemark)
    EditText text_operationRemark;
    String id;
    String coldstorageId="";
    int operationType;
    int isThird;
    int isTransfer=0;

    @Override
    protected int getContentView() {
        return R.layout.activity_deliver;

    }

    @Override
    public DeliverPresenter initPresenter() {
        return new DeliverPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        operationType = getIntent().getIntExtra("operationType", 1);
        isThird = getIntent().getIntExtra("isThird", 0);
        if (operationType == 2) {
            toolbar_title.setText("出货");

        } else {
            toolbar_title.setText("进货");
            if (isThird == 0) {
                ll_isTransfer.setVisibility(View.VISIBLE);
            } else {
                ll_isTransfer.setVisibility(View.GONE);
            }
        }

        text_Name.setText(name + "");
    }

    @OnClick({R.id.toolbar_subtitle, R.id.text_coldstorageId, R.id.text_isTransfer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData();
                break;
            case R.id.text_coldstorageId:
                startActivityForResult(new Intent(DeliverActivity.this, QualificationActivity.class).putExtra("page", "import"), 1001);
                break;
            case R.id.text_isTransfer:
                showSelectPopWindow();
                break;
        }
    }

    void postData() {
        Map<String,Object>  params = new HashMap<>();
        params.put("operationCount",getText(text_operationCount));
        params.put("operationRemark",getText(text_operationRemark));
        params.put("isTransfer",isTransfer);
        params.put("coldstorageId",coldstorageId);
        mPresenter.submitData(operationType,id,params);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showResult() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showPostImage(String localPath, ImageBack imageBack) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1001:
                    coldstorageId = data.getStringExtra("id");
                    text_coldstorageId.setText(data.getStringExtra("name"));
                    break;
            }
        }

    }
    SelectPopupWindows selectPopupWindows;

    void showSelectPopWindow() {
        UIAdjuster.closeKeyBoard(this);

        String[] array = {"是", "否"};
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                text_isTransfer.setText(msg);
                if (index == 0) {
                    isTransfer = 1;
                    ll_coldstorageId.setVisibility(View.VISIBLE);
                } else {
                    isTransfer = 0;
                    ll_coldstorageId.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }
}