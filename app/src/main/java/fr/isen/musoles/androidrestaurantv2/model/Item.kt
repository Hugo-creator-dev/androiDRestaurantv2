package fr.isen.musoles.androidrestaurantv2.model

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import fr.isen.musoles.androidrestaurantv2.R
import java.io.Serializable

data class Item(val id : Int,val name_fr : String,val name_en : String,val id_category : Int,val categ_name_fr : String,val categ_name_en : String,val images : List<String>, val ingredients : List<Ingredient>, val prices : List<Price>, var quantity : Int) : Serializable
{
    override fun toString(): String {
        return name_fr + " au prix de " + getPrice().toString() + " avec " + ingredients.size + " ingrédient."
    }

    override fun equals(other: Any?): Boolean {
        return ingredients.containsAll((other as Item).ingredients) && name_fr == other.name_fr
    }

    fun getPrice(): Double?
    {
        if (prices.firstOrNull() != null)
        {
            return prices.firstOrNull()!!.price
        }
        return null
    }

    fun getIngredients() : String
    {
        return ingredients.joinToString { it.name_fr }
    }

    fun firstOrDefaultImage() : RequestCreator
    {
        return if(images.any { it.isNotEmpty() })
            Picasso.get().load( images.find { it.isNotEmpty() } ).placeholder(R.drawable.defaultimage)
        else
            Picasso.get().load( R.drawable.defaultimage )
    }

    fun getImagesOrOneDefault() : List<RequestCreator>
    {
        return if (images.any { it.isNotEmpty() })
            images.map {  Picasso.get().load( it ).placeholder(R.drawable.defaultimage) }
        else
            ArrayList<RequestCreator>().apply { add(Picasso.get().load( R.drawable.defaultimage )) }
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun getRealQuantity() : Int
    {
        return quantity + 1
    }
}