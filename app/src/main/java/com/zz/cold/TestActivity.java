package com.zz.cold;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zz.cold.utils.GlideUtils;
import com.zz.cold.utils.LogUtils;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        iv = findViewById(R.id.iv);
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //不限数量的多选
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(TestActivity.this, 0x1); // 打开相册，n为了判断点击的是那个imageview

            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //不限数量的多选
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(TestActivity.this, 0x1); // 打开相册，n为了判断点击的是那个imageview

            }
        });
        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
                if(takePhotoIntent.resolveActivity(getPackageManager())!=null){//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
                    startActivityForResult(takePhotoIntent,0x2);//启动相机
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelectorUtils.SELECT_RESULT);
                GlideUtils.loadImage(TestActivity.this, images.get(0), iv);
            }
        }
        if(requestCode==0x2&&resultCode==RESULT_OK){
            /*缩略图信息是储存在返回的intent中的Bundle中的，
             * 对应Bundle中的键为data，因此从Intent中取出
             * Bundle再根据data取出来Bitmap即可*/
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            iv.setImageBitmap(bitmap);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.v("----------------");
    }
}