package com.barys.fanficapp.data.api

import com.barys.fanficapp.data.vo.FanficDetails
import com.barys.fanficapp.data.vo.FanficResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FanficDBInterface {

    @GET("fanfics")
    fun getFanfics(@Query("page") page: Int): Single<FanficResponse>

    @GET("fanfic/{id}")
    fun getFanfic(@Path("id") id: Int): Single<FanficDetails>

}

