package com.zz.cold.net;




import com.zz.cold.bean.ImageBack;
import com.zz.cold.bean.ImageBean;
import com.zz.cold.bean.UserBasicBean;
import com.zz.cold.bean.UserInfo;
import com.zz.cold.bean.Version;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
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


    @POST( "/app/v1/login")
    Observable<JsonT<UserInfo>> login(@QueryMap Map<String, Object> params);

    @POST("/app/light/gtClientId")
    Observable<JsonT> putClientId(@QueryMap Map<String, Object> params);


    @GET("/app/v1")
    Observable<JsonT<UserBasicBean>> getUserDetail();

    @POST("/app/v1/logout")
    Observable<JsonT> logout();

    @POST( "/app/v1/resetPwd")
    Observable<JsonT> resetPwd(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/version/latest")
    Observable<JsonT<Version>> getVersion();
   @GET("/app/v1/supervise/version/versionCode/{versionCode}")
    Observable<JsonT<Version>> getVersionInfo(@Path("versionCode") String terminalId);

    @Multipart
    @POST( "/app/v1/supervise/enclosure/upload")
    Observable<JsonT<ImageBean>> upload(@Part List<MultipartBody.Part> imgs);


    @POST("/app/v1/supervise/enclosure/uploadSingle")
    @FormUrlEncoded
    Observable<JsonT<String>> uploadImg( @Field("base64") String handleFile);


    @GET("/app/v1/supervise/enclosure/base64/{type}/{modelId}")
    Observable<JsonT<List<ImageBack>>> getImageBase64(@Path("type") String type, @Path("modelId") String modelId);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/{url}/submitSign/{id}")
    Observable<JsonT> submitSign(@Path("url")String url,@Path("id")String id,@Field("companySign") String companySign, @Field("officerSign") String officerSign);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/{url}/submitSign/{id}")
    Observable<JsonT> submitSign(@Path("url")String url,@Path("id")String id,@Field("fillerSign") String fillerSign, @Field("ownerSign") String  ownerSign,@Field("reviewerSign") String  reviewerSign);

    @GET("/app/v1/supervise/pdfPrint/getPdfDownPath/{id}")
    Observable<JsonT<String>> getDocInfo( @Path("id") String id,@QueryMap Map<String, Object> params);


}

