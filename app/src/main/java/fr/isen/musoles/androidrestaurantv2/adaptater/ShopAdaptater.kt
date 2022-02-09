package fr.isen.musoles.androidrestaurantv2.adaptater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.musoles.androidrestaurantv2.databinding.AdaptaterShopBinding
import fr.isen.musoles.androidrestaurantv2.generalStatic.PersonalString
import fr.isen.musoles.androidrestaurantv2.model.Item

class ShopAdaptater (private val items: List<Item>, private val onItemClick : (Item) -> Unit,private val onItemQuantity : (Item) -> Int) : RecyclerView.Adapter<ShopAdaptater.ShopViewHolder>() {
    private var total : Double = 0.0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = AdaptaterShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = PersonalString.reduceString(item.name_fr,14,"...")
        holder.quantity.text = onItemQuantity(item).toString()
        val string1 : String = (item.getPrice() *  onItemQuantity(item)).toString() + "€"
        holder.price.text = string1
        total += item.getPrice() * onItemQuantity(item)
        val string2 : String = total.toString() + "€"
        holder.cumulus.text = string2
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ShopViewHolder(ItemView: AdaptaterShopBinding) : RecyclerView.ViewHolder(ItemView.root) {
        val title: TextView = ItemView.titleadashop
        val price: TextView = ItemView.priceadashop
        val cumulus: TextView = ItemView.cumulateadashop
        val quantity: TextView = ItemView.itemadashop

    }
}