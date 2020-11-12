package com.zz.cold.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.gyf.immersionbar.ImmersionBar;
import com.zz.cold.R;
import com.zz.cold.business.login.LoginActivity;
import com.zz.cold.net.OutDateEvent;
import com.zz.cold.App;
import com.zz.cold.utils.woolglass.FragmentClass;
import com.zz.lib.commonlib.CommonApplication;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.core.ui.BaseActivity;
import com.zz.lib.core.utils.AnimeUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by admin on 2018/5/14.
 */

public abstract class MyBaseActivity<P extends com.zz.lib.core.ui.mvp.BasePresenter> extends BaseActivity<P> {
    private Fragment lastFragment;
    private String firstFragment;
    private Bundle mTransAnimation;
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        CommonApplication.activity = this;
        App.context=this;
//        App.context.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    String s = new String();
//                }
//            }
//        });

    }

    //获取Activity布局
    protected abstract int getContentView();
    public void clearTransAnimation() {
        mTransAnimation = null;
    }

    public void setTransAnimation(Bundle transAnimation) {
        mTransAnimation = transAnimation;
    }
  public ImmersionBar immersionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mTransAnimation = AnimeUtils.sceneTransAnime(this);
        initView();
        initToolBar();
        immersionBar = ImmersionBar.with(this);
        immersionBar.statusBarDarkFont(false)
                .navigationBarColor(R.color.black)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (immersionBar != null)
//            immersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOutDate(OutDateEvent event) {
        onOutDatePreExcuted();
        CacheUtility.saveToken("");
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("drawback", true);
        startActivity(intent);
//        IToast.show(this,"跳登录");
    }

    protected void onOutDatePreExcuted() {

    }

    //初始化视图
    protected abstract void initView();

    //初始化标题栏
    protected abstract void initToolBar();
    @Override
    public void finish() {
        super.finish();
        AnimeUtils.sceneTransAnimeExit(this);
    }


    @Override
    public void startActivity(Intent intent) {
        if (Build.VERSION.SDK_INT < 16 || mTransAnimation == null) {
            super.startActivity(intent);
        } else {
            ActivityCompat.startActivity(this, intent, mTransAnimation);
        }
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (Build.VERSION.SDK_INT < 16 || mTransAnimation == null) {
            super.startActivityForResult(intent, requestCode);
        } else {
            ActivityCompat.startActivityForResult(this, intent, requestCode, mTransAnimation);
        }
    }


    protected Fragment setContentView(Class<? extends Fragment> fragmentClass, int FrameLayoutId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String fragmentName = fragmentClass.getSimpleName();
        Fragment fragment = (Fragment) fragmentManager.findFragmentByTag(fragmentName);

        try {
            if (fragment == null) {
                fragment = fragmentClass.newInstance();
                transaction.add(FrameLayoutId, fragment, fragmentName);
                firstFragment = FragmentClass.newInstance();
            }
            if (lastFragment != null)
                transaction.hide(lastFragment);
            transaction.show(fragment);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        lastFragment = fragment;
        transaction.commit();
        return fragment;
    }

    @Override
    public void onError() {

    }

}
