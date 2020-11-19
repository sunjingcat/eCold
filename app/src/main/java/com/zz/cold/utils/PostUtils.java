package com.zz.cold.utils;

import com.zz.cold.bean.ImageBack;

import java.util.ArrayList;

public class PostUtils {
    public static String getImageIds(ArrayList<ImageBack> imageBacks){
        String ids="";
        for (int i = 0; i < imageBacks.size(); i++) {
            if (i==imageBacks.size()-1){
                ids = ids+imageBacks.get(i).getId();
            }else {
                ids = ids+imageBacks.get(i).getId()+",";
            }
        }

        return "";
    }
}
