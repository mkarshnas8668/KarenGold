package com.mkarshnas6.karenstudio.karengold

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkarshnas6.karenstudio.karengold.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PriceAdapter
    private val priceList = mutableListOf<PriceItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        adapter = PriceAdapter(priceList)
        binding.recylerPrice.adapter = adapter
        binding.recylerPrice.layoutManager = LinearLayoutManager(this)

        fetchData()
    }

    private fun fetchData() {
        RetrofitClient.instance.getPrices().enqueue(object : Callback<PriceResponse> {
            override fun onResponse(call: Call<PriceResponse>, response: Response<PriceResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    priceList.clear()
                    priceList.addAll(response.body()!!.gold + response.body()!!.currency + response.body()!!.cryptocurrency)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<PriceResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "API Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}
