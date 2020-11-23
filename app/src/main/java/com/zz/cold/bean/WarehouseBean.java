package com.zz.cold.bean;

import java.util.List;

public class WarehouseBean {
    String content;
    int isProhibitedFood;
    int isRegularCheck;
    String reportTime;
    String prohibitedFoodRemark;
    String regularCheckRemark;
    String timeType;
    String coldchainWarehouseDailyList;

    public void setColdchainWarehouseDailyList(String coldchainWarehouseDailyList) {
        this.coldchainWarehouseDailyList = coldchainWarehouseDailyList;
    }

    public void setIsProhibitedFood(int isProhibitedFood) {
        this.isProhibitedFood = isProhibitedFood;
    }

    public void setIsRegularCheck(int isRegularCheck) {
        this.isRegularCheck = isRegularCheck;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public void setProhibitedFoodRemark(String prohibitedFoodRemark) {
        this.prohibitedFoodRemark = prohibitedFoodRemark;
    }

    public void setRegularCheckRemark(String regularCheckRemark) {
        this.regularCheckRemark = regularCheckRemark;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
