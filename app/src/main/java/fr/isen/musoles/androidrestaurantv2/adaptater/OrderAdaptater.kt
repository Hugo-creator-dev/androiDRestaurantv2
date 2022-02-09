package fr.isen.musoles.androidrestaurantv2.adaptater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.musoles.androidrestaurantv2.databinding.AdaptaterOrderBinding
import fr.isen.musoles.androidrestaurantv2.model.BigShop
import fr.isen.musoles.androidrestaurantv2.model.Shop

class OrderAdaptater(private val items: List<BigShop>, private val onItemClick : (Shop) -> Unit): RecyclerView.Adapter<OrderAdaptater.OrderViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = AdaptaterOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = items[position]
        val oky : String = "commande n°" + position + ": " + item.quantity + " items pour " + item.totalPrice + "€"
        holder.title.text = oky
        holder.div.setOnClickListener {
            onItemClick(Shop(item))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class OrderViewHolder(ItemView: AdaptaterOrderBinding) : RecyclerView.ViewHolder(ItemView.root) {
        val title: TextView = ItemView.textView12
        val div: Button = ItemView.button4
    }

}