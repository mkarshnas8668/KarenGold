package com.mkarshnas6.karenstudio.karengold

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("FreeTsetmcBourseApi/Api_Free_Gold_Currency_v2.json")
    fun getPrices(): Call<PriceResponse>
}
