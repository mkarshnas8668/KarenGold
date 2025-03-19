package com.mkarshnas6.karenstudio.karengold

data class PriceResponse(
    val gold: List<PriceItem>,          // لیست قیمت‌های طلا
    val currency: List<PriceItem>,      // لیست قیمت‌های ارز
    val cryptocurrency: List<PriceItem> // لیست قیمت‌های ارز دیجیتال
)

