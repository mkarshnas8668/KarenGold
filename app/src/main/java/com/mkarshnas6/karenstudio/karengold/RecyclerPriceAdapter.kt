package com.mkarshnas6.karenstudio.karengold

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PriceAdapter(private val priceList: List<PriceItem>) :
    RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    class PriceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txt_name)
        val price: TextView = view.findViewById(R.id.txt_price)
        val time: TextView = view.findViewById(R.id.txt_time)
        val unit: TextView = view.findViewById(R.id.txt_unit)
        val symbol: TextView = view.findViewById(R.id.txt_symbol)
        val changePercent: TextView = view.findViewById(R.id.txt_change_percent)
        val color_change: View = view.findViewById(R.id.color_show_change_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_main_item, parent, false)
        return PriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val item = priceList[position]

        holder.name.text = item.name ?: "نام موجود نیست"
        holder.price.text = item.price?.let { "${it}" } ?: "قیمت موجود نیست"
        holder.time.text = item.time ?: "زمان موجود نیست"
        holder.unit.text = item.unit ?: "واحد موجود نیست"
        holder.symbol.text = item.symbol ?: "نماد موجود نیست"

        val changePercent = item.change_percent ?: 0.0
        holder.changePercent.text = "${changePercent}%"

        val color = if (changePercent >= 0) android.graphics.Color.GREEN
        else android.graphics.Color.RED
        holder.color_change.setBackgroundColor(color)
    }

    override fun getItemCount(): Int = priceList.size
}
