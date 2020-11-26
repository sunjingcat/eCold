package com.zz.cold.bean;

import android.text.TextUtils;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.contrarywind.interfaces.IPickerViewData;

public class DictBean implements IPickerViewData {

    String deptId;// null,
    String dictCode;// 165,
    String dictSort;// 1,
    String dictLabel;// 食品加工企业自建冷库,
    String dictValue;// 1,
    String dictType;// coldstorageType,
    String cssClass;// null,
    String listClass;// null,
    String isDefault;// Y,
    String status;// 0,
    boolean isSelect;// 0,

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getDictCode() {
        return dictCode;
    }

    public String getDictSort() {
        return dictSort;
    }

    public String getDictLabel() {
        if(TextUtils.isEmpty(dictLabel)){
            return "";
        }
        return  dictLabel;
    }

    public String getDictValue() {
        if(TextUtils.isEmpty(dictValue)){
            return "";
        }
        return  dictValue;
    }

    public String getDictType() {
        return dictType;
    }

    public String getCssClass() {
        return cssClass;
    }

    public String getListClass() {
        return listClass;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getPickerViewText() {
         if(TextUtils.isEmpty(dictLabel)){
             return "";
         }
        return  dictLabel;
    }
}
