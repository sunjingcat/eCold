package com.zz.cold.business.trace;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.business.qualification.adapter.ImageDeleteItemAdapter;
import com.zz.cold.business.trace.mvp.Contract;
import com.zz.cold.business.trace.mvp.presenter.PurchaseAddPresenter;
import com.zz.cold.utils.PostUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 入库
 */
public class PurchaseActivity extends MyBaseActivity<Contract.IsetPurchaseAddPresenter> implements Contract.IGetPurchaseAddView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<ImageBack> images = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;

    GoodsBean goodsBean;

    String id;

    @Override
    protected int getContentView() {
        return R.layout.activity_qualification_add;

    }

    @Override
    public PurchaseAddPresenter initPresenter() {
        return new PurchaseAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rvImages.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new ImageDeleteItemAdapter(this, images);
        rvImages.setAdapter(adapter);
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            mPresenter.getData(id);
            mPresenter.getImage("coldstorage",id);
        }
        adapter.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    if (!TextUtils.isEmpty(images.get(i).getPath())) {
                        localPath.add(images.get(i).getPath());
                    } else {
                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - images.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(PurchaseActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
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
//        params.put("operatorName",getText(etOperatorName));

        params.put("enclosureIds", PostUtils.getImageIds(images));
        if (!TextUtils.isEmpty(id)){
            params.put("id",id);
        }

        mPresenter.submitData(params);
    }

    @Override
    public void showPurchaseInfo(GoodsBean data) {
        goodsBean = data;

    }


    @Override
    public void showResult() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showPostImage(String localPath,ImageBack imageBack) {
        if (imageBack ==null) return;
        imageBack.setPath(localPath);
        images.add(imageBack);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showImage(List<ImageBack> list) {
        if (list ==null) return;
        images.clear();
        images.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {

                case 1101:
                    ArrayList<String> selectImages = data.getStringArrayListExtra(
                            ImageSelectorUtils.SELECT_RESULT);
                    for (String path : selectImages) {
                        Luban.with(this)
                                .load(path)
                                .ignoreBy(100)
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {
                                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                    }

                                    @Override
                                    public void onSuccess(File file) {

                                        mPresenter.postImage(file.getPath(), getImageBody(file.getPath()));
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        // TODO 当压缩过程出现问题时调用
                                    }
                                }).launch();

                    }
                    break;
            }
        }
    }


}