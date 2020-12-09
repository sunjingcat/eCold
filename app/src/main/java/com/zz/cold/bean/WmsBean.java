package com.zz.cold.bean;

public class WmsBean {
    String id;// 1,
    String       coldstorageId;// 3,
    int       operationType;// 1,
    String       operatorName;// 1,
    String     operationTypeText;// 进货,
    String     goodsId;// 1,
    String     goodsName;// 商品,
    String    beforeCount;// null,
    String     count;// 100.0,
    String     afterCount;// null,
    String     operationTime;// 2020-11-24,
    String     reviewStatus;// 1,
    String     reviewStatusText;// 待审核,
    String    reviewTime;// null,
    String     reviewRemark;// null
    String     spec;// null

    public String getOperatorName() {
        return operatorName;
    }

    public String getSpec() {
        return spec;
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

    public String getOperationTypeText() {
        return operationTypeText;
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

    public String getReviewStatusText() {
        return reviewStatusText;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public String getReviewRemark() {
        return reviewRemark;
    }
}
