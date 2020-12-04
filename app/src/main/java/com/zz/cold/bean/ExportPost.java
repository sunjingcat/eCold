package com.zz.cold.bean;

public   class ExportPost{
    int isTransfer;
    String coldstorageId;
    String operationCount;
    String operationRemark ;
    String id ;
    int operationType ;

    public void setId(String id) {
        this.id = id;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

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