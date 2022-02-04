package fr.isen.musoles.androidrestaurantv2.adaptater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.musoles.androidrestaurantv2.databinding.AdaptaterCategorieBinding
import fr.isen.musoles.androidrestaurantv2.model.Item


class CategorieAdaptater (private val items: List<Item>, private val onItemClick : (Item) -> Unit) : RecyclerView.Adapter<CategorieAdaptater.CategorieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {
        val binding = AdaptaterCategorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategorieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = if(item.name_fr.length > 17) item.name_fr.replaceRange( 15,item.name_fr.length,"...") else item.name_fr
        item.firstOrDefaultImage().resize(70,50).into(holder.img)
        holder.price.text = item.getPrice().toString() + "€"

        holder.title.setOnClickListener {
            onItemClick(item)
        }
        holder.img.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class CategorieViewHolder(ItemView: AdaptaterCategorieBinding) : RecyclerView.ViewHolder(ItemView.root) {
        val title: TextView = ItemView.titlecate
        val img: ImageView = ItemView.imageView
        val price : TextView = ItemView.pricecate
    }
}