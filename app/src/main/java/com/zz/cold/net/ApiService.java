package com.zz.cold.net;


import com.zz.cold.bean.DailyBean;
import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.EquipmentBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.ImageBean;
import com.zz.cold.bean.PendingCompanyBean;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.StorageBean;
import com.zz.cold.bean.TemperatureBean;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.UserBasicBean;
import com.zz.cold.bean.UserInfo;
import com.zz.cold.bean.Version;
import com.zz.cold.bean.WarehouseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


/**
 * Created by admin on 2018/4/23.
 */

public interface ApiService {


    @POST("/app/v1/coldchain/login")
    Observable<JsonT<UserInfo>> login(@QueryMap Map<String, Object> params);

    @POST("/app/light/gtClientId")
    Observable<JsonT> putClientId(@QueryMap Map<String, Object> params);


    @GET("/app/v1/coldchain")
    Observable<JsonT<UserBasicBean>> getUserDetail();

    @POST("/app/v1/coldchain/logout")
    Observable<JsonT> logout();

    @POST("/app/v1/coldchain/updatePwd")
    Observable<JsonT> resetPwd(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/version/latest")
    Observable<JsonT<Version>> getVersion();

    @GET("/app/v1/supervise/version/versionCode/{versionCode}")
    Observable<JsonT<Version>> getVersionInfo(@Path("versionCode") String terminalId);

    @Multipart
    @POST("/app/v1/supervise/enclosure/upload")
    Observable<JsonT<ImageBean>> upload(@Part List<MultipartBody.Part> imgs);

    @Multipart
    @POST("/app/v1/coldchain/enclosure/upload/single")
    Observable<JsonT<ImageBack>> uploadImg(@Part List<MultipartBody.Part> imgs);


    @GET("/app/v1/coldchain/enclosure/{model}/{modelId}")
    Observable<JsonT<List<ImageBack>>> getModelImages(@Path("model") String type, @Path("modelId") String modelId);

    @GET("/app/v1/supervise/dict/getDicts")
    Observable<JsonT<List<DictBean>>> getDicts(@QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/{url}/submitSign/{id}")
    Observable<JsonT> submitSign(@Path("url") String url, @Path("id") String id, @Field("companySign") String companySign, @Field("officerSign") String officerSign);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/{url}/submitSign/{id}")
    Observable<JsonT> submitSign(@Path("url") String url, @Path("id") String id, @Field("fillerSign") String fillerSign, @Field("ownerSign") String ownerSign, @Field("reviewerSign") String reviewerSign);

    @GET("/app/v1/supervise/pdfPrint/getPdfDownPath/{id}")
    Observable<JsonT<String>> getDocInfo(@Path("id") String id, @QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldstorage/list")
    Observable<JsonT<List<QualificationBean>>> getQualificationList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldstorage/{id}")
    Observable<JsonT<QualificationBean>> getQualificationInfo(@Path("id") String id);

    @POST("/app/v1/coldchain/coldstorage")
    Observable<JsonT> postQualificationInfo(@QueryMap Map<String, Object> params);

    @PUT("/app/v1/coldchain/coldstorage")
    Observable<JsonT> editQualificationInfo(@QueryMap Map<String, Object> params);

    @DELETE("/app/v1/coldchain/coldstorage/{id}")
    Observable<JsonT> removeQualificationInfo(@Path("id") String id);

    @GET("/app/v1/coldchain/warehouse/list")
    Observable<JsonT<List<StorageBean>>> getStorageList(@QueryMap Map<String, Object> params);

    @POST("/app/v1/coldchain/warehouse/addWarehouseInfo")
    Observable<JsonT> postStorageInfo(@QueryMap Map<String, Object> params);

    @PUT("/app/v1/coldchain/warehouse/editWarehouseInfo")
    Observable<JsonT> editStorageInfo(@QueryMap Map<String, Object> params);


    @GET("/app/v1/coldchain/warehouse/editWarehouseInfo/{id}")
    Observable<JsonT<StorageBean>> getStorageInfo(@Path("id") String id);

    @DELETE("/app/v1/coldchain/warehouse/editWarehouseInfo{id}")
    Observable<JsonT> removeStorageInfo(@Path("id") String id);

    @GET("/app/v1/supervise/pdfPrint/getPdfDownPath/{id}")
    Observable<JsonT<EquipmentBean>> getEquipmentInfo(@Path("id") String id);

    @GET("/app/v1/coldchain/coldchainColdstorageDaily/list")
    Observable<JsonT<List<DailyBean>>> getDailyList(@QueryMap Map<String, Object> params);

    @POST("/app/v1/coldchain/coldchainColdstorageDaily")
    Observable<JsonT> postDailyInfo(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/pdfPrint/getPdfDownPath/{id}")
    Observable<JsonT<DailyBean>> getDailyInfo(@Path("id") String id);

    @GET("/app/v1/coldchain/warehouse/allList")
    Observable<JsonT<List<WarehouseBean>>> getWarehouseAll();

    @GET("/app/v1/coldchain/coldchainGoodsAccount/list")
    Observable<JsonT<List<TraceBean>>> getTraceList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoodsAccount/toBeReviewedCompanyList")
    Observable<JsonT<List<PendingCompanyBean>>> getPendingList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoodsAccount/listByColdstorageId")
    Observable<JsonT<List<PendingCompanyBean>>> getPendingGoodsList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/pdfPrint/getPdfDownPath/{id}")
    Observable<JsonT<TraceBean>> getTraceInfo(@Path("id") String id);


}

