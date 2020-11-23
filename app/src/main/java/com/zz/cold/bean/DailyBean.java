package com.zz.cold.bean;

import com.zz.cold.BuildConfig;

import java.util.List;

public class DailyBean {
    String id;
    String deptId;// 112,
    String coldstorageId;// 1,
    String coldstorageName;// App测试用户,
    String temperatureAm;// 1,
    String temperaturePm;// 0,
    int isRegularCheck;// 0,
    String regularCheckRemark;// ,
    int isProhibitedFood;// 0,
    String prohibitedFoodRemark;// ,
    String reportTime;// 2020-11-12,
    List<Temperature> temperatureAmList;// null,
    List<Temperature> temperaturePmList;// null

    public String getId() {
        return id;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getColdstorageId() {
        return coldstorageId;
    }

    public String getColdstorageName() {
        return coldstorageName;
    }

    public String getTemperatureAm() {
        return temperatureAm;
    }

    public String getTemperaturePm() {
        return temperaturePm;
    }

    public int getIsRegularCheck() {
        return isRegularCheck;
    }

    public String getRegularCheckRemark() {
        return regularCheckRemark;
    }

    public int getIsProhibitedFood() {
        return isProhibitedFood;
    }

    public String getProhibitedFoodRemark() {
        return prohibitedFoodRemark;
    }

    public String getReportTime() {
        return reportTime;
    }

    public List<Temperature> getTemperatureAmList() {
        return temperatureAmList;
    }

    public List<Temperature> getTemperaturePmList() {
        return temperaturePmList;
    }

    public class Temperature {

        String deptId;// 112,
        String id;// 2,
        String timeType;// am,
        String warehouseId;// 2,
        String warehouseName;// 发文阿飞娃娃房,
        String temperatureName;// 巍反反复复峨,
        String temperatureValue;// -40,
        String reportTime;// 2020-11-12,
        String coldstorageDailyId;// 1,
        String downloadUrl;// /profile/upload/coldchain/image/2020/11/19/424df2d7-1070-45b6-a8da-45fa637a69bc.jpg,
        String enclosureId;//null

        public String getDeptId() {
            return deptId;
        }

        public String getId() {
            return id;
        }

        public String getTimeType() {
            return timeType;
        }

        public String getWarehouseId() {
            return warehouseId;
        }

        public String getWarehouseName() {
            return warehouseName;
        }

        public String getTemperatureName() {
            return temperatureName;
        }

        public String getTemperatureValue() {
            return temperatureValue;
        }

        public String getReportTime() {
            return reportTime;
        }

        public String getColdstorageDailyId() {
            return coldstorageDailyId;
        }

        public String getDownloadUrl() {
            return BuildConfig.API_HOST+downloadUrl;
        }

        public String getEnclosureId() {
            return enclosureId;
        }
    }
}
