package com.zz.cold.utils;

import com.zz.cold.bean.ImageBack;

import java.util.ArrayList;
import java.util.List;

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

        return ids;
    }
    public static List<String> getImageIdList(ArrayList<ImageBack> imageBacks){
        List<String> ids=new ArrayList<>();
        for (int i = 0; i < imageBacks.size(); i++) {
            ids.add(imageBacks.get(i).getId());
        }

        return ids;
    }
}
