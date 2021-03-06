package com.zz.cold.business.mine;

import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.utils.UpdateManager;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于
 */
public class AboutActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_icon)
    ImageView tvIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.bt_update)
    Button btUpdate;

    @Override
    protected int getContentView() {
        return R.layout.activity_about;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvVersion.setText(getVersioName());
//        tvInfo.setText();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @OnClick(R.id.bt_update)
    public void onViewClicked() {
        new UpdateManager(this).checkUpdate();
    }
    private String getVersioName() {
        try {
          String  versionName = this.getPackageManager().
                    getPackageInfo(this.getPackageName(), 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return "";
    }

    //获取app版本
    private int getVersionCode() {
        try {
            return getPackageManager().
                    getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 0;
    }
}
