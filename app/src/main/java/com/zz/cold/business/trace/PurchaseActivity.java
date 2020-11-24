package com.zz.cold.business.trace;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.business.daily.AddDailyActivity;
import com.zz.cold.business.qualification.adapter.ImageDeleteItemAdapter;
import com.zz.cold.business.trace.mvp.Contract;
import com.zz.cold.business.trace.mvp.presenter.PurchaseAddPresenter;
import com.zz.cold.utils.PostUtils;
import com.zz.cold.utils.TimeUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
    ArrayList<DictBean> goodsTypes = new ArrayList<>();
    ArrayList<DictBean> isImporteds = new ArrayList<>();
    ArrayList<DictBean> transportModes = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;

    @BindView(R.id.text_goodsName)
    EditText text_goodsName;

    @BindView(R.id.text_goodsType)
    TextView text_goodsType;
    @BindView(R.id.text_productionDate)
    TextView text_productionDate;
    @BindView(R.id.text_spec)
    TextView text_spec;
    @BindView(R.id.text_count)
    EditText text_count;
    @BindView(R.id.text_purchaseTime)
    TextView text_purchaseTime;
    @BindView(R.id.text_supplierName)
    EditText text_supplierName;
    @BindView(R.id.text_supplierAddress)
    EditText text_supplierAddress;
    @BindView(R.id.text_supplierContact)
    EditText text_supplierContact;
    @BindView(R.id.text_transportMode)
    TextView text_transportMode;
    String transportMode;
    @BindView(R.id.text_period)
    TextView text_period;
    @BindView(R.id.text_isImported)
    TextView text_isImported;
    String isImported = "";
    @BindView(R.id.ll_isImported)
    LinearLayout ll_isImported;
    @BindView(R.id.text_isSphsjc)
    TextView text_isSphsjc;
    int isSphsjc = 0;
    @BindView(R.id.text_isRyhsjc)
    TextView text_isRyhsjc;
    int isRyhsjc = 0;
    @BindView(R.id.text_isClhsjc)
    TextView text_isClhsjc;
    int isClhsjc = 0;
    @BindView(R.id.text_isXdzm)
    TextView text_isXdzm;
    int isXdzm = 0;
    @BindView(R.id.text_entryPort)
    EditText text_entryPort;
    @BindView(R.id.text_isFfzzwjc)
    TextView text_isFfzzwjc;
    int isFfzzwjc = 0;
    GoodsBean goodsBean;

    String id;

    @Override
    protected int getContentView() {
        return R.layout.activity_purchase;

    }

    @Override
    public PurchaseAddPresenter initPresenter() {
        return new PurchaseAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageDeleteItemAdapter(this, images);
        rvImages.setAdapter(adapter);
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            mPresenter.getData(id);

        }
        mPresenter.getType("goodsType");
        mPresenter.getType("transportMode");
        mPresenter.getType("isImported");
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


    @OnClick({R.id.toolbar_subtitle, R.id.text_productionDate, R.id.text_period, R.id.text_purchaseTime, R.id.text_transportMode, R.id.text_isImported, R.id.text_isSphsjc, R.id.text_isRyhsjc, R.id.text_isClhsjc, R.id.text_isXdzm, R.id.text_isFfzzwjc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData();
                break;
            case R.id.text_productionDate:
                DatePickDialog dialog = new DatePickDialog(PurchaseActivity.this);
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
                        text_productionDate.setText(time);
                    }
                });
                dialog.show();
                break;
            case R.id.text_purchaseTime:
                DatePickDialog dialog1 = new DatePickDialog(PurchaseActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog1.setYearLimt(20);
                //设置标题
                dialog1.setTitle("选择时间");
                //设置类型
                dialog1.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog1.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog1.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog1.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        text_purchaseTime.setText(time);
                    }
                });
                dialog1.show();
                break;
            case R.id.text_period:
                DatePickDialog dialog2 = new DatePickDialog(PurchaseActivity.this);
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
                        text_period.setText(time);
                    }
                });
                dialog2.show();
                break;
            case R.id.text_transportMode:
                showSelectPopWindow("transportMode");
                break;
            case R.id.text_isImported:
                showSelectPopWindow("isImported");
                break;
            case R.id.text_isSphsjc:
                showSelectPopWindow("isSphsjc");
                break;
            case R.id.text_isRyhsjc:
                showSelectPopWindow("isRyhsjc");
                break;
            case R.id.text_isClhsjc:
                showSelectPopWindow("isClhsjc");
                break;
            case R.id.text_isXdzm:
                showSelectPopWindow("isXdzm");
                break;
            case R.id.text_isFfzzwjc:
                showSelectPopWindow("isFfzzwjc");
                break;

        }
    }

    void postData() {
        Map<String, Object> params = new HashMap<>();
        params.put("operationType", 1);
        params.put("goodsName", getText(text_goodsName));
        params.put("productionDate", getText(text_productionDate));
        params.put("spec", getText(text_spec));
        params.put("count", getText(text_count));
        params.put("purchaseTime", getText(text_purchaseTime));
        params.put("supplierName", getText(text_supplierName));
        params.put("supplierAddress", getText(text_supplierAddress));
        params.put("supplierContact", getText(text_supplierContact));
        params.put("period", getText(text_period));

        params.put("isImported", isImported);
        params.put("entryPort", getText(text_entryPort));
        params.put("isSphsjc", isSphsjc);
        params.put("isRyhsjc", isRyhsjc);
        params.put("isClhsjc", isClhsjc);
        params.put("isXdzm", isXdzm);
        params.put("isFfzzwjc", isFfzzwjc);


        params.put("enclosureIds", PostUtils.getImageIds(images));
        if (!TextUtils.isEmpty(id)) {
            params.put("id", id);
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
    public void showPostImage(String localPath, ImageBack imageBack) {
        if (imageBack == null) return;
        imageBack.setPath(localPath);
        images.add(imageBack);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showImage(List<ImageBack> list) {
        if (list == null) return;
        images.clear();
        images.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showType(String type, List<DictBean> list) {
        if (list == null) return;
        if (type.equals("goodsType")) {
            goodsTypes.clear();
            goodsTypes.addAll(list);
        } else if (type.equals("transportMode")) {
            transportModes.clear();
            transportModes.addAll(list);
        } else if (type.equals("isImported")) {
            isImporteds.clear();
            isImporteds.addAll(list);
        }
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

    SelectPopupWindows selectPopupWindows;

    void showSelectPopWindow(String type) {
        UIAdjuster.closeKeyBoard(this);
        String[] array = new String[isImporteds.size()];
        String[] values = new String[isImporteds.size()];
        if (type.equals("isImported")) {
            List<String> list = new ArrayList<>();
            List<String> list1 = new ArrayList<>();
            for (int i = 0; i < isImporteds.size(); i++) {
                list.add(isImporteds.get(i).getDictLabel());
                list1.add(isImporteds.get(i).getDictValue());
            }
            array = (String[]) list.toArray(new String[list.size()]);
            values = (String[]) list1.toArray(new String[list1.size()]);
        } else if (type.equals("transportMode")) {
            List<String> list = new ArrayList<>();
            List<String> list1 = new ArrayList<>();
            for (int i = 0; i < transportModes.size(); i++) {
                list.add(transportModes.get(i).getDictLabel());
                list1.add(transportModes.get(i).getDictValue());
            }
            array = (String[]) list.toArray(new String[list.size()]);
            values = (String[]) list1.toArray(new String[list1.size()]);
        } else {
            array = new String[]{"是", "否"};
        }

        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        String[] finalValues = values;
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                if (type.equals("isImported")) {
                    text_isImported.setText(msg);
                    isImported = finalValues[index];
                    if (msg.equals("进口")) {
                        ll_isImported.setVisibility(View.VISIBLE);
                    } else {
                        ll_isImported.setVisibility(View.GONE);
                    }
                } else if (type.equals("transportMode")) {
                    text_transportMode.setText(msg);
                    transportMode = finalValues[index];
                } else if (type.equals("isSphsjc")) {
                    text_isSphsjc.setText(msg);
                    isSphsjc = index == 0 ? 1 : 0;
                } else if (type.equals("isRyhsjc")) {
                    text_isRyhsjc.setText(msg);
                    isRyhsjc = index == 0 ? 1 : 0;
                } else if (type.equals("isClhsjc")) {
                    text_isClhsjc.setText(msg);
                    isClhsjc = index == 0 ? 1 : 0;
                } else if (type.equals("isXdzm")) {
                    text_isXdzm.setText(msg);
                    isXdzm = index == 0 ? 1 : 0;
                } else if (type.equals("isFfzzwjc")) {
                    text_isFfzzwjc.setText(msg);
                    isFfzzwjc = index == 0 ? 1 : 0;
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

}