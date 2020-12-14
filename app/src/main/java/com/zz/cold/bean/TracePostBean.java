package com.zz.cold.bean;

import java.util.List;

public class TracePostBean {

    String id;// 1,
    String coldstorageId;// 3,
    String goodsName;// 商品,
    String goodsType1;// null,
    String goodsType2;// null,
    String goodsType3;// null,
    String typeRemark;// null,
    String productionDate;// 2020-11-24,
    String spec;// kg,
    String count;// 0.0,
    String purchaseTime;// 2020-11-24,
    String supplierName;// 单位,
    String supplierAddress;// 地址,
    String supplierContact;// ,
    String productionAddress;// null,
    String transportMode;// null,
    String period;// 2020-11-25,
    String isImported;// 1,
    String entryPort;// ,
    String batchNumber;// ,
    String goodsRemark;// ,
    String importRegistNum;// ,
    String importTime;// ,
    String chinaDistributorName;// ,
    String chinaDistributorContact;// ,
    String originCountry;// ,
    int isSphsjc;// 0,
    int isCrjjyjyzm;// 0,
    int isBgd;// 0,
    int isXdzm;// 0,
    int isFfzzwjc;// 0,
    int operationType;// 0,
    int isThird;// 0,
    int isMddhsjc;// 0,

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public void setIsMddhsjc(int isMddhsjc) {
        this.isMddhsjc = isMddhsjc;
    }

    public void setChinaDistributorName(String chinaDistributorName) {
        this.chinaDistributorName = chinaDistributorName;
    }

    public void setChinaDistributorContact(String chinaDistributorContact) {
        this.chinaDistributorContact = chinaDistributorContact;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    public void setImportRegistNum(String importRegistNum) {
        this.importRegistNum = importRegistNum;
    }

    public void setIsThird(int isThird) {
        this.isThird = isThird;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setColdstorageId(String coldstorageId) {
        this.coldstorageId = coldstorageId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsType1(String goodsType1) {
        this.goodsType1 = goodsType1;
    }

    public void setGoodsType2(String goodsType2) {
        this.goodsType2 = goodsType2;
    }

    public void setGoodsType3(String goodsType3) {
        this.goodsType3 = goodsType3;
    }

    public void setTypeRemark(String typeRemark) {
        this.typeRemark = typeRemark;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public void setProductionAddress(String productionAddress) {
        this.productionAddress = productionAddress;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setIsImported(String isImported) {
        this.isImported = isImported;
    }

    public void setEntryPort(String entryPort) {
        this.entryPort = entryPort;
    }

    public void setIsSphsjc(int isSphsjc) {
        this.isSphsjc = isSphsjc;
    }

    public void setIsCrjjyjyzm(int isCrjjyjyzm) {
        this.isCrjjyjyzm = isCrjjyjyzm;
    }

    public void setIsBgd(int isBgd) {
        this.isBgd = isBgd;
    }

    public void setIsXdzm(int isXdzm) {
        this.isXdzm = isXdzm;
    }

    public void setIsFfzzwjc(int isFfzzwjc) {
        this.isFfzzwjc = isFfzzwjc;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }


    List<String> sphsjcEnclosureIdList;// null,
    List<String> crjjyjyzmEnclosureIdList;// null,
    List<String> bgdEnclosureIdList;// null,
    List<String> xdzmEnclosureIdList;// null,
    List<String> ffzzwjcEnclosureIdList;// null,
    List<String> mddhsjcEnclosureIdList;// null,
    List<String> enclosureIds;// null,

    public void setMddhsjcEnclosureIdList(List<String> mddhsjcEnclosureIdList) {
        this.mddhsjcEnclosureIdList = mddhsjcEnclosureIdList;
    }

    public void setSphsjcEnclosureIdList(List<String> sphsjcEnclosureIdList) {
        this.sphsjcEnclosureIdList = sphsjcEnclosureIdList;
    }

    public void setCrjjyjyzmEnclosureIdList(List<String> crjjyjyzmEnclosureIdList) {
        this.crjjyjyzmEnclosureIdList = crjjyjyzmEnclosureIdList;
    }

    public void setBgdEnclosureIdList(List<String> BgdEnclosureIdList) {
        this.bgdEnclosureIdList = BgdEnclosureIdList;
    }

    public void setXdzmEnclosureIdList(List<String> xdzmEnclosureIdList) {
        this.xdzmEnclosureIdList = xdzmEnclosureIdList;
    }

    public void setFfzzwjcEnclosureIdList(List<String> ffzzwjcEnclosureIdList) {
        this.ffzzwjcEnclosureIdList = ffzzwjcEnclosureIdList;
    }

    public void setEnclosureIds(List<String> enclosureIds) {
        this.enclosureIds = enclosureIds;
    }

    public List<String> getSphsjcEnclosureIdList() {
        return sphsjcEnclosureIdList;
    }

    public List<String> getCrjjyjyzmEnclosureIdList() {
        return crjjyjyzmEnclosureIdList;
    }

    public List<String> getBgdEnclosureIdList() {
        return bgdEnclosureIdList;
    }

    public List<String> getXdzmEnclosureIdList() {
        return xdzmEnclosureIdList;
    }

    public List<String> getFfzzwjcEnclosureIdList() {
        return ffzzwjcEnclosureIdList;
    }

    public List<String> getEnclosureIds() {
        return enclosureIds;
    }

    public String getId() {
        return id;
    }

    public String getColdstorageId() {
        return coldstorageId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsType1() {
        return goodsType1;
    }

    public String getGoodsType2() {
        return goodsType2;
    }

    public String getGoodsType3() {
        return goodsType3;
    }

    public String getTypeRemark() {
        return typeRemark;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public String getSpec() {
        return spec;
    }

    public String getCount() {
        return count;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public String getProductionAddress() {
        return productionAddress;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public String getPeriod() {
        return period;
    }

    public String getIsImported() {
        return isImported;
    }

    public String getEntryPort() {
        return entryPort;
    }

    public int getIsSphsjc() {
        return isSphsjc;
    }

    public int getIsCrjjyjyzm() {
        return isCrjjyjyzm;
    }

    public int getIsBgd() {
        return isBgd;
    }

    public int getIsXdzm() {
        return isXdzm;
    }

    public int getIsFfzzwjc() {
        return isFfzzwjc;
    }



}
