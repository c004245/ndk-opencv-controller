package com.jiangdg.demo

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import timber.log.Timber
import okhttp3.OkHttpClient
import com.pixplicity.easyprefs.library.Prefs
import com.rocateer.mediscount.BuildConfig
import com.rocateer.mediscount.maintabs.Pilleye
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import com.rocateer.mediscount.models.api.BaseRouter
import com.rocateer.mediscount.utils.*
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

open class BaseRouter {
    companion object {
        var PILLEYE_API_HOST = "https://api.dev.pilleye.io/"

        @JvmStatic
//        protected fun getRetrofit(clazz: Class<*>?): Any {
        protected fun getRetrofit(type: ApiClientType): Retrofit {
//            val networkLogging = HttpLoggingInterceptor { message: String? ->
//                try {
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    val jp = JsonParser()
//                    Timber.d("%s", gson.toJson(jp.parse(message)))
//                } catch (e: JsonParseException) {
//                    Timber.d(message)
//                }
//            }

//            val httpClient = OkHttpClient.Builder()
//                .connectTimeout(5, TimeUnit.MINUTES)
//                .readTimeout(5, TimeUnit.MINUTES)
//                .addInterceptor(AuthInterceptor(Prefs.getString(Constants.ACCESS_TOKEN, ""), Prefs.getString(Constants.LANG, "en")))
//                .addInterceptor(ErrorInterceptor())
//                .writeTimeout(5, TimeUnit.MINUTES)
//                .authenticator(PilleyeAuthenticator())
//            httpClient.addInterceptor(appLogging)
            return Retrofit.Builder()
                .baseUrl(PILLEYE_API_HOST)
//                .client(httpClient.build())
                .client(getApiClient(type))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }

        private fun getApiClient(type: ApiClientType): OkHttpClient {
            val appLogging = HttpLoggingInterceptor { message: String? ->
                try {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val jp = JsonParser()
                    Timber.d("%s", gson.toJson(jp.parse(message)))
                } catch (e: JsonParseException) {
                    Timber.d(message)
                }
            }
            appLogging.level = HttpLoggingInterceptor.Level.BODY
            Log.d("HWO", "getApiClient -> $type")
            return OkHttpClient().newBuilder()
                .apply {
                    addInterceptor(appLogging)
                    when (type) {
                        ApiClientType.NONACCESS ->
                            addInterceptor(PilleyeInterceptor(Prefs.getString(Constants.LANG, "en")))
                        ApiClientType.HAVEACCESS -> {
                            addInterceptor(
                                AuthInterceptor(
                                    Prefs.getString(
                                        Constants.ACCESS_TOKEN,
                                        ""
                                    ), Prefs.getString(Constants.LANG, "en")
                                )
                            )
                            addInterceptor(ErrorInterceptor())
                        }
                        ApiClientType.OLD -> {
                            addInterceptor(OldInterceptor(Prefs.getString(Constants.LANG, "en")))

                        }
                    }
                }
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build()
        }
    }

}


enum class ApiClientType {
    HAVEACCESS, NONACCESS, OLD
}