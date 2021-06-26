package com.barys.fanficapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://localhost:9191"

object FanficDBClient {

    fun getClient(): FanficDBInterface {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60,
            TimeUnit.SECONDS).build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FanficDBInterface::class.java)
    }
}
