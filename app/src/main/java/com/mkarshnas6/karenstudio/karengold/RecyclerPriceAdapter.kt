import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mkarshnas6.karenstudio.karengold.PriceItem
import com.mkarshnas6.karenstudio.karengold.R

class PriceAdapter(private val priceList: List<PriceItem>) :
    RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    class PriceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txt_name)
        val price: TextView = view.findViewById(R.id.txt_price)
        val date: TextView = view.findViewById(R.id.txt_date)
        val time: TextView = view.findViewById(R.id.txt_time)
        val unit: TextView = view.findViewById(R.id.txt_unit)
        val symbol: TextView = view.findViewById(R.id.txt_symbol)
        val changePercent: TextView = view.findViewById(R.id.txt_change_percent)
        val color_change: View = view.findViewById(R.id.color_show_change_price)
        val imgFlag: ImageView = view.findViewById(R.id.img_price) // اضافه کردن ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_main_item, parent, false)
        return PriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val item = priceList[position]

        holder.name.text = item.name ?: "نام موجود نیست"
        holder.price.text = item.price?.toString() ?: "قیمت موجود نیست"
        holder.date.text = item.date ?: "تاریخ موجود نیست"
        holder.time.text = item.time ?: "زمان موجود نیست"
        holder.unit.text = item.unit ?: "واحد موجود نیست"
        holder.symbol.text = item.symbol ?: "نماد موجود نیست"

        val changePercent = item.change_percent ?: 0.0
        holder.changePercent.text = "${changePercent}%"

        val color = if (changePercent >= 0) android.graphics.Color.GREEN
        else android.graphics.Color.RED
        holder.color_change.setBackgroundColor(color)
        holder.changePercent.setTextColor(color)

        val context = holder.itemView.context
        val isCrypto = item.symbol.equals("BTC", ignoreCase = true) || item.symbol.equals(
            "ETH",
            ignoreCase = true
        ) ||
                item.symbol.equals("dash", ignoreCase = true) || item.symbol.equals(
            "xrp",
            ignoreCase = true
        ) ||
                item.symbol.equals("ltc", ignoreCase = true)

        if (isCrypto) {
            val cryptoFlagResource = context.resources.getIdentifier(
                "crypto_${item.symbol.lowercase()}", "drawable", context.packageName
            )

            if (cryptoFlagResource != 0) {
                holder.imgFlag.setImageResource(cryptoFlagResource)
                holder.imgFlag.scaleType = ImageView.ScaleType.CENTER_CROP
                val params = holder.imgFlag.layoutParams
                params.width = dpToPx(holder.itemView.context, 70)
                params.height = dpToPx(holder.itemView.context, 70)
                holder.imgFlag.layoutParams = params
            } else {
                holder.imgFlag.setImageResource(R.drawable.ic_18)
                holder.imgFlag.scaleType = ImageView.ScaleType.CENTER_CROP

                val params = holder.imgFlag.layoutParams
                params.width = dpToPx(holder.itemView.context, 75)
                params.height = dpToPx(holder.itemView.context, 75)
                holder.imgFlag.layoutParams = params
            }
        } else {
            val flagResource = context.resources.getIdentifier(
                "flag_${item.symbol.lowercase()}", "drawable", context.packageName
            )

            if (flagResource != 0) {
                holder.imgFlag.setImageResource(flagResource)
                holder.imgFlag.scaleType = ImageView.ScaleType.FIT_CENTER

                val params = holder.imgFlag.layoutParams
                params.width = dpToPx(holder.itemView.context, 70)
                params.height = dpToPx(holder.itemView.context, 70)
                holder.imgFlag.layoutParams = params
            } else {
                holder.imgFlag.setImageResource(R.drawable.ic_18)
                holder.imgFlag.scaleType = ImageView.ScaleType.FIT_CENTER

                val params = holder.imgFlag.layoutParams
                params.width = dpToPx(holder.itemView.context, 75)
                params.height = dpToPx(holder.itemView.context, 75)
                holder.imgFlag.layoutParams = params
            }
        }
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }


    override fun getItemCount(): Int = priceList.size
}
