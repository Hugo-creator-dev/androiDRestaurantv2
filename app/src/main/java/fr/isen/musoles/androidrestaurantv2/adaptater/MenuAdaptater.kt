package fr.isen.musoles.androidrestaurantv2.adaptater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityMenuBinding
import fr.isen.musoles.androidrestaurantv2.databinding.AdaptaterMenuBinding
import fr.isen.musoles.androidrestaurantv2.model.Items

class MenuAdaptater(private val items: List<Items>, private val onItemClick : (Items) -> Unit) : RecyclerView.Adapter<MenuAdaptater.MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = AdaptaterMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name_fr
        holder.div.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
        holder.title.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MenuViewHolder(ItemView: AdaptaterMenuBinding) : RecyclerView.ViewHolder(ItemView.root) {
        val title: TextView = ItemView.menuChoiceTitle
        val div: View = ItemView.divider
    }
}