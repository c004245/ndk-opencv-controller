package com.jiangdg.demo

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import rx.Single

class CommonRouter: BaseRouter {

    @Multipart
    @POST("api/v1/users/images")
    fun predict(
        @Part file: MultipartBody.Part,
        @Part("has_multi_mode_subscription") hasMulti: Boolean,
        @Part("is_auto_save") isAutoSave: Boolean? =false,
        @Part("pill_name") pillName: String? = null
    ): Single<PillModel>

}