package com.mkarshnas6.karenstudio.karengold

data class PriceItem(
    val date: String,   // تاریخ
    val time: String,   // ساعت
    val name: String,   // نام کالا (مثلاً سکه، دلار، بیت‌کوین و ...)
    val price: Long,    // قیمت
    val unit: String    // واحد پولی (تومان یا دلار)
)

