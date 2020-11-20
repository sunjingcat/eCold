package com.zz.cold;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zz.cold.utils.GlideUtils;

import java.util.ArrayList;

public class Test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        //不限数量的多选
        ImageSelector.builder()
                .useCamera(true) // 设置是否使用拍照
                .setSingle(false)  //设置是否单选
                .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                .setViewImage(true) //是否点击放大图片查看,，默认为true
                .start(Test2Activity.this, 0x1); // 打开相册，n为了判断点击的是那个imageview
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelectorUtils.SELECT_RESULT);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);


    }
}