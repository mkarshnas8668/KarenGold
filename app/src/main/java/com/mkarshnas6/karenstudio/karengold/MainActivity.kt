package com.mkarshnas6.karenstudio.karengold

import PersianDate
import PriceAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkarshnas6.karenstudio.karengold.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PriceAdapter
    private val priceList = mutableListOf<PriceItem>()
    private var allGoldPrices: List<PriceItem> = emptyList()
    private var allCurrencyPrices: List<PriceItem> = emptyList()
    private var allCryptoPrices: List<PriceItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

//   ....... set date on title page ................
        val todayDate = Date()
        val solarCalendar = PersianDate.Companion.SolarCalendar(todayDate)
        val persianDate = "${solarCalendar.date} ${solarCalendar.strMonth} ${solarCalendar.year}"
        binding.txtPersionDate.text = persianDate
        val miladiFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val miladiDate = miladiFormat.format(todayDate)
        binding.txtMiladiDate.text = miladiDate
        binding.txtPersionDate.setTextSize(30f)

        adapter = PriceAdapter(priceList)
        binding.recylerPrice.adapter = adapter
        binding.recylerPrice.layoutManager = LinearLayoutManager(this)

        // Fetch the data
        fetchData()

        // Set OnClickListeners for filtering
        binding.txtPriceGold.setOnClickListener {
            showGoldPrices()
            binding.txtPriceGold.setTextColor(getColor(R.color.gold_text))
            binding.txtPriceCrypto.setTextColor(getColor(R.color.white_text))
            binding.txtPriceMoney.setTextColor(getColor(R.color.white_text))

        }

        binding.txtPriceCrypto.setOnClickListener {
            showCryptoPrices()
            binding.txtPriceGold.setTextColor(getColor(R.color.white_text))
            binding.txtPriceCrypto.setTextColor(getColor(R.color.gold_text))
            binding.txtPriceMoney.setTextColor(getColor(R.color.white_text))
        }

        binding.txtPriceMoney.setOnClickListener {
            showCurrencyPrices()
            binding.txtPriceGold.setTextColor(getColor(R.color.white_text))
            binding.txtPriceCrypto.setTextColor(getColor(R.color.white_text))
            binding.txtPriceMoney.setTextColor(getColor(R.color.gold_text))
        }

//    set last date ....
        RetrofitClient.instance.getPrices().enqueue(object : Callback<PriceResponse> {
            override fun onResponse(call: Call<PriceResponse>, response: Response<PriceResponse>) {
                if (response.isSuccessful) {
                    val priceResponse = response.body()
                    priceResponse?.let {
                        binding.txtLastUpdate.text = "${it.message}\n${it.last_update}"
                    }
                }
            }

            override fun onFailure(call: Call<PriceResponse>, t: Throwable) {
                Log.e("API_ERROR", "Error: ${t.message}")
                binding.txtLastUpdate.text = "خطا در دریافت اطلاعات"
            }
        })

    }


    private fun fetchData() {
        RetrofitClient.instance.getPrices().enqueue(object : Callback<PriceResponse> {
            override fun onResponse(call: Call<PriceResponse>, response: Response<PriceResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val priceResponse = response.body()!!

                    // بررسی `null` بودن `data`
                    val data = priceResponse.data
                    if (data == null) {
                        Toast.makeText(this@MainActivity, "داده‌ای دریافت نشد!", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }

                    allGoldPrices = data.golds ?: emptyList()
                    allCurrencyPrices = data.currencies ?: emptyList()
                    allCryptoPrices = data.cryptocurrencies ?: emptyList()

                    priceList.clear()
                    priceList.addAll(allGoldPrices)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<PriceResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "API Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    // Function to display only Gold prices
    private fun showGoldPrices() {
        priceList.clear()
        priceList.addAll(allGoldPrices)
        adapter.notifyDataSetChanged()
    }

    // Function to display only Currency prices
    private fun showCurrencyPrices() {
        priceList.clear()
        priceList.addAll(allCurrencyPrices)
        adapter.notifyDataSetChanged()
    }

    // Function to display only Crypto prices
    private fun showCryptoPrices() {
        priceList.clear()
        priceList.addAll(allCryptoPrices)
        adapter.notifyDataSetChanged()
    }
}
