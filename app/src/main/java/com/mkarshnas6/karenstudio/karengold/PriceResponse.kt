package com.mkarshnas6.karenstudio.karengold

data class PriceResponse(
    val success: Int,
    val message: String,
    val last_update: String,
    val source: String,
    val data: PriceData
)

data class PriceData(
    val golds: List<PriceItem>?,
    val currencies: List<PriceItem>?,
    val cryptocurrencies: List<PriceItem>?
)

