package com.barys.fanficapp.data.api

import com.barys.fanficapp.data.vo.FanficDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FanficDBInterface {

    @GET("fanfic/{id}")
    fun getFanfic(@Path("id") id: Int): Single<FanficDetails>

}

