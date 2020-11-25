package com.zz.cold.bean;

import java.util.ArrayList;

public class PendingCompanyBean {
    String id;// null,
    String loginName;// null,
    String      salt;// null,
    String      password;// null,
    String       coldstorageId;// null,
    String      flag;// null,
    String     operatorName;// 冷库1,
    String     socialCreditCode;// 123456789,
    String     licenseNumber;// 987654321,
    String     address;// ,
    String     contact;// 张三,
    String     contactInformation;// 18888888888,
    String     coldstorageType1;// 1,
    String      coldstorageType1Text;// null,
    String      coldstorageType2;// 1.2,
    String      coldstorageType2Text;// null,
    String      userType;// 2,
    String      fieldTime;// null,
    String      validDate;// null,
    String     longitude;// 123.6370661238426,
    String     latitude;// 47.216275430241495,
    String     mapAddress;// null,
    String     enclosureIds;// null,
    String     coldstorageUserCount;// null,
    int     reviewCount;// 3,
    String     goodsCount;// null,
    ArrayList<ImageBack> coldstorageUserList;// null;

    public String getLoginName() {
        return loginName;
    }

    public String getId() {
        return id;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

    public String getColdstorageId() {
        return coldstorageId;
    }

    public String getFlag() {
        return flag;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public String getColdstorageType1() {
        return coldstorageType1;
    }

    public String getColdstorageType1Text() {
        return coldstorageType1Text;
    }

    public String getColdstorageType2() {
        return coldstorageType2;
    }

    public String getColdstorageType2Text() {
        return coldstorageType2Text;
    }

    public String getUserType() {
        return userType;
    }

    public String getFieldTime() {
        return fieldTime;
    }

    public String getValidDate() {
        return validDate;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getMapAddress() {
        return mapAddress;
    }

    public String getEnclosureIds() {
        return enclosureIds;
    }

    public String getColdstorageUserCount() {
        return coldstorageUserCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public ArrayList<ImageBack> getColdstorageUserList() {
        return coldstorageUserList;
    }
}
