package fr.isen.musoles.androidrestaurantv2.model

import java.io.Serializable

data class Ingredient(val id : Int,val id_shop: Int, val name_fr : String,val name_en : String,val create_date : String,val update_date : String,val id_pizza : Int) : Serializable
{
    override fun toString(): String {
        return name_fr
    }

    override fun equals(other: Any?): Boolean {
        if (other != null)
            if (other.javaClass == this.javaClass)
                return (other as Ingredient).id == id && other.create_date == create_date && other.id_pizza == id_pizza && other.name_fr == other.name_fr && other.name_en == other.name_en
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
