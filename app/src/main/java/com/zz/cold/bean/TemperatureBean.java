package com.zz.cold.bean;

public class TemperatureBean {
    String warehouseId;// 1,
    String  temperatureValue;// -30,
    String  enclosureId;// 1
    String  enclosureURL;// 1
    String  warehouseName;// 1

    public TemperatureBean() {
    }

    public String getEnclosureURL() {
        return enclosureURL;
    }

    public void setEnclosureURL(String enclosureURL) {
        this.enclosureURL = enclosureURL;
    }

    public TemperatureBean(String warehouseId, String temperatureValue, String enclosureId, String warehouseName) {
        this.warehouseId = warehouseId;
        this.temperatureValue = temperatureValue;
        this.enclosureId = enclosureId;
        this.warehouseName = warehouseName;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setTemperatureValue(String temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    public void setEnclosureId(String enclosureId) {
        this.enclosureId = enclosureId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getTemperatureValue() {
        return temperatureValue;
    }

    public String getEnclosureId() {
        return enclosureId;
    }
}
