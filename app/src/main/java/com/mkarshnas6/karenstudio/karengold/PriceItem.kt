package com.mkarshnas6.karenstudio.karengold

data class PriceItem(
    val date: String,         // تاریخ
    val time: String,         // ساعت
    val symbol: String,       // نماد (BTC, ETH, ...)
    val name: String,         // نام (بیت کوین، اتریوم و ...)
    val price: Double,        // قیمت
    val change_percent: Double, // درصد تغییر قیمت
    val label : String
)


