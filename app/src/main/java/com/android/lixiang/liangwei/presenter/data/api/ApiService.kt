package com.android.lixiang.liangwei.presenter.data.api

import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Call

import retrofit2.http.*
import java.io.File

interface ApiService {
    @POST("/global/programming/detail")
    @FormUrlEncoded
    fun detail(@Field("area") targetSentence: String,
               @Field("productType") targetSentence2: String): Observable<DetailBean>

    @POST("/nb_back/collectingInformation/insertCollectingInformation")
    @FormUrlEncoded
    fun infoCollect(@Field("loginNameId") targetSentence: String,
                    @Field("adminPlotNumber") targetSentence2: String,
                    @Field("insurer") targetSentence3: String,
                    @Field("identityType") targetSentence4: String,
                    @Field("idNumber") targetSentence5: String,
                    @Field("insurerAddress") targetSentence6: String,
                    @Field("phoneNumber") targetSentence7: String,
                    @Field("plantingPlace") targetSentence8: String,
                    @Field("insuranceAmount") targetSentence9: String,
                    @Field("shape") targetSentence10: String): Observable<InfoCollectBean>

//    @POST("/collectingInformation/selectCollectingInformation")
//    @FormUrlEncoded
//    fun infoCollectPicture(@Field("informationId") targetSentence: String,
//                           @Field("collectingPictureNames") targetSentence2: String): Observable<InfoCollectPictureBean>


    @POST("/nb_back/collectingInformation/insertCollectingPicture")
    @Multipart
    fun insertCollectingPicture(@Part("informationId") targetSentence: String,
                                @Part("collectingPictureNames") targetSentence2: Array<File>): Observable<InsertCollectingPictureBean>

    @POST("/nb_back/collectingInformation/insertCollectingPicture")
    @Multipart
    fun testInsertCollectingPicture(@Part("informationId") targetSentence: String,
                                    @Part("collectingPictureNames") targetSentence2: File): Observable<InsertCollectingPictureBean>


    @POST("/nb_back/collectingInformation/insertCollectingPicture")
    @Multipart
    fun testInsertCollectingPicture2(@Part("informationId") targetSentence: String,
                                     @Part targetSentence2: MultipartBody.Part): Observable<InsertCollectingPictureBean>

    @POST("/nb_back/collectingInformation/insertCollectingPicture")
    @Multipart
    fun InsertCollectingPictureOffical(@Query("informationId") targetSentence: String,
                                       @Part targetSentence2: List<MultipartBody.Part>): Observable<InsertCollectingPictureBean>

//    @POST("/nb_back/login")
//    @FormUrlEncoded
//    fun login(@Field("loginName") targetSentence: String,
//              @Field("passWord") targetSentence2: String): Observable<LoginInfoBean>

    @POST("/nb_back/user/updatePassword")
    @FormUrlEncoded
    fun updatePassword(@Field("oldPassword") targetSentence: String,
                       @Field("newPassword") targetSentence2: String): Observable<ResetPasswordBean>

    @POST("/send")
    @FormUrlEncoded
    fun send(@Field("phone") targetSentence: String): Observable<SendBean>

    @POST("/login")
    @FormUrlEncoded
    fun login(@Field("phone") targetSentence: String, @Field("password") targetSentence2: String): Observable<LoginBean>

    @POST("/verify")
    @FormUrlEncoded
    fun verify(@Field("phone") targetSentence: String, @Field("code") targetSentence2: String): Observable<VerifyBean>


    @POST("/reg")
    @FormUrlEncoded
    fun reg(@Field("password") targetSentence: String, @Field("name") targetSentence2: String, @Field("phone") targetSentence3: String): Observable<RegBean>


    @POST("/illegalinfo")
    @FormUrlEncoded
    fun illegalinfo(@Field("name") name: String,
                    @Field("phone") phone: String,
                    @Field("work") work: String,
                    @Field("iid") iid: String,
                    @Field("address") address: String,
                    @Field("area") area: String,
                    @Field("perimeter") perimeter: String,
                    @Field("flag") flag: String,
                    @Field("floor") floor: String,
                    @Field("type") type: String,
                    @Field("time") time: String,
                    @Field("isdanger") isdanger: String,
                    @Field("isliangwei") isliangwei: String,
                    @Field("info") info: String,
                    @Field("url") url: String,
                    @Field("status") status: String,
                    @Field("line") line: String): Observable<IllegalInfoBean>

    @POST("/updateillegalinfo")
    @FormUrlEncoded
    fun updateillegalinfo(@Field("name") name: String,
                          @Field("phone") phone: String,
                          @Field("work") work: String,
                          @Field("iid") iid: String,
                          @Field("address") address: String,
                          @Field("area") area: String,
                          @Field("perimeter") perimeter: String,
                          @Field("flag") flag: String,
                          @Field("floor") floor: String,
                          @Field("type") type: String,
                          @Field("time") time: String,
                          @Field("isdanger") isdanger: String,
                          @Field("isliangwei") isliangwei: String,
                          @Field("info") info: String,
                          @Field("url") url: String,
                          @Field("status") status: String,
                          @Field("line") line: String
                          , @Field("number") number: String
    ): Observable<UpdateIllegalInfoBean>

    @POST("/examinfo")
    @FormUrlEncoded
    fun examinfo(@Field("work") name: String,
                 @Field("lost") phone: String,
                 @Field("infonumber") work: String,
                 @Field("info") iid: String,
                 @Field("label") address: String,
                 @Field("spnumber") area: String,
                 @Field("sptime") time: String,
                 @Field("url") string: String): Observable<ExamInfoBean>

    @POST("/updateexaminfo")
    @FormUrlEncoded
    fun updateexaminfo(@Field("work") name: String,
                       @Field("lost") phone: String,
                       @Field("infonumber") work: String,
                       @Field("info") iid: String,
                       @Field("label") address: String,
                       @Field("spnumber") are: String,
                       @Field("sptime") area: String,
                       @Field("url") string: String,
                       @Field("number") time: String): Observable<UpdateExamInfoBean>

    @POST("/search")
    @FormUrlEncoded
    fun search(@Field("param") name: String,
               @Field("pagenum") phone: String,
               @Field("pagesize") work: String): Observable<SearchBean>

    @POST("/search")
    @FormUrlEncoded
    fun search_(@Field("param") name: String,
                @Field("pagenum") phone: String,
                @Field("pagesize") work: String): SearchBean

    @POST("/listexaminfo")
    @FormUrlEncoded
    fun listexaminfo(@Field("pagenum") phone: String,
                     @Field("pagesize") work: String): Observable<ListExamInfoBean>

//    @POST("/nb_back/collectingInformation/selectCollectingInformation")
//    @FormUrlEncoded
//    fun selectCollectingPicture() : Observable<ReturnInfoBean>

    @POST("/selectillegal")
    @FormUrlEncoded
    fun selectillegal(@Field("id") phone: String): Observable<SelectIllegalBean>

    @POST("/selectexam")
    @FormUrlEncoded
    fun selectexam(@Field("id") phone: String): Observable<SelectExamBean>

    @POST("/updatelabel")
    @FormUrlEncoded
    fun updatelabel(@Field("label") phone: String,
                    @Field("number") work: String): Observable<UpdateLabelBean>


    @POST("/deleteexaminfo")
    @FormUrlEncoded
    fun deleteexaminfo(@Field("ids") phone: String): Observable<DeleteExamInfoBean>

    @POST("/deleteillegalinfo")
    @FormUrlEncoded
    fun deleteillegalinfo(@Field("ids") phone: String): Observable<DeleteIllegalInfoBean>

    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("/listline")
    fun listline(): Observable<ListLineBean>

    @POST("/updatestatus")
    @FormUrlEncoded
    fun updatestatus(@Field("status") phone: String, @Field("number") phone1: String): Observable<UpdateStatusBean>

    @POST("/upload")
    @Multipart
    fun upload(@Part targetSentence: MultipartBody.Part): Observable<UploadBean>

    @POST("/upload")
    @Multipart
    fun upload2(@Part targetSentence: List<MultipartBody.Part>): Observable<UploadBean>
}
