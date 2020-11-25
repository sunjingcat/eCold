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

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.LogUtils;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zz.cold.R;
import com.zz.cold.base.MyBaseActivity;
import com.zz.cold.bean.CategoryBean;
import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.TracePostBean;
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

    ArrayList<CategoryBean> goodsTypes = new ArrayList<>();
    ArrayList<DictBean> isImporteds = new ArrayList<>();
    ArrayList<DictBean> transportModes = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;

    @BindView(R.id.rv_Sphsjc)
    RecyclerView rv_Sphsjc;
    ArrayList<ImageBack> images_Sphsjc = new ArrayList<>();
    ImageDeleteItemAdapter adapter_Sphsjc;

    @BindView(R.id.rv_Ryhsjc)
    RecyclerView rv_Ryhsjc;
    ArrayList<ImageBack> images_Ryhsjc = new ArrayList<>();
    ImageDeleteItemAdapter adapter_Ryhsjc;

    @BindView(R.id.rv_Clhsjc)
    RecyclerView rv_Clhsjc;
    ArrayList<ImageBack> images_Clhsjc = new ArrayList<>();
    ImageDeleteItemAdapter adapter_Clhsjc;

    @BindView(R.id.rv_Xdzm)
    RecyclerView rv_Xdzm;
    ArrayList<ImageBack> images_Xdzm = new ArrayList<>();
    ImageDeleteItemAdapter adapter_Xdzm;

    @BindView(R.id.rv_ffzzwjc)
    RecyclerView rv_ffzzwjc;
    ArrayList<ImageBack> images_ffzzwjc = new ArrayList<>();
    ImageDeleteItemAdapter adapter_ffzzwjc;

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
    TraceBean goodsBean;

    String id;

    String goodsType1 = "";
    String goodsType2 = "";
    String goodsType3 = "";

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

        rv_Sphsjc.setLayoutManager(new GridLayoutManager(this, 3));
        adapter_Sphsjc = new ImageDeleteItemAdapter(this, images_Sphsjc);
        rv_Sphsjc.setAdapter(adapter_Sphsjc);

        rv_Ryhsjc.setLayoutManager(new GridLayoutManager(this, 3));
        adapter_Ryhsjc = new ImageDeleteItemAdapter(this, images_Ryhsjc);
        rv_Ryhsjc.setAdapter(adapter_Ryhsjc);

        rv_Clhsjc.setLayoutManager(new GridLayoutManager(this, 3));
        adapter_Clhsjc = new ImageDeleteItemAdapter(this, images_Clhsjc);
        rv_Clhsjc.setAdapter(adapter_Clhsjc);

        rv_Xdzm.setLayoutManager(new GridLayoutManager(this, 3));
        adapter_Xdzm = new ImageDeleteItemAdapter(this, images_Xdzm);
        rv_Xdzm.setAdapter(adapter_Xdzm);

        rv_ffzzwjc.setLayoutManager(new GridLayoutManager(this, 3));
        adapter_ffzzwjc = new ImageDeleteItemAdapter(this, images_ffzzwjc);
        rv_ffzzwjc.setAdapter(adapter_ffzzwjc);

        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            mPresenter.getData(id);

        }
        mPresenter.getGoodsType();
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
        adapter_Sphsjc.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < images_Sphsjc.size(); i++) {
                    if (!TextUtils.isEmpty(images_Sphsjc.get(i).getPath())) {
                        localPath.add(images_Sphsjc.get(i).getPath());
                    } else {
                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - images_Sphsjc.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(PurchaseActivity.this, 1102); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images_Sphsjc.remove(option);
                adapter_Sphsjc.notifyDataSetChanged();
            }
        });

        adapter_Ryhsjc.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < images_Ryhsjc.size(); i++) {
                    if (!TextUtils.isEmpty(images_Ryhsjc.get(i).getPath())) {
                        localPath.add(images_Ryhsjc.get(i).getPath());
                    } else {
                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - images_Ryhsjc.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(PurchaseActivity.this, 1103); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images_Ryhsjc.remove(option);
                adapter_Ryhsjc.notifyDataSetChanged();
            }
        });

        adapter_Clhsjc.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < images_Clhsjc.size(); i++) {
                    if (!TextUtils.isEmpty(images_Clhsjc.get(i).getPath())) {
                        localPath.add(images_Clhsjc.get(i).getPath());
                    } else {
                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - images_Clhsjc.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(PurchaseActivity.this, 1104); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images_Clhsjc.remove(option);
                adapter_Clhsjc.notifyDataSetChanged();
            }
        });
        adapter_Xdzm.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < images_Xdzm.size(); i++) {
                    if (!TextUtils.isEmpty(images_Xdzm.get(i).getPath())) {
                        localPath.add(images_Xdzm.get(i).getPath());
                    } else {
                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - images_Xdzm.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(PurchaseActivity.this, 1105); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images_Xdzm.remove(option);
                adapter_Xdzm.notifyDataSetChanged();
            }
        });
        adapter_ffzzwjc.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < images_ffzzwjc.size(); i++) {
                    if (!TextUtils.isEmpty(images_ffzzwjc.get(i).getPath())) {
                        localPath.add(images_ffzzwjc.get(i).getPath());
                    } else {
                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - images_ffzzwjc.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(PurchaseActivity.this, 1106); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images_ffzzwjc.remove(option);
                adapter_ffzzwjc.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @OnClick({R.id.toolbar_subtitle, R.id.text_goodsType, R.id.text_productionDate, R.id.text_period, R.id.text_purchaseTime, R.id.text_transportMode, R.id.text_isImported, R.id.text_isSphsjc, R.id.text_isRyhsjc, R.id.text_isClhsjc, R.id.text_isXdzm, R.id.text_isFfzzwjc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData();
                break;
            case R.id.text_goodsType:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(PurchaseActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText()
                                + options2Items.get(options1).get(option2)
                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        text_goodsType.setText(tx);
                        goodsType1 = options1Items.get(options1).getDictValue();
                        goodsType2 = options2Items.get(options1).get(option2).getDictValue();
                        goodsType3 = options3Items.get(options1).get(option2).get(options3).getDictValue();
                    }
                }).build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);
                pvOptions.show();

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

        TracePostBean params = new TracePostBean();
        params.setOperationType(1);
        params.setGoodsName(getText(text_goodsName));
        params.setProductionDate(getText(text_productionDate));
        params.setGoodsType1(goodsType1);
        params.setGoodsType2(goodsType2);
        params.setGoodsType3(goodsType3);
        params.setSpec(getText(text_spec));
        params.setCount(getText(text_count));
        params.setPurchaseTime(getText(text_purchaseTime));
        params.setSupplierName(getText(text_supplierName));
        params.setSupplierAddress(getText(text_supplierAddress));
        params.setSupplierContact(getText(text_supplierContact));
        params.setPeriod(getText(text_period));

        params.setIsImported(isImported);
        params.setEntryPort(getText(text_entryPort));
        params.setIsSphsjc(isSphsjc);
        params.setIsRyhsjc(isRyhsjc);
        params.setIsClhsjc(isClhsjc);
        params.setIsXdzm(isXdzm);
        params.setIsFfzzwjc(isFfzzwjc);

        params.setSphsjcEnclosureIdList(PostUtils.getImageIdList(images_Sphsjc));
        params.setRyhsjcEnclosureIdList(PostUtils.getImageIdList(images_Ryhsjc));
        params.setClhsjcEnclosureIdList(PostUtils.getImageIdList(images_Clhsjc));
        params.setXdzmEnclosureIdList(PostUtils.getImageIdList(images_Xdzm));
        params.setFfzzwjcEnclosureIdList(PostUtils.getImageIdList(images_ffzzwjc));
        params.setEnclosureIds(PostUtils.getImageIdList(images));
        if (!TextUtils.isEmpty(id)) {
            params.setId(id);
        }

        mPresenter.submitData(params);
    }

    @Override
    public void showInfo(TraceBean data) {
        goodsBean = data;
        text_goodsName.setText(data.getGoodsName() + "");
        text_productionDate.setText(data.getProductionDate() + "");
        text_spec.setText(data.getSpec() + "");
        text_count.setText(data.getCount() + "");
        text_purchaseTime.setText(data.getPurchaseTime() + "");
        text_supplierName.setText(data.getSupplierName() + "");
        text_supplierAddress.setText(data.getSupplierAddress() + "");
        text_supplierContact.setText(data.getSupplierContact() + "");
        text_period.setText(data.getPeriod() + "");
        isImported = data.getIsImported();
        text_isImported.setText(data.getIsImportedText() + "");
        text_entryPort.setText(data.getEntryPort() + "");
        isSphsjc = data.getIsSphsjc();
        text_isSphsjc.setText(data.getIsSphsjcText());
        if (isImported.equals("进口")){
            ll_isImported.setVisibility(View.VISIBLE);
        }else {
            ll_isImported.setVisibility(View.GONE);
        }

        isRyhsjc = data.getIsRyhsjc();
        text_isRyhsjc.setText(data.getIsRyhsjcText());

        isClhsjc = data.getIsClhsjc();
        text_isClhsjc.setText(data.getIsClhsjcText());

        isXdzm = data.getIsXdzm();
        text_isXdzm.setText(data.getIsXdzmText());

        isFfzzwjc = data.getIsFfzzwjc();
        text_isFfzzwjc.setText(data.getIsFfzzwjcText());

        goodsType1 = data.getGoodsType1();
        goodsType2 = data.getGoodsType2();
        goodsType3 = data.getGoodsType3();
        text_goodsType.setText(data.getGoodsType1Text() + "" + data.getGoodsType2Text() + data.getGoodsType3Text());

    }


    @Override
    public void showResult() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showPostImage(int requestCode, String localPath, ImageBack imageBack) {
        if (imageBack == null) return;
        imageBack.setPath(localPath);
        switch (requestCode) {
            case 1101:
                images.add(imageBack);
                adapter.notifyDataSetChanged();
                break;
            case 1102:
                images_Sphsjc.add(imageBack);
                adapter_Sphsjc.notifyDataSetChanged();
                break;
            case 1103:
                images_Ryhsjc.add(imageBack);
                adapter_Ryhsjc.notifyDataSetChanged();
                break;
            case 1104:
                images_Clhsjc.add(imageBack);
                adapter_Clhsjc.notifyDataSetChanged();
                break;
            case 1105:
                images_Xdzm.add(imageBack);
                adapter_Xdzm.notifyDataSetChanged();
                break;
            case 1106:
                images_ffzzwjc.add(imageBack);
                adapter_ffzzwjc.notifyDataSetChanged();
                break;
        }

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
        if (type.equals("transportMode")) {
            transportModes.clear();
            transportModes.addAll(list);
        } else if (type.equals("isImported")) {
            isImporteds.clear();
            isImporteds.addAll(list);
        }
    }

    List<DictBean> options1Items = new ArrayList<>();
    List<List<DictBean>> options2Items = new ArrayList<>();
    List<List<List<DictBean>>> options3Items = new ArrayList<>();

    @Override
    public void showGoods(List<CategoryBean> list) {
        if (list == null) return;
        goodsTypes.clear();
        goodsTypes.addAll(list);
        for (CategoryBean categoryBean : goodsTypes) {
            options1Items.add((DictBean) categoryBean);
            List<List<DictBean>> optionsItems2 = new ArrayList<>();
            List<DictBean> optionsItems_02 = new ArrayList<>();
            for (CategoryBean.Child1 child1 : categoryBean.getChilds()) {
                List<DictBean> optionsItems = new ArrayList<>();
                for (CategoryBean.Child2 child2 : child1.getChilds()) {
                    optionsItems.add(child2);
                }
                optionsItems2.add(optionsItems);
                optionsItems_02.add(child1);
            }

            options2Items.add(optionsItems_02);
            options3Items.add(optionsItems2);

        }
        LogUtils.v("--");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
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

                                mPresenter.postImage(requestCode, file.getPath(), getImageBody(file.getPath()));
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.v("------onDestroy");
    }
}