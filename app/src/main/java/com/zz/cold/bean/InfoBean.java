package com.zz.cold.bean;

import java.util.List;

public class InfoBean {
    String title;
    String value;
    List<ImageBack> images;

    public List<ImageBack> getImages() {
        return images;
    }

    public void setImages(List<ImageBack> images) {
        this.images = images;
    }

    public InfoBean(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
