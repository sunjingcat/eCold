package com.zz.cold.business.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.igexin.sdk.PushManager;
import com.troila.customealert.CustomDialog;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.UserBasicBean;
import com.zz.cold.business.mine.mvp.Contract;
import com.zz.cold.business.mine.mvp.presenter.MineInfoPresenter;
import com.zz.cold.business.qualification.AddQualificationActivity;
import com.zz.cold.business.qualification.QualificationInfoActivity;
import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.OutDateEvent;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.cold.utils.GlideUtils;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.utils.LoadingUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.cold.net.RxNetUtils.getApi;

/**
 * 我的
 */
public class MineActivity extends MyBaseActivity<Contract.IsetMineInfoPresenter> implements Contract.IMineInfoView {


    @BindView(R.id.my_about)
    LinearLayout myAbout;
    UserBasicBean userInfo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_information)
    RelativeLayout myInformation;
    @BindView(R.id.my_password)
    LinearLayout myPassword;
    @BindView(R.id.my_code)
    LinearLayout myCode;
    @BindView(R.id.my_company)
    LinearLayout my_company;
    QualificationBean qualificationBean;
    @Override
    protected int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    public MineInfoPresenter initPresenter() {
        return new MineInfoPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mPresenter.getMineInfo();

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @Override
    public void showUserInfo(UserBasicBean userInfo) {
        this.userInfo = userInfo;
        myName.setText(userInfo.getOperatorName() + "");
//        notContent.setText(userInfo.getPhonenumber() + "");

//        if (!TextUtils.isEmpty(userInfo.getAvatar())) {
//
//            GlideUtils.loadCircleImage(this, userInfo.getAvatar(), myHead);
//        }
        mPresenter.getCompany(userInfo.getId()+"");
    }

    @Override
    public void showCompany(QualificationBean qualificationBean) {
        this.qualificationBean = qualificationBean;
    }

    @Override
    public void showIntent() {
        CacheUtility.saveToken("");
        CacheUtility.saveRole(0);
        CacheUtility.clear();
//        startActivity(new Intent(MineActivity.this, LoginActivity.class));
        EventBus.getDefault().post(new OutDateEvent());
        PushManager.getInstance().turnOffPush(this);
        finish();
    }

    private CustomDialog customDialog;

    @OnClick({R.id.my_password, R.id.my_code, R.id.my_about, R.id.my_logout, R.id.my_log, R.id.my_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_password:
                if (userInfo == null || TextUtils.isEmpty(userInfo.getLoginName())) {
                    return;
                }
                startActivity(new Intent(MineActivity.this, PasswordActivity.class).putExtra("userName", userInfo.getLoginName()));

                break;
            case R.id.my_code:
                startActivity(new Intent(MineActivity.this, ChangeCodeActivity.class));

                break;
            case R.id.my_company:
                if (this.qualificationBean!=null&&!TextUtils.isEmpty(qualificationBean.getId())) {
                    startActivity(new Intent(MineActivity.this, QualificationInfoActivity.class).putExtra("id",qualificationBean.getId()));
                }else {
                    startActivity(new Intent(MineActivity.this, AddQualificationActivity.class));
                }

                break;
            case R.id.my_about:
                startActivity(new Intent(MineActivity.this, AboutActivity.class));
                break;
            case R.id.my_logout:
                CustomDialog.Builder builder = new CustomDialog.Builder(MineActivity.this)
                        .setTitle("提示")
                        .setMessage("确定退出登录")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.logout();
                            }
                        });
                customDialog = builder.create();
                customDialog.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

}
