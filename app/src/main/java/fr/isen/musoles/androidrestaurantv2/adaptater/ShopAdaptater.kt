package fr.isen.musoles.androidrestaurantv2.adaptater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.musoles.androidrestaurantv2.databinding.AdaptaterShopBinding
import fr.isen.musoles.androidrestaurantv2.model.Item
import fr.isen.musoles.androidrestaurantv2.model.Items

class ShopAdaptater (private val items: List<Item>, private val onItemClick : (Item) -> Unit) : RecyclerView.Adapter<ShopAdaptater.ShopViewHolder>() {
    private var total : Double = 0.0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = AdaptaterShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = if(item.name_fr.length > 14) item.name_fr.replaceRange( 11,item.name_fr.length,"...") else item.name_fr
        holder.quantity.text = item.getRealQuantity().toString() + " items"
        holder.price.text = (item.getPrice()!! * item.getRealQuantity()).toString() + "€"
        total += item.getPrice()!! * item.getRealQuantity()
        holder.cumul.text = total.toString() + "€"
        holder.title.setOnClickListener {
            onItemClick(item)
            holder.itemView.visibility = View.GONE
        }
        holder.quantity.setOnClickListener {
            onItemClick(item)
            holder.itemView.visibility = View.GONE
        }
        holder.cumul.setOnClickListener {
            onItemClick(item)
            holder.itemView.visibility = View.GONE
        }
        holder.price.setOnClickListener {
            onItemClick(item)
            holder.itemView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ShopViewHolder(ItemView: AdaptaterShopBinding) : RecyclerView.ViewHolder(ItemView.root) {
        val title: TextView = ItemView.titleadashop
        val price: TextView = ItemView.priceadashop
        val cumul: TextView = ItemView.cumulateadashop
        val quantity: TextView = ItemView.itemadashop

    }
}