package com.zz.cold.bean;

import java.util.List;

public class StorageBean {
    String deptId;// 112,
    String id;// 2,
    String warehouseCode;// afaw,
    String warehouseName;// 发文阿飞娃娃房,
    String temperatureName;// 巍反反复复峨,
    String coldstorageId;// 1,
    int isAccord;// 0,
    String enclosureIds;// null,
    List<EquipmentBean> equipmentList;// null

    public void setId(String id) {
        this.id = id;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setTemperatureName(String temperatureName) {
        this.temperatureName = temperatureName;
    }

    public void setColdstorageId(String coldstorageId) {
        this.coldstorageId = coldstorageId;
    }

    public void setIsAccord(int isAccord) {
        this.isAccord = isAccord;
    }

    public void setEnclosureIds(String enclosureIds) {
        this.enclosureIds = enclosureIds;
    }

    public void setEquipmentList(List<EquipmentBean> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getId() {
        return id;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getTemperatureName() {
        return temperatureName;
    }

    public String getColdstorageId() {
        return coldstorageId;
    }

    public int getIsAccord() {
        return isAccord;
    }

    public String getEnclosureIds() {
        return enclosureIds;
    }

    public List<EquipmentBean> getEquipmentList() {
        return equipmentList;
    }
}
