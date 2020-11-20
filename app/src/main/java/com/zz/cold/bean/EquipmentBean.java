package com.zz.cold.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EquipmentBean implements Parcelable {
    String deptId;// 0,
    String id;// 2,
    String warehouseId;// 2,
    String equipmentName;// ,
    String equipmentRemark;// ,
    String enclosureIds;// null

    public EquipmentBean() {
    }

    public EquipmentBean(String equipmentName, String equipmentRemark, String enclosureIds) {
        this.equipmentName = equipmentName;
        this.equipmentRemark = equipmentRemark;
        this.enclosureIds = enclosureIds;
    }

    public EquipmentBean(Parcel in) {
        deptId = in.readString();
        id = in.readString();
        warehouseId = in.readString();
        equipmentName = in.readString();
        equipmentRemark = in.readString();
        enclosureIds = in.readString();
    }

    public static final Creator<EquipmentBean> CREATOR = new Creator<EquipmentBean>() {
        @Override
        public EquipmentBean createFromParcel(Parcel in) {
            return new EquipmentBean(in);
        }

        @Override
        public EquipmentBean[] newArray(int size) {
            return new EquipmentBean[size];
        }
    };

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public void setEquipmentRemark(String equipmentRemark) {
        this.equipmentRemark = equipmentRemark;
    }

    public void setEnclosureIds(String enclosureIds) {
        this.enclosureIds = enclosureIds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getId() {
        return id;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getEquipmentRemark() {
        return equipmentRemark;
    }

    public String getEnclosureIds() {
        return enclosureIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deptId);
        dest.writeString(id);
        dest.writeString(warehouseId);
        dest.writeString(equipmentName);
        dest.writeString(equipmentRemark);
        dest.writeString(enclosureIds);
    }
}
