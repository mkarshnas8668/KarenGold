package com.mkarshnas6.karenstudio.karengold

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/currencies")
    fun getPrices(): Call<PriceResponse>
}
