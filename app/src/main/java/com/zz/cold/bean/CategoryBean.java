package com.zz.cold.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.JSectionEntity;

import java.util.List;

public class CategoryBean extends JSectionEntity implements Parcelable {
    private boolean isHeader;
    String title;
    String value;
    String fatherValue;
    boolean isSelect;

    protected CategoryBean(Parcel in) {
        isHeader = in.readByte() != 0;
        title = in.readString();
        value = in.readString();
        fatherValue = in.readString();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<CategoryBean> CREATOR = new Creator<CategoryBean>() {
        @Override
        public CategoryBean createFromParcel(Parcel in) {
            return new CategoryBean(in);
        }

        @Override
        public CategoryBean[] newArray(int size) {
            return new CategoryBean[size];
        }
    };

    public boolean isHeader() {
        return isHeader;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public String getFatherValue() {
        return fatherValue;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setFatherValue(String fatherValue) {
        this.fatherValue = fatherValue;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public CategoryBean(boolean isHeader, String title, String value, String fatherValue) {
        this.isHeader = isHeader;
        this.title = title;
        this.value = value;
        this.fatherValue = fatherValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isHeader ? 1 : 0));
        dest.writeString(title);
        dest.writeString(value);
        dest.writeString(fatherValue);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }
}
