package com.zz.cold.bean;

public class PendingGoods {

    String        deptId;// null,
    String     id;// 1,
    String      coldstorageId;// 3,
    int      operationType;// 1,
    String    goodsId;// 1,
    String     goodsName;// 商品,
    String     beforeCount;// null,
    String     count;// 100.0,
    String    afterCount;// null,
    String     operationTime;// 2020-11-24,
    String    reviewStatus;// 1,
    String    reviewTime;// null,
    String     reviewRemark;// null

    public String getDeptId() {
        return deptId;
    }

    public String getId() {
        return id;
    }

    public String getColdstorageId() {
        return coldstorageId;
    }

    public int getOperationType() {
        return operationType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getBeforeCount() {
        return beforeCount;
    }

    public String getCount() {
        return count;
    }

    public String getAfterCount() {
        return afterCount;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public String getReviewRemark() {
        return reviewRemark;
    }
}
