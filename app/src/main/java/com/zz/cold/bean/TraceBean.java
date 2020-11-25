package com.zz.cold.bean;

import java.util.List;

public class TraceBean {

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
    int isSphsjc;// 0,
    int isRyhsjc;// 0,
    int isClhsjc;// 0,
    int isXdzm;// 0,
    int isFfzzwjc;// 0,
    String operatorName;// 冷库1
    String goodsType1Text;// ,
    String goodsType2Text;// ,
    String goodsType3Text;// ,
    String transportModeText;// ,
    String isImportedText;// 国产,
    String isSphsjcText;// 否,
    String isRyhsjcText;// 否,
    String isClhsjcText;// 否,
    String isXdzmText;// 否,
    String isFfzzwjcText;// 否,
    List<ImageBack> sphsjcEnclosureList;// null,
    List<ImageBack> ryhsjcEnclosureList;// null,
    List<ImageBack> clhsjcEnclosureList;// null,
    List<ImageBack> xdzmEnclosureList;// null,
    List<ImageBack> ffzzwjcEnclosureList;// null,
    List<WmsBean> coldchainGoodsAccountcList;// null,

    public List<WmsBean> getColdchainGoodsAccountcList() {
        return coldchainGoodsAccountcList;
    }

    public String getGoodsType1Text() {
        return goodsType1Text;
    }

    public String getGoodsType2Text() {
        return goodsType2Text;
    }

    public String getGoodsType3Text() {
        return goodsType3Text;
    }

    public String getTransportModeText() {
        return transportModeText;
    }

    public String getIsImportedText() {
        return isImportedText;
    }

    public String getIsSphsjcText() {
        return isSphsjcText;
    }

    public String getIsRyhsjcText() {
        return isRyhsjcText;
    }

    public String getIsClhsjcText() {
        return isClhsjcText;
    }

    public String getIsXdzmText() {
        return isXdzmText;
    }

    public String getIsFfzzwjcText() {
        return isFfzzwjcText;
    }

    public List<ImageBack> getSphsjcEnclosureList() {
        return sphsjcEnclosureList;
    }

    public List<ImageBack> getRyhsjcEnclosureList() {
        return ryhsjcEnclosureList;
    }

    public List<ImageBack> getClhsjcEnclosureList() {
        return clhsjcEnclosureList;
    }

    public List<ImageBack> getXdzmEnclosureList() {
        return xdzmEnclosureList;
    }

    public List<ImageBack> getFfzzwjcEnclosureList() {
        return ffzzwjcEnclosureList;
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

    public int getIsRyhsjc() {
        return isRyhsjc;
    }

    public int getIsClhsjc() {
        return isClhsjc;
    }

    public int getIsXdzm() {
        return isXdzm;
    }

    public int getIsFfzzwjc() {
        return isFfzzwjc;
    }

    public String getOperatorName() {
        return operatorName;
    }
}
