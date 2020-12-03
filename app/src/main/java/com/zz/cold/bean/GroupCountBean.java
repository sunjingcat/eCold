package com.zz.cold.bean;

public class GroupCountBean {
    String coldstorageId;// ,
    String count;// ,
    String operatorName;//
    boolean select;//

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

    public String getColdstorageId() {
        return coldstorageId;
    }

    public String getCount() {
        return count;
    }

    public String getOperatorName() {
        return operatorName;
    }


}
