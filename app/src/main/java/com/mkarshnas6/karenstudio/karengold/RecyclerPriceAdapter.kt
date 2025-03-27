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
        val imgFlag: ImageView = view.findViewById(R.id.img_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_main_item, parent, false)
        return PriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val item = priceList[position]

        // بررسی `null` و مقداردهی پیش‌فرض
        holder.name.text = item.label ?: "نام موجود نیست"
        holder.price.text = item.price?.toString() ?: "قیمت موجود نیست"

        val symbol = item.name ?: "unknown"  // جلوگیری از `NullPointerException`

        val context = holder.itemView.context

        // بررسی ارزهای دیجیتال
        val cryptoSymbols = setOf("bitcoin", "tether")
        val isCrypto = cryptoSymbols.contains(symbol.lowercase())

        val flagResource = if (isCrypto) {
            context.resources.getIdentifier(
                "crypto_${symbol.lowercase()}",
                "drawable",
                context.packageName
            )
        } else {
            context.resources.getIdentifier(
                "flag_${symbol.lowercase()}",
                "drawable",
                context.packageName
            )
        }

        // تنظیم عکس پرچم با مقدار پیش‌فرض در صورت `0`
        if (flagResource != 0) {
            holder.imgFlag.setImageResource(flagResource)
        } else {
            holder.imgFlag.setImageResource(R.drawable.ic_18)
        }

        // تنظیمات ابعاد و `scaleType`
        holder.imgFlag.scaleType =
            if (isCrypto) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        val size = if (flagResource != 0) 70 else 75
        holder.imgFlag.layoutParams.apply {
            width = dpToPx(context, size)
            height = dpToPx(context, size)
        }
    }

    override fun getItemCount(): Int = priceList.size

    private fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
