package com.zz.cold.net;


import com.zz.cold.bean.CategoryBean;
import com.zz.cold.bean.DailyBean;
import com.zz.cold.bean.DailyPost;
import com.zz.cold.bean.DictBean;
import com.zz.cold.bean.EquipmentBean;
import com.zz.cold.bean.ExportPost;
import com.zz.cold.bean.GoodsBean;
import com.zz.cold.bean.GroupCountBean;
import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.ImageBean;
import com.zz.cold.bean.MainShowData;
import com.zz.cold.bean.PendingCompanyBean;
import com.zz.cold.bean.PendingGoods;
import com.zz.cold.bean.QualificationBean;
import com.zz.cold.bean.SellPost;
import com.zz.cold.bean.StorageBean;
import com.zz.cold.bean.TemperatureBean;
import com.zz.cold.bean.TraceBean;
import com.zz.cold.bean.TracePostBean;
import com.zz.cold.bean.UserBasicBean;
import com.zz.cold.bean.UserInfo;
import com.zz.cold.bean.Version;
import com.zz.cold.bean.WarehouseBean;
import com.zz.cold.bean.WmsBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    @PUT("/app/v1/coldchain/updatePwd")
    Observable<JsonT> resetPwd(@QueryMap Map<String, Object> params);

    @PUT("/app/v1/coldchain/coldstorage/resetPwd/{id}")
    Observable<JsonT> resetStoragePwd(@Path("id") String id,@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/version/latest")
    Observable<JsonT<Version>> getVersion();

    @GET("/app/v1/coldchain/mainShow/getMainData")
    Observable<JsonT<MainShowData>> getMainData();

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

    @GET("/app/v1/coldchain/coldchainGoodsAccount/selectGoodsType")
    Observable<JsonT<List<CategoryBean>>> getGoodsType(@QueryMap Map<String, Object> params);

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


    @GET("/app/v1/coldchain/coldchainGoods/selectImportColdstorageGroupCount")
    Observable<JsonT<List<GroupCountBean>>> selectImportColdstorageGroupCount();

    @GET("/app/v1/coldchain/coldchainGoods/selectExportColdstorageGroupCount")
    Observable<JsonT<List<GroupCountBean>>> selectExportColdstorageGroupCount();

    @GET("/app/v1/coldchain/coldchainGoods/sellList")
    Observable<JsonT<List<TraceBean>>> sellList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoods/salesAccount")
    Observable<JsonT<List<TraceBean>>> salesAccount(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoods/importExportAccount")
    Observable<JsonT<List<TraceBean>>> importExportAccount(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoods/importList/{coldstorageId}")
    Observable<JsonT<List<TraceBean>>> importList(@Path("coldstorageId") String coldstorageId,@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoods/exportList/{coldstorageId}")
    Observable<JsonT<List<TraceBean>>> exportList(@Path("coldstorageId") String coldstorageId,@QueryMap Map<String, Object> params);

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

    @POST("/app/v1/coldchain/equipment/addEquipmentInfo")
    Observable<JsonT> postEquipmentInfo(@QueryMap Map<String, Object> params);

    @PUT("/app/v1/coldchain/equipment/editEquipmentInfo")
    Observable<JsonT> editEquipmentInfo(@QueryMap Map<String, Object> params);

    @Headers("Content-Type: application/json")
    @POST("/app/v1/coldchain/warehouse/addWarehouseInfo")
    Observable<JsonT> postStorageInfo(@Body StorageBean requestBody);

    @Headers("Content-Type: application/json")
    @PUT("/app/v1/coldchain/warehouse/editWarehouseInfo")
    Observable<JsonT> editStorageInfo(@Body StorageBean requestBody);


    @GET("/app/v1/coldchain/warehouse/{id}")
    Observable<JsonT<StorageBean>> getStorageInfo(@Path("id") String id);

    @DELETE("/app/v1/coldchain/warehouse/removeWarehouseInfo/{id}")
    Observable<JsonT> removeStorageInfo(@Path("id") String id);

    @GET("/app/v1/coldchain/equipment/{id}")
    Observable<JsonT<EquipmentBean>> getEquipmentInfo(@Path("id") String id);

    @DELETE("/app/v1/coldchain/equipment/removeEquipmentInfo/{id}")
    Observable<JsonT> removeEquipment(@Path("id") String id);

    @GET("/app/v1/coldchain/coldchainColdstorageDaily/list")
    Observable<JsonT<List<DailyBean>>> getDailyList(@QueryMap Map<String, Object> params);


    @Headers("Content-Type: application/json")
    @POST("/app/v1/coldchain/coldchainColdstorageDaily")
    Observable<JsonT> postDailyInfo(@Body WarehouseBean requestBody);

    @GET("/app/v1/coldchain/coldchainColdstorageDaily/{id}")
    Observable<JsonT<DailyBean>> getDailyInfo(@Path("id") String id);

    @GET("/app/v1/coldchain/warehouse/allList")
    Observable<JsonT<List<StorageBean>>> getWarehouseAll();

    @GET("/app/v1/coldchain/coldchainGoodsAccount/list")
    Observable<JsonT<List<TraceBean>>> getTraceList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoodsAccount/toBeReviewedCompanyList")
    Observable<JsonT<List<PendingCompanyBean>>> getPendingList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoods/toBeReviewedList")
    Observable<JsonT<List<PendingGoods>>> getPendingGoodsList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoodsAccount/notPassList")
    Observable<JsonT<List<WmsBean>>> getPendingHisList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/coldchain/coldchainGoodsAccount/{id}")
    Observable<JsonT<TraceBean>> getTraceInfo(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("/app/v1/coldchain/coldchainGoods")
    Observable<JsonT> postGoodsAccount(@Body TracePostBean requestBody);

    @Headers("Content-Type: application/json")
    @POST("/app/v1/coldchain/coldchainGoods/detailsImport/{goodsId}")
    Observable<JsonT> postGoodsAccountJian(@Path("goodsId") String goodsId,@Body ExportPost requestBody);

    @Headers("Content-Type: application/json")
    @POST("/app/v1/coldchain/coldchainGoods/export/{goodsId}")
    Observable<JsonT> exportGoodsAccountJian(@Path("goodsId") String goodsId,@Body ExportPost requestBody);

    @POST("/app/v1/coldchain/coldchainGoods/sell/{goodsId}")
    Observable<JsonT> postSell(@Path("goodsId") String goodsId,@QueryMap Map<String, Object> params);

    @Headers("Content-Type: application/json")
    @POST("/app/v1/coldchain/coldchainGoods/confirm")
    Observable<JsonT<String>> confirmGoodsAccount(@Body TracePostBean requestBody);

    @POST("/app/v1/coldchain/coldchainGoods/review/{accountId}")
    Observable<JsonT> reviewGoods(@Path("accountId") String accountId,@QueryMap Map<String, Object> params);
}

