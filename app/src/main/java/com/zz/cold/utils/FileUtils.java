package com.zz.cold.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.zz.cold.net.ApiService;
import com.zz.cold.net.JsonT;
import com.zz.cold.net.RequestObserver;
import com.zz.cold.net.RxNetUtils;
import com.zz.lib.core.utils.LoadingUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class FileUtils {
    public static String getAssetsCacheFile(Context context, String fileName)   {
        File cacheFile = new File(context.getCacheDir(), fileName);
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacheFile.getAbsolutePath();
    }
    public static String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String parseName(String url) {
        String fileName = null;
        try {
            fileName = url.substring(url.lastIndexOf("/") + 1);
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }
        }
        return fileName;

    }


    public static File getLocalFile(String mFileName) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mFileName);
    }
    public static boolean isLocalExist(String mFileName) {
        return getLocalFile(mFileName).exists();
    }

public static     void getDocInfo(Activity activity,String id,int tinspectSheetType,int tinspectType) {
        Map<String, Object> map = new HashMap<>();

        map.put("tinspectSheetType", tinspectSheetType);
        map.put("tinspectType", tinspectType);
        RxNetUtils.request(RxNetUtils.getApi(ApiService.class).getDocInfo(id, map), new RequestObserver<JsonT<String>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSuccess(JsonT<String> jsonT) {
                String path = jsonT.getData();
                if (TextUtils.isEmpty(path))return;
                try {
//                    PrintUtil.printpdf(activity,path);
                } catch (Exception e) {

                }
            }

            @Override
            protected void onFail2(JsonT<String> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(activity));
    }
}
