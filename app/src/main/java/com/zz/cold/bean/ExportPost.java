package com.zz.cold.bean;

public   class ExportPost{
    int isTransfer;
    String coldstorageId;
    String operationCount;
    String operationRemark ;


    public void setIsTransfer(int isTransfer) {
        this.isTransfer = isTransfer;
    }

    public void setColdstorageId(String coldstorageId) {
        this.coldstorageId = coldstorageId;
    }

    public void setOperationCount(String operationCount) {
        this.operationCount = operationCount;
    }

    public void setOperationRemark(String operationRemark) {
        this.operationRemark = operationRemark;
    }
}