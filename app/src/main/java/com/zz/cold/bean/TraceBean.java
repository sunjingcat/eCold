package com.zz.cold.bean;

import android.text.TextUtils;

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
    String allCount;// 0.0,
    String purchaseTime;// 2020-11-24,
    String operationTime;// 2020-11-24,
    String supplierName;// 单位,
    String supplierAddress;// 地址,
    String supplierContact;// ,
    String productionAddress;// null,
    String transportMode;// null,
    String period;// 2020-11-25,
    int isImported;// 1,
    String entryPort;// ,
    String importRegistNum;// ,
    String importTime;// ,
    String batchNumber;// ,
    String goodsRemark;// ,
    int isSphsjc;// 0,
    int isCrjjyjyzm;// 0,
    int isBgd;// 0,
    int isXdzm;// 0,
    int isFfzzwjc;// 0,
    int isThird;// 0,
    int isMddhsjc;// 0,
    int operationType;// 0,
    String operationTypeText;// 冷库1
    String operatorName;// 冷库1
    String goodsType1Text;// ,
    String goodsType2Text;// ,
    String goodsType3Text;// ,
    String transportModeText;// ,
    String isImportedText;// 国产,
    String isSphsjcText;// 否,
    String isCrjjyjyzmText;// 否,
    String isThirdText;// 否,
    String isBgdText;// 否,
    String isXdzmText;// 否,
    String isFfzzwjcText;// 否,
    String isMddhsjcText;// 否,
    String chinaDistributorName;// ,
    String originCountry;// ,
    String coldstorageUserName;// ,
    String chinaDistributorContact;// ,
    List<ImageBack> sphsjcEnclosureList;// null,
    List<ImageBack> crjjyjyzmEnclosureList;// null,
    List<ImageBack> bgdEnclosureList;// null,
    List<ImageBack> xdzmEnclosureList;// null,
    List<ImageBack> ffzzwjcEnclosureList;// null,
    List<ImageBack> mddhsjcEnclosureList;// null,
    List<WmsBean> coldchainGoodsAccountcList;// null,

    public String getAllCount() {
        return allCount;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public String getColdstorageUserName() {
        return coldstorageUserName;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getOperationTypeText() {
        return operationTypeText;
    }

    public int getOperationType() {
        return operationType;
    }

    public int getIsMddhsjc() {
        return isMddhsjc;
    }

    public String getIsMddhsjcText() {
        return isMddhsjcText;
    }

    public List<ImageBack> getMddhsjcEnclosureList() {
        return mddhsjcEnclosureList;
    }

    public String getChinaDistributorName() {
        return chinaDistributorName;
    }

    public String getChinaDistributorContact() {
        return chinaDistributorContact;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public String getImportRegistNum() {
        return importRegistNum;
    }

    public String getImportTime() {
        return importTime;
    }

    public String getGoodsRemark() {
        return goodsRemark;
    }

    public String getIsThirdText() {
        return isThirdText;
    }

    public int getIsThird() {
        return isThird;
    }

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

    public String getIsCrjjyjyzmText() {
        return isCrjjyjyzmText;
    }

    public String getTsBgdText() {
        return isBgdText;
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

    public int getIsBgd() {
        return isBgd;
    }

    public String getIsBgdText() {
        return isBgdText;
    }

    public List<ImageBack> getCrjjyjyzmEnclosureList() {
        return crjjyjyzmEnclosureList;
    }

    public List<ImageBack> getBgdEnclosureList() {
        return bgdEnclosureList;
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
        if (TextUtils.isEmpty(purchaseTime)) {
            return operationTime;
        } else {
            return purchaseTime;
        }
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

    public int getIsImported() {
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
